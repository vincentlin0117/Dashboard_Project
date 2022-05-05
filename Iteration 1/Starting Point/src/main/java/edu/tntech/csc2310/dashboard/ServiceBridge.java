package edu.tntech.csc2310.dashboard;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ServiceBridge {

    private static final String apiKey = "1F976517-D21F-4B16-87F3-15A1143E9F74";
    private static final String urlString = "https://portapit.tntech.edu/express/api/unprotected/getCourseInfoByAPIKey.php?Subject=%s&Term=%s&Key=%s";

    private CourseInstance[] courses;

    public ServiceBridge(String term, String subject){

        Gson gson = new Gson();

        try {
            String urlFmt = String.format(urlString, subject.toUpperCase(), term, apiKey);
            URL url = new URL(urlFmt);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            JsonReader jr = gson.newJsonReader(in);
            courses = gson.fromJson(jr, CourseInstance[].class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CourseInstance[] getCourses() {
        return courses;
    }
}
