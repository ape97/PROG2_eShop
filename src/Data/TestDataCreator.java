package Data;

import Controller.MainController;

public class TestDataCreator {

    private MainController _mainController;

    public TestDataCreator(MainController mainController){
        _mainController = mainController;
    }

    public void CreateData(){
        createEmployees();
        createCustomers();
        createArticles();
        fillShoppingCarts();
        buyArticles();
        changeArticleStock();
    }

    private void createEmployees(){
        _mainController.addEmployee("", "", "", "");
        _mainController.addEmployee("", "", "", "");
        _mainController.addEmployee("", "", "", "");
        _mainController.addEmployee("", "", "", "");
        _mainController.addEmployee("", "", "", "");
        _mainController.addEmployee("", "", "", "");
        _mainController.addEmployee("", "", "", "");
        _mainController.addEmployee("", "", "", "");
    }

    private void createCustomers(){
        _mainController.addCustomer("", "", "", "", "", "", "", "");
        _mainController.addCustomer("", "", "", "", "", "", "", "");
        _mainController.addCustomer("", "", "", "", "", "", "", "");
        _mainController.addCustomer("", "", "", "", "", "", "", "");
    }

    private void createArticles(){
        _mainController.addArticle("", 1, 100, 50.99);
        _mainController.addArticle("", 1, 10, 50.99);
        _mainController.addArticle("", 1, 10, 50.99);
        _mainController.addArticle("", 1, 10, 50.99);
        _mainController.addArticle("", 1, 10, 50.99);
        _mainController.addArticle("", 1, 10, 50.99);
        _mainController.addArticle("", 1, 10, 50.99);
        _mainController.addArticle("", 1, 10, 50.99);
    }

    private void fillShoppingCarts(){
        _mainController.login("x", "");
        _mainController.addArticleToShoppingCart(1, 50);
        _mainController.addArticleToShoppingCart(1, 50);
        _mainController.addArticleToShoppingCart(1, 50);
        _mainController.addArticleToShoppingCart(1, 50);
        _mainController.logout();

        _mainController.login("y", "");
        _mainController.addArticleToShoppingCart(1, 50);
        _mainController.addArticleToShoppingCart(1, 50);
        _mainController.addArticleToShoppingCart(1, 50);
        _mainController.logout();

        _mainController.login("z", "");
        _mainController.addArticleToShoppingCart(1, 50);
        _mainController.addArticleToShoppingCart(1, 50);
        _mainController.addArticleToShoppingCart(1, 50);
        _mainController.logout();
    }

    private void buyArticles(){
        _mainController.login("x", "");
        _mainController.buyShoppingCart();
        _mainController.logout();

        _mainController.login("y", "");
        _mainController.buyShoppingCart();
        _mainController.logout();
    }

    private void changeArticleStock(){
        _mainController.login("", "");
        _mainController.updateStock(1, 50);
        _mainController.updateStock(1, 50);
        _mainController.updateStock(1, 50);
        _mainController.updateStock(1, 50);
        _mainController.logout();

        _mainController.login("", "");
        _mainController.updateStock(1, 50);
        _mainController.updateStock(1, 50);
        _mainController.updateStock(1, 50);
        _mainController.updateStock(1, 50);
        _mainController.logout();

        _mainController.login("", "");
        _mainController.updateStock(1, 50);
        _mainController.updateStock(1, 50);
        _mainController.updateStock(1, 50);
        _mainController.updateStock(1, 50);
        _mainController.logout();
    }
}
