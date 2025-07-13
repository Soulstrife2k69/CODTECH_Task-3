import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            System.out.println(" Connected to chat server.");
            System.out.print("Enter your name: ");
            String name = keyboard.readLine();
            out.println(name + " joined the chat.");

            // Thread to read messages from server
            new Thread(() -> {
                String response;
                try {
                    while ((response = in.readLine()) != null) {
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    System.out.println("🔌 Connection closed.");
                }
            }).start();

            // Main thread reads user input and sends to server
            String message;
            while ((message = keyboard.readLine()) != null) {
                out.println(name + ": " + message);
            }

        } catch (IOException e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }
}
