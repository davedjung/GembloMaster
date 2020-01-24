package minki.gemblomaster;

public class Board {
    
    int status[][][];
    int rows, columns, playerCount;
    boolean playing;
    
    public Board(){
        rows = 29; columns = 43; playerCount = 6;//standard size for six player Gemblo
        rows = 17; columns = 25; playerCount = 2;//mini size for two player Gemblo
        initialize(rows,columns,playerCount);
        playing = false;
    }
    
    public void initialize(int rows, int columns, int playerCount){
        
        status = new int[rows][columns][playerCount+1];
        
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                for (int k=0; k<=playerCount; k++){
                    status[i][j][k] = -1;
                }
            }
        }
        
        for (int i=0; i<=rows/2; i++){
            for (int j=0; j<columns; j++){
                if (playerCount == 6){
                    if (i%2 == 1 && j%2 == 0 || i%2 == 0 && j%2 == 1){
                        int n = 3 * i;
                        int center = columns / 2;
                        if (Math.abs(center - j) <= n){
                            for (int k=0; k<=playerCount; k++){
                                status[i][j][k] = 0;
                                status[rows - 1 - i][j][k] = 0;
                            }
                        }
                    }
                } else if (playerCount == 2){
                    if (i%2 == 1 && j%2 == 1 || i%2 == 0 && j%2 == 0){
                        int n = 3 * i;
                        int center = columns / 2;
                        if (Math.abs(center - j) <= n){
                            for (int k=0; k<=playerCount; k++){
                                status[i][j][k] = 0;
                                status[rows - 1 - i][j][k] = 0;
                            }
                        }
                    }
                }
            }
        }
        
        
    }
    
    public void placePiece(int[] x, int[] y, int player){
        
        //check validity
        
        int size = x.length;
        for (int i=0; i<size; i++){
            status[x[i]][y[i]][0] = player;
        }
    }
    
    public void placePiece(Piece piece, int x, int y, int player){
        int[][] coordinates = piece.getPiece();
        int size = piece.getSize();
        
        status[x][y][0] = player;
        for (int i=0; i<size-1; i++){
            status[x+coordinates[i][0]][y+coordinates[i][1]][0] = player;
        }
    }
    
    public int[][] showOptions(int player){
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                if (status[i][j][player] != -1 && status[i][j][0] != 0){
                    status[i][j][player] = -1;
                }
                
                //exclude neighboring cells
                if (status[i][j][0] == player){
                    if (i-1 >= 0){
                        if (j-1 >= 0){
                            status[i-1][j-1][player] = -1;
                        }
                        if (j+1 < columns){
                            status[i-1][j+1][player] = -1;
                        }
                    }
                    
                    if (j+2 < columns){
                        status[i][j+2][player] = -1;
                    }
                    if (j-2 >= 0){
                        status[i][j-2][player] = -1;
                    }
                    
                    if (i+1 <rows){
                        if (j-1 >= 0){
                            status[i+1][j-1][player] = -1;
                        }
                        if (j+1 < columns){
                            status[i+1][j+1][player] = -1;
                        }
                    }
                }
                
                //check open connections
                if (status[i][j][0] == player){
                    if (i-2 >= 0){
                        if (status[i-2][j][player] != -1){
                            status[i-2][j][player] = 1;
                        }
                        if (j-1 >= 0 && j+1 < columns){
                            if (status[i-1][j-1][0] == status[i-1][j+1][0] && status[i-1][j-1][0] > 0){
                                status[i-2][j][player] = -1;
                            }
                        }
                    }
                    if (i-1 >= 0 && j+3 < columns){
                        if (status[i-1][j+3][player] != -1){
                            status[i-1][j+3][player] = 1;
                        }
                        if (status[i-1][j+1][0] == status[i][j+2][0] && status[i-1][j+1][0] > 0){
                            status[i-1][j+3][player] = -1;
                        }
                    }
                    if (i+1 < rows && j+3 < columns){
                        if (status[i+1][j+3][player] != -1){
                            status[i+1][j+3][player] = 1;
                        }
                        if (status[i][j+2][0] == status[i+1][j+1][0] && status[i][j+2][0] > 0){
                            status[i+1][j+3][player] = -1;
                        }
                    }
                    if (i+2 < rows){
                        if (status[i+2][j][player] != -1){
                            status[i+2][j][player] = 1;
                        }
                        if (j+1 < columns && j-1 >= 0){
                            if (status[i+1][j+1][0] == status[i+1][j-1][0] && status[i+1][j+1][0] > 0){
                                status[i+2][j][player] = -1;
                            }
                        }
                    }
                    if (i+1 < rows && j-3 >= 0){
                        if (status[i+1][j-3][player] != -1){
                            status[i+1][j-3][player] = 1;
                        }
                        if (status[i+1][j-1][0] == status[i][j-2][0] && status[i+1][j-1][0] > 0){
                            status[i+1][j-3][player] = -1;
                        }
                    }
                    if (i-1 >= 0 && j-3 >= 0){
                        if (status[i-1][j-3][player] != -1){
                            status[i-1][j-3][player] = 1;
                        }
                        if (status[i][j-2][0] == status[i-1][j-1][0] && status[i][j-2][0] > 0){
                            status[i-1][j-3][player] = -1;
                        }
                    }
                    
                    
                }
            }
        }
        
        if (playing == false){
            status[4][24][1] = 1;
            status[12][0][2] = 1;
            playing = true;
        }
        
        
        int counter = 0;
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                if (status[i][j][player] == 1){
                    //System.out.print("?");
                    counter++;
                } else {
                    switch (status[i][j][0]){
                        case -1:
                            //System.out.print(" ");
                            break;
                        case 0:
                            //System.out.print("o");
                            break;
                        case 1:
                            //System.out.print("*");
                            break;
                        case 2:
                            //System.out.print("#");
                            break;
                        default:
                            //System.out.print("?");
                    }
                }
            }
            //System.out.println();
        }
        
        int[][] candidates = new int[counter][2];
        counter = 0;
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                if (status[i][j][player] == 1){
                    candidates[counter][0] = i;
                    candidates[counter++][1] = j;
                }
            }
        }
        
        return candidates;
        
    }
    
    public boolean checkValidity(Piece piece, int x, int y, int player){
        boolean valid = true;
        
        if (status[x][y][0] == 0 && status[x][y][player] == 1){
            int[][] coordinates = piece.getPiece();
            int size = coordinates.length;
            for (int i=0; i<size; i++){
                if (coordinates[i][0]+x < 0 || coordinates[i][0]+x >= rows){
                    valid = false;
                    break;
                } else if (coordinates[i][1]+y < 0 || coordinates[i][1]+y >= columns){
                    valid = false;
                    break;
                }
                if (status[coordinates[i][0]+x][coordinates[i][1]+y][0] != 0){
                    valid = false;
                } else {
                    if (status[coordinates[i][0]+x][coordinates[i][1]+y][player] == -1){
                        valid =false;

                    }
                }
            }
        } else {
            valid = false;
        }
        
        return valid;
    }
    
    public void display(){
        //System.out.print("\033[H\033[2J");  
        //System.out.flush();
        for (int i=0; i<rows; i++){
            for (int j=0; j<columns; j++){
                switch (status[i][j][0]){
                    case -1:
                        System.out.print(" ");
                        break;
                    case 0:
                        System.out.print("o");
                        break;
                    case 1:
                        System.out.print("*");
                        break;
                    case 2:
                        System.out.print("#");
                        break;
                    default:
                        System.out.print("?");
                }
            }
            System.out.println();
        }
    }
}
