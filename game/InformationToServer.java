package game;

import environment.Direction;

import java.io.Serializable;

public class InformationToServer implements Serializable {

    private byte currentStrength;
    private Direction direction;
    private Estado estado;

    public InformationToServer(byte currentStrength, Direction direction, Estado estado){
        this.currentStrength = currentStrength;
        this.direction = direction;
        this.estado = estado;
    }

    public byte getCurrentStrength(){
        return currentStrength;
    }

    public Direction getDirection(){
        return direction;
    }
    public Estado getEstado(){
        return estado;
    }

}
