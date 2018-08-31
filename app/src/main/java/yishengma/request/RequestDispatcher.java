package yishengma.request;

import android.util.Log;

import java.util.concurrent.BlockingQueue;

import yishengma.loader.Loader;
import yishengma.loader.LoaderManager;

/**
 * Created by asus on 18-8-31.
 */

public class RequestDispatcher extends Thread {
    private BlockingQueue<BitmapRequest> mRequests;


    public RequestDispatcher(BlockingQueue<BitmapRequest> requests) {
        mRequests = requests;
    }

    @Override
    public void run() {
        try {
            while (!this.isInterrupted()) {
                BitmapRequest request = mRequests.take();
                if (request.isCancel) {
                    continue;
                }

                String schema = parseSchema(request.imageUri);
                Loader imageLoader = LoaderManager.getInstance().getLoader(schema);
                imageLoader.loadImage(request);


            }
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
    }

    //检查 Uri
    private String parseSchema(String uri) {
        if (uri.contains("://")) {
            return uri.split("://")[0];
        } else {
            Log.e(getName(), "### wrong scheme, image uri is : " + uri);
        }

        return "";
    }
}
