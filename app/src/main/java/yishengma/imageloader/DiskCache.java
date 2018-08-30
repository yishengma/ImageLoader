package yishengma.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import yishengma.App;
import yishengma.cache.Util;
import yishengma.utils.CloseUtil;
import yishengma.utils.FileUtil;

/**
 * 本地文件缓存
 */

public class DiskCache implements ImageCache {
    private static final String TAG = "DiskCache";

    @Override
    public void put(String url, Bitmap bitmap) {
        FileOutputStream outputStream = null;


        try {

            File file = FileUtil.getDiskCacheDir(url.replace("/", ""));
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            Log.e(TAG, "put: ");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(outputStream);
        }

    }

    @Override
    public Bitmap get(String url) {
        File file = FileUtil.getDiskCacheDir(url.replace("/", ""));
        return file.exists() ? BitmapFactory.decodeFile(file.getAbsolutePath()) : null;
    }
}
