package yishengma.policy;

import yishengma.request.BitmapRequest;

/**
 * Created by asus on 18-8-31.
 */

public interface LoadPolicy {
     int compare(BitmapRequest request,BitmapRequest request1);
}
