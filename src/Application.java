import Controller.MainController;
import Data.DataWriterReader;
import Data.TestDataCreator;
import Utilities.Result;
import View.CUI.CUIController;

public class Application {
    public static void main(String[] args) {
        MainController mainController;

        DataWriterReader dataWriterReader = new DataWriterReader("data.bn");
        Object loadObject = dataWriterReader.load();

        if (loadObject == null) {
            mainController = new MainController();
            TestDataCreator testDataCreator = new TestDataCreator(mainController);
            testDataCreator.createData();
        } else {
            mainController = (MainController) loadObject;
        }

       /* CUIController cuiController = new CUIController(mainController);
        cuiController.runMainMenu();*/
        // TODO: GUI HIER STARTEN

        mainController.logout();
        dataWriterReader.save(mainController);
    }
}
