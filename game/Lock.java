package game;

public class Lock {

    private boolean close = false;

    public synchronized void lock() throws InterruptedException {
        if(close == true)
            wait();
        close = true;
    }

    public synchronized void unlock(){
        if(close == true){
            close = false;
            notifyAll();
        }
    }

}
