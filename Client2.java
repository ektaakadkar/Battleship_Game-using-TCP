import java.io.IOException;

public class Client2 {

    public static void main(String[] args) throws IOException {
        TCPConnection connection = new TCPConnection("localhost", 8000);
        connection.newSocket();
        Client_Algorithm clientObject = new Client_Algorithm();
        clientObject.client_Algorithm(connection, 1);
    }
}
