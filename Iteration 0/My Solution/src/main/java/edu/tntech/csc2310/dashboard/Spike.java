package edu.tntech.csc2310.dashboard;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Locale;

// mvn exec:java -Dexec.mainClass=classname -Dexec.args="args" (CSC 202210 2310)
// mvn exec:java -D exec.mainClass=edu.tntech.csc2310.dashboard.Spike -D exec.args="CSC 202210 2310"
public class Spike {

	public static void main(String[] args) {

		String subject = "";
		String term = "";
		String course = "";

		subject = args[0];
		term = args[1];
		course = args[2];


		if(args.length > 1 && args.length < 4){
			ServiceBridge serviceBridge = new ServiceBridge(subject.toUpperCase(Locale.ROOT), term);
		}



	}

}
