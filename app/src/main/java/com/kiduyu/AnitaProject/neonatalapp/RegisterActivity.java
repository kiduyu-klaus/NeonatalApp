package com.kiduyu.AnitaProject.neonatalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kiduyu.AnitaProject.neonatalapp.StatusBar.StatusBar;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBar.changeStatusBarColor(this);
        setContentView(R.layout.activity_register);
    }
}