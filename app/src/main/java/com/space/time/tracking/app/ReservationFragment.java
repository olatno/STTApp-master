package com.space.time.tracking.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //  startNewAsyncTask();
    }

    private void startNewAsyncTask() {
        ReservationAsyncTask asyncTask = new ReservationAsyncTask(this);
        WeakReference<ReservationAsyncTask> asyncTaskWeakRef = new WeakReference<>(asyncTask );
        asyncTask.execute();
    }

    private class ReservationAsyncTask extends AsyncTask<String, Void, String> {

        private WeakReference<ReservationFragment> fragmentWeakRef;
        Activity activity;
        ProgressDialog pd;
        //final String url = "http://192.168.1.2:8080/tracker/json/reservation/all";
        final String url = "http://192.168.177.206:8080/tracker/json/reservation/all";

        private ReservationAsyncTask(ReservationFragment reservationFragment) {

            this.fragmentWeakRef = new WeakReference<>(reservationFragment);
            this.activity = activity;
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
                    final ReservationAdapter adapter = new ReservationAdapter(getContext(),R.layout.reservation_textview, reservation);

                    // Attach the adapter to a ListView
                    ListView listView = (ListView)getView().findViewById(R.id.listView_reservation);
                    listView.setAdapter(adapter);

                    //enable filtering for the content of given ListView
                    listView.setTextFilterEnabled(true);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //when clicked, show toast with TextView text
                            ReservationArrival reservationArrival1 = (ReservationArrival)parent.getItemAtPosition(position);
                            Toast.makeText(getContext(), reservationArrival1.getName(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    EditText myFilter = (EditText)getActivity().findViewById(R.id.myFilter);
                    myFilter.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            adapter.getFilter().filter(s.toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        public class ReservationAdapter extends ArrayAdapter<ReservationArrival> implements Filterable{

            private ArrayList<ReservationArrival> originalList;
            private ArrayList<ReservationArrival> reservationArrivals;
            private ReservationFilter filter;

            // View lookup cache
            private class ViewHolder {
                TextView name;
                TextView date;
                TextView package_name;
                TextView numbers;
            }

            public ReservationAdapter(Context context, int textViewResourceId, ArrayList<ReservationArrival> reservationArrivals) {
                super(context, R.layout.reservation_textview, reservationArrivals);
                this.reservationArrivals = new ArrayList<>();
                this.reservationArrivals.addAll(reservationArrivals);
                Log.d("reserArrivals size", String.valueOf(reservationArrivals.size()));
                this.originalList = new ArrayList<>();
                this.originalList.addAll(reservationArrivals);
                Log.d("originalList size", String.valueOf(originalList.size()));
            }

            @Override
            public Filter getFilter(){
                if(filter == null)
                    filter = new ReservationFilter();
                return filter;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the data item for this position
                ReservationArrival reservationArrival = reservationArrivals.get(position);
                // Log.d("reservationName", reservationArrival.getName());
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

            private class ReservationFilter extends Filter{

                @Override
                protected FilterResults performFiltering( CharSequence constraint){

                    FilterResults results = new FilterResults();
                    if( constraint != null && constraint.toString().length() > 0 ){
                        ArrayList<ReservationArrival> reservationItems = new ArrayList<>();
                        for(int i = 0, l = originalList.size();  i < l; i++){
                            ReservationArrival reservationArrival = originalList.get(i);
                            if(reservationArrival.toString().contains(constraint)){
                                reservationItems.add(reservationArrival);
                            }

                            results.count = reservationItems.size();
                            results.values = reservationItems;
                        }
                    }
                    else synchronized (this){
                        results.values = originalList;
                        results.count = originalList.size();
                    }

                    return results;
                }

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,FilterResults results ){

                    reservationArrivals = (ArrayList<ReservationArrival>) results.values;
                    notifyDataSetChanged();
                    clear();
                    for(int i = 0, l = reservationArrivals.size(); i < l; i++){
                        add(reservationArrivals.get(i));
                        notifyDataSetChanged();
                    }

                }

            }
        }
    }
}