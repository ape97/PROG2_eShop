package Data;

import Communication.ClientController;
import Model.*;
import Utilities.Result;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sun.util.resources.cldr.chr.CalendarData_chr_US;

import java.util.ArrayList;

public class DataCache {

    private ObservableList<Article> _articleList;
    private ObservableList<Customer> _customerList;
    private ObservableList<Employee> _employeeList;
    private ObservableList<Event> _eventList;
    private  ObservableList<ShoppingCartItem> _shoppingCartItemList;

    private DataCache() {
        _articleList = FXCollections.observableArrayList(new ArrayList<>());
        _customerList = FXCollections.observableList(new ArrayList<>());
        _employeeList = FXCollections.observableList(new ArrayList<>());
        _eventList = FXCollections.observableList(new ArrayList<>());
        _shoppingCartItemList = FXCollections.observableList(new ArrayList<>());
    }

    private static  DataCache _instance;
    public static DataCache getInstance(){
        if(_instance == null){
            _instance = new DataCache();
        }
        return _instance;
    }

    public ObservableList<Article> getArticleList(){
        return _articleList;
    }

    public ObservableList<Customer> getCustomerList(){
        return _customerList;
    }

    public ObservableList<Employee> getEmployeeList(){
        return _employeeList;
    }

    public ObservableList<ShoppingCartItem> getShoppingCartItemList(){
        return _shoppingCartItemList;
    }

    public ObservableList<Event> getEventList(){
        return _eventList;
    }

    public Result<Void> refreshArticleList(){
        Result<ArrayList<Article>> result = ClientController.getInstance().getArticleList();
        if(result.getState() == Result.State.FAILED){
            return new Result<Void>(result.getState(), result.getMessage(), null);
        } else{
            _articleList.clear();
            for (Article article : result.getObject()){
                _articleList.add(article);
                System.out.print(article.getArticleNumber());
            }
            return new Result<>(result.getState(), result.getMessage(), null);

        }
    }

    public Result<Void> refreshEmployeeList(){
        Result<ArrayList<Employee>> result = ClientController.getInstance().getEmployeeList();
        if(result.getState() == Result.State.FAILED){
            return new Result<Void>(result.getState(), result.getMessage(), null);
        } else{
            _employeeList.clear();
            for (Employee employee : result.getObject()){
                _employeeList.add(employee);
            }
            return new Result<>(result.getState(), result.getMessage(), null);

        }
    }

    public Result<Void> refreshCustomerList(){
        Result<ArrayList<Customer>> result = ClientController.getInstance().getCustomerList();
        if(result.getState() == Result.State.FAILED){
            return new Result<Void>(result.getState(), result.getMessage(), null);
        } else{
            _customerList.clear();
            for (Customer customer : result.getObject()){
                _customerList.add(customer);
            }
            return new Result<>(result.getState(), result.getMessage(), null);

        }
    }

    public Result<Void> refreshEventList(){
        Result<ArrayList<Event>> result = ClientController.getInstance().getEventList();
        if(result.getState() == Result.State.FAILED){
            return new Result<Void>(result.getState(), result.getMessage(), null);
        } else{
            _eventList.clear();
            for (Event event : result.getObject()){
                _eventList.add(event);
            }
            return new Result<>(result.getState(), result.getMessage(), null);

        }
    }

    public Result<Void> refreshShoppingCartItemList(){
        Result<ArrayList<ShoppingCartItem>> result = ClientController.getInstance().getShoppingCartItemList();
        if(result.getState() == Result.State.FAILED){
            return new Result<Void>(result.getState(), result.getMessage(), null);
        } else{
            _shoppingCartItemList.clear();
            for (ShoppingCartItem shoppingCartItem : result.getObject()){
                _shoppingCartItemList.add(shoppingCartItem);
            }
            return new Result<>(result.getState(), result.getMessage(), null);
        }
    }
}