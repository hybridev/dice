package com.hybridev.dice;



import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DiceModel {

    ArrayList<DicePiece> pieceList = new ArrayList<DicePiece>();

    DicePiece selectedPiece;
    DicePiece fixedPiece;
    DicePiece targetEnemy;

    DicePiece fixedBattlePiece;

    int[][] movableRange = {{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1}};


    DiceModel()
    {
        reset();
    }

    void reset()
    {
        pieceList.clear();
        pieceList.add(new DicePiece("test_player",0,0, R.drawable.testssam_ccexpress, DiceEnums.pieceType.Player));
        pieceList.add(new DicePiece("test_enemy",1,0, R.drawable.testrpg, DiceEnums.pieceType.Enemy));
        pieceList.add(new DicePiece("test_mountain1",0,5, R.drawable.mountain1, DiceEnums.pieceType.Terrain));
        pieceList.add(new DicePiece("test_mountain2",1,6, R.drawable.mountain2, DiceEnums.pieceType.Terrain));
        pieceList.add(new DicePiece("test_mountain3",2,6, R.drawable.mountain2, DiceEnums.pieceType.Terrain));
        pieceList.add(new DicePiece("test_mountain4",3,6, R.drawable.mountain2, DiceEnums.pieceType.Terrain));
        pieceList.add(new DicePiece("test_mountain5",4,3, R.drawable.mountain1, DiceEnums.pieceType.Terrain));
        pieceList.add(new DicePiece("test_mountain6",5,1, R.drawable.mountain1, DiceEnums.pieceType.Terrain));
        pieceList.add(new DicePiece("test_mountain7",5,7, R.drawable.mountain2, DiceEnums.pieceType.Terrain));
        pieceList.add(new DicePiece("test_mountain8",6,2, R.drawable.mountain1, DiceEnums.pieceType.Terrain));
        pieceList.add(new DicePiece("test_mountain9",6,3, R.drawable.mountain2, DiceEnums.pieceType.Terrain));
        pieceList.add(new DicePiece("test_mountain10",7,4, R.drawable.mountain1, DiceEnums.pieceType.Terrain));
        pieceList.add(new DicePiece("test_mountain11",4,5, R.drawable.mountain1, DiceEnums.pieceType.Terrain));
        pieceList.add(new DicePiece("test_mountain12",0,7, R.drawable.mountain1, DiceEnums.pieceType.Terrain));
    }

    DicePiece pieceAt(int col, int row)
    {
        for(int i = 0; i < pieceList.size(); i++)
        {
            if((col == pieceList.get(i).col) && (row == pieceList.get(i).row))
            {
                return pieceList.get(i);
            }
        }
        return null;
    }

    DicePiece battlePieceAt(int col, int row)
    {
        //TODO 같은 위치에 말이 없다는걸 보장해야함
        DicePiece retPiece = null;
        for(int i = 0; i < fixedPiece.battlePieceList.size(); i++)
        {
            DicePiece player = (DicePlayer) fixedPiece.battlePieceList.get(i);
            if((col == player.col) && (row == player.row))
            {
                retPiece =  player;
            }
        }
        for(int i = 0; i < targetEnemy.battlePieceList.size(); i++)
        {
            DicePiece enemy = (DiceEnemy) targetEnemy.battlePieceList.get(i);
            if((col == enemy.col) && (row == enemy.row))
            {
                retPiece = enemy;
            }
        }
        return retPiece;
    }

    void fixPiece(DicePiece selectedPiece)
    {
        fixedPiece = selectedPiece;
        setMovableRange(fixedPiece);
    }

    void unfixPiece()
    {
        fixedPiece = null;
        setMovableRange(fixedPiece);
    }

    void fixBattlePiece(DicePiece selectedPiece)
    {
        fixedBattlePiece = selectedPiece;
        setMovableRange(fixedBattlePiece);
    }

    void unfixBattlePiece()
    {
        fixedBattlePiece = null;
        setMovableRange(fixedBattlePiece);
    }

    DiceEnums.pieceType movePiece(int toCol, int toRow)
    {

        DiceEnums.pieceType type = DiceEnums.pieceType.Blank;

        DicePiece piece = pieceAt(toCol, toRow);
        if(piece != null)
        {
            switch (piece.type){
                case Player:
                    type = DiceEnums.pieceType.Player;
                    break;
                case Enemy:
                    type = DiceEnums.pieceType.Enemy;
                    setTargetEnemy(piece);
                    break;
                case Terrain:
                    type = DiceEnums.pieceType.Terrain;
                    break;
                case Event:
                    type = DiceEnums.pieceType.Event;
                    break;
            }
        }
        else
        {
            int col, row;
            for(int ArrayRow = 0; ArrayRow <= 7; ArrayRow++)
            {
                col = movableRange[ArrayRow][0];
                row = movableRange[ArrayRow][1];

                if(col == toCol && row == toRow)
                {
                    DicePiece movingPiece = fixedPiece;
                    fixedPiece.col = toCol;
                    fixedPiece.row = toRow;
                    pieceList.remove(movingPiece);
                    pieceList.add(0,fixedPiece);
                    setMovableRange(fixedPiece);
                    type = DiceEnums.pieceType.Blank;

                }
            }
        }
        return type;
    }


    DiceEnums.pieceType moveBattlePiece(int toCol, int toRow)
    {

        DiceEnums.pieceType type = DiceEnums.pieceType.Blank;

        DicePiece piece = battlePieceAt(toCol, toRow);
        if(piece != null)
        {
            switch (piece.type){
                case Player:
                    type = DiceEnums.pieceType.Player;
                    break;
                case Enemy:
                    type = DiceEnums.pieceType.Enemy;
                    //TODO ATTACK()
                    break;
                case Terrain:
                    type = DiceEnums.pieceType.Terrain;
                    break;
                case Event:
                    type = DiceEnums.pieceType.Event;
                    break;
            }
        }
        else
        {
            int col, row;
            for(int ArrayRow = 0; ArrayRow <= 7; ArrayRow++)
            {
                col = movableRange[ArrayRow][0];
                row = movableRange[ArrayRow][1];

                if(col == toCol && row == toRow)
                {
                    DicePiece movingPiece = fixedBattlePiece;
                    fixedBattlePiece.col = toCol;
                    fixedBattlePiece.row = toRow;
                    fixedPiece.battlePieceList.remove(movingPiece);
                    fixedPiece.battlePieceList.add(0,fixedBattlePiece);
                    setMovableRange(fixedBattlePiece);
                    type = DiceEnums.pieceType.Blank;

                }
            }
        }
        return type;
    }

    void setMovableRange(DicePiece fixedPiece)
    {
        if(fixedPiece == null)
        {
            for(int row = 0; row <= 7; row++)
            {
                for(int col = 0; col <= 1; col++)
                {
                    movableRange[row][col] = -1;
                }
            }
        }
        else
        {
            int fixedCol = fixedPiece.col;
            int fixedRow = fixedPiece.row;
            movableRange[0][0] = fixedCol - 1;    movableRange[0][1] = fixedRow + 1;
            movableRange[1][0] = fixedCol;        movableRange[1][1] = fixedRow + 1;
            movableRange[2][0] = fixedCol + 1;    movableRange[2][1] = fixedRow + 1;
            movableRange[3][0] = fixedCol - 1;    movableRange[3][1] = fixedRow;
            movableRange[4][0] = fixedCol + 1;    movableRange[4][1] = fixedRow;
            movableRange[5][0] = fixedCol - 1;    movableRange[5][1] = fixedRow - 1;
            movableRange[6][0] = fixedCol;        movableRange[6][1] = fixedRow - 1;
            movableRange[7][0] = fixedCol + 1;    movableRange[7][1] = fixedRow - 1;
        }
    }

    DicePiece getFixedPiece()
    {
        return fixedPiece;
    }

    int[][] getMovableRange()
    {
        return movableRange;
    }

    void setTargetEnemy(DicePiece piece)
    {
        targetEnemy = piece;
    }

    DicePiece getEnemyInfo()
    {
        return targetEnemy;
    }


}
