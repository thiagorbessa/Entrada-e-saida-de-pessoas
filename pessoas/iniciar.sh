#!/bin/bash

# Vai para a pasta onde o script esta localizado
cd "$(dirname "$0")"

echo "[1/3] Verificando o arquivo JAR da aplicacao..."
# Altere para o caminho correto do seu jar gerado pelo Maven
JAR_FILE="target/entrada-saida-pessoas-0.0.1-SNAPSHOT.jar"

if [ ! -f "$JAR_FILE" ]; then
    echo "O arquivo JAR nao foi encontrado. Compilando com o Maven..."
    mvn clean package -DskipTests
    if [ ! -f "$JAR_FILE" ]; then
        echo "ERRO: Nao foi possivel gerar o arquivo JAR."
        read -p "Pressione [Enter] para sair..."
        exit 1
    fi
else
    echo "Arquivo JAR encontrado com sucesso!"
fi

echo "[2/3] Verificando se o banco de dados Docker ja existe..."
# Verifica se o volume do MySQL ja existe no Docker
if ! docker volume inspect entrada-saida-pessoas_mysql_data &>/dev/null; then
    echo "Primeira inicializacao: Banco de dados nao encontrado. Subindo Docker..."
    docker compose up -d --build
else
    echo "Banco de dados ja existente encontrado. Preservando seus dados..."
    docker compose up -d
fi

echo "[3/3] Sistema rodando com sucesso!"
echo "Acesse no navegador: http://localhost:8080"
read -p "Pressione [Enter] para fechar esta janela..."