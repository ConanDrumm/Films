package com.example.android.films;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by stan on 11/22/15.
 */
public class SquareImageView extends ImageView {
    public SquareImageView(Context context) {

        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /*
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
    */

}