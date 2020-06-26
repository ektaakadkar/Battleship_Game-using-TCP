import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class Main_Algorithm {
    private static int checkHit ;
    private static String fileName;
    private static int flag;
    private static HashMap<Integer, Create_Ship> shipObjectMap = new HashMap<>();
    private Socket socketOf8000;
    private Socket socketOf8080;

    private static void check(){
        for (int shipNumber = 0; shipNumber < 10; shipNumber++) {
            if (shipObjectMap.containsKey(shipNumber)) {
                Create_Ship ship = shipObjectMap.get(shipNumber);
                int length = ship.isEmpty();
                if (length != 0) {
                    flag = 0;
                    return;
                }
                flag = 1;
            }
        }
    }

    private static boolean checkmiddleVerticle(int xCoOrdinate, Create_Ship ship, PrintStream printStreamofPlayer1,
                                               PrintStream printStreamOfPlayer2){
        int middle = ship.getMiddleCoOrdinateOfVertical();
        if (xCoOrdinate == middle && ship.isFirstHit){
            ship.coOrdinateList.removeAll(ship.coOrdinateList);
            printStreamofPlayer1.println("Super hit. The ship was destroyed entirely!");
            checkHit = 1;
            printStreamOfPlayer2.println("The other player has super hit.");
            return true;
        }
        return false;
    }

    private static boolean checkMiddle(int yCoOrdinate, Create_Ship ship, PrintStream printStreamOfPlayer1,
                                       PrintStream printStreamOfPlayer2){
        if (yCoOrdinate == ship.getMiddleCoOrdinateOfHorizontal() && ship.isFirstHit){
            ship.coOrdinateList.removeAll(ship.coOrdinateList);
            printStreamOfPlayer1.println("Super hit. The ship was destroyed entirely!");
            checkHit = 1;
            printStreamOfPlayer2.println("The other player has super hit.");
            return true;
        }
        return false;
    }

    private static boolean checkVertical(Create_Ship ship){
        if (ship.isEmpty() == 2){
        int first = ship.coOrdinateList.get(0);
        int second = ship.coOrdinateList.get(1);
            return first == second;
        }
        return false;
    }

    private static void remove(boolean isMiddle, Create_Ship shipObject, int yCoOrdinate, PrintStream printStreamOfPlayer1,
                               PrintStream printStreamOfPlayer2){
        if (!isMiddle){
            shipObject.coOrdinateList.remove(new Integer(yCoOrdinate));
        printStreamOfPlayer1.println("Correct hit");
        checkHit = 1;
        printStreamOfPlayer2.println("The other player has correct hit!");
        }
        shipObject.secondHit();
    }

    private static void hit(int xCoOrdinate, int yCoOrdinate, PrintStream printStreamOfPlayer1, PrintStream printStreamOfPlayer2){
        for (int shipNumber = 0; shipNumber < 10;shipNumber++) {
            if (shipObjectMap.containsKey(shipNumber)) {
                Create_Ship shipObject = shipObjectMap.get(shipNumber);
                if (shipObject.isFirstHit){
                    int shipLength = shipObject.coOrdinateList.size();
                    shipObject.assignLength(shipLength);
                }
                if (shipObject.coOrdinateList.contains(yCoOrdinate)) {
                    boolean isVertical = shipObject.isVertical;
                    if (!isVertical) {
                        if (shipObject.rowNumber == xCoOrdinate) {
                            boolean isMiddle = checkMiddle(yCoOrdinate, shipObject, printStreamOfPlayer1, printStreamOfPlayer2);
                            remove(isMiddle, shipObject, yCoOrdinate, printStreamOfPlayer1, printStreamOfPlayer2);
                            break;
                        }
                    }
                    else{
                        int shipLength = shipObject.getLength();
                        if (((shipObject.rowNumber-1) < xCoOrdinate) && (xCoOrdinate < (shipObject.rowNumber+shipLength))){
                            boolean isMiddle = checkmiddleVerticle(xCoOrdinate, shipObject, printStreamOfPlayer1, printStreamOfPlayer2);
                            remove(isMiddle, shipObject, yCoOrdinate, printStreamOfPlayer1, printStreamOfPlayer2);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void initialize(){
        try {
            String fileLine;
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int rowNumber = 1;
            while ((fileLine = bufferedReader.readLine()) != null) {
                String[] splitArray = fileLine.split(" ");
                int coOrdinate = 1;
                int index = 0;
                while (index < splitArray.length) {
                    if (splitArray[index].equals(".")){
                        flag = 0;
                    }
                    else{
                        flag = 1;
                    }
                    if (flag == 1){
                        int boatNumber = Integer.parseInt(splitArray[index]);
                        if (!shipObjectMap.containsKey(boatNumber)){
                            Create_Ship ship = new Create_Ship(rowNumber, false);
                            ship.coOrdinateList.add(coOrdinate);
                            shipObjectMap.put(boatNumber, ship);
                        }
                        else {
                            Create_Ship ship = shipObjectMap.get(boatNumber);
                            ship.coOrdinateList.add(coOrdinate);
                            boolean isVertical = checkVertical(ship);
                            if (isVertical){
                                ship.isVertical();
                            }
                        }
                    }
                    index++;
                    coOrdinate++;
                }
                rowNumber++;
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private Scanner connectionOfPlayer1() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        this.socketOf8080 = serverSocket.accept();
        return new Scanner(this.socketOf8080.getInputStream());
    }

    private Scanner connectionOfPlayer2() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        this.socketOf8000 = serverSocket.accept();
        return new Scanner(this.socketOf8000.getInputStream());
    }

    public static void main(String[] args) throws IOException {
        fileName = "Ship";
        flag = 0;
        Main_Algorithm object = new Main_Algorithm();
        Scanner scannerOfPlayer1 = object.connectionOfPlayer1();
        Scanner scannerOfPlayer2 = object.connectionOfPlayer2();
        object.initialize();
        int playerFlag = 0;
        PrintStream printStreamOfPlayer1 = new PrintStream(object.socketOf8080.getOutputStream());
        PrintStream printStreamOfPlayer2 = new PrintStream(object.socketOf8000.getOutputStream());
        while (flag == 0){
            String[] coOrdinates ;
            printStreamOfPlayer1.println(playerFlag);
            printStreamOfPlayer2.println(playerFlag);
            checkHit = 0;
            if (playerFlag == 0){
                String coOrdinateString = scannerOfPlayer1.nextLine();
                coOrdinates = coOrdinateString.split(" ");
                hit(Integer.parseInt(coOrdinates[0]), Integer.parseInt(coOrdinates[1]), printStreamOfPlayer1, printStreamOfPlayer2);
                playerFlag = 1;
                if (checkHit == 0) {
                    printStreamOfPlayer1.println("Missed the chance");
                    printStreamOfPlayer2.println("The other player's hit was missed!");
                }
            }
            else {
                String coOrdinateString = scannerOfPlayer2.nextLine();
                coOrdinates = coOrdinateString.split(" ");
                hit(Integer.parseInt(coOrdinates[0]), Integer.parseInt(coOrdinates[1]), printStreamOfPlayer2, printStreamOfPlayer1);
                playerFlag = 0;
                if (checkHit == 0) {
                    printStreamOfPlayer2.println("Missed the chance");
                    printStreamOfPlayer1.println("The other player's hit was missed!");
                }
            }
            check();
            printStreamOfPlayer1.println(flag);
            printStreamOfPlayer2.println(flag);
        }
        printStreamOfPlayer1.println("All the ships are destroyed!");
        printStreamOfPlayer2.println("All the ships are destroyed!");
        if (playerFlag == 1){
            printStreamOfPlayer1.println("Congratulations! You WON!");
            printStreamOfPlayer2.println("Sorry... You LOST!");
        }
        else {
            printStreamOfPlayer2.println("Congratulations! You WON!");
            printStreamOfPlayer1.println("Sorry... You LOST!");
        }
    }
}
