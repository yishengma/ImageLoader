package yishengma.request;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import yishengma.config.DisplayConfig;
import yishengma.imageloader.ImageLoader;
import yishengma.policy.LoadPolicy;
import yishengma.utils.ImageViewHelper;
import yishengma.utils.MD5Util;

/**
 * Created by asus on 18-8-31.
 */

public class BitmapRequest implements Comparable<BitmapRequest> {

    Reference<ImageView> viewReference;

    public DisplayConfig displayConfig;

    public ImageLoader.ImageListener imageListener;

    public String imageUri;

    public String imageUriMD5;

    public int serialNum = 0;

    public boolean isCancel = false;

    //用于判读是从网络加载还是从本地的文件加载
    //如果是本地的加载就不需要缓存到内存中去即 false
    public boolean justCacheInMem = false;

    public LoadPolicy loadPolicy = ImageLoader.getInstance().mConfig.mLoadPolicy;


    public BitmapRequest(ImageView imageView, String uri, DisplayConfig config, ImageLoader.ImageListener listener) {
        viewReference = new WeakReference<>(imageView);
        displayConfig = config;
        imageUri = uri;
        imageView.setTag(uri);

        imageUriMD5 = MD5Util.hashKeyForDisk(imageUri);
    }

    public void setLoadPolicy(LoadPolicy loadPolicy) {
        if (loadPolicy != null) {
            this.loadPolicy = loadPolicy;
        }

    }

    public boolean isImageViewTagValid() {
        return viewReference.get() != null && viewReference.get().getTag().equals(imageUri);
    }

    public ImageView getImageView() {
        return viewReference.get();
    }

    public int getImageViewWidth(){
        return ImageViewHelper.getImageViewWidth(viewReference.get());

    }

    public int getImageViewHeigth(){
        return ImageViewHelper.getImageViewHeight(viewReference.get());
    }

    @Override
    public int compareTo(@NonNull BitmapRequest bitmapRequest) {
        return loadPolicy.compare(this, bitmapRequest);
    }

    /**
     * 重写 equals
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        //如果是同一个对象直接返回，可省略一些不必要的比较
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        //如果不是同一类对象直接 false
        if (getClass() != obj.getClass()) {
            return false;
        }


        //转换为同一类对象 才能进行比较
        BitmapRequest request = (BitmapRequest) obj;
        if (imageUri == null) {
            if (request.imageUri != null) {
                return false;
            }
        } else if (viewReference == null) {
            if (request.viewReference != null) {
                return false;
            }
        } else if (!viewReference.get().equals(request.viewReference.get())) {
            return false;
        }
        if (serialNum != request.serialNum) {
            return false;
        }


        return true;


    }

    /**
     * 重写 equals 就要重写 hashCode
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = 1; //选择一个非 0 常数

        //按下列公式计算
        result = 31 * result  + (imageUri==null? 0 : imageUri.hashCode());
        result = 31 * result + (viewReference==null? 0 :viewReference.hashCode());
        result = 31 * result + serialNum;

        return result;
    }
}
