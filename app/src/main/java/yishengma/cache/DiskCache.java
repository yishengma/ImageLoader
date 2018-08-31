package yishengma.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import yishengma.request.BitmapRequest;
import yishengma.utils.CloseUtil;
import yishengma.utils.FileUtil;
import yishengma.utils.MD5Util;

/**
 * 本地文件缓存
 */

public class DiskCache implements BitmapCache {
    private static final String TAG = "DiskCache";

    @Override
    public void put(BitmapRequest key, Bitmap bitmap) {
        FileOutputStream outputStream = null;


        try {

            File file = FileUtil.getDiskCacheDir(MD5Util.hashKeyForDisk(key.imageUri));
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(outputStream);
        }
    }

    @Override
    public Bitmap get(BitmapRequest key) {

        File file = FileUtil.getDiskCacheDir(MD5Util.hashKeyForDisk(key.imageUri));
        return file.exists() ? BitmapFactory.decodeFile(file.getAbsolutePath()) : null;
    }

    @Override
    public void remove(BitmapRequest key) {

    }




}
