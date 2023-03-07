import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {

    private Socket clientSocket;
    private int numberToGuess;
    private ArrayList<ClientHandler> clients;
    private boolean isConnected;

    public ClientHandler(Socket socket, int numberToGuess, ArrayList<ClientHandler> clients) {
        this.clientSocket = socket;
        this.numberToGuess = numberToGuess;
        this.clients = clients;
        this.isConnected = true;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            while (isConnected) {
                String guess = input.readLine();

                if (guess != null) {
                    int number = Integer.parseInt(guess);
                    if (number == numberToGuess) {
                        output.println("Congratulations! You guessed the number.");
                        notifyClients("Client " + clientSocket + " guessed the number.");
                        break;
                    } else if (number < numberToGuess) {
                        output.println("Too low. Guess again:");
                    } else {
                        output.println("Too high. Guess again:");
                    }
                } else {
                    isConnected = false;
                }
            }

            input.close();
            output.close();
            clientSocket.close();
        } catch (IOException ex) {
            System.out.println("ClientHandler exception: " + ex.getMessage());
        }
    }

    private void notifyClients(String message) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != this) {
                    client.send(message);
                }
            }
        }
    }

    private void send(String message) {
        try {
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            output.println(message);
        } catch (IOException ex) {
            System.out.println("Send exception: " + ex.getMessage());
        }
    }
}
