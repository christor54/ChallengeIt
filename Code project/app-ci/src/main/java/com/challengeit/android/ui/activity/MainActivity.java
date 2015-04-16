package com.challengeit.android.ui.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.userLocalEndpoint.model.UserLocal;
import com.challengeit.android.log.LogWrapper;
import com.challengeit.android.ui.adapter.MainFragmentPagerAdapter;
import com.challengeit.android.ui.fragment.CreateMessageFragment;

import static com.challengeit.android.util.DomainModelUtils.getUserLocal;


/*
* This class contains 5 fragments. Look at the Navigation Among Views Diagram for more details.
* */
public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
    public static final Class<?> thisClass = MainActivity.class;
    public static final int NUM_ITEMS = 4;

    private UserLocal userLocal;

    protected MainFragmentPagerAdapter mAdapter;

    private ViewPager mViewPager;

    public Fragment cameraFragment;


    private String message;

    /* 0 - Camera full screen
       1 - Camera half screen
       2 - Camera invisible
     */
    public int Status;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        userLocal = getUserLocal(getIntent());
        cameraFragment= new CreateMessageFragment();
        Status = 0;

        // Create the adapter that will return a fragment for each of the four
        // primary sections of the activity.
        mAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), cameraFragment);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.view_pager_main);
        mViewPager.setAdapter(mAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAdapter.getPageTitle(i))
                            .setTabListener(this));
        }


        mViewPager.setCurrentItem(1);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    @Override
    public void onBackPressed() {
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_create_game:
                LogWrapper.info(this.getClass(),"new game_player");
                Intent intent = new Intent(this, CreateGameActivity.class);
                startActivity(intent);
                return true;

            case R.id.refresh_game:
                LogWrapper.showMessage(this,"Feature not implemented yet");
                return true;

            case R.id.logout:
                LogWrapper.showMessage(this,"Feature not implemented yet");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Methods for MessageFragment*/
    public void OnClick(View v) {
        Log.i("camera", "photo");
        ((CreateMessageFragment) cameraFragment).takePicture();
        Log.i("main", "peut reprendre");
    }

    public void OnClickVideo(View v){
        ((CreateMessageFragment) cameraFragment).takeVideo();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.i("MainActivity","Passe par là");
//    }


    public void DragCamera(View v){
        int height= ((RelativeLayout) findViewById(R.id.fragment_layout)).getHeight();

        FrameLayout text_layout;
        text_layout = (FrameLayout) findViewById(R.id.text_layout);
        RelativeLayout.LayoutParams text_layout_params = new RelativeLayout.LayoutParams(text_layout.getWidth(), text_layout.getHeight());

        if(Status==0) {
            text_layout_params.height = (int) (height *0.3);
            Status++;
        }
        else if (Status==1){
            text_layout_params.height = ((RelativeLayout) findViewById(R.id.fragment_layout)).getHeight()-150;
            ((FrameLayout) findViewById(R.id.camera_preview)).setVisibility(View.GONE);
            ((ImageButton) findViewById(R.id.button_to_swipe)).setImageResource(R.drawable.expand_icon);
            Status++;
        }
        else {
            text_layout_params.height = 0;//((RelativeLayout) findViewById(R.id.fragment_layout)).getHeight();
            ((FrameLayout) findViewById(R.id.camera_preview)).setVisibility(View.VISIBLE);
            ((ImageButton) findViewById(R.id.button_to_swipe)).setImageResource(R.drawable.reduce_button);
            Status = 0;
        }


        ( this.findViewById(R.id.text_layout)).setLayoutParams(text_layout_params);
        Log.i("Screen size","Width: "+text_layout_params.width+" Height: "+text_layout_params.height);

    }


    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    private void wrapError(Exception e){
        LogWrapper.error(thisClass,e.getMessage());
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}

