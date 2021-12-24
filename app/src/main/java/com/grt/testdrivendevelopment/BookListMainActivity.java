package com.grt.testdrivendevelopment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class BookListMainActivity extends AppCompatActivity {


    private String[] tableTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);
        tableTitle=new String[]{"图书","新闻","游戏","卖家"};

        ViewPager2 viewPagerFragment=findViewById(R.id.viewpager2_content);
        viewPagerFragment.setAdapter(new MyFragmentAdapter(this));

        TabLayout tableLayoutHeader=findViewById(R.id.tablayout_header);
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tableLayoutHeader, viewPagerFragment, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tableTitle[position]);
            }
        });
        tabLayoutMediator.attach();
    }

    private class MyFragmentAdapter extends FragmentStateAdapter {


        public MyFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position)
            {
                case 0:
                    return BookListFragment.newInstance();
                case 1:
                    return BrowserFragment.newInstance();
                case 2:
                    return GameFragment.newInstance();
                default:
                    return MapViewFragment.newInstance();
            }
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}