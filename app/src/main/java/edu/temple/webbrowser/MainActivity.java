package edu.temple.webbrowser;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements WebFragment.WebFragmentInterface {
    EditText urlInput;
    Button goButton;

    WebAdapter webAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        /*initialize the UI elements, adapter, and pager*/
        urlInput = findViewById(R.id.url_edit_text);
        goButton = findViewById(R.id.go_button);
        FragmentManager fm = getSupportFragmentManager();
        webAdapter = new WebAdapter(fm);
        viewPager = findViewById(R.id.view_pager);
        /*link the ViewPager with an adapter*/
        viewPager.setAdapter(webAdapter);
        viewPager.setOffscreenPageLimit(20);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                String test = webAdapter.fragments.get(i).newURL;
                urlInput.setText(test);
            }

            @Override
            public void onPageSelected(int i) {
                String test = webAdapter.fragments.get(i).newURL;
                urlInput.setText(test);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webAdapter.getCount() > 0) {
                    WebFragment wf = (WebFragment) webAdapter.getItem(viewPager.getCurrentItem());
                    wf.loadSite(urlInput.getText().toString());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            webAdapter.addWebFragment();
            if (webAdapter.getCount() > 0) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                viewPager.setCurrentItem(viewPager.getCurrentItem());
            }
        } else if (id == R.id.action_forward) {
            if (webAdapter.getCount() > 0) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        } else if (id == R.id.action_backward) {
            if (webAdapter.getCount() > 0) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        }
        webAdapter.getItem(viewPager.getCurrentItem());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateURL(String url) {
        urlInput.setText(url, TextView.BufferType.EDITABLE);
    }
}