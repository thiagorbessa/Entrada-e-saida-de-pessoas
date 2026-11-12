@echo off
REM Backup fisico COMPLETO do MySQL que roda em container Docker (Windows).
REM Gera dois arquivos .sql em .\backups :
REM   1) backup_<banco>_<data>.sql            -> o banco da aplicacao (todas as tabelas, inclusive
REM                                              logs e tabelas anuais). Restaura pela tela "Importar backup".
REM   2) backup_servidor_completo_<data>.sql  -> o servidor inteiro (todos os bancos + usuarios/permissoes
REM                                              do MySQL). Para desastre; restaure por linha de comando.
REM
REM Uso: de dois cliques ou rode backup-banco.bat
REM Agendar: Agendador de Tarefas do Windows apontando para este arquivo.
REM Obs.: se a senha tiver os caracteres % & ^ ! o .bat pode interpretar errado; prefira uma senha sem eles.

setlocal

REM ==================== CONFIGURACAO ====================
set "CONTAINER=mysql_db"
set "DB_NAME=entrada_saida_pessoas"
set "DB_USER=root"
set "DB_PASS=root"
set "DESTINO=%~dp0backups"
set "DIAS_RETENCAO=30"
set "SERVIDOR_COMPLETO=1"
REM DIAS_RETENCAO: apaga backups com mais de N dias; 0 = nunca apagar
REM SERVIDOR_COMPLETO: 1 = gera tambem o dump de todos os bancos do servidor; 0 = so o banco da aplicacao
REM ======================================================

REM Banco da aplicacao: sem --routines/--events (geram DELIMITER, que a tela de importar nao entende).
set "OPTS=--single-transaction --no-tablespaces --default-character-set=utf8mb4 --hex-blob"

if not exist "%DESTINO%" mkdir "%DESTINO%"

for /f %%i in ('powershell -NoProfile -Command "Get-Date -Format yyyy-MM-dd_HH-mm-ss"') do set "TS=%%i"
set "ARQ=%DESTINO%\backup_%DB_NAME%_%TS%.sql"
set "DESTINO2=%DESTINO%\servidor-completo-NAO-IMPORTAR-PELA-TELA"
set "ARQ2=%DESTINO2%\backup_servidor_completo_%TS%.sql"

REM ---------- 1) Banco da aplicacao ----------
echo [1/2] Gerando backup do banco %DB_NAME% em: %ARQ%

REM A senha vai por variavel de ambiente (MYSQL_PWD) para nao aparecer na lista de processos.
docker exec -e "MYSQL_PWD=%DB_PASS%" %CONTAINER% mysqldump -u %DB_USER% %OPTS% %DB_NAME% > "%ARQ%"
if errorlevel 1 (
    echo ERRO: mysqldump do banco falhou.
    del "%ARQ%" 2>nul
    exit /b 1
)

REM O mysqldump termina com "-- Dump completed on ..."; se nao estiver no arquivo, ele esta truncado.
findstr /C:"Dump completed" "%ARQ%" >nul
if errorlevel 1 (
    echo ERRO: o arquivo gerado parece incompleto.
    del "%ARQ%" 2>nul
    exit /b 1
)

REM Conferencia: toda tabela do banco precisa ter um CREATE TABLE no arquivo.
set "N_TAB="
set "N_DUMP="
for /f "usebackq delims=" %%n in (`docker exec -e "MYSQL_PWD=%DB_PASS%" %CONTAINER% mysql -u %DB_USER% -N -B -e "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema='%DB_NAME%'"`) do set "N_TAB=%%n"
for /f %%c in ('findstr /B /C:"CREATE TABLE" "%ARQ%" ^| find /c /v ""') do set "N_DUMP=%%c"
if not defined N_TAB echo AVISO: nao foi possivel contar as tabelas do banco para conferir o arquivo.
if defined N_TAB if not "%N_TAB%"=="%N_DUMP%" (
    echo ERRO: o banco tem %N_TAB% tabelas, mas o arquivo tem %N_DUMP%. Backup descartado.
    del "%ARQ%" 2>nul
    exit /b 1
)
echo Conferido: %N_DUMP% tabelas no arquivo.

REM ---------- 2) Servidor completo ----------
set "NOME2="
if not "%SERVIDOR_COMPLETO%"=="1" goto apos_servidor
if not exist "%DESTINO2%" mkdir "%DESTINO2%"
echo [2/2] Gerando backup de TODOS os bancos do servidor em: %ARQ2%
docker exec -e "MYSQL_PWD=%DB_PASS%" %CONTAINER% mysqldump -u %DB_USER% %OPTS% --routines --triggers --events --all-databases > "%ARQ2%"
if errorlevel 1 (
    echo AVISO: o dump do servidor completo falhou; o backup do banco da aplicacao foi mantido.
    del "%ARQ2%" 2>nul
    goto apos_servidor
)
findstr /C:"Dump completed" "%ARQ2%" >nul
if errorlevel 1 (
    echo AVISO: o dump do servidor completo parece incompleto e foi descartado.
    del "%ARQ2%" 2>nul
    goto apos_servidor
)
for %%A in ("%ARQ2%") do set "NOME2=%%~nxA"
:apos_servidor

REM ---------- Retencao ----------
if not "%DIAS_RETENCAO%"=="0" (
    forfiles /p "%DESTINO%" /m "backup_%DB_NAME%_*.sql" /d -%DIAS_RETENCAO% /c "cmd /c del @path" >nul 2>&1
    forfiles /p "%DESTINO2%" /m "backup_servidor_completo_*.sql" /d -%DIAS_RETENCAO% /c "cmd /c del @path" >nul 2>&1
)

REM ---------- Registra na tela "Logs de Auditoria" ----------
REM Descobre sozinho a tabela de logs (a que tem a coluna usuario_cpf e nao termina em _AAAA).
REM Falha aqui NAO invalida o backup: apenas avisa.
for %%A in ("%ARQ%") do set "TAMANHO=%%~zA"
for %%A in ("%ARQ%") do set "NOME=%%~nxA"
set "DET=Backup fisico gerado: %NOME% - %TAMANHO% bytes - %N_DUMP% tabelas."
if defined NOME2 set "DET=%DET% Servidor completo: %NOME2%."

set "SQLTMP=%TEMP%\log_backup_%TS%.sql"
echo SET NAMES utf8mb4; > "%SQLTMP%"
echo SET @det := '%DET%'; >> "%SQLTMP%"
echo SET @tab := (SELECT table_name FROM information_schema.columns WHERE table_schema = DATABASE() AND column_name = 'usuario_cpf' AND table_name NOT REGEXP '_[0-9]{4}$' LIMIT 1); >> "%SQLTMP%"
echo SET @idt := (SELECT data_type FROM information_schema.columns WHERE table_schema = DATABASE() AND table_name = @tab AND column_name = 'id' LIMIT 1); >> "%SQLTMP%"
echo SET @sql := CONCAT('INSERT INTO ', @tab, ' (id, data_hora, usuario_cpf, usuario_nome, acao, entidade, entidade_descricao, detalhes) VALUES (', IF(@idt IN ('binary','varbinary'), 'UUID_TO_BIN(UUID())', 'UUID()'), ', NOW(), ''-'', ''Rotina de backup'', ''BACKUP_FISICO'', ''BACKUP'', ''Backup fisico do banco (.sql)'', ?)'); >> "%SQLTMP%"
echo PREPARE st FROM @sql; >> "%SQLTMP%"
echo EXECUTE st USING @det; >> "%SQLTMP%"
echo DEALLOCATE PREPARE st; >> "%SQLTMP%"

docker exec -i -e "MYSQL_PWD=%DB_PASS%" %CONTAINER% mysql -u %DB_USER% --default-character-set=utf8mb4 %DB_NAME% < "%SQLTMP%"
if errorlevel 1 echo AVISO: backup OK, mas nao foi possivel registrar o log de auditoria.
del "%SQLTMP%" 2>nul

echo OK: backup concluido.
endlocal
exit /b 0