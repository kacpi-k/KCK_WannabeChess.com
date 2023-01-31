package chess.gui;

import chess.engine.board.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessMenu {

    private JFrame menuFrame;
    private final  JButton newGame = new JButton("Nowa Gra");

    private final  JButton exit = new JButton("Wyjście");
    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
    private ImageIcon BackgroundImage;
    private JLabel myLabel;
    public ChessMenu() {

        this.BackgroundImage = new ImageIcon(this.getClass().getResource("/chesswallpaper.jpg"));
        this.myLabel = new JLabel(BackgroundImage);
        this.myLabel.setSize(600,600);
        this.menuFrame = new JFrame("ChessMenu");
        this.menuFrame.add(myLabel);
        this.menuFrame.setLayout(null);
        this.menuFrame.setSize(OUTER_FRAME_DIMENSION);
        this.menuFrame.add(this.newGame);
        this.newGame.setBounds(130,100,100,40);
        this.newGame.setPreferredSize(new Dimension(40,50));
        this.menuFrame.add(this.exit);
        this.exit.setBounds(130,160,100,40);
        this.exit.setPreferredSize(new Dimension(40,50));
        this.menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.menuFrame.setVisible(true);

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Board board = Board.createStandardBoard();
                System.out.println(board);

                Table table = new Table();
                menuFrame.dispose();
            }
        });

    }







}
