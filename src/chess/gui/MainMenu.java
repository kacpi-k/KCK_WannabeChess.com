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

    private final JButton startGameButton = new JButton("Utwórz grę");
    public JButton getStartGameButton() {
        return this.startGameButton;
    }

    private final JButton backButton = new JButton("Wróć");
    public JButton getBackButton() {
        return backButton;
    }

    private final  JButton newGame = new JButton("Nowa Gra");
    public JButton getNewGame() {
        return this.newGame;
    }
    private final  JButton exit = new JButton("Wyjście");
    public JButton getExit() {
        return this.exit;
    }
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


        this.newGame.setBounds((OUTER_FRAME_DIMENSION.width/2) - 150, (OUTER_FRAME_DIMENSION.height/2) - 300, 300, 50);
        //this.newGame.setPreferredSize(new Dimension(200,50));
        this.newGame.setBackground(Color.lightGray);
        this.newGame.setFont(new Font("Monaco", Font.BOLD, 16));
        this.newGame.setFocusable(false);
        this.exit.setBounds((OUTER_FRAME_DIMENSION.width/2) - 125,(OUTER_FRAME_DIMENSION.height/2) - 200,250,50);
        //this.exit.setPreferredSize(new Dimension(40,50));
        this.exit.setFocusable(false);
        this.startGameButton.setBounds((OUTER_FRAME_DIMENSION.width/2) - 150, (OUTER_FRAME_DIMENSION.height/2) - 300, 300, 50);
        this.startGameButton.setFocusable(false);
        this.startGameButton.setVisible(false);
        this.backButton.setBounds((OUTER_FRAME_DIMENSION.width/2) - 125, (OUTER_FRAME_DIMENSION.height/2) - 200, 250, 50);
        this.backButton.setFocusable(false);
        this.backButton.setVisible(false);

        this.menuFrame.add(this.newGame);
        this.menuFrame.add(this.exit);
        this.menuFrame.add(this.startGameButton);
        this.menuFrame.add(this.backButton);
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

                //Table table = new Table();
                //menuFrame.dispose();

                getNewGame().setVisible(false);
                getExit().setVisible(false);

                getStartGameButton().setVisible(true);
                getBackButton().setVisible(true);

            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Table table = new Table();
                menuFrame.dispose();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getStartGameButton().setVisible(false);
                getBackButton().setVisible(false);

                getNewGame().setVisible(true);
                getExit().setVisible(true);

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
