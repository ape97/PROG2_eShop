package Controller;

import Data.DataWriterReader;

import Model.Article;
import Model.Event;
import Model.Person;

import java.util.ArrayList;

public class DataController {

    private static DataController _instance;

    private ArrayList<Article> _articleList;
    private ArrayList<Event> _eventList;
    private ArrayList<Person> _personList;


    private DataController() {
        _articleList = new ArrayList<>();
        _eventList = new ArrayList<>();
        _personList = new ArrayList<>();
    }

    public static DataController getInstance() {
        if (_instance == null) {
            _instance = new DataController();
        }

        return _instance;
    }

    public static void loadData(){
        DataWriterReader dataWriterReader = new DataWriterReader("data.bn");
        Object loadObject = dataWriterReader.load();

        if (loadObject == null) {
            _instance = new DataController();
        } else {
            _instance = ((DataController) loadObject);
        }
    }

    public ArrayList<Article> getArticleList(){
        return _articleList;
    }

    public  ArrayList<Event> getEventList(){
        return _eventList;
    }

    public ArrayList<Person> getPersonList(){
        return  _personList;
    }
}
