package com.bignerdranch.android.mymanager.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.LoginFilter;
import android.util.Log;

import com.bignerdranch.android.mymanager.AppInfo;
import com.bignerdranch.android.mymanager.MLManagerApplication;
import com.bignerdranch.android.mymanager.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by liubin on 2017/5/21.
 */

public class UtilsApp {

    private static final String TAG = "UtilsApp";


    public static File getAppFolder() {

        AppPreferences appPreferences = MLManagerApplication.getAppPreferences();
        return new File(appPreferences.getCustomPath());
    }

    public static File getDefaultAppFolder() {
        return new File(Environment.getExternalStorageDirectory() + "/MLManager");
    }

    //复制文件/data/app/com.javiersantos.mlmanager-1/base.apk 到
    // /storage/emulated/0/MLManager/com.javiersantos.mlmanager_1.0.4.1-debug.apk
    public static Boolean copyFile(AppInfo appInfo) {

        Boolean res = false;


        File initialFile = new File(appInfo.getSource());


        File finalFile = getOutputFilename(appInfo);
        Log.i(TAG, initialFile + "_" + finalFile);

        try {

            FileUtils.copyFile(initialFile, finalFile);
            res = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }


    public static void goToGooglePlay(Context context, String id) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + id)));
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + id)));
        }
    }

    /**
     * 获取要复制的目的文件路径+getApkFilename(com.qiyi.video + 版本号)
     * @param appInfo
     * @return
     */
    public static File getOutputFilename(AppInfo appInfo) {
        return new File(getAppFolder().getPath() + "/" + getApkFilename(appInfo) + ".apk");
    }


    /**
     * 返回apk的名称   格式 com.qiyi.video + 版本号
     * @param appInfo
     * @return
     */
    public static String getApkFilename(AppInfo appInfo) {

        AppPreferences appPreferences = MLManagerApplication.getAppPreferences();
        String res;

        switch (appPreferences.getCustomFilename()) {

            case "1":
                Log.i(TAG, appInfo.getAPK());
                res = appInfo.getAPK() + "_" + appInfo.getVersion();
                break;
            case "2":

                res = appInfo.getAPK() + "_" + appInfo.getVersion();
                break;
            case "4":

                res = appInfo.getAPK();
                break;
            default:
                res = appInfo.getAPK();
                break;
        }
        return res;
    }


    public static Intent getShareIntent(File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.setType("application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return intent;
    }

    public static Boolean extractMLManagerPro(Context context, AppInfo appInfo) {
        Boolean res = false;
        File finalFile = new File(getAppFolder().getPath(), getApkFilename(appInfo) + ".png");

        try {
            File fileUri = new File(context.getCacheDir(), getApkFilename(appInfo) + ".png");
            FileOutputStream out = new FileOutputStream(fileUri);
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.banner_troll);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            FileUtils.moveFile(fileUri, finalFile);
            res = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }
}
