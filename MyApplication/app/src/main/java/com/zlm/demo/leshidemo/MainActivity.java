package com.zlm.demo.leshidemo;

import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lecloud.sdk.constant.PlayerEvent;
import com.lecloud.sdk.constant.PlayerParams;
import com.lecloud.sdk.videoview.IMediaDataVideoView;
import com.lecloud.sdk.videoview.VideoViewListener;
import com.lecloud.sdk.videoview.vod.VodVideoView;
import com.lecloud.skin.ui.utils.TimerUtils;
import com.lecloud.skin.ui.utils.timer.IChange;
import com.lecloud.skin.ui.utils.timer.LeTimerManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.zlm.demo.leshidemo.view.MyVodVideoView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private IMediaDataVideoView videoView;
    String uuid = "waracwqfo5";
    String vuid = "db5a171766";//db5a171766
    private Bundle mBundle;
    private ImageView btnPayOfPause;
    protected LeTimerManager leTimerManager;
    private Bus mBus;
    private TextView tvCurrent;
    private SeekBar mSeekBar;
    private TextView tvAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.GONE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBus = MyApplication.getApp().getBus();
        mBus.register(this);
        tvCurrent = (TextView) findViewById(R.id.video_currentTime);
        tvAll = (TextView) findViewById(R.id.video_AllTime);
        mSeekBar = (SeekBar) findViewById(R.id.vnew_seekbar);
        mSeekBar.setOnSeekBarChangeListener(this);
        mSeekBar.setMax(250505);
        btnPayOfPause = (ImageView) findViewById(R.id.btn_playOrPause);
        btnPayOfPause.setOnClickListener(this);
        initView();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                initData();
            }
        }, 500);

    }


    public void initView() {
        String progress = TimerUtils.stringForTime((int) 250505 / 1000);
        tvAll.setText(progress);
        RelativeLayout videoContainer = (RelativeLayout) findViewById(R.id.layout_player);
        videoView = new MyVodVideoView(this);
//        videoView = new UIVodVideoView(this);
        videoContainer.addView((View) videoView, 0);
    }

    public void initData() {
        mBundle = new Bundle();
        mBundle.putString(PlayerParams.KEY_PLAY_UUID, uuid);
        mBundle.putString(PlayerParams.KEY_PLAY_VUID, vuid);

        videoView.setDataSource(mBundle);
        videoView.setVideoViewListener(new VideoViewListener() {
            @Override
            public void onStateResult(int i, Bundle bundle) {
                onHanlder(i, bundle);
            }
        });
    }

    public void onHanlder(int state, Bundle bundle) {
        switch (state) {
            case PlayerEvent.PLAY_PREPARED://
                // 播放器准备完成，此刻调用start()就可以进行播放了
                if (videoView != null) {
//                    videoView.seekTo(200000);

                    btnPayOfPause.setBackgroundResource(R.mipmap.pause);
                    videoView.onStart();
                }
                break;

            case PlayerEvent.PLAY_COMPLETION:
                Toast.makeText(MainActivity.this, "正在缓冲下一个视频", Toast.LENGTH_SHORT).show();
                // 播放完成
                videoView.setDataSource(mBundle);
                break;

            case PlayerEvent.PLAY_SEEK_COMPLETE:
//                long currentPosition = videoView.getCurrentPosition();
//                System.out.println(currentPosition+"positionSEEK");
                Toast.makeText(MainActivity.this, "seek", Toast.LENGTH_SHORT).show();
                break;

            case PlayerEvent.PLAY_INFO:
//                long currntln(currentPosition+"positionSEEK");

                break;

            default:
                break;
        }
    }

    @Subscribe
    public void aa(Duration duration) {
        long duration1 = duration.getDuration();
//        long duration1=Integer.parseInt(duration.getDuration()+"");
        long currentDuration = duration.getCurrentDuration();
        mSeekBar.setProgress((int) currentDuration);
        System.out.println(duration1 + "--" + currentDuration);


//        String progress = TimerUtils.stringForTime((int) (mSeekBar.getProgress() * currentDuration / duration1 / 1000));
        String progress = TimerUtils.stringForTime((int) currentDuration / 1000);

        System.out.println(progress + "AAAAA");
        tvCurrent.setText(progress);
//    int times = (int) (duration/1000);
//    String duration = TimerUtils.stringForTime(times);
////    mGestureControl.mSeekToPopWindow.setProgress(progress, duration);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (videoView != null) {
            videoView.onResume();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null) {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_playOrPause:
                if (videoView.isPlaying()) {
                    btnPayOfPause.setBackgroundResource(R.mipmap.play);
                    videoView.onPause();

                } else {
                    btnPayOfPause.setBackgroundResource(R.mipmap.pause);
                    videoView.onStart();
                }
                break;
        }
    }

    //该方法拖动进度条进度改变的时候调用
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        System.out.println(i + "`````````````````" + b);
    }

    //该方法拖动进度条开始拖动的时候调用。
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Toast.makeText(MainActivity.this, "开始拖动", Toast.LENGTH_SHORT).show();
    }

    //该方法拖动进度条开始拖动的时候调用。
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Toast.makeText(MainActivity.this, "停止拖动", Toast.LENGTH_SHORT).show();
        if (videoView != null) {
            videoView.seekTo(20000);
            String progress1 = TimerUtils.stringForTime((int) 20000 / 1000);
            System.out.println(progress1 + "--------------------------------");
        }
    }
}
