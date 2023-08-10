import java.io.*;
import java.net.*;
import java.util.*;

public class KeyValueStoreServer {

    // Datastore to store key-value pairs
    private Map<String, String> datastore;

    // Transaction management
    private Map<Long, Map<String, String>> transactions;
    private long currentTransactionId;

    // Flag for graceful shutdown
    private volatile boolean isShuttingDown = false;

    // Constructor to initialize data structures
    public KeyValueStoreServer() {
        datastore = new HashMap<>();
        transactions = new HashMap<>();
        currentTransactionId = 0;
    }

    // Method to start the server
    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            // Accept client connections until shutdown flag is set
            while (!isShuttingDown) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Create a thread for each client to handle requests concurrently
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Inner class to handle client connections
    private class ClientHandler implements Runnable {
        private Socket clientSocket;

        // Constructor to initialize the client socket
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String input;
                while ((input = reader.readLine()) != null) {
                    // Process the client's input command
                    String[] parts = input.split(" ");
                    String command = parts[0].toUpperCase();

                    String response = "";
                    switch (command) {
                        // Handle different commands and generate responses
                        case "START":
                            currentTransactionId++;
                            transactions.put(currentTransactionId, new HashMap<>(datastore));
                            response = "{\"status\":\"Ok\"}";
                            break;
                        case "GET":
                            response = getValue(parts[1]);
                            break;
                        case "PUT":
                            if (parts.length >= 3) {
                                response = putValue(parts[1], String.join(" ", Arrays.copyOfRange(parts, 2, parts.length)));
                            } else {
                                response = "{\"status\":\"Error\", \"mesg\":\"Invalid command format\"}";
                            }
                            break;
                        case "COMMIT":
                            if (currentTransactionId > 0) {
                                datastore.putAll(transactions.get(currentTransactionId));
                                transactions.remove(currentTransactionId);
                                currentTransactionId--;
                                response = "{\"status\":\"Ok\"}";
                            } else {
                                response = "{\"status\":\"Error\", \"mesg\":\"No active transaction\"}";
                            }
                            break;
                        case "ROLLBACK":
                            if (currentTransactionId > 0) {
                                datastore.clear();
                                datastore.putAll(transactions.get(currentTransactionId));
                                transactions.remove(currentTransactionId);
                                currentTransactionId--;
                                response = "{\"status\":\"Ok\"}";
                            } else {
                                response = "{\"status\":\"Error\", \"mesg\":\"No active transaction\"}";
                            }
                            break;
                    }

                    // Send the response back to the client
                    writer.println(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getValue(String key) {
        String value = datastore.get(key);
        if (value != null) {
            return "{\"status\":\"Ok\", \"result\":\"" + value + "\"}";
        } else {
            return "{\"status\":\"Error\", \"mesg\":\"Key not found\"}";
        }
    }

    private String putValue(String key, String value) {
        datastore.put(key, value);
        return "{\"status\":\"Ok\"}";
    }

    // Method to initiate a graceful shutdown
    public synchronized void shutdown() {
        isShuttingDown = true;
    }
}
