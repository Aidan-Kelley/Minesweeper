/*
 * Minesweeper by Sean O
 * 4/9/2024
 * For AP Computer Science A Project
 */

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Minesweeper{

    public Minesweeper(){

    }

    public static void intro(){
        Scanner scan = new Scanner(System.in);
        System.out.println("\nHello, and Welcome to...");
        String[] title = new String[6];
        title[0] = " __  __ _____ _   _ ______  _______          ________ ______ _____  ______ _____  ";
        title[1] = "|  \\/  |_   _| \\ | |  ____|/ ____\\ \\        / /  ____|  ____|  __ \\|  ____|  __ \\ ";
        title[2] = "| \\  / | | | |  \\| | |__  | (___  \\ \\  /\\  / /| |__  | |__  | |__) | |__  | |__) |";
        title[3] = "| |\\/| | | | | . ` |  __|  \\___ \\  \\ \\/  \\/ / |  __| |  __| |  ___/|  __| |  _  / ";
        title[4] = "| |  | |_| |_| |\\  | |____ ____) |  \\  /\\  /  | |____| |____| |    | |____| | \\ \\ ";
        title[5] = "|_|  |_|_____|_| \\_|______|_____/    \\/  \\/   |______|______|_|    |______|_|  \\_\\";
        for(int i = 0; i < 6; i++){
            System.out.println(title[i]);
            try{
                TimeUnit.MILLISECONDS.sleep(50);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("\n|| HOW TO PLAY ||\n"+
        "There are three different actions you can take:\nF - Flags the spot\nU - Unflags the spot if flagged\nB - Reveals the spot.  If it is a mine, game over!\n" +
        "Enter your action, then add the row and column for the spot you chose.\n"+
        "Example: \"F 1,5\" - adds a flag at (1,5), or the 1st column and 5th row\n\n"+
        "Type anything to start: "
        );
        
        scan.nextLine();
        start();
    }
    public static void start(){
        Scanner scan = new Scanner(System.in);
        int rows = 9;
        int cols = 9;
        int mines = 10;
        int[] act = new int[3];
        String[][] field = new String[rows][cols];
        int[][] trueField = new int[rows][cols];
        boolean finished = false;
        boolean won = false;
        int breakcount = 0;

        for (int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                field[r][c] = " ? ";
            }
        }

        //reveal middle 9 spots (3x3), then generate the mines
        //  while mines != 0, go through each element to test mine
        //  once mines == 0, calculate numbers
        for(int r = (rows/2)-1; r < (rows/2)+2; r++){
            for(int c = (cols/2)-1; c < (cols/2)+2; c++){
                trueField[r][c] = 11;//11 will be skipped by the mine placing
            }
        }
        while(mines > 0){
            for(int r = 0; r < rows; r++){
                for(int c = 0; c < cols; c++){
                    if(trueField[r][c] != 11 && Math.random()*100 <= 5){ //if its not the middle 9, and hits the 5% chance, places the mine
                        trueField[r][c] = 9; //10 is a mine
                        mines--;
                        break;
                    }
                }
            }
        }

        for(int r = 0; r < trueField.length; r++){//set up all the numbers
            for(int c = 0; c < trueField[0].length; c++){
                trueField[r][c] = setNumber(trueField, r, c);
            }
        }
        for(int r = (rows/2)-1; r < (rows/2)+2; r++){//set the middle
            for(int c = (cols/2)-1; c < (cols/2)+2; c++){
                field[r][c] = " " + trueField[r][c] + " ";
            }
        }

        while(!finished){
            showField(field);
            System.out.print("Enter: ");
            String ans = scan.nextLine();
            act = act(ans, rows, cols);
            int action = act[0];//1: flag, 2: break, 3: unflag
            int rownew = act[2]-1;
            int colnew = act[1]-1;
            //next, set the field val to the truefield val to reveal number
            //if it is 9, its a mine. put X
            if(action == 1){//if flag
                field[rownew][colnew] = " F ";
            }
            if(action == 2){//if break
                if(trueField[rownew][colnew] == 9){//if it's a mine, you lose
                    field[rownew][colnew] = " X ";
                    showField(field);
                    finished = true;
                    break;
                }
                else{
                    field[rownew][colnew] = " " + trueField[rownew][colnew] + " ";
                    breakcount++;
                    if(breakcount == 62){
                        won = true;
                        finished = true;
                        break;
                    }
                }
            }
            if(action == 3){//if unflag
                field[rownew][colnew] = " ? ";
            }            
        }
        end(won);
    }
    
    public static int[] act(String ans, int rows, int cols){
        ans = ans.toUpperCase();
        String x = ans.substring(ans.indexOf(" ")+1, ans.indexOf(" ", ans.indexOf(" ")+1));
        String y = ans.substring(1+ans.indexOf(" ", ans.indexOf(" ")+1));
        int[] action = new int[3];

        if(ans.substring(0,1).equals("F")){//if flag
            action[0] = 1;
        }
        else if(ans.substring(0,1).equals("B")){//if break
            action[0] = 2;
        }
        else{//assume unflag
            action[0] = 3;
        }
        
        //check for coordinates
        for(int i = 1; i <= cols; i++){//extracts column value
            if(x.equals(i+"")){
                action[1] = i;
                break;
            }
        }
        for(int i = 1; i <= rows; i++){//extracts row value
            if(y.equals(""+i)){
                action[2] = i;   
                break;
            }
        }
        return action;
    }

    public static int setNumber(int[][] field, int row, int col){
        int counter = 0;
        int mineNumber = 9;
        //for same row
        if(field[row][col] == 9){//if it's a mine
            return 9;
        }
        if(col > 0){//not touching the left wall
            if(field[row][col-1] == mineNumber){//mine at top left
                counter++;
            }
        }
        if(col < field.length-1){//not touching the right wall
            if(field[row][col+1] == mineNumber){//mine at top right
                counter++;
            }
        }

        //for top row
        if(row > 0){//if row is not touching the ceiling, check for top 3 elements
            //let int[row][col] be the element we're modifying
            if(field[row-1][col] == mineNumber){//mine at top
                counter++;
            }
            if(col > 0){//not touching the left wall
                if(field[row-1][col-1] == mineNumber){//mine at top left
                    counter++;
                }
            }
            if(col < field[0].length-1){//not touching the right wall
                if(field[row-1][col+1] == mineNumber){//mine at top right
                    counter++;
                }
            }
        }
        if(row < field.length-1){//not touching the bottom
            if(field[row+1][col] == mineNumber){//mine at bottom
                counter++;
            }
            if(col > 0){//not touching the left wall
                if(field[row+1][col-1] == mineNumber){//mine at top left
                    counter++;
                }
            }
            if(col < field[0].length-1){//not touching the right wall
                if(field[row+1][col+1] == mineNumber){//mine at top right
                    counter++;
                }
            }

        }
    
        return counter;
    }

    public static void end(boolean won){
        String[] msg = new String[6];
        if(won){
            System.out.println("Amazing!\n");
            msg[0] = " __     __           _____  _     _   _____ _   _ ";
            msg[1] = " \\ \\   / /          |  __ \\(_)   | | |_   _| | | |";
            msg[2] = "  \\ \\_/ /__  _   _  | |  | |_  __| |   | | | |_| |";
            msg[3] = "   \\   / _ \\| | | | | |  | | |/ _` |   | | | __| |";
            msg[4] = "    | | (_) | |_| | | |__| | | (_| |  _| |_| |_|_|";
            msg[5] = "    |_|\\___/ \\__,_| |_____/|_|\\__,_| |_____|\\__(_)";

        }
        else{
            System.out.println("Oops!");
            msg[0] = "   _____          __  __ ______    ______      ________ _____  ";
            msg[1] = "  / ____|   /\\   |  \\/  |  ____|  / __ \\ \\    / /  ____|  __ \\ ";
            msg[2] = " | |  __   /  \\  | \\  / | |__    | |  | \\ \\  / /| |__  | |__) |";
            msg[3] = " | | |_ | / /\\ \\ | |\\/| |  __|   | |  | |\\ \\/ / |  __| |  _  / ";
            msg[4] = " | |__| |/ ____ \\| |  | | |____  | |__| | \\  /  | |____| | \\ \\ ";
            msg[5] = "  \\_____/_/    \\_\\_|  |_|______|  \\____/   \\/   |______|_|  \\_\\";
        }
        for(int i = 0; i < 6; i++){
            System.out.println(msg[i]);
            try{
                TimeUnit.MILLISECONDS.sleep(500);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println();


    }
    public static void showField(String[][] field){
        System.out.print("   ");
        for(int i = 1; i <= field[0].length; i++){ //add the column numbers
            System.out.print(" " + i + " ");
        }
        System.out.println();
        for(int r = 0; r < field.length; r++){ //add the row numbers
            System.out.print((r+1) + " ");
            if(r < 9){
                System.out.print(" ");
            }
            for(int c = 0; c < field[0].length; c++){
                System.out.print(field[r][c]);
            }
            System.out.println();
        }
        System.out.println("\nF = Flagged\n");
        
    }
    public static void showTrueField(int[][] field){
        System.out.print("  ");
        for(int i = 1; i <= field[0].length; i++){ //add the column numbers
            System.out.print(" " + i + " ");
        }
        System.out.println();
        for(int r = 0; r < field.length; r++){ //add the row numbers
            System.out.print((r+1) + " ");
            if(r < 9){
                System.out.print(" ");
            }
            for(int c = 0; c < field[0].length; c++){
                System.out.print(field[r][c] + ", ");
            }
            System.out.println();
        }
        System.out.println("\nF = Flagged");
        
    }

    public static void main(String args[]){
        intro();
    }
}