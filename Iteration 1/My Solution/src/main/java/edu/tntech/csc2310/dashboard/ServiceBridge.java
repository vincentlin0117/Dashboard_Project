package edu.tntech.csc2310.dashboard;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceBridge {

    private static final String apiKey = "1F976517-D21F-4B16-87F3-15A1143E9F74";
    private static final String urlString = "https://portapit.tntech.edu/express/api/unprotected/getCourseInfoByAPIKey.php?Subject=%s&Term=%s&Key=%s";

    private CourseInstance[] courses;


    @GetMapping("/allcourses")
    public SemesterSchedule allcourses(@RequestParam(value = "term", defaultValue = "na") String term){
        // accessing the TNTECH webservice
        Gson gson = new Gson();
        try{
            String urlFmt = String.format(urlString,"",term, apiKey);
            URL url = new URL(urlFmt);
            BufferedReader in = new BufferedReader((new InputStreamReader(url.openStream())));
            JsonReader jr = gson.newJsonReader(in);
            courses = gson.fromJson(jr,CourseInstance[].class);
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        //setting subjectTerm and faculty
        for (CourseInstance c: courses){
            c.setSubjectTerm(term);
            c.setFaculty();
        }

        // creating the semsterSchedule
        SemesterSchedule semesterSchedule = new SemesterSchedule( "ALL",term, courses);

        return semesterSchedule;
    }

    @GetMapping("coursesbysubject")
    public SemesterSchedule coursesbysubject(@RequestParam(value = "term", defaultValue = "na") String term,
                                             @RequestParam(value = "subject", defaultValue = "na") String subject){
        //accessing the TNTECH Webserice
        Gson gson = new Gson();
        try{
            String urlFmt = String.format(urlString,subject.toUpperCase(),term,apiKey);
            URL url = new URL(urlFmt);
            BufferedReader in = new BufferedReader((new InputStreamReader(url.openStream())));
            JsonReader jr = gson.newJsonReader(in);
            courses = gson.fromJson(jr,CourseInstance[].class);
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        // setting subjectTerm and faculty object
        for (CourseInstance c: courses){
            c.setSubjectTerm(term);
            c.setFaculty();
        }

        // creating and returning semesterscheudle object
        SemesterSchedule semesterSchedule = new SemesterSchedule(subject,term, courses);
        return semesterSchedule;
    }

    @GetMapping("coursesbyfaculty")
    public CourseInstance[] coursesbyfaculty(@RequestParam(value = "term", defaultValue = "na") String term,
                                             @RequestParam(value = "subject", defaultValue = "na")String subject,
                                             @RequestParam(value = "firstname", defaultValue = "na") String firstname,
                                             @RequestParam(value = "lastname", defaultValue = "na") String lastname){
        Gson gson = new Gson();

        try{
            String urlFmt = String.format(urlString,subject.toUpperCase(),term,apiKey);
            URL url = new URL(urlFmt);
            BufferedReader in = new BufferedReader((new InputStreamReader(url.openStream())));
            JsonReader jr = gson.newJsonReader(in);
            courses = gson.fromJson(jr,CourseInstance[].class);
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        // Creating a array List with courseinstances and a faculty object with given name
        ArrayList<CourseInstance> returnValue = new ArrayList<>();
        Faculty prof = new Faculty(firstname,lastname);

        // set subjectTerm and Faculty
        for (CourseInstance c: courses){
            c.setSubjectTerm(term);
            c.setFaculty();
            // if professor is not null and if the professor name matches add the professor to the array
            if(c.getFaculty() != null && c.getFaculty().compareTo(prof) == 0){
                returnValue.add(c);
            }
        }

        // change the arrayList of CourseInstance to a CourseInstance Array
        CourseInstance[] result = new CourseInstance[returnValue.size()];
        return returnValue.toArray(result);

    }

    @GetMapping("coursebysection")
    public CourseInstance coursebysection(@RequestParam(value = "term", defaultValue = "na") String term,
                                             @RequestParam(value = "subject", defaultValue = "na")String subject,
                                             @RequestParam(value = "course", defaultValue = "na") String course,
                                             @RequestParam(value = "section", defaultValue = "na") String section){
        // Accessing the web service
        Gson gson = new Gson();
        try{
            String urlFmt = String.format(urlString,subject.toUpperCase(),term,apiKey);
            URL url = new URL(urlFmt);
            BufferedReader in = new BufferedReader((new InputStreamReader(url.openStream())));
            JsonReader jr = gson.newJsonReader(in);
            courses = gson.fromJson(jr,CourseInstance[].class);
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        CourseInstance result = null;

        // setting SubjectTerm and Faculty
        for (CourseInstance c: courses){
            c.setSubjectTerm(term);
            c.setFaculty();

            // If course and section matches result = the course
            if(c.getCOURSE().contentEquals(course) && c.getSECTION().contentEquals(section)){
                result = c;
            }
        }


        return result;
    }

    @GetMapping("schbydepartment")
    public SubjectCreditHours schbydepartment(@RequestParam(value = "term",defaultValue = "na") String term,
                                              @RequestParam(value = "subject", defaultValue = "na") String subject){
        // Accessing webservice
        Gson gson = new Gson();
        try{
            String urlFmt = String.format(urlString,subject.toUpperCase(),term,apiKey);
            URL url = new URL(urlFmt);
            BufferedReader in = new BufferedReader((new InputStreamReader(url.openStream())));
            JsonReader jr = gson.newJsonReader(in);
            courses = gson.fromJson(jr,CourseInstance[].class);
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        // hold total
        float total = 0;

        //looping through array
        for (CourseInstance c: courses){
            // if the cred and student count are not null change them to floats, multiply and then add it to the total
            if(c.getCREDITS() != null && c.getSTUDENTCOUNT() != null){
                total = total + (Float.parseFloat(c.getCREDITS()) * Float.parseFloat(c.getSTUDENTCOUNT()));
            }

        }

        // create everything else needed for subjecCreditHours, Round total incase it is a decimal
        SubjectTerm subjectTerm = new SubjectTerm(subject.toUpperCase(), term);
        SubjectCreditHours subjectCreditHours = new SubjectCreditHours(Math.round(total),subjectTerm);

        return subjectCreditHours;

    }

    @GetMapping("schbyfaculty")
    public FacultyCreditHours schbyfaculty(@RequestParam(value = "term", defaultValue = "na") String term,
                                           @RequestParam(value = "subject", defaultValue = "na") String subject,
                                           @RequestParam(value = "firstname",defaultValue = "na") String firstname,
                                           @RequestParam(value = "lastname",defaultValue = "na") String lastname) {
        // Accessing Webserice
        Gson gson = new Gson();

        try {
            String urlFmt = String.format(urlString, subject.toUpperCase(), term, apiKey);
            URL url = new URL(urlFmt);
            BufferedReader in = new BufferedReader((new InputStreamReader(url.openStream())));
            JsonReader jr = gson.newJsonReader(in);
            courses = gson.fromJson(jr, CourseInstance[].class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // creating objects needed for FacultyCreditHour Object
        Faculty prof = new Faculty(firstname,lastname);
        SubjectTerm subjectTerm = new SubjectTerm(subject.toUpperCase(), term);

        float total = 0; // use to hold total

        // set subjectTerm and Faculty
        for(CourseInstance c: courses){
             c.setSubjectTerm(term);
             c.setFaculty();
            // if professor is not null and matches the Faculty Object
             if(c.getFaculty() != null && c.getFaculty().compareTo(prof) == 0){
                 // if the credit and student is not null
                 if(c.getCREDITS() != null && c.getSTUDENTCOUNT() != null){
                     // change value to float and then multiply, add total
                     total = total + (Float.parseFloat(c.getCREDITS()) * Float.parseFloat(c.getSTUDENTCOUNT()));
                 }
             }
        }
        //create and return object
        FacultyCreditHours facultyCreditHours = new FacultyCreditHours(Math.round(total),subjectTerm,prof);

        return facultyCreditHours;
    }

}
