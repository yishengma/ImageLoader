package yishengma.loader;

import android.graphics.Bitmap;

import yishengma.request.BitmapRequest;

/**
 * Created by asus on 18-8-31.
 */

public class NullLoader extends AbsLoader {

    @Override
    Bitmap onLoadImage(BitmapRequest request) {
        return null;
    }
}
