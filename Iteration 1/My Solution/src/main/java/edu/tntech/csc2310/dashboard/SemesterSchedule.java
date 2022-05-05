package edu.tntech.csc2310.dashboard;

public class SemesterSchedule {
    private SubjectTerm SubjectTerm;
    private CourseInstance[] schedule;

    public SemesterSchedule( String subject , String term, CourseInstance[] schedule) {
        this.SubjectTerm = new SubjectTerm(subject,term);
        this.schedule = schedule;
    }

    public edu.tntech.csc2310.dashboard.SubjectTerm getSubjectTerm() {
        return SubjectTerm;
    }

    public CourseInstance[] getSchedule() {
        return schedule;
    }

}
