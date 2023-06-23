package com.android.somo.AdminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.somo.Models.StaffModel;
import com.android.somo.databinding.ActivityAddNewStaffBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewStaffActivity extends AppCompatActivity {

    private ActivityAddNewStaffBinding binding;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private String staffFirstName, staffLastName, staffName, staffEmail, staffPassword, pswd,  staffUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewStaffBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("New Staff");

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating New User...");


        binding.btnRgstNewStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                staffFirstName = binding.edtStaffFirstName.getText().toString();
                staffLastName = binding.edtStaffLastName.getText().toString();
                staffEmail = binding.edtStaffEmail.getText().toString();
                staffPassword = binding.edtStaffPassword.getText().toString();
                pswd = binding.confimedPass.getText().toString();
                validateInput(staffFirstName, staffLastName, staffEmail, staffPassword, pswd);
            }
        });



    }

    private void validateInput(String staffFirstName, String staffLastName, String staffEmail, String staffPassword, String confPass){

        if (!staffFirstName.isEmpty()  && !staffLastName.isEmpty() && !staffEmail.isEmpty()
                && !staffPassword.isEmpty() && !confPass.isEmpty()){

            staffName = staffFirstName + " " + staffLastName;
            if (confPass.equals(staffPassword)){
                registerNewStaff();
            }
            else {
                Toast.makeText(this, "Password don't match", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(this, "Provide required details", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerNewStaff() {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(staffEmail, staffPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            staffUID = task.getResult().getUser().getUid();
                            storeStaffInfo();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AddNewStaffActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void storeStaffInfo() {
        StaffModel staffModel = new StaffModel(staffName, staffEmail);
        staffModel.setStaffUID(staffUID);
        database.getReference()
                .child("Staff")
                .child(staffUID)
                .setValue(staffModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(AddNewStaffActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AddNewStaffActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}