package com.bignerdranch.android.mymanager.activites;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.mymanager.AppInfo;
import com.bignerdranch.android.mymanager.R;
import com.bignerdranch.android.mymanager.async.ExtractFileInBackground;
import com.bignerdranch.android.mymanager.fragment.DetailFragment;
import com.bignerdranch.android.mymanager.utils.UtilsApp;

import java.io.File;

public class DetailActivity extends AppCompatActivity {

    private static final String APP_INFO = "app_info";
    private static final String TAG = "DetailActivity";
    private static final int UNINSTALL_REQUEST_CODE = 1;
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

    private Context context;
    private Activity activity;


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

        this.context = this;
        this.activity = (Activity) context;
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
        final ImageView icon = (ImageView) findViewById(R.id.app_icon);
        ImageView icon_googleplay = (ImageView) findViewById(R.id.app_googleplay);
        TextView name = (TextView) findViewById(R.id.app_name);
        TextView version = (TextView) findViewById(R.id.app_version);
        TextView apk = (TextView) findViewById(R.id.app_apk);

        final CardView googleplay = (CardView) findViewById(R.id.id_card);
        final CardView start = (CardView) findViewById(R.id.start_card);
        CardView extract = (CardView) findViewById(R.id.extract_card);
        CardView uninstall = (CardView) findViewById(R.id.uninstall_card);


//        Log.i(TAG, appInfo.getName());
//        Log.i(TAG, String.valueOf(appInfo.getIcon()));
        icon.setImageDrawable(appInfo.getIcon());
        name.setText(appInfo.getName());
        apk.setText(appInfo.getAPK());
        version.setText(appInfo.getVersion());

        //判断是不是系统应用
        if (appInfo.isSystem()) {
            //跳转到google play 的图标不可见
            icon_googleplay.setVisibility(View.GONE);
        } else {
            googleplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UtilsApp.goToGooglePlay(context, appInfo.getAPK());
                }
            });
            //长按
            googleplay.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //长按复制到剪贴板

                    ClipData clipData;

                    ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

                    clipData = ClipData.newPlainText("text", appInfo.getAPK());
                    clipboardManager.setPrimaryClip(clipData);

                    Snackbar.make(v,
                            String.format(context.getResources().getString(R.string.copied_clipboard)),
                            Snackbar.LENGTH_SHORT).show();
                    //返回true不会让点击和长按同时发生
                    return true;
                }
            });


            //打开应用
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Intent intent = getPackageManager().getLaunchIntentForPackage(appInfo.getAPK());
                        startActivity(intent);
                    } catch (NullPointerException e) {

                        e.printStackTrace();


                    }
                }
            });
            //提取

            extract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //progressDialog.setMessage(mContext.getResources().getString(R.string.dialog_saving));
                    ProgressDialog progressDialog =  ProgressDialog.show(context,String.format(context.getResources().getString(R.string.dialog_saving),
                            appInfo.getName()),getResources().getString(R.string.dialog_saving_description));

                    new ExtractFileInBackground(appInfo, context, progressDialog).execute();
                }
            });


            //卸载应用

            uninstall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);

                    intent.setData(Uri.parse("package:" + appInfo.getAPK()));
                    intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);

                    startActivityForResult(intent, UNINSTALL_REQUEST_CODE);;
                }
            });


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UNINSTALL_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                Log.i("App", "OK");
                Intent intent = new Intent(context, ManagerActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                finish();
            } else if (resultCode == RESULT_CANCELED) {
                Log.i("App", "CANCEL");
            }
        }
    }
}
