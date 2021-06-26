package com.techelevator;

import javax.sql.DataSource;

import com.techelevator.model.Reservation;
import com.techelevator.model.Space;
import com.techelevator.model.Venue;
import com.techelevator.model.jdbc.JDBCReservationDAO;
import com.techelevator.model.jdbc.JDBCSpaceDAO;
import com.techelevator.model.jdbc.JDBCVenueDAO;
import com.techelevator.view.UserInterface;
import org.apache.commons.dbcp2.BasicDataSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class ExcelsiorCLI {

    //Main method establishes connection with the "excelsior_venues" database and runs the application
    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/excelsior_venues");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        ExcelsiorCLI application = new ExcelsiorCLI(dataSource);
        application.run();
    }

    private static final String RETURN_TO_SPACES = "0";
    private static final String LIST_ALL_VENUES = "1";
    private static final String DISPLAY_VENUE_SPACES = "1";
    private static final String RESERVE_A_SPACE = "1";
    private static final String SEARCH_FOR_RESERVATION = "2";
    private static final boolean DISPLAY_VENUE_DETAILS = true;
    private static final boolean ASK_FOR_NAME = true;
    private static final String DISPLAY_RESERVATIONS_NEXT_THIRTY_DAYS = "D";
    private static final String RETURN_TO_PREVIOUS_MENU = "R";
    private static final String TAKE_USER_TO_ADVANCED_SEARCH = "S";
    private static final String EXIT_PROGRAM = "Q";

    private JDBCReservationDAO jdbcReservationDAO;
    private JDBCSpaceDAO jdbcSpaceDAO;
    private JDBCVenueDAO jdbcVenueDAO;
    private UserInterface userInterface;

    //Loads DAOs and the UserInterface class
    public ExcelsiorCLI(DataSource datasource) {
        jdbcReservationDAO = new JDBCReservationDAO(datasource);
        jdbcSpaceDAO = new JDBCSpaceDAO(datasource);
        jdbcVenueDAO = new JDBCVenueDAO(datasource);
        userInterface = new UserInterface();
    }

    public void run() {

        //Loop will run until user choice breaks it
        while (true) {

            //Prints main menu and returns the user's choice
            String choice = userInterface.printMainMenu();

            //If choice is "1," the program will drop into a submenu, which will list all the venues in the database
            if (choice.equals(LIST_ALL_VENUES)) {

                viewVenues();

                //If choice is "S," the program will drop into a submenu that has the advanced venue search
            } else if (choice.equalsIgnoreCase(TAKE_USER_TO_ADVANCED_SEARCH)) {

                advancedVenueSearch();

                //If choice is "D," the userInterface will print all reservations in the next thirty days
            } else if (choice.equalsIgnoreCase(DISPLAY_RESERVATIONS_NEXT_THIRTY_DAYS)) {

                userInterface.printReservationsNextThirtyDays(jdbcReservationDAO.getAllReservationsInTheNextThirtyDays());

                //If choice is "Q," the program will terminate
            } else if (choice.equalsIgnoreCase(EXIT_PROGRAM)) {

                break;

                //If choice is anything besides the previous options, the program will inform the user of the invalid input
                //and will restart the loop, asking again for the user's choice
            } else {

                userInterface.printErrorMessage("Invalid option. Please try again.");
            }
        }
    }

    public void viewVenues() {

        //Loop will run until user choice breaks it
        while (true) {

            //The userInterface is passed a list of venues to print. After printing, it returns the list as a map, for easy indexing with the user
            Map<String, Venue> venueMap = userInterface.printViewAllVenues(jdbcVenueDAO.getListOfVenues());
            //User is asked for the venue index of choice
            String choice = userInterface.askUserForVenueChoice();

            //If the map has a key that matches the user's choice, the program will drop into a submenu, which will display the venue's details
            if (venueMap.containsKey(choice) == DISPLAY_VENUE_DETAILS) {

                //The chosen venue will be passed to each of the following methods of the program for easy identification/tracking
                venueDetails(venueMap.get(choice));

                //If choice is "R," the program will break the while loop, and return to the previous menu (method)
            } else if (choice.equalsIgnoreCase(RETURN_TO_PREVIOUS_MENU)) {

                break;

                //If choice is anything besides the previous options, the program will inform the user of the invalid input
                //and will restart the loop, asking again for the user's choice
            } else {

                userInterface.printErrorMessage("Invalid option. Please try again.\n");
            }
        }
    }

    public void venueDetails(Venue chosenVenue) {

        //Loop will run until user choice breaks it
        while (true) {

            //userInterface prints the venue's details and returns the user's choice
            String choice = userInterface.printVenueDetails(chosenVenue);

            //If choice is "1," the program will drop into a submenu, which will list all the spaces in the selected venue
            if (choice.equals(DISPLAY_VENUE_SPACES)) {

                venueSpaces(chosenVenue);

                //If choice is "2," the program will drop into a submenu, skipping displaying the spaces in the selected venue, and asking the user for their search criteria
            } else if (choice.equalsIgnoreCase(SEARCH_FOR_RESERVATION)) {

                reserveASpace(chosenVenue);

                //If choice is "R," the program will break the while loop, and return to the previous menu (method)
            } else if (choice.equalsIgnoreCase(RETURN_TO_PREVIOUS_MENU)) {

                break;

                //If choice is anything besides the previous options, the program will inform the user of the invalid input
                //and will restart the loop, asking again for the user's choice
            } else {

                userInterface.printErrorMessage("Invalid option. Please try again.");
            }
        }
    }

    public void venueSpaces(Venue chosenVenue) {

        //Loop will run until user choice breaks it
        while (true) {

            //userInterface prints a list of spaces and returns the user's choice
            String choice = userInterface.printListOfSpaces(chosenVenue);

            //If choice is "1," the program will drop into a submenu, asking the user for their search criteria
            if (choice.equals(RESERVE_A_SPACE)) {

                reserveASpace(chosenVenue);

                //If choice is "R," the program will break the while loop, and return to the previous menu (method)
            } else if (choice.equalsIgnoreCase(RETURN_TO_PREVIOUS_MENU)) {

                break;

                //If choice is anything besides the previous options, the program will inform the user of the invalid input
                //and will restart the loop, asking again for the user's choice
            } else {

                userInterface.printErrorMessage("Invalid option. Please try again.");
            }
        }
    }

    public void reserveASpace(Venue chosenVenue) {

        //Loop will run until user choice breaks it
        while (true) {

            //The following methods ask user for their search criteria, then record the user's search criteria into variables
            LocalDate arrivalDate = userDateCriteria();
            String lengthOfTrip = userLengthCriteria(arrivalDate);
            LocalDate departureDate = arrivalDate.plusDays(Integer.parseInt(lengthOfTrip));
            int numberOfGuests = userGuestsCriteria();
            boolean isAccessible = userRequestedAccessibility();
            BigDecimal userBudget = userRequestedBudget();

            //The DAO is passed the user's criteria and returns the top five results, which is printed by the userInterface. After printing, it returns the list as a map, for easy indexing with the user
            Map<String, Space> topFiveSpaces = userInterface.printTopFiveSpaces(jdbcReservationDAO.checkAvailability(chosenVenue.getVenueId(), arrivalDate, departureDate, numberOfGuests, isAccessible, userBudget), lengthOfTrip);

            //If the list is empty the user is asked if they want to make another search
            if (topFiveSpaces.isEmpty()) {

                //Loop will run until user choice breaks it
                while (true) {
                    String choice = userInterface.printNoSpacesAvailable();

                    //If choice is "1," the program will break the while loop in the reserveASpace menu (method) and return the user to a previous menu (method)
                    if (choice.equalsIgnoreCase("Y")) {

                        break;

                        //If choice is "N," the program will terminate
                    } else if (choice.equalsIgnoreCase("N")) {

                        System.exit(0);

                    } else {

                        //If choice is anything besides the previous options, the program will inform the user of the invalid input
                        //and will restart the loop, asking again for the user's choice
                        userInterface.printErrorMessage("Invalid Option. Please try again.");

                    }
                }
                //Second break is used when user choice is "1" in order to break out of the current menu (method)
                break;
            }

            //userInterface prints the top 5 spaces from the selected venue. Returns the user's choice
            String choice = userRequestedSpace(topFiveSpaces);

            //If the map has a key that matches the user's choice, the program will continue to make the reservation
            if (topFiveSpaces.containsKey(choice) == ASK_FOR_NAME) {

                //Stores the chosen space into a variable
                Space chosenSpace = topFiveSpaces.get(choice);

                //Asks user for a name to put onto the reservation
                String customerName = userInterface.askUserForName();

                //Stores reservation into database and returns the reservation id
                Reservation newReservation = jdbcReservationDAO.saveReservation(chosenSpace.getSpaceId(), numberOfGuests, arrivalDate, departureDate, customerName, lengthOfTrip);

                //Prints completed reservation for user
                userInterface.printReservationConfirmation(newReservation);

                //Terminates program
                System.exit(0);

                //If choice was "0," the user is returned to a previous menu (method)
            } else if (choice.equals(RETURN_TO_SPACES)) {

                break;
            }
        }
    }

    public void advancedVenueSearch() {

        //Loop will run until user choice breaks it
        while (true) {

            //The following methods ask user for their search criteria, then record the user's search criteria into variables
            LocalDate arrivalDate = userDateCriteria();
            String lengthOfTrip = userLengthCriteria(arrivalDate);
            LocalDate departureDate = arrivalDate.plusDays(Integer.parseInt(lengthOfTrip));
            int numberOfGuests = userGuestsCriteria();
            boolean isAccessible = userRequestedAccessibility();
            BigDecimal userBudget = userRequestedBudget();
            String userCategoryChoice = userRequestedCategory();

            //Lists all venues and categories that match the user's criteria and returns the spaces into a map, with the space id as the index.
            Map <String, Space> listOfSpaces = userInterface.printAdvancedSearch(jdbcReservationDAO.checkAvailabilityAdvancedSearch(arrivalDate, departureDate, numberOfGuests, isAccessible, userBudget, userCategoryChoice), lengthOfTrip);

            //If the list is empty the user is asked if they want to make another search
            if (listOfSpaces.isEmpty()) {

                //Loop will run until user choice breaks it
                while (true) {
                    String choice = userInterface.printNoSpacesAvailable();

                    //If choice is "1," the program will break the while loop in the reserveASpace menu (method) and return the user to a previous menu (method)
                    if (choice.equalsIgnoreCase("Y")) {

                        break;

                        //If choice is "N," the program will terminate
                    } else if (choice.equalsIgnoreCase("N")) {

                        System.exit(0);

                    } else {

                        //If choice is anything besides the previous options, the program will inform the user of the invalid input
                        //and will restart the loop, asking again for the user's choice
                        userInterface.printErrorMessage("Invalid Option. Please try again.");

                    }
                }
                //Second break is used when user choice is "1" in order to break out of the current menu (method)
                break;
            }

            //userInterface returns the user's choice of space
            String choice = userRequestedSpace(listOfSpaces);

            //If the map has a key that matches the user's choice, the program will continue to make the reservation
            if (listOfSpaces.containsKey(choice) == ASK_FOR_NAME) {

                //Stores the chosen space into a variable
                Space chosenSpace = listOfSpaces.get(choice);

                //Asks user for a name to put onto the reservation
                String customerName = userInterface.askUserForName();

                //Stores reservation into database and returns the reservation id
                Reservation newReservation = jdbcReservationDAO.saveReservation(chosenSpace.getSpaceId(), numberOfGuests, arrivalDate, departureDate, customerName, lengthOfTrip);

                //Prints completed reservation for user
                userInterface.printReservationConfirmation(newReservation);

                //Terminates program
                System.exit(0);

                //If choice was "0," the user is returned to a previous menu (method)
            } else if (choice.equals(RETURN_TO_SPACES)) {

                break;
            }
        }
    }

    public LocalDate userDateCriteria() {

        //Placeholder variables
        LocalDate arrivalDateIsGreaterThanCurrentDate = LocalDate.now();
        LocalDate arrivalDateIsNotMoreThanTwoYearsOut = LocalDate.now().plusYears(2);
        LocalDate arrivalDate;

        //Loop will run until user's input is returned
        while (true) {

            try {

                //userInterface asks user for arrival date. If input is not compatible with LocalDate, the program will jump to the catch
                arrivalDate = userInterface.askUserForDate();

                //Verifies that the date is later than the current date
                if(arrivalDateIsGreaterThanCurrentDate.isBefore(arrivalDate) && arrivalDate.isBefore(arrivalDateIsNotMoreThanTwoYearsOut)) {

                    //Returns the chosen date
                    return arrivalDate;

                }

                //If the selected date was in proper LocalDate format but was not after the current date, the user is informed of invalid option
                userInterface.printErrorMessage("Invalid input. Must be a date after " + arrivalDateIsGreaterThanCurrentDate + " and before " + arrivalDateIsNotMoreThanTwoYearsOut + ".");

            } catch (Exception e) {

                //Informs user that their input was invalid
                userInterface.printErrorMessage("Invalid input. Must be a date after " + arrivalDateIsGreaterThanCurrentDate + " and before " + arrivalDateIsNotMoreThanTwoYearsOut + ".");
            }
        }
    }

    public String userLengthCriteria(LocalDate arrivalDate) {

        //Placeholder variables
        int lengthIsGreaterThanZero = 0;
        int lengthIsLessThanOneHundred = 100;
        String lengthOfTrip = "";

        //Loop will run until user's input is returned
        while (true) {

            try {

                //userInterface asks user for the length of the trip. If input is not compatible with String, the program will jump to the catch
                lengthOfTrip = userInterface.askUserForLengthOfTrip(arrivalDate);

                //Verifies that the length is between 1 and 99
                if (lengthIsGreaterThanZero < Integer.parseInt(lengthOfTrip) && Integer.parseInt(lengthOfTrip) < lengthIsLessThanOneHundred) {

                    //Returns the desired length of the trip
                    return lengthOfTrip;

                }

                //If the selected length was in proper String format but was not between the listed number of days, the user is informed of invalid option
                userInterface.printErrorMessage("Invalid input. Must be a positive number greater than 0 and less than 100.\n");

            } catch (Exception e) {

                //Informs user that their input was invalid
                userInterface.printErrorMessage("Invalid input. Must be a positive number greater than 0 and less than 100.\n");
            }
        }
    }

    public int userGuestsCriteria() {

        //Placeholder variables
        int guestsAreGreaterThanZero = 0;
        int guestsAreLessThanOneThousand = 1000;
        int numberOfGuests = 0;

        //Loop will run until user's input is returned
        while (true) {

            try {

                //userInterface asks user for arrival date. If input is not compatible with int, the program will jump to the catch
                numberOfGuests = Integer.parseInt(userInterface.askUserForNumberOfGuests());

                //Verifies that the number of guests is between 1 and 999
                if (guestsAreGreaterThanZero < numberOfGuests && numberOfGuests < guestsAreLessThanOneThousand) {

                    //Returns the desired number of guests
                    return numberOfGuests;

                }

                //If the selected length was in proper int format but was not between the listed numbers, the user is informed of invalid option
                userInterface.printErrorMessage("Invalid input. Must be a positive number greater than 0 and less than 1000.\n");

            } catch (Exception e) {

                //Informs user that their input was invalid
                userInterface.printErrorMessage("Invalid input. Must be a positive number greater than 0 and less than 1000.\n");
            }
        }
    }

    public String userRequestedSpace(Map<String, Space> selectedSpaces) {

        //Placeholder variable
        String choice = "";

        //Loop will run until user's input is returned
        while (true) {

            try {

                //userInterface asks user for the desired space. If input is not compatible with String, the program will jump to the catch
                choice = userInterface.askUserForSpace();

                //Verifies that the choice is either a key in the map or equals "0"
                if(selectedSpaces.containsKey(choice) || choice.equalsIgnoreCase("0")) {

                    //Returns the desired user choice
                    return choice;

                }

                //If the selected length was in proper String format but was not a valid option, the user is informed of invalid option
                userInterface.printErrorMessage("Invalid input. Please select a valid space # or select 0 to return to the previous menu.");

            } catch (Exception e) {

                //Informs user that their input was invalid
                userInterface.printErrorMessage("Invalid input. Please select a valid space # or select 0 to return to the previous menu.");
            }
        }
    }

    public boolean userRequestedAccessibility() {

        //Placeholder variable
        String choice = "";

        //Loop will run until user's input is returned
        while (true) {

            try {

                //userInterface asks user for accessibility preference. If input is not compatible with String, the program will jump to the catch
                choice = userInterface.askUserForAccessibility();

                //Verifies that the choice is either Y or N
                if(choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("N")) {

                    //Returns the desired user choice
                    return choice.equalsIgnoreCase("Y");

                }

                //If the selected length was in proper String format but was not a valid option, the user is informed of invalid option
                userInterface.printErrorMessage("Invalid input. Please enter a valid option.\n");

            } catch (Exception e) {

                //Informs user that their input was invalid
                userInterface.printErrorMessage("Invalid input. Please enter a valid option.\n");
            }
        }
    }

    public BigDecimal userRequestedBudget() {

        //Placeholder variables
        BigDecimal userBudget = new BigDecimal(0);

        //Loop will run until user's input is returned
        while (true) {

            try {

                //userInterface asks user for budget. If input is not compatible with BigDecimal, the program will jump to the catch
                userBudget = userInterface.askUserForBudget();

                return userBudget;

            } catch (Exception e) {

                //Informs user that their input was invalid
                userInterface.printErrorMessage("Invalid input. Please enter a valid option\n");
            }
        }
    }

    public String userRequestedCategory() {

        //Placeholder variable
        String requestedCategory = "";

        //Loop will run until user's input is returned
        while (true) {

            try {

                //userInterface asks user for the desired space. If input is not compatible with String, the program will jump to the catch
                Map<String, String> mapOfCategories = userInterface.askUserForCategory(jdbcVenueDAO.getAllCategories());
                requestedCategory = userInterface.returnUserInput();

                //Verifies that the choice is either a key in the map
                if(mapOfCategories.containsKey(requestedCategory)) {

                    //Returns the desired user choice
                    return mapOfCategories.get(requestedCategory);

                    //Verifies choice is "N"
                } else if (requestedCategory.equalsIgnoreCase("N")) {

                    //Returns the desired user choice
                    return requestedCategory;

                }

                //If the selected length was in proper String format but was not a valid option, the user is informed of invalid option
                userInterface.printErrorMessage("Invalid input. Please select a valid category.");

            } catch (Exception e) {

                //Informs user that their input was invalid
                userInterface.printErrorMessage("Invalid input. Please select a valid category");
            }
        }
    }
}
