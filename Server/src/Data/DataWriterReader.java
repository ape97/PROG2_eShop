package Data;

import Utilities.Result;

import java.io.*;

// Quelle: https://mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
public class DataWriterReader {
    private String _filename;

    public DataWriterReader(String filename) {
        _filename = filename;
    }

    public Result<Void> save(Object object) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(_filename));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(object);

            objectOutputStream.flush();
            objectOutputStream.close();

            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException ex) {
            return new Result<Void>(Result.State.FAILED, "Daten konnten nicht gespeichert werden. " + ex.getMessage(), null);
        } catch (IOException ex) {
            return new Result<Void>(Result.State.FAILED, "Daten konnten nicht gespeichert werden. " + ex.getMessage(), null);
        }

        return new Result<Void>(Result.State.SUCCESSFULL, "Daten wurden erfolgreich gespeichert.", null);
    }

    public Result<Object> load() {
        Object object = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(_filename));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            object = objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();

        } catch (FileNotFoundException ex) {
            return new Result<Object>(Result.State.FAILED, "Daten konnten nicht geladen werden. " + ex.getMessage(), null);
        } catch (IOException ex) {
            return new Result<Object>(Result.State.FAILED, "Daten konnten nicht geladen werden. " + ex.getMessage(), null);
        } catch (ClassNotFoundException ex) {
            return new Result<Object>(Result.State.FAILED, "Daten konnten nicht geladen werden. " + ex.getMessage(), null);
        }

        return new Result<Object>(Result.State.SUCCESSFULL, "Daten wurden erfolgreich geladen.", object);
    }
}