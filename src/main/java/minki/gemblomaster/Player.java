package minki.gemblomaster;
import java.util.Random;

public class Player {
    
    Piece unused[];
    int ID;
    
    public Player(Piece[] set, int ID){
        unused = set;
        this.ID = ID;
    }
    
    public boolean makeMove(Board board){
        System.out.println("Player " + ID + " playing");
        int[][] dockPoints = board.showOptions(ID);
        
        if (dockPoints.length == 0){
            System.out.println("No valid move remaining");
            System.out.println("Player " + ID + " : " + tallyScore());
            return false;
        }
        
        
        int score = unused.length;
        int options = dockPoints.length;
        int counter;
        boolean validMovePresent = false;
        Piece[][] nextMove = new Piece[options][score];
        for (int i=0; i<options; i++){
            counter = 0;
            for (int j=0; j<score; j++){
                if (board.checkValidity(unused[j],dockPoints[i][0],dockPoints[i][1],ID)){
                    nextMove[i][counter++] = unused[j];
                    validMovePresent = true;
                }
            }
        }
        
        
        if (validMovePresent){
            while (true){
                Random random = new Random();
                int i = random.nextInt(options);
                int j = random.nextInt(score);
                if (nextMove[i][j] != null){
                    board.placePiece(nextMove[i][j],dockPoints[i][0],dockPoints[i][1],ID);
                    removeUsed(nextMove[i][j]);
                    System.out.println("Player " + ID + " : " + tallyScore());
                    return true;
                }
            }
        } else {
            System.out.println("No valid move remaining");      
            System.out.println("Player " + ID + " : " + tallyScore());
            return false;
        }
        
    }
    
    private void removeUsed(Piece used){
        
        
        int counter = unused.length;
        for (int k=0; k<unused.length; k++){
            if (unused[k].getID().equals(used.getID())){
                counter--;
                unused[k] = null;
            }
        }
        
        Piece[] updatedUnusedList = new Piece[counter];

        int index = 0;
        for (int i=0; i<unused.length; i++){
            if (unused[i] != null){
                updatedUnusedList[index++] = unused[i];
            }
        }
        this.unused = updatedUnusedList;
    }
    
    public int tallyScore(){
        
        int score = 0;
        Piece[] tallyList = new Piece[unused.length];
        for (int i=0; i<tallyList.length; i++){
            tallyList[i] = unused[i];
        }
        for (int i=0; i<tallyList.length; i++){
            if (tallyList[i] != null){
                for (int j=i+1; j<tallyList.length; j++){
                    if (tallyList[i].getID().equals(tallyList[j].getID())){
                        tallyList[j] = null;
                    }
                }
                score += tallyList[i].getSize();
            }
        }
        
        return score;
        
    }
}
