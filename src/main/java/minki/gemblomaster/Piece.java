package minki.gemblomaster;

public class Piece {
    
    String ID;
    int size;
    int configuration[][];
    
    public Piece(String ID, int size, int[][] configuration){
        this.ID = ID;
        this.size = size;
        this.configuration = configuration;
    }
    
    public String getID(){return ID;}
    public int getSize(){return size;}
    public int[][] getPiece(){return configuration;}
    
}
