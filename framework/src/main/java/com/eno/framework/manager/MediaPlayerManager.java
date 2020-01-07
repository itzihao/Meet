package com.eno.framework.manager;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;


/**
 * Created by Hao on 2020-01-06.
 * Email:itzihao@sina.com
 * 播放器管理类
 */
public class MediaPlayerManager {

    private static final int MEDIA_STATUS_PLAY = 1;
    private static final int MEDIA_STATUS_PAUSE = 2;
    private static final int MEDIA_STATUS_STOP = 3;

    private static int MEDIA_STATUS = MEDIA_STATUS_STOP;

    private static final int H_PROGRESS = 1000;


    public onMusicProgressListener mOnMusicProgressListener;

    private MediaPlayer mMediaPlayer;

    public MediaPlayerManager() {
        mMediaPlayer = new MediaPlayer();
    }

    /**
     * 计算歌曲的进度
     * 1、开始播放的时候就开启循环计算时长
     * 2、将进度计算结果实时反馈
     */
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(final Message msg) {
            switch (msg.what) {
                case H_PROGRESS:
                    if (mOnMusicProgressListener != null) {
                        int currentPosition = getCurrentPosition();
                        int pos = (int) (((float) currentPosition / (float) getDuration()) * 100);
                        mOnMusicProgressListener.onProgress(currentPosition, pos);
                        mHandler.sendEmptyMessageDelayed(H_PROGRESS, 1000);
                    }
                    break;

            }
            return false;
        }
    });

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public void startPlay(final String path) {
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            MEDIA_STATUS = MEDIA_STATUS_PLAY;
            mHandler.sendEmptyMessage(H_PROGRESS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void startPlay(final @NonNull AssetFileDescriptor afd) {
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(afd);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            MEDIA_STATUS = MEDIA_STATUS_PLAY;
            mHandler.sendEmptyMessage(H_PROGRESS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pausePlay() {
        if (isPlaying()) {
            mMediaPlayer.pause();
            MEDIA_STATUS = MEDIA_STATUS_PAUSE;
        }
    }

    public void continuePlay() {
        mMediaPlayer.start();
        MEDIA_STATUS = MEDIA_STATUS_PLAY;
        mHandler.sendEmptyMessage(H_PROGRESS);
    }

    public void stopPlay() {
        mMediaPlayer.stop();
        MEDIA_STATUS = MEDIA_STATUS_STOP;
        mHandler.removeMessages(H_PROGRESS);
    }


    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    public int getPlayState() {
        return MEDIA_STATUS;
    }

    public void setLooping(boolean isLooping) {
        mMediaPlayer.setLooping(isLooping);
    }

    public void setSeekTo(int msec) {
        mMediaPlayer.seekTo(msec);
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener listener) {
        mMediaPlayer.setOnCompletionListener(listener);
    }

    public void setOnCompletionListener(MediaPlayer.OnErrorListener listener) {
        mMediaPlayer.setOnErrorListener(listener);
    }

    public void setOnMusicProgressListener(final onMusicProgressListener onMusicProgressListener) {
        mOnMusicProgressListener = onMusicProgressListener;
    }

    public interface onMusicProgressListener {
        void onProgress(int currentPosition, int pos);
    }
}

