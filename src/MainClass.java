import java.util.Arrays;
import java.util.Scanner;

public class MainClass {
    private enum Status{
        none("NONE"),p1("Player1 Won"),p2("Player2 Won"),draw("DRAW");
        String value;
        Status(String s) {
            value = s;
        }
    }
    private enum OX{E(" "),O("O"),X("X");
        String value;
        OX(){}
        OX(String s) {
            value=s;
        }
    }
    enum test{a(1),b(2);
        int value;
        test(int i) {
            value = i;
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
        String temp="";
        for(int i=0;i<boardLength;i++) {
            temp=temp+"| ";
            for(int j=0;j<boardLength;j++)
                temp = temp+ox[i][j].value + " | ";
//            temp = temp + " " + ox[i][0].value + " | " + ox[i][1].value + " | " + ox[i][2].value + " \n";
            temp+=" \n";
        }
        return temp;
    }
    private boolean winning(OX e){
        int r,c,d1,d2;
        r=c=d1=d2=0;
        for(int i=0;i<boardLength;i++){
            r=c=0;
            for(int j=0;j<boardLength;j++){
                if(ox[i][j] == e){
                   r++;
                }
                if(ox[j][i]==e){
                    c++;
                }
            }
            if(r==boardLength || c==boardLength){
                return true;
            }
            if(ox[i][i]==e)d1++;
            if(ox[i][boardLength-1-i]==e)d2++;
        }
        return d1==boardLength || d2==boardLength;
    }
    private boolean handlingInput(int currentPlayer){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Player-"+currentPlayer+"'s turn ["+OX.values()[currentPlayer]+"] : ");
        String position = scanner.nextLine();
        int i= Integer.parseInt(position.substring(0,1));
        int j =Integer.parseInt(position.substring(1));
        if(j<0 || j>boardLength-1 || i<0 || i>boardLength-1) return false;
        if(ox[i][j]!=OX.E || ox[i][j]==OX.values()[currentPlayer]) return false;
        ox[i][j]=OX.values()[currentPlayer];
        System.out.println(layout());
        return true;
    }
    public void start(){
        Status gameStatus = Status.none;
        int turn = 0;
        int currentPlayer=1;

        System.out.println("Player-1 [O] & Player-2 [X]\n");
        System.out.println("Input Range - [0,"+(boardLength-1)+"]\n");
        System.out.println(layout());
        while(gameStatus==Status.none && turn<boardLength+1){
            if(!handlingInput(currentPlayer)) continue;
            currentPlayer=3-currentPlayer;
            turn++;
        }
        while(gameStatus==Status.none && turn<boardLength*boardLength){
            if(!handlingInput(currentPlayer)) continue;
            if(winning(OX.values()[currentPlayer])){
                gameStatus = Status.values()[currentPlayer];
                System.out.println(gameStatus.value);
                break;
            }
            currentPlayer=3-currentPlayer;
            turn++;
        }
        if(gameStatus==Status.none){
            gameStatus = Status.draw;
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
