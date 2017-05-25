package com.bignerdranch.android.mymanager.activites;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.mymanager.AppInfo;
import com.bignerdranch.android.mymanager.R;
import com.bignerdranch.android.mymanager.fragment.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    private static final String APP_INFO = "app_info";
    private static final String TAG = "DetailActivity";
//    @Override
//    public DetailFragment createFragment() {
//        //该activity获取上个界面传递过来的i，发送给CrimeFragment
//        //AppInfo appInfo = (AppInfo) getIntent().getSerializableExtra(APP_INFO);
//
//        String appName = getIntent().getStringExtra("app_name");
//        String appApk = getIntent().getStringExtra("app_apk");
//        String appVersion = getIntent().getStringExtra("app_version");
//        String appSource = getIntent().getStringExtra("app_source");
//        String appData = getIntent().getStringExtra("app_data");
//        Bitmap bitmap = getIntent().getParcelableExtra("app_icon");
//        Drawable appIcon = new BitmapDrawable(getResources(), bitmap);
//        Boolean appIsSystem = getIntent().getExtras().getBoolean("app_isSystem");
//
//        return DetailFragment.newInstance(appName,appApk,appVersion,appSource,appData,appIcon,appIsSystem);
//    }

    private AppInfo appInfo;


    public static Intent newIntent(Context packageContext, AppInfo appInfo) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        //intent.putExtra(APP_INFO, appInfo);
        intent.putExtra("app_name", appInfo.getName());
        intent.putExtra("app_apk", appInfo.getAPK());
        intent.putExtra("app_version", appInfo.getVersion());
        intent.putExtra("app_source", appInfo.getSource());
        intent.putExtra("app_data", appInfo.getData());
        Bitmap bitmap = ((BitmapDrawable) appInfo.getIcon()).getBitmap();
        intent.putExtra("app_icon", bitmap);
        intent.putExtra("app_isSystem", appInfo.isSystem());

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail);
        getInitialConfiguration();

        setScreenElements();

    }



    private void getInitialConfiguration() {

        String appName = getIntent().getStringExtra("app_name");
        String appApk = getIntent().getStringExtra("app_apk");
        String appVersion = getIntent().getStringExtra("app_version");
        String appSource = getIntent().getStringExtra("app_source");
        String appData = getIntent().getStringExtra("app_data");
        Bitmap bitmap = getIntent().getParcelableExtra("app_icon");
        Drawable appIcon = new BitmapDrawable(getResources(), bitmap);
        Boolean appIsSystem = getIntent().getExtras().getBoolean("app_isSystem");

        appInfo = new AppInfo(appName, appApk, appVersion, appSource, appData, appIcon, appIsSystem);


    }


    private void setScreenElements() {

        TextView header = (TextView) findViewById(R.id.header);
        ImageView icon = (ImageView) findViewById(R.id.app_icon);
        TextView name = (TextView) findViewById(R.id.app_name);
        TextView version = (TextView) findViewById(R.id.app_version);
        TextView apk = (TextView) findViewById(R.id.app_apk);


        Log.i(TAG, appInfo.getName());
        Log.i(TAG, String.valueOf(appInfo.getIcon()));
        icon.setImageDrawable(appInfo.getIcon());
        name.setText(appInfo.getName());
        apk.setText(appInfo.getAPK());
        version.setText(appInfo.getVersion());

    }

}
