import Communication.ServerController;
import Controller.DataController;


import java.io.IOException;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerController serverController = new ServerController();
        serverController.start();

        while (!readConsoleInput().equals("stop")) {
            // Wartet bis der Stopp Befehl eingegebn wurde
        }

        serverController.stop();
    }

    private static String readConsoleInput() {
        Scanner input = new Scanner(System.in);
        String in;
        try {
            in = input.nextLine();
        } catch (Exception ex) {
            System.out.println("Eingabe ungültig, bitte erneut versuchen. " + ex.getMessage());
            in = readConsoleInput();
        }
        return in;
    }
}
