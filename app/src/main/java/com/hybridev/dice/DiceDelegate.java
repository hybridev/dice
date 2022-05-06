package com.hybridev.dice;

public interface DiceDelegate {

    DicePiece pieceAt(int col, int row);
    void showSelectedPiece(DicePiece piece);
    boolean isFixedPiece();
    boolean movePiece(int toCol, int toRow);
    DicePiece battlePieceAt(int col, int row);
    void showSelectedBattlePiece(DicePiece piece);
    boolean moveBattlePiece(int toCol, int toRow);
}
