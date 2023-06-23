package com.android.somo.SharedActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.somo.R;
import com.android.somo.SharedFragments.FragmentPg1;
import com.android.somo.SharedFragments.FragmentPg2;
import com.android.somo.SharedFragments.FragmentPg3;
import com.android.somo.SharedFragments.FragmentPg4;
import com.android.somo.SharedFragments.FragmentPg5;
import com.android.somo.SharedFragments.SubRptFragment;
import com.android.somo.databinding.ActivityStemClubReportingBinding;

public class StemClubReportingActivity extends AppCompatActivity {

    private ActivityStemClubReportingBinding binding;
    private Button btnNext, btnPrevious;
    private FragmentManager fragmentManager;
    private Fragment[] fragments = {
            new FragmentPg1(),
            new FragmentPg2(),
            new FragmentPg3(),
            new FragmentPg4(),
            new FragmentPg5(),
            new SubRptFragment()
    };
    private int currentFragmentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStemClubReportingBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);
        getSupportActionBar().setTitle("Reporting Template");


        btnNext = binding.nextButton;
        btnPrevious = binding.previousButton;
        fragmentManager = getSupportFragmentManager();

        updateButtonVisibility(); //hide and show buttons

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              navigateToNextFragment(); 
            }
        }); 

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToPreviousFragment();
            }
        });

        showCurrentFragment();
        updateTitle();
    }


    /*Showing current fragment*/
    private void showCurrentFragment() {
      Fragment currentFragment = fragments[currentFragmentIndex];

      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      fragmentTransaction.replace(R.id.fragment_container, currentFragment);
      fragmentTransaction.commit();

      updateButtonVisibility();
    }

    /*Show next fragment*/
    private void navigateToNextFragment() {
        currentFragmentIndex++;

        //hide next button
        if (currentFragmentIndex >= fragments.length -1){
            btnNext.setVisibility(View.GONE);
        }

        showCurrentFragment();
    }

    private void navigateToPreviousFragment() {
        currentFragmentIndex--;

        //show next button
        if (currentFragmentIndex < fragments.length -1){
            btnNext.setVisibility(View.VISIBLE);
        }

        showCurrentFragment();
    }


    /* Hide and show Previous button dynamically*/
    private void updateButtonVisibility(){
      if (currentFragmentIndex <= 0){
          btnPrevious.setVisibility(View.GONE);
      }
      else{
          btnPrevious.setVisibility(View.VISIBLE);
      }
    }

    private void updateTitle() {
        if (currentFragmentIndex == 5){
            getSupportActionBar().setTitle("Submit Report");
        }
    }
}