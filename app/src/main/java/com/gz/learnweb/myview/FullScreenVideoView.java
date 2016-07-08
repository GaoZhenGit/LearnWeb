package com.gz.learnweb.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by host on 2015/8/16.
 */
public class FullScreenVideoView extends VideoView {

    private int videoWidth;
    private int videoHeight;
    private onVideoStartListener onVideoStartListener;

    public FullScreenVideoView(Context context) {
        super(context);
    }

    public FullScreenVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullScreenVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(videoWidth, widthMeasureSpec);
        int height = getDefaultSize(videoHeight, heightMeasureSpec);
        if (videoWidth > 0 && videoHeight > 0) {
            if (videoWidth * height > width * videoHeight) {
                height = width * videoHeight / videoWidth;
            } else if (videoWidth * height < width * videoHeight) {
                width = height * videoWidth / videoHeight;
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    public void start() {
        super.start();
        if (onVideoStartListener != null) {
            onVideoStartListener.onStart();
        }
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    public void setOnVideoStartListener(FullScreenVideoView.onVideoStartListener onVideoStartListener) {
        this.onVideoStartListener = onVideoStartListener;
    }

    public interface onVideoStartListener {
        void onStart();
    }
}