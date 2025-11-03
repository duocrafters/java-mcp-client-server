# README — MCP Server and Client with Spring AI & Mistral

## 🧠 Overview

This repository provides a **minimal working example** of implementing the **Model Context Protocol (MCP)** using **Spring AI**.  
It demonstrates how to integrate an MCP **server** exposing tools over SSE/WebMVC and an MCP **client** orchestrating a **Mistral** LLM to call those tools dynamically.

**Goal:** Quickly understand, configure, test, and deploy a simple yet complete MCP setup using Spring Boot and Mistral.

## ⚙️ Prerequisites

Before running the project, make sure you have:

- **Java 25**
- **Maven 3.8+**

## 🚀 Build & Run

You’ll need to start the **MCP Server**, then the **MCP Client**, and finally the **frontend**.

Run the following commands for each module (`server`, `client`, `frontend`):

```bash
cd project-name
mvn clean install -DskipTests
mvn spring-boot:run
```

### Default Ports & Endpoints

| Component  | Default Port | Description |
|-------------|--------------|-------------|
| **MCP Server** | `8082` | Exposes MCP tools and SSE stream on `/mcp/sse` |
| **MCP Client** | `8080` | Orchestrates the LLM and exposes an HTTP endpoint for user queries |
| **Frontend** | `8081` | Provides a simple web UI to interact with the MCP setup |

## 🧩 Architecture Overview

```
Frontend (8081)
   │
   ▼
MCP Client (8080) ──> Mistral LLM
   │
   ▼
MCP Server (8082)
```

- **MCP Server**: Defines and exposes domain-specific tools (via Spring MVC + SSE).
- **MCP Client**: Connects to the server, processes user input, and delegates reasoning and tool usage to Mistral.
- **Frontend**: Offers a basic interface to send queries and visualize responses.

## 🧰 Useful Links

- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/)
- [Mistral AI](https://docs.mistral.ai/)
- [Model Context Protocol (MCP)](https://github.com/modelcontextprotocol)
