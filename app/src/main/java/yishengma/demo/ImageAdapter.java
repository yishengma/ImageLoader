package yishengma.demo;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import yishengma.cache.DoubleCache;
import yishengma.imageloader.ImageLoader;
import yishengma.config.ImageLoaderConfig;
import yishengma.policy.LoadPolicy;
import yishengma.imageloader.R;
import yishengma.policy.SerialPolicy;

/**
 * Created by asus on 18-8-29.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<String> mUrlList;
    private static final String TAG = "ImageAdapter";

    public ImageAdapter(List<String> urlList) {
        mUrlList = urlList;
        ImageLoaderConfig config = new ImageLoaderConfig.Builder()
                .setBitmapCache(new DoubleCache())
                .setLoadingPlaceholder(R.drawable.ic_launcher_background)
                .setNotFoundPlaceholder(R.drawable.ic_launcher_background)
                .setLoadPolicy(new SerialPolicy())
                .create();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        holder.mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if(!mUrlList.get(position).equals(holder.mImageView.getTag())) {
            holder.mImageView.setTag(mUrlList.get(position));
            ImageLoader.getInstance().displayImage(holder.mImageView, mUrlList.get(position), new ImageLoader.ImageListener() {
                @Override
                public void onComplete(ImageView imageView, Bitmap bitmap, String uri, boolean isSuccess) {
                    Log.e(TAG, "onComplete: ");
                }
            });
        }
        Log.e(TAG, "onBindViewHolder: Url" + holder.mImageView.getTag().equals(mUrlList.get(position)));
        Log.e(TAG, "onBindViewHolder: " + position);

    }

    @Override
    public int getItemCount() {
        return mUrlList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imv_image);
            mTextView = itemView.findViewById(R.id.tv_position);
        }
    }


}
