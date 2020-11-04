package com.example.kevin_2201729386;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class adapterviewpager extends FragmentStateAdapter {
    public adapterviewpager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 1){
            return new SaveFrag();
        }
        else if(position == 0){
            return new SearchFrag();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
