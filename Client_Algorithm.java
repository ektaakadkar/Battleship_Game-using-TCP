import java.io.PrintStream;
import java.util.Scanner;

public class Client_Algorithm {

    void client_Algorithm(TCPConnection connection, int checkPlayerFlag) {
        int flag = 0;
        while (flag == 0) {
            PrintStream printStream = connection.printStream;
            int playerFlag = connection.scanner.nextInt();
            if (playerFlag == checkPlayerFlag) {
                System.out.println("Your turn!");
                System.out.println("Please enter the x co-ordinate to hit the ship!");
                Scanner hitObject = new Scanner(System.in);
                int xHitCoOrdinate = hitObject.nextInt();
                System.out.println("Please enter the y co-ordinate to hit the ship!");
                int yHitCoOrdinate = hitObject.nextInt();
                printStream.println("" + xHitCoOrdinate + " " + yHitCoOrdinate);
            }
            connection.scanner.nextLine();
            System.out.println(connection.scanner.nextLine());
            flag = connection.scanner.nextInt();
        }
        connection.scanner.nextLine();
        System.out.println(connection.scanner.nextLine());
        System.out.println(connection.scanner.nextLine());
    }

}
