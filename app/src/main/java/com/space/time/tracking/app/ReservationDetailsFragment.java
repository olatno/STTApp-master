package com.space.time.tracking.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by olatu on 28/01/2016.
 */
public class ReservationDetailsFragment extends Fragment {

    TextView nameView;
    TextView arrivalView;
    TextView departureView;
    TextView contactView;
    TextView emailView;
    TextView packageView;
    TextView numberView;
    TextView channelView;
    final String DETAILS = "Details";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.reservation_details, parentViewGroup, false);
        savedInstanceState = getArguments();
        ArrayList<String> arrayString = savedInstanceState.getStringArrayList("reserDetails");
        getActivity().setTitle(arrayString.get(0).split(" ")[0]+" "+ DETAILS);
        nameView = (TextView)rootView.findViewById(R.id.cust_name);
        nameView.setText(arrayString.get(0));

        arrivalView = (TextView)rootView.findViewById(R.id.arr_date);
        arrivalView.setText(arrayString.get(1));

        departureView = (TextView)rootView.findViewById(R.id.dep_date);
        departureView.setText(arrayString.get(2));

        contactView = (TextView)rootView.findViewById(R.id.telephone);
        contactView.setText(arrayString.get(3));

        emailView = (TextView)rootView.findViewById(R.id.email);
        emailView.setText(arrayString.get(4));

        packageView = (TextView)rootView.findViewById(R.id.package_booked);
        packageView.setText(arrayString.get(5));

        numberView = (TextView)rootView.findViewById(R.id.number_booked);
        numberView.setText(arrayString.get(6));

        channelView = (TextView)rootView.findViewById(R.id.channel);
        channelView.setText(arrayString.get(7));
        return rootView;
    }

}
