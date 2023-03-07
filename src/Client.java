import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        String serverHostname = "localhost";

        if (args.length > 0) {
            serverHostname = args[0];
        }

        System.out.println("Connexion au serveur " + serverHostname + " sur le port 5000.");

        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            socket = new Socket(serverHostname, 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Hôte inconnu : " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Connexion refusée : " + serverHostname);
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);
        String userInput;

        while (true) {
            System.out.print("Entrez un nombre ou un opérateur (ou 'FIN' pour terminer) : ");
            userInput = scanner.nextLine();

            out.println(userInput);

            if ("FIN".equals(userInput)) {
                break;
            }

            String response = in.readLine();
            System.out.println("Réponse du serveur : " + response);
        }

        out.close();
        in.close();
        socket.close();
    }
}
