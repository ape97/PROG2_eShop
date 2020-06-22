package Controller;

import Data.DataWriterReader;

import Model.Article;
import Model.Event;
import Model.Person;
import Utilities.Result;

import java.io.Serializable;
import java.util.ArrayList;

public class DataController implements Serializable {

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

    public static Result<Void> loadData() {
        DataWriterReader dataWriterReader = new DataWriterReader("data.bn");

        Result<Object> loadDataResult = dataWriterReader.load();
        if (loadDataResult.getState() == Result.State.FAILED) {
            return new Result<Void>(Result.State.FAILED, loadDataResult.getMessage(), null);
        }

        _instance = (DataController) loadDataResult.getObject();
        return new Result<Void>(Result.State.SUCCESSFULL, loadDataResult.getMessage(), null);
    }

    public Result<Void> saveData() {
        DataWriterReader dataWriterReader = new DataWriterReader("data.bn");
        Result<Void> saveDataResult = dataWriterReader.save(_instance);
        return saveDataResult;
    }

    public ArrayList<Article> getArticleList() {
        return _articleList;
    }

    public ArrayList<Event> getEventList() {
        return _eventList;
    }

    public ArrayList<Person> getPersonList() {
        return _personList;
    }
}
