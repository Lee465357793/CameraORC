package com.example.orclibrary.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * className: FileUtil
 * description: 文件操作工具
 * author: hong
 * datetime: 2016/4/13 0013 上午 9:03
 */
public class FileUtil {

    /**
     * 获取外部存储路径
     *
     * @return 外部存储的绝对路径
     */
    public static String getExternalStoragePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 获取外部存储文件目录
     *
     * @return 外部存储文件目录
     */
    public static File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 判断外部存储是否存在
     *
     * @return
     */
    public static boolean hasExternalStorage() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 获取系统存储路径
     *
     * @return 系统存储的绝对路径
     */
    public static String getRootStoragePath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }


    /**
     * 检查并建立指定的目录
     *
     * @param dirPath 目录的路径
     * @return 是否成功建立目录
     */
    public static boolean mkdirIfNotFound(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return false;
        }
        File dir = new File(dirPath);
        if (dir.mkdirs() || dir.isDirectory()) {
            return true;
        }
        return false;
    }


    public static String getDefaultCacheDir(Context context) {

        String cacheDir = null;
        if (FileUtil.hasExternalStorage()) {
            cacheDir = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"kaka";
        } else {
            cacheDir = Environment.getRootDirectory().getAbsolutePath();
        }

        if (mkdirIfNotFound(cacheDir)) {
            Log.i("FileUtil", "======getDefaultCacheDir: " + cacheDir);
            return cacheDir;
        }

        return null;

    }


    /**
     * 删除单个指定路径的文件
     *
     * @param filePath 文件的路径
     * @return 是否成功删除
     */

    public static boolean deleteFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        try {
            File file = new File(filePath);
            return file.delete();
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 递归删除指定路径的目录及其下的文件和子目录
     *
     * @param dirPath 要删除的目录的路径
     * @return 是否成功删除
     */
    public static boolean deleteDir(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return false;
        }

        try {
            File dir = new File(dirPath);
            if (!dir.exists() || !dir.isDirectory()) {
                return false;
            }

            return deleteDir(dir);  // 递归清空目录中的文件及子目录
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir == null) {
            return false;
        }
        boolean result = true;
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                boolean hasSuccess = true;
                if (file.isDirectory()) {
                    hasSuccess = deleteDir(file);
                } else {
                    hasSuccess = file.delete();
                }
                if (!hasSuccess) {
                    result = false;
                }
            }
        }
        if (dir.delete() == false) {
            result = false;
        }
        return result;
    }


    public static String saveFile(String dir, String name, InputStream is) {
        if (mkdirIfNotFound(dir)) {
            try {
                FileOutputStream fos = new FileOutputStream(new File(dir, name));
                byte[] b = new byte[1024];
                int len = 0;
                while ((len = is.read(b)) != -1) {
                    fos.write(b,0,len);
                }
                is.close();
                fos.close();
                return dir + File.separator + name;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }
    public static File getSaveFile(Context context, String failName) {
        File file = new File(context.getFilesDir(), failName);
        return file;
    }


}