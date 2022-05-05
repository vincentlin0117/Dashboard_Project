package edu.tntech.csc2310.dashboard;

import org.junit.Before;
import org.junit.Test;

import java.util.zip.CheckedOutputStream;

import static org.junit.Assert.*;

public class ServiceBridgeTest {

    @Test
    public void allcourse(){
        // happy path
        SemesterSchedule bridge = new ServiceBridge().allcourses("202110");
        assertTrue("courses found",bridge.getSchedule().length != 0);
        assertTrue("correct term",bridge.getSubjectTerm().getTerm().contentEquals("202110"));

        // Wrong term
        SemesterSchedule bridge1 = new ServiceBridge().allcourses("202580");
        assertTrue("Wrong Term, courses not found", bridge1.getSchedule().length == 0);
    }
    @Test
    public void coursesbysubject(){
        // happy path
        SemesterSchedule bridge = new ServiceBridge().coursesbysubject("202210","CSC");
        assertTrue("courses found", bridge.getSchedule().length != 0);

        // check capitalization
        SemesterSchedule caseBridge = new ServiceBridge().coursesbysubject("202210", "csc");
        assertTrue("courses found",caseBridge.getSchedule().length != 0);

        // wrong term
        SemesterSchedule wrongTerm = new ServiceBridge().coursesbysubject("202210","dfg");
        assertEquals("No course found",0, wrongTerm.getSchedule().length);

        // wrong subject
        SemesterSchedule wrongSubject = new ServiceBridge().coursesbysubject("232179","che");
        assertEquals("No courses found",0,wrongSubject.getSchedule().length);
    }

    @Test
    public void coursebysection(){
        // happy path
        CourseInstance bridge = new ServiceBridge().coursebysection("202180","CSC","2310","001");
        assertTrue("course found", bridge.getCRN().contentEquals("81776"));

        // Check capitalization
        CourseInstance bridge1 = new ServiceBridge().coursebysection("202180","csc","2310","001");
        assertTrue("course found",bridge1.getCRN().contentEquals("81776"));
        assertTrue("correct capitalization",bridge1.getSubjectTerm().getSubject().contentEquals("CSC"));

        // Wrong Term
        CourseInstance wrongTerm = new ServiceBridge().coursebysection("111479","csc","2310","001");
        assertTrue("course not found",wrongTerm == null);

        // Wrong Subject
        CourseInstance wrongSubject = new ServiceBridge().coursebysection("202210", "EFG","2310", "001");
        assertTrue("Wrong subject",wrongSubject == null);

        // Wrong Course
        CourseInstance wrongCourse = new ServiceBridge().coursebysection("202210","cSc","1234","001");
        assertTrue("Wrong course",wrongCourse == null);

        //Wrong section
        CourseInstance wrongSection = new ServiceBridge().coursebysection("202210","csc","2310","450");
        assertTrue("Section Not Found",wrongSection == null);

    }

    @Test
    public void coursebyfaculty(){
        // happy path
        CourseInstance[] bridge0 = new ServiceBridge().coursesbyfaculty("202210","CSC","Gerald C","Gannod");
        assertTrue(bridge0.length !=0);
        assertTrue(bridge0[0].getCRN().contentEquals("10885"));

        // Professor not found
        CourseInstance[] noProfessor = new ServiceBridge().coursesbyfaculty("202210","CSC","Bill","Gates");
        assertEquals("No Course match professor", 0, noProfessor.length);

        // Capitalize name
        CourseInstance[] bridge1 = new ServiceBridge().coursesbyfaculty("202210", "csc","GERald c", "GaNNod");
        assertTrue("Course found",bridge1.length != 0);

        // space in name
        CourseInstance[] bridge2 = new ServiceBridge().coursesbyfaculty("202210", "csc"," Gerald c ", " Gannod ");
        assertTrue("Course found",bridge1.length != 0);

        // wrong term
        CourseInstance[] wrongTerm = new ServiceBridge().coursesbyfaculty("231079","csc","Gerald C", "Gannod");
        assertEquals("Wrong term, no courses found", 0, wrongTerm.length);

        // wrong subject
        CourseInstance[] wrongSubject = new ServiceBridge().coursesbyfaculty("202210", "qqq", "Gerald C", "Gannod");
        assertEquals("Wrong subject, courses not found",0, wrongSubject.length);
    }

    @Test
    public void schbydepartment(){
        // happy path
        SubjectCreditHours bridge = new ServiceBridge().schbydepartment("202210","CSC");
        assertTrue(bridge.getCreditHoursGenerated() != 0);
        assertTrue("right term",bridge.getSubjectTerm().getTerm().contentEquals("202210"));

        // Check capitalization and floats in credit hours
        SubjectCreditHours bridge2 = new ServiceBridge().schbydepartment("202110","che");
        assertTrue("Credit Hour Generated",bridge.getCreditHoursGenerated() != 0);
        assertTrue("right subject/department", bridge2.getSubjectTerm().getSubject().contentEquals("CHE"));

        // term not found
        SubjectCreditHours wrongTerm = new ServiceBridge().schbydepartment("111163","cSc");
        assertEquals("No Hours Generated/ Wrong Term",0,wrongTerm.getCreditHoursGenerated());

        // subject not found
        SubjectCreditHours wrongSubject = new ServiceBridge().schbydepartment("202210","REE");
        assertEquals("No Hours Generated/ Wrong Subject",0,wrongSubject.getCreditHoursGenerated());
    }

    @Test
    public void schbyfaculty(){
        // happy path
        FacultyCreditHours bridge = new ServiceBridge().schbyfaculty("202210","csc","Gerald C","Gannod");
        assertTrue("professor found",bridge.getCreditHoursGenerated() != 0);
        assertTrue(bridge.getSubjectTerm().getTerm().contentEquals("202210"));

        // check capitalization
        FacultyCreditHours bridge1 = new ServiceBridge().schbyfaculty("202210", "cSC", "geralD C", "gAnnOd");
        assertTrue("Professor found",bridge.getCreditHoursGenerated() != 0);

        // wrong term
        FacultyCreditHours wrongTerm = new ServiceBridge().schbyfaculty("231079", "CSC", "Gerald C", "Gannod");
        assertEquals("Term doesnt Exist", 0, wrongTerm.getCreditHoursGenerated());

        // wrong subject
        FacultyCreditHours wrongSubject = new ServiceBridge().schbyfaculty("202210", "Dfg", "Gerald C", "Gannod");
        assertEquals("subject does not exist",0,wrongSubject.getCreditHoursGenerated());

        // professor not found
        FacultyCreditHours professorNotFound = new ServiceBridge().schbyfaculty("202210","CSC","Bill", "Gates");
        assertEquals("Professor does not exist", 0,professorNotFound.getCreditHoursGenerated());
    }
}