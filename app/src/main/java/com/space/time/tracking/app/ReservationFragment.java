package com.space.time.tracking.app;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.space.time.android.tracker.bean.ReservationArrival;
import com.space.time.tracking.util.JsonStringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * Created by olatu on 09/01/2016.
 */
public class ReservationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        startNewAsyncTask();

        View rootView = inflater.inflate(R.layout.reservation_container, parentViewGroup, false);
        return rootView;
    }

    private void startNewAsyncTask() {
        ReservationAsyncTask asyncTask = new ReservationAsyncTask(this);
        WeakReference<ReservationAsyncTask> asyncTaskWeakRef = new WeakReference<>(asyncTask );
        asyncTask.execute();
    }

    private class ReservationAsyncTask extends AsyncTask<String, Void, String> {

        private WeakReference<ReservationFragment> fragmentWeakRef;
        ProgressDialog pd;
        final String url = "http://192.168.1.3:8080/tracker/json/reservation/all";
        //final String url = "http://192.168.177.122:8080/tracker/json/reservation/all";

        private ReservationAsyncTask(ReservationFragment reservationFragment) {

            this.fragmentWeakRef = new WeakReference<>(reservationFragment);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getContext());
            pd.setMessage("loading...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {

            return JsonStringUtil.requestContent(url);
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            pd.dismiss();
            if (this.fragmentWeakRef.get() != null) {
                try {
                    JSONArray items = new JSONArray(response);
                    ArrayList<ReservationArrival> reservation = new ArrayList<>();
                    ReservationArrival reservationArrival;
                    for(int i = 0; i < items.length(); i++){
                        JSONObject reserObject = items.getJSONObject(i);
                        reservationArrival = new ReservationArrival();

                        reservationArrival.setName(reserObject.getJSONObject("customer").getString("firstName")
                                +" "+ reserObject.getJSONObject("customer").getString("lastName"));
                        reservationArrival.setArriDate(reserObject.getJSONObject("arrivalDate").getString("dayOfMonth")
                                +"-"+reserObject.getJSONObject("arrivalDate").getString("monthValue")
                                +"-"+reserObject.getJSONObject("arrivalDate").getString("year"));
                        reservationArrival.setPackageBooked(reserObject.getJSONObject("rateCode")
                                .getString("rateName"));
                        reservationArrival.setNumberReversed(reserObject.getString("numberReserved"));
                        reservation.add(reservationArrival);
                    }

                    // Construct the data source

                    // Create the adapter to convert the array to views
                    ReservationAdapter adapter = new ReservationAdapter(getContext(), reservation);

                    // Attach the adapter to a ListView
                    ListView listView = (ListView) getView().findViewById(R.id.listView_reservation);
                    listView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        public class ReservationAdapter extends ArrayAdapter<ReservationArrival> {
            // View lookup cache
            private class ViewHolder {
                TextView name;
                TextView date;
                TextView package_name;
                TextView numbers;
            }

            public ReservationAdapter(Context context, ArrayList<ReservationArrival> reservationArrivals) {
                super(context, R.layout.reservation_textview, reservationArrivals);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the data item for this position
                ReservationArrival reservationArrival = getItem(position);
                // Check if an existing view is being reused, otherwise inflate the view
                ViewHolder viewHolder; // view lookup cache stored in tag
                if (convertView == null) {
                    viewHolder = new ViewHolder();
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.reservation_textview, parent, false);
                    viewHolder.name = (TextView) convertView.findViewById(R.id.textView_name);
                    viewHolder.date = (TextView) convertView.findViewById(R.id.textView_arrival);
                    viewHolder.package_name = (TextView) convertView.findViewById(R.id.textView_package);
                    viewHolder.numbers = (TextView) convertView.findViewById(R.id.textView_number);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                // Populate the data into the template view using the data object
                viewHolder.name.setText(reservationArrival.getName());
                viewHolder.date.setText(reservationArrival.getArriDate());
                viewHolder.package_name.setText(reservationArrival.getPackageBooked());
                viewHolder.numbers.setText(reservationArrival.getNumberReversed());
                // Return the completed view to render on screen
                return convertView;
            }
        }
    }
}
