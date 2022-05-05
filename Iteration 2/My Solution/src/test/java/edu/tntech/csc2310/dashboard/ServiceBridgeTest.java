package edu.tntech.csc2310.dashboard;

import edu.tntech.csc2310.dashboard.ServiceBridge;
import edu.tntech.csc2310.dashboard.data.*;
import org.junit.Test;

import javax.print.attribute.SupportedValuesAttribute;

import static org.junit.Assert.*;

public class ServiceBridgeTest {

    @Test
    public void schbydeptandterms(){
        // test with spring
        SubjectCreditHours[] testSpring = new ServiceBridge().schbydeptandterms("CSC","202010","202280");
        assertEquals("correct number of sch Objects",9,testSpring.length);

        // start with summer
        SubjectCreditHours[] testSummer = new ServiceBridge().schbydeptandterms("CSC","202050","202210");
        assertEquals("correct number of sch Objects",6,testSummer.length);

        // start will fall
        SubjectCreditHours[] testFall = new ServiceBridge().schbydeptandterms("CSC","202080","202250");
        assertEquals("correct number of sch Objects",6,testFall.length);

        // testing capitalization and wrong beginterm
        try {
            SubjectCreditHours[] test = new ServiceBridge().schbydeptandterms("csc", "202030", "202080");
        }catch (IllegalArgumentException e){
            assertTrue("Error generated", true);
        }

        // wrong order of beginterm and endterm
        try {
            SubjectCreditHours[] test = new ServiceBridge().schbydeptandterms("csc","202210","202010");
        }catch (IllegalArgumentException e){
            assertTrue("Error generated",true);
        }

        // testing capitalization wrong endterm
        SubjectCreditHours[] test1 = new ServiceBridge().schbydeptandterms("csc", "202010", "202040");
        assertEquals("Correct number of sch Object",0,test1.length);

        // testing wrong subject
        SubjectCreditHours[] test2 = new ServiceBridge().schbydeptandterms("DFG", "202010","202280");
        assertEquals("correct number of sch objects", 9,test2.length);
        for (SubjectCreditHours e : test2){
            assertEquals("nothing found for subject",0,e.getCreditHoursGenerated());
        }

    }

    @Test
    public void schbydeptandtermlist(){
        // happy path
        SubjectCreditHours[] happypath = new ServiceBridge().schbydeptandtermlist("CSC","201710,202150,202180");
        assertEquals("Correct number of terms",3,happypath.length);

        // test capitalizationa and wrong index
        SubjectCreditHours[] test = new ServiceBridge().schbydeptandtermlist("csc","201710,202210,205615");
        assertEquals("Correct number of terms",3,test.length);
        assertTrue("Nothing generated",test[2].getCreditHoursGenerated() == 0);
    }

    @Test
    public void schbyfacultyandterms(){
        // happy path spring
        FacultyCreditHours[] happypathSpring = new ServiceBridge().schbyfacultyandterms("CSC","Gerald C","Gannod", "202010","202210");
        assertEquals("Fsch founded", 7,happypathSpring.length);

        // happy path summer
        FacultyCreditHours[] happypathSummer = new ServiceBridge().schbyfacultyandterms("CSC","Gerald C","Gannod", "202050","202250");
        assertEquals("Fsch founded", 7,happypathSummer.length);

        // happy path fall
        FacultyCreditHours[] happypathFall = new ServiceBridge().schbyfacultyandterms("CSC","Gerald C","Gannod", "202080","202280");
        assertEquals("Fsch founded", 7,happypathFall.length);

        // Check capitalization
        FacultyCreditHours[] lowerCase = new ServiceBridge().schbyfacultyandterms("csc","gerald c","gannod","202010","202210");
        assertEquals("Fsch found",7,lowerCase.length);

        // testing capitalization and wrong beginterm
        try {
            FacultyCreditHours[] test = new ServiceBridge().schbyfacultyandterms("csc", "Gerald c","Gannod","202030", "202080");
        }catch (IllegalArgumentException e){
            assertTrue("Error generated", true);
        }

        // wrong order of beginterm and endterm
        try {
            FacultyCreditHours[] test = new ServiceBridge().schbyfacultyandterms("csc","Gerald c","Gannod","202210","202010");
        }catch (IllegalArgumentException e){
            assertTrue("Error generated",true);
        }

        // wrong endterm
        FacultyCreditHours[] wrongendterm = new ServiceBridge().schbyfacultyandterms("csc", "gerald c", "gannod", "202010", "202215");
        assertEquals("No obj in array", 0,wrongendterm.length);

        // wrong subject
        FacultyCreditHours[] wrongsubject = new ServiceBridge().schbyfacultyandterms("dfg", "gerald c ", "gannod", "202010", "202210");
        assertEquals("obj found",7,wrongsubject.length);
        assertEquals("wrong subject",0,wrongsubject[0].getCreditHoursGenerated());

        // invalid faculty name
        FacultyCreditHours[] invalidFac = new ServiceBridge().schbyfacultyandterms("csc", "bill", "gates", "202010", "202210");
        assertEquals("obj found",7,invalidFac.length);
        assertEquals("wrong subject",0,wrongsubject[0].getCreditHoursGenerated());
    }

    @Test
    public void schbyfacultyandtermlist(){
        // happy path
        FacultyCreditHours[] happypath = new ServiceBridge().schbyfacultyandtermlist("CSC","Gerald c", "Gannod", "202210,202010,201780,201950");
        assertEquals("courses found",4,happypath.length);

        // testing capitalization and 1 wrong term
        FacultyCreditHours[] test1 = new ServiceBridge().schbyfacultyandtermlist("csc", "gerald c", "gannod","202210,202050,205649");
        assertEquals("terms found",3,test1.length);
        assertEquals("no sch for term", 0,test1[2].getCreditHoursGenerated());

        // wrong professor
        FacultyCreditHours[] test2 = new ServiceBridge().schbyfacultyandtermlist("csc", "bill", "gates","202210,202050");
        assertEquals("terms found", 2, test2.length);
        assertEquals("wrong prof name", 0, test2[0].getCreditHoursGenerated());

        // wrong subject
        FacultyCreditHours[] test3 = new ServiceBridge().schbyfacultyandtermlist("dfg", "gerald c", "gannod","202210,202050");
        assertEquals("term found",2,test3.length);
        assertEquals("wrong subject",0,test3[0].getCreditHoursGenerated());
    }

    @Test
    public void coursesbycrnlist(){
        // happy path
        CourseInstance[] happypath = new ServiceBridge().coursesbycrnlist("202210","10803,13519");
        assertEquals("crn found", 2,happypath.length);

        // invalid crn number, if invalid it does not appear in array
        CourseInstance[] test = new ServiceBridge().coursesbycrnlist("202110","10803,13519,80167");
        assertEquals("crn found",2,test.length);
        for(CourseInstance e : test){
            assertFalse("not 80167",e.getCRN().contentEquals("80167"));
        }
        // wrong term
        CourseInstance[] test1 = new ServiceBridge().coursesbycrnlist("202215","10803,13519");
        assertEquals("nothing found, wrong term", 0,test1.length);
    }

    @Test
    public void facultybysubject(){
        // happy path
        Faculty[] happypath = new ServiceBridge().facultybysubject("202210","CSC");
        assertNotEquals("professors found",0, happypath.length);

        // test lower case
        Faculty[] cap = new ServiceBridge().facultybysubject("202210","csc");
        assertNotEquals("professor found",0,cap.length);

        // test invalid term
        Faculty[] test = new ServiceBridge().facultybysubject("202015","csc");
        assertEquals(0, test.length);

        // invalid subject
        Faculty[] invalidsubject = new ServiceBridge().facultybysubject("202010","dfg");
        assertEquals(0, invalidsubject.length);
    }

    @Test
    public void getallsubjects(){
        // happy path
        String[] happypath =  new ServiceBridge().getallsubjects("202010");
        assertNotEquals("subjects found", 0, happypath.length);

        // happy path with different term
        String[] happypath1 = new ServiceBridge().getallsubjects("202150");
        assertNotEquals("subjects found", 0, happypath1.length);

        // wrong term
        String[] test = new ServiceBridge().getallsubjects("202015");
        assertEquals("no subjects found", 0, test.length);
    }
}