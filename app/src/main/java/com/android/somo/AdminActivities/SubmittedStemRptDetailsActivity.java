package com.android.somo.AdminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.somo.PDF.PdfGenerator;
import com.android.somo.R;
import com.android.somo.databinding.ActivitySubmiitedStemRptDetailsBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.net.MalformedURLException;

public class SubmittedStemRptDetailsActivity extends AppCompatActivity {

    private ActivitySubmiitedStemRptDetailsBinding binding;
    private PdfGenerator pdfGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubmiitedStemRptDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pdfGenerator = new PdfGenerator(this);

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
        String firstName = getIntent().getStringExtra("firstName");
        String rptSubmissionTime = getIntent().getStringExtra("rptSubmissionTime");

        //set data to pdf Generator
        pdfGenerator.setDocumentData(getIntent().getStringExtra("staff_name"),
                getIntent().getStringExtra("staff_role"),
                getIntent().getStringExtra("session_date"),
                getIntent().getStringExtra("school"),
                getIntent().getStringExtra("topic"),
                getIntent().getStringExtra("session_overview"),
                getIntent().getStringExtra("student_engagement"),
                getIntent().getStringExtra("skill_demonstrated"),
                getIntent().getStringExtra("project_progress"),
                getIntent().getStringExtra("challenges"),
                getIntent().getStringExtra("support_provided"),
                getIntent().getStringExtra("next_steps"),
                getIntent().getStringExtra("feedback"),
                firstName, rptSubmissionTime);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stem_report_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.action_download:
                requestRuntimePermission();
                break;
            case R.id.action_locate_file:
                //TODO: Locate downloaded PDF file from internal storage
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*For requesting permission to Write to external storage*/
    private void requestRuntimePermission(){
        Dexter.withContext(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        downloadReport();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                      if (permissionDeniedResponse.isPermanentlyDenied()){

                          new AlertDialog.Builder(SubmittedStemRptDetailsActivity.this)
                                  .setTitle("Permission Denied")
                                  .setMessage("Permission to write to external storage is permanently denied. Please go to settings and enable permission")
                                  .setNegativeButton("Cancel", null)
                                  .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialog, int which) {
                                       //TODO: Create intent to access settings
                                      }
                                  })
                                  .show();
                      }
                      else {
                          Toast.makeText(SubmittedStemRptDetailsActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                      }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    /** For downloading report in PDF format**/
    private void downloadReport() {
        Toast.makeText(this, "Downloading report...", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    pdfGenerator.generatePDF();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 4000);
    }
}