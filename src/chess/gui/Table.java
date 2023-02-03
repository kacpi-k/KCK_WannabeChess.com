package chess.gui;

import chess.engine.board.Board;
import chess.engine.board.BoardUtils;
import chess.engine.board.Move;
import chess.engine.board.Tile;
import chess.engine.pieces.Piece;
import chess.engine.player.MoveTransition;
import com.google.common.collect.Lists;
import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table
{

    private final String SOCKET_SERVER_ADDRESS = "localhost";
    private final int PORT = 7;
    private ServerSocket listener;
    private Socket client;
    private PrintWriter printWriter;


    public final JFrame gameFrame;
    public final JFrame getGameFrame() {
        return gameFrame;
    }
    private final JButton serverButton = new JButton("S");

    private final JButton clientButton = new JButton("K");



    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final BoardPanel chessBoardPanel;
    private final MoveLog moveLog;
    private Board chessBoard;
    public Board getChessBoard() {
        return chessBoard;
    }

    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;
    private BoardDirection boardDirection;
    private boolean highlightLegalMoves;
    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(1200,1060);

    public static Dimension getOuterFrameDimension() {
        return OUTER_FRAME_DIMENSION;
    }

    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(800,800);
    public static Dimension getBoardPanelDimension() {
        return BOARD_PANEL_DIMENSION;
    }
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension (20,20);
    private final static String defaultPieceImagesPath = "graphics/pieces/";

    private final Color lightTileColor = MainMenu.getLightTileColor();
    private final Color darkTileColor = MainMenu.getDarkTileColor();
    private final Color lightHighlight = MainMenu.getLightHighlight();
    private final Color darkHighlight = MainMenu.getDarkHighlight();



    public Table()
    {
        this.gameFrame = new JFrame("KCK Mazowiecka Chess");
        this.gameFrame.setLayout(new BorderLayout());
        //gameFrame.setUndecorated(true);
        //ameFrame.setShape(new Ellipse2D.Double(100,100, gameFrame.getWidth(),gameFrame.getHeight()));
        //final JMenuBar tableMenuBar = createTableMenuBar();
        //this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.chessBoard = Board.createStandardBoard();
        this.gameHistoryPanel = new GameHistoryPanel();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.chessBoardPanel = new BoardPanel();
        this.moveLog = new MoveLog();
        this.boardDirection = BoardDirection.NORMAL;
        this.highlightLegalMoves = true;
        this.serverButton.setBounds(1100, 500, 50, 50);
        this.clientButton.setBounds(1100, 600, 50, 50);
        //this.gameFrame.add(this.serverButton);
        //this.gameFrame.add(this.clientButton);
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.chessBoardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);

        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameFrame.setResizable(false);
        MainMenu.center(this.gameFrame);
        this.gameFrame.setVisible(true);

        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverButton.setEnabled(false);
                clientButton.setEnabled(false);
                gameFrame.setTitle("Serwer");
                runServer();
                JOptionPane.showMessageDialog(gameFrame, "Serwer działa na porcie " + PORT);
            }
        });

        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverButton.setEnabled(false);
                clientButton.setEnabled(false);
                gameFrame.setTitle("Klient");
                runClient();
                JOptionPane.showMessageDialog(gameFrame, "Połączono z serwerem na porcie " + PORT);
            }
        });

        gameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (printWriter != null) printWriter.close();
                try {
                    if (listener != null) listener.close();
                    if (client != null) client.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }
    private JMenuBar createTableMenuBar()
    {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        return tableMenuBar;
    }
    private JMenu createFileMenu()
    {
        final JMenu fileMenu = new JMenu ("Opcje");

        final JMenuItem openPGN = new JMenuItem ("Załaduj plik PGN");
        openPGN.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("No załaduj tego PGN'a :)!");
            }
        });
        fileMenu.add(openPGN);
            final JMenuItem exitMenuItem = new JMenuItem("Zakończ");
            exitMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){

                    System.exit(0);
                }
            });
            fileMenu.add(exitMenuItem);
        return fileMenu;
    }
    private JMenu createPreferencesMenu(){
        final JMenu preferencesMenu =  new JMenu("Preferencje");
        final JMenuItem flipBoardMenuItem = new JMenuItem("Odwróć szachownice");
        flipBoardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                boardDirection = boardDirection.opposite();
                chessBoardPanel.drawBoard(chessBoard);
            }
        });
        preferencesMenu.add(flipBoardMenuItem);
        preferencesMenu.addSeparator();
        final JCheckBoxMenuItem legalMoveHighlighterCheckbox = new JCheckBoxMenuItem("Podpowiadaj ruchy", false);
        legalMoveHighlighterCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                highlightLegalMoves = legalMoveHighlighterCheckbox.isSelected();

            }
        });
        preferencesMenu.add(legalMoveHighlighterCheckbox);
        return preferencesMenu;
    }
    public enum BoardDirection {
        NORMAL {
            @Override
            List<TilePanel> traverse (final List <TilePanel> boardTiles){
                return boardTiles;
            }
            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            List<TilePanel> traverse (final List <TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }
            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };

        abstract List<TilePanel> traverse (final List<TilePanel> boardTiles);
        abstract BoardDirection opposite();

    }
    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;

        BoardPanel() {
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for(int i = 0; i < BoardUtils.NUM_TILES; i++)
            {
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }
        public void drawBoard(final Board board) {
            removeAll();
            for(final TilePanel tilePanel : boardDirection.traverse(boardTiles)){
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }

    }

    public static class MoveLog {
        private final List<Move> moves;
        MoveLog(){
            this.moves = new ArrayList<>();
        }
        public List<Move> getMoves(){
            return this.moves;
        }
        public void addMove(final Move move) {
            this.moves.add(move);
        }
        public int size() {
            return this.moves.size();
        }
        public void clear() {
            this.moves.clear();
        }
        public Move removeMove (int index) {
            return this.moves.remove(index);
        }
        public boolean removeMove (final Move move) {
            return this.moves.remove(move);
        }
    }

    private class TilePanel extends JPanel
    {
        private final int tileId;

        TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId= tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {

                    if(BoardUtils.isEndGame(getChessBoard())) {
                        return;
                    }

                    if(isRightMouseButton(e)) {
                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;
                        System.out.println("ppm");
                    } else if(isLeftMouseButton(e)) {
                        if(sourceTile == null) {
                            sourceTile = chessBoard.getTile(tileId);
                            humanMovedPiece = sourceTile.getPiece();
                            if(humanMovedPiece == null) {
                                sourceTile = null;
                            }
                        } else {
                            destinationTile = chessBoard.getTile(tileId);
                            final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                            final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                            if(transition.getMoveStatus().isDone()){
                                chessBoard = transition.getTransitionBoard();

                                moveLog.addMove(move);
                            }
                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;

                        }
                        SwingUtilities.invokeLater(new Runnable(){
                            @Override
                            public void run(){


                                gameHistoryPanel.redo(chessBoard, moveLog);
                                takenPiecesPanel.redo(moveLog);
                                boardPanel.drawBoard(chessBoard);
                            }
                        });
                    }


                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }
            });
            validate();
        }

        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            highlightTile(board);
            highlightLegals(board);
            highlightKingIfChecked(board);
            highlightKingIfCheckMate(board);
            validate();
            repaint();
        }
        private void assignTilePieceIcon(final Board board) {   //Przydzielanie ikony do poszczególnych bierek
            this.removeAll();
            if(board.getTile(this.tileId).isTileOccupied()) {
                try {
                    final BufferedImage image = ImageIO.read(new File(defaultPieceImagesPath + board.getTile(this.tileId).getPiece().getPieceAlliance().toString().substring(0,1)+ "" +
                            board.getTile(this.tileId).getPiece().toString()+ ".png"));
                    add(new JLabel (new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void highlightKingIfChecked(final Board board) {
            if(board.currentPlayer().isInCheck()) {
                if(board.currentPlayer().getPlayerKing().getPiecePosition() == this.tileId) {
                    setBackground(Color.red);
                }
            }
        }

        private void highlightKingIfCheckMate(final Board board) {
            if(board.currentPlayer().isInCheckMate()) {
                if(board.currentPlayer().getPlayerKing().getPiecePosition() == this.tileId) {
                    setBackground(Color.red);
                    JOptionPane.showMessageDialog(gameFrame, "Koniec gry!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        private void highlightTile(final Board board) {
            if(humanMovedPiece != null &&
                    humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance() &&
                    humanMovedPiece.getPiecePosition() == this.tileId) {
                if(BoardUtils.EIGHTH_ROW[this.tileId] ||
                        BoardUtils.SIXTH_ROW[this.tileId] ||
                        BoardUtils.FOURTH_ROW[this.tileId] ||
                        BoardUtils.SECOND_ROW[this.tileId]) {setBackground(this.tileId % 2 == 0 ? lightHighlight : darkHighlight);
                }   else if(BoardUtils.SEVENTH_ROW[this.tileId] ||
                        BoardUtils.FIFTH_ROW[this.tileId] ||
                        BoardUtils.THIRD_ROW[this.tileId] ||
                        BoardUtils.FIRST_ROW[this.tileId]) {setBackground(this.tileId % 2 != 0 ? lightHighlight : darkHighlight);

                }


            }
        }

        private void highlightLegals(final Board board) {
            if(highlightLegalMoves){
                for(final Move move : pieceLegalMoves(board)) {
                    if(move.getDestinationCoordinate() == this.tileId) {
                        try {if(BoardUtils.EIGHTH_ROW[this.tileId] ||
                                BoardUtils.SIXTH_ROW[this.tileId] ||
                                BoardUtils.FOURTH_ROW[this.tileId] ||
                                BoardUtils.SECOND_ROW[this.tileId]) {setBackground(this.tileId % 2 == 0 ? lightHighlight : darkHighlight);
                        }   else if(BoardUtils.SEVENTH_ROW[this.tileId] ||
                                BoardUtils.FIFTH_ROW[this.tileId] ||
                                BoardUtils.THIRD_ROW[this.tileId] ||
                                BoardUtils.FIRST_ROW[this.tileId]) {setBackground(this.tileId % 2 != 0 ? lightHighlight : darkHighlight);

                        }
                            //add(new JLabel(new ImageIcon(ImageIO.read(new File("misc/green_dot.png")))));

                            //setBackground(Color.green);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMoves(final Board board) {
            if(humanMovedPiece != null && humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance()) {
                return humanMovedPiece.calculateLegalMoves(board);
            }
            return Collections.emptyList();
        }

        private void assignTileColor() {
            if(BoardUtils.EIGHTH_ROW[this.tileId] ||
                BoardUtils.SIXTH_ROW[this.tileId] ||
                BoardUtils.FOURTH_ROW[this.tileId] ||
                BoardUtils.SECOND_ROW[this.tileId]) {setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
                }   else if(BoardUtils.SEVENTH_ROW[this.tileId] ||
                    BoardUtils.FIFTH_ROW[this.tileId] ||
                    BoardUtils.THIRD_ROW[this.tileId] ||
                    BoardUtils.FIRST_ROW[this.tileId]) {setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);

                }
        }
    }

    private void movePiece(int from, int to) {
        final Move move = Move.MoveFactory.createMove(chessBoard, from, to);
        final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
        if(transition.getMoveStatus().isDone()){
            chessBoard = transition.getTransitionBoard();
        }

    }

    private void receiveMove(Scanner scanner) {
        System.out.println("jestem w receiveMove");
        while (scanner.hasNextLine()) {
            System.out.println("jestem w petli");
            var moveStr = scanner.nextLine();
            System.out.println("Otrzymany ruch: " + moveStr);
            var moveStrArr = moveStr.split(",");
            System.out.println("ruch z: " + moveStrArr[0]);
            var from = Integer.parseInt(moveStrArr[0]);
            System.out.println("ruch do: " + moveStrArr[1]);
            var to = Integer.parseInt(moveStrArr[1]);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    movePiece(from, to);
                    chessBoardPanel.repaint();
                }
            });
        }
    }

    private void runServer() {
        Executors.newFixedThreadPool(1).execute(new Runnable() {
            @Override
            public void run() {
                try {
                    listener = new ServerSocket(PORT);
                    System.out.println("Serwer uruchomiony na porcie " + 7);
                    client = listener.accept();
                    System.out.println(client.getInetAddress().getHostName() + " połączył się z serwerem");
                    printWriter = new PrintWriter(client.getOutputStream(), true);
                    var scanner = new Scanner(client.getInputStream());
                    receiveMove(scanner);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void runClient() {
        try {
            client = new Socket(SOCKET_SERVER_ADDRESS, PORT);
            System.out.println("Klient połączył się z portem " + 7);
            var scanner = new Scanner(client.getInputStream());
            printWriter = new PrintWriter(client.getOutputStream(), true);

            Executors.newFixedThreadPool(1).execute(new Runnable() {
                @Override
                public void run() {
                    receiveMove(scanner);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}