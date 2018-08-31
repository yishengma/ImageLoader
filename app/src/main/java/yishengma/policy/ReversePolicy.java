package yishengma.policy;

import android.arch.lifecycle.LifecycleObserver;

import yishengma.request.BitmapRequest;

/**
 *  逆序加载策略，最后加的请求先执行
 */
public class ReversePolicy implements LoadPolicy {


    @Override
    public int compare(BitmapRequest request, BitmapRequest request1) {
        return request1.serialNum-request.serialNum;
    }
}
