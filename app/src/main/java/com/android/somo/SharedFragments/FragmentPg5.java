package com.android.somo.SharedFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.somo.Helpers.LocalStorage;
import com.android.somo.R;
import com.android.somo.databinding.FragmentPg4Binding;
import com.android.somo.databinding.FragmentPg5Binding;

public class FragmentPg5 extends Fragment {

    private FragmentPg5Binding binding;
    private String nextStep, feedback;
    private LocalStorage localStorage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPg5Binding.inflate(inflater, container, false);

        localStorage = new LocalStorage();
        localStorage.initializeSharedPreferences(getContext());

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStep = binding.edtNextSteps.getText().toString();
                feedback = binding.edtFeedback.getText().toString();
                saveDataToLocal();
            }
        });
        retrieveDataFromLocal();
        return binding.getRoot();
    }

    /** Save data to local storage **/
    private void saveDataToLocal() {
        localStorage.storeData("next_steps", nextStep);
        localStorage.storeData("feedback", feedback);
        Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
    }

    //** Retrieve data from local storage **/
    private void retrieveDataFromLocal(){
        try {
            if (!localStorage.retrieveData("next_steps").isEmpty()){
                binding.edtNextSteps.setText(localStorage.retrieveData("next_steps"));
            }
            else {binding.edtNextSteps.setHint("");}

            if (!localStorage.retrieveData("feedback").isEmpty()){
                binding.edtFeedback.setText(localStorage.retrieveData("feedback"));
            }
            else{ binding.edtFeedback.setHint("");}
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}