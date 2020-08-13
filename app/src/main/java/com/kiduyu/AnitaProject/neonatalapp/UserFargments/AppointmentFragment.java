package com.kiduyu.AnitaProject.neonatalapp.UserFargments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.kiduyu.AnitaProject.neonatalapp.R;

public class AppointmentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.appointment_fragment, container, false);




        return layout;
    }
}
