import Controller.ShoppingCartController;
import Model.*;
import Utilities.PersonType;
import View.ConsoleTest;

public class Application {
    public static void main(String[] args){
        ConsoleTest console = new ConsoleTest();
        console.start();

        Article article = new Article("TestA", 12345, 50, 30);

        ShoppingCart shoppingCart = new ShoppingCart();
        ShoppingCartController shoppingCartController = new ShoppingCartController();
        shoppingCartController.addArticle(shoppingCart, article, 10);
        shoppingCartController.addArticle(shoppingCart, article, 50);


    }
}
