/*
 * Copyright (c) 2015 by Epishie.
 */

package com.epishie.ripley.framework.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.epishie.ripley.R;
import com.epishie.ripley.di.HasComponent;
import com.epishie.ripley.di.component.AppComponent;
import com.epishie.ripley.di.component.DaggerFeedComponent;
import com.epishie.ripley.di.component.FeedComponent;
import com.epishie.ripley.framework.view.fragment.MainFragment;

public class MainActivity extends AppCompatActivity implements HasComponent<FeedComponent> {

    private static final String TAG_CONTENT = "content";
    private static final String TAG_RETAINED = "retained";
    private FeedComponent mComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup DI component
        @SuppressWarnings("unchecked")
        HasComponent<AppComponent> app = (HasComponent<AppComponent>)getApplication();
        mComponent = DaggerFeedComponent.builder()
                .appComponent(app.getComponent())
                .build();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(TAG_CONTENT);
        if (fragment == null) {
            fragment = new MainFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, fragment, TAG_CONTENT);
            ft.commit();
        }

        RetainedFragment retainedFragment = (RetainedFragment)fm.findFragmentByTag(TAG_RETAINED);
        if (retainedFragment == null) {
            retainedFragment = new RetainedFragment();
            retainedFragment.setComponent( mComponent);
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(retainedFragment, TAG_RETAINED);
            ft.commit();
        } else {
            mComponent = retainedFragment.getComponent();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public FeedComponent getComponent() {
        return mComponent;
    }

    public static class RetainedFragment extends Fragment {

        private FeedComponent mComponent;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setRetainInstance(true);
        }

        public FeedComponent getComponent() {
            return mComponent;
        }

        public void setComponent(FeedComponent component) {
            mComponent = component;
        }
    }
}
