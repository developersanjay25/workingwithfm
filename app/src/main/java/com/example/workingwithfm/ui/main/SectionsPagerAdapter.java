package com.example.workingwithfm.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.workingwithfm.R;
import com.example.workingwithfm.homefragment;
import com.example.workingwithfm.infouploader;
import com.example.workingwithfm.musicfragment;
import com.example.workingwithfm.playlist;
import com.example.workingwithfm.songs;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1,R.string.tab_text_2,R.string.tab_text_3,R.string.tab_text_4,R.string.tab_text_5};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
       Fragment fragment = null;
       switch (position)
     {
         case 0:
             fragment = new homefragment();
             break;
         case 1:
             fragment = new infouploader();
             break;
         case 2:
             fragment = new musicfragment();
             break;
         case  3:
             fragment = new songs();
             break;
         case 4:
             fragment = new playlist();
             break;

     }
     return fragment;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 5;
    }
}