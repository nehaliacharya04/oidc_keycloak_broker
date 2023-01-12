# Keycloak as a Broker

This repository is a small POC to demonstrate the basic *username and password OIDC authentication* at the `Spring Authorization Server` with `Keycloak` acting as a broker.

* **Client** - An `Angular` app which calls a protected `REST` endpoint in the `Resource Server`. The app is registered as a *client* with `Keycloak`
* **Keycloak**
* **Spring Authorization Server** - a `Spring Boot` application registered in `Keycloak` as a custom identity provider (IDP)
* **Resource Server** - a `Spring Boot` application with a secure `REST` endpoint
---
### Basic Setup
* Download the latest version of Keycloak server (I am using Quarkus): https://www.keycloak.org/downloads
* Install `NodeJS` and `Angular CLI`
* Install `Maven` and `Java 17`
---
### Run the servers
* The `authorization` and `resource` servers can be run using `mvn spring-boot:run` from the command prompt.

  * **Spring Authorization Server**: http://localhost:9000
  * **Resource Server**: http://localhost:7070

* The `Angular` app can be run using `ng serve`. The app is available at http://localhost:4200
* **Keycloak**: Navigate to the `/bin` folder and open a terminal to run the server using `kc.bat start-dev`. The server runs at http://localhost:8080/
  
