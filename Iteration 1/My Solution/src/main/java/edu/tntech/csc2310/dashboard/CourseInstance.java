package edu.tntech.csc2310.dashboard;

public class CourseInstance {

    private Faculty faculty;
    private SubjectTerm subjectTerm;

    private String DEPARTMENT;
    private String  STUDENTCOUNT;
    private String  CREDITS;
    private String CRN;
    private String COURSE;
    private String SECTION;
    private boolean ISTIMEDETERMINED;
    private String STARTTIME;
    private String STARTAM_PM;
    private String ENDTIME;
    private String ENDAM_PM;
    private String CLASSDAYS;
    private boolean ISLOCDETERMINED;
    private String BUILDING;
    private String ROOM;
    private boolean ISONLINE;
    private String PROF;
    private int MAXIMUMSTUDENTS;
    private boolean ISOPEN;
    private String TITLE;



    public void setSubjectTerm(String term) {

        this.subjectTerm = new SubjectTerm(DEPARTMENT, term);
    }

    public void setFaculty() {

        if(PROF == null){
            this.faculty = new Faculty(null,null);
        }
        else{
            String[] temp = PROF.split(",");
            this.faculty = new Faculty(temp[1].trim(),temp[0].trim());
        }

    }


    public Faculty getFaculty(){return faculty;}

    public SubjectTerm getSubjectTerm() {
        return subjectTerm;
    }

    public String getSTUDENTCOUNT() {
        return STUDENTCOUNT;
    }

    public String getCREDITS() {
        return CREDITS;
    }

    public String getCRN() {
        return CRN;
    }

    public String getCOURSE() {
        return COURSE;
    }

    public String getSECTION() {
        return SECTION;
    }

    public boolean getISTIMEDETERMINED() {
        return ISTIMEDETERMINED;
    }

    public String getSTARTTIME() {
        return STARTTIME;
    }

    public String getSTARTAM_PM() {
        return STARTAM_PM;
    }

    public String getENDTIME() {
        return ENDTIME;
    }

    public String getENDAM_PM() {
        return ENDAM_PM;
    }

    public String getCLASSDAYS() {
        return CLASSDAYS;
    }

    public boolean   getISLOCDETERMINED() {
        return ISLOCDETERMINED;
    }

    public String getBUILDING() {
        return BUILDING;
    }

    public String getROOM() {
        return ROOM;
    }

    public boolean getISONLINE() {
        return ISONLINE;
    }

    public String getPROF() {
        return PROF;
    }

    public int getMAXIMUMSTUDENTS() {
        return MAXIMUMSTUDENTS;
    }

    public boolean getISOPEN() {
        return ISOPEN;
    }

    public String getTITLE() {
        return TITLE;
    }

    public String toString(){
        return DEPARTMENT + " " + COURSE + "-" + SECTION + " (" + PROF + ") " +
                CLASSDAYS + "\t\t" + STARTTIME + STARTAM_PM + " - " + ENDTIME + ENDAM_PM + "\t" + STUDENTCOUNT + "/" + MAXIMUMSTUDENTS;
    }
}
