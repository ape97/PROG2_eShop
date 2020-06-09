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

       // if (loadObject == null) {
            mainController = new MainController();
           // TestDataCreator testDataCreator = new TestDataCreator(mainController);
            //testDataCreator.createData();
        //} else {
        //    mainController = (MainController) loadObject;
        //}

        CUIController cuiController = new CUIController(mainController);
        cuiController.runMainMenu();

        mainController.logout();

        dataWriterReader.save(mainController);




        Result<String> result1 = new Result<String>(Result.State.SUCCESSFULL, "Erstellen war erfolgreich.", "Adrian");

        String s1 = result1.getObject();

        if(result1.getState() == Result.State.SUCCESSFULL){
            // ERfolgreich
        } else{
            // Nicht erfolgreich
        }


        Result<Void> result2 = new Result<Void>(Result.State.FAILED, "Hat nicht gelappt.", null);

        Void s2 = result2.getObject();









       // Result<String> testResult = new Result<>(Result.State.SUCCESSFULL, "TEST", "");
        // Result<Void> testResult2 = new Result<Void>(Result.State.SUCCESSFULL, "TEST", null);
    }
}
