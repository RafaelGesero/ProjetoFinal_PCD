package game;

import java.io.Serializable;
import java.util.ArrayList;

public class Barreira implements Serializable {

    private int count;
    ArrayList<Player> finalizadores = new ArrayList<>();


    public Barreira(int count){
        this.count = count;
    }

    //coloca os tres primeiros jogadores em wait
    public synchronized void await () throws InterruptedException {
        while(count >0)
            wait ();
    }

    //se um jogador terminem entre os 3 primeiros o mesmo é adicionado á arrayList, apos isto inicia o final do jogo atraves da função podio()
    public synchronized void countDown (Player player){
        finalizadores.add(player);
        count --;
        if( count ==0){
            podio();
        }


    }

    //termina o jogo e apresenta na consola os jogadores(não humanos e humanos) pela sua ordem de chegada
    private void podio(){
        System.out.println("O jogo terminou, os vencedores foram: ");
        for(int i = 0; i < 3; i++){
            Player classi = finalizadores.get(i);

            if(classi.isHumanPlayer()){
                System.out.println("O Jogador humano com id: " +classi.getIdentification() + " terminou em " + (i + 1) + "º lugar: ");
            }else
                System.out.println("O Jogador automatico com id: " +classi.getIdentification() + " terminou em " + (i  + 1) + "º lugar: ");

        }
        System.exit(0);
    }

}