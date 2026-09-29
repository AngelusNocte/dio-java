# 🔔 Notification API

Projeto em **Java 17 + Maven** que simula o envio de notificações por **e-mail, SMS e push**, criado como desafio final de **Padrões de Projeto (Design Patterns)** da DIO.

O foco é mostrar, de forma prática e justificada, como cada padrão resolve um problema real do código.

## 🎯 Objetivo

Enviar notificações a usuários por diferentes canais, validando os dados antes do envio e registrando o resultado como eventos — sem `if/else` gigantes e permitindo adicionar novos canais sem alterar o código existente.

## 🧱 Fluxo da aplicação

```
Builder  →  Chain of Responsibility  →  Factory  →  Strategy  →  Observer
(cria)      (valida)                    (escolhe)   (envia)      (registra)
```

## 🧩 Design Patterns utilizados

| Padrão | Onde | Por quê |
|---|---|---|
| **Builder** | `builder/NotificationBuilder` | Monta a `Notification` de forma fluente e legível |
| **Chain of Responsibility** | `chain/*` | Cada regra de validação é um elo independente e reordenável |
| **Factory** (simples) | `factory/NotificationStrategyFactory` | Centraliza a criação da estratégia de cada canal |
| **Strategy** | `strategy/*` | Cada canal encapsula sua forma de envio |
| **Observer** | `observer/*` | Logs e métricas reagem a eventos sem acoplar ao serviço |
| **Singleton** | `config/AppConfig` | Configuração única, thread-safe (holder idiom) |

> Nota: a `Factory` aqui é uma *Simple Factory* baseada em `switch` sobre um `enum`. Uma evolução possível é o *Factory Method* clássico, com uma subclasse criadora por canal.

## 📐 Princípios SOLID aplicados

- **SRP** – cada classe tem uma única responsabilidade (um validador = uma regra; uma strategy = um canal).
- **OCP** – novo canal = nova `Strategy` + uma linha na factory, sem alterar o `NotificationService`.
- **LSP** – qualquer `NotificationStrategy` ou `ValidationHandler` pode substituir outra.
- **ISP** – interfaces pequenas (`NotificationStrategy`, `NotificationListener`).
- **DIP** – `NotificationService` recebe a cadeia e o publisher por construtor.

## 📁 Estrutura

```
notification-api/
├── pom.xml
├── README.md
└── src
    ├── main/java/com/exemplo/notification
    │   ├── Main.java
    │   ├── builder/       NotificationBuilder
    │   ├── chain/         ValidationHandler, Recipient/Message/ChannelValidator
    │   ├── config/        AppConfig (Singleton)
    │   ├── factory/       NotificationStrategyFactory
    │   ├── notification/  Notification, User, Channel
    │   ├── observer/      EventPublisher, LogListener, MetricsListener...
    │   ├── service/       NotificationService
    │   └── strategy/      Email/Sms/PushStrategy
    └── test/java/...      testes JUnit 5
```

## ▶️ Como executar

Requisitos: **JDK 17+** e **Maven 3.8+**.

```bash
# testes
mvn test

# executar a demonstração
mvn compile exec:java
```

Sem Maven:

```bash
mkdir out
javac -encoding UTF-8 -d out $(find src/main -name "*.java")
java -Dfile.encoding=UTF-8 -cp out com.exemplo.notification.Main
```

## 📺 Exemplo de saída

```
=== Notification API ===

Usuário: João
Canal: EMAIL
Mensagem: Seu pedido foi enviado!
Validando notificação...
  ✓ Destinatário válido
  ✓ Mensagem válida
  ✓ Canal válido
  Enviando e-mail para joao@exemplo.com: Seu pedido foi enviado!
  ✓ Notificação enviada com sucesso!
  Evento registrado: NOTIFICATION_SENT
```

## 🚀 Melhorias futuras

- Expor como API REST com **Spring Boot** (`POST /notifications`)
- Persistir usuários e histórico com Spring Data JPA
- Integrar provedores reais (SendGrid, Twilio, Firebase)
- Adicionar os padrões **Decorator** (ex.: assinatura na mensagem) e **Template Method**
- Envio assíncrono com fila e *retry*

## 👤 Autor

william macedo — [GitHub](https://github.com/AngelusNocte) · 
