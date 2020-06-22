import Controller.MainController;
import Core.ServerController;
import Data.DataWriterReader;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException {
//        DataWriterReader dataWriterReader = new DataWriterReader("data.bn");
//        Object loadObject = dataWriterReader.load();
//
//        if (loadObject == null) {
//            TestDataCreator testDataCreator = new TestDataCreator(MainController.getInstance());
//            testDataCreator.createData();
//        } else {
//            MainController.setInstance((MainController) loadObject);
//            MainController.getInstance().InitAfterSerialization();
//        }

        ServerController serverController = new ServerController();
        serverController.start();

        while(!readInput().equals( "stop")){
            // Wartet bis der Stopp Befehl eingegebn wurde
        }
        serverController.stop();
        System.out.println("Server wird beendet!");
        //dataWriterReader.save(MainController.getInstance());
    }

    private static String readInput() {
        Scanner input = new Scanner(System.in);
        String in;
        try{
            in = input.nextLine();
        }catch(Exception stringerror){
            System.out.println("Eingabe ungültig! Bitte erneut versuchen.");
            in = readInput();
        }
        return in;
    }

    private static void RegisterClients(){

    }
}
