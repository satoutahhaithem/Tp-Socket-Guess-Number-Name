import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Welcome to the Number Guessing Game!");
            System.out.println("Guess a number between 1 and 100:");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = reader.readLine()) != null) {
                output.println(line);

                String response = input.readLine();
                System.out.println(response);

                if (response.startsWith("Congratulations!") || response.startsWith("Sorry")) {
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Client exception: " + ex.getMessage());
        }
    }
}
