package com.hybridev.dice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DiceControl extends AppCompatActivity implements DiceDelegate {

    //gittest

    DiceModel diceModel = new DiceModel();
    DiceView diceView;
    DiceBattleView diceBattleView;

    Dice selectedDice;

    Button bt_roll_dice;
    TextView tv_show_ap;
    TextView tv_piece_info;

    int pipSum;
    int battlePipSum;
    boolean isBattle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_control);

        diceView = findViewById(R.id.dice_view);
        diceView.diceDelegate = this;

        diceBattleView = findViewById(R.id.dice_battle_view);
        diceBattleView.diceDelegate = this;
        diceBattleView.setVisibility(View.GONE);

        tv_piece_info = findViewById(R.id.piece_info);
        tv_piece_info.setText("");
        bt_roll_dice = findViewById(R.id.bt_dice_control_roll_dice);
        bt_roll_dice.setVisibility(View.INVISIBLE);
        tv_show_ap = findViewById(R.id.temp_roll);
        tv_show_ap.setVisibility(View.INVISIBLE);

        bt_roll_dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isBattle)
                {
                    battlePipSum = diceModel.selectedPiece.dice.roll();
                    tv_show_ap.setVisibility(View.VISIBLE);
                    tv_show_ap.setText(Integer.toString(battlePipSum));
                    diceModel.fixBattlePiece(diceModel.selectedPiece);
                    diceBattleView.setMovableRange(diceModel.getMovableRange());
                    //diceBattleView.

                }
                else
                {
                    pipSum = diceModel.selectedPiece.dice.roll();
                    tv_show_ap.setVisibility(View.VISIBLE);
                    tv_show_ap.setText(Integer.toString(pipSum));
                    diceModel.fixPiece(diceModel.selectedPiece);
                    diceView.setMovableRange(diceModel.getMovableRange());
                }
                showFixedPieceInfo();

            }
        });

    }


    @Override
    public DicePiece pieceAt(int col, int row) {
        return diceModel.pieceAt(col, row);
    }

    @Override
    public void showSelectedPiece(DicePiece piece)
    {
        if(piece != null)
        {
            //ToDo 선택말 테두리에 녹색이던 뭐든 표시하기

            if(!isFixedPiece() && piece.type == DiceEnums.pieceType.Player)
            {
                bt_roll_dice.setVisibility(View.VISIBLE);
            }
            else
            {
                bt_roll_dice.setVisibility(View.INVISIBLE);
            }
            String infoText = getString(R.string.piece_info, piece.name, piece.col, piece.row);
            tv_piece_info.setText(infoText);

            diceModel.selectedPiece = piece;
            selectedDice = diceModel.selectedPiece.dice;

        }
        else
        {
            tv_piece_info.setText("");
            bt_roll_dice.setVisibility(View.INVISIBLE);

            diceModel.selectedPiece = null;
            selectedDice = null;
        }
    }

    void showFixedPieceInfo()
    {
        bt_roll_dice.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean isFixedPiece() {
        boolean ret = false;

        if(isBattle)
        {
            if(diceModel.fixedBattlePiece != null)
                ret = true;
        }
        else
        {
            if(diceModel.fixedPiece != null)
                ret = true;
        }
        return ret;
    }

    @Override
    public boolean movePiece(int toCol, int toRow) {

        boolean ret = false;
        switch (diceModel.movePiece(toCol, toRow)){
            case Player:
                ret = false;
                break;
            case Terrain:
                ret = false;
                break;
            case Enemy:
                startBattle();
                //Toast.makeText(this,"Enemy",Toast.LENGTH_SHORT).show();
                break;
            case Event:
                //do event
                break;
            case Blank:
                pipSum -= 1;
                if(pipSum == 0)
                {
                    tv_show_ap.setVisibility(View.INVISIBLE);
                    diceModel.selectedPiece = diceModel.fixedPiece;
                    diceModel.unfixPiece();
                    showSelectedPiece(diceModel.selectedPiece);

                }
                else
                {
                    tv_show_ap.setText(Integer.toString(pipSum));
                }
                diceView.setMovableRange(diceModel.getMovableRange());
                ret = true;
        }
        return ret;
    }

    @Override
    public boolean moveBattlePiece(int toCol, int toRow) {

        boolean ret = false;
        switch (diceModel.moveBattlePiece(toCol, toRow)){
            case Player:
                ret = false;
                break;
            case Terrain:
                ret = false;
                break;
            case Enemy:
                //TODO Attack();
                Toast.makeText(this,"Enemy",Toast.LENGTH_SHORT).show();
                break;
            case Event:
                //do event
                break;
            case Blank:
                battlePipSum -= 1;
                if(battlePipSum == 0)
                {
                    tv_show_ap.setVisibility(View.INVISIBLE);
                    diceModel.selectedPiece = diceModel.fixedBattlePiece;
                    diceModel.unfixBattlePiece();
                    showSelectedPiece(diceModel.selectedPiece);

                }
                else
                {
                    tv_show_ap.setText(Integer.toString(battlePipSum));
                }
                diceBattleView.setMovableRange(diceModel.getMovableRange());
                ret = true;
        }
        return ret;
    }

    @Override
    public DicePiece battlePieceAt(int col, int row) {
        return diceModel.battlePieceAt(col, row);
    }

    @Override
    public void showSelectedBattlePiece(DicePiece piece) {
        if(piece != null)
        {
            //ToDo 선택말 테두리에 녹색이던 뭐든 표시하기

            if(!isFixedPiece() && piece.type == DiceEnums.pieceType.Player)
            {
                bt_roll_dice.setVisibility(View.VISIBLE);
            }
            else
            {
                bt_roll_dice.setVisibility(View.INVISIBLE);
            }
            String infoText = getString(R.string.piece_info, piece.name, piece.col, piece.row);
            tv_piece_info.setText(infoText);

            diceModel.selectedPiece = piece;
            selectedDice = diceModel.selectedPiece.dice;

        }
        else
        {
            tv_piece_info.setText("");
            bt_roll_dice.setVisibility(View.INVISIBLE);

            diceModel.selectedPiece = null;
            selectedDice = null;
        }
    }

    void startBattle()
    {
        //ToDo pipsum 제로로 만들고 필드 턴 끝내야함

        //diceBattleView.playerPieceList = diceModel.fixedPiece.battlePieceList;
        //diceBattleView.enemyPieceList = diceModel.targetEnemy.battlePieceList;
        isBattle = true;
        diceView.setVisibility(View.INVISIBLE);
        diceBattleView.setVisibility(View.VISIBLE);

        tv_piece_info.setText("");
        tv_show_ap.setText("");

    }



}