package yishengma.imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 内存缓存
 */

public class MemoryCache  implements ImageCache{
    private LruCache<String, Bitmap> mImageCache;

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
    public void put(String url, Bitmap bitmap) {
        mImageCache.put(url, bitmap);
    }

    @Override
    public Bitmap get(String url) {
        return mImageCache.get(url);
    }
}
