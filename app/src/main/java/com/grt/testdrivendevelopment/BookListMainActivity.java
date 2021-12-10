package com.grt.testdrivendevelopment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class BookListMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_main);




        ArrayList<Fragment> datas = new ArrayList<Fragment>();
        datas.add(new BookListFragment());
        datas.add(new BrowserFragment());
        datas.add(new MapViewFragment());

        ArrayList<String> titles = new ArrayList<String>();
        titles.add("图书");
        titles.add("新闻");
        titles.add("卖家");

        MyPageAdapter myPageAdapter = new MyPageAdapter(this.getSupportFragmentManager());
        myPageAdapter.setData(datas);
        myPageAdapter.setTitles(titles);

        TabLayout tabLayout = (TabLayout) this.findViewById(R.id.tablayout_header) ;
        ViewPager viewPager = (ViewPager)this.findViewById(R.id.viewpager_content);

        // 将适配器设置进ViewPager
        viewPager.setAdapter(myPageAdapter);
        // 将ViewPager与TabLayout相关联
        tabLayout.setupWithViewPager(viewPager);



    }

    public class MyPageAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> datas;
        ArrayList<String> titles;

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setData(ArrayList<Fragment> datas) {
            this.datas = datas;
        }

        public void setTitles(ArrayList<String> titles) {
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return datas == null ? null : datas.get(position);
        }

        @Override
        public int getCount() {
            return datas == null ? 0 : datas.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles == null ? null : titles.get(position);
        }
    }



}

