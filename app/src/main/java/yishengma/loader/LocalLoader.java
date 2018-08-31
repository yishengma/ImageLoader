package yishengma.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;

import yishengma.request.BitmapRequest;
import yishengma.utils.BitmapDecoder;

/**
 * Created by asus on 18-8-31.
 */

public class LocalLoader extends AbsLoader {

    @Override
    Bitmap onLoadImage(BitmapRequest request) {
        final String imagePath = Uri.parse(request.imageUri).getPath();
        File file = new File(imagePath);
        if (!file.exists()){
            return null;
        }

        //从本地文件中加载的图片，不需要再缓存到本地
        //只需要缓存到 内存就可以
        request.justCacheInMem = true;

        BitmapDecoder decoder = new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(imagePath,options);
            }
        };

        return decoder.decodeBitmap(request.getImageViewWidth(),request.getImageViewHeigth());
    }
}
