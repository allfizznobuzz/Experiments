# Module 2 Capstone - Excelsior Venues

Congratulations! Word has gotten out that you know your way around the complexities of the catering and event planning industry, and that you can create amazing software products to help companies make more money in less time. Due to your phenomenal success with the catering system, Excelsior Venues, a company that rents unique space for events, has decided to have your team build a new venue reservation system.

Below are the features and requirements Excelsior Venues is looking for you and your team to complete:

1. **(MVP)** As a user of the system, I need the ability to view a list of all of the unique venues in the system, sorted alphabetically by name.
    * A venue includes an id, name, city, state, description, and category. A venue _may have many_ categories.
2. **(MVP)** As a user of the system, I need the ability to select a venue that my customer would like to host an event at and see a list of all spaces for the venue.
    * A space includes an id, name, open month, closing month, maximum occupancy, wheelchair accessibility, and a daily rate.
    * A space may be blocked out for various months during the year. For instance, if the space is outdoors or is generally closed for repairs and maintenance during specific months, this will be represented with the open month and closing month.
   * If the open month and closing month do not have any dates, there are not any availability restrictions based on the month.
3. **(MVP)** As a user of the system, I need the ability to select a space and search for availability so I can reserve the space.
    * A reservation search only requires the desired space, a start date, and an end date.
    * A space is unavailable if any part of their preferred date range overlaps with an existing reservation.
    * If no spaces are available, indicate to the user that there are no available spaces and ask them if they would like to try a different search. If they answer yes, restart the search dialog.
    * The TOP 5 spaces should be displayed along with the cost for the total reservation.
    * **(Non-MVP)**: If a date range is entered that occurs when the space is closed, then the space should not be listed in the available spaces.
    * **(Non-MVP)**: Provide advanced search functionality that allows users to indicate requirements they have based on accessibility needs, a category, and a daily budget.
4. **(MVP)** As a user of the system, once I find a space that is available based on my needs, I am able to book a reservation for the space.
    * A space requires the name of the person or group reserving the space, a start date, and an end date.
    * A confirmation id is presented to the user once the reservation has been added to the system.
5. **(Non-MVP)** As a user of the system, I want the ability to search for available spaces across all venues based on my needs so I can make a reservation.
    * Up to 5 spaces for each venue should be displayed if they have availability.
    * The same rules apply as the space search.
6. **(Non-MVP)** As a user of the system, I would like the ability to see a list of all upcoming reservations within the next 30 days for a selected venue.

## Sample Screens

### MVP: Main Menu

```
What would you like to do?
    1) List Venues
    Q) Quit
```

### MVP: View Venues

```
Which venue would you like to view?
    1) Hidden Owl Eatery
    2) Painted Squirrel Club
    3) Rusty Farmer Spot
    4) ...
    R) Return to Previous Screen
```
### MVP: Venue Details

```
Runaway Time Emporium
Location: Andoshire, PA
Categories: Modern

The perfect office space for those trying to get that next big project done. Plenty of office supplies available and a lightning fast wifi network.

What would you like to do next?
    1) View Spaces
    2) Search fo Reservation
    R) Return to Previous Screen
```

### MVP: List Venue Spaces

```
Runaway Time Emporium Spaces

     Name                Open   Close   Daily Rate   Max. Occupancy
#1   Prepare             Apr.   Aug.    $2850.00     190
#2   Work it Out         Jan.   Jun.    $4400.00     220
#3   Brainstorm                         $3000.00     200
#4   Get Work Done       Jul.   Dec.    $4800.00     160
#5   Deliver Status                     $4400.00     220
#6   Waste Time                         $6900.00     230
#7   Creative Thinking                  $900.00      30

What would you like to do next?
    1) Reserve a Space
    R) Return to Previous Screen
```

### MVP: Reserve a Space

```
When do you need the space? 9/29/2019
How many days will you need the space? 5
How many people will be in attendance? 100

The following spaces are available based on your needs:

Space #   Name                Daily Rate   Max Occup.   Accessible?   Total Cost
111       The Rotunda         $350         100          Yes           $1,750
333       The Golden Walrus   $900         150          No            $4,500

Which space would you like to reserve (enter 0 to cancel)? 111
Who is this reservation for? Happy Madison

Thanks for submitting your reservation! The details for your event are listed below:

Confirmation #: 98783478
         Venue: Trapp Family Lodge
         Space: The Rotunda
  Reserved For: Happy Madison
     Attendees: 100
  Arrival Date: 9/29/2019
   Depart Date: 10/3/2019
    Total Cost: $1,750
```

### Non-MVP: Advanced Venue Search

The Search for a Space menu option only needs to be added to the main menu if the requirment is completed.  If the Display Reservations requirement has also be completed then both will be on the menu.

```
What would you like to do?
    1) List Venues
    S) Search for a Space
    Q) Quit
S

When do you need the space? 10/15/2019
How many days will you need the space? 2
How many people will be in attendance? 100
Does the space require accessibility accommodations (Y/N)? Y
What is your daily budget for the event? 300

Which of the categories would you like to include?
    1) Family Friendly
    2) Outdoors
    3) Historic
    4) Rustic
    N) None
N

The following venues and spaces are available based on your needs:

Mountain View Grand Resort & Spa

Space #   Name                Daily Rate   Max Occup.   Accessible?   Total Cost
342       Snow Globe Room     $295         100          Yes           $590
783       Shire Glen          $175         200          Yes           $400

Gasparilla Inn & Club

Space #   Name                Daily Rate   Max Occup.   Accessible?   Total Cost
912       Racquet Room        $700         125          Yes           $1,400

Which space would you like to reserve (enter 0 to cancel)? 783
Who is this reservation for? Happy Madison

Thanks for submitting your reservation! The details for your event are listed below:

Confirmation #: 78783492
         Venue: Mountain View Grand Resort & Spa
         Space: Racquet Room
  Reserved For: Happy Madison
     Attendees: 100
  Arrival Date: 10/15/2019
   Depart Date: 10/16/2019
    Total Cost: $400
```

### Non-MVP: Display Reservations

The Display Reservations menu option only needs to be added to the main menu if the requirment is completed.  If the Search for a Reservation requirement has also be completed then both will be on the menu.

```
What would you like to do?
    1) List Venues
    D) Display Reservations
    Q) Quit
D

The following reservations are coming up in the next 30 days:

Venue                              Space          Reserved For    From         To
Trapp Family Lodge                 The Rotunda    Happy Madison   9/29/2019    10/3/2019
Mountain View Grand Resort & Spa   Racquet Room   Happy Madison   10/15/2019   10/16/2019
```

## Table Structure

Your application will have access to a relational database with the following tables. Venues, spaces, and categories will be seeded with data.

### Venue

| Constraint | Name        | Description                            |
| ---------- | ----------- | -------------------------------------- |
| PK         | id          | Primary auto incrementing id.          |
|            | name        | The venue name.                        |
| FK         | city_id     | References the id from the city table. |
|            | description | The description of the venue.          |

### Space

| Constraint | Name          | Description                                                                            |
| ---------- | ------------- | -------------------------------------------------------------------------------------- |
| PK         | id            | Primary auto incrementing id.                                                          |
| FK         | venue_id      | References the id from the venue table.                                                |
|            | name          | The name of the space.                                                                 |
|            | is_accessible | Whether or not this space is handicap accessible.                                      |
|            | open_from     | The month of the year this space is available from. If null, then it is always open.   |
|            | open_to       | The month of the year this space is available until. If null, then it is never closed. |
|            | daily_rate    | The daily cost for renting the space.                                                  |
|            | max_occupancy | The maximum number of people that the space is able to safely support.                 |

### City

| Constraint | Name               | Description                                       |
| ---------- | ------------------ | ------------------------------------------------- |
| PK         | id                 | Primary auto incrementing id.                     |
|            | name               | The name of the city.                             |
| FK         | state_abbreviation | References the abbreviation from the state table. |

### State

| Constraint | Name         | Description                                |
| ---------- | ------------ | ------------------------------------------ |
| PK         | abbreviation | The 2 character abbreviation for the state |
|            | name         | The name of the state.                     |

### Category

| Constraint | Name | Description                   |
| ---------- | ---- | ----------------------------- |
| PK         | id   | Primary auto incrementing id. |
|            | name | The name of the category.     |

### Category_Venue

The associative entity used to specify the categories associated with a venue. A venue may not have any categories, or it could have many categories.

| Constraint | Name        | Description                                  |
| ---------- | ----------- | -------------------------------------------- |
| PK, FK     | venue_id    | References the id from the venues table.     |
| PK, FK     | category_id | References the id from the categories table. |

### Reservation

All of the reservations for spaces.

| Constraint | Name                | Description                                                                            |
| ---------- | ------------------- | -------------------------------------------------------------------------------------- |
| PK         | id                  | Primary auto incrementing id. This can be used for displaying the confirmation number. |
| FK         | space_id            | The id of the space that has been reserved.                                            |
|            | number_of_attendees | The number of people that will be attending the event.                                 |
|            | start_date          | The date the space will first be needed on.                                            |
|            | end_date            | The last date the space will be needed on.                                             |
|            | reserved_for        | The name of the person or organization this reservation is for.                        |
