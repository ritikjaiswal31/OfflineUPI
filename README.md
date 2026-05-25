# README.md

````md
# OfflineUPI

<h3 align="center">
Mesh-Routed Deferred Payment Settlement System
</h3>

<p align="center">
OfflineUPI is a distributed payment simulation system built using Spring Boot that demonstrates how encrypted payment instructions can propagate through nearby devices in a no-internet environment using a mesh-style gossip protocol.
</p>

<p align="center">
Instead of performing instant online settlement, the system allows encrypted payment packets to travel offline through relay devices until any bridge node regains internet connectivity and uploads the packet to the backend for secure settlement.
</p>

---

# Tech Stack

- Java
- Spring Boot
- Distributed Systems
- Cryptography
- H2 Database
- Maven
- Postman

---

# Project Overview

Traditional UPI systems require active internet connectivity for transaction processing. In environments such as underground metro stations, tunnels, disaster zones, or remote low-connectivity areas, digital payments fail because banking servers become unreachable.

OfflineUPI simulates an alternative architecture where:

- payment instructions are securely encrypted
- packets propagate through nearby devices
- relay devices forward packets without reading them
- bridge nodes upload packets once internet connectivity becomes available
- backend validates and settles transactions securely

This project demonstrates:

- Mesh Propagation
- Gossip Protocol
- Hybrid Encryption
- Idempotent Transaction Processing
- Deferred Settlement
- Replay Protection
- Concurrent Upload Handling

---

# Real-World Use Case

## Underground Metro Station During Network Failure

Imagine:

- passengers are inside an underground metro station
- there is no 4G or 5G connectivity
- UPI applications stop working
- users still need to recharge metro cards or make payments

OfflineUPI allows encrypted payment instructions to travel device-to-device through nearby commuters until one device regains internet connectivity and uploads the transaction for settlement.

---

# Core Idea

The project does NOT perform instant offline money transfer.

Instead:

1. Sender creates an encrypted payment instruction packet.
2. Packet propagates through nearby relay devices using gossip-based mesh routing.
3. A bridge node eventually regains internet connectivity.
4. Backend receives packet and performs final settlement.

This is called:

# Deferred Settlement

Meaning:

- payment request happens offline
- actual account settlement happens later when internet becomes available

---

# Architecture

```text
Sender Device
      ↓
Encrypted Payment Packet
      ↓
Relay Nodes (offline propagation)
      ↓
Bridge Node (internet restored)
      ↓
Spring Boot Backend
      ↓
Settlement Engine
      ↓
Transaction Ledger
````

---

# Working Flow

## Step 1 — Create Payment Packet

Sender creates a payment request containing:

* sender
* receiver
* amount
* timestamp
* nonce

The payload is encrypted using:

* RSA-OAEP
* AES-256-GCM

---

## Step 2 — Mesh Propagation

Encrypted packet propagates through nearby relay devices using a gossip protocol.

Example:

```text
phone-node-1
   ↓
phone-relay-1
   ↓
phone-relay-2
   ↓
phone-bridge
```

Relay devices:

* cannot decrypt packet
* cannot modify payment
* only forward encrypted data

---

## Step 3 — Bridge Upload

When a bridge node regains internet connectivity, it uploads packet to backend.

---

## Step 4 — Backend Settlement

Backend performs:

* SHA-256 packet hashing
* idempotency check
* packet decryption
* freshness validation
* transaction settlement
* ledger recording

---

# Security Features

## Hybrid Encryption

The project uses:

* RSA-OAEP for AES key encryption
* AES-256-GCM for payload encryption

---

## Tamper Detection

AES-GCM ensures packet integrity.

If even one bit changes in ciphertext:

* decryption fails
* packet becomes invalid
* settlement is rejected

---

## Replay Protection

Replay attacks are prevented using:

* nonce generation
* timestamp validation

Old packets are rejected automatically.

---

## Idempotency

Duplicate packet settlements are prevented using:

```java
ConcurrentHashMap.putIfAbsent()
```

Equivalent production concept:

```text
Redis SETNX
```

This ensures:

* same packet settles only once
* duplicate uploads are dropped safely

---

# Distributed Systems Concepts Demonstrated

* Mesh Networking
* Gossip Protocol
* Deferred Settlement
* Eventual Delivery
* Concurrent Upload Handling
* Relay Nodes
* Bridge Nodes
* Idempotency
* Packet Propagation

---

# Database

The project uses H2 in-memory database for:

* account balances
* transaction history
* settlement records

Main entities:

* Account
* Transaction

---

# Why Relay Nodes Exist

Relay nodes help transport encrypted payment instructions through offline environments.

Without relay nodes:

* packets would remain stuck
* backend could never be reached

Relay devices only act as:

* message carriers
* offline forwarding nodes

They cannot:

* read packets
* modify transactions
* access balances

---

# Why Bridge Node Exists

Bridge node acts as:

* internet gateway
* upload device

When bridge regains internet:

* packet reaches backend
* backend decrypts payment
* transaction settles successfully

---

# Gossip Protocol

A gossip protocol spreads information across nearby devices by repeatedly forwarding packets.

Example:

```text
phone-node-1
   ↓
phone-relay-1
   ↓
phone-relay-2
   ↓
phone-bridge
```

This allows payment packets to eventually reach internet connectivity without centralized communication.

---

# Encrypted Payment Packet

The packet contains:

* sender
* receiver
* amount
* timestamp
* nonce

Before transmission:

```text
Readable Payment Data
        ↓
Encrypted Ciphertext
```

Relay devices cannot:

* read payment data
* modify payment
* tamper with transaction

Only backend can decrypt packet using private key.

---

# Why This Project Is Technically Strong

This project is not a basic CRUD application.

It demonstrates:

| Concept                | Usage                        |
| ---------------------- | ---------------------------- |
| Distributed Systems    | mesh propagation             |
| Gossip Protocol        | packet forwarding            |
| Cryptography           | RSA + AES-GCM                |
| Concurrency            | simultaneous bridge uploads  |
| Idempotency            | duplicate prevention         |
| Replay Protection      | nonce + timestamp            |
| Transaction Management | atomic settlement            |
| Optimistic Locking     | @Version                     |
| Deferred Settlement    | offline-to-online transition |
| Mesh Simulation        | virtual devices              |

---

# Real Meaning of Deferred Settlement

OfflineUPI does NOT transfer money instantly offline.

Instead:

```text
Payment Request Now
        ↓
Packet Travels Offline
        ↓
Bridge Gets Internet
        ↓
Backend Settles Later
```

This is called:

# Deferred Settlement

Meaning:

* settlement happens later when connectivity becomes available

---

# Screenshots

## Application Dashboard

```md
![Dashboard](screenshots/dashboard.png)
```

---

## Payment Packet Injection

```md
![Inject Payment](screenshots/inject-payment.png)
```

---

## Gossip Propagation

```md
![Gossip Round](screenshots/gossip-round.png)
```

---

## Bridge Upload & Settlement

```md
![Bridge Upload](screenshots/bridge-upload.png)
```

---

## Successful Transaction Settlement

```md
![Settlement Success](screenshots/settlement-success.png)
```

---

## Duplicate Prevention / Idempotency

```md
![Duplicate Prevention](screenshots/idempotency.png)
```

---

## Postman API Testing

```md
![Postman Testing](screenshots/postman-testing.png)
```

---

# API Testing Screenshots

## Inject Payment Packet API

```md
![Inject API](screenshots/api-inject.png)
```

---

## Gossip Protocol API

```md
![Gossip API](screenshots/api-gossip.png)
```

---

## Bridge Upload API

```md
![Bridge API](screenshots/api-bridge.png)
```

---

## Transaction History API

```md
![Transaction API](screenshots/api-transactions.png)
```

---

## Mesh State API

```md
![Mesh State API](screenshots/api-mesh-state.png)
```

---

# Key Learning Outcomes

This project demonstrates:

* Distributed Systems
* Cryptography
* Transaction Management
* Concurrent Processing
* Idempotency
* Secure Packet Propagation
* Mesh Network Simulation
* Spring Boot Backend Engineering

---

# Final Project Understanding

OfflineUPI is not a traditional payment application.

It is a:

# Distributed Offline Transaction Propagation Simulator

demonstrating:

* secure mesh-routed packet propagation
* encrypted deferred settlement
* idempotent transaction handling
* backend concurrency management
* distributed systems concepts

---

# Resume Description

Built a Spring Boot based distributed payment simulation system implementing offline mesh-routed deferred settlement using hybrid cryptography, idempotent transaction processing, gossip-based packet propagation, and concurrent bridge-node settlement handling.

```
```
