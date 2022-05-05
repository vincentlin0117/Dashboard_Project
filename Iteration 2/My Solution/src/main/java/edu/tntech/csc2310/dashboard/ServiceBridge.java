package edu.tntech.csc2310.dashboard;

import com.fasterxml.jackson.databind.deser.CreatorProperty;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import edu.tntech.csc2310.dashboard.data.*;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.ranges.Range;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.*;

@RestController
public class ServiceBridge {

    private static final String apiKey = "1F976517-D21F-4B16-87F3-15A1143E9F74";

    private static final String urlString = "https://portapit.tntech.edu/express/api/unprotected/getCourseInfoByAPIKey.php?Subject=%s&Term=%s&Key=%s";

    /***
     * Courses returns a CourseInstance array when passes a subject and term.
     * If either the subject or term does not exist, it will return an empty array
     * <p>NOTE: Spring: 10 Summer: 50 Fall: 80</p>
     * @param subject (String) enter the subject code EX: csc EX: ENGL
     * @param term (String) enter in a term, in format of YearTerm (yyyytt) EX: 202010
     * @return Course instance array
     */
    private CourseInstance[] courses(String subject, String term) {

        String serviceString = String.format(urlString, subject.toUpperCase(), term, apiKey);
        Gson gson = new Gson();
        CourseInstance[] courses = null;

        try {
            URL url = new URL(serviceString);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            JsonReader jr = gson.newJsonReader(in);
            courses = gson.fromJson(jr, CourseInstance[].class);

            for (CourseInstance c: courses){
                c.setSubjectterm(term);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }

    /***
     * Courses return a SemesterSchedule object that has a list of all of the courses in the given semester.
     * If a invalid term is entered, the Schedule object will be empty
     * <p>NOTE: Spring: 10 Summer: 50 Fall: 80</p>
     * @param term (String) enter in a term, in format of YearTerm (yyyytt) EX: 202010
     * @return Semester Schedule object (has a SubjectTerm, CourseInstance array)
     */
    @GetMapping("/allcourses")
    public SemesterSchedule allcourses(
            @RequestParam(value = "term", defaultValue = "na") String term
    ) {

        String urlString = "https://portapi.tntech.edu/express/api/unprotected/getCourseInfoByAPIKey.php?Term=%s&Key=%s";
        String serviceString = String.format(urlString, term, apiKey);
        Gson gson = new Gson();
        CourseInstance[] gm = null;
        SemesterSchedule schedule = null;

        try {
            URL url = new URL(serviceString);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            JsonReader jr = gson.newJsonReader(in);
            gm = gson.fromJson(jr, CourseInstance[].class);

            for (CourseInstance c: gm){
                c.setSubjectterm(term);
            }

            SubjectTerm subjectTerm = new SubjectTerm("ALL", term);
            schedule = new SemesterSchedule(subjectTerm, gm);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    /***
     * Allcourses returns a SemesterSchedule object with a list of all the courses in a given year and subject/department.
     * If either term or subject is invalid it will return an empty array.
     * <p>NOTE: Spring: 10 Summer: 50 Fall: 80</p>
     * @param subject (String) enter the subject code EX: csc EX: ENGL
     * @param term (String) enter in a term, in format of YearTerm (yyyytt) EX:202010 EX: 202280
     * @return SemesterSechulde Object (has a SubjectTerm, CourseInstance array)
     */
    @GetMapping("/coursesbysubject")
    public SemesterSchedule coursesbysubject(
            @RequestParam(value = "subject", defaultValue = "CSC") String subject,
            @RequestParam(value = "term", defaultValue = "202210") String term
    ){
        CourseInstance[] courses = this.courses(subject, term);
        SubjectTerm subjectTerm = new SubjectTerm(subject, term);
        SemesterSchedule schedule = new SemesterSchedule(subjectTerm, courses);
        return schedule;
    }

    /***
     * Coursesbyfaculty gets all courses in a subject at 1 term and then filters the list of courses taught by a given professor/faculty member.
     * If any of the parameters are invaid, code will return an empty list
     * <p>NOTE: Spring: 10 Summer: 50 Fall: 80</p>
     * @param subject (String) subject enter the subject code EX: csc EX: ENGL
     * @param term (String) enter in a term, in format of YearTerm (yyyytt) EX: 202010
     * @param lastname (String) enter in the last name of professor EX: Gannod EX: Crockett
     * @param firstname (String) enter in the firstname and middle name EX: Gerald C EX: April Renee
     * @return CourseInstance array
     */
    @GetMapping("/coursesbyfaculty")
    public ArrayList<CourseInstance> coursesbyfaculty(
            @RequestParam(value = "subject", defaultValue = "CSC") String subject,
            @RequestParam(value = "term", defaultValue = "202210") String term,
            @RequestParam(value = "lastname", defaultValue = "Gannod") String lastname,
            @RequestParam(value = "firstname", defaultValue = "Gerald C") String firstname
    ) {

        CourseInstance[] courses = this.courses(subject, term);

        ArrayList<CourseInstance> list = new ArrayList<>();

        for (CourseInstance c: courses){
            Faculty f = c.getFaculty();
            if (f.getLastname() != null && f.getFirstname() != null) {
                if (lastname.toLowerCase().contentEquals(f.getLastname().toLowerCase()) && firstname.toLowerCase().contentEquals(f.getFirstname().toLowerCase()))
                    list.add(c);
            }
        }
        return list;
    }

    /***
     * Coursebysection finds a specific course section when given a term, subject, course number, and section number.
     * If any of the paramters are invalid, a emtpy CourseInstance object will be returned
     * <p>NOTE: Spring: 10 Summer: 50 Fall: 80</p>
     * @param subject (String) subject enter the subject code EX: csc EX: ENGL
     * @param term (String) enter in a term, in format of YearTerm (yyyytt) EX: 202010
     * @param course (String) course number EX: 2310
     * @param section (String) course section number EX: 001 EX: 104
     * @return Courseinstance object (has a subjectTerm, Faculty, other info about course)
     */
    @GetMapping("/coursebysection")
    public CourseInstance coursebysection(
            @RequestParam(value = "subject", defaultValue = "CSC") String subject,
            @RequestParam(value = "term", defaultValue = "202210") String term,
            @RequestParam(value = "course", defaultValue = "2310") String course,
            @RequestParam(value = "section", defaultValue = "001") String section
    ) {
        CourseInstance[] courses = this.courses(subject, term);

        CourseInstance result = null;
        for (CourseInstance c: courses){
            if (c.getCOURSE().contentEquals(course) && c.getSECTION().contentEquals(section))
                result = c;
        }
        return result;
    }

    /***
     * schbydepartment calculates the total amount of student credit hours generated by a specific department/subject in a given term.
     * <p>NOTE: Spring: 10 Summer: 50 Fall: 80</p>
     * @param subject (String) subject enter the subject code EX: csc EX: ENGL
     * @param term (String) enter in a term, in format of YearTerm (yyyytt) EX:202010
     * @return returns a SubjectCreditHours object (has a subjectTerm, total sch generated)
     */
    @GetMapping("/schbydepartment")
    public SubjectCreditHours creditHours(
            @RequestParam(value = "subject", defaultValue = "CSC") String subject,
            @RequestParam(value = "term", defaultValue = "202210") String term
    ) {

        CourseInstance[] gm = this.courses(subject, term);
        int scrh = 0;

        for (CourseInstance i : gm){
            scrh += i.getSTUDENTCOUNT() * i.getCREDITS();
        }
        SubjectCreditHours sch = new SubjectCreditHours(subject, term, scrh);
        return sch;
    }

    /***
     * schbyfaculty calculates the total amount of student credit hours generated by a professor.
     * <p>NOTE: Spring: 10 Summer: 50 Fall: 80</p>
     * @param subject (String) subject enter the subject code EX: csc EX: ENGL
     * @param term (String) enter in a term, in format of YearTerm (yyyytt) EX: 202010
     * @param lastname (String) enter in the last name of professor EX: Gannod EX: Crockett
     * @param firstname (String) enter in the firstname and middle name EX: Gerald C EX: April Renee
     * @return returns a FacultyCreditHours object (has a faculty, subjectCreditHours)
     */
    @GetMapping("/schbyfaculty")
    public FacultyCreditHours creditHoursByFaculty(
            @RequestParam(value = "subject", defaultValue = "CSC") String subject,
            @RequestParam(value = "term", defaultValue = "202210") String term,
            @RequestParam(value = "lastname", defaultValue = "Gannod") String lastname,
            @RequestParam(value = "firstname", defaultValue = "Gerald C") String firstname
    ) {
        CourseInstance[] courses = this.courses(subject, term);
        int scrh = 0;
        for (CourseInstance c : courses){
            Faculty f = c.getFaculty();
            if (f.getLastname() != null && f.getFirstname() != null) {
                if (lastname.toLowerCase().contentEquals(f.getLastname().toLowerCase()) && firstname.toLowerCase().contentEquals(f.getFirstname().toLowerCase()))
                    scrh += c.getSTUDENTCOUNT() * c.getCREDITS();
            }
        }
        FacultyCreditHours sch = new FacultyCreditHours(subject, term, lastname, firstname, scrh);
        return sch;
    }

    /***
     * schbydeptandterms finds the total student credit hours generated by a department within a given range of yearsterms.
     * The code will find the total credit hours generated of spring, summer, and fall terms between the range.
     * <p>NOTE: Spring: 10 Summer: 50 Fall: 80</p>
     * <p>NOTE: beginterm should be older/ less than endterm.</p>
     * @param subject (String) subject enter the subject code EX: csc EX: ENGL
     * @param beginterm (String) begin term of a range of yearterms (yyyytt) EX: 202010
     * @param endterm (String) end term of a range of yearterms (yyyytt) EX: 202210
     * @return array of SubjectCreditHours
     */
    @GetMapping("/schbydeptandterms")
    public SubjectCreditHours[] schbydeptandterms(
            @RequestParam(value = "subject", defaultValue = "na") String subject,
            @RequestParam(value = "beginterm", defaultValue = "na") String beginterm,
            @RequestParam(value = "endterm", defaultValue = "na") String endterm)
    {
        ArrayList<SubjectCreditHours> schArr = new ArrayList<>();
        //  check if beginterm > endterm
        if (Integer.parseInt(beginterm) > Integer.parseInt(endterm)){
            throw new IllegalArgumentException();

        }
        // check for valid term
        String begintemp = beginterm.substring(beginterm.length()-2,beginterm.length());
        if(!begintemp.contentEquals("10") && !begintemp.contentEquals("50") && !begintemp.contentEquals("80")){
            throw new IllegalArgumentException();
        }

        //check if term is valid or not
        String endtemp = endterm.substring(endterm.length()-2,endterm.length());
        if(endtemp.contentEquals("10") || endtemp.contentEquals("50") || endtemp.contentEquals("80")) {
            for (int i = Integer.parseInt(beginterm); i <= Integer.parseInt(endterm); i = i + 30) {
                String term = String.valueOf(i);
                if (term.charAt(term.length() - 2) == '4') {
                    i += 10;
                }

                SubjectCreditHours courses = creditHours(subject, String.valueOf(i));
                schArr.add(courses);
            }
        }

        return schArr.toArray(new SubjectCreditHours[0]);

    }

    /***
     * Schbydeptandtermlist finds the total student credit hours generated by a department in a list of yearsterms.
     * The code will find the total credit hours generated of spring, summer, and fall terms between the range.
     * <p>NOTE: Spring: 10 Summer: 50 Fall: 80</p>
     * <p>NOTE: beginterm should be older/ less than endterm.</p>
     * @param subject (String) subject enter the subject code EX: csc EX: ENGL
     * @param termlist (String) List of yearTerm (yyyytt) separtated by comma EX: "202210,201780,201950"
     * @return Array of Subject Credit Hours
     */
    @GetMapping("schbydeptandtermlist")
    public SubjectCreditHours[] schbydeptandtermlist(
            @RequestParam(value = "subject", defaultValue = "na") String subject,
            @RequestParam(value = "termlist", defaultValue = "na") String termlist
    ) {
        String[] list = termlist.split(",");
        ArrayList<SubjectCreditHours> schArray = new ArrayList<>();

        for( String l : list){
            SubjectCreditHours  schObj = creditHours(subject,l);
            schArray.add(schObj);
        }

        return schArray.toArray(new SubjectCreditHours[0]);
    }

    /***
     * schbyfacultyandterms finds the total student credit hours generated by a faculty in the given range of yearTerm
     * <p>NOTE: Spring: 10 Summer: 50 Fall: 80</p>
     * @param subject (String) subject enter the subject code EX: csc EX: ENGL
     * @param firstname (String) enter in the firstname and middle name EX: Gerald C EX: April Renee
     * @param lastname (String) enter in the last name of professor EX: Gannod EX: Crockett
     * @param beginterm (String) begin term of a range of yearterms (yyyytt) EX: 202010
     * @param endterm (String) end term of a range of yearterms (yyyytt) EX: 202210
     * @return Array of facultycredithours object
     */
    @GetMapping("schbyfacultyandterms")
    public FacultyCreditHours[] schbyfacultyandterms(
            @RequestParam(value = "subject", defaultValue = "na") String subject,
            @RequestParam(value = "firstname", defaultValue = "na") String firstname,
            @RequestParam(value = "lastname", defaultValue = "na") String lastname,
            @RequestParam(value = "beginterm", defaultValue = "na") String beginterm,
            @RequestParam(value = "endterm", defaultValue = "na") String endterm
    ){
        if (Integer.parseInt(beginterm) > Integer.parseInt(endterm)){
            throw new IllegalArgumentException();

        }
        String begintemp = beginterm.substring(beginterm.length()-2,beginterm.length());
        if(!begintemp.contentEquals("10") && !begintemp.contentEquals("50") && !begintemp.contentEquals("80")){
            throw new IllegalArgumentException();
        }

        ArrayList<FacultyCreditHours> fchArr = new ArrayList<>();
        String endtemp = endterm.substring(endterm.length()-2,endterm.length());
        if(endtemp.contentEquals("10") || endtemp.contentEquals("50") || endtemp.contentEquals("80")) {
            for (int i = Integer.parseInt(beginterm); i <= Integer.parseInt(endterm); i = i + 30) {
                String term = String.valueOf(i);
                if (term.charAt(term.length() - 2) == '4') {
                    i += 10;
                }

                FacultyCreditHours fch = creditHoursByFaculty(subject, String.valueOf(i), lastname, firstname);
                fchArr.add(fch);
            }
        }

        return fchArr.toArray(new FacultyCreditHours[0]);
    }

    /***
     * schbyfacutlyandtermlist finds the total student credit hours generated by a faculty in a list of yearTerms
     * <p>NOTE: Spring: 10 Summer: 50 Fall: 80</p>
     * @param subject (String) subject enter the subject code EX: csc EX: ENGL
     * @param firstname (String) enter in the firstname and middle name EX: Gerald C EX: April Renee
     * @param lastname (String) enter in the last name of professor EX: Gannod EX: Crockett
     * @param termlist (String) List of yearTerm (yyyytt) separtated by comma EX: "202210,201780,201950"
     * @return Array of FacultyCreditHours object
     */
    @GetMapping("schbyfacultyandtermlist")
    public FacultyCreditHours[] schbyfacultyandtermlist(
            @RequestParam(value = "subject", defaultValue = "na") String subject,
            @RequestParam(value = "firstname", defaultValue = "na") String firstname,
            @RequestParam(value = "lastname", defaultValue = "na") String lastname,
            @RequestParam(value = "termlist", defaultValue = "na") String termlist
    ){
        String[] term = termlist.split(",");
        ArrayList<FacultyCreditHours> fchArray = new ArrayList<>();

        for (String i : term){
            FacultyCreditHours fchObj = creditHoursByFaculty(subject,i,lastname,firstname);
            fchArray.add(fchObj);
        }

        return fchArray.toArray(new FacultyCreditHours[0]);
    }

    /***
     * coursesbycrnlist gathers all courses in a sinle yearterm and then search for a list of crns
     * <p>NOTE: Spring: 10 Summer: 50 Fall: 80</p>
     * @param term (String) enter in a term, in format of YearTerm (yyyytt) EX: 202010
     * @param crnlist list of course crn numbers EX: "10803,13519,11111"
     * @return array of CourseInstance
     */
    @GetMapping("coursesbycrnlist")
    public CourseInstance[] coursesbycrnlist(
            @RequestParam(value = "term", defaultValue = "na") String term,
            @RequestParam(value = "crnlist", defaultValue = "na") String crnlist
    ){
        String[] crn = crnlist.split(",");
        ArrayList<CourseInstance> crnArray = new ArrayList<>();

        CourseInstance[] courses = courses("",term);
        for(CourseInstance e : courses){
            for(String i : crn){
                if(e.getCRN().contentEquals(i)){
                    crnArray.add(e);
                }
            }
        }

        return crnArray.toArray(new CourseInstance[0]);
    }

    /***
     * facultybysubject creates a Faculty array with every single faculty member that taught in a specific subject given yearTerm
     * <p>NOTE: Spring: 10 Summer: 50 Fall: 80</p>
     * @param term (String) enter in a term, in format of YearTerm (yyyytt) EX: 202010
     * @param subject subject (String) subject enter the subject code EX: csc EX: ENGL
     * @return array of Faculty objects
     */
    @GetMapping("facultybysubject")
    public Faculty[] facultybysubject(
            @RequestParam(value = "term", defaultValue = "na") String term,
            @RequestParam(value = "subject", defaultValue = "na") String subject
    ) {
        TreeSet<Faculty> facultyTreeSet = new TreeSet<>();
        CourseInstance[] courses = courses(subject, term);

        for( CourseInstance e : courses){
            if(facultyTreeSet.size() == 0){ // if treeset is empty or not
                facultyTreeSet.add(e.getFaculty());
            }
            else { // check if the faculty member is already in the treeset, if repeat = 0 then need to be added to the treeset
                int repeat = 0;
                for (Faculty f : facultyTreeSet){
                    if(e.getFaculty().compareTo(f) == 0){
                        repeat = 1;
                    }
                    else if(e.getFaculty().getFirstname() == null || e.getFaculty().getLastname()  == null){
                        repeat =  -1;
                    }
                }
                if(repeat == 0){
                    facultyTreeSet.add(e.getFaculty());
                }
            }
        }
        return facultyTreeSet.toArray(new Faculty[0]);
    }

    /***
     * getallsubjects returns a array of all of the subjects taught in a given yearterm
     * <p>NOTE: Spring: 10 Summer: 50 Fall: 80</p>
     * @param term (String) enter in a term, in format of YearTerm (yyyytt) EX: 202010
     * @return array of string (contain subject codes)
     */
    @GetMapping("getallsubjects")
    public String[] getallsubjects(
            @RequestParam(value = "term", defaultValue = "na") String term
    ){
        TreeSet<String> subjects = new TreeSet<>();

        CourseInstance[] course = courses("", term);

        for (CourseInstance e : course){
            subjects.add(e.getSubjectterm().getSubject());
        }

        return subjects.toArray(new String[0]);
    }
}
