package Data;

import java.io.*;

// Quelle: https://mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
public class DataWriterReader {
    private String _filename;

    public DataWriterReader(String filename) {
        _filename = filename;
    }

    public void save(Object object) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(_filename));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(object);

            objectOutputStream.flush();
            objectOutputStream.close();

            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException ex) {
           System.out.println(ex);
           //TODO
        } catch (IOException ex) {
            System.out.println(ex);
            //TODO
        }
    }

    public Object load() {
        Object object = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(_filename));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            object = objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();

        } catch (FileNotFoundException ex) {
            //TODO
            System.out.println(ex);
        } catch (IOException ex) {
            //TODO
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            //TODO
            System.out.println(ex);
        }

        return object;
    }
}