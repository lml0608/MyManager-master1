package com.bignerdranch.android.mymanager.activites;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bignerdranch.android.mymanager.R;
import com.bignerdranch.android.mymanager.fragment.DetailFragment;
import com.bignerdranch.android.mymanager.fragment.ManagerFragment;

public abstract class SignleFragmentActivity extends AppCompatActivity {

    public abstract ManagerFragment createFragment();

    @LayoutRes
    public int getLayoutId() {

        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        //获取Fragment管理者对象
        FragmentManager fm = getSupportFragmentManager();
        //从FragmentManager获取fragment
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        //add(R.id.fragment_container, fragment),参数1为fragment视图应该出现在活动的什么位置，用作
        //FragmentManager队列中fragment的唯一标识符
        if (fragment == null) {

            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
