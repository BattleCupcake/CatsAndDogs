package com.catsanddogs.application;

import com.catsanddogs.fragments.DetailsFragment;
import com.catsanddogs.fragments.Fragments;
import com.catsanddogs.fragments.ListFragment;
import com.catsanddogs.network.ItemData;
import com.catsanddogs.network.Retrofit;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import retrofit2.converter.gson.GsonConverterFactory;

import static com.catsanddogs.fragments.Fragments.CATS;

public class Application extends android.app.Application {

    private static Retrofit retrofit;
    private ListFragment catsFragment;
    private ListFragment dogsFragment;
    private DetailsFragment detailsFragment;
    private Fragments selectedTab = CATS;
    private ConcurrentHashMap<String, List<ItemData>> dataStorage = new ConcurrentHashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://kot3.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Application.retrofit = retrofit.create(Retrofit.class);
    }

    public static Retrofit getApi() {
        return retrofit;
    }

    public ListFragment getCatsFragment() {
        return catsFragment;
    }

    public void setCatsFragment(ListFragment catsFragment) {
        this.catsFragment = catsFragment;
    }

    public ListFragment getDogsFragment() {
        return dogsFragment;
    }

    public void setDogsFragment(ListFragment dogsFragment) {
        this.dogsFragment = dogsFragment;
    }

    public DetailsFragment getDetailsFragment() {
        return detailsFragment;
    }

    public void setDetailsFragment(DetailsFragment detailsFragment) {
        this.detailsFragment = detailsFragment;
    }

    public Fragments getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(Fragments selectedTab) {
        this.selectedTab = selectedTab;
    }

    public void dataStoragePut(String key, List<ItemData> value) {
        dataStorage.put(key, value);
    }

    public List<ItemData> dataStorageGet(String key) {
        return dataStorage.containsKey(key) ? dataStorage.get(key) : null;
    }
}
