import Controller.MainController;
import Data.DataWriterReader;

public class Application {
    public static void main(String[] args) {
        DataWriterReader dataWriterReader = new DataWriterReader("data.bn");
        MainController mainController = (MainController) dataWriterReader.load();

        if (mainController == null) {
            mainController = new MainController();
        }

        // TODO: New Console, der den MainController geben
        // TODO: Programm starten

        dataWriterReader.save(mainController);
    }
}
