package com.eno.meet.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.eno.framework.bmob.BmobManager;
import com.eno.framework.event.EventManager;
import com.eno.framework.helper.FileHelper;
import com.eno.framework.manager.DialogManager;
import com.eno.framework.utils.LogUtils;
import com.eno.framework.widget.DialogView;
import com.eno.framework.widget.LodingView;
import com.eno.framework.widget.TextEnoWatcher;
import com.eno.meet.R;
import com.eno.meet.base.BaseUIActivity;

import java.io.File;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 头像上传
 */
public class FirstUploadActivity extends BaseUIActivity implements View.OnClickListener {

    @BindView(R.id.iv_photo)
    CircleImageView mIvPhoto;
    @BindView(R.id.et_nickname)
    AppCompatEditText mEtNickname;
    @BindView(R.id.btn_upload)
    AppCompatButton mBtnUpload;


    private File uploadFile = null;
    private LodingView mLodingView;
    private DialogView mPhotoSelectView;
    private AppCompatTextView tv_camera;
    private AppCompatTextView tv_ablum;
    private AppCompatTextView tv_cancel;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_first_upload;
    }

    @Override
    protected void initData() {

        mBtnUpload.setEnabled(false);
        mEtNickname.addTextChangedListener(new TextEnoWatcher() {
            @Override
            public void onTextChanged(final CharSequence charSequence, final int start, final int before, final int count) {
                mBtnUpload.setEnabled(charSequence.length() > 0 && uploadFile != null);
            }
        });

        initPhotoView();
    }

    private void initPhotoView() {
        mLodingView = new LodingView(this);
        mLodingView.setLodingText(getString(R.string.text_upload_photo_loding));

        mPhotoSelectView = DialogManager.getInstance()
                .initView(this, R.layout.dialog_select_photo, Gravity.BOTTOM);

        tv_camera = mPhotoSelectView.findViewById(R.id.tv_camera);
        tv_camera.setOnClickListener(this);
        tv_ablum = mPhotoSelectView.findViewById(R.id.tv_ablum);
        tv_ablum.setOnClickListener(this);
        tv_cancel = mPhotoSelectView.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
    }


    @OnClick({R.id.iv_photo, R.id.btn_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_photo:
                //显示选择提示框
                DialogManager.getInstance().show(mPhotoSelectView);
                break;
            case R.id.btn_upload:
                uploadPhoto();
                break;
        }
    }

    private void uploadPhoto() {
        //如果条件没有满足，是走不到这里的
        String nickName = mEtNickname.getText().toString().trim();
        mLodingView.show();
        BmobManager.getInstance().uploadFirstPhoto(nickName, uploadFile, new BmobManager.OnUploadPhotoListener() {
            @Override
            public void OnUpdateDone() {
                mLodingView.hide();
                EventManager.post(EventManager.EVENT_REFRE_TOKEN_STATUS);
                finish();
            }

            @Override
            public void OnUpdateFail(BmobException e) {
                mLodingView.hide();
                Toast.makeText(FirstUploadActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.tv_camera:
                DialogManager.getInstance().hide(mPhotoSelectView);
                if (!checkPermissions(Manifest.permission.CAMERA)) {
                    requestPermission(new String[]{Manifest.permission.CAMERA});
                } else {
                    //跳转到相机
                    FileHelper.getInstance().toCamera(mActivity);
                }
                break;
            case R.id.tv_ablum:
                DialogManager.getInstance().hide(mPhotoSelectView);
                //跳转到相册
                FileHelper.getInstance().toAlbum(mActivity);
                break;
            case R.id.tv_cancel:
                DialogManager.getInstance().hide(mPhotoSelectView);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.logE("requestCode:" + requestCode);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == FileHelper.CAMEAR_REQUEST_CODE) {
                try {
                    FileHelper.getInstance().startPhotoZoom(this, FileHelper.getInstance().getTempFile());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == FileHelper.ALBUM_REQUEST_CODE) {
                Uri uri = data.getData();
                if (uri != null) {
                    String path = FileHelper.getInstance().getRealPathFromURI(this, uri);
                    if (!TextUtils.isEmpty(path)) {
                        uploadFile = new File(path);
                        try {
                            FileHelper.getInstance().startPhotoZoom(this, uploadFile);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (requestCode == FileHelper.CAMERA_CROP_RESULT) {
                LogUtils.logE("CAMERA_CROP_RESULT");
                uploadFile = new File(FileHelper.getInstance().getCropPath());
                LogUtils.logE("uploadPhotoFile:" + uploadFile.getPath());
            }
            //设置头像
            if (uploadFile != null) {
                Bitmap mBitmap = BitmapFactory.decodeFile(uploadFile.getPath());
                mIvPhoto.setImageBitmap(mBitmap);

                //判断当前的输入框
                String nickName = mEtNickname.getText().toString().trim();
                mBtnUpload.setEnabled(!TextUtils.isEmpty(nickName));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
