package com.android.somo.SharedFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.somo.Constants.Constants;
import com.android.somo.Helpers.LocalStorage;
import com.android.somo.Models.StemReportsModel;
import com.android.somo.R;
import com.android.somo.SharedActivities.MainActivity;
import com.android.somo.databinding.FragmentSubRptBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;


public class SubRptFragment extends Fragment {

    private FragmentSubRptBinding binding;
    private LocalStorage localStorage;
    private StemReportsModel model;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private String rptId;
    private String fullName;
    private String firstName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSubRptBinding.inflate(inflater, container, false);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        model  = new StemReportsModel();
        localStorage = new LocalStorage();
        localStorage.initializeSharedPreferences(getContext());
        retrieveDataFromLocal();

        //get staff first name to generate report id
        fullName = localStorage.retrieveData("staff_name");
        if (fullName!=null){
            try {
                String[] name = fullName.split(" ");
                if (name.length >= 1){
                  firstName = name[0];
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            Toast.makeText(getContext(), "Failed to get your name", Toast.LENGTH_SHORT).show();
        }

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        return binding.getRoot();
    }


    private void retrieveDataFromLocal() {
        try {
            binding.txtName.setText(localStorage.retrieveData("staff_name"));
            binding.txtRole.setText(localStorage.retrieveData("staff_role"));
            binding.txtSchool.setText(localStorage.retrieveData("school"));
            binding.txtDate.setText(localStorage.retrieveData("session_date"));
            binding.txtTopic.setText(localStorage.retrieveData("topic"));
            binding.txtSessionOverview.setText(localStorage.retrieveData("session_overview"));
            binding.txtStudentEngagement.setText(localStorage.retrieveData("students_engagement"));
            binding.txtSkillsDemonstrated.setText(localStorage.retrieveData("demonstrated_skill"));
            binding.txtProjectCompletion.setText(localStorage.retrieveData("project_progress"));
            binding.txtChallenges.setText(localStorage.retrieveData("challenges"));
            binding.txtSupport.setText(localStorage.retrieveData("support"));
            binding.txtNextSteps.setText(localStorage.retrieveData("next_steps"));
            binding.txtFeedback.setText(localStorage.retrieveData("feedback"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void validateData() {
        if (binding.txtName.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Couldn't find your name", Toast.LENGTH_SHORT).show();
        }
        else if (binding.txtRole.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Specify your role", Toast.LENGTH_SHORT).show();
        }else if (binding.txtSchool.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "School cannot be empty", Toast.LENGTH_SHORT).show();
        }else if (binding.txtDate.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Date cannot be empty", Toast.LENGTH_SHORT).show();
        }else if (binding.txtTopic.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Topic cannot be empty", Toast.LENGTH_SHORT).show();
        }else if (binding.txtSessionOverview.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Session Overview cannot be empty", Toast.LENGTH_SHORT).show();
        }else if (binding.txtStudentEngagement.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Specify level of student engagement", Toast.LENGTH_SHORT).show();
        }else if (binding.txtSkillsDemonstrated.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Specify skills and concepts demonstrated", Toast.LENGTH_SHORT).show();
        }else if (binding.txtProjectCompletion.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Project progress cannot be empty", Toast.LENGTH_SHORT).show();
        }else if (binding.txtChallenges.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Specify challenges encountered", Toast.LENGTH_SHORT).show();
        }else if (binding.txtSupport.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Indicate support provided", Toast.LENGTH_SHORT).show();
        }else if (binding.txtNextSteps.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Next steps cannot be empty", Toast.LENGTH_SHORT).show();
        }else if (binding.txtFeedback.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Feedback cannot be empty", Toast.LENGTH_SHORT).show();
        }else {
            //upload data to cloud firebase
            setData();
        }

    }

    private void setData() {
        rptId = firstName+ generateTimeStamp();
        model.setTimestamp(generateTimeStamp());
        model.setReportId(rptId);
        model.setStaffName(localStorage.retrieveData("staff_name"));
        model.setRole(localStorage.retrieveData("staff_role"));
        model.setSessionDate(localStorage.retrieveData("session_date"));
        model.setSchool(localStorage.retrieveData("school"));
        model.setTopic(localStorage.retrieveData("topic"));
        model.setSessionOverview(localStorage.retrieveData("session_overview"));
        model.setStudentEngagement(localStorage.retrieveData("students_engagement"));
        model.setDemonstratedSkills(localStorage.retrieveData("demonstrated_skill"));
        model.setProjectProgress(localStorage.retrieveData("project_progress"));
        model.setChallengesEncountered(localStorage.retrieveData("challenges"));
        model.setSupportProvided(localStorage.retrieveData("support"));
        model.setNextSteps(localStorage.retrieveData("next_steps"));
        model.setFeedback(localStorage.retrieveData("feedback"));
        uploadDataToCloud();
    }

    private long generateTimeStamp(){
        return new Date().getTime();
    }

    private void uploadDataToCloud() {
       //upload data to staff bucket
        database.getReference()
                .child(Constants.STAFF_REPORTS)
                .child(mAuth.getCurrentUser().getUid())
                .child(rptId)
                .setValue(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        //save data to all reports bucket
                        database.getReference()
                                .child(Constants.SUBMITTED_REPORTS)
                                .child(rptId)
                                .setValue(model)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        clearDataFromLocal();  //delete the saved data
                                        Toast.makeText(getContext(), "Submitted Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getContext(), MainActivity.class));
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /*For deleting saved data from the local storage*/
    private void clearDataFromLocal(){
     localStorage.deleteData("staff_name");
     localStorage.deleteData("staff_role");
     localStorage.deleteData("school");
     localStorage.deleteData("session_date");
     localStorage.deleteData("topic");
     localStorage.deleteData("session_overview");
     localStorage.deleteData("students_engagement");
     localStorage.deleteData("demonstrated_skill");
     localStorage.deleteData("project_progress");
     localStorage.deleteData("challenges");
     localStorage.deleteData("support");
     localStorage.deleteData("next_steps");
     localStorage.deleteData("feedback");
    }

}