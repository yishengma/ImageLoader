package yishengma.cache;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import yishengma.request.BitmapRequest;
import yishengma.utils.MD5Util;

/**
 * 内存缓存
 */

public class MemoryCache  implements BitmapCache {
    private LruCache<String, Bitmap> mImageCache;
    private static final String TAG = "MemoryCache";

    public MemoryCache() {
        initMemoryCache();
    }

    private void initMemoryCache() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory() / 1024;
        mImageCache = new LruCache<String, Bitmap>(maxMemory / 4) {// maxSize 是缓存的容量的最大大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;//sizeOf 是计算每一项 的大小，根据这个大小判断容量大小
            }
        };
    }

    @Override
    public void put(BitmapRequest key, Bitmap bitmap) {

        mImageCache.put(MD5Util.hashKeyForDisk(key.imageUri),bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest key) {

        return mImageCache.get(MD5Util.hashKeyForDisk(key.imageUri));
    }

    @Override
    public void remove(BitmapRequest key) {
        mImageCache.remove(MD5Util.hashKeyForDisk(key.imageUri));
    }
}
