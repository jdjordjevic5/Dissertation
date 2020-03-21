package com.example.dissertation;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends FragmentPagerAdapter {
    private String nameDirection;
    private String keyDirection;
    private String nameRestaurant;
    private String keyRestaurant;
    private String nameCoffee;
    private String keyCoffee;
    private String nameCultural;
    private String keyCultural;
    private FragmentDirection fragmentDirection;
    private FragmentRestaurant fragmentRestaurant;
    private FragmentCoffee fragmentCoffee;
    private FragmentCultural fragmentCultural;

    PageAdapter(FragmentManager manager) {
        super(manager);
    }

    public void addDirection(Fragment fragment, String key, String name) {
        this.fragmentDirection = (FragmentDirection) fragment;
        this.nameDirection = name;
        this.keyDirection = key;
    }
    public void addRestaurant(Fragment fragment, String key, String name) {
        this.fragmentRestaurant = (FragmentRestaurant) fragment;
        this.nameRestaurant = name;
        this.keyRestaurant = key;
    }
    public void addCoffee(Fragment fragment, String key, String name) {
        this.fragmentCoffee = (FragmentCoffee) fragment;
        this.nameCoffee = name;
        this.keyCoffee = key;
    }
    public void addCultural(Fragment fragment, String key, String name) {
        this.fragmentCultural = (FragmentCultural) fragment;
        this.nameCultural = name;
        this.keyCultural = key;
    }


    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fragmentDirection.newInstance(keyDirection, nameDirection);
            case 1:
                return fragmentRestaurant.newInstance(keyRestaurant, nameRestaurant);
            case 2:
                return fragmentCoffee.newInstance(keyCoffee, nameCoffee);
            case 3:
                return fragmentCultural.newInstance(keyCultural, nameCultural);
            default:
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}