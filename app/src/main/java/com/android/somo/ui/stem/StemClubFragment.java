package com.android.somo.ui.stem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.somo.AdminActivities.AddNewStaffActivity;
import com.android.somo.AdminActivities.SubmittedStemRptsActivity;
import com.android.somo.Constants.Constants;
import com.android.somo.Models.NotificationModel;
import com.android.somo.SharedActivities.StemClubReportingActivity;
import com.android.somo.StaffActivities.StemReportsViewActivity;
import com.android.somo.databinding.FragmentStemClubBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class StemClubFragment extends Fragment {

    private FragmentStemClubBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private boolean isAdmin = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentStemClubBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        database = FirebaseDatabase.getInstance();

        //validate admin
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser().getUid().equals(Constants.ADMIN_1_UID)){
            isAdmin = true;
        }
        if(mAuth.getCurrentUser().getUid().equals(Constants.ADMIN_2_UID)){
            isAdmin = true;
        }

        //give admin privileges
        if (isAdmin){
            binding.btnAddNewStaff.setVisibility(View.VISIBLE);
            binding.btnViewSubmittedReports.setVisibility(View.VISIBLE);
            obtainFCMToken();
        }

        //show number of reports
        getAllRptCount();
        getIndividualRptCount();



        //add new staff
        binding.btnAddNewStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddNewStaffActivity.class));
            }
        });

        //report session
        binding.btnRptNewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), StemClubReportingActivity.class));
            }
        });

        //view reports
        binding.btnViewReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), StemReportsViewActivity.class));
            }
        });

        //view all submitted reports
        binding.btnViewSubmittedReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SubmittedStemRptsActivity.class));
            }
        });

        return rootView;
    }

    /*For obtaining Firebase Cloud Messaging Token from the admins device*/
    private void obtainFCMToken() {
        FirebaseInstanceId.getInstance()
                .getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                      if (task.isSuccessful() && task.getResult() != null){
                          String token = task.getResult().getToken();
                          Log.d("FCM Token", token);
                          new NotificationModel().setFcmToken(token);
                      }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       Log.w("FCM TOKEN", "Failed to generate FCM token");
                    }
                });


    }

    private void getIndividualRptCount(){
        database.getReference()
                .child(Constants.STAFF_REPORTS)
                .child(mAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {

                            if (!snapshot.exists()){
                                binding.txtRptCount.setText("0");
                            }else{
                                binding.txtRptCount.setText(String.valueOf(snapshot.getChildrenCount()));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("Firebase Database", "Failed to retrieve staff reports"+ error);
                    }
                });
    }

    private void getAllRptCount(){
        database.getReference()
                .child(Constants.SUBMITTED_REPORTS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            if (!snapshot.exists()){
                                binding.txtAllRptCount.setText("0");
                            }else {
                                binding.txtAllRptCount.setText(String.valueOf(snapshot.getChildrenCount()));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("Firebase Database", "Failed to retrieve submitted reports"+ error);
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}