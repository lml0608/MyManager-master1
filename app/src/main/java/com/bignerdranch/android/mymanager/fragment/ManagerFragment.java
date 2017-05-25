package com.bignerdranch.android.mymanager.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.mymanager.Adapter.PagerFragmentAdapter;
import com.bignerdranch.android.mymanager.AppInfo;
import com.bignerdranch.android.mymanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubin on 2017/5/18.
 */

public class ManagerFragment extends Fragment {


    //页面数据
    private List<AppInfo> appList;
    private List<AppInfo> appSystemList;
    private List<List<AppInfo>> mAppDatas;

    //页面数
//    private List<TabDetailPager> mDetailPagers;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private Context mContext;
    private Activity activity;
    private List<String> mTitles;
    private View mView;

    private static final int PERMISSION_REQUEST_CAMERA = 0;

    private View mLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.activity_main, container, false);


        initView();
        initViewPage();
        activity = getActivity();

        new getInstalledApps().execute();

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //requestCameraPermission();

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CAMERA);
        }
        return mView;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.

                // Permission request was denied.
//                Snackbar.make(mView, "Camera permission request was denied.",
//                        Snackbar.LENGTH_SHORT)
//                        .show();
            }
        }
    }

    private void initViewPage() {

        mTabLayout = (TabLayout) mView.findViewById(R.id.tab_layout_main);
        mViewPager = (ViewPager) mView.findViewById(R.id.view_pager_main);

        mTitles = new ArrayList<>();
        mTitles.add("应用");
        mTitles.add("系统");
        //titles.add("喜爱");
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitles.get(1)));

        /*预加载2页，ViewPager默认每次都会加载下一个页面，可以选择在页面被选择中时才加载当前页面数据*/
        //mViewPager.setOffscreenPageLimit(2);


    }

    private void initView() {
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        Toolbar toolbar = (Toolbar) mView.findViewById(R.id.toolbar_main);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        activity.setSupportActionBar(toolbar);

    }


    class getInstalledApps extends AsyncTask<Void, String, Void> {

        public getInstalledApps() {

            appList = new ArrayList<>();
            appSystemList = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... params) {

            PackageManager packageManager = getActivity().getPackageManager();
            List<PackageInfo> installedPackages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA);

            for (PackageInfo packageInfo : installedPackages) {

                if (!(packageManager.getApplicationLabel(packageInfo.applicationInfo).equals("")
                        || packageInfo.packageName.equals(""))) {
                    if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                        //用户应用

                        try {
                            String name = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
                            String packageName = packageInfo.packageName;
                            String versionName = packageInfo.versionName;
                            String sourceDir = packageInfo.applicationInfo.sourceDir;

                            String dataDir = packageInfo.applicationInfo.dataDir;
                            Drawable Icon = packageManager.getApplicationIcon(packageInfo.applicationInfo);

                            boolean system = false;

                            AppInfo tempApp = new AppInfo(name, packageName, versionName,sourceDir,
                                    dataDir, Icon, system);

                            appList.add(tempApp);

                        } catch (OutOfMemoryError e) {
                            String name = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
                            String packageName = packageInfo.packageName;
                            String versionName = packageInfo.versionName;
                            String sourceDir = packageInfo.applicationInfo.sourceDir;

                            String dataDir = packageInfo.applicationInfo.dataDir;
                            Drawable Icon = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                Icon = getResources().getDrawable(R.drawable.ic_android, null);
                            }

                            boolean system = false;

                            AppInfo tempApp = new AppInfo(name, packageName, versionName,sourceDir,
                                    dataDir, Icon, system);

                            appList.add(tempApp);
                        }

                    } else {

                        //系统应用

                        try {
                            String name = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
                            String packageName = packageInfo.packageName;
                            String versionName = packageInfo.versionName;
                            String sourceDir = packageInfo.applicationInfo.sourceDir;

                            String dataDir = packageInfo.applicationInfo.dataDir;
                            Drawable Icon = packageManager.getApplicationIcon(packageInfo.applicationInfo);

                            boolean system = false;

                            AppInfo tempApp = new AppInfo(name, packageName, versionName,sourceDir,
                                    dataDir, Icon, true);

                            appSystemList.add(tempApp);

                        } catch (OutOfMemoryError e) {
                            String name = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
                            String packageName = packageInfo.packageName;
                            String versionName = packageInfo.versionName;
                            String sourceDir = packageInfo.applicationInfo.sourceDir;

                            String dataDir = packageInfo.applicationInfo.dataDir;
                            Drawable Icon = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                                Icon = getResources().getDrawable(R.drawable.ic_android, null);
                            }

                            boolean system = true;

                            AppInfo tempApp = new AppInfo(name, packageName, versionName,sourceDir,
                                    dataDir, Icon, system);

                            appSystemList.add(tempApp);
                        }

                    }
                }
            }



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mAppDatas = new ArrayList<>();
            mAppDatas.add(appList);
            mAppDatas.add(appSystemList);


            initData();
        }
    }

    private void initData() {

//        mDetailPagers = new ArrayList<>();

//        mDetailPagers.add(new TabDetailPager(activity,appList));
//        mDetailPagers.add(new TabDetailPager(activity,appSystemList));

        PagerFragmentAdapter adapter = new PagerFragmentAdapter(getFragmentManager(),mAppDatas,mTitles);
        //ViewPagerAdapter
        mViewPager.setAdapter(adapter);
        //TabLayout与ViewPager关联
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Snackbar.make(mView,
                    "Permission is not available. Requesting camera permission.",
                    Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CAMERA);

        }
    }

}




















