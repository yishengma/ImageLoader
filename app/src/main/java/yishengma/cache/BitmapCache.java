package yishengma.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import yishengma.request.BitmapRequest;

/**
 * Created by asus on 18-8-29.
 */

public interface BitmapCache {
    void put(BitmapRequest key,Bitmap bitmap);
    Bitmap get(BitmapRequest key);
    void remove(BitmapRequest key);
}
