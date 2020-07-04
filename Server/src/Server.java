import Communication.ServerController;
import java.io.IOException;
import java.util.Scanner;

/**
 * Der Einstiegpunkt der Server-Anwendung.
 */
public class Server {

    public static void main(String[] args) throws IOException {
        // Startet den ServerController, dieser startet den ClientRegisterProcessor
        ServerController serverController = new ServerController();
        serverController.start();

        // Beendet den Server erst, wenn in die Console "stop" eingegeben wurde.
        // WICHTIG: Ohne die Eingabe von "stop", werden die Daten NICHT gespeichert!
        //          Der Server muss stets korrekt beendet werden!
        System.out.println("Um die Daten zu SPEICHERN und den Server zu BEENDEN, bitte 'stop' eingeben!!!");
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
