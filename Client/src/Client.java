import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9999);
        } catch (UnknownHostException ex) {
            System.out.println(ex);
        } catch (IOException ex){
            System.out.println(ex);
        }
    }
}

//
//        System.out.println("Client gestartet");
//
//    ClientRequest clientRequest = new ClientRequest(ClientAction.LOGIN, new String[]{"admin"});
//
//            System.out.println("123");
//    // ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
//            System.out.println("moin");
//    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//            System.out.println("lol");
//            objectOutputStream.writeObject(clientRequest);
//            System.out.println("Nachricht gesendet");
//
//
//    // wait foR response
//            System.out.println("Wait for response");
//    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
//
//
//            try{
//        Result<PersonType> result = (Result<PersonType>) objectInputStream.readObject();
//        if(result.getState() == Result.State.SUCCESSFULL){
//            System.out.println(result.getObject().toString());
//        } else{
//            System.out.println(result.getMessage());
//        }
//    } catch(ClassNotFoundException ex){
//        System.out.println("Leider Fehler UUU" + ex);
//    }
//
//} catch (IOException ex) {
//        System.out.println("Leider Fehler X" + ex);
//        System.exit(-1);
//        }