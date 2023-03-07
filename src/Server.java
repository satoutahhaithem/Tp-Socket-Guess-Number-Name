import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("Le serveur est en écoute sur le port 5000...");
        } catch (IOException e) {
            System.err.println("Impossible d'écouter sur le port 5000.");
            System.exit(1);
        }

        while (true) {
            Socket clientSocket = null;
            PrintWriter out = null;
            BufferedReader in = null;

            try {
                clientSocket = serverSocket.accept();
                System.out.println("Nouvelle connexion : " + clientSocket);
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                System.err.println("Erreur lors de l'acceptation de la connexion.");
                System.exit(1);
            }

            Calculator calculator = new Calculator();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                if ("FIN".equals(inputLine)) {
                    break;
                }

                try {
                    double result = calculator.calculate(inputLine);
                    out.println(result);
                } catch (IllegalArgumentException e) {
                    out.println(e.getMessage());
                }
            }

            out.close();
            in.close();
            clientSocket.close();
        }
    }
}
