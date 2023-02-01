package chess.gui;

import chess.engine.board.Board;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {

    private JFrame menuFrame;
    public JFrame getMenuFrame() {
        return menuFrame;
    }
    private final  JButton newGame = new JButton("Nowa Gra");
    private final  JButton exit = new JButton("Wyjście");
    private final static Dimension OUTER_FRAME_DIMENSION = Table.getOuterFrameDimension();
    private ImageIcon BackgroundImage;
    private JLabel myLabel;
    public MainMenu() {

        this.BackgroundImage = new ImageIcon(this.getClass().getResource("/chesswallpaper.png"));
        this.myLabel = new JLabel(BackgroundImage);
        this.myLabel.setSize(Table.getOuterFrameDimension());
        this.menuFrame = new JFrame("KCK Mazowiecka Chess");
        this.menuFrame.setLayout(new BorderLayout());
        this.menuFrame.setSize(OUTER_FRAME_DIMENSION);
        this.newGame.setBounds((OUTER_FRAME_DIMENSION.width/2) - 100, OUTER_FRAME_DIMENSION.height/2, 200, 50);
        this.newGame.setPreferredSize(new Dimension(200,50));
        this.newGame.setBackground(Color.white);
        this.exit.setBounds(130,160,100,40);
        this.exit.setPreferredSize(new Dimension(40,50));
        this.menuFrame.add(this.newGame, BorderLayout.CENTER);
        this.menuFrame.add(this.exit, BorderLayout.CENTER);
        this.menuFrame.add(myLabel);
        this.menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.menuFrame.setResizable(false);
        center(this.menuFrame);
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


    public static void center(final JFrame frame) {
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final int w = frame.getSize().width;
        final int h = frame.getSize().height;
        final int x = (dim.width - w) / 2;
        final int y = ((dim.height - h) / 2)-12;
        frame.setLocation(x, y);
    }




}
