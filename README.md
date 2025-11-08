<img width="100%" bottom=50px src ="https://capsule-render.vercel.app/api?type=waving&height=100&color=FF78CB&section=header&reversal=false&descAlign=22&descAlignY=42"/>

<div align = "center" id="english">
<a href="https://github.com/albiecr"><img src="https://readme-typing-svg.herokuapp.com?font=Sour+Gummy&size=40&pause=100&color=EF82F7&width=370&height=60&lines=Taxi+Booking+System" alt="Typing SVG" /></a></div>

<p align="center">
  <strong>English</strong> | <a href="#português">Português</a>
</p>

<p align="center">
REST API built with Spring Boot, Java, and MySQL for a ride-hailing system.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/status-in%20development-yellow" alt="Project Status">
</p>


## 🚕 About the Project

This repository contains the back-end (API) for the ride-hailing system. It is responsible for all business logic and data management, including:

* User (passenger) and driver registration and authentication.
* Processing ride requests.
* Finding available drivers.
* Updating ride status (e.g., PENDING, ACCEPTED, COMPLETED).

This project was developed to apply and deepen knowledge in RESTful API development using the Spring ecosystem.

## 🛠️ Technologies Used

The project was built with the following technologies:

* **Language:** Java 17
* **Framework:** Spring Boot 3
* **Data Access:** Spring Data JPA (Hibernate)
* **Database:** MySQL
* **Package Manager:** Maven
* **Testing Tool:** Postman

## 🚀 How to Run the Project

Follow the steps below to run the application locally.

### 1. Prerequisites

* JDK 17 (or higher) installed.
* MySQL Server installed and running.
* Maven installed (or use the Maven wrapper - `mvnw`).

### 2. Clone the Repository

```bash
git clone https://github.com/YOUR-USERNAME/taxi-api.git
cd taxi-api
```
### 3. Configure the Database
1. Access your MySQL terminal (or MySQL Workbench).

2. Create the database for the application:
```SQL
CREATE DATABASE taxi_system_db;                                        # Create Database
CREATE USER 'taxi_user'@'localhost' IDENTIFIED BY '123456';            # Create a user for your application
GRANT ALL PRIVILEGES ON taxi_system_db.* TO 'taxi_user'@'localhost';   # Grant permissions to the user
FLUSH PRIVILEGES;                                                      # Update permissions
````
### 4. Run the Application
You can run the application in two ways:

**Via Maven (recommended):**
```Bash
./mvnw  spring-boot:run
```
**Via your IDE (VS Code, IntelliJ)**
* Open the project.
* Find the main class.
* Run it as Java Application.

The API will be available at `http://localhost:8080`

## 📍 API Endpoints(Planned)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/users` | Registers a new user (passenger) |
| `POST` | `/api/drivers` | Registers a new driver |
| `POST` | `/api/rides/request` | Registers a new ride |
| `GET` | `/api/rides/{id}` | Fetches the status of a ride |
| `PUT` | `/api/rides/{id}/accept` | Driver accepts a ride |
| `PUT` | `/api/rides/{id}/complete` | Driver completes a ride |

## 💻 Related Projects
The front-end(HTML, CSS and JavaScript) that consumes this API will be developed in a separate repository:
* The link will be here soon.
<br>
<br>

<div align = "center" id="português">
<a href="https://github.com/albiecr"><img src="https://readme-typing-svg.herokuapp.com?font=Sour+Gummy&size=40&pause=100&color=EF82F7&width=500&height=60&lines=Sistema+de+Reserva+de+Táxi" alt="Typing SVG" /></a></div>

<p align="center">
  <a href="#english">English</a> | <strong>Português</strong>
</p>

<p align="center">
API REST desenvolvida com Spring Boot, Java e MySQL para um sistema de transporte por aplicativo.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/status-em%20desenvolvimento-yellow" alt="Status do Projeto">
</p>

## 🚕 Sobre o projeto
Este repositório contém o back-end (API) do sistema de transporte por aplicativo. Ele é responsável por toda a lógica de negócios e gerenciamento de dados, incluindo:
* Cadastro e autenticação de usuários (passageiros) e motoristas.
* Processamento de solicitações de viagens.
* Busca de motoristas disponíveis.
* Atualização do status da viagem (por exemplo, PENDENTE, ACEITA, CONCLUÍDA).

Este projeto foi desenvolvido para aplicar e aprofundar o conhecimento em desenvolvimento de APIs RESTful utilizando o ecossistema Spring.

## 🛠️ Tecnologias Utilizadas

O projeto foi desenvolvido com as seguintes tecnologias:

* **Linguagem:** Java 17
* **Framework:** Spring Boot 3
* **Acesso a Dados:** Spring Data JPA (Hibernate)
* **Banco de Dados:** MySQL
* **Gerenciador de Pacotes:** Maven
* **Ferramenta de Teste:** Postman

## 🚀 Como executar o projeto

Siga os passos abaixo para executar a aplicação localmente.

### 1. Pré-requisitos

* JDK 17 (ou superior) instalado.

* Servidor MySQL instalado e em execução.

* Maven instalado (ou utilize o wrapper do Maven - `mvnw`).

### 2. Clone o repositório
```bash
git clone https://github.com/YOUR-USERNAME/taxi-api.git
cd taxi-api
```

### 3. Configurar o Banco de Dados
1. Acesse seu terminal MySQL (ou MySQL Workbench).

2. Crie o banco de dados para o aplicativo:
```SQL
CREATE DATABASE taxi_system_db;                                        # Criar Banco de Dados
CREATE USER 'taxi_user'@'localhost' IDENTIFIED BY '123456';            # Criar um usuário para sua aplicação
GRANT ALL PRIVILEGES ON taxi_system_db.* TO 'taxi_user'@'localhost';   # Conceder permissões ao usuário
FLUSH PRIVILEGES;                                                      # Atualizar permissões
````

### 4. Executar a Aplicação
Você pode executar a aplicação de duas maneiras:

**Via Maven (recomendado):**
```Bash
./mvnw  spring-boot:run
```

**Via sua IDE (VS Code, IntelliJ)**
* Abra o projeto.
* Encontre a classe principal.
* Execute-a como uma aplicação Java.

A API estará disponível em `http://localhost:8080`.
## 📍 Endpoints da API (Planejados)

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/users` | Cadastra um novo usuário (passageiro) |
| `POST` | `/api/drivers` | Cadastra um novo motorista |
| `POST` | `/api/rides/request` | Cadastra uma nova corrida |
| `GET` | `/api/rides/{id}` | Obtém o status de uma viagem |
| `PUT` | `/api/rides/{id}/accept` | O motorista aceita a corrida |
| `PUT` | `/api/rides/{id}/complete` | O motorista completa uma corrida |

## 💻 Projetos Relacionados
O front-end (HTML, CSS e JavaScript) que consome esta API será desenvolvido em um repositório separado:
* O link estará aqui em breve.

<img width="100%" bottom=50px src ="https://capsule-render.vercel.app/api?type=waving&height=100&color=FF78CB&section=footer&reversal=false&descAlign=22&descAlignY=42"/>
