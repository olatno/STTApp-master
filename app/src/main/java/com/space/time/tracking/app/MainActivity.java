package com.space.time.tracking.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getFragmentManager();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MainActivityFragment fragment = new MainActivityFragment();
            fragmentTransaction.add(R.id.fragmentParentViewGroup, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
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
        if (id == R.id.action_reservations) {
            this.setTitle(R.string.action_reservations);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fragment = new ReservationFragment();
            fragmentTransaction.replace(R.id.fragmentParentViewGroup, fragment);
            fragmentTransaction.commit();
            return true;
        }

        else if (id == R.id.action_arrivals) {
            this.setTitle(R.string.action_arrivals);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fragment = new ReservationDetailsFragment();
            fragmentTransaction.replace(R.id.fragmentParentViewGroup, fragment);
            fragmentTransaction.commit();
            return true;
        }

        else if (id == R.id.action_departures) {
            this.setTitle(R.string.action_departures);
            return true;
        }

        else if (id == R.id.action_tracker) {
            this.setTitle(R.string.action_tracker);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
