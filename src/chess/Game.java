package chess;

import chess.engine.board.Board;
import chess.gui.Table;

public class Game {
    public static void main(String[] args){
        Board board = Board.createStandardBoard();
        System.out.println(board);

        Table table = new Table();
    }
}
