import java.util.ArrayList;
import java.util.List;

class Create_Ship {
    boolean isFirstHit;
    private int length;
    List<Integer> coOrdinateList;
    int rowNumber;
    boolean isVertical;

    Create_Ship(int rowNumber, boolean isColumn){
        isFirstHit = true;
        coOrdinateList = new ArrayList<>();
        this.rowNumber = rowNumber;
        this.isVertical = isColumn;
    }

    void secondHit(){
        isFirstHit = false;
    }

    void isVertical(){
        isVertical = true;
    }

    int getMiddleCoOrdinateOfHorizontal(){
        if (length % 2 == 0)
            return (length / 2) + coOrdinateList.get(0) - 1;
        return (length / 2) + coOrdinateList.get(0) ;
    }

    int isEmpty(){
        return coOrdinateList.size();
    }

    void assignLength(int length){
        this.length = length;
    }

     int getLength(){
        return length;
    }

     int getMiddleCoOrdinateOfVertical(){
        if (length % 2 == 0)
            return (length / 2) + rowNumber - 1;
        return (length / 2) + rowNumber ;
    }
}
