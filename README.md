# Sistema de Controle de Entrada, Saída e Auditoria Corporativa

Sistema web corporativo desenvolvido em **Java (Spring Boot)** e **Thymeleaf**, estruturado com arquitetura orientada a serviços, segurança avançada via Spring Security e isolamento estrito de dados por unidades operacionais (ex: `hc1`, `hc2`).

---

## 🏛️ Visão Geral e Arquitetura de Permissões

O sistema foi concebido para gerir portarias e fluxos de atendimento em complexos divididos por unidades físicas, garantindo privacidade e controle total de conformidade:

* **Administrador Geral (`ROLE_GERAL`)**: Possui privilégios globais. Pode visualizar, cadastrar, editar e excluir registros em **todas** as unidades, gerir usuários e auditar todas as ações do sistema.
* **Administradores de Unidade / Operadores**: O acesso a dados e operações de CRUD é restrito estritamente ao conjunto de unidades atribuídas ao perfil do usuário (ex: operadores vinculados apenas a `hc1` e `hc2` só visualizam e operam nestes escopos).

---

## 🔒 Segurança do Banco de Dados no Docker (`.env`)

Para evitar a exposição de credenciais sensíveis (usuário e senha) no código-fonte ou no repositório do GitHub, a configuração do banco de dados MySQL via Docker utiliza variáveis de ambiente centralizadas em um arquivo `.env`.

### 1. Configurar o arquivo `.env`
Na raiz do projeto, crie o arquivo `.env` (caso ainda não exista) com as seguintes configurações:

```env
# altere aqui o username e password do banco de dados que irá subir no docker
DB_NAME=entrada_saida_pessoas
DB_USER=root
DB_ROOT_PASSWORD=sua_senha_super_segura_aqui
```

> **Atenção:** Certifique-se de que o arquivo `.env` está incluído no seu arquivo `.gitignore` para impedir que suas senhas reais sejam enviadas ao Git!
>
> 

---

## 🚀 Principais Funcionalidades

* **Controle de Entrada e Saída**: Registro automatizado de fluxo de pessoas, acompanhamento de status em tempo real e histórico de movimentações por indivíduo.
* **Módulo de Auditoria Integrado (`LogAuditoria`)**: Registro automático de todas as ações de criação, edição e exclusão no sistema, incluindo comparação detalhada de campos "antes vs. depois" e conversão consistente para identificadores `UUID`.
* **Gestão de Usuários e Segurança**: Cadastro de operadores e administradores com seleção dinâmica de perfis e unidades de atendimento.
* **Auto-gestão de Senha**: Funcionalidade nativa integrada em um modal flutuante na página principal, permitindo que qualquer usuário autenticado altere sua própria senha com validação segura da senha antiga.
* **Busca Dinâmica e Ordenação A/Z - Z/A**: Interface altamente responsiva com pesquisa instantânea via AJAX e ordenação alfabética interativa em todas as colunas das tabelas (Listagem de Pessoas, Usuários e Logs de Auditoria).
* **Organização de Estilos Centralizada**: Layout moderno e limpo com folha de estilos global unificada (`estilo.css`).

---

## 🛠️ Como Executar via Docker

Certifique-se de que tem o **Docker** e o **Docker Compose** instalados na sua máquina.

1. Na pasta raiz do projeto, abra o terminal.
2. Execute os comandos abaixo para limpar contêineres antigos, recompilar a imagem do zero (sem cache) e subir a aplicação:

```bash
# 1. Para e remove contêineres e volumes anteriores
docker compose down -v

# 2. Reconstrói a imagem sem usar cache (forçando compilação limpa do código)
docker compose build --no-cache

# 3. Inicia os contêineres em segundo plano
docker compose up -d
```

3. Acesse o sistema no seu navegador através de: `http://localhost:8080`



---

# Guia de Manutenção, Atualização e Backup no Docker

## 1. Como Atualizar a Aplicação Sem Apagar o Banco de Dados

Quando você utiliza o comando `docker compose down -v`, o parâmetro `-v` (volumes) apaga o volume de dados do banco, fazendo com que todos os registros cadastrados sejam perdidos.

Para atualizar a aplicação (recompilar o código e reiniciar os contêineres) mantendo os dados do banco intactos, **não** utilize o parâmetro `-v`. Execute os seguintes comandos na raiz do projeto:

```bash
# 1. Para os contêineres mantendo os dados salvos no volume do Docker
docker compose down

# 2. Reconstrói a imagem da aplicação do zero (sem cache) com as novas alterações do código
docker compose build --no-cache

# 3. Sobe novamente os contêineres em segundo plano
docker compose up -d
```

Dessa forma, seu código será atualizado e o banco de dados continuará exatamente como estava antes.

---

## 2. Como Fazer um Backup do Banco de Dados do Docker

Para exportar e salvar um backup do seu banco de dados MySQL que está rodando dentro do contêiner, você pode utilizar o comando `docker exec` em conjunto com o `mysqldump`.

Abra o seu terminal e execute o comando abaixo (substitua a senha e o nome do arquivo se necessário):

```bash
docker exec -i mysql_db mysqldump -u root -proot entrada_saida_pessoas > backup_sistema.sql
```

> **Nota:** No exemplo acima:
> - `mysql_db` é o nome do contêiner definido no `docker-compose.yml`.
> - `root` é o usuário do banco.
> - `entrada_saida_pessoas` é o nome da base de dados.
> - O arquivo `backup_sistema.sql` será gerado na pasta onde você executou o comando.

---

## 3. Como Restaurar o Banco de Dados no Docker

Se precisar restaurar um backup anterior (por exemplo, após uma migração ou restauração de ambiente), certifique-se de que o contêiner do banco esteja rodando e execute o comando de importação:

```bash
docker exec -i mysql_db mysql -u root -proot entrada_saida_pessoas < backup_sistema.sql
```

Esse comando irá ler o arquivo `backup_sistema.sql` e aplicar todas as tabelas e registros de volta para dentro do banco de dados no Docker.

## 🌍 Exposição Pública com Ngrok (Windows e macOS)

O **Ngrok** permite expor a sua aplicação local para a internet de forma segura (ideal para testes remotos).

### Pré-requisitos
1. Crie uma conta gratuita em [ngrok.com](https://ngrok.com) e obtenha o seu *AuthToken*.
2. Configure o token no seu terminal:
   ```bash
   ngrok config add-authtoken SEU_TOKEN_AQUI
   ```

---

### 💻 No Windows

1. Com a aplicação rodando no Docker (`http://localhost:8080`), abra o **Prompt de Comando (CMD)** ou **PowerShell**.
2. Inicie o túnel para a porta 8080:
   ```cmd
   ngrok http 8080
   ```
3. Copie a **Forwarding URL** gerada (ex: `https://xxxx-xxxx.ngrok-free.app`) e utilize-a no navegador.

---

### 🍏 No macOS

1. Com a aplicação rodando no Docker (`http://localhost:8080`), abra o **Terminal**.
2. Inicie o túnel com o comando:
   ```bash
   ngrok http 8080
   ```
3. Copie a URL pública fornecida no terminal (`https://xxxx-xxxx.ngrok-free.app`) para acessar o sistema externamente.

---

## 📦 Comandos Úteis do Git (Atualização do Repositório)

Para enviar novas alterações para o seu repositório remoto no GitHub:

```bash
git status
git add .
git commit -m "Adiciona configuração segura de variáveis de ambiente no README"
git push origin main
```
*(Se o seu ramo principal for `master`, substitua `main` por `master`).*
