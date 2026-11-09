@echo off
setlocal
title Inicializador do Sistema - Docker Mode

echo [1/2] Verificando o ambiente do banco de dados...
docker volume inspect entrada-saida-pessoas_mysql_data >nul 2>&1
if %errorlevel% neq 0 (
    echo Primeira inicializacao detectada: Criando banco e compilando a aplicacao do zero...
    docker compose up -d --build
) else (
    echo Banco de dados existente detectado. Atualizando codigo e mantendo os dados...
    docker compose build --no-cache
    docker compose up -d
)

echo [2/2] Sistema pronto e rodando no Docker!
echo Acesse no navegador: http://localhost:8080
pause
