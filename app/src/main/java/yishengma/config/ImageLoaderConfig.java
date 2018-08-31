package yishengma.config;

import yishengma.cache.BitmapCache;
import yishengma.cache.MemoryCache;
import yishengma.policy.LoadPolicy;
import yishengma.policy.SerialPolicy;

/**
 * Created by asus on 18-8-31.
 */

public class ImageLoaderConfig {
    public BitmapCache mBitmapCache = new MemoryCache();
    public DisplayConfig mDisplayConfig = new DisplayConfig();
    public LoadPolicy mLoadPolicy = new SerialPolicy();
    public int mThreadCount = Runtime.getRuntime().availableProcessors() + 1;

    private ImageLoaderConfig() {
    }


    /**
     * Builder 模式
     */
    public static class Builder {
        private BitmapCache mBitmapCache = new MemoryCache();
        private DisplayConfig mDisplayConfig = new DisplayConfig();

        private LoadPolicy mLoadPolicy = new SerialPolicy();
        private int mThreadCount = Runtime.getRuntime().availableProcessors() + 1;

        public ImageLoaderConfig create() {
            ImageLoaderConfig config = new ImageLoaderConfig();
            applyConfig(config);
            return config;
        }

        private void applyConfig(ImageLoaderConfig config) {
            config.mBitmapCache = mBitmapCache;
            config.mDisplayConfig = mDisplayConfig;
            config.mLoadPolicy = mLoadPolicy;
            config.mThreadCount = mThreadCount;
        }

        //return this  支持链式调用
        public Builder setThreadCount(int threadCount) {
            mThreadCount = Math.max(1,threadCount);
            return this;
        }

        /**
         * 实现可以注入不同的 BitmapCache ,依赖抽象
         *
         * @param bitmapCache
         */
        public Builder setBitmapCache(BitmapCache bitmapCache) {
            mBitmapCache = bitmapCache;
            return this;
        }

        public Builder setLoadingPlaceholder(int resId) {
            DisplayConfig.failedResId = resId;
            return this;
        }

        public Builder setNotFoundPlaceholder(int resId) {
            DisplayConfig.loadingResId = resId;
            return this;
        }

        public Builder setLoadPolicy(LoadPolicy loadPolicy) {
            if (loadPolicy != null) {
                mLoadPolicy = loadPolicy;
            }
            return this;
        }

    }


}
