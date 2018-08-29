package yishengma.imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by asus on 18-8-29.
 */

public interface ImageCache {
    void put(String url,Bitmap bitmap);
    Bitmap get(String url);
}
