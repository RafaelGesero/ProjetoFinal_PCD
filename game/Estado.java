package game;

//classe utilizada para defenir o estado de cada jogador ao longo do tempo do jogo
public enum Estado {
    VIVO(1),MORTO(2), TERMINAL(3);
    private int estadoAtual;

    Estado(int estadoAtual){
        this.estadoAtual = estadoAtual;
    }

    public int getEstadoAtual(){
        return estadoAtual;
    }
}
