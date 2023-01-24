package game;

import java.io.Serializable;

public class Lock implements Serializable {

    private boolean close = false;


    public synchronized void lock() throws InterruptedException {
        if(close)
            wait();
        close = true;
    }

    public synchronized void unlock(){
        if(close){
            close = false;
            notifyAll();
        }
    }
    

}
