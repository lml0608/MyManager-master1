package com.bignerdranch.android.mymanager.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.mymanager.AppInfo;
import com.bignerdranch.android.mymanager.R;

import org.w3c.dom.Text;

import java.io.Serializable;

/**
 * Created by liubin on 2017/5/19.
 */

public class DetailFragment extends Fragment {

    private static final String ARG_APP_INFO = "arg_app_info";
    private static final String TAG = "DetailFragment";
    private AppInfo mAppInfo;

    public static DetailFragment newInstance(String name, String apk,  String version,
                                             String source, String data, Drawable icon, Boolean system) {


        Bundle args = new Bundle();

        args.putSerializable(ARG_APP_INFO, name);
        args.putSerializable(ARG_APP_INFO, apk);
        args.putSerializable(ARG_APP_INFO, version);
        args.putSerializable(ARG_APP_INFO, source);
        args.putSerializable(ARG_APP_INFO, data);
        args.putSerializable(ARG_APP_INFO, (Serializable) icon);
        args.putSerializable(ARG_APP_INFO, system);



        DetailFragment fragment = new DetailFragment();

        fragment.setArguments(args);
        return fragment;
    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);

//        //AppInfo对象，app页面数据源
//        mAppInfo = (AppInfo) getArguments().getSerializable(ARG_APP_INFO);
//        String appName = (String) getArguments().getSerializable("app_name");
//        String appApk = (String) getArguments().getSerializable("app_apk");
//        String appVersion = (String) getArguments().getSerializable("app_version");
//        String appSource = (String) getArguments().getSerializable("app_source");
//        String appData = (String) getArguments().getSerializable("app_data");
//        Serializable bitmap = getArguments().getSerializable("app_icon");
//        Drawable appIcon = new BitmapDrawable(getResources(), bitmap);
//        Boolean appIsSystem = (Boolean) getArguments().getSerializable("app_isSystem");
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        setScreenElements(view);

        return view;
    }


    /**
     * 初始化本页面用到的数据
     */
    private void getInitialConfiguration() {

        String appName = mAppInfo.getName();
        String appApk = mAppInfo.getAPK();
        String appVersion = mAppInfo.getVersion();
        String appSource = mAppInfo.getSource();
        String appData = mAppInfo.getData();

        Bitmap bitmap = ((BitmapDrawable) mAppInfo.getIcon()).getBitmap();
        //Drawable appIcon = mAppInfo.getIcon();
        boolean appIsSystem = mAppInfo.isSystem();


    }


    private void setScreenElements(View view) {

        TextView header = (TextView) view.findViewById(R.id.header);
        ImageView icon = (ImageView) view.findViewById(R.id.app_icon);
        TextView name = (TextView) view.findViewById(R.id.app_name);
        TextView version = (TextView) view.findViewById(R.id.app_version);
        TextView apk = (TextView) view.findViewById(R.id.app_apk);


        Log.i(TAG, mAppInfo.getName());
        Log.i(TAG, String.valueOf(mAppInfo.getIcon()));
        icon.setImageDrawable(mAppInfo.getIcon());
        name.setText(mAppInfo.getName());
        apk.setText(mAppInfo.getAPK());
        version.setText(mAppInfo.getVersion());

    }




























}
