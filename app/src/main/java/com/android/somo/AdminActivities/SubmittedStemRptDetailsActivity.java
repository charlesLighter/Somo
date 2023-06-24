package com.android.somo.AdminActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.somo.PDF.PdfGenerator;
import com.android.somo.R;
import com.android.somo.databinding.ActivitySubmiitedStemRptDetailsBinding;

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
                getIntent().getStringExtra("feedback"));
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
                downloadReport();
                break;
            case R.id.action_locate_file:
                //TODO: Locate downloaded PDF file from internal storage
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /** For downloading report in PDF format**/
    private void downloadReport() {
        Toast.makeText(this, "Downloading report...", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO: Request permission to write to external storage
                pdfGenerator.generatePDF();
            }
        }, 3000);
    }
}