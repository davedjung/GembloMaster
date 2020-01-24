package minki.gemblomaster;
import java.io.*;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;


public class GembloMaster {

    public static void main(String[] args) throws FileNotFoundException {
        
        System.out.println("Gemblo Master version 0.1");
        System.out.println("author : Jung Min Ki");
        
        System.out.println();
        
        System.out.println("Automatic player testing");
        Board board = new Board();
        System.out.println("New board generated");
        
        System.out.println();
        
        Player player1 = new Player(generatePieces(), 1);
        player1.makeMove(board);
        board.display();
        
        Player player2 = new Player(generatePieces(), 2);
        player2.makeMove(board);
        board.display();
        
        
        while(true){
            boolean player1Playing = player1.makeMove(board);
            boolean player2Playing = player2.makeMove(board);
            board.display();
            if (!player1Playing && !player2Playing){
                break;
            }
        }
        System.out.println("Routine terminated");
                
    }
    
    public static Piece[] generatePieces() throws FileNotFoundException{
        
        File file = new File("/Users/minki/Documents/GembloMaster/src/main/java/minki/gemblomaster/database.txt"); 
        Scanner sc = new Scanner(file); 
  
        int counter = 0;
        
        Piece[] draft = new Piece[10000];
        
        while (sc.hasNextLine()){
            String[] parameters = sc.nextLine().split(",");
            int[][] conf = new int[Integer.parseInt(parameters[1])-1][2];
            for (int i=0; i<Integer.parseInt(parameters[1])-1; i++){
                conf[i][0] = Integer.parseInt(parameters[i*2+2]);
                conf[i][1] = Integer.parseInt(parameters[i*2+3]);
            }
            draft[counter++] = new Piece(parameters[0],Integer.parseInt(parameters[1]),conf);
        }
        
        
        
        
        Piece[] finalList = new Piece[counter];
        for (int i=0; i<counter; i++){
            finalList[i] = draft[i];
        }
        
        return finalList;
    }
    
}
