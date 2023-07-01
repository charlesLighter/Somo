package com.android.somo.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.somo.AdminActivities.SubmittedStemRptDetailsActivity;
import com.android.somo.Models.StemReportsModel;
import com.android.somo.R;
import com.android.somo.StaffActivities.StemRptDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StemReportsAdapter extends RecyclerView.Adapter<StemReportsAdapter.ReportsViewHolder>{

     public Context context;
     public ArrayList<StemReportsModel> stemRptList;

    public StemReportsAdapter(Context context, ArrayList<StemReportsModel> stemRptList) {
        this.context = context;
        this.stemRptList = stemRptList;
    }

    @NonNull
    @Override
    public ReportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_stem_submitted_rpt, parent, false);
        return new ReportsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportsViewHolder holder, int position) {
       StemReportsModel model = stemRptList.get(position);

        //school name
        holder.txtSchool.setText(model.getSchool());
        //staff name
        holder.txtStaffName.setText(model.getStaffName());

        //Report submission date
        Date date = new Date(model.getTimestamp());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd-MMM-yy   hh:mm a", Locale.getDefault());
        String submissionDate = simpleDateFormat.format(date);
        holder.txtDate.setText(submissionDate);

        //Extracting a timestamp and staff first name from the report ID
        String reportId = model.getReportId();
        String firstName = reportId.replaceAll("[^a-zA-Z]", "");
        long timestamp = Long.parseLong(reportId.replaceAll("[^\\d]", ""));

        Date mDate = new Date(timestamp);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmss");
        String rptSubmissionTime = sdf.format(mDate);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SubmittedStemRptDetailsActivity.class)
                        .putExtra("staff_name", model.getStaffName())
                        .putExtra("staff_role", model.getRole())
                        .putExtra("session_date", model.getSessionDate())
                        .putExtra("submission_date", submissionDate)
                        .putExtra("school", model.getSchool())
                        .putExtra("topic", model.getTopic())
                        .putExtra("session_overview", model.getSessionOverview())
                        .putExtra("student_engagement", model.getStudentEngagement())
                        .putExtra("skill_demonstrated", model.getDemonstratedSkills())
                        .putExtra("project_progress", model.getProjectProgress())
                        .putExtra("challenges", model.getChallengesEncountered())
                        .putExtra("support_provided", model.getSupportProvided())
                        .putExtra("next_steps", model.getNextSteps())
                        .putExtra("feedback", model.getFeedback())
                        .putExtra("firstName", firstName)
                        .putExtra("rptSubmissionTime", rptSubmissionTime));
            }
        });

    }

    @Override
    public int getItemCount() {
        return stemRptList.size();
    }

    public class ReportsViewHolder extends RecyclerView.ViewHolder{
        public TextView txtSchool, txtStaffName, txtDate;
        public ReportsViewHolder(@NonNull View itemView) {
            super(itemView);

            txtSchool = itemView.findViewById(R.id.txtSchoolName);
            txtStaffName = itemView.findViewById(R.id.txtName);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
