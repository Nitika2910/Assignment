public class Main {
    public static void main(String[] args) {
        KeyValueStoreServer server = new KeyValueStoreServer();
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
        server.start(8080); // Change the port as needed
    }
}