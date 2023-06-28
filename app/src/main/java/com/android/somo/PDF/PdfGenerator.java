package com.android.somo.PDF;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.android.somo.AdminActivities.SubmittedStemRptDetailsActivity;
import com.android.somo.R;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

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
           skillDemonstrated, projectProgress, challengesEncountered, supportProvided, nextSteps, feedback, firstname, reportingTime;

    //constructor
    public PdfGenerator(Context context) {
        this.context = context;
    }

    public void setDocumentData(String staffName, String staffRole, String sessionDate, String mSchool,
                                String topicCovered, String session_overview, String student_engagement,
                                String demonstrated_skill, String project_progress, String challenges,
                                String provided_support, String next_steps, String session_feedback, String firstName, String rptTime){


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

        if (firstName != null){
            firstname = firstName;
        }

        if (rptTime != null){
            reportingTime = rptTime;
        }

    }


    public void generatePDF()  {
        //document it
        String docId = reportingTime + firstname;
        //path
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                + File.separator + "stem_club_session_report254" + docId + ".pdf";
        PdfWriter pdfWriter;
        try {
            pdfWriter = new PdfWriter(filePath);
        }catch (IOException e){
            Toast.makeText(context, "Error creating pdf: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        //create the Document
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument, PageSize.A4);
        //set margins
        document.setLeftMargin(46f);
        document.setRightMargin(46f);
        document.setTopMargin(46f);
        document.setBottomMargin(46f);

        // Get the resource id of the logo image
        int imageResId = R.raw.somo_logo;


        // Get the path of the logo image
        String logoPath = "android.resource://" + context.getPackageName() + "/" + imageResId;

        // Load the Calibri font
        PdfFont documentFont;
        try {
            //calibriFont = PdfFontFactory.createFont("fonts/calibri.ttf", "Identity-H", true);
            documentFont = PdfFontFactory.createFont(FontConstants.HELVETICA);
        } catch (IOException e) {
            Toast.makeText(context, "Error loading font: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        // Set font color
        Color blackFontColor = new DeviceRgb(0, 0, 0);
        Color lightBlueFontColor = new DeviceRgb(1, 87, 155);
        Color tealFontColor = new DeviceRgb(1, 135, 134);

        //add content to the document
        try {

            //TODO: Add header to the document
            //Add title
            Paragraph title = new Paragraph("STEM Club Session Reporting Template")
                    .setFont(documentFont)
                    .setFontSize(14)
                    .setBold()
                    .setFontColor(blackFontColor)
                    .setMarginTop(20f)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            //Add name
            Paragraph staffName = new Paragraph();
            staffName.add("Name: " + name)
                    .setFontColor(blackFontColor)
                    .setFont(documentFont)
                    .setFontSize(12f)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(staffName);

            //Add Role
            Paragraph staffRole = new Paragraph();
            staffRole.add("Role: " + role)
                    .setFontColor(blackFontColor)
                    .setFont(documentFont)
                    .setFontSize(12f)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginTop(3f);
            document.add(staffRole);

            //Add date
            Paragraph dateOfSession = new Paragraph();
            dateOfSession.add("Date of Session: " + date)
                    .setFontColor(blackFontColor)
                    .setFont(documentFont)
                    .setFontSize(12f)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(dateOfSession);

            //Add venue
            Paragraph venue = new Paragraph();
            venue.add("Venue: " + school)
                    .setFontColor(blackFontColor)
                    .setFont(documentFont)
                    .setFontSize(12f)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(venue);

            //Add topic
            Paragraph topicCovered = new Paragraph();
            topicCovered.add("Topic: " + topic)
                    .setFontColor(blackFontColor)
                    .setFont(documentFont)
                    .setFontSize(12f)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(topicCovered);

            /************Session Overview**************/
            Paragraph sessionOverViewHeading = new Paragraph("Session Overview:")
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(lightBlueFontColor)
                    .setFontSize(12f)
                    .setMarginTop(10f);
            document.add(sessionOverViewHeading);

            Paragraph sessionOverViewDescription = new Paragraph("[Provide a brief overview of what was covered during the session, including key concepts, activities, and any materials or resources used.]")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(tealFontColor)
                    .setFontSize(12f)
                    .setMarginTop(0f);
            document.add(sessionOverViewDescription);

            Paragraph sessionOverViewResponse = new Paragraph(sessionOverview)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(blackFontColor)
                    .setFontSize(12f)
                    .setMarginTop(0f);
            document.add(sessionOverViewResponse);


            /************Student Engagement and Participation**************/
            Paragraph studentEngagementHeading = new Paragraph("Student Engagement and Participation:")
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(lightBlueFontColor)
                    .setFontSize(12f)
                    .setMarginTop(10f);
            document.add(studentEngagementHeading);

            Paragraph studentEngagementDescription = new Paragraph("[Describe the level of engagement and participation observed among the students during the session. Were they actively involved in discussions, hands-on activities, and project work?]")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(tealFontColor)
                    .setFontSize(12f)
                    .setMarginTop(0f);
            document.add(studentEngagementDescription);

            Paragraph studentEngagementResponse = new Paragraph(studentEngagement)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(blackFontColor)
                    .setFontSize(12f)
                    .setMarginTop(0f);
            document.add(studentEngagementResponse);


            /************Skills and Concepts Demonstrated**************/
            Paragraph skillsDemonstratedHeading = new Paragraph("Skills and Concepts Demonstrated:")
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(lightBlueFontColor)
                    .setFontSize(12f)
                    .setMarginTop(10f);
            document.add(skillsDemonstratedHeading);

            Paragraph skillDemonstratedDescription = new Paragraph("[Outline the specific skills and concepts that were demonstrated by the students during the session. Highlight any notable achievements or areas where improvement is needed.]")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(tealFontColor)
                    .setFontSize(12f)
                    .setMarginTop(0f);
            document.add(skillDemonstratedDescription);

            Paragraph skillDemonstratedResponse = new Paragraph(skillDemonstrated)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(blackFontColor)
                    .setFontSize(12f)
                    .setMarginTop(0f);
            document.add(skillDemonstratedResponse);


            /************Project Progress and Completion**************/
            Paragraph projectProgressHeading = new Paragraph("Project Progress and Completion:")
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(lightBlueFontColor)
                    .setFontSize(12f)
                    .setMarginTop(10f);
            document.add(projectProgressHeading);

            Paragraph projectProgressDescription = new Paragraph("[Detail the progress made by the students on their projects related to the session's topic. Indicate whether the projects were completed or if there are any ongoing tasks or milestones.]")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(tealFontColor)
                    .setFontSize(12f)
                    .setMarginTop(0f);
            document.add(projectProgressDescription);

            Paragraph projectProgressResponse = new Paragraph(projectProgress)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(blackFontColor)
                    .setFontSize(12f)
                    .setMarginTop(0f);
            document.add(projectProgressResponse);

            //create the second page to the document
            pdfDocument.addNewPage();

            /************Challenges Encountered**************/
            Paragraph challengesEncounteredHeading = new Paragraph("Challenges Encountered:")
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(lightBlueFontColor)
                    .setFontSize(12f)
                    .setMarginTop(10f);
            document.add(challengesEncounteredHeading);

            Paragraph challengesEncounteredDescription = new Paragraph("[Identify any challenges or difficulties faced by the students during the session. This could include technical issues, conceptual hurdles, or other obstacles they encountered while working on their projects.]")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(tealFontColor)
                    .setFontSize(12f)
                    .setMarginTop(0f);
            document.add(challengesEncounteredDescription);

            Paragraph challengesEncounteredResponse = new Paragraph(challengesEncountered)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(blackFontColor)
                    .setFontSize(12f)
                    .setMarginTop(0f);
            document.add(challengesEncounteredResponse);


            /************Support and Guidance Provided**************/
            Paragraph supportProvidedHeading = new Paragraph("Support and Guidance Provided:")
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(lightBlueFontColor)
                    .setFontSize(12f)
                    .setMarginTop(10f);
            document.add(supportProvidedHeading);

            Paragraph supportProvidedDescription = new Paragraph("[Explain the support and guidance provided by the trainers to help students overcome challenges and further their understanding of the topic. Include any resources, references, or one-on-one assistance offered.]")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(tealFontColor)
                    .setFontSize(12f)
                    .setMarginTop(0f);
            document.add(supportProvidedDescription);

            Paragraph supportProvidedResponse = new Paragraph(supportProvided)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(blackFontColor)
                    .setFontSize(12f)
                    .setMarginTop(0f);
            document.add(supportProvidedResponse);

            /************Next Steps**************/
            Paragraph nextStepHeading = new Paragraph("Next Steps:")
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(lightBlueFontColor)
                    .setFontSize(12f)
                    .setMarginTop(10f);
            document.add(nextStepHeading);

            Paragraph nextStepDescription = new Paragraph("[Specify the next steps for the students, including upcoming topics, project objectives, or assignments. Provide guidance on how they can continue their learning and progress.]")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(tealFontColor)
                    .setFontSize(12f)
                    .setMarginTop(0f);
            document.add(nextStepDescription);

            Paragraph nextStepResponse = new Paragraph(nextSteps)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(blackFontColor)
                    .setFontSize(12f)
                    .setMarginTop(0f);
            document.add(nextStepResponse);

            /************Feedback and Suggestions**************/
            Paragraph feedbackAndSuggestionHeading = new Paragraph("Feedback and Suggestions:")
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(lightBlueFontColor)
                    .setFontSize(12f)
                    .setMarginTop(10f);
            document.add(feedbackAndSuggestionHeading);

            Paragraph feedbackAndSuggestionDescription = new Paragraph("[Include any feedback or suggestions for improvement based on the session, such as adjustments to activities, resources, or teaching strategies.]")
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(tealFontColor)
                    .setFontSize(12f)
                    .setMarginTop(0f);
            document.add(feedbackAndSuggestionDescription);

            Paragraph feedbackAndSuggestionResponse = new Paragraph(feedback)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFont(documentFont)
                    .setFontColor(blackFontColor)
                    .setFontSize(12f)
                    .setMarginTop(0f);
            document.add(feedbackAndSuggestionResponse);



            document.close();
            Toast.makeText(context, "Download successful" + filePath, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Toast.makeText(context, "Error adding content to pdf" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }





}
