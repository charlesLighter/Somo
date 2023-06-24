package com.android.somo.PDF;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.android.somo.AdminActivities.SubmittedStemRptDetailsActivity;
import com.android.somo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@SuppressWarnings("ALL")
public class PdfGenerator {

    //context
    private Context context;

    //header image
    public Bitmap logoBitmap;

    //header text data
    private String address = "Ochieng Avenue";
    private String postalCode = "P.O.BOX 1721-40100, Kisumu";
    private String phone = "Tell: +254795177370";
    private String email = "Email: hello@somo.africa";
    private String website = "Website: www.somo.africa";

    //document's data
    public String name, role, date, school, topic, sessionOverview, studentEngagement,
           skillDemonstrated, projectProgress, challengesEncountered, supportProvided, nextSteps, feedback;

    //constructor
    public PdfGenerator(Context context) {
        this.context = context;
    }

    public void setDocumentData(String staffName, String staffRole, String sessionDate, String mSchool,
                                String topicCovered, String session_overview, String student_engagement,
                                String demonstrated_skill, String project_progress, String challenges,
                                String provided_support, String next_steps, String session_feedback){


        if (staffName != null){
            name = staffName;
        }

        if (staffRole != null){
            role = staffRole;
        }

        if (sessionDate != null){
            date = sessionDate;
        }

        if (mSchool != null){
            school = mSchool;
        }

        if (topicCovered!= null){
            topic = topicCovered;
        }

        if (session_overview != null){
            sessionOverview = session_overview;
        }

        if (student_engagement!= null){
            studentEngagement = student_engagement;
        }

        if (demonstrated_skill != null){
            skillDemonstrated = demonstrated_skill;
        }

        if (project_progress != null){
            projectProgress = project_progress;
        }

        if (challenges != null){
            challengesEncountered = challenges;
        }

        if (provided_support != null){
            supportProvided = provided_support;
        }

        if (next_steps != null){
            nextSteps = next_steps;
        }

        if (session_feedback != null){
            feedback = session_feedback;
        }

    }



    public void generatePDF(){
        PdfDocument pdfDocument = new PdfDocument();

        //create A4 size document page
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        //Page canvas
        Canvas canvas = page.getCanvas();

        //Document text
        String text = "STEM Club Session Reporting Template\n" +
                "Name: " + name+" \n" +
                "Role: " + role+" \n" +
                "Date of Session: "+ date+ " \n" +
                "Venue: " +school + " \n" +
                "Topic Covered: "+topic+ " \n\n" +
                "Session Overview: \n" +
                "[Provide a brief overview of what was covered during the session, including key concepts, activities, and any materials or resources used.] \n" +
                 sessionOverview + " \n\n" +
                "Student Engagement and Participation:\n" +
                "[Describe the level of engagement and participation observed among the students during the session. Were they actively involved in discussions, hands-on activities, and project work?] \n" +
                studentEngagement + " \n\n" +
                "Skills and Concepts Demonstrated:\n" +
                "[Outline the specific skills and concepts that were demonstrated by the students during the session. Highlight any notable achievements or areas where improvement is needed.] \n" +
                skillDemonstrated + " \n\n" +
                "Project Progress and Completion:\n" +
                "[Detail the progress made by the students on their projects related to the session's topic. Indicate whether the projects were completed or if there are any ongoing tasks or milestones.] \n" +
                projectProgress+ " \n\n";

        //set size and position for text
        int x = 10;
        int y = 10;
        int maxWidth = 575;

        //paint obj for font size and style
        Paint paint = new Paint();
        paint.setTextSize(12f);

        //Split lines
        String[] lines = text.split("\n");

        //draw each line of text on the canvas
        for (String line : lines){
            canvas.drawText(line, x,y, paint);
            y += paint.descent() - paint.ascent();
        }

        // Draw the header logo
        logoBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.somo_logo);
        int logoWidth = 100; //TODO: Adjust the logo width as needed
        int logoHeight = 60; //TODO:  Adjust the logo height as needed
        int logoMargin = 10; // TODO: Adjust the logo margin as needed
        Rect logoRect = new Rect(logoMargin, logoMargin, logoMargin + logoWidth, logoMargin + logoHeight);
        canvas.drawBitmap(logoBitmap, null, logoRect, null);

        // Draw the header text
        int textMargin = 10; //TODO: Adjust the text margin as needed
        int lineHeight = 20; //TODO: Adjust the line height as needed
        int addressX = canvas.getWidth() - textMargin;
        int addressY = logoMargin + logoHeight + textMargin + lineHeight;
        canvas.drawText(address, addressX, addressY, paint);

        int postalCodeX = canvas.getWidth() - textMargin;
        int postalCodeY = addressY + lineHeight;
        canvas.drawText(postalCode, postalCodeX, postalCodeY, paint);

        int phoneX = canvas.getWidth() - textMargin;
        int phoneY = addressY + lineHeight;
        canvas.drawText(phone, phoneX, phoneY, paint);

        int emailX = canvas.getWidth() - textMargin;
        int emailY = phoneY + lineHeight;
        canvas.drawText(email, emailX, emailY, paint);

        int websiteX = canvas.getWidth() - textMargin;
        int websiteY = emailY + lineHeight;
        canvas.drawText(website, websiteX, websiteY, paint);

        //finish the current page
        pdfDocument.finishPage(page);

        //Create a new Directory for the PDF files
        String directoryPath = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        //create stem club file in the somo reports dir
        File file = new File(directory, "stem_club_session_report_charles.pdf");

        try {
            //save the pdf document as a file
            FileOutputStream outputStream = new FileOutputStream(file);
            pdfDocument.writeTo(outputStream);
            pdfDocument.close();
            outputStream.close();

            //TODO: provide user feedback
            Toast.makeText(context, "Download Successful", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(context, "Download Failed", Toast.LENGTH_SHORT).show();
        }


    }




}
