package com.android.somo.StaffActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.somo.R;
import com.android.somo.databinding.ActivityStemRptDetailsBinding;

public class StemRptDetailsActivity extends AppCompatActivity {

    private ActivityStemRptDetailsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStemRptDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.txtName.setText(getIntent().getStringExtra("staff_name"));
        binding.txtRole.setText(getIntent().getStringExtra("staff_role"));
        binding.txtDate.setText(getIntent().getStringExtra("session_date"));
        binding.txtSchool.setText(getIntent().getStringExtra("school"));
        binding.txtTopic.setText(getIntent().getStringExtra("topic"));
        binding.txtSessionOverview.setText(getIntent().getStringExtra("session_overview"));
        binding.txtStudentEngagement.setText(getIntent().getStringExtra("student_engagement"));
        binding.txtSkillsDemonstrated.setText(getIntent().getStringExtra("skill_demonstrated"));
        binding.txtProjectCompletion.setText(getIntent().getStringExtra("project_progress"));
        binding.txtChallenges.setText(getIntent().getStringExtra("challenges"));
        binding.txtSupport.setText(getIntent().getStringExtra("support_provided"));
        binding.txtNextSteps.setText(getIntent().getStringExtra("next_steps"));
        binding.txtFeedback.setText(getIntent().getStringExtra("feedback"));
    }
}