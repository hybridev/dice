package com.hybridev.dice;

public class DicePlayer extends DicePiece{

    DicePlayer(String name, int col, int row, int resID, DiceEnums.pieceType type) {
        super(name, col, row, resID, type);

        //this.type = DiceEnums.pieceType.Player;
        //dice = new Dice(DiceEnums.pieceType.Player);

    }

    DicePlayer(String name, int resID)
    {
        super(name, resID);

        this.name = name;
        this.resID = resID;

        type = DiceEnums.pieceType.Player;
        dice = new Dice(DiceEnums.pieceType.Player);

        col = 0;
        row = 0;

    }

}
