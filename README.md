# OfflineUPI 📶💳
**Offline Mesh-Based Secure Payment Settlement System**

---

## Project Overview

OfflineUPI is a distributed payment simulation system built using Spring Boot that demonstrates secure offline payment propagation through mesh-based relay devices using gossip protocol and deferred settlement architecture.

---

## Key Features

- Offline Mesh-Based Payment Propagation
- Gossip Protocol Simulation
- Hybrid Encryption (RSA + AES-GCM)
- Idempotent Transaction Settlement
- Replay Attack Prevention
- Concurrent Bridge Upload Handling
- Deferred Settlement Architecture
- Interactive Mesh Network Dashboard

---

## Architecture

OfflineUPI uses a mesh-routing architecture where encrypted payment packets propagate across nearby relay devices until a bridge node regains internet connectivity and uploads the packet to backend for secure transaction settlement.

---

## Tech Stack

| Category | Technologies |
|----------|-------------|
| Language | Java |
| Framework | Spring Boot |
| Database | H2 Database |
| Security | RSA-OAEP, AES-256-GCM |
| Build Tool | Maven |
| API Testing | Postman |
| Concepts | Distributed Systems, Gossip Protocol, Deferred Settlement |

---

## Core Components

| Component | Description |
|-----------|-------------|
| Mesh Simulator | Simulates mesh propagation |
| Relay Nodes | Forward encrypted packets |
| Bridge Node | Uploads packets when internet returns |
| Settlement Service | Handles atomic settlement |
| Hybrid Crypto Service | Encryption & decryption |
| Idempotency Service | Prevents duplicate settlement |
| Dashboard UI | Visualizes packet propagation |

---

## Application Flow

1. Sender creates encrypted payment packet
2. Packet propagates across relay devices
3. Bridge node regains internet
4. Backend validates & settles transaction

---

## Working Flow

```text
phone-node-1
   ↓
phone-relay-1
   ↓
phone-relay-2
   ↓
phone-relay-3
   ↓
phone-bridge
````

1. Sender creates encrypted payment packet
2. Relay devices forward encrypted packet
3. Bridge uploads packet to backend
4. Backend decrypts and settles transaction

---

## Security Features

* RSA-OAEP + AES-256-GCM Hybrid Encryption
* Tamper Detection using AES-GCM
* Replay Protection using nonce & timestamp validation
* Idempotent Transaction Settlement

---

## Distributed Systems Concepts

* Mesh Networking
* Gossip Protocol
* Deferred Settlement
* Eventual Delivery
* Concurrent Upload Handling
* Relay Nodes & Bridge Nodes
* Packet Propagation

---

## Deferred Settlement

```text
Payment Request
      ↓
Offline Packet Propagation
      ↓
Bridge Gets Internet
      ↓
Backend Settlement
```

Settlement occurs only after internet connectivity becomes available.

---

## Screenshots

### Application Dashboard

![Dashboard](screenshots/dashboard.png)

### Payment Packet Injection

![Inject Payment](screenshots/inject-payment.png)

### Gossip Propagation

![Gossip Round](screenshots/gossip-round.png)

### Bridge Upload & Settlement

![Bridge Upload](screenshots/bridge-upload.png)

### Successful Transaction Settlement

![Settlement Success](screenshots/settlement-success.png)

### Duplicate Prevention / Idempotency

![Duplicate Prevention](screenshots/idempotency.png)

### Postman API Testing

![Postman Testing](screenshots/postman-testing.png)

---

## API Testing Screenshots

### Inject Payment Packet API

![Inject API](screenshots/api-inject.png)

### Gossip Protocol API

![Gossip API](screenshots/api-gossip.png)

### Bridge Upload API

![Bridge API](screenshots/api-bridge.png)

### Transaction History API

![Transaction API](screenshots/api-transactions.png)

### Mesh State API

![Mesh State API](screenshots/api-mesh-state.png)
