package com.example.littlevideo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;


public class PlayVideo extends AppCompatActivity {

private VideoView mVideoView;
private int mVideoWidth=0;
private int mVideoHeight=0;
private float mVideoScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        String url=getIntent().getStringExtra("url");
        mVideoView=(VideoView)findViewById(R.id.video_view);
        mVideoView.setVideoPath(url);
        mVideoView.setMediaController(new MediaController(PlayVideo.this));
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        //FixMe 获取视频资源的宽度
                        mVideoWidth = mp.getVideoWidth();
                        //FixMe 获取视频资源的高度
                        mVideoHeight = mp.getVideoHeight();

                        mVideoScale = (float) mVideoWidth / (float) mVideoHeight;
                    }
                });

            }
        });
        adaptScreen();
        mVideoView.start();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adaptScreen();
    }

    private void adaptScreen(){
        if (mVideoView == null | mVideoWidth==0 | mVideoHeight==0) {
            return;
        }
        if (this.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().invalidate();
            float height = ScreenUtil.getScreenHeightPix(this);
            float width = ScreenUtil.getScreenWidthPix(this);
            float sacle=width/height;
            if(sacle>=mVideoScale){
                mVideoView.getLayoutParams().height = (int) height;
                mVideoView.getLayoutParams().width = (int) (mVideoScale*height);
            }else{
                mVideoView.getLayoutParams().height = (int) (width/mVideoScale);
                mVideoView.getLayoutParams().width = (int) width;
            }

        } else {
            // 竖屏
            final WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            float width = ScreenUtil.getScreenWidthPix(this);
            float height = ScreenUtil.getScreenHeightPix(this);
            float sacle=width/height;
            if(sacle>=mVideoScale){
                mVideoView.getLayoutParams().height = (int) height;
                mVideoView.getLayoutParams().width = (int) (mVideoScale*height);
            }else{
                mVideoView.getLayoutParams().height = (int) (width/mVideoScale);
                mVideoView.getLayoutParams().width = (int) width;
            }
        }
    }




}
