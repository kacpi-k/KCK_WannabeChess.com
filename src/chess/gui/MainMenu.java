package chess.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenu {

    private JFrame menuFrame;
    public JFrame getMenuFrame() {
        return menuFrame;
    }
    Border emptyBorder = BorderFactory.createEmptyBorder();

    private final JButton startGameButton = new JButton("Utwórz grę");
    public JButton getStartGameButton() {
        return this.startGameButton;
    }

    private final JButton searchForGameButton = new JButton("Wyszukaj grę");
    public JButton getSearchForGameButton() { return this.searchForGameButton; }

    private final JButton backButton = new JButton("Wróć");
    public JButton getBackButton() {
        return backButton;
    }

    private JButton newGame;
    public JButton getNewGame() {
        return this.newGame;
    }
    private JButton exit = new JButton("Wyjście");
    public JButton getExit() {
        return this.exit;
    }
    private JButton options;
    private final static Dimension OUTER_FRAME_DIMENSION = Table.getOuterFrameDimension();
    private ImageIcon BackgroundImage;
    private JLabel myLabel;
    private JLabel Authors;
    private JButton ThemeGreen;
    private JButton ThemePurple;
    private JButton ThemeBrown;
    private JLabel ThemeSelect = new JLabel("a");
    public MainMenu() {

        this.BackgroundImage = new ImageIcon(this.getClass().getResource("/chesswallpaper.png"));



        this.ThemeSelect = new JLabel(new ImageIcon(getClass().getResource("/ThemeSelect.png")));
        //this.ThemeSelect.setBounds((OUTER_FRAME_DIMENSION.width / 2)-350,(OUTER_FRAME_DIMENSION.height / 2) +250,700, 150);
        this.ThemeSelect.setLocation((OUTER_FRAME_DIMENSION.width / 2)-350,(OUTER_FRAME_DIMENSION.height / 2) -250);
        this.ThemeSelect.setSize(new Dimension(700,150));
        this.ThemeSelect.setVisible(false);

        this.Authors = new JLabel(new ImageIcon(getClass().getResource("/Label.png")));
        this.Authors.setSize(new Dimension(240,110));

        this.myLabel = new JLabel(BackgroundImage);
        this.myLabel.setSize(Table.getOuterFrameDimension());

        this.menuFrame = new JFrame("KCK Mazowiecka Chess");
        this.menuFrame.setLayout(new BorderLayout());
        this.menuFrame.setSize(OUTER_FRAME_DIMENSION);

        this.newGame = new JButton( new ImageIcon(getClass().getResource("/NewGameButton.png")));
        this.newGame.setBounds((OUTER_FRAME_DIMENSION.width / 2) - 200, (OUTER_FRAME_DIMENSION.height / 2)- 250 , 400, 160);
        //this.newGame.setPreferredSize(new Dimension(200,50));
        this.newGame.setBackground(Color.lightGray);
        this.newGame.setFont(new Font("Monaco", Font.BOLD, 16));
        this.newGame.setFocusable(false);
        this.newGame.setBorder(emptyBorder);
        this.newGame.setOpaque(false);

        this.exit = new JButton(new ImageIcon(getClass().getResource("/Exit.png")));
        this.exit.setBounds((OUTER_FRAME_DIMENSION.width / 2) - 200, (OUTER_FRAME_DIMENSION.height / 2) + 250, 400, 160);
        this.exit.setBackground(Color.RED);
        //this.exit.setPreferredSize(new Dimension(40,50));
        this.exit.setFocusable(false);
        this.exit.setBorder(emptyBorder);
        this.exit.setOpaque(false);

        this.startGameButton.setBounds((OUTER_FRAME_DIMENSION.width / 2) - 150, (OUTER_FRAME_DIMENSION.height / 2) - 300, 300, 50);
        this.startGameButton.setFocusable(false);
        this.startGameButton.setVisible(false);

        this.backButton.setBounds((OUTER_FRAME_DIMENSION.width / 2) - 125, (OUTER_FRAME_DIMENSION.height / 2) - 200, 250, 50);
        this.backButton.setFocusable(false);
        this.backButton.setVisible(false);

        this.searchForGameButton.setBounds((OUTER_FRAME_DIMENSION.width / 2) - 150, (OUTER_FRAME_DIMENSION.height / 2) + 150, 300, 50);
        this.searchForGameButton.setFocusable(false);
        this.searchForGameButton.setVisible(false);

        this.options = new JButton(new ImageIcon(getClass().getResource("/Options.png")));
        this.options.setBounds((OUTER_FRAME_DIMENSION.width / 2) - 200, (OUTER_FRAME_DIMENSION.height / 2)-100, 400, 160);
        this.options.setBackground(Color.RED);
        //this.exit.setPreferredSize(new Dimension(40,50));
        this.options.setFocusable(false);
        this.options.setBorder(emptyBorder);
        this.options.setOpaque(false);

        this.ThemeGreen = new JButton(new ImageIcon(getClass().getResource("/ThemeGreen.png")));
        this.ThemeGreen.setBounds(100, (OUTER_FRAME_DIMENSION.height / 2), 200, 200);
        this.ThemeGreen.setBackground(Color.RED);
        this.ThemeGreen.setFocusable(false);
        this.ThemeGreen.setBorder(emptyBorder);
        this.ThemeGreen.setOpaque(false);
        this.ThemeGreen.setVisible(false);

        this.ThemePurple = new JButton(new ImageIcon(getClass().getResource("/ThemePurple.png")));
        this.ThemePurple.setBounds(500, (OUTER_FRAME_DIMENSION.height / 2), 200, 200);
        this.ThemePurple.setBackground(Color.RED);
        this.ThemePurple.setFocusable(false);
        this.ThemePurple.setBorder(emptyBorder);
        this.ThemePurple.setOpaque(false);
        this.ThemePurple.setVisible(false);

        this.ThemeBrown = new JButton(new ImageIcon(getClass().getResource("/ThemeBrown.png")));
        this.ThemeBrown.setBounds(900, (OUTER_FRAME_DIMENSION.height / 2), 200, 200);
        this.ThemeBrown.setBackground(Color.RED);
        this.ThemeBrown.setFocusable(false);
        this.ThemeBrown.setBorder(emptyBorder);
        this.ThemeBrown.setOpaque(false);
        this.ThemeBrown.setVisible(false);

        this.menuFrame.setLayout(null);
        this.menuFrame.add(this.newGame);
        this.menuFrame.add(this.exit);
        this.menuFrame.add(this.startGameButton);
        this.menuFrame.add(this.backButton);
        this.menuFrame.add(this.searchForGameButton);
        this.menuFrame.add(this.options);
        this.menuFrame.add(this.ThemeGreen);
        this.menuFrame.add(this.ThemePurple);
        this.menuFrame.add(this.ThemeBrown);
        this.menuFrame.add(Authors);
        this.menuFrame.add(this.ThemeSelect);
        this.menuFrame.add(myLabel);

        this.menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.menuFrame.setResizable(false);
        center(this.menuFrame);
        this.menuFrame.setVisible(true);

        /*newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Table();
                menuFrame.dispose();

                getNewGame().setVisible(false);
                getExit().setVisible(false);

                getStartGameButton().setVisible(true);
                getBackButton().setVisible(true);
                getSearchForGameButton().setVisible(true);

            }
        });*/

        /*exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });*/

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Table table = new Table();
                /*try {
                    Server server = new Server();
                    server.run();
                    if(server.connectionEstablished) {
                        menuFrame.dispose();
                        Table table = new Table();
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }*/

            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getStartGameButton().setVisible(false);
                getBackButton().setVisible(false);
                getSearchForGameButton().setVisible(false);

                getNewGame().setVisible(true);
                getExit().setVisible(true);

            }
        });
        newGame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Table();
                menuFrame.dispose();

                getNewGame().setVisible(false);
                getExit().setVisible(false);

                getStartGameButton().setVisible(true);
                getBackButton().setVisible(true);
                getSearchForGameButton().setVisible(true);

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                //newGame = new JButton(new ImageIcon(this.getClass().getResource("/NewGameButton2.png")));
                newGame.setIcon(new ImageIcon(this.getClass().getResource("/NewGameButton2.png")));
                newGame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                newGame.setIcon(new ImageIcon(this.getClass().getResource("/NewGameButton.png")));
            }
        });
        exit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                exit.setIcon(new ImageIcon(this.getClass().getResource("/Exit2.png")));
                exit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exit.setIcon(new ImageIcon(this.getClass().getResource("/Exit.png")));
            }
        });
        Authors.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {


            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {


            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Authors.setIcon(new ImageIcon(getClass().getResource("/Label2.png")));
                Authors.setSize(new Dimension(400,190));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                Authors.setIcon(new ImageIcon(getClass().getResource("/Label.png")));
                Authors.setSize(new Dimension(240,110));

            }
        });
        options.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                newGame.setVisible(false);
                options.setVisible(false);
                exit.setVisible(false);
                Authors.setVisible(false);
                ThemeGreen.setVisible(true);
                ThemePurple.setVisible(true);
                ThemeBrown.setVisible(true);
                ThemeSelect.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                options.setIcon(new ImageIcon(this.getClass().getResource("/Options2.png")));
                options.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                options.setIcon(new ImageIcon(this.getClass().getResource("/Options.png")));

            }
        });

        ThemeGreen.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ThemeGreen.setIcon(new ImageIcon(getClass().getResource("/ThemeGreen2.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ThemeGreen.setIcon(new ImageIcon(getClass().getResource("/ThemeGreen.png")));
            }
        });
        ThemePurple.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ThemePurple.setIcon(new ImageIcon(getClass().getResource("/ThemePurple2.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ThemePurple.setIcon(new ImageIcon(getClass().getResource("/ThemePurple.png")));
            }
        });
        ThemeBrown.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ThemeBrown.setIcon(new ImageIcon(getClass().getResource("/ThemeBrown2.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ThemeBrown.setIcon(new ImageIcon(getClass().getResource("/ThemeBrown.png")));
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
