package com.android.somo.SharedFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.somo.Helpers.LocalStorage;
import com.android.somo.R;
import com.android.somo.databinding.FragmentPg3Binding;


public class FragmentPg3 extends Fragment {

    private FragmentPg3Binding binding;
    private String demonstratedSkill, projectCompletion;
    private LocalStorage localStorage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPg3Binding.inflate(inflater, container, false);

        localStorage = new LocalStorage();
        localStorage.initializeSharedPreferences(getContext());

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demonstratedSkill = binding.edtSkills.getText().toString();
                projectCompletion = binding.edtProjectProgress.getText().toString();
                saveDataToLocal();
            }
        });
        retrieveDataFromLocal();
        return binding.getRoot();
    }

    /** Save data to local storage **/
    private void saveDataToLocal() {
        localStorage.storeData("demonstrated_skill", demonstratedSkill);
        localStorage.storeData("project_progress", projectCompletion);
    }

    //** Retrieve data from local storage **/
    private void retrieveDataFromLocal(){
       try {
           if (!localStorage.retrieveData("demonstrated_skill").isEmpty()){
               binding.edtSkills.setText(localStorage.retrieveData("demonstrated_skill"));
           }
           else {binding.edtSkills.setHint("");}

           if (!localStorage.retrieveData("project_progress").isEmpty()){
               binding.edtProjectProgress.setText(localStorage.retrieveData("project_progress"));
           }
           else{ binding.edtSkills.setHint("");}
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}