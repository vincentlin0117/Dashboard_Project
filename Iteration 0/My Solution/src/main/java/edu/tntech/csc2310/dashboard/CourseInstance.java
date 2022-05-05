package edu.tntech.csc2310.dashboard;

public class CourseInstance {
    private String CRN;
    private String DEPARTMENT;
    private String COURSE;
    private String SECTION;
    private Integer CREDITS;
    private Boolean ISTIMEDETERMINED;
    private String STARTTIME;
    private String STARTAM_PM;
    private String ENDTIME;
    private String ENDAM_PM;
    private String CLASSDAYS;
    private Boolean ISLOCDETERMINED;
    private String BUILDING;
    private String ROOM;
    private Boolean ISONLINE;
    private String PROF;
    private Integer STUDENTCOUNT;
    private Integer MAXIMUMSTUDENTS;
    private Boolean ISOPEN;
    private String TITLE;

    public String toString(){
        return DEPARTMENT + " " + CRN + "-" + SECTION + "(" + PROF + ")" + " " + CLASSDAYS + " " + STARTTIME+STARTAM_PM + " - " + ENDTIME+ENDAM_PM;
    }

    public String getCRN() {
        return CRN;
    }

    public String getDEPARTMENT() {
        return DEPARTMENT;
    }

    public String getCOURSE() {
        return COURSE;
    }

    public String getSECTION() {
        return SECTION;
    }

    public Integer getCREDITS() {
        return CREDITS;
    }

    public Boolean getISTIMEDETERMINED() {
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

    public Boolean getISLOCDETERMINED() {
        return ISLOCDETERMINED;
    }

    public String getBUILDING() {
        return BUILDING;
    }

    public String getROOM() {
        return ROOM;
    }

    public Boolean getISONLINE() {
        return ISONLINE;
    }

    public String getPROF() {
        return PROF;
    }

    public Integer getSTUDENTCOUNT() {
        return STUDENTCOUNT;
    }

    public Integer getMAXIMUMSTUDENTS() {
        return MAXIMUMSTUDENTS;
    }

    public Boolean getISOPEN() {
        return ISOPEN;
    }

    public String getTITLE() {
        return TITLE;
    }
}
