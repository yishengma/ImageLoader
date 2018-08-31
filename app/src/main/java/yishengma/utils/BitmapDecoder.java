package yishengma.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;
/**
 * Created by asus on 18-8-31.
 */

public abstract class BitmapDecoder {

    /**
     * @param options
     * @return
     */
    public abstract Bitmap decodeBitmapWithOption(Options options);

    /**
     * @param width 图片的目标宽度
     * @param height 图片的目标高度
     * @return
     */
    public final Bitmap decodeBitmap(int width, int height) {
        // 如果请求原图,则直接加载原图
        if (width <= 0 || height <= 0) {
            return decodeBitmapWithOption(null);
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        decodeBitmapWithOption(options);
        configBitmapOptions(options, width, height);
        return decodeBitmapWithOption(options);
    }

    /**
     * 加载原图
     *
     * @return
     */
    public Bitmap decodeOriginBitmap() {
        return decodeBitmapWithOption(null);
    }

    /**
     * @param options
     * @param width
     * @param height
     * @param
     */
    protected void configBitmapOptions(Options options, int width, int height) {
        // 设置缩放比例
        options.inSampleSize = computeInSmallSize(options, width, height);

        // 图片质量
        options.inPreferredConfig = Config.RGB_565;
        // 设置为false,解析Bitmap对象加入到内存中
        options.inJustDecodeBounds = false;

    }

    private int computeInSmallSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);


            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;


            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down
            // further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }
}
