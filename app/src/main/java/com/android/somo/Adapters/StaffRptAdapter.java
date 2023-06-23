package com.android.somo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.somo.Models.StemReportsModel;
import com.android.somo.R;
import com.android.somo.StaffActivities.StemRptDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StaffRptAdapter  extends RecyclerView.Adapter<StaffRptAdapter.VIEWHOLDER> {

    public Context context;
    public ArrayList<StemReportsModel> stemRptList;

    public StaffRptAdapter(Context context, ArrayList<StemReportsModel> stemRptList) {
        this.context = context;
        this.stemRptList = stemRptList;
    }

    @NonNull
    @Override
    public VIEWHOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_stem_report, parent, false);
        return new VIEWHOLDER(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VIEWHOLDER holder, int position) {
        StemReportsModel model = stemRptList.get(position);

        holder.txtSchool.setText(model.getSchool());

        Date date = new Date(model.getTimestamp());
        SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("dd-MMM-yy    hh:mm a");
        holder.textDate.setText(simpleDateFormat.format(date));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, StemRptDetailsActivity.class)
                        .putExtra("staff_name", model.getStaffName())
                        .putExtra("staff_role", model.getRole())
                        .putExtra("session_date", model.getSessionDate())
                        .putExtra("school", model.getSchool())
                        .putExtra("topic", model.getTopic())
                        .putExtra("session_overview", model.getSessionOverview())
                        .putExtra("student_engagement", model.getStudentEngagement())
                        .putExtra("skill_demonstrated", model.getDemonstratedSkills())
                        .putExtra("project_progress", model.getProjectProgress())
                        .putExtra("challenges", model.getChallengesEncountered())
                        .putExtra("support_provided", model.getSupportProvided())
                        .putExtra("next_steps", model.getNextSteps())
                        .putExtra("feedback", model.getFeedback()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return stemRptList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class VIEWHOLDER extends RecyclerView.ViewHolder{
        TextView txtSchool, textDate;
        public VIEWHOLDER(@NonNull View itemView) {
            super(itemView);

            txtSchool = itemView.findViewById(R.id.txtSchoolName);
            textDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
