package com.bignerdranch.android.mymanager.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.mymanager.AppInfo;
import com.bignerdranch.android.mymanager.fragment.AppFragment;
import com.bignerdranch.android.mymanager.fragment.ManagerFragment;

import java.util.List;

/**
 * Created by liubin on 2017/5/18.
 */

public class PagerFragmentAdapter extends FragmentStatePagerAdapter {

    private List<List<AppInfo>> mLists;
    private List<String> mTitles;


    public PagerFragmentAdapter(FragmentManager fm, List<List<AppInfo>> lists, List<String> titles) {
        super(fm);
        mLists = lists;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return AppFragment.newInstance(mLists.get(position));
    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
