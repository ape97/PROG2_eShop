package Data;

import Controller.MainController;

public class TestDataCreator {

    private MainController _mainController;

    public TestDataCreator(){
        _mainController = new MainController();
    }

    public void createData(){
        System.out.println("TEST DATEN GENERIEREN!");
        createEmployees();
        createCustomers();
        createArticles();
        fillShoppingCarts();
        buyArticles();
        changeArticleStock();
    }

    private void createEmployees(){
        _mainController.login("admin", "admin");
        _mainController.addEmployee("Luis", "Müller", "LMüller", "1Apfelkuchen!");
        _mainController.addEmployee("Anna", "Christ", "Christ2502", "IceTea25$");
        _mainController.addEmployee("Ben", "Tempo", "Ben10", "G&250899");
        _mainController.addEmployee("Hannah", "Lange", "Hannah1998", "1RosenRot!");
        _mainController.addEmployee("Leo", "Fischer", "FischerKing", "Pa$$w0rd!");
        _mainController.addEmployee("Thomas", "Licht", "Thomas1208", "Bruno$25");
        _mainController.addEmployee("Maximilian", "Haagen", "MaxiDerGroße", "Scr4b&bl3");
        _mainController.addEmployee("Laura", "Richter", "LauraR", "Heidi27!");
        _mainController.logout();
    }

    private void createCustomers(){
        _mainController.addCustomer("Justus", "Jonas", "ErsterDetektiv", "Catherine3???", "Am Bürgerpark", "25", "49088", "Osnabrück");
        _mainController.addCustomer("Peter", "Shaw", "ZweiterDetektiv", "Fußball%1998", "Lessingstraße", "8", "49661", "Cloppenburg");
        _mainController.addCustomer("Bob", "Andrews", "DritterDetektiv", "N4richt3n$", "Oldeoogestraße", "15", "26382", "Wilhelmshaven");
        _mainController.addCustomer("Sophie", "Meyer", "SMeyer", "Ostwind&1507", "Fichtenallee", "32", "49661", "Cloppenburg");
        _mainController.addCustomer("Emma", "Nüssel", "Emma1807", "$chokol4d3", "Dörnbergstraße", "5", "30161", "Hannover");
        _mainController.addCustomer("Chiara", "Schneider", "Schneider88", "Alex1997!", "Thedinghauser Straße", "10", "28201", "Bremen");
        _mainController.addCustomer("Alina", "Wagner", "Alina1999", "30031999$", "Münchener Straße", "109", "28215", "Bremen");
    }

    private void createArticles(){
        _mainController.login("admin","admin");
        _mainController.addArticle("Teelicht",  100, 0.99, 1);
        _mainController.addArticle("Kerze: Vanille",  25, 3.99, 1);
        _mainController.addArticle("Kerze: Pfirsich",  30, 3.99, 1);
        _mainController.addArticle("Kerze: Lavendel",  20, 5.99, 1);
        _mainController.addArticle("Räucherstäbchen: Jasmin",  15, 9.99, 1);
        _mainController.addArticle("Räucherstäbchen:Orchideen",  10, 9.99, 1);
        _mainController.addArticle("Räucherstäbchen: Rose",  20, 9.99, 1);
        _mainController.addArticle("Teelichtglass: Rot",  30, 4.99, 1);
        _mainController.addArticle("Teelichtglass: Blau",  20, 4.99, 1);
        _mainController.logout();
    }

    private void fillShoppingCarts(){
        _mainController.login("ErsterDetektiv", "Catherine3???");
        _mainController.addArticleToShoppingCart(1, 5);
        _mainController.addArticleToShoppingCart(2, 1);
        _mainController.addArticleToShoppingCart(4, 2);
        _mainController.addArticleToShoppingCart(8, 3);
        _mainController.addArticleToShoppingCart(9, 2);
        _mainController.logout();

        _mainController.login("Schneider88", "Alex1997!");
        _mainController.addArticleToShoppingCart(6, 4);
        _mainController.addArticleToShoppingCart(7, 1);
        _mainController.addArticleToShoppingCart(3, 2);
        _mainController.logout();

        _mainController.login("SMeyer", "Ostwind&1507");
        _mainController.addArticleToShoppingCart(8, 10);
        _mainController.addArticleToShoppingCart(9, 10);
        _mainController.logout();
    }

    private void buyArticles(){
        _mainController.login("SMeyer", "Ostwind&1507");
        _mainController.buyShoppingCart();
        _mainController.logout();

        _mainController.login("ErsterDetektiv", "Catherine3???");
        _mainController.buyShoppingCart();
        _mainController.logout();
    }

    private void changeArticleStock(){
        _mainController.login("FischerKing", "Pa$$w0rd!");
        _mainController.updateStock(1, 10);
        _mainController.updateStock(5, 20);
        _mainController.updateStock(7, 5);
        _mainController.updateStock(9, 8);
        _mainController.logout();

        _mainController.login("Christ2502", "IceTea25$");
        _mainController.updateStock(2, 10);
        _mainController.updateStock(3, 20);
        _mainController.updateStock(6, 10);
        _mainController.logout();

        _mainController.login("Thomas1208", "Bruno$25");
        _mainController.updateStock(4, 30);
        _mainController.updateStock(8, 20);
        _mainController.updateStock(9, 10);
        _mainController.logout();
    }
}
