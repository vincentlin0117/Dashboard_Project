package edu.tntech.csc2310.dashboard;

/**
 * Run using mvn exec:java -Dexec.mainClass=edu.tntech.csc2310.dashboard.Spike -Dexec.args="CSC 202210 2310"
 */
public class Spike {

    public static void main(String[] args){

        String subject = args[0];
        String term = args[1];
        String course = args[2];

        ServiceBridge bridge;
        bridge = new ServiceBridge(term, subject);
        CourseInstance[] courses = bridge.getCourses();

        if (args.length < 4) {

            for (CourseInstance c : courses) {
                if (c.getCOURSE().contentEquals(course))
                    System.out.println(c);
            }
        } else {
            for (CourseInstance c : courses) {
                String lastname = args[3];
                String firstname = args[4];

                if (c.getPROF() != null) {
                    String[] nm = c.getPROF().split(",");

                    if (nm[0].trim().toLowerCase().contentEquals(lastname.toLowerCase()) && nm[1].trim().toLowerCase().contentEquals(firstname.toLowerCase()))
                        System.out.println(c);
                }
            }

        }
    }

}
