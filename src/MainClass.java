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
    private OX[][] ox={{OX.E,OX.E,OX.E},{OX.E,OX.E,OX.E},{OX.E,OX.E,OX.E}};
    private String layout(){
        String temp=" ========== \n";
        for(int i=0;i<3;i++) {
            temp = temp + " " + ox[i][0].value + " | " + ox[i][1].value + " | " + ox[i][2].value + " \n";
            temp += " ========== \n";
        }
        return temp;
    }
    private boolean winning(OX e){
        int r,c,d1,d2;
        r=c=d1=d2=0;
        for(int i=0;i<3;i++){
            r=c=0;
            for(int j=0;j<3;j++){
                if(ox[i][j] == e){
                   r++;
                }
                if(ox[j][i]==e){
                    c++;
                }
            }
            if(r==3 || c==3){
                return true;
            }
            if(ox[i][i]==e)d1++;
            if(ox[i][2-i]==e)d2++;
        }
        return d1==3 || d2==3;
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
        Status gameStatus = Status.none;
        int turn = 0;
        int currentPlayer=1;

        System.out.println("Player-1 [O] & Player-2 [X]\n");
        System.out.println("Input Range - [0,2]\n");
        System.out.println(layout());
        while(gameStatus==Status.none && turn<4){
            if(!handlingInput(currentPlayer)) continue;
            currentPlayer=3-currentPlayer;
            turn++;
        }
        while(gameStatus==Status.none && turn<9){
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
    public static void main(String[] args) {
        new MainClass().start();
    }
}
