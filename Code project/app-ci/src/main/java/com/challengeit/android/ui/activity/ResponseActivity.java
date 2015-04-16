package com.challengeit.android.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.userLocalEndpoint.model.Response;
import com.challengeit.android.log.LogWrapper;
import com.challengeit.android.persistence.PersistenceSingleton;
import com.challengeit.android.ui.fragment.ResponseFragment;

/*
 * Allow a player to respond to an activity
 */
public class ResponseActivity extends Activity {
    private Response response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_response);
        response = PersistenceSingleton.getInstance().getResponseClicked();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, ResponseFragment.newIntance())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.response, menu);
        if(response.getTitle()==null)
            setTitle("response");
        else
            setTitle(response.getTitle());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.review){
            startReviewActivity();
            LogWrapper.showMessage(this, "Feature not implemented yet");
            //startReviewActivity()
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startReviewActivity() {
        Intent intent = new Intent(this, ReviewResponseActivity.class);
        startActivity(intent);
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_response, container, false);
            return rootView;
        }
    }
}
