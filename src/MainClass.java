import java.util.Arrays;
import java.util.Scanner;

public class MainClass {
    private enum Status{
        NONE("None"),
        PLAYER1("Player1 Won"),
        PLAYER2("Player2 Won"),
        DRAW("Draw");
        String value;
        Status(String s) {
            value = s;
        }
    }
    private enum OX{
        E(" "),
        O("O"),
        X("X");
        String value;
        OX(){}
        OX(String s) {
            value=s;
        }
    }
    private final int boardLength;
    private final OX[][] ox;

    MainClass(int boardLength){
        this.boardLength = boardLength;
        ox= new OX[boardLength][];
        for(int i=0;i<boardLength;i++) {
            ox[i] = new OX[boardLength];
            for (int j = 0; j < boardLength; j++) {
                ox[i][j] = OX.E;
            }
        }
    }
    private String layout(){
        StringBuilder temp= new StringBuilder("");
        for(int i=0;i<boardLength;i++) {
            temp.append("| ");
            for(int j=0;j<boardLength;j++)
                temp.append(ox[i][j].value+" | ");
            temp.append("\n");
        }
        return String.valueOf(temp);
    }
    private boolean winning(OX e){
        int row,column,diagonal1,diagonal2;
        row=column=diagonal1=diagonal2=0;
        for(int i=0;i<3;i++){
            row=column=0;
            for(int j=0;j<3;j++){
                if(ox[i][j] == e){
                   row++;
                }
                if(ox[j][i]==e){
                    column++;
                }
            }
            if(row==3 || column==3){
                return true;
            }
            if(ox[i][i]==e)diagonal1++;
            if(ox[i][2-i]==e)diagonal2++;
        }
        return diagonal1==3 || diagonal2==3;
    }
    private boolean handlingInput(int currentPlayer){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Player-"+currentPlayer+"'s turn ["+OX.values()[currentPlayer]+"] : ");
        String position = scanner.nextLine();
        if(position.split(" ").length!=2) return false;
        try {
            int i = Integer.parseInt(position.split(" ")[0]);
            int j = Integer.parseInt(position.split(" ")[1]);
            if (j < 0 || j > boardLength - 1 || i < 0 || i > boardLength - 1) return false;
            if (ox[i][j] != OX.E || ox[i][j] == OX.values()[currentPlayer]) return false;
            ox[i][j] = OX.values()[currentPlayer];
        }catch (NumberFormatException e){
            return false;
        }
        System.out.println(layout());
        return true;
    }
    public void start(){
        Status gameStatus = Status.NONE;
        int turn = 0;
        int currentPlayer=1;

        System.out.println("Player-1 [O] & Player-2 [X]\n");
        System.out.println("Input Range - [0,"+(boardLength-1)+"]\n");
        System.out.println(layout());

        while(gameStatus==Status.NONE && turn<boardLength+1){
            if(!handlingInput(currentPlayer)) continue;
            currentPlayer=3-currentPlayer;
            turn++;
        }
        while(gameStatus==Status.NONE && turn<boardLength*boardLength){
            if(!handlingInput(currentPlayer)) continue;
            if(winning(OX.values()[currentPlayer])){
                gameStatus = Status.values()[currentPlayer];
                System.out.println(gameStatus.value);
                break;
            }
            currentPlayer=3-currentPlayer;
            turn++;
        }
        if(gameStatus==Status.NONE){
            gameStatus = Status.DRAW;
            System.out.println(gameStatus.value);
        }
    }
    public static void init_phase(){
        Scanner scanner=new Scanner(System.in);
        int boardLength=Integer.MIN_VALUE;
        while(boardLength<3) {
            System.out.print("Enter boardlength: ");
            boardLength = scanner.nextInt();
        }
        new MainClass(boardLength).start();
    }
    public static void main(String[] args) {
        init_phase();
    }
}
