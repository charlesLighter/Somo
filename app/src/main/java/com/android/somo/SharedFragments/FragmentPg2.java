package com.android.somo.SharedFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.somo.Helpers.LocalStorage;
import com.android.somo.R;
import com.android.somo.databinding.FragmentPg2Binding;


public class FragmentPg2 extends Fragment {

    private FragmentPg2Binding binding;
    private String sessionOverview, studentsEngagement;
    private LocalStorage localStorage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPg2Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        localStorage = new LocalStorage();
        localStorage.initializeSharedPreferences(getContext());

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionOverview = binding.edtSessionOverview.getText().toString();
                studentsEngagement = binding.edtStudentEngagement.getText().toString();
                saveDataToLocal();
            }
        });

        retrieveDataFromLocal();
        return root;
    }

    /*Save data to local storage*/
    private void saveDataToLocal() {
        localStorage.storeData("session_overview", sessionOverview);
        localStorage.storeData("students_engagement", studentsEngagement);
    }

    private void retrieveDataFromLocal(){
       try {
           if (!localStorage.retrieveData("session_overview").isEmpty()){
               binding.edtSessionOverview.setText(localStorage.retrieveData("session_overview"));
           }
           else {
               binding.edtSessionOverview.setText("");
           }


           if (!localStorage.retrieveData("students_engagement").isEmpty()){
               binding.edtStudentEngagement.setText(localStorage.retrieveData("students_engagement"));
           }
           else {
               binding.edtStudentEngagement.setText("");
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}