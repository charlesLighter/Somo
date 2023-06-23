package com.android.somo.StaffActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.somo.Adapters.StaffRptAdapter;
import com.android.somo.Constants.Constants;
import com.android.somo.Models.StemReportsModel;
import com.android.somo.R;
import com.android.somo.databinding.ActivityStemReportsViewBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StemReportsViewActivity extends AppCompatActivity {

    private ActivityStemReportsViewBinding binding;
    private StaffRptAdapter adapter;
    private ArrayList<StemReportsModel> rptList;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStemReportsViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Reports");

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        rptList = new ArrayList<>();
        adapter = new StaffRptAdapter(this, rptList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        binding.recyclerview.setLayoutManager(layoutManager);
        binding.recyclerview.setAdapter(adapter);

        showReports();

    }

    private void showReports() {
        database.getReference()
                .child(Constants.STAFF_REPORTS)
                .child(mAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                         try {
                             if (snapshot.exists()){
                                rptList.clear();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    StemReportsModel stemReportsModel = dataSnapshot.getValue(StemReportsModel.class);
                                    assert stemReportsModel != null;
                                    stemReportsModel.setReportId(dataSnapshot.getKey());
                                    rptList.add(stemReportsModel);
                                }
                                adapter.notifyDataSetChanged();
                             }
                             else {
                                 binding.txtNoRpt.setVisibility(View.VISIBLE);
                             }

                         }catch (Exception e){
                             e.printStackTrace();
                         }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Firebase", "database error " + error.getMessage());
                    }
                });
    }
}