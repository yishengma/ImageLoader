package yishengma.policy;

import yishengma.request.BitmapRequest;

/**
 * Created by asus on 18-8-31.
 */

public class SerialPolicy implements LoadPolicy {

    @Override
    public int compare(BitmapRequest request, BitmapRequest request1) {
        return request.serialNum - request1.serialNum;
    }
}
