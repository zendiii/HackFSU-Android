package com.hackfsu.hackfsu_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FeedFragment extends BaseFragment {

    private OnFragmentInteractionListener mListener;

    // View Items
    Toolbar mToolbar;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    AppBarLayout mAppBar;
    CollapsingToolbarLayout mCollasping;

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    // Required empty public constructor
    public FeedFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feed, container, false);

        mToolbar = (Toolbar) v.findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) v.findViewById(R.id.tabs);
        mViewPager = (ViewPager) v.findViewById(R.id.viewpager);
        mAppBar = (AppBarLayout) v.findViewById(R.id.action_bar);
        mCollasping = (CollapsingToolbarLayout) v.findViewById(R.id.collapsing_toolbar);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Toolbar stuff
        //toolbar.inflateMenu(R.menu.menu_main);
        //setSupportActionBar(mToolbar);
        //mToolbar.setTitle("HackFSU");
        mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        mToolbar.inflateMenu(R.menu.menu_main);
        mListener.registerToolbar(mToolbar);

        // Init toolbar icons
        SharedPreferences sp = getContext().getSharedPreferences(HackFSU.PREFERENCES, Context.MODE_PRIVATE);
        Menu menu = mToolbar.getMenu();
        MenuItem notifs = menu.findItem(R.id.action_notifications);
        notifs.setIcon(sp.getBoolean(HackFSU.NOTIFICATIONS, true) ?
                R.drawable.ic_notifications_24dp : R.drawable.ic_notifications_off_24dp);
        MenuItem countdown = menu.findItem(R.id.action_countdown);
        countdown.setIcon(sp.getBoolean(HackFSU.COUNTDOWN, true) ?
                R.drawable.ic_timer_24dp : R.drawable.ic_timer_off_white_24dp);


        // View Pager setup
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(UpdateFragment.newInstance());
        fragments.add(ScheduleFragment.newInstance());
        PagerAdapter mPagerAdapter = new PagerAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        if(savedInstanceState != null) {
            mViewPager.setCurrentItem(savedInstanceState.getInt("pos"));
        }


        // Custom toolbar font
        Typeface face;
        face = Typeface.createFromAsset(getContext().getAssets(), "unisans.OTF");
        mCollasping.setCollapsedTitleTypeface(face);
        mCollasping.setExpandedTitleTypeface(face);
        mCollasping.setTitle("HackFSU");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pos", mViewPager.getCurrentItem());
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
