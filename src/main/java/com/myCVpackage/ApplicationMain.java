package com.myCVpackage;

import com.myCVpackage.Controller.ControllerFactory;
import com.myCVpackage.Controller.IController;
import com.myCVpackage.data.Repository.GenericRepository;
import com.myCVpackage.data.model.CVUser;
import com.myCVpackage.data.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;

public class ApplicationMain {
    public static void main(String[] args) {
        System.out.println("Running the application...");

        ApplicationMain application = new ApplicationMain();
        application.run();
    }

    private final static Logger LOGGER = LogManager.getLogger();
    static {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
    }
    private IController<User> userController = ControllerFactory.createController(User.class);
    private IController<CVUser> cvUserController = ControllerFactory.createController(CVUser.class);
    private BufferedReader consoleReader;

    ApplicationMain() {
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    void run() {
        LOGGER.info("Process started ...");
        String choice = "";
        int option = 0;
        do {
            showMenu();
            try {
                choice = promptAction();
                option = Integer.parseInt(choice);
                switch (option) {
                    case 0:
                        break;
                    case 1:
                        createCV();
                        break;
                    case 2:
                        cvDetails();
                        break;
                    case 3:
                        createUser();
                        break;
                    default:
                        System.out.printf("Unknown choice:  '%s'. Try again.  %n%n%n",
                                choice);
                }
            } catch (Exception ioe) {
                System.out.println(ioe.getMessage());
            }

        } while (option != 0);
        closeEntityManagerObjects();
    }

    private void cvDetails() throws Exception {
        Scanner scan=new Scanner(System.in);
        System.out.println("Please introduce your criteria!");
        System.out.println("\n Name /Birth Date /City /Company /Position");
        String criteria=scan.next();
        if(Validation.nameValidation(criteria)){
            ArrayList<String> criteriaList=new ArrayList<String>();
            criteriaList.add("Name");
            criteriaList.add("Birth Date");
            criteriaList.add("City");
            criteriaList.add("Company");
            criteriaList.add("Position");
            if(criteriaList.contains(criteria)==true){
                System.out.println("Please introduce "+criteria+" !");
                String searchValue=scan.next();
                ArrayList<CVUser> listaCV=(ArrayList<CVUser>)cvUserController.findAll();
                for(int i=0;i<listaCV.size();i++){
                    CVUser cvUser=listaCV.get(i);
                    String cvtext=cvUser.getCvContent();
                    if(cvtext.contains(searchValue)==true){
                        System.out.println("UserID="+cvUser.getUserId()+" CVTitle="+ cvUser.getCvTitle()
                        +"\n CVContent: \n"+cvUser.getCvContent());
                    }
                }
            }
            else{
                System.out.println("Your criteria doesn't exist!");
            }
        }
        else{
            System.out.println("Please enter again, this is not a valid word!");

        }

    }

    private void createCV() throws Exception {
        String cvTitle = read("Insert the CV title: ");
        String stringUserId = read("Insert the user Id for your CV: ");
        boolean isValid = true;

        if (Validation.isPositiveInt(stringUserId)) {
            int userID=Integer.parseInt(stringUserId);
            User user = userController.findById(userID);
            if(user != null) {

                String fileName = "C:\\Users\\andreea\\Desktop\\CV"+user.getFirstName()+user.getLastName()+".txt";
                File fisier = new File(fileName);
                if(fisier!=null) {
                    Scanner scanFile = new Scanner(fisier);
                    String cvTxt = "";
                    while (scanFile.hasNextLine()) {
                        cvTxt = cvTxt + scanFile.nextLine();
                    }
                    CVUser cv=new CVUser();
                    cv.setUserId(userID);
                    cv.setCvTitle(cvTitle);
                    cv.setCvContent(cvTxt);
                    cvUserController.save(cv);
                }
            }else {
                System.out.println("Your user doesn't match any id!");
            }

        }
        else{
            System.out.println("This is not a valid number!");
            }
    }

    private void createUser() throws Exception {

        User user = new User();
        boolean isValid = true;

        do {
            String userFirstName = read("Enter the your first name: ");
            if (Validation.nameValidation(userFirstName)) {
                isValid = true;
                user.setFirstName(userFirstName);
            } else {
                isValid = false;
                System.out.println("Your first name is not a valid one! "
                        + "\n Please enter string name with minimum of 3 characters and maximum of 20 characters and with uppercase for first letter .");
            }
        } while (isValid == false);

        do {
            String userLastName = read("Enter the your last name: ");
            if (Validation.nameValidation(userLastName)) {
                isValid = true;
                user.setLastName(userLastName);
            } else {
                isValid = false;
                System.out.println("Your last name is not a valid one! "
                        + "\n Please enter string name with minimum of 3 characters and maximum of 20 characters and with uppercase for first letter .");
            }
        } while (isValid == false);

        do {
            String userDateOfBirth = read("Enter your date of birth: ");
            if (Validation.dataValidation(userDateOfBirth)) {
                isValid = true;
                String dateOfBirth = userDateOfBirth;
                Scanner scannDate = new Scanner(dateOfBirth);
                scannDate.useDelimiter("/");
                String dayString = scannDate.next();
                String monthString = scannDate.next();
                String yearString = scannDate.next();
                int day = Integer.parseInt(dayString);
                int month = Integer.parseInt(monthString);
                int year = Integer.parseInt(yearString);

                LocalDate dateOfBirthNew = LocalDate.of(year, month, day);
                //System.out.println(dateOfBirthNew);
                user.setDateOfBirth(dateOfBirthNew);
            } else {
                isValid = false;
                System.out.println("The given date is not valid!");
            }
        } while (isValid == false);

        do {
            String userCnp = read("Please enter your CNP: ");
            if (Validation.cnpValidation(userCnp)){
                isValid = true;
                long nr=Long.parseLong(userCnp);
                System.out.println("cnp"+nr);
                List<User> usersByCNP=userController.findByColumn("cnp",String.valueOf(nr));
                if(usersByCNP.size()==0) {
                    user.setCnp(nr);
                } else {
                    System.out.println("This CNP exist in our data base!");
                    isValid=false;
                }
            } else{
                isValid = false;
                System.out.println("Your CNP is not valid!");
            }

        } while (isValid == false);

        userController.save(user);
    }

    private String read(String message) {
        String result = null;
        try {
            System.out.print(message);
            result = consoleReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void closeEntityManagerObjects() {
        Optional.ofNullable(GenericRepository.entityManager).ifPresent(EntityManager::close);
        Optional.ofNullable(GenericRepository.entityManagerFactory).ifPresent(EntityManagerFactory::close);
    }

    private String promptAction() throws Exception {
        System.out.println("Choose a category: ");
        System.out.println();

        String choice = consoleReader.readLine();
        return choice.trim().toLowerCase();
    }

    private void showMenu() {
        System.out.println("0. quit");
        System.out.println("1. Create a CV");
        System.out.println("2. Search details for an CV in database");
        System.out.println("3. Create a new user");

    }

}
