/*
  TCPConnection.java

  The TCPConnection.java class does the initialization of all the required parameters such as 'socket', 'scanner' and
  'print stream' of a given TCP connection. The input parameters are hostname and port number.

  @author  :  Ekta Rajio Akadkar

  Version:     1
*/



import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class TCPConnection {

    private String hostname;
    private int port;
    Scanner scanner;
    PrintStream printStream;

    TCPConnection(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    void newSocket() throws IOException {
        Socket socket = new Socket(hostname, port);
        scanner = new Scanner(socket.getInputStream());
        printStream  = new PrintStream(socket.getOutputStream());
        /*
         * The socket is initialized using the constructor parameters.
         * The above loop initializes all the parameters for a given hostname and port number.
         * @param  scanner          scanner of the respective socket
         * @param  printStream      printStream of the respective socket
         *
         */
    }
}
