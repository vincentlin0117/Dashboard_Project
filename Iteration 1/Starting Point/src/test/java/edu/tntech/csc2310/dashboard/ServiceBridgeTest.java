package edu.tntech.csc2310.dashboard;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServiceBridgeTest {

    @Test
    public void coursesExist() {
        ServiceBridge bridge = new ServiceBridge("202210", "CSC");
        assertTrue( "courses exist",bridge.getCourses().length > 0);
        bridge = new ServiceBridge("202210", "csc");
        assertTrue( "courses exist",bridge.getCourses().length > 0);
        bridge = new ServiceBridge("202210", "cSc");
        assertTrue( "courses exist", bridge.getCourses().length > 0);
    }

    @Test
    public void coursesDontExist(){
        ServiceBridge bridge = new ServiceBridge("202210", "Jerry");
        CourseInstance[] ci = bridge.getCourses();
        assertEquals("course does not exist", 0, ci.length);
    }

    @Test
    public void courseFound(){
        ServiceBridge bridge = new ServiceBridge("202180", "UNIV");
        CourseInstance[] ci = bridge.getCourses();
        boolean found = false;
        for (CourseInstance co: ci){
            if (co.getCOURSE().contentEquals("1030"))
                found = true;
        }
        assertTrue("course found", found);
    }

    @Test
    public void termDoesNotExist(){
        ServiceBridge bridge = new ServiceBridge("202380", "UNIV");
        CourseInstance[] ci = bridge.getCourses();
        assertEquals("term does not exist",0, ci.length);
    }

}