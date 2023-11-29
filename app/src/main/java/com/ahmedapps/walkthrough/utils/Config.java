package com.ahmedapps.walkthrough.utils;

import android.content.Context;

public class Config {


    // غير كما تشاء
    // Change as you want

// ------------------------------------------------------------------------------------------------------------------------------

    public static int USER_DEFAULT_COINS = 155;
    public static int TO_START_COINS = 200;
    public static int TO_OPEN_VIDEOS_COINS = 200;

    public static int WATCH_AD_COINS = 50;

    public static int PUZZLE_COINS = 150;
    public static int SPIN_WHEEL_COINS = 500;


    public static int TOTAL_QUIZ_COINS = 150;
    public static int ANSWERED_90_PERCENT_QUIZ_COINS = 100;
    public static int ANSWERED_70_PERCENT_QUIZ_COINS = 70;
    public static int ANSWERED_50_PERCENT_QUIZ_COINS = 30;
    public static int ANSWERED_10_PERCENT_QUIZ_COINS = 10;


// ------------------------------------------------------------------------------------------------------------------------------


    // ------------------------------------------------------------------------------------------------------------------------------

    // لا تغير شيء هنا
    // DO NOT CHANGE THIS

    public static void setCoins(Context context, int coins) {
        COINS = coins;
        Shared.setInt(context, COINS_SAVED_KEY, COINS);
    }

    public static void addCoins(Context context, int coins) {
        COINS = COINS + coins;
        Shared.setInt(context, COINS_SAVED_KEY, COINS);
    }

    public static int getCoins(Context context) {
        return Shared.getInt(context, COINS_SAVED_KEY, USER_DEFAULT_COINS);
    }



    public static MediaPlayerManager mediaPlayerManager;


    public static Data DATA;

    public static int COINS;
    public final static String COINS_SAVED_KEY = "coins";
    public final static String SPINS_SAVED_KEY = "spins";

    public static boolean IS_CAMERA_PERMISSION_AVAILABLE = false;

    public static boolean IS_PLAY_MUSIC = true;

}
