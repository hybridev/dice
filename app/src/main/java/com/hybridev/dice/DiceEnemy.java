package com.hybridev.dice;

public class DiceEnemy extends DicePiece{

    DiceEnemy(String name, int col, int row, int resID, DiceEnums.pieceType type) {
        super(name, col, row, resID, type);

        this.type = DiceEnums.pieceType.Enemy;
        dice = new Dice(DiceEnums.pieceType.Enemy);
    }

    DiceEnemy(String name, int resID)
    {
        super(name, resID);

        this.name = name;
        this.resID = resID;

        type = DiceEnums.pieceType.Enemy;
        dice = new Dice(DiceEnums.pieceType.Enemy);

    }
}
