package yishengma.demo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import yishengma.imageloader.DoubleCache;
import yishengma.imageloader.ImageLoader;
import yishengma.imageloader.R;

/**
 * Created by asus on 18-8-29.
 */

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> mUrlList;
    private ImageLoader mImageLoader;
    private static final String TAG = "ImageAdapter";
    public ImageAdapter(List<String> urlList) {
        mUrlList = urlList;
        mImageLoader = new ImageLoader();
        mImageLoader.setImageCache(new DoubleCache());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageView imageView = holder.itemView.findViewById(R.id.imv_image);
        mImageLoader.displayImage(mUrlList.get(position),imageView);

    }

    @Override
    public int getItemCount() {
        return mUrlList.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
