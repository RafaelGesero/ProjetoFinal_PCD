package game;

public class BarreiraManual extends Thread {

    private int count=3;
     private byte energia;

    private int lugar=0;


    public BarreiraManual () {
    }

    public int cheguei(){
        lugar++;
        return lugar;
    }

    public synchronized void await ()
            throws InterruptedException {
        while (energia<10)
            wait();
    }
    public synchronized void countDown (){
        count --;
        if(count ==0)
            notifyAll();
    }
}
