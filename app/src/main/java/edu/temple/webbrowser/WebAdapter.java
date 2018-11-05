package edu.temple.webbrowser;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;

public class WebAdapter extends FragmentStatePagerAdapter {

    ArrayList<WebFragment> fragments;

    /*links adapter with a FragmentManager;
    **initializes the ArrayList of fragments*/
    public WebAdapter(FragmentManager manager) {
        super(manager);
        fragments = new ArrayList<>();
    }

    /*returns number of WebFragments stored in fragments*/
    @Override
    public int getCount() {
        return fragments.size();
    }

    /*returns the WebFragment stored in fragments located at position*/
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    /*adds a WebFragment to fragments*/
    public void addWebFragment() {
        WebFragment wf = new WebFragment();
        fragments.add(wf);
        notifyDataSetChanged();
    }
}
