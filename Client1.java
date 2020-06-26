import java.io.IOException;

public class Client1 {

    public static void main(String[] args) throws IOException {
        TCPConnection connection = new TCPConnection("localhost", 8080);
        connection.newSocket();
        Client_Algorithm clientObject = new Client_Algorithm();
        clientObject.client_Algorithm(connection, 0);
    }
}
