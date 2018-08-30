package yishengma.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import yishengma.cache.Util;
import yishengma.utils.FileUtil;
import yishengma.utils.MD5Util;

/**
 * Created by asus on 18-8-30.
 */

public class DiskLruCache implements ImageCache {
    private static final int APP_VERSION = 1; //APP版本号，当版本号改变时，缓存数据会被清除
    private static final int VALUE_COUNT = 1;  //同一个key可以对应 VALUE_COUNT 个文件
    private static final int MAX_SIZE = 10 * 1024 * 1024;  //最大可以缓存的数据量
    private yishengma.cache.DiskLruCache mDiskLruCache;

    private static final String TAG = "DiskLruCache";
    public DiskLruCache() {
        initDiskLruCache();
    }

    private void initDiskLruCache(){
        if (mDiskLruCache==null||mDiskLruCache.isClosed()){
            File file = FileUtil.getDiskCacheDir("CacheDir");
            if (!file.exists()){
                file.mkdirs();
            }

            try {
                mDiskLruCache = yishengma.cache.DiskLruCache.open(file,APP_VERSION,VALUE_COUNT,MAX_SIZE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void put(String url, Bitmap bitmap) {

        try {
            yishengma.cache.DiskLruCache.Editor editor = mDiskLruCache.edit(MD5Util.hashKeyForDisk(url));
            if (editor==null){
                mDiskLruCache.flush();
                return;
            }
            OutputStream outputStream = editor.newOutputStream(0);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            editor.commit();
            mDiskLruCache.flush();
            Log.e(TAG, "put: " );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Bitmap get(String url) {
        try {
            String key = MD5Util.hashKeyForDisk(url);
            yishengma.cache.DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot == null) {
                return null;
            }
            InputStream inputStream = snapshot.getInputStream(0);
            Log.e(TAG, "get: " );
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
