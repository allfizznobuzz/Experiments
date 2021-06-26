START TRANSACTION;

DROP TABLE IF EXISTS category_venue;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS space;
DROP TABLE IF EXISTS venue;
DROP TABLE IF EXISTS city;
DROP TABLE IF EXISTS state;

CREATE TABLE venue (
  id SERIAL PRIMARY KEY,
  name VARCHAR(120) NOT NULL,
  city_id INTEGER NOT NULL,
  description VARCHAR(500) NOT NULL
);

CREATE TABLE space (
  id SERIAL PRIMARY KEY,
  venue_id INTEGER NOT NULL,
  name VARCHAR(80) NOT NULL,
  is_accessible BOOLEAN NOT NULL DEFAULT false,
  open_from INTEGER NULL,
  open_to INTEGER NULL,
  daily_rate MONEY NOT NULL,
  max_occupancy INTEGER NOT NULL
);

CREATE TABLE city (
  id SERIAL PRIMARY KEY,
  name VARCHAR(80) NOT NULL,
  state_abbreviation CHAR(2) NOT NULL
);

CREATE TABLE state (
  abbreviation CHAR(2) PRIMARY KEY,
  name VARCHAR(15) NOT NULL
);

CREATE TABLE category (
  id SERIAL PRIMARY KEY,
  name VARCHAR(80)
);

CREATE TABLE category_venue (
  venue_id INTEGER NOT NULL,
  category_id INTEGER NOT NULL,

  PRIMARY KEY (venue_id, category_id)
);

CREATE TABLE reservation (
  reservation_id SERIAL PRIMARY KEY,
  space_id INTEGER NOT NULL,
  number_of_attendees INTEGER NOT NULL DEFAULT 100,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  reserved_for VARCHAR(100) NOT NULL
);

-- Hidden Owl Eatery
INSERT INTO venue (id, name, city_id, description) VALUES (1, 'Hidden Owl Eatery', 1, 'This venue has plenty of "property" to enjoy. Roll the dice and check out all of our spaces.');

-- Painted Squirrel Club
INSERT INTO venue (id, name, city_id, description) VALUES (2, 'Painted Squirrel Club', 1, 'Lock in your reservation now! This venue has dungeons and an underground theme. Not for the faint of heart!');

-- Rusty Farmer Spot
INSERT INTO venue (id, name, city_id, description) VALUES (3, 'Rusty Farmer Spot', 1, 'This rustic and seasonal spot is fun year-round. Great for equinox celebrations!');

-- Lilac Bottle Speakeasy
INSERT INTO venue (id, name, city_id, description) VALUES (4, 'Lilac Bottle Speakeasy', 2, 'Psst, keep this venue a secret. The perfect place for private meetings between spies. Be quiet and make sure you know the password.');

-- Smirking Stone Bistro
INSERT INTO venue (id, name, city_id, description) VALUES (5, 'Smirking Stone Bistro', 2, 'This fantastical and tropical venue is the perfect escape. Why not take a staycation here and enjoy a lovely tiki theme!');

-- Blue Nomad Outpost
INSERT INTO venue (id, name, city_id, description) VALUES (6, 'Blue Nomad Outpost', 3, 'Blast off into a venue filled with all sorts of NASA and space-themed decorations. Strap yourself in for an intergalactic voyage through this lovely "space."');

-- Howling Pour Lounge
INSERT INTO venue (id, name, city_id, description) VALUES (7, 'Howling Pour Lounge', 3, 'An exclusive and high-class venue which will leave you breathless. This is the definition of luxury. Enjoy the lifestyles of the rich and famous here!');

-- Feisty Barrel Saloon
INSERT INTO venue (id, name, city_id, description) VALUES (8, 'Feisty Barrel Saloon', 3, 'Newly weds abound at this fantastic venue. A perfect Valentine''s day spot with plenty to offer couples of all ages. Be sure to ask about our chocolates.');

-- Proud Lion Hideout
INSERT INTO venue (id, name, city_id, description) VALUES (9, 'Proud Lion Hideout', 3, 'Groove out to this 70''s themed venue which plays all your favorite hits from the past. Grab your tie dye t shirt and enjoy walking through history.');

-- Crystal Traveler Taproom
INSERT INTO venue (id, name, city_id, description) VALUES (10, 'Crystal Traveler Taproom', 4, 'Color themed rooms make this venue an interior decorator''s dream. Lots of playful uses of palettes here.');

-- Singing Table Pub
INSERT INTO venue (id, name, city_id, description) VALUES (11, 'Singing Table Pub', 4, 'This poker themed venue hosts many game nights. The venue fills up fast so don''t be left outside because of a "full house." Make your reservation today!');

-- Curious Anchor Garage
INSERT INTO venue (id, name, city_id, description) VALUES (12, 'Curious Anchor Garage', 4, 'Ahoy maties! Swing on over to this pirate-themed bar which will have you signing sea shanties by the end of the night. Be sure to try out the rum cocktails.');

-- Wise Rooster Brewhouse
INSERT INTO venue (id, name, city_id, description) VALUES (13, 'Wise Rooster Brewhouse', 2, 'King Tut would love spending time at this ancient Egypt themed venue. You might end up with some sand in your shoes, but this place will have you feeling like a pharoah.');

-- Runaway Time Emporium
INSERT INTO venue (id, name, city_id, description) VALUES (14, 'Runaway Time Emporium', 4, 'The perfect office space for those trying to get that next big project done. Plenty of office supplies available and a lightning fast wifi network.');

-- The Bittersweet Elephant Tavern
INSERT INTO venue (id, name, city_id, description) VALUES (15, 'The Bittersweet Elephant Tavern', 4, 'It''s like a zoo in here! This animal themed venue is a hoot. You can really go wild in this jungle.');

-- Hidden Owl Eatery Spaces
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (1, 1, 'Boardwalk', TRUE, NULL, NULL, '5250', 210);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (2, 1, 'Park Place', TRUE, 2, 8, '900', 60);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (3, 1, 'Marvin Gardens', TRUE, NULL, NULL, '1650', 110);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (4, 1, 'The Jailhouse', FALSE, 4, 5, '1800', 90);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (5, 1, 'Saint James Place', TRUE, 1, 3, '4200', 210);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (6, 1, 'Baltic Avenue', TRUE, NULL, NULL, '300', 10);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (7, 1, 'R&R Room', TRUE, 5, 7, '2100', 70);

-- Painted Squirrel Club Spaces
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (8, 2, 'The Dungeon', FALSE, NULL, NULL, '7200', 240);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (9, 2, 'Cruel Convention Hall', FALSE, NULL, NULL, '1400', 70);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (10, 2, 'Torturous Tasting Space', FALSE, NULL, NULL, '400', 40);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (11, 2, 'The Banal Basement', FALSE, NULL, NULL, '900', 30);

-- Rusty Farmer Spot Spaces
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (12, 3, 'Fall Harvest Feast', TRUE, 10, 12, '2600', 130);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (13, 3, 'Summertime Sitting Room', TRUE, 7, 9, '3800', 190);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (14, 3, 'Spring Spectators'' Box', FALSE, 4, 6, '2000', 80);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (15, 3, 'Winter Workout Gym', TRUE, 1, 3, '3600', 240);

-- Lilac Bottle Speakeasy Spaces
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (16, 4, 'Mysterious Meeting Room', TRUE, 3, 11, '900', 90);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (17, 4, 'The Perplexing Palace', TRUE, NULL, NULL, '400', 20);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (18, 4, 'The Secretive Spot', FALSE, NULL, NULL, '2400', 160);

-- Smirking Stone Bistro Spaces
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (19, 5, 'Hidden Sanctuary', TRUE, 6, 12, '1250', 50);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (20, 5, 'Twilight Watch', TRUE, NULL, NULL, '1600', 160);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (21, 5, 'Private Hideout', TRUE, NULL, NULL, '4200', 140);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (22, 5, 'Ocean Front', TRUE, NULL, NULL, '450', 30);

-- Blue Nomad Outpost Spaces
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (23, 6, 'New Horizons', TRUE, 10, 12, '2200', 110);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (24, 6, 'Stargazer Rooftop', TRUE, NULL, NULL, '900', 30);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (25, 6, 'Out of this World Spot', TRUE, NULL, NULL, '250', 10);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (26, 6, 'The Omega', TRUE, 3, 6, '2200', 220);

-- Howling Pour Lounge Spaces
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (27, 7, 'The Royal Room', TRUE, 5, 9, '4600', 230);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (28, 7, 'The Run-Down Room', TRUE, 5, 9, '1000', 100);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (29, 7, 'Luxury Lighthouse', FALSE, 1, 3, '1200', 80);

-- Feisty Barrel Saloon Spaces
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (30, 8, 'Honeymoon Hall', TRUE, NULL, NULL, '2400', 80);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (31, 8, 'The Romantic Room', TRUE, NULL, NULL, '3500', 140);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (32, 8, 'Lovers'' Lounge', TRUE, 6, 7, '2700', 180);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (33, 8, 'Married Meeting Room', TRUE, 8, 10, '5000', 200);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (34, 8, 'Elopers'' Escape Hatch', TRUE, NULL, NULL, '1600', 80);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (35, 8, 'Flirting Fort', TRUE, NULL, NULL, '3000', 150);

-- Proud Lion Hideout Spaces
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (36, 9, 'The Groovy Guildhall', TRUE, NULL, NULL, '6900', 230);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (37, 9, 'The Chill Churchroom', TRUE, 8, 11, '6900', 230);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (38, 9, 'Tie Dye Tables', TRUE, NULL, NULL, '1900', 190);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (39, 9, 'Tubular Reception', TRUE, 1, 2, '1700', 170);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (40, 9, 'Sunshine Setup', FALSE, NULL, NULL, '2700', 90);

-- Crystal Traveler Taproom Spaces
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (41, 10, 'Purple Porthouse', TRUE, 4, 5, '450', 30);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (42, 10, 'The Red Room', TRUE, 8, 12, '2600', 130);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (43, 10, 'Blue Basement', FALSE, NULL, NULL, '5500', 220);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (44, 10, 'Amber Attic', FALSE, NULL, NULL, '200', 20);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (45, 10, 'Orange Office Space', TRUE, 3, 10, '3300', 220);

-- Singing Table Pub Spaces
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (46, 11, 'Kingly Cavern', TRUE, 2, 12, '1700', 170);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (47, 11, 'Queen''s Quarters', TRUE, NULL, NULL, '2400', 80);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (48, 11, 'Jack''s Veranda', FALSE, NULL, NULL, '450', 30);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (49, 11, 'Ace Aviary', TRUE, NULL, NULL, '4400', 220);

-- Curious Anchor Garage Spaces
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (63, 12, 'Pirate Poorhouse', TRUE, NULL, NULL, '1800', 120);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (64, 12, 'Cutlass Corner', TRUE, NULL, NULL, '100', 10);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (65, 12, 'The Open Seas', TRUE, 6, 7, '1500', 150);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (66, 12, 'Avast Ye Scurvy Dining Room', TRUE, 5, 10, '750', 30);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (67, 12, 'The Admirable Admirals Only Room', TRUE, NULL, NULL, '4000', 160);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (68, 12, 'The Poop Deck', TRUE, NULL, NULL, '1000', 100);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (69, 12, 'Crow''s Nest', FALSE, NULL, NULL, '700', 70);

-- Wise Rooster Brewhouse Spaces
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (50, 13, 'The Pharoah''s Tomb', TRUE, 3, 4, '1800', 180);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (51, 13, 'Pyramid Plaza', TRUE, 5, 11, '900', 90);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (52, 13, 'The Sandy Spot', TRUE, NULL, NULL, '4000', 200);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (53, 13, 'Camel Tent', TRUE, NULL, NULL, '5000', 200);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (54, 13, 'The Nile Pool Room', TRUE, NULL, NULL, '1400', 140);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (55, 13, 'The Sphinx''s Riddle Room', TRUE, NULL, NULL, '3200', 160);

-- Runaway Time Emporium Spaces
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (56, 14, 'Prepare', TRUE, 4, 8, '2850', 190);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (57, 14, 'Work it Out', TRUE, 1, 6, '4400', 220);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (58, 14, 'Brainstorm', TRUE, NULL, NULL, '3000', 200);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (59, 14, 'Get Work Done', TRUE, 7, 12, '4800', 160);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (60, 14, 'Deliver Status', TRUE, NULL, NULL, '4400', 220);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (61, 14, 'Waste Time', TRUE, NULL, NULL, '6900', 230);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (62, 14, 'Creative Thinking', TRUE, NULL, NULL, '900', 30);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (70, 15, 'Owl Operations', FALSE, NULL, NULL, '1500', 60);

-- The Bittersweet Elephant Tavern Spaces
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (71, 15, 'Otter Offices', FALSE, NULL, NULL, '3800', 190);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (72, 15, 'The Mousehole', TRUE, 4, 5, '5000', 250);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (73, 15, 'Giraffe Gym', TRUE, 8, 10, '7500', 250);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (74, 15, 'The Doghouse', TRUE, 9, 12, '1200', 40);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (75, 15, 'Kitty Cat Conference Room', TRUE, NULL, NULL, '900', 60);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (76, 15, 'Platypus Plotting Corner', TRUE, NULL, NULL, '1050', 70);
INSERT INTO space (id, venue_id, name, is_accessible, open_from, open_to, daily_rate, max_occupancy) VALUES (77, 15, 'Gazelle Greenhouse', TRUE, NULL, NULL, '1900', 190);

-- City
INSERT INTO city (id, name, state_abbreviation) VALUES (1, 'Bona', 'MI');
INSERT INTO city (id, name, state_abbreviation) VALUES (2, 'Srulbury', 'OH');
INSERT INTO city (id, name, state_abbreviation) VALUES (3, 'Yepford', 'IA');
INSERT INTO city (id, name, state_abbreviation) VALUES (4, 'Andoshire', 'PA');

-- State
INSERT INTO state (abbreviation, name) VALUES ('MI', 'Michigan');
INSERT INTO state (abbreviation, name) VALUES ('OH', 'Ohio');
INSERT INTO state (abbreviation, name) VALUES ('IA', 'Iowa');
INSERT INTO state (abbreviation, name) VALUES ('PA', 'Pennsylvania');

-- Category
INSERT INTO category (id, name) VALUES (1, 'Family Friendly');
INSERT INTO category (id, name) VALUES (2, 'Outdoors');
INSERT INTO category (id, name) VALUES (3, 'Historic');
INSERT INTO category (id, name) VALUES (4, 'Rustic');
INSERT INTO category (id, name) VALUES (5, 'Luxury');
INSERT INTO category (id, name) VALUES (6, 'Modern');

-- Category_Venue
INSERT INTO category_venue (venue_id, category_id) VALUES (1, 1);
INSERT INTO category_venue (venue_id, category_id) VALUES (1, 6);
INSERT INTO category_venue (venue_id, category_id) VALUES (3, 1);
INSERT INTO category_venue (venue_id, category_id) VALUES (3, 2);
INSERT INTO category_venue (venue_id, category_id) VALUES (3, 4);
INSERT INTO category_venue (venue_id, category_id) VALUES (4, 6);
INSERT INTO category_venue (venue_id, category_id) VALUES (5, 2);
INSERT INTO category_venue (venue_id, category_id) VALUES (5, 1);
INSERT INTO category_venue (venue_id, category_id) VALUES (6, 3);
INSERT INTO category_venue (venue_id, category_id) VALUES (6, 1);
INSERT INTO category_venue (venue_id, category_id) VALUES (7, 5);
INSERT INTO category_venue (venue_id, category_id) VALUES (7, 6);
INSERT INTO category_venue (venue_id, category_id) VALUES (8, 5);
INSERT INTO category_venue (venue_id, category_id) VALUES (9, 3);
INSERT INTO category_venue (venue_id, category_id) VALUES (10, 1);
INSERT INTO category_venue (venue_id, category_id) VALUES (12, 3);
INSERT INTO category_venue (venue_id, category_id) VALUES (12, 1);
INSERT INTO category_venue (venue_id, category_id) VALUES (13, 3);
INSERT INTO category_venue (venue_id, category_id) VALUES (13, 1);
INSERT INTO category_venue (venue_id, category_id) VALUES (14, 6);
INSERT INTO category_venue (venue_id, category_id) VALUES (15, 1);
INSERT INTO category_venue (venue_id, category_id) VALUES (15, 2);

-- Reservations 
-- Hidden Owl Eatery Spaces
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (1, 1, 165, NOW() - INTERVAL '2 days', NOW() + INTERVAL '2 days', 'Smith Family Reservation');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (2, 1, 140, NOW() - INTERVAL '6 days', NOW() - INTERVAL '3 days', 'Lockhart Family Reservation');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (3, 2, 55, NOW() - INTERVAL '2 days', NOW() + INTERVAL '2 days', 'Jones Reservation');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (4, 3, 64, NOW(), NOW() + INTERVAL '2 days', 'Bauer Family Reservation');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (5, 4, 45, NOW() + INTERVAL '5 days', NOW() + INTERVAL '10 days', 'Eagles Family Reservation');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (6, 7, 60, NOW() - INTERVAL '3 days', NOW() + INTERVAL '2 days', 'Aersomith eservation');

-- Painted Squirrel Club Spaces
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (7, 9, 67, NOW(), NOW() + INTERVAL '1 days', 'Claus Family Reservation');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (8, 9, 40, NOW() - INTERVAL '7 days', NOW() - INTERVAL '5 days', 'Taylor Family Reservation');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (9, 10, 25, NOW() + INTERVAL '14 days', NOW() + INTERVAL '21 days', 'Astley Family Reservation');

-- Rusty Farmer Spot Spaces
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (10, 13, 110, NOW() + INTERVAL '1 days', NOW() + INTERVAL '4 days', 'Jobs Family Reservation');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (11, 14, 70, NOW() + INTERVAL '1 days', NOW() + INTERVAL '4 days', 'Cook Family Reservation');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (12, 15, 155, NOW() + INTERVAL '1 days', NOW() + INTERVAL '4 days', 'Gates Reservation');

-- Lilac Bottle Speakeasy Spaces
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (13, 16, 46, NOW() + INTERVAL '1 days', NOW() + INTERVAL '4 days', 'Cue Family Reservation');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (14, 17, 11, NOW() + INTERVAL '1 days', NOW() + INTERVAL '4 days', 'Ive Family Reservation');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (15, 18, 96, NOW() + INTERVAL '1 days', NOW() + INTERVAL '4 days', 'Federighi Family Reservation');

-- Smirking Stone Bistro Spaces
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (16, 19, 35, NOW() + INTERVAL '1 days', NOW() + INTERVAL '4 days', 'Schiller Family Reservation');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (17, 20, 145, NOW() + INTERVAL '1 days', NOW() + INTERVAL '4 days', 'Williams Family Reservation');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (18, 20, 130, NOW() + INTERVAL '10 days', NOW() + INTERVAL '21 days', 'Kawasaki Family Reservation');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (19, 20, 123, NOW() + INTERVAL '22 days', NOW() + INTERVAL '28 days', 'Branson Family Reservation');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (20, 20, 108, NOW() + INTERVAL '30 days', NOW() + INTERVAL '33 days', 'Zukerberg Family Reservation');

-- Blue Nomad Outpost Spaces
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (21, 24, 20, NOW() + INTERVAL '4 days', NOW() + INTERVAL '10 days', 'Musk Family Reservation');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (22, 24, 27, NOW() - INTERVAL '4 days', NOW(), 'Buffett Family Reservation');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (23, 25, 5, NOW() + INTERVAL '3 days', NOW() + INTERVAL '10 days', 'Satya Nedella');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (24, 25, 9, NOW() + INTERVAL '10 days', NOW() + INTERVAL '14 days', 'Scott Gutherie');

-- Howling Pour Lounge Spaces
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (25, 28, 70, NOW() + INTERVAL '5 days', NOW() + INTERVAL '11 days', 'Amy Hood');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (26, 29, 57, NOW() + INTERVAL '5 days', NOW() + INTERVAL '11 days', 'Peggy Johnson');

-- Feisty Barrel Saloon Spaces
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (27, 31, 100, NOW() + INTERVAL '9 days', NOW() + INTERVAL '11 days', 'Terry Myerson');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (28, 32, 180, NOW() + INTERVAL '13 days', NOW() + INTERVAL '15 days', 'Steve Ballmer');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (29, 32, 164, NOW() + INTERVAL '16 days', NOW() + INTERVAL '19 days', 'Gates Family Reservation');

-- Proud Lion Hideout Spaces
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (30, 37, 205, NOW() - INTERVAL '15 days', NOW() - INTERVAL '10 days', 'Marisa Mayer');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (31, 37, 222, NOW(), NOW() + INTERVAL '4 days', 'Beth Mooney');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (32, 40, 70, NOW() + INTERVAL '2 days', NOW() + INTERVAL '6 days', 'William Priemer');

-- Crystal Traveler Taproom Spaces
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (33, 42, 125, NOW() + INTERVAL '3 days', NOW() + INTERVAL '8 days', 'Tricia Griffith');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (34, 43, 200, NOW() + INTERVAL '5 days', NOW() + INTERVAL '11 days', 'Toby Cosgrove');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (35, 43, 220, NOW() + INTERVAL '12 days', NOW() + INTERVAL '18 days', 'Akram Boutros');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (36, 44, 15, NOW() + INTERVAL '9 days', NOW() + INTERVAL '11 days', 'Barbara Snyder');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (37, 45, 195, NOW() - INTERVAL '9 days', NOW() + INTERVAL '1 days', 'Bill Board');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (38, 45, 190, NOW() + INTERVAL '1 days', NOW() + INTERVAL '11 days', 'Bill Loney');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (39, 45, 210, NOW() + INTERVAL '17 days', NOW() + INTERVAL '21 days', 'Cara Van');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (40, 45, 210, NOW() + INTERVAL '31 days', NOW() + INTERVAL '37 days', 'Forrest Gump');

-- Singing Table Pub Spaces
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (41, 46, 165, NOW() - INTERVAL '6 days', NOW() + INTERVAL '1 days', 'Simpson Family');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (42, 46, 155, NOW() + INTERVAL '2 days', NOW() + INTERVAL '11 days', 'Smith Family');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (43, 46, 145, NOW() + INTERVAL '14 days', NOW() + INTERVAL '15 days', 'Leela Family');
INSERT INTO reservation (reservation_id, space_id, number_of_attendees, start_date, end_date, reserved_for) VALUES (44, 46, 112, NOW() + INTERVAL '35 days', NOW() + INTERVAL '40 days', 'Scott Family');

ALTER TABLE venue ADD FOREIGN KEY (city_id) REFERENCES city(id);
ALTER TABLE space ADD FOREIGN KEY (venue_id) REFERENCES venue(id);
ALTER TABLE city ADD FOREIGN KEY (state_abbreviation) REFERENCES state(abbreviation);
ALTER TABLE category_venue ADD FOREIGN KEY (venue_id) REFERENCES venue(id);
ALTER TABLE category_venue ADD FOREIGN KEY (category_id) REFERENCES category(id);
ALTER TABLE reservation ADD FOREIGN KEY (space_id) REFERENCES space(id);

ALTER SEQUENCE category_id_seq RESTART 1000;
ALTER SEQUENCE city_id_seq RESTART 1000;
ALTER SEQUENCE reservation_reservation_id_seq RESTART 1000;
ALTER SEQUENCE space_id_seq RESTART 1000;
ALTER SEQUENCE venue_id_seq RESTART 1000;

COMMIT;