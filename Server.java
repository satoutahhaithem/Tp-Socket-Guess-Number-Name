import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Server {

    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static int numberToGuess;

    public static void main(String[] args) {
        Random rand = new Random();
        numberToGuess = rand.nextInt(100) + 1;
        System.out.println("Number to guess: " + numberToGuess);

        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started. Listening on port 5000...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket, numberToGuess, clients);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
        }
    }
}
