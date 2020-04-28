import java.io.IOException;
import java.util.Scanner;
public class CUIController {


    public CUIController(){
    }

    public static void showMenu(){
        System.out.println("Test Menü: ");
        System.out.println("Test starten: 'a'");
        System.out.println("Test-Beenden: 'q'");
        System.out.flush();
    }




    public static void runMenu(){

            showMenu();
            String inputdata = "main_"+readInput();
            processinput(inputdata);

    }

    public static String readInput(){
        Scanner input = new Scanner(System.in);
        String in = input.nextLine();
        return in;
    }

    public static void processinput(String inputdata){
        System.out.println("Eingabe: "+inputdata);
        switch(inputdata){
            case "main_q":
                System.exit(0);
            case "main_a":
                submenu();
                break;

            case "sub_a":
                System.out.println("Test erfolgreich!");
                submenu();
                break;
            case "sub_q":
                runMenu();
                break;

        }
    }


    public static void submenu(){
        System.out.println("Drücke die 'a' um ein Test zu starten!");
        System.out.println("Drücke 'q' um Menü zu verlassen.");
        String inputdata = "sub_"+readInput();
        processinput(inputdata);



    }
}
