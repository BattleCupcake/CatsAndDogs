package com.catsanddogs.activities;

import android.app.FragmentTransaction;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.catsanddogs.R;
import com.catsanddogs.application.Application;
import com.catsanddogs.fragments.DetailsFragment;
import com.catsanddogs.fragments.Fragments;
import com.catsanddogs.fragments.ListFragment;
import com.catsanddogs.list.OnListItemClick;

import static com.catsanddogs.fragments.Fragments.DETAILS;

public class MainActivity extends AppCompatActivity implements OnListItemClick {

    public static String CATS = "cat";
    public static String DOGS = "dog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        if (((Application)getApplication()).getCatsFragment() == null) {
            Bundle bundleTab1 = new Bundle();
            bundleTab1.putString(ListFragment.CATEGORY_TAG, CATS);
            ListFragment tab1Fragment = new ListFragment();
            tab1Fragment.setArguments(bundleTab1);
            ((Application)getApplication()).setCatsFragment(tab1Fragment);
        }

        if (((Application)getApplication()).getDogsFragment() == null) {
            Bundle bundleTab2 = new Bundle();
            bundleTab2.putString(ListFragment.CATEGORY_TAG, DOGS);
            ListFragment tab2Fragment = new ListFragment();
            tab2Fragment.setArguments(bundleTab2);
            ((Application)getApplication()).setDogsFragment(tab2Fragment);
        }

        TabLayout tlTabs = findViewById(R.id.tabs);
        TabLayout.Tab tab = tlTabs.getTabAt(((Application) getApplication()).getSelectedTab() == Fragments.CATS ? 0 : 1);
        tab.select();

        ((TabLayout)findViewById(R.id.tabs)).addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        onOpenTab(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                    }
                }
        );

        if (((Application)getApplication()).getDetailsFragment() != null) {
            showFragment(DETAILS);
        } else {
            showFragment(((Application) getApplication()).getSelectedTab());
        }
    }

    private void onOpenTab(int position) {
        switch (position) {
            case 0:
                ((Application)getApplication()).setSelectedTab(Fragments.CATS);
                showFragment(Fragments.CATS);
                break;
            case 1:
                ((Application)getApplication()).setSelectedTab(Fragments.DOGS);
                showFragment(Fragments.DOGS);
                break;
            default:
                return;
        }
    }

    private void showFragment(Fragments enumItem) {
        FragmentTransaction ftTransaction = getFragmentManager().beginTransaction();
        switch (enumItem) {
            case CATS:
                ftTransaction.replace(R.id.list_container, ((Application)getApplication()).getCatsFragment());
                showTabs(true);
                break;
            case DOGS:
                ftTransaction.replace(R.id.list_container, ((Application)getApplication()).getDogsFragment());
                showTabs(true);
                break;
            case DETAILS:
                ftTransaction.replace(R.id.list_container, ((Application)getApplication()).getDetailsFragment());
                showTabs(false);
                break;
            default:
                return;
        }
        ftTransaction.commit();
    }

    @Override
    public void onClick(int position, String url, String title) {
        showDetails(position, url, title);
    }

    private void showDetails(int position, String url, String title) {
        Bundle bundleDetails = new Bundle();
        bundleDetails.putInt(DetailsFragment.POSITION_TAG, position);
        bundleDetails.putString(DetailsFragment.URL_TAG, url);
        bundleDetails.putString(DetailsFragment.TITLE_TAG, title);
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundleDetails);
        ((Application)getApplication()).setDetailsFragment(detailsFragment);

        showFragment(DETAILS);
    }

    private void hideDetails() {
        showFragment(((Application) getApplication()).getSelectedTab());
        ((Application)getApplication()).setDetailsFragment(null);
    }

    public void showTabs(boolean show) {
        findViewById(R.id.tabs).setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (((Application)getApplication()).getDetailsFragment() != null) {
            hideDetails();
        } else {
            super.onBackPressed();
        }
    }
}