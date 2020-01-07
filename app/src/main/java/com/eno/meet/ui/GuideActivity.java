package com.eno.meet.ui;

import android.animation.ObjectAnimator;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.eno.framework.base.BasePageAdapter;
import com.eno.framework.manager.MediaPlayerManager;
import com.eno.framework.utils.AnimUtils;
import com.eno.framework.widget.OnEnoPageChangeListener;
import com.eno.meet.R;
import com.eno.meet.base.BaseUIActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 引导页
 * <p>
 * 1、ViewPage ：适配器|帧动画播放
 * 2、小圆点控制
 * 3、歌曲播放
 * 4、属性动画旋转
 * 5、跳转
 */
public class GuideActivity extends BaseUIActivity {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.img_guide_music)
    AppCompatImageView mImgGuideMusic;
    @BindView(R.id.img_guide_point_1)
    AppCompatImageView mImgGuidePoint1;
    @BindView(R.id.img_guide_point_2)
    AppCompatImageView mImgGuidePoint2;
    @BindView(R.id.img_guide_point_3)
    AppCompatImageView mImgGuidePoint3;
    @BindDrawable(R.drawable.img_guide_point)
    Drawable point;
    @BindDrawable(R.drawable.img_guide_point_p)
    Drawable point_p;

    private View mView1;
    private View mView2;
    private View mView3;

    private ImageView imgGuideStar;
    private ImageView imgGuideNight;
    private ImageView imgGuideSmile;

    private MediaPlayerManager mPlayerManager;

    private List<View> mPageList = new ArrayList<>();
    private ObjectAnimator mAnim;
    private BasePageAdapter mPageAdapter;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initData() {

        mView1 = View.inflate(this, R.layout.layout_pager_guide_1, null);
        mView2 = View.inflate(this, R.layout.layout_pager_guide_2, null);
        mView3 = View.inflate(this, R.layout.layout_pager_guide_3, null);

        mPageList.add(mView1);
        mPageList.add(mView2);
        mPageList.add(mView3);

        imgGuideStar = mView1.findViewById(R.id.img_guide_star);
        imgGuideNight = mView2.findViewById(R.id.img_guide_night);
        imgGuideSmile = mView3.findViewById(R.id.img_guide_smile);

        //播放帧动画
        AnimationDrawable animStar = (AnimationDrawable) imgGuideStar.getBackground();
        animStar.start();

        AnimationDrawable animNight = (AnimationDrawable) imgGuideNight.getBackground();
        animNight.start();

        AnimationDrawable animSmile = (AnimationDrawable) imgGuideSmile.getBackground();
        animSmile.start();

        mPageAdapter = new BasePageAdapter(mPageList);
        mViewPager.setAdapter(mPageAdapter);

        mViewPager.setOffscreenPageLimit(mPageList.size());
        mViewPager.addOnPageChangeListener(new OnEnoPageChangeListener() {
            @Override
            public void onPageSelected(final int position) {
                seletePoint(position);
            }
        });

        startMusic();
    }

    private void startMusic() {
        mPlayerManager = new MediaPlayerManager();
        mPlayerManager.setLooping(true);

        final AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.guide);
        mPlayerManager.startPlay(file);
        mPlayerManager.setOnCompletionListener(mp -> mPlayerManager.startPlay(file));

        //旋转动画
        mAnim = AnimUtils.rotation(mImgGuideMusic);
        mAnim.start();
    }

    /**
     * 动态选择小圆点
     *
     * @param position
     */
    private void seletePoint(final int position) {
        mImgGuidePoint1.setImageDrawable(position == 0 ? point_p : point);
        mImgGuidePoint2.setImageDrawable(position == 1 ? point_p : point);
        mImgGuidePoint3.setImageDrawable(position == 2 ? point_p : point);
    }


    @OnClick({R.id.img_guide_music, R.id.tv_guide_skip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_guide_music:
                if (mPlayerManager.getPlayState() == MediaPlayerManager.MEDIA_STATUS_PAUSE) {
                    mAnim.start();
                    mPlayerManager.continuePlay();
                    mImgGuideMusic.setImageResource(R.drawable.img_guide_music);
                } else if (mPlayerManager.getPlayState() == MediaPlayerManager.MEDIA_STATUS_PLAY) {
                    mAnim.pause();
                    mPlayerManager.pausePlay();
                    mImgGuideMusic.setImageResource(R.drawable.img_guide_music_off);
                }
                break;
            case R.id.tv_guide_skip:
                startActivityFinishSelf(LoginActivity.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerManager.stopPlay();
    }
}
