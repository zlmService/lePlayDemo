package com.zlm.demo.leshidemo;

import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.lecloud.sdk.constant.PlayerEvent;
import com.lecloud.sdk.constant.PlayerParams;
import com.lecloud.sdk.videoview.IMediaDataVideoView;
import com.lecloud.sdk.videoview.VideoViewListener;
import com.lecloud.sdk.videoview.vod.VodVideoView;
import com.lecloud.skin.videoview.vod.UIVodVideoView;

public class Main3Activity extends AppCompatActivity {
    private IMediaDataVideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
        RelativeLayout videoContainer = (RelativeLayout) findViewById(R.id.videoContainer);
        videoView = new UIVodVideoView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        videoContainer.addView((View) videoView, params);

        init();
    }
    public void init() {


        String uuid = "waracwqfo5";
        String vuid = "db5a171766";
        String puid = "98209E7541";
        String p = "102";
        String pu = "66666";
        boolean isSaas = true;
        Bundle mBundle = new Bundle();
// 配置播放类型为 点播
        mBundle.putInt(PlayerParams.KEY_PLAY_MODE, PlayerParams.VALUE_PLAYER_VOD);
// UUID和VUID配置
        mBundle.putString(PlayerParams.KEY_PLAY_UUID, uuid);
        mBundle.putString(PlayerParams.KEY_PLAY_VUID, vuid);
        mBundle.putString(PlayerParams.KEY_PLAY_PU, puid);

//        mBundle.putString(PlayerParams.KEY_PLAY_BUSINESSLINE, p);
//        mBundle.putBoolean("saas", isSaas);
//        mBundle.putString(PlayerParams.KEY_PLAY_PAYNAME, pu);
        videoView.setDataSource(mBundle);
        videoView.setVideoViewListener(new VideoViewListener() {
            @Override
            public void onStateResult(int i, Bundle bundle) {
                onHanlder(i, bundle);
            }
        });

    }
    public void onHanlder(int i,Bundle bundle){
        switch (i){
            case PlayerEvent.PLAY_PREPARED:
                // 播放器准备完成，此刻调用start()就可以进行播放了
                if (videoView != null) {
                    videoView.onStart();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(videoView!=null){
            videoView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(videoView!=null){
            videoView.onPause();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.onDestroy();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (videoView != null) {
            videoView.onConfigurationChanged(newConfig);
        }
    }
}
