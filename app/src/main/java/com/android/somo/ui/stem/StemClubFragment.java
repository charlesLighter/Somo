package com.android.somo.ui.stem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.somo.AdminActivities.AddNewStaffActivity;
import com.android.somo.AdminActivities.SubmittedStemRptsActivity;
import com.android.somo.Constants.Constants;
import com.android.somo.Models.NotificationModel;
import com.android.somo.R;
import com.android.somo.SharedActivities.StemClubReportingActivity;
import com.android.somo.StaffActivities.StemReportsViewActivity;
import com.android.somo.databinding.FragmentStemClubBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;

public class StemClubFragment extends Fragment {

    private FragmentStemClubBinding binding;


    private FirebaseAuth mAuth;
    private boolean isAdmin = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentStemClubBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        //validate admin
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser().getUid().equals(Constants.ADMIN_UID)){
            isAdmin = true;
        }

        //give admin privileges
        if (isAdmin){
            binding.btnAddNewStaff.setVisibility(View.VISIBLE);
            binding.btnViewSubmittedReports.setVisibility(View.VISIBLE);
            obtainFCMToken();
        }



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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}