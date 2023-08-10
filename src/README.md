# In-Memory Key/Value Datastore Server with Transactions

This is a Java-based in-memory key/value datastore server that supports transactions. It allows you to interact with the server over plain socket connections to perform operations like adding/updating key/value pairs, retrieving values by key, and managing transactions.

## How to Run

1. **Compile the Code:**
   Open a terminal and navigate to the directory containing `KeyValueStoreServer.java`. Compile the code using the following command:

   javac KeyValueStoreServer.java Main.java

2. Run the Server:
   After compiling, start the server by running the following command:

   java Main
   The server will start listening for incoming connections on the specified port (default: 8080).
   
3. Connect to the Server:
   Open another terminal/command prompt to interact with the server using a plain socket client. You can use Telnet or netcat for this purpose.

   Using Telnet: telnet localhost 8080
4. Send Commands:
   After connecting to the server using the socket client, you can send the following commands:

   START: Start a new transaction.
   PUT [key][value]: Add or update a key/value pair.
   GET [key]: Retrieve the latest value for a key.
   COMMIT: Commit the current transaction.
   ROLLBACK: Rollback the current transaction.
   Press Enter after typing each command.

It also supports multiple clients connection.
   

