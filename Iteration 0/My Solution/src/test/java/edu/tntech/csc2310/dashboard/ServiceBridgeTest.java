package edu.tntech.csc2310.dashboard;

import com.sun.source.tree.AssertTree;
import org.apache.catalina.valves.JsonErrorReportValve;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ServiceBridgeTest {

    @Test
    public void getCourse() {
        ServiceBridge test = new ServiceBridge("CSC" ,"202210");
        assertEquals("CSC 10455-109(Crockett, April Renee) R 1630pm - 1745pm", test.getCourse()[3].toString());
        assertEquals("CSC 13519-600(Gannod, Barbara ) TR 1500pm - 1615pm", test.getCourse()[0].toString());
        assertTrue(test.getCourse()[0].getISTIMEDETERMINED());
        assertFalse(test.getCourse()[0].getISONLINE());
        assertTrue(test.getCourse()[0].getCREDITS() == 3);

    }

    @Test
    public void invalidSubject(){
        ServiceBridge test = new ServiceBridge("ABC","202210");
        assertTrue(test.getCourse().length == 0);
    }


    @Test
    public void invalidYearOrTerm(){
        ServiceBridge test = new ServiceBridge("CSC","204410");

        assertTrue(test.getCourse().length == 0);

    }

    @Test
    public void LowerCaseSub(){
        ServiceBridge test = new ServiceBridge("csc", "202210");

        assertTrue(test.getCourse().length == 0);
    }

    @Test
    public void CourseSearch(){
        ServiceBridge test = new ServiceBridge("CSC","202210");
        String course = "2310";

        ArrayList<CourseInstance> result = new ArrayList<>();


        for(int i = 0; i < test.getCourse().length; i++){
            if(test.getCourse()[i].getCOURSE().contentEquals(course)){
                result.add(test.getCourse()[i]);
            }
        }
        assertTrue(result.size() == 4);
        assertTrue(result.get(0).toString().contentEquals("CSC 10885-103(Gannod, Gerald C) T 1630pm - 1745pm"));
        assertTrue(result.get(1).toString().contentEquals("CSC 11571-102(Gannod, Gerald C) R 1500pm - 1615pm"));
        assertTrue(result.get(2).toString().contentEquals("CSC 11496-101(Gannod, Gerald C) T 1500pm - 1615pm"));
        assertTrue(result.get(3).toString().contentEquals("CSC 10803-001(Gannod, Gerald C) MWF 1600pm - 1650pm"));
    }

}