package com.github.batulovandrey.notesrealm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.github.batulovandrey.notesrealm.BasicFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrey Batulov on 15/07/2017
 */

public class CustomPagerAdapter extends FragmentStatePagerAdapter {

    private List<BasicFragment> mFragmentList;

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.isEmpty() ? null : mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentList.get(position).getTitle();
    }

    public void addFragments(List<BasicFragment> fragments) {
        mFragmentList.addAll(fragments);
        notifyDataSetChanged();
    }

    public void clear() {
        mFragmentList = null;
        mFragmentList = new ArrayList<>();
    }
}