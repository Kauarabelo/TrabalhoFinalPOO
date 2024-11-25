# Trabalho Final POO - Sistema de Leilão

Este projeto implementa um sistema de leilão em Java utilizando Spring Boot. O sistema permite a criação, gerenciamento e consulta de leilões e produtos, bem como a associação de instituições financeiras.

## Índice
- [Visão Geral](#visão-geral)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Requisitos](#requisitos)
- [Procedimentos de Build](#procedimentos-de-build)
- [Execução do Projeto](#execução-do-projeto)
- [Testes dos Endpoints](#testes-dos-endpoints)
- [Endpoints Disponíveis](#endpoints-disponíveis)
- [Diagrama de Classe](#diagrama-de-classe)

## Visão Geral

O **Sistema de Leilão** foi desenvolvido para facilitar a gestão e operação de leilões de dispositivos de informática e veículos apreendidos. O sistema oferece funcionalidades de cadastro, consulta, atualização e remoção de itens e leilões, além de permitir que clientes façam lances nos produtos disponíveis.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **H2 Database** (para testes)
- **Maven**
- **Lombok**
- **JUnit** (para testes)
- **Postman** (para testes de API)

## Requisitos

Para executar este projeto, você precisará ter os seguintes requisitos instalados em sua máquina:

- **Java Development Kit (JDK) 17**
- **Maven**
- **IDE** como IntelliJ IDEA ou Eclipse (opcional, mas recomendado)

## Procedimentos de Build

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/Kauarabelo/TrabalhoFinalIPOO.git

2. Importe o projeto no IntelliJ:

- Abra o IntelliJ IDEA.<br>
- Selecione "File" > "Open..." e navegue até o diretório onde você clonou o repositório.<br>
- Selecione o arquivo pom.xml e clique em "Open".<br>
- O IntelliJ irá reconhecer que se trata de um projeto Maven e fará o download das dependências automaticamente.

## Execução do Projeto

1. Iniciar o servidor:
- No IntelliJ, localize a classe principal do seu projeto que contém o método main, geralmente chamada Application ou TrabalhoFinalPooApplication.
- Clique com o botão direito do mouse sobre a classe e selecione "Run 'Application.main()'".
- O aplicativo será iniciado na porta 8080 por padrão. Você pode acessar a API através da URL http://localhost:8080.

## Testes dos Endpoints
Os testes dos endpoints podem ser realizados usando ferramentas como Postman. Segue o link do import em formato json: <a href="https://github.com/Kauarabelo/TrabalhoFinalPOO/blob/main/PostManCode.txt">Postman Collection.</a>

## Endpoints Disponíveis
A lista completa de endpoints disponíveis pode ser acessada no Swagger. <a href="http://localhost:8080/swagger-ui/index.html">Swagger UI</a>

## Diagrama de Classe
![Diagrama de Classe](https://github.com/Kauarabelo/TrabalhoFinalPOO/blob/main/DiagramaDeClasses.png)
