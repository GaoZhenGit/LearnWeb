package com.gz.learnweb.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.MediaController;

/**
 * Created by host on 2015/9/4.
 */
public class FullScreenMediaController extends MediaController {
    private View bringView;

    public FullScreenMediaController(Context context) {
        super(context);
    }

    public FullScreenMediaController(Context context, boolean useFastForward) {
        super(context, useFastForward);
    }

    public FullScreenMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBringView(View view) {
        bringView = view;
    }

    @Override
    public void show(int overtime) {
        super.show(overtime);
        if (bringView != null) {
            bringView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hide() {
        super.hide();
        if (bringView != null) {
            bringView.setVisibility(View.INVISIBLE);
        }
    }
}
