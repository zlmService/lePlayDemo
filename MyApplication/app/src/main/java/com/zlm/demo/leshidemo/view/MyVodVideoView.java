package com.zlm.demo.leshidemo.view;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lecloud.sdk.constant.PlayerEvent;
import com.lecloud.sdk.constant.PlayerParams;
import com.lecloud.sdk.constant.StatusCode;
import com.lecloud.sdk.videoview.vod.VodVideoView;
import com.lecloud.skin.ui.utils.timer.IChange;
import com.lecloud.skin.ui.utils.timer.LeTimerManager;
import com.squareup.otto.Bus;
import com.zlm.demo.leshidemo.Duration;
import com.zlm.demo.leshidemo.MyApplication;
import com.zlm.demo.leshidemo.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhaoliming on 2016/8/9.
 */
public class MyVodVideoView extends VodVideoView {

    protected LeTimerManager leTimerManager;
//    protected ILetvVodUICon letvVodUICon;
    private long lastPosition;
    private boolean isSeeking = false;
    protected int width = -1;
    protected int height = -1;
    TextView textView;
    private Bus mBus;
    private Duration duration;


    public MyVodVideoView(Context context) {
        super(context);

        duration=new Duration();
        getLeTimerManager(1000);
        textView = (TextView)findViewById(R.id.video_currentTime);
        mBus= MyApplication.getApp().getBus();
        mBus.register(this);

    }


    public LeTimerManager getLeTimerManager(long delaymillts) {
        if (leTimerManager == null) {
            leTimerManager = new LeTimerManager(new IChange() {
                @Override
                public void onChange() {
                    if (player != null) {
                        post(new  Runnable() {
                            @Override
                            public void run() {
                                duration.setCurrentDuration(player.getCurrentPosition());
                                duration.setDuration(player.getDuration());
                                mBus.post(duration);
                            }
                        });
                    }
                }
            }, delaymillts);
        }
        return leTimerManager;
    }

    @Override
    protected void notifyPlayerEvent(int event, Bundle bundle) {
        super.notifyPlayerEvent(event, bundle);
//        letvVodUICon.processPlayerState(event, bundle);
        switch (event) {
            case PlayerEvent.PLAY_COMPLETION://202
                //btn pause
//                letvVodUICon.setPlayState(false);
//                //update progress
//                if (letvVodUICon != null && player != null) {
//                    letvVodUICon.setDuration(player.getDuration());
//                    letvVodUICon.setCurrentPosition(player.getCurrentPosition());
//                }
                lastPosition = 0;

                stopTimer();
                break;
            case PlayerEvent.PLAY_INFO:
                int code = bundle.getInt(PlayerParams.KEY_RESULT_STATUS_CODE);
                if (code == StatusCode.PLAY_INFO_VIDEO_RENDERING_START) {
                    startTimer();
                }

                switch (code) {
                    case StatusCode.PLAY_INFO_BUFFERING_START://500004  缓冲开始

                        break;
                    case StatusCode.PLAY_INFO_BUFFERING_END://500005   缓冲结束

                        break;
                    case StatusCode.PLAY_INFO_VIDEO_RENDERING_START://500006 渲染第一帧开始

                        break;
                    default:
                        break;
                }
                break;
            case PlayerEvent.PLAY_PREPARED: {

                break;
            }
            case PlayerEvent.PLAY_SEEK_COMPLETE: {//209
                setLastPostion();
                isSeeking = false;
//                Log.e("meng", "PlayerEvent.PLAY_SEEK_COMPLETE: " + lastPosition);
                break;
            }
            case PlayerEvent.PLAY_ERROR://205

                break;

            default:
                break;
        }
    }

    private void stopTimer() {
        if (leTimerManager != null) {
            leTimerManager.stop();
            leTimerManager = null;
        }
    }

    private void startTimer() {
        if (leTimerManager == null) {
            getLeTimerManager(1000);
        }
        if (leTimerManager != null) {
            leTimerManager.start();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (width == -1 || height == -1) {
            width = MyVodVideoView.this.getLayoutParams().width;
            height = MyVodVideoView.this.getLayoutParams().height;
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
//        if (timeTextView != null) {
//            removeView(timeTextView);
//        }
        setLastPostion();
    }

    private void setLastPostion() {
        lastPosition = player.getCurrentPosition();
    }

}
