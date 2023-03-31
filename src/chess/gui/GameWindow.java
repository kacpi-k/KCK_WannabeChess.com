package chess.gui;

import chess.engine.board.Board;
import chess.engine.board.BoardUtils;
import chess.engine.board.Move;
import chess.engine.board.Tile;
import chess.engine.pieces.Piece;
import chess.engine.board.MoveTransition;
import com.google.common.collect.Lists;

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

public class GameWindow
{
    // Główny frame
    public final JFrame gameFrame;
    /*public final JFrame getGameFrame() {
        return gameFrame;
    }*/



    // Panele
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
    private final BoardDirection boardDirection;
    private final boolean highlightLegalMoves;



    // Wymiary i ścieżki
    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(1200,1060);
    public static Dimension getOuterFrameDimension() {
        return OUTER_FRAME_DIMENSION;
    }
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(800,800);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension (20,20);
    private final static String defaultPieceImagesPath = "graphics/pieces/";



    // Kolory
    private final Color lightTileColor = MainMenuWindow.getLightTileColor();
    private final Color darkTileColor = MainMenuWindow.getDarkTileColor();
    private final Color lightHighlight = MainMenuWindow.getLightHighlight();
    private final Color darkHighlight = MainMenuWindow.getDarkHighlight();



    //Network Utilities, ale połączenie sieciowe nie zostało dokończone :(
    private final String SOCKET_SERVER_ADDRESS = "localhost";
    private final int PORT = 7;
    private ServerSocket listener;
    private Socket client;
    private PrintWriter printWriter;

    //private final JButton serverButton = new JButton("S");
    //private final JButton clientButton = new JButton("K");




    public GameWindow()
    {
        // Tworzenie okna gry

        this.gameFrame = new JFrame("KCK Mazowiecka Chess");
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);

        this.chessBoard = Board.createStandardBoard();

        this.gameHistoryPanel = new GameHistoryPanel();

        this.takenPiecesPanel = new TakenPiecesPanel();

        this.chessBoardPanel = new BoardPanel();

        this.moveLog = new MoveLog();

        this.boardDirection = BoardDirection.NORMAL;

        this.highlightLegalMoves = true;


        //this.serverButton.setBounds(1100, 500, 50, 50);
        //this.clientButton.setBounds(1100, 600, 50, 50);


        //this.gameFrame.add(this.serverButton);
        //this.gameFrame.add(this.clientButton);
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.chessBoardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);


        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameFrame.setResizable(false);
        MainMenuWindow.center(this.gameFrame);
        this.gameFrame.setVisible(true);



        // Dodane buttony i WindowListener dla testów połączenia sieciowego

/*        serverButton.addActionListener(new ActionListener() {
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
        });*/

    }



    // Metoda służąca do odwórcenia szachownicy. Miała zostać użyta podczas gry przez sieć, aby drugi gracz miał obróconą szachownicę.
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



    // Tworzenie szachownicy
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



    // Lista wykonanych ruchów
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
    }



    // Klasa odzwiercielająca konkretne pole na szachownicy
    private class TilePanel extends JPanel
    {
        private final int tileId;

        TilePanel(final BoardPanel boardPanel, final int tileId) {
            super(new GridBagLayout());
            this.tileId= tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);



            // Implementacja sposóbu wykonywania ruchów
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



        // Rysowanie
        public void drawTile(final Board board) {
            assignTileColor();
            assignTilePieceIcon(board);
            //highlightTile(board);
            highlightLegals(board);
            highlightKingIfChecked(board);
            highlightKingIfCheckMate(board);
            validate();
            repaint();
        }



        // Wstawianie grafik figur do odpowiednich pól na szachownicy
        private void assignTilePieceIcon(final Board board) {
            this.removeAll();
            if(board.getTile(this.tileId).isTileOccupied()) {
                try {
                    final BufferedImage image = ImageIO.read(new File(defaultPieceImagesPath + board.getTile(this.tileId).getPiece().getPieceAlliance().toString().charAt(0)+ "" +
                          board.getTile(this.tileId).getPiece().toString()+ ".png"));
                    add(new JLabel (new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



        // Oznaczenie króla przy szachu
        private void highlightKingIfChecked(final Board board) {
            if(board.currentPlayer().isInCheck()) {
                if(board.currentPlayer().getPlayerKing().getPiecePosition() == this.tileId) {
                    setBackground(Color.red);
                }
            }
        }



        // Oznaczenie króla przy szach mat
        private void highlightKingIfCheckMate(final Board board) {
            if(board.currentPlayer().isInCheckMate()) {
                if(board.currentPlayer().getPlayerKing().getPiecePosition() == this.tileId) {
                    setBackground(Color.red);
                    JOptionPane.showMessageDialog(gameFrame, "Koniec gry!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }



        // Oznaczanie
/*        private void highlightTile(final Board board) {
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
        }*/



        // Oznaczanie ruchów możliwych do wykonania
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



        // Przydzielanie poszczególnym polom na szachownicy odpowiednich kolorów
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


    // CZĘŚĆ SIECIOWA //


    // Metoda miała służyć do odebrania koordynatów pól wysłanych przez drugiego gracza i wykonanie ruchu na szachownicy drugiego gracza, ale nie się nawet sprawdzić jej poprawności
    private void movePiece(int from, int to) {
        final Move move = Move.MoveFactory.createMove(chessBoard, from, to);
        final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
        if(transition.getMoveStatus().isDone()){
            chessBoard = transition.getTransitionBoard();
        }
    }



    // Metoda miała służyć jako "pipeline" w połączeniu sieciowym, ale w obecnej postaci i po różnych przeróbkach nie działała poprawnie
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



    // Metoda startująca serwer
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



    // Metoda tworząca klienta
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