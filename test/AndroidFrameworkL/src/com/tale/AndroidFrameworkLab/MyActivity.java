package com.tale.AndroidFrameworkLab;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.talenguyen.androidframework.module.fragment.NavigationFragmentActivity;
import com.talenguyen.androidframework.module.fragment.NavigationViewPager;

public class MyActivity extends NavigationFragmentActivity {

    private static final List<Class<? extends Fragment>> pages;
    static {
        pages = new ArrayList<Class<? extends Fragment>>();
        pages.add(PageFragment.class);
        pages.add(Page1Fragment.class);
        pages.add(Page2Fragment.class);
        pages.add(Page3Fragment.class);
        pages.add(Page4Fragment.class);
    }
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        NavigationViewPager viewPager = (NavigationViewPager) findViewById(R.id.viewpager);
        viewPager.setSwipingEnabled(true);
        configNavigation(viewPager, pages);
    }


}
