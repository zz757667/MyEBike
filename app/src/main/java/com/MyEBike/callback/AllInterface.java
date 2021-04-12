package com.MyEBike.callback;

/**
 * Created by wwj on 21/4/6.
 */

public class AllInterface {

    public  interface OnMenuSlideListener{
        void onMenuSlide(float offset);
    }
    public  interface IUnlock{
        void onUnlock();
    }
    public  interface IUpdateLocation{
        void updateLocation(String totalTime,String totalDistance);
        void endLocation();
    }
}
