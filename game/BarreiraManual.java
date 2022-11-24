package game;

public class BarreiraManual extends Thread {

    private int count;
    private int lugar=0;


    public BarreiraManual (int count) {
        this.count = count;
    }

    public int cheguei(){
        lugar++;
        return lugar;
    }

    public synchronized void await ()
            throws InterruptedException {
        while (count > 0)
            wait();
    }
    public synchronized void countDown (){
        count --;
        if(count ==0)
            System.out.println("--------------------CHEGUEI AQUI-----------------------------");
    }
}
