package com.android.somo.SharedFragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.somo.Constants.Constants;
import com.android.somo.Helpers.LocalStorage;
import com.android.somo.Models.StaffModel;
import com.android.somo.R;
import com.android.somo.databinding.FragmentPg1Binding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class FragmentPg1 extends Fragment {

    private FragmentPg1Binding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private String staffName, role, school, topic, date;
    private LocalStorage localStorage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPg1Binding.inflate(inflater, container, false);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        localStorage = new LocalStorage();
        localStorage.initializeSharedPreferences(getContext());
        retrieveName();

        //date picker
        binding.btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDatePickerDialog();
            }
        });


        binding.txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                school = binding.edtSchoolName.getText().toString();
                topic = binding.edtTopic.getText().toString();
                saveDataToLocal();

            }
        });

        binding.roleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               if (checkedId == binding.radioButtonTrainer.getId()){
                   role = Constants.TRAINER;
               }else if (checkedId == binding.radioBtnSupport.getId()){
                   role = Constants.SUPPORT_FELLOW;
               }
            }
        });

        retrieveDataFromLocal();
        return binding.getRoot();
    }


    //retrieve name from firebase database
    private void retrieveName() {
        database.getReference()
                .child("Staff")
                .child(mAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            if (snapshot.exists()){
                                StaffModel staffModel = snapshot.getValue(StaffModel.class);
                                staffName = staffModel.getStaffName();
                                binding.txtName.setText(staffName);
                            }
                            else {
                                Toast.makeText(getContext(), "Name not found", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "/" + (month +1 ) + "/" + year;
                binding.btnSelectDate.setText(date);

            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), dateSetListener, year, month, dayOfMonth);
        datePickerDialog.show();

    }

    /**Save data to local storage**/
    private void saveDataToLocal() {
      localStorage.storeData("staff_name", staffName);
      localStorage.storeData("staff_role", role);
      localStorage.storeData("session_date", date);
      localStorage.storeData("school", school);
      localStorage.storeData("topic", topic);
    }


    /**Retrieve saved data from local storage**/
    private void retrieveDataFromLocal() {
     try {
         if (!localStorage.retrieveData("session_date").isEmpty()){
             binding.btnSelectDate.setText(localStorage.retrieveData("session_date"));
         } else{
             binding.btnSelectDate.setText("Select Date");
         }


         if (!localStorage.retrieveData("school").isEmpty()){
             binding.edtSchoolName.setText(localStorage.retrieveData("school"));
         }else{
             binding.edtSchoolName.setHint("Enter School Name");
         }

         if (!localStorage.retrieveData("topic").isEmpty()){
             binding.edtTopic.setText(localStorage.retrieveData("topic"));
         }else {
             binding.edtTopic.setHint("Topic Covered");
         }
     }catch (Exception exception){
         exception.printStackTrace();
     }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}