package com.talenguyen.androidframework.module.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import com.talenguyen.androidframework.utils.ReflectionUtil;

import java.util.List;

/**
 * Created by Giang on 3/6/14.
 * <p/>
 * To use single Activity and multiple Fragment you should use this activity. For the fragment container we will use
 * ViewPager and call setViewPager() in very first place. We recommend onCreate().
 */
public class NavigationFragmentActivity extends FragmentActivity {

    private NavigationViewPager mViewPager;
    private NavigationFragmentAdapter mAdapter;

    /**
     * Get the id of the fragment container. Where every child fragment can attach to.
     *
     * @return The resource id of the Fragment container.
     */
    public int getFragmentContainerId() {
        return 0;
    }

    /**
     * Configure navigation pages.
     *
     * @param viewPager The ViewPager object which will be a FragmentContainer where the Fragment will be placed.
     * @param pageList  The list of pages to be navigate.
     */
    public void configNavigation(NavigationViewPager viewPager, List<Class<? extends Fragment>> pageList) {
        if (viewPager == null) {
            throw new NullPointerException("ViewPager must not be null");
        }
        mAdapter = new NavigationFragmentAdapter(getSupportFragmentManager());
        mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mViewPager.setAdapter(mAdapter);
        if (pageList != null) {
            mAdapter.setClassFragmentList(pageList);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Open a page specific to position.
     *
     * @param position The position of page target.
     */
    public void setCurrentPage(int position) {
        mViewPager.setCurrentItem(position);
    }

    /**
     * Open a page specific to position.
     *
     * @param position     The position of page target.
     * @param smoothScroll enable animation.
     */
    public void setCurrentPage(int position, boolean smoothScroll) {
        mViewPager.setCurrentItem(position, smoothScroll);
    }

    @Override
    public void onBackPressed() {
        final int currentPosition = mViewPager.getCurrentItem();
        if (currentPosition == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mViewPager.setCurrentItem(currentPosition - 1);
        }
    }

    public void nextPage() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    private static class NavigationFragmentAdapter extends FragmentStatePagerAdapter {

        private static final String TAG = NavigationFragmentAdapter.class.getSimpleName();

        private List<Class<? extends Fragment>> classFragmentList;

        public NavigationFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setClassFragmentList(List<Class<? extends Fragment>> classFragmentList) {
            if (this.classFragmentList == classFragmentList) {
                return;
            }
            this.classFragmentList = classFragmentList;
        }

        @Override
        public Fragment getItem(int i) {
            final Class<? extends Fragment> classFragment = classFragmentList.get(i);
            return ReflectionUtil.newInstance(classFragment);
        }

        @Override
        public int getCount() {
            if (classFragmentList != null) {
                return classFragmentList.size();
            }
            return 0;
        }
    }

}
