import Controller.MainController;
import Data.DataWriterReader;
import Utilities.Message;

public class Application {
    public static void main(String[] args) {
        DataWriterReader dataWriterReader = new DataWriterReader("data.bn");
        if (object == null) {
            mainController = new MainController();
        } else {
            mainController = (MainController) object;
        }

        // TODO: New Console, der den MainController geben
        // TODO: Programm starten

        dataWriterReader.save(mainController);
        Object object = dataWriterReader.load();
        MainController mainController;

    }
}
