# Multi-Client Key-Value Store Project

This project implements a multi-client key-value store server with support for transactions and a graceful shutdown mechanism. Clients can connect to the server and manage key-value pairs using commands such as `GET`, `PUT`, and transaction control commands like `START`, `COMMIT`, and `ROLLBACK`.

## Features

- **Multi-Client Support:** The server can handle multiple clients concurrently, allowing them to interact with the key-value store simultaneously.

- **Transaction Management:** Clients can start, commit, and rollback transactions to ensure data consistency and atomic operations.

- **Graceful Shutdown:** The server can be gracefully shut down, ensuring pending transactions are completed before the server stops.

## Getting Started
 Clone the repository using this cpmmand: git clone https://github.com/Nitika2910/Assignment.git

### Prerequisites

- Java Development Kit (JDK) installed on your machine.
- Terminal or Command Prompt for running commands.

### Installation
## How to Run

1. **Compile the Code:**
   Open a terminal and navigate to the directory containing `KeyValueStoreServer.java`. Compile the code using the following command:

   javac KeyValueStoreServer.java Main.java

2. Run the Server:
   After compiling, start the server by running the following command: java Main
   The server will start listening for incoming connections on the specified port (default: 8080).
   
3. Connect to the Server:
   Open another terminal/command prompt to interact with the server using a plain socket client. You can use Telnet or netcat for this purpose.

   Using Telnet: telnet localhost 8080
4. Send Commands:
   After connecting to the server using the socket client, you can send the following commands:

   START: Start a new transaction. Response will be {"status":"Ok"}
   PUT [key][value]: Add or update a key/value pair. Response will be {"status":"Ok"}
   GET [key]: Retrieve the latest value for a key. Response will be {"status":"Ok", result:"value"}
   COMMIT: Commit the current transaction. Response will be {"status":"Ok"}
   Press Enter after typing each command.

