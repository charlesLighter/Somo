package com.android.somo.SharedFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.somo.Helpers.LocalStorage;
import com.android.somo.R;
import com.android.somo.databinding.FragmentPg3Binding;
import com.android.somo.databinding.FragmentPg4Binding;


public class FragmentPg4 extends Fragment {
    private FragmentPg4Binding binding;
    private String challengesEncountered, supportOffered;
    private LocalStorage localStorage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPg4Binding.inflate(inflater, container, false);

        localStorage = new LocalStorage();
        localStorage.initializeSharedPreferences(getContext());

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                challengesEncountered = binding.edtChallenges.getText().toString();
                supportOffered = binding.edtSupport.getText().toString();
                saveDataToLocal();
            }
        });
        retrieveDataFromLocal();
        return binding.getRoot();
    }

    /** Save data to local storage **/
    private void saveDataToLocal() {
        localStorage.storeData("challenges", challengesEncountered);
        localStorage.storeData("support", supportOffered);
    }

    //** Retrieve data from local storage **/
    private void retrieveDataFromLocal(){
        try {
            if (!localStorage.retrieveData("challenges").isEmpty()){
                binding.edtChallenges.setText(localStorage.retrieveData("challenges"));
            }
            else {binding.edtChallenges.setHint("");}

            if (!localStorage.retrieveData("support").isEmpty()){
                binding.edtSupport.setText(localStorage.retrieveData("support"));
            }
            else{ binding.edtSupport.setHint("");}
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}