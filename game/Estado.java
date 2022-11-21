package game;

public enum Estado {

    VIVO(1),MORTO(2),ESPERA(3), TERMINAL(4);
    private int estadoAtual;
    Estado(int estadoAtual){
        this.estadoAtual = estadoAtual;
    }

    public int getEstadoAtual(){
        return estadoAtual;
    }
}
