# CameraORC
android ORC 相机样式
# KChartView
KChart for Android ；股票k线图

效果预览
-------  

* 相机样式  正反身份证，银行卡。
<div class='row'>
        <img src='https://gitee.com/uploads/images/2018/0428/223609_a97b3f4e_862414.png' width="300px"/>
        <img src='https://gitee.com/uploads/images/2018/0428/223722_3c39a50d_862414.png' width="300px"/>
        <img src='https://github.com/tifezh/KChartView/blob/master/img/demo.gif' width="300px"/>
</div>

#### 配置及使用

##### 添加依赖
```xml
implementation 'com.github.Lee465357793:CameraORC:v1.0'
```


##### 打开相机

```java

Intent intent = new Intent(this, CameraActivity.class); //一般样式
intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtil.getSaveFile(getApplication(), NAME_A).getAbsolutePath());
intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_GENERAL);
startActivityForResult(intent, REQUEST_A_CAMERA);

intent = new Intent(this, CameraActivity.class);//身份证正面
intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtil.getSaveFile(getApplication(), NAME_B).getAbsolutePath());
intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
startActivityForResult(intent, REQUEST_B_CAMERA);

intent = new Intent(this, CameraActivity.class);//身份证背面
intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtil.getSaveFile(getApplication(), NAME_C).getAbsolutePath());
intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
startActivityForResult(intent, REQUEST_C_CAMERA);

intent = new Intent(this, CameraActivity.class);//银行卡
intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtil.getSaveFile(getApplication(), NAME_C).getAbsolutePath());
intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_BANK_CARD);
startActivityForResult(intent, REQUEST_C_CAMERA);

```

##### 接收数据

```java

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
            }
            if (TextUtils.isEmpty(filePath)) {
                ToastUtil.showShort("照片获取失败,请重新拍摄");
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

```

License
-------

    Copyright 2018 tifezh

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
