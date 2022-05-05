# Webservices to Support Departmental Dashboard
## Project Description
Creating a programs that will take the data from the Tennessee Technological University and then transform the data depending on what users want.

The project is broken down into 6 parts. Each part adds more information and progresses the project further.

>###### Concept Initiate 0
>Create User Stories of what might a user want in this webservice.
>###### Concept Initiate 1
>Using the use cases created from Concept Initiate 0 and create a Use Case Diagram.
>###### Concept Initiate 2
>Using The use cases diagram created from Concept Initiate 1 to create a Class diagram to show relationships between classes.
>###### Iteration 0
>Learn how to use Maven, Spring-Boot, and Junit and create a spike program.
>###### Iteration 1
>Implement methods using the given Class diagram and the provided parameters of each method.
>###### Iteration 2
>Implement more method and create Javadoc for each method.

Link to the full pdf write up: [Full Assignment Writeup](https://drive.google.com/file/d/1aVvcTtf8PBMnn1Y1Ck-Ra3wmjSBUR0GJ/view?usp=sharing)

## Directory Description
##### src/main
Code to make the program is written in this directory. The java files are used to define the classes defined from the class diagram from the write up.
Other files like the pom.xml and mvnw, mvnw.cmd are used to run maven.

##### src/test
Code in this directory test the code written in main to see if the methods produce the expected outputs

## Compilation Instructions
Before compiling the code make sure to open the Maven tab on the right edge of the screen.
> dashboard ► lifecycle ► clean ► compile ► package
>
>then in the terminal run the command:  ./mvnw Spring-Boot:run

## Development Instructions
##### Cloning project
First locate where you want to clone the project to. Then run the following command:
> git clone https://gitlab.csc.tntech.edu/csc2310-sp22-students/vlin42/vlin42-dashboard_2.git

##### Loading Project
Open Intellij and then Click:
>File ► Open ► Locate project ► Ok

Click:
>File ► Project Structure ► SDK ► Version 11 ► Apply

To make sure the test are using the correct JUnit:
> Open ServiceBridge.Java  in src/main ► Right click ► Generate ► Test ► Change Testing Library to JUnit 4  Apply ► Cancel



