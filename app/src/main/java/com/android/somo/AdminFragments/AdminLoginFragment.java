package com.android.somo.AdminFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.somo.R;
import com.android.somo.SharedActivities.MainActivity;
import com.android.somo.databinding.FragmentAdminLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class AdminLoginFragment extends Fragment {

    private FragmentAdminLoginBinding binding;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private String adminEmail;
    private String adminPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminLoginBinding.inflate(inflater, container, false);


        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Signing In");
        progressDialog.setMessage("Please wait");

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminEmail = binding.edtAdminEmail.getText().toString();
                adminPassword = binding.edtAdminPassword.getText().toString().trim();
                validateInput(adminEmail, adminPassword);
            }
        });


        return binding.getRoot();
    }

    private void validateInput(String adminEmail, String adminPassword){
        if (!adminEmail.isEmpty() && !adminPassword.isEmpty()){
            loginAdmin();
        }
        else {
            Toast.makeText(getContext(), "Provide credentials", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginAdmin(){
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(adminEmail, adminPassword)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                       progressDialog.dismiss();
                       startActivity(new Intent(getContext(), MainActivity.class)
                               .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                      progressDialog.dismiss();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}