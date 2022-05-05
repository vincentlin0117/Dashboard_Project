package edu.tntech.csc2310.dashboard;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Run using mvn exec:java -Dexec.mainClass=edu.tntech.csc2310.dashboard.Spike -Dexec.args="CSC 202210 2310"
 */

@SpringBootApplication
public class Spike {

    public static void main(String[] args){
        SpringApplication.run(Spike.class,args);
    }

}
