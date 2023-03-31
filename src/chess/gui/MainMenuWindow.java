package chess.gui;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class MainMenuWindow {

    // Główny frame menu
    private final JFrame menuFrame;


    Border emptyBorder = BorderFactory.createEmptyBorder();

    // Przyciski
    private final JButton newGameButton;
    private final JButton optionsButton;
    private final JButton exitButton;
    private final JButton themeGreenButton;
    private final JButton themePurpleButton;
    private final JButton themeBrownButton;



    // Rozmiar okna
    private final static Dimension OUTER_FRAME_DIMENSION = GameWindow.getOuterFrameDimension();


    private final JLabel authors;
    private final JLabel themeSelect;




    private static Color lightTileColor = Color.decode("#EBECD0");
    public static Color getLightTileColor() {
        return lightTileColor;
    }
    private static Color darkTileColor = Color.decode("#779556");
    public static Color getDarkTileColor() {
        return darkTileColor;
    }

    private static Color lightHighlight = Color.decode("#F6F669");
    public static Color getLightHighlight() {
        return lightHighlight;
    }
    private static Color darkHighlight = Color.decode("#BACA2B");
    public static Color getDarkHighlight() {
        return darkHighlight;
    }




    public MainMenuWindow() {

        // Background main menu
        ImageIcon backgroundImagePath = new ImageIcon(Objects.requireNonNull(getClass().getResource("/chesswallpaper.png")));
        final JLabel backgroundImage = new JLabel(backgroundImagePath);
        backgroundImage.setSize(GameWindow.getOuterFrameDimension());


        this.menuFrame = new JFrame("KCK Mazowiecka Chess");
        this.menuFrame.setLayout(new BorderLayout());
        this.menuFrame.setSize(OUTER_FRAME_DIMENSION);


        this.newGameButton = new JButton( new ImageIcon(Objects.requireNonNull(getClass().getResource( "/NewGameButton.png"))));
        this.newGameButton.setBounds((OUTER_FRAME_DIMENSION.width / 2) - 200, (OUTER_FRAME_DIMENSION.height / 2)- 250 , 400, 160);
        this.newGameButton.setBackground(Color.lightGray);
        this.newGameButton.setFont(new Font("Monaco", Font.BOLD, 16));
        this.newGameButton.setFocusable(false);
        this.newGameButton.setBorder(emptyBorder);
        this.newGameButton.setOpaque(false);

        this.exitButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Exit.png"))));
        this.exitButton.setBounds((OUTER_FRAME_DIMENSION.width / 2) - 200, (OUTER_FRAME_DIMENSION.height / 2) + 250, 400, 160);
        this.exitButton.setBackground(Color.RED);
        this.exitButton.setFocusable(false);
        this.exitButton.setBorder(emptyBorder);
        this.exitButton.setOpaque(false);

        this.optionsButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Options.png"))));
        this.optionsButton.setBounds((OUTER_FRAME_DIMENSION.width / 2) - 200, (OUTER_FRAME_DIMENSION.height / 2)-100, 400, 160);
        this.optionsButton.setBackground(Color.RED);
        this.optionsButton.setFocusable(false);
        this.optionsButton.setBorder(emptyBorder);
        this.optionsButton.setOpaque(false);

        this.authors = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Label.png"))));
        this.authors.setSize(new Dimension(240,110));

        this.themeGreenButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/ThemeGreen.png"))));
        this.themeGreenButton.setBounds(100, (OUTER_FRAME_DIMENSION.height / 2), 200, 200);
        this.themeGreenButton.setBackground(Color.RED);
        this.themeGreenButton.setFocusable(false);
        this.themeGreenButton.setBorder(emptyBorder);
        this.themeGreenButton.setOpaque(false);
        this.themeGreenButton.setVisible(false);

        this.themePurpleButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/ThemePurple.png"))));
        this.themePurpleButton.setBounds(500, (OUTER_FRAME_DIMENSION.height / 2), 200, 200);
        this.themePurpleButton.setBackground(Color.RED);
        this.themePurpleButton.setFocusable(false);
        this.themePurpleButton.setBorder(emptyBorder);
        this.themePurpleButton.setOpaque(false);
        this.themePurpleButton.setVisible(false);

        this.themeBrownButton = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/ThemeBrown.png"))));
        this.themeBrownButton.setBounds(900, (OUTER_FRAME_DIMENSION.height / 2), 200, 200);
        this.themeBrownButton.setBackground(Color.RED);
        this.themeBrownButton.setFocusable(false);
        this.themeBrownButton.setBorder(emptyBorder);
        this.themeBrownButton.setOpaque(false);
        this.themeBrownButton.setVisible(false);

        this.themeSelect = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/ThemeSelect.png"))));
        this.themeSelect.setLocation((OUTER_FRAME_DIMENSION.width / 2)-350,(OUTER_FRAME_DIMENSION.height / 2) -250);
        this.themeSelect.setSize(new Dimension(700,150));
        this.themeSelect.setVisible(false);


        this.menuFrame.setLayout(null);
        this.menuFrame.add(this.newGameButton);
        this.menuFrame.add(this.exitButton);
        this.menuFrame.add(this.optionsButton);
        this.menuFrame.add(this.themeGreenButton);
        this.menuFrame.add(this.themePurpleButton);
        this.menuFrame.add(this.themeBrownButton);
        this.menuFrame.add(authors);
        this.menuFrame.add(this.themeSelect);
        this.menuFrame.add(backgroundImage);

        this.menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.menuFrame.setResizable(false);
        center(this.menuFrame);
        this.menuFrame.setVisible(true);


        newGameButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new GameWindow();
                menuFrame.dispose();
                newGameButton.setVisible(false);
                exitButton.setVisible(false);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                newGameButton.setIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/NewGameButton2.png"))));
                newGameButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                newGameButton.setIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/NewGameButton.png"))));
            }
        });


        exitButton.addMouseListener(new MouseListener() {
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
                exitButton.setIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Exit2.png"))));
                exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Exit.png"))));
            }
        });

        authors.addMouseListener(new MouseListener() {
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
                authors.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Label2.png"))));
                authors.setSize(new Dimension(400,190));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                authors.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Label.png"))));
                authors.setSize(new Dimension(240,110));

            }
        });


        optionsButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                newGameButton.setVisible(false);
                optionsButton.setVisible(false);
                exitButton.setVisible(false);
                authors.setVisible(false);
                themeGreenButton.setVisible(true);
                themePurpleButton.setVisible(true);
                themeBrownButton.setVisible(true);
                themeSelect.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                optionsButton.setIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Options2.png"))));
                optionsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                optionsButton.setIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/Options.png"))));
            }
        });


        themeGreenButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lightTileColor = Color.decode("#EBECD0");
                darkTileColor = Color.decode("#779556");
                lightHighlight = Color.decode("#F6F669");
                darkHighlight = Color.decode("#BACA2B");

                themeGreenButton.setVisible(false);
                themePurpleButton.setVisible(false);
                themeBrownButton.setVisible(false);
                themeSelect.setVisible(false);
                newGameButton.setVisible(true);
                optionsButton.setVisible(true);
                exitButton.setVisible(true);
                authors.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                themeGreenButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/ThemeGreen2.png"))));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                themeGreenButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/ThemeGreen.png"))));
            }
        });
        themePurpleButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lightTileColor = Color.decode("#EBECD0");
                darkTileColor = Color.decode("#985291");
                lightHighlight = Color.decode("#E5C8F2");
                darkHighlight = Color.decode("#D59CEF");

                themeGreenButton.setVisible(false);
                themePurpleButton.setVisible(false);
                themeBrownButton.setVisible(false);
                themeSelect.setVisible(false);
                newGameButton.setVisible(true);
                optionsButton.setVisible(true);
                exitButton.setVisible(true);
                authors.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                themePurpleButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/ThemePurple2.png"))));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                themePurpleButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/ThemePurple.png"))));
            }
        });

        themeBrownButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lightTileColor = Color.decode("#EBECD0");
                darkTileColor = Color.decode("#986B52");
                lightHighlight = Color.decode("#F6C5A8");
                darkHighlight = Color.decode("#C9A67E");

                themeGreenButton.setVisible(false);
                themePurpleButton.setVisible(false);
                themeBrownButton.setVisible(false);
                themeSelect.setVisible(false);
                newGameButton.setVisible(true);
                optionsButton.setVisible(true);
                exitButton.setVisible(true);
                authors.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                themeBrownButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/ThemeBrown2.png"))));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                themeBrownButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/ThemeBrown.png"))));
            }
        });

    }


    // Metoda, dzięki której generowane okno programu pojawia się na środku ekranu
    public static void center(final JFrame frame) {
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final int w = frame.getSize().width;
        final int h = frame.getSize().height;
        final int x = (dim.width - w) / 2;
        final int y = ((dim.height - h) / 2)-12;
        frame.setLocation(x, y);
    }
}
