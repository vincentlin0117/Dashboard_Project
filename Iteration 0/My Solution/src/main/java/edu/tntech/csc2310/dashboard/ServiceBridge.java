package edu.tntech.csc2310.dashboard;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;


public class ServiceBridge {
    private CourseInstance[] ci;

    /*
    Name: Constructor
    Purpose: Open url and populate a Course instance array
     */
    public ServiceBridge(String subject, String term) {
        Gson gson = new Gson();

        try {
            URL url = new URL("https://portapit.tntech.edu/express/api/unprotected/getCourseInfoByAPIKey.php?Key=1F976517-D21F-4B16-87F3-15A1143E9F74&Subject="+subject+"&Term="+term);

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            JsonReader jr =gson.newJsonReader(in);
            this.ci = gson.fromJson(jr, CourseInstance[].class);



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public CourseInstance[] getCourse(){
        return ci;
    }
}

