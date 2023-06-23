package com.android.somo.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.android.somo.AdminFragments.AdminLoginFragment;
import com.android.somo.StaffFragments.StaffLoginFragment;

public class LoginPagerAdapter extends FragmentStateAdapter {

    //array for tab tittles
    private String[] tabTitles = new String[]{"Staff", "Admin"};

    public LoginPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new StaffLoginFragment();
            case 1:
                return new AdminLoginFragment();
        }
        return new StaffLoginFragment(); //default fragment in login screen
    }

    @Override
    public int getItemCount() {
        return tabTitles.length;
    }
}
