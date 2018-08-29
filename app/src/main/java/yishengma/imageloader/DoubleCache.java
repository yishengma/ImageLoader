package yishengma.imageloader;

import android.graphics.Bitmap;

/**
 * Created by asus on 18-8-29.
 */

public class DoubleCache implements ImageCache {
    private MemoryCache mMemoryCache;
    private DiskCache mDiskCache;

    public DoubleCache() {
        mMemoryCache  = new MemoryCache();
        mDiskCache = new DiskCache();

    }

    @Override
    public void put(String url, Bitmap bitmap) {
       mDiskCache.put(url,bitmap);
       mMemoryCache.put(url,bitmap);
    }

    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = mMemoryCache.get(url);
        if (bitmap==null){
            bitmap = mDiskCache.get(url);
        }
        return bitmap;
    }
}
