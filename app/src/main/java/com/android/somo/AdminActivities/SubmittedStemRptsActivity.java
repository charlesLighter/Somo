package com.android.somo.AdminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.somo.Adapters.StemReportsAdapter;
import com.android.somo.Constants.Constants;
import com.android.somo.Models.StaffModel;
import com.android.somo.Models.StemReportsModel;
import com.android.somo.R;
import com.android.somo.databinding.ActivitySubmittedStemRptsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SubmittedStemRptsActivity extends AppCompatActivity {

    private ActivitySubmittedStemRptsBinding binding;
    private FirebaseDatabase database;
    private StemReportsAdapter adapter;
    private ArrayList<StemReportsModel> stemRptList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubmittedStemRptsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Submitted Reports");

        database = FirebaseDatabase.getInstance();
        stemRptList = new ArrayList<>();
        adapter = new StemReportsAdapter(this, stemRptList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        binding.recyclerview.setLayoutManager(layoutManager);
        binding.recyclerview.setAdapter(adapter);

        showSubmittedReports();

    }

    private void showSubmittedReports(){
      database.getReference()
              .child(Constants.SUBMITTED_REPORTS)
              .addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      if (snapshot.exists()){
                          try {
                              if (snapshot.hasChildren()){

                                  stemRptList.clear();
                                  for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                      StemReportsModel model = dataSnapshot.getValue(StemReportsModel.class);
                                      assert model != null;
                                      model.setReportId(dataSnapshot.getKey());
                                      stemRptList.add(model);
                                  }
                                  adapter.notifyDataSetChanged();

                              }else {
                                  binding.txtNoRpt.setVisibility(View.VISIBLE);
                              }

                          }
                          catch (Exception e){
                              e.printStackTrace();
                          }
                      }else {
                          binding.txtNoRpt.setVisibility(View.VISIBLE);
                      }
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {
                      Log.e("Firebase Database", "Database error" + error.getMessage());
                      Toast.makeText(SubmittedStemRptsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                  }
              });
    }
}