import Controller.MainController;
import Data.DataWriterReader;
import Data.TestDataCreator;
import View.GUI.MainFrame;
import javafx.application.Application;

public class Program {
    public static void main(String[] args) {

        DataWriterReader dataWriterReader = new DataWriterReader("data.bn");
        Object loadObject = dataWriterReader.load();

        if (loadObject == null) {
            TestDataCreator testDataCreator = new TestDataCreator(MainController.getInstance());
            testDataCreator.createData();
        } else {
            MainController.setInstance((MainController) loadObject);
        }

       /* CUIController cuiController = new CUIController(mainController);
        cuiController.runMainMenu();*/
        Application.launch(MainFrame.class, args);

        MainController.getInstance().logout();
        dataWriterReader.save(MainController.getInstance());
    }
}