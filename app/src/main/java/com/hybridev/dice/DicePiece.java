package com.hybridev.dice;

import java.util.ArrayList;

public class DicePiece {
    String name;
    int col;
    int row;
    int resID;
    DiceEnums.pieceType type;
    Dice dice;

    int battlePieceNum = 1;
    ArrayList battlePieceList;

    DicePiece(String name, int col, int row, int resID, DiceEnums.pieceType type)
    {
        this.name = name;
        this.col = col;
        this.row = row;
        this.resID = resID;
        this.type = type;

        this.dice = new Dice(type);

        createBattlePiece(type);
    }

    public DicePiece(String name, int resID)
    {

    }

    void createBattlePiece(DiceEnums.pieceType type)
    {
        switch (type){
            case Player:
                battlePieceList = new ArrayList<DicePlayer>();
                for(int i = 0; i < battlePieceNum; i++)
                {
                    battlePieceList.add(new DicePlayer(name, resID));
                }
                break;
            case Enemy:
                battlePieceNum = 2;
                battlePieceList = new ArrayList<DiceEnemy>();
                for(int i = 0; i < battlePieceNum; i++)
                {
                    DiceEnemy enemy = new DiceEnemy(name, resID);
                    enemy.col = 1+i; enemy.row = 0;
                    battlePieceList.add(enemy);
                }
                break;
            case Terrain:
                //battlePieceList = new ArrayList<DicePlayer>();
                break;
            case Event:
                //battlePieceList = new ArrayList<DicePlayer>();
                break;
        }
    }
}
