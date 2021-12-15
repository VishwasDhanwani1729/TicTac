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
    private OX[][] ox={{OX.E,OX.E,OX.E},{OX.E,OX.E,OX.E},{OX.E,OX.E,OX.E}};
    private String layout(){
        StringBuilder temp= new StringBuilder(" ========== \n");
        for(int i=0;i<3;i++) {
            temp.append( " " + ox[i][0].value + " | " + ox[i][1].value + " | " + ox[i][2].value + " \n");
            temp.append(" ========== \n");
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
        int i= Integer.parseInt(position.substring(0,1));
        int j =Integer.parseInt(position.substring(1));
        if(j<0 || j>2 || i<0 || i>2) return false;
        if(ox[i][j]!=OX.E || ox[i][j]==OX.values()[currentPlayer]) return false;
        ox[i][j]=OX.values()[currentPlayer];
        System.out.println(layout());
        return true;
    }
    public void start(){
        Status gameStatus = Status.NONE;
        int turn = 0;
        int currentPlayer=1;

        System.out.println("Player-1 [O] & Player-2 [X]\n");
        System.out.println("Input Range - [0,2]\n");
        System.out.println(layout());
        while(gameStatus==Status.NONE && turn<4){
            if(!handlingInput(currentPlayer)) continue;
            currentPlayer=3-currentPlayer;
            turn++;
        }
        while(gameStatus==Status.NONE && turn<9){
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
    public static void main(String[] args) {
        new MainClass().start();
    }
}
