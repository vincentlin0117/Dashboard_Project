package edu.tntech.csc2310.dashboard;

public class FacultyCreditHours extends SubjectCreditHours {
    private Faculty Faculty;

    public FacultyCreditHours(int creditHoursGenerated,SubjectTerm subjectTerm, Faculty professor){
        super(creditHoursGenerated,subjectTerm); // using SubjectCreditHour
        this.Faculty = professor;
    }

    public Faculty getFaculty() {
        return Faculty;
    }


}
