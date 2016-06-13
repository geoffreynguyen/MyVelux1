package com.myvelux.myvelux;

import android.content.Intent;
import android.util.Log;

/**
 * Created by geoffrey on 03/06/16.
 */
public class MenuUpdate {
    public  Intent clientMenu(Intent intent, Reservation resa, int updateClient, String backActivity){
        intent.putExtra("resa",resa);
        intent.putExtra("updateClient",updateClient);
        intent.putExtra("backActivity",backActivity);
        return intent;
    }
}
