package yishengma.loader;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import yishengma.cache.BitmapCache;
import yishengma.config.DisplayConfig;
import yishengma.imageloader.ImageLoader;
import yishengma.request.BitmapRequest;

/**
 * 图片加载的模板，使用模板模式
 */

public abstract class AbsLoader implements Loader {
    private static final BitmapCache bitmapCache = ImageLoader.getInstance().getBitmapCache();
    private static final String TAG = "AbsLoader";

    @Override
    public final void loadImage(BitmapRequest request) {
        //第一步，从缓存中获取
        Bitmap bitmap = bitmapCache.get(request);

        //第二步，缓存中没有
        if (bitmap == null) {
            //显示图片加载中

            showLoading(request);

            //第三步，从网络获取
            bitmap = onLoadImage(request);

            //第四步，缓存
            cacheBitmap(request, bitmap);
            //显示图片
            deliverUIThread(request, bitmap);


        } else {
            //缓存中有
            request.justCacheInMem = true;
            //显示图片
            deliverUIThread(request, bitmap);

        }


    }

    abstract Bitmap onLoadImage(BitmapRequest request);

    private void showLoading(final BitmapRequest request) {
        final ImageView imageView = request.getImageView();
        if (request.isImageViewTagValid() && hasLoadingPlaceholder(request.displayConfig)) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(DisplayConfig.loadingResId);
                }
            });
        }


    }


    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {
        if (bitmap != null && bitmapCache != null) {
            synchronized (bitmapCache) {
                bitmapCache.put(request, bitmap);
            }
        }
    }

    private void deliverUIThread(final BitmapRequest request, final Bitmap bitmap) {

        final ImageView imageView = request.getImageView();
        if (imageView == null) {
            return;
        }

        imageView.post(new Runnable() {
            @Override
            public void run() {

                updateImageView(request, bitmap);

            }
        });
    }

    private void updateImageView(BitmapRequest request, Bitmap bitmap) {
        boolean isSuccess = false;
        ImageView imageView = request.getImageView();
        String uri = request.imageUri;
        if (bitmap != null && imageView.getTag().equals(uri)) {
            imageView.setImageBitmap(bitmap);
            isSuccess = true;
        }

        if (bitmap == null && hasFailedPlaceholder(request.displayConfig)) {
            imageView.setImageResource(DisplayConfig.failedResId);
            isSuccess = false;

        }
        if (request.imageListener != null) {
            request.imageListener.onComplete(imageView, bitmap, uri, isSuccess);
        }
    }

    private boolean hasLoadingPlaceholder(DisplayConfig displayConfig) {
        return displayConfig != null && DisplayConfig.loadingResId > 0;
    }

    private boolean hasFailedPlaceholder(DisplayConfig displayConfig) {
        return displayConfig != null && DisplayConfig.failedResId > 0;
    }

}
