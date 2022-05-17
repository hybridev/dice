package com.hybridev.dice;

import static java.lang.Math.min;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * TODO: document your custom view class.
 */
public class DiceView extends View {
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    float scaleFactor = 1f;
    float originX = 850f;
    float originY = 50f;
    float cellSide = 100f;

    Paint paint = new Paint();

    public Set<Integer> imgResIDs = new HashSet<Integer>();
    public Map<Integer, Bitmap> bitmaps = new HashMap<Integer, Bitmap>();

    DiceDelegate diceDelegate;

    int[][] movableRange = {{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1},{-1,-1}};


    public DiceView(Context context) {
        super(context);

        loadBitmaps();
    }

    public DiceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        loadBitmaps();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int smaller = min(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(smaller, smaller);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float chessBoardSide = min(getWidth(), getHeight()) * scaleFactor;
        cellSide = chessBoardSide / 8f;
        originX = (getWidth() - chessBoardSide) / 2f;
        originY = (getHeight() - chessBoardSide) /2f;

        //Log.d("tempD","onDraw (" + Float.toString(originX)+ ", " + Float.toString(originY) + ") chessBoardSide : " + Float.toString(chessBoardSide) + " cellSide : " + Float.toString(cellSide) + " getWidth() : " + Float.toString(getWidth())+ " getHeight() : " + Float.toString(getHeight()));

        drawBoard(canvas);
        drawPieces(canvas);
        drawMovableRange(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int fromCol = -1;
        int fromRow = -1;

        switch (event.getActionMasked())
        {
            case MotionEvent.ACTION_DOWN :
                fromCol = (int)((event.getX() - originX) / cellSide);
                fromRow = 7 - (int)((event.getY() - originY) / cellSide);

                selectPiece(fromCol, fromRow);
                if(diceDelegate.isFixedPiece())
                {
                    if(diceDelegate.movePiece(fromCol, fromRow))
                        invalidate();
                }
                //Log.d("tempD","from (" + Float.toString(event.getY())+ ", " + Float.toString(event.getX()) + ") cellside : " + Float.toString(cellSide) + " originX : " + Float.toString(originX) + " originY : " + Float.toString(originY));
                //Log.d("tempD","from (" +Integer.toString(fromRow) + ", " + Integer.toString(fromCol) + ")");
                break;

            case MotionEvent.ACTION_MOVE :

                break;

            case MotionEvent.ACTION_UP :
                int col = Math.round((event.getX() - originX) / cellSide);
                int row = 7 - Math.round((event.getY() - originY) / cellSide);

                //Log.d("tempD","to (" +Integer.toString(row) + ", " + Integer.toString(col) + ")");
                break;

        }
        return true;
    }

    private void drawBoard(Canvas canvas)
    {
        for (int row = 0; row <= 7; row++)
        {
            for(int col = 0; col<= 7; col++)
            {
                drawSquareAt(canvas, col, row, (col + row) % 2 == 1);
            }
        }
    }

    private void drawSquareAt(Canvas canvas, int col, int row, boolean isDark)
    {
        if(isDark)
            paint.setColor(getResources().getColor(R.color.chess_board_dark));
        else
            paint.setColor(getResources().getColor(R.color.chess_board_light));

        paint.setStyle(Paint.Style.FILL);

        canvas.drawRect(originX + col * cellSide,originY + row * cellSide,
                originX+ (col + 1)*cellSide,originY+(row +1)*cellSide,paint);

    }

    private void drawPieces(Canvas canvas)
    {
        for(int row = 0; row <= 7; row++)
        {
            for(int col = 0; col<= 7; col++)
            {
                if(diceDelegate.pieceAt(col, row) != null)
                {
                    drawPieceAt(canvas, col, row, diceDelegate.pieceAt(col, row).resID);
                }
            }
        }
    }

    private void drawPieceAt(Canvas canvas, int col, int row, int resID)
    {
        Bitmap bitmap = bitmaps.get(resID);
        if(bitmap == null)
            Log.d("tempD", "null " + Integer.toString(resID));
        else
        canvas.drawBitmap(bitmap, null, new RectF(originX + col * cellSide, originY + (7 - row) * cellSide,
                    originX + (col + 1) * cellSide, originY + ((7 - row) + 1) * cellSide), paint);
    }

    private void loadBitmaps()
    {
        imgResIDs.add(R.drawable.testrpg);
        imgResIDs.add(R.drawable.testssam_ccexpress);
        imgResIDs.add(R.drawable.mountain1);
        imgResIDs.add(R.drawable.mountain2);

        Iterator<Integer> resIDIter = imgResIDs.iterator();
        while (resIDIter.hasNext())
        {
            int resId = resIDIter.next();
            bitmaps.put(resId, BitmapFactory.decodeResource(getContext().getResources(), resId));
        }

    }

    void selectPiece(int col, int row)
    {
        DicePiece selectedPiece = diceDelegate.pieceAt(col, row);
        diceDelegate.showSelectedPiece(selectedPiece);
    }

    void drawMovableRange(Canvas canvas)
    {
        paint.setColor(getResources().getColor(R.color.range_yellow));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(cellSide/16);

        int col, row;

        for(int ArrayRow = 0; ArrayRow <= 7; ArrayRow++)
        {
                col = movableRange[ArrayRow][0];
                row = movableRange[ArrayRow][1];
                if(col >= 0 && col <= 7 && row >= 0 && row <= 7 )
                {
                    canvas.drawRect(originX + col * cellSide,originY + (7 - row) * cellSide,
                            originX+ (col + 1)*cellSide,originY + ((7 - row) + 1) * cellSide,paint);
                    //Log.d("tempD", "(" + Integer.toString(col) + ", " + Integer.toString(row)+")");
                }
        }
    }

    void setMovableRange(int[][] range)
    {
        movableRange = range;
        invalidate();
    }
}