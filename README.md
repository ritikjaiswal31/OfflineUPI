# OfflineUPI 📶
**Mesh-Based Secure Transaction Processing System**

---

## Project Overview

OfflineUPI is a distributed transaction processing system built using Spring Boot that demonstrates secure offline payment propagation through mesh-based communication, gossip protocol routing, and deferred settlement architecture.

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

OfflineUPI uses a distributed mesh-routing architecture where encrypted payment packets propagate across nearby relay nodes through gossip-based communication until a bridge node restores internet connectivity and forwards the packet to the backend for secure transaction settlement.

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
| Concepts | Distributed Systems, Gossip Protocol, Deferred Settlement, Idempotency |

---

## Core Components

| Component | Description |
|-----------|-------------|
| Mesh Simulator | Simulates offline mesh-based packet propagation |
| Relay Nodes | Forward encrypted payment packets across the mesh network |
| Bridge Node | Uploads packets to backend after internet restoration |
| Settlement Service | Performs atomic debit-credit transaction settlement |
| Hybrid Crypto Service | Handles RSA + AES-GCM encryption and decryption |
| Idempotency Service | Prevents duplicate transaction settlement |
| Dashboard UI | Visualizes mesh propagation and settlement flow |

---

## Application Flow

1. Sender creates an encrypted payment packet
2. Packet propagates across nearby relay nodes using gossip-based mesh routing
3. Bridge node regains internet connectivity
4. Bridge uploads packet to backend
5. Backend validates, decrypts and settles transaction

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

### Hybrid Encryption

- RSA-OAEP is used to securely exchange encryption keys
- AES-256-GCM encrypts payment payloads and provides tamper detection

### Replay Protection

- nonce generation
- timestamp validation

### Idempotency

- duplicate packet settlement prevention

## Engineering Challenges & Solutions

### 1. Untrusted Devices

OfflineUPI uses hybrid encryption with RSA-OAEP and AES-256-GCM to securely propagate encrypted payment packets across untrusted relay devices.

- RSA-OAEP securely encrypts the AES session key
- AES-256-GCM encrypts payment payloads and provides authenticated tamper detection
- Relay devices cannot read or modify transaction data

---

### 2. Duplicate Packet Handling

Multiple bridge nodes may upload the same payment packet concurrently after internet restoration.

To prevent duplicate transaction settlement:

- SHA-256 ciphertext hashing is used as the idempotency key
- Atomic duplicate detection is handled using `ConcurrentHashMap.putIfAbsent()`
- Duplicate packets are rejected before settlement processing

This ensures idempotent and duplicate-safe transaction handling during concurrent bridge uploads.

---

### 3. Replay Attack Prevention

Replay protection is implemented using:

- unique nonce generation for every transaction
- timestamp freshness validation
- backend expiry checks for stale packets

This prevents attackers from replaying previously captured encrypted payment packets.

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

### Initial Dashboard State

Shows:
- account balances
- mesh devices
- empty transaction history
- initial offline network state

<img width="1950" height="1313" alt="Initial Dashboard" src="https://github.com/user-attachments/assets/3dc36c24-a374-464a-a5c7-c2d98319a911" />

---

### Accounts

Fetches current account balances.

<img width="1919" height="1310" alt="image" src="https://github.com/user-attachments/assets/00a21cec-f633-466b-8557-d3e9d3bd8934" />

---

### Transactions

Fetches transaction ledger and settlement history.

<img width="1906" height="739" alt="image" src="https://github.com/user-attachments/assets/869959a6-9b49-4666-ac25-fbfd3231a289" />

---

### Mesh State

Displays mesh topology, relay nodes and bridge node state.

<img width="1911" height="1302" alt="image" src="https://github.com/user-attachments/assets/b96f1b86-b9d7-45af-941f-82d5986bb651" />

---

### Inject Payment Packet

Creates encrypted payment packet for offline propagation.

<img width="1909" height="897" alt="image" src="https://github.com/user-attachments/assets/e40db7e4-3c7d-4541-a379-43b4493dd210" />

<img width="1950" height="1327" alt="image" src="https://github.com/user-attachments/assets/e03472f1-8de0-4f3c-8b58-1772f0c4b221" />

---

### Gossip Protocol — Round 1

Demonstrates initial packet propagation through relay devices.

<img width="1903" height="734" alt="image" src="https://github.com/user-attachments/assets/43760e4e-3fa5-40ad-b412-4613a8cb4fd4" />

---

### Gossip Protocol — Round 2

Demonstrates duplicate-safe propagation and idempotent mesh behavior.

<img width="1913" height="742" alt="image" src="https://github.com/user-attachments/assets/8f41742b-d25d-4d68-b0e7-5a98f705f965" />

<img width="1902" height="784" alt="image" src="https://github.com/user-attachments/assets/d82ca519-7564-4e5e-9262-73a6ee409fff" />

---

### Bridge Upload

Bridge node uploads encrypted packet to backend for settlement.

<img width="1175" height="201" alt="image" src="https://github.com/user-attachments/assets/9782c709-f4e7-4b9e-b4a2-76a5f0762749" />

---

### Updated Account Balances

Shows updated balances after successful settlement.

<img width="745" height="486" alt="image" src="https://github.com/user-attachments/assets/b2363082-6c8e-4937-996d-69d1d508805c" />

---

### Transaction History

Displays final settled transaction stored in ledger.

<img width="1896" height="672" alt="image" src="https://github.com/user-attachments/assets/d680c58e-c2f5-4cc2-b924-f792a1f99cbd" />

<img width="1900" height="874" alt="image" src="https://github.com/user-attachments/assets/c098bd57-e2c1-4f07-b621-aa4d6d68081a" />

---

### Duplicate Prevention / Idempotency

Second bridge upload attempt safely ignored already-settled packets using idempotent transaction handling.

<img width="1913" height="530" alt="image" src="https://github.com/user-attachments/assets/7060a5d3-876f-4da1-9c76-dd670ca69533" />

```
