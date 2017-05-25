package com.bignerdranch.android.mymanager.async;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bignerdranch.android.mymanager.AppInfo;
import com.bignerdranch.android.mymanager.R;
import com.bignerdranch.android.mymanager.utils.UtilsApp;
import com.bignerdranch.android.mymanager.utils.UtilsDialog;

import java.io.File;

/**
 * Created by lfs-ios on 23/5/17.
 */

public class ExtractFileInBackground extends AsyncTask<Void, String, Boolean> {

    private static final String TAG = "ExtractFileInBackground";
    private Context mContext;
    private Activity mActivity;
    private ProgressDialog progressDialog;
    private AppInfo mAppInfo;

    public ExtractFileInBackground(AppInfo appInfo, Context context, ProgressDialog progressDialog) {
        mActivity = (Activity) context;
        mAppInfo = appInfo;
        mContext = context;
        this.progressDialog = progressDialog;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Boolean status = false;

//        if (UtilsApp.checkPermissions(activity)) {
//            if (!appInfo.getAPK().equals(MLManagerApplication.getProPackage())) {
//                status = UtilsApp.copyFile(appInfo);
//            } else {
//                status = UtilsApp.extractMLManagerPro(context, appInfo);
//            }
//        }
        status =  UtilsApp.copyFile(mAppInfo);
        return status;
    }


    @Override
    protected void onPostExecute(Boolean status) {
        super.onPostExecute(status);

        Log.i(TAG, String.valueOf(status));
        progressDialog.dismiss();

        if (status) {



//            UtilsDialog.showSnackbar(mActivity,
//                    String.format(mContext.getResources().getString(R.string.dialog_saved_description),
//                            mAppInfo.getName(), UtilsApp.getApkFilename(mAppInfo)),
//                    mContext.getResources().getString(R.string.button_undo),
//                    UtilsApp.getOutputFilename(mAppInfo), 1).show();
            Snackbar.make(mActivity.findViewById(R.id.main_layout),
                    String.format(mContext.getResources().getString(R.string.dialog_saved_description), mAppInfo.getName(),
                            UtilsApp.getApkFilename(mAppInfo)),
                    Snackbar.LENGTH_SHORT)
                    .setAction(mContext.getResources().getString(R.string.button_undo), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //需要删除复制好的文件
                            File file = UtilsApp.getOutputFilename(mAppInfo);
                            Log.i("TAG", String.valueOf(file));
                            file.delete();
                        }
                    }).show();
        } else {

            //前面复制失败，
            //context.getResources().getString(R.string.dialog_extract_fail),
                    //context.getResources().getString(R.string.dialog_extract_fail_description)


            AlertDialog.Builder dialog =  new AlertDialog.Builder(mActivity)
                    .setTitle(mContext.getResources().getString(R.string.dialog_extract_fail))
                    .setMessage(mContext.getResources().getString(R.string.dialog_extract_fail_description))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            dialog.show();
        }
    }
}
