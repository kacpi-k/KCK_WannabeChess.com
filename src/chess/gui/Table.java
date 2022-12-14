package chess.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import jawa.awt.event.ActionEvent;
public class Table
{
    int tab(];


    private final JFrame gameFrame;
    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
    public Table()
    {
        this.gameFrame = new JFrame("Jchess");
        final JMenuBar tableMenuBar = populateMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setVisible(true);
    }
    private JMenuBar createTableMenuBar()
    {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }
    private JMenu createFileMenu()
    {
        final JMenu fileMenu = new JMenu ("File");

        final JMenuItem openPGN = new JMenuItem ("Load PGN File");
        openPGN.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.printIn("open up that PGN file!");
            }
        });
        fileMenu.add(openPGN);
        return fileMenu;
    }
    private class BoardPanel extends JPanel
    {
        final List<TilePanel> boardTiles;

        BoardPanel()
        {
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for(int i = 0; i < BoardUtils.NUM_TILES; i++)
            {

            }
        }

    }
    private class TilePanel extends JPanel
    {

    }
}