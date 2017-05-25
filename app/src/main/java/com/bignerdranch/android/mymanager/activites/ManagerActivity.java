package com.bignerdranch.android.mymanager.activites;

import com.bignerdranch.android.mymanager.fragment.ManagerFragment;

/**
 * Created by liubin on 2017/5/18.
 */

public class ManagerActivity extends SignleFragmentActivity {


    @Override
    public ManagerFragment createFragment() {
        return new ManagerFragment();
    }




}
