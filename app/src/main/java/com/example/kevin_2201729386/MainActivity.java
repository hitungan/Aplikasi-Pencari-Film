package com.example.kevin_2201729386;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout ;
    ViewPager2 viewPager ;
    adapterviewpager adapterviewpager ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        tabLayout = findViewById(R.id.tabtab);
        viewPager = findViewById(R.id.viewpager);
        adapterviewpager = new adapterviewpager(this);

        viewPager.setAdapter(adapterviewpager);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 1 ){
                    tab.setText("Saved Movie");
                }
                else if(position == 0){
                    tab.setText("Search Movie");
                }
            }
        }).attach();
    }
}
