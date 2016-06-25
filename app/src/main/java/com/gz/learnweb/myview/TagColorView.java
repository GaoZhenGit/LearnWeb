package com.gz.learnweb.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.gz.learnweb.R;

/**
 * Created by host on 2015/10/2.
 */
public class TagColorView extends TextView {
    private TypedArray typedArray;

    public TagColorView(Context context) {
        super(context);
    }

    public TagColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagColorView);
    }

    public TagColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.TagColorView);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        GradientDrawable myGrad = (GradientDrawable)this.getBackground();
        if(typedArray==null)
            return;
        myGrad.setColor(typedArray.getColor(R.styleable.TagColorView_tcv_tagColor,0));
        myGrad.setStroke(0,0);
    }
}
