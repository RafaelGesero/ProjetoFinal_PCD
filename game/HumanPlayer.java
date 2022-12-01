package game;

public class HumanPlayer extends Player {



    public HumanPlayer(int id, Game game) {
        super(id, game);
    }

    @Override
    public boolean isHumanPlayer() {
        return true;
    }

    @Override
    public void run() {

    }

    public static void main(String[] args){
    }
}
