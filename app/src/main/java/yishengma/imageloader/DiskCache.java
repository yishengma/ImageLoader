package yishengma.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import yishengma.App;
import yishengma.utils.CloseUtil;

/**
 * 本地文件缓存
 */

public class DiskCache implements ImageCache {

    private static final String cacheDir = Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    public void put(String url, Bitmap bitmap) {
        FileOutputStream outputStream = null ;
        File file = new File(App.cacheDir, url);

        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            CloseUtil.close(outputStream);
        }

    }

    @Override
    public Bitmap get(String url) {
        File file = new File(App.cacheDir,url);
        return BitmapFactory.decodeFile(file.getName());
    }
}
