package com.hybridev.dice;

public class DiceTerrain extends DicePiece{


    DiceTerrain(String name, int col, int row, int resID, DiceEnums.pieceType type) {
        super(name, col, row, resID, type);

        type = DiceEnums.pieceType.Terrain;
        //dice = new Dice(DiceEnums.pieceType.Terrain);
    }


}
