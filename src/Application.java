import Controller.MainController;
import Data.DataWriterReader;
import Utilities.Message;
import View.CUI.CUIController;

public class Application {
    public static void main(String[] args) {
        MainController mainController;

        DataWriterReader dataWriterReader = new DataWriterReader("data.bn");
        Object loadObject = dataWriterReader.load();

        if (loadObject == null) {
            mainController = new MainController();
        } else {
            mainController = (MainController) loadObject;
        }

        CUIController cuiController = new CUIController(mainController);
        cuiController.runMainMenu();

        dataWriterReader.save(mainController);
    }
}
