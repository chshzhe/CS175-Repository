package com.example.project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);



        final ArrayList<String> titleList = new ArrayList<>();
        titleList.add("Page1");
        titleList.add("Page2");
        titleList.add("Page3");
        ViewPager pager = findViewById(R.id.view_pager);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new PlaceholderFragment();
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position)
            {
                return titleList.get(position);
            }
        });



        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);
        pager.setOffscreenPageLimit(tabLayout.getTabCount());
    }
}
