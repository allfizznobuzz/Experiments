package com.techelevator.view;

import com.techelevator.model.Reservation;
import com.techelevator.model.Space;
import com.techelevator.model.Venue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserInterface {

    private Scanner scanner;

    public UserInterface() {
        scanner = new Scanner(System.in);
    }

    public String printMainMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("    1) List Venues");
        System.out.println("    S) Search for a space");
        System.out.println("    D) Display reservations");
        System.out.println("    Q) Quit");
        return scanner.nextLine();
    }

    public void printReservationsNextThirtyDays(List<Reservation> reservations) {

        System.out.println("\nThe following reservations are coming up in the next 30 days:\n");

        System.out.printf("%-30s%-35s%-30s%-15s%-10s\n", "Venue", "Space", "Reserved For", "From", "To");

        for(Reservation reservation : reservations) {

            String arrivalDate = reservation.getArrivalDate().format(DateTimeFormatter.ofPattern("d/M/yyyy"));
            String departureDate = reservation.getDepartureDate().format(DateTimeFormatter.ofPattern("d/M/yyyy"));

            System.out.printf("%-30s%-35s%-30s%-15s%-10s\n", reservation.getVenueName(), reservation.getSpaceName(), reservation.getCustomerName(), arrivalDate, departureDate);
        }
    }

    public Map<String, Venue> printViewAllVenues(List<Venue> venueList) {
        Map<String, Venue> venueMap = new HashMap<>();
        int counter = 1;

        System.out.println("\nWhich venue would you like to view?");

        for (Venue venue : venueList) {
            System.out.println("    " + counter + ") " + venue.getVenueName());
            venueMap.put(Integer.toString(counter), venue);
            counter++;
        }

        System.out.println("    R) Return to previous screen");
        return venueMap;
    }

    public String askUserForVenueChoice() {
        return scanner.nextLine();
    }

    public String printVenueDetails(Venue venue) {
        int counter = 0;

        System.out.println("\n" + venue.getVenueName());
        System.out.println("Location: " + venue.getCity() + ", " + venue.getState());
        System.out.print("Categories: ");

        for (String string : venue.getCategoryList()) {

            if (counter > 0) {

                System.out.print(", ");
            }

            System.out.print(string);
            counter++;
        }

        System.out.println("\n\n" + venue.getDescription() + "\n");
        System.out.println("What would you like to do next?");
        System.out.println("    1) View Spaces");
        System.out.println("    2) Search for reservations");
        System.out.println("    R) Return to previous screen");

        return scanner.nextLine();
    }

    public String printListOfSpaces(Venue venue) {
        int counter = 1;

        System.out.println("\n" + venue.getVenueName() + "\n");

        System.out.printf("%-42s%-10s%-10s%-16s%-8s\n", "    Name", "Open", "Close", "Daily Rate", "Max. Occupancy");

        for (Space space : venue.getSpaceList()) {

            if (space.getOpeningMonth() == null) {

                System.out.printf("#%1d%-40s%-10s%-10s$%-15.2f%-6d\n", counter, "  " + space.getSpaceName(), "", "", space.getDailyRate(), space.getMaxOccupancy());

            } else {

                System.out.printf("#%1d%-40s%-10s%-10s$%-15.2f%-6d\n", counter, "  " + space.getSpaceName(), space.getOpeningMonth() + ".", space.getClosingMonth() + ".", space.getDailyRate(), space.getMaxOccupancy());
            }

            counter++;
        }

        System.out.println("\nWhat would like to do next?");
        System.out.println("    1) Reserve a space");
        System.out.println("    R) Return to previous screen");

        return scanner.nextLine();
    }

    public String printNoSpacesAvailable() {
        System.out.print("\nThere are no available spaces for the given information. Would you like to try a different search? (Y/N) ");
        return scanner.nextLine();
    }

    public Map<String, Space> printTopFiveSpaces(List<Space> listOfSpaces, String lengthOfTrip) {
        Map<String, Space> spaceMap = new HashMap<>();

        System.out.println("\nThe following spaces are available based on your needs:\n");
        System.out.printf("%-10s%-35s%-20s%-15s%-15s%-10s\n", "Space #", "Name", "Daily Rate", "Max. Occup.", "Accessible?", "Total Cost");

        for (Space space : listOfSpaces) {

            System.out.printf("%-10d%-35s%-20.2f%-15d%-15s$%-10.2f\n", space.getSpaceId(), space.getSpaceName(), space.getDailyRate(), space.getMaxOccupancy(), space.isAccessible(), space.getDailyRate().multiply(new BigDecimal((lengthOfTrip))));
            spaceMap.put(Long.toString(space.getSpaceId()), space);
        }

        return spaceMap;
    }

    public Map<String, Space> printAdvancedSearch(Map<Space, String> listOfAvailableSpacesAndVenues, String lengthOfTrip) {
        Map<String, Space> spaceMap = new HashMap<>();
        List<String> listOfVenues = new ArrayList<>();

        Set<Space> keys = listOfAvailableSpacesAndVenues.keySet();

        System.out.println("\nThe following spaces are available based on your needs:");

        for (Space id : keys) {

            String venue = listOfAvailableSpacesAndVenues.get(id);

            if (!listOfVenues.contains(venue)) {
                listOfVenues.add(venue);
                System.out.println("\n" +venue);
                System.out.printf("\n%-10s%-35s%-20s%-15s%-15s%-10s\n", "Space #", "Name", "Daily Rate", "Max. Occup.", "Accessible?", "Total Cost");
            }

            System.out.printf("%-10d%-35s%-20.2f%-15d%-15s$%-10.2f\n", id.getSpaceId(), id.getSpaceName(), id.getDailyRate(), id.getMaxOccupancy(), id.isAccessible(), id.getDailyRate().multiply(new BigDecimal((lengthOfTrip))));
            spaceMap.put(Long.toString(id.getSpaceId()), id);

        }

        return spaceMap;
    }

    public LocalDate askUserForDate() {
        System.out.print("\nWhen do you need the space? (mm/dd/yyyy) ");
        return LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("M/d/yyyy"));
    }

    public String askUserForLengthOfTrip(LocalDate arrivalDate) {
        System.out.print("How many days will you need the space? ");
        return scanner.nextLine();
    }

    public String askUserForNumberOfGuests() {
        System.out.print("How many people will be in attendance? ");
        return scanner.nextLine();
    }

    public String askUserForSpace() {
        System.out.print("\nWhich space would you like to reserve? (enter 0 to cancel) ");
        return scanner.nextLine();
    }

    public String askUserForName() {
        System.out.print("Who is this reservation for? ");
        return scanner.nextLine();
    }

    public String askUserForAccessibility() {
        System.out.print("Does the space require accessibility accommodations? (Y/N) ");
        return scanner.nextLine();
    }

    public BigDecimal askUserForBudget() {
        System.out.print("What is your daily budget for the event? ");
        return new BigDecimal(scanner.nextLine());
    }

    public Map<String, String> askUserForCategory(List<String> listOfCategories) {
        Map<String, String> mapOfCategories = new HashMap<>();
        int counter = 1;

        System.out.println("\nWhich of the categories would you like to include?");

        for (String category : listOfCategories) {
            System.out.println("    " + counter + ") " + category);
            mapOfCategories.put(Integer.toString(counter), category);
            counter++;
        }

        System.out.println("    N) None");
        return mapOfCategories;
    }

    public String returnUserInput() {
        return scanner.nextLine();
    }

    public void printReservationConfirmation(Reservation reservation) {
        String report = "";
        String reservationId = String.format("%15s%s\n", "Confirmation #: ", reservation.getReservationId());
        String venueName = String.format("%16s%s\n", "Venue: ", reservation.getVenueName());
        String spaceName = String.format("%16s%s\n", "Space: ", reservation.getSpaceName());
        String reservedFor = String.format("%16s%s\n", "Reserved For: ", reservation.getCustomerName());
        String attendees = String.format("%16s%s\n", "Attendees: ", reservation.getNumberOfGuests());
        String arrivalDate = String.format("%16s%s\n", "Arrival Date: ", reservation.getArrivalDate());
        String departureDate = String.format("%15s%s\n", "Departure Date: ", reservation.getDepartureDate());
        String totalCost = String.format("%17s%.2f\n", "Total Cost: $", reservation.getTotalCost());
        report = "\nThanks for submitting your reservation! The details for your event are listed below:\n\n" + reservationId + venueName + spaceName + reservedFor + attendees + arrivalDate + departureDate + totalCost;
        System.out.println(report);
    }

    public void printErrorMessage(String message) {
        System.out.println("\n" + message);
    }
}
