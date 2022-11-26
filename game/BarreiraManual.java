package game;

public class BarreiraManual{

    //numero de thread que queres para a barreira terminar tudo
    private int Nespera;
    private int NesperaAtual = 0;

    //funação que vais criar no gui como esta la na cyclic, este runnable so vai ser
    // executado quando o Nespera for igual o NesperaAtual
    private Runnable podio;

//crias esta barreira como a cyclic, como está no gui
    public BarreiraManual(){

    }


//aqui vais ter de ver se o as as threads estao em espera e quando 3
// delas tiverem executas o run do runnabel que criaste
    public synchronized void await () throws InterruptedException{
        }
}
