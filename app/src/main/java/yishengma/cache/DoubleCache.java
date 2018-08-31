package yishengma.cache;

import android.graphics.Bitmap;
import android.util.Log;

import yishengma.request.BitmapRequest;

/**
 * Created by asus on 18-8-29.
 */

public class DoubleCache implements BitmapCache {
    private MemoryCache mMemoryCache;
    private DiskLruCache mDiskCache;
    private static final String TAG = "DoubleCache";

    public DoubleCache() {
        mMemoryCache  = new MemoryCache();
        mDiskCache = new DiskLruCache();

    }

    @Override
    public void put(BitmapRequest key, Bitmap bitmap) {

        mDiskCache.put(key,bitmap);
        mMemoryCache.put(key,bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest key) {

        Bitmap bitmap = mMemoryCache.get(key);
        if (bitmap==null){
            bitmap = mDiskCache.get(key);
        }
        return bitmap;
    }

    @Override
    public void remove(BitmapRequest key) {
          mDiskCache.remove(key);
          mMemoryCache.remove(key);
    }


}
