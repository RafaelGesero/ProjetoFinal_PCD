package game;

import java.io.Serializable;
import java.util.ArrayList;

public class Barreira implements Serializable {

    private int count;
    ArrayList<Integer> finalizadores = new ArrayList<>();


    public Barreira(int count){
        this.count = count;
    }

    public synchronized void await () throws InterruptedException {
        while(count >0)
            wait ();
    }

    public synchronized void countDown (int id){
        System.out.println("terminei " + id);
        finalizadores.add(id);
        count --;
        if( count ==0){
            podio();
        }


    }

    private void podio(){
        System.out.println("O jogo terminou, os vencedores foram: ");
        for(int i = 0; i < 3; i++){
            int classi = finalizadores.get(i);
            System.out.println(i + 1 + "º lugar: " + classi);
        }
        System.exit(0);
    }



}
