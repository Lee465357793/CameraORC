package com.lee.cameraorc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.example.orclibrary.CameraActivity;
import com.example.orclibrary.util.FileUtil;
import com.example.orclibrary.util.PictureUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_A_CAMERA = 1000;
    private static final int REQUEST_B_CAMERA = 1001;
    private static final int REQUEST_C_CAMERA = 1002;
    private static final int REQUEST_D_CAMERA = 1003;
    private static final String NAME_A = "photo_a.jpg";
    private static final String NAME_B = "photo_b.jpg";
    private static final String NAME_C = "photo_c.jpg";
    private static final String NAME_D = "photo_d.jpg";
    private String mCurrFileName;

    private ImageView mVerifyPhotoA;
    private ImageView mVerifyPhotoB;
    private ImageView mVerifyPhotoC;
    private ImageView mVerifyPhotoD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_general).setOnClickListener(this);
        findViewById(R.id.btn_card_front).setOnClickListener(this);
        findViewById(R.id.btn_card_back).setOnClickListener(this);
        findViewById(R.id.btn_bank_card).setOnClickListener(this);
        mVerifyPhotoA = findViewById(R.id.image_a);
        mVerifyPhotoB = findViewById(R.id.image_b);
        mVerifyPhotoC = findViewById(R.id.image_c);
        mVerifyPhotoD = findViewById(R.id.image_d);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String filePath = null;
            switch (requestCode) {
                case REQUEST_A_CAMERA://人证合一
                    filePath = new File(getFilesDir(), NAME_A).getAbsolutePath();
                    break;
                case REQUEST_B_CAMERA://身份证正面
                    filePath = new File(getFilesDir(), NAME_B).getAbsolutePath();
                    break;
                case REQUEST_C_CAMERA: //身份证背面
                    filePath = new File(getFilesDir(), NAME_C).getAbsolutePath();
                    break;
                case REQUEST_D_CAMERA: //银行卡
                    filePath = new File(getFilesDir(), NAME_C).getAbsolutePath();
                    break;
            }
            if (TextUtils.isEmpty(filePath)) {
//                ToastUtil.showShort("照片获取失败,请重新拍摄");
                return;
            }

            Bitmap smallBitmap = PictureUtil.getSmallBitmap(filePath);
            PictureUtil.saveBitmap(smallBitmap, 70, filePath, Bitmap.CompressFormat.JPEG);
            File file = new File(filePath);
            if (file != null && smallBitmap != null) {
                setFilePath(smallBitmap, filePath, file);
            }
        }

    }

    private void setFilePath(Bitmap smallBitmap, String filePath, File file) {
        if (NAME_A.equals(mCurrFileName)) {
            mVerifyPhotoA.setImageBitmap(smallBitmap);
        } else if (NAME_B.equals(mCurrFileName)) {
            mVerifyPhotoB.setImageBitmap(smallBitmap);
        } else if (NAME_C.equals(mCurrFileName)) {//身份证背面
            mVerifyPhotoC.setImageBitmap(smallBitmap);
        } else if (NAME_D.equals(mCurrFileName)) {//身份证背面
            mVerifyPhotoD.setImageBitmap(smallBitmap);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_general:
                mCurrFileName = NAME_A;
                Intent intent = new Intent(this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication(), NAME_A).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_A_CAMERA);
                break;
            case R.id.btn_card_front:
                mCurrFileName = NAME_B;
                intent = new Intent(this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication(), NAME_B).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, REQUEST_B_CAMERA);
                break;
            case R.id.btn_card_back:
                mCurrFileName = NAME_C;
                intent = new Intent(this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication(), NAME_C).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                startActivityForResult(intent, REQUEST_C_CAMERA);
                break;
            case R.id.btn_bank_card:
                mCurrFileName = NAME_D;
                intent = new Intent(this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication(), NAME_D).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_BANK_CARD);
                startActivityForResult(intent, REQUEST_D_CAMERA);
                break;
        }

    }
}
