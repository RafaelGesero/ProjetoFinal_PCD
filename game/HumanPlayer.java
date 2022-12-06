package game;

import environment.Direction;
import gui.BoardJComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HumanPlayer extends Player {

    private String name;
    BoardJComponent boardGui;
    private boolean keyTeclas;

    public HumanPlayer(int id, Game game, Barreira barreira, BoardJComponent boardGui, boolean keyTeclas) {
        super(id, game, barreira);
        currentStrength = 5;
        this.boardGui = boardGui;
        this.keyTeclas = keyTeclas;
    }

    public void tecla(){
        System.out.println(boardGui.getLastPressedDirection());
    }

    @Override
    public boolean isHumanPlayer() {
        return true;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public void run() {

    }

    public static void main(String[] args) throws IOException {

        JFrame addHumanPlayer = new JFrame("newPlayer");
        addHumanPlayer.setSize(300,300);
        addHumanPlayer.setLocation(550, 280);
        addHumanPlayer.setLayout(new GridLayout(4, 1));
        String[] choseOptions = {"","setas do teclado", "teclas: WASD"};

        JLabel title = new JLabel("como quer jogar?");
        JComboBox<String> jComboBox = new JComboBox<>(choseOptions);
        JTextArea nameBox = new JTextArea("Nome do jogador");
        JButton button = new JButton("Entrar no jogo");
        JLabel erro = new JLabel();

        addHumanPlayer.add(title);
        addHumanPlayer.add(jComboBox);
        addHumanPlayer.add(nameBox);
        addHumanPlayer.add(button);
        addHumanPlayer.add(erro);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String name = nameBox.getText();
               String keysPlay = jComboBox.getItemAt(jComboBox.getSelectedIndex());
               if(keysPlay.equals("setas do teclado")){

                   addHumanPlayer.dispose();

               }else if(keysPlay.equals("teclas: WASD")){
                   //fazer o addPlayerToGame aqui
                   addHumanPlayer.dispose();

               }else{
                   erro.setText("ERRO: adicionar forma de jogar!!!!!");
               }
            }
        });
        addHumanPlayer.setVisible(true);
        addHumanPlayer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*InformationToServer its = new  InformationToServer((byte) 5, Direction.UP, Estado.VIVO);
        Socket socket = new Socket("localhost", Game.PORTO);
        OutputStream out = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        objectOutputStream.writeObject(its);
        socket.close(); */
    }



}
