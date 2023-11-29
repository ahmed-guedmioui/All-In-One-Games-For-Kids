package com.ahmedapps.walkthrough.items;

import android.content.Context;

import androidx.appcompat.widget.AppCompatImageView;

public class ItemPuzzle extends AppCompatImageView {
    public int xCoord;
    public int yCoord;
    public int pieceWidth;
    public int pieceHeight;
    public boolean canMove = true;

    public ItemPuzzle(Context context) {
        super(context);
    }
}