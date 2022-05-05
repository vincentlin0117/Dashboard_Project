package edu.tntech.csc2310.dashboard;

import java.util.Locale;

public class Faculty implements Comparable <Faculty>{
    private String firstname;
    private String lastname;

    public Faculty(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }


    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    @Override
    public int compareTo(Faculty o) {
        int result = 1;

        // if the first name and last name are not null and they match then return 0
        if(this.firstname != null && this.firstname.toLowerCase().contentEquals(o.firstname.toLowerCase())){
            if(this.lastname != null && this.lastname.toLowerCase().contentEquals(o.lastname.toLowerCase())){
                result = 0;
            }
        }
        return result;
    }
}
