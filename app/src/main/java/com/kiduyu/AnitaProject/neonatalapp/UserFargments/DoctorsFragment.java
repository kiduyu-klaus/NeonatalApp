package com.kiduyu.AnitaProject.neonatalapp.UserFargments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import androidx.fragment.app.Fragment;

import com.kiduyu.AnitaProject.neonatalapp.Adapters.DoctorsAdapter;
import com.kiduyu.AnitaProject.neonatalapp.Constants.Constants;
import com.kiduyu.AnitaProject.neonatalapp.Models.Doctor;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import com.kiduyu.AnitaProject.neonatalapp.R;

public class DoctorsFragment extends Fragment {
    private DoctorsAdapter doctorsAdapter;
    public static int confirmation = 0;
    public static boolean isRefreshed;
    private RequestQueue mRequestQueue;
    RecyclerView recycler;
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Doctor> consultantArrayList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.doctor_fragment, container, false);

        mRequestQueue = Volley.newRequestQueue(getActivity());
        recycler = layout.findViewById(R.id.recyclerview_doctor);

        swipeRefreshLayout = layout.findViewById(R.id.doctor_refresh);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        recycler.setLayoutManager(layoutManager);
        recycler.setFocusable(false);

        fetchData();

        //Loading.showProgressDialog(getActivity(),true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshed = true;
                fetchData();
                swipeRefreshLayout.setRefreshing(false);
                FancyToast.makeText(getActivity(), "Data refreshed!", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
            }
        });


        return layout;
    }

    private void fetchData() {
        String urlForJsonObject = Constants.Baseurl +Constants.DOCTORS_API;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                urlForJsonObject,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray jsonArray = jsonObject.getJSONArray("doctors");

                            if (isRefreshed) {
                                consultantArrayList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject consultant = jsonArray.getJSONObject(i);
                                    String title = consultant.getString("name");
                                    String idnumber = consultant.getString("idnumber");
                                    String phone = consultant.getString("phone");
                                    String location = consultant.getString("location");
                                    String image = consultant.getString("image");
                                    String date = consultant.getString("date");
                                    //Loading.showProgressDialog(getActivity(),false);
                                    Log.d("TAG", "onResponse: json "+title+idnumber+phone+location+image);
                                    consultantArrayList.add(new Doctor(title, phone, idnumber, location, image, date));

                                }

                            } else {
                                consultantArrayList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject consultant = jsonArray.getJSONObject(i);
                                    String title = consultant.getString("name");
                                    String idnumber = consultant.getString("idnumber");
                                    String phone = consultant.getString("phone");
                                    String location = consultant.getString("location");
                                    String image = consultant.getString("image");
                                    String date = consultant.getString("date");
                                    String jcover=Constants.Baseimageurl+image;
                                    consultantArrayList.add(new Doctor(title, phone, idnumber, location,jcover , date));
                                    Log.d("TAG", "onResponse: json "+jcover);

                                }
                            }

                            doctorsAdapter = new DoctorsAdapter(getActivity(), consultantArrayList);
                            recycler.setAdapter(doctorsAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }
}
