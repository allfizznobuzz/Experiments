BEGIN TRANSACTION;

DROP TABLE IF EXISTS workouts_for_day;
DROP TABLE IF EXISTS day;
DROP TABLE IF EXISTS week_day;
DROP TABLE IF EXISTS week_schedule;
DROP TABLE IF EXISTS workout_review;
DROP TABLE IF EXISTS equipment;
DROP TABLE IF EXISTS equipment_required;
DROP TABLE IF EXISTS workout_used;
DROP TABLE IF EXISTS workout;
DROP TABLE IF EXISTS date;
DROP TABLE IF EXISTS workout_session;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS seq_user_id;
DROP SEQUENCE IF EXISTS seq_week_id;
DROP SEQUENCE IF EXISTS seq_day_id;
DROP SEQUENCE IF EXISTS seq_review_id;
DROP SEQUENCE IF EXISTS seq_equipment_id;
DROP SEQUENCE IF EXISTS seq_workout_id;
DROP SEQUENCE IF EXISTS seq_workout_sesion_id;
DROP SEQUENCE IF EXISTS seq_date_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_week_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_day_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_review_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_equipment_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_workout_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_workout_session_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE SEQUENCE seq_date_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
  
CREATE TABLE type (
	type_id int DEFAULT nextval('seq_type_id'::regclass) NOT NULL,
	name varchar(50) NOT NULL,
	CONSTRAINT PK_type_id PRIMARY KEY (type_id)
);

CREATE TABLE beer_type (
	beer_type_id int DEFAULT nextval('seq_beer_type_id'::regclass) NOT NULL,
	name varchar(50) NOT NULL,
	description varchar(400) NOT NULL,
	CONSTRAINT PK_beer_type_id PRIMARY KEY (beer_type_id)
);

CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE brewery (
	brewery_id int DEFAULT nextval('seq_brewery_id'::regclass) NOT NULL,
	name varchar(50) NOT NULL,
	description varchar(400) NOT NULL,
	type_id int NOT NULL,
	website_url varchar(100) NOT NULL,
	phone_number varchar(50) NOT NULL,
	street_address varchar(100) NOT NULL,
	city varchar(50) NOT NULL,
	state varchar(50) NOT NULL,
	zip varchar(50) NOT NULL,
	CONSTRAINT FK_type_id FOREIGN KEY (type_id) REFERENCES type(type_id),
	CONSTRAINT PK_brewery PRIMARY KEY (brewery_id)
);

CREATE TABLE owned_brewery (
	brewery_id int NOT NULL,
	user_id int NOT NULL,
	CONSTRAINT FK_brewery_id FOREIGN KEY (brewery_id) REFERENCES brewery(brewery_id),
	CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE beers (
	beer_id int DEFAULT nextval('seq_beer_id'::regclass) NOT NULL,
	brewery_id int NOT NULL,
	name varchar(50) NOT NULL,
	beer_type_id int NOT NULL,
	description varchar(400) NOT NULL,
	ABV varchar(5),
	CONSTRAINT FK_beer_type_id FOREIGN KEY (beer_type_id) REFERENCES beer_type(beer_type_id),
	CONSTRAINT FK_brewery_id FOREIGN KEY (brewery_id) REFERENCES brewery(brewery_id),
	CONSTRAINT PK_beer_id PRIMARY KEY (beer_id)
);

CREATE TABLE brewery_reviews (
	brewery_id int NOT NULL,
	description varchar(200) NOT NULL,
	stars decimal NOT NULL,
	title varchar(100) NOT NULL,
	username varchar(100) NOT NULL,
	CONSTRAINT FK_brewery_id FOREIGN KEY (brewery_id) REFERENCES brewery(brewery_id)
);

CREATE TABLE beer_reviews (
	beer_id int NOT NULL,
	description varchar(200) NOT NULL,
	overall decimal NOT NULL,
	look decimal NOT NULL,
	smell decimal NOT NULL,
	feel decimal NOT NULL,
	taste decimal NOT NULL,
	title varchar(100) NOT NULL,
	username varchar(100) NOT NULL,
	CONSTRAINT FK_beer_id FOREIGN KEY (beer_id) REFERENCES beers(beer_id)
);

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO type (type_id, name) VALUES (1, 'Micro Brewery');
INSERT INTO type (type_id, name) VALUES (2, 'Brew Pub');
INSERT INTO type (type_id, name) VALUES (3, 'Taproom Brewery');
INSERT INTO type (type_id, name) VALUES (4, 'Regional Brewery');
INSERT INTO type (type_id, name) VALUES (5, 'Contract Brewing Company');
INSERT INTO type (type_id, name) VALUES (6, 'Alternating Proprietor');

INSERT INTO brewery (brewery_id, name, description, type_id, website_url, phone_number, street_address, city, state, zip) VALUES (1, 'John Doe Beer', 'Place for anyone and no one at the same time.', 1, 'johndoebeer.com', '614-555-9991', '1210 Georgesville Rd', 'Columbus', 'OH', '43228');
INSERT INTO brewery (brewery_id, name, description, type_id, website_url, phone_number, street_address, city, state, zip) VALUES (2, 'Six-One-Pour', 'The biggest, small brewery you''ll ever see.', 3, 'sixonepour.com', '614-555-9992', '1661 Hilliard Rome Rd', 'Columbus', 'OH', '43228');
INSERT INTO brewery (brewery_id, name, description, type_id, website_url, phone_number, street_address, city, state, zip) VALUES (3, 'Encapsulate Brewing', 'Only go-getters allowed.', 2, 'encapsulatebrewing.com', '614-555-9993', '1275 Kinnear Rd', 'Columbus', 'OH', '43212');
INSERT INTO brewery (brewery_id, name, description, type_id, website_url, phone_number, street_address, city, state, zip) VALUES (4, 'The Lone Swallowtail', 'Standard of Ohio.', 4, 'loneswallowtail.com', '614-555-9994', '33 East Gay St', 'Columbus', 'OH', '43215');
INSERT INTO brewery (brewery_id, name, description, type_id, website_url, phone_number, street_address, city, state, zip) VALUES (5, 'Deck Escalator', 'Hang out and talk DIY projects around the house with the other dads.', 2, 'deckescalate.com', '614-555-9995', '525 East Tompkins St', 'Columbus', 'OH', '43202');
INSERT INTO brewery (brewery_id, name, description, type_id, website_url, phone_number, street_address, city, state, zip) VALUES (6, 'Ope Brewing', 'Oh oops, can I just get by ya there? "It''s an Ohio thing."', 4, 'opebrewingco.com', '614-555-9996', '577 South State St', 'Columbus', 'OH', '43081');
INSERT INTO brewery (brewery_id, name, description, type_id, website_url, phone_number, street_address, city, state, zip) VALUES (7, 'Brewmeysa Contract Brewing', 'The beer that scatters dust and hides the footprints and tracks.', 5, 'brewmeysabrewing.com', '614-555-9997', '907 Morse Rd', 'Columbus', 'OH', '43229');
INSERT INTO brewery (brewery_id, name, description, type_id, website_url, phone_number, street_address, city, state, zip) VALUES (8, 'Brewtus Custom Brewhouse', 'A collective of local, passionate brewers.', 6, 'brewtusbeers.com', '614-555-9998', '2650 South Hamilton Rd', 'Columbus', 'OH', '43232');
INSERT INTO brewery (brewery_id, name, description, type_id, website_url, phone_number, street_address, city, state, zip) VALUES (9, 'One Direction Fan Club Brewery', 'We''re all about One Direction here!', 1, 'onedirectionbrewing.com', '614-555-9978', '1174 Northwest Blvd', 'Columbus', 'OH', '43221');
INSERT INTO brewery (brewery_id, name, description, type_id, website_url, phone_number, street_address, city, state, zip) VALUES (10, 'South High Brewing', 'Great southern tradition right here up north.', 1, 'southhighbrewco.com', '614-555-9991', '3691 South High St', 'Columbus', 'OH', '43207');
INSERT INTO brewery (brewery_id, name, description, type_id, website_url, phone_number, street_address, city, state, zip) VALUES (11, 'Lucky Stars Brewing', 'Fortune flavors the bold.', 4, 'luckystarsbrew.com', '614-555-9984', '321 Noe-Bixby Rd', 'Columbus', 'OH', '43213');
INSERT INTO brewery (brewery_id, name, description, type_id, website_url, phone_number, street_address, city, state, zip) VALUES (12, 'Out Of Bounds Alehouse', 'No index is too high for us!', 3, 'outofboundsale.com', '614-555-9914', '180 Groveport Rd', 'Canal Winchester', 'OH', '43110');

INSERT INTO owned_brewery (brewery_id, user_id) VALUES (1, 1);
INSERT INTO owned_brewery (brewery_id, user_id) VALUES (2, 2);

INSERT INTO beer_type (beer_type_id, name, description) VALUES (1, 'Pale Ale', 'A usually medium-colored, very dry ale with a strong flavor of hops.');
INSERT INTO beer_type (beer_type_id, name, description) VALUES (2, 'Lager', 'A light-bodied effervescent beer, fermented in a closed vessel using yeasts that sink to the bottom of the brew.');
INSERT INTO beer_type (beer_type_id, name, description) VALUES (3, 'India Pale Ale', 'An extension of the pale ale category with higher gravity ranges and an enhanced hop presence.');
INSERT INTO beer_type (beer_type_id, name, description) VALUES (4, 'Stout', 'Very dark beers that are fermented at warm temperatures and vary in strength.');
INSERT INTO beer_type (beer_type_id, name, description) VALUES (5, 'Pilsner', 'Pilseners offer clean, bready maltiness and plenty of hop character.');
INSERT INTO beer_type (beer_type_id, name, description) VALUES (6, 'Porter', 'A porter is dark in color with flavors of chocolate, light coffee, nut and caramel.');

INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (1, 'Coache''s Beer', 6, 'Classic, aged to perfection', '6.2');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (1, 'The Malt in our Stars', 5, 'Profound and original taste', '4');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (1, 'Who is Jim', 1, 'Smooth and floral', '5.7');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (4, 'Kevin''s Beer', 2, 'Del corazon de San Salvador.', '7');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (4, 'Cask and Ye Shall Receive', 4, 'Serendipitous coupling of taste and smoothness.', '4.5');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (4, 'Back-Draught', 1, 'Quench the dryest thirst', '5.4');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (5, 'Shareeque''s Beer', 3, 'Air Force One of a beer', '4');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (5, 'Barley and Me', 4, 'Will bring you to tears', '5');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (5, 'Hops, in the Name of Love', 6, 'Before you break my heart', '9');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (2, 'The Real MVC', 3, 'Stop dorkin'' around', '4.5');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (2, 'Yeastbound and Down', 2, 'Will put a moustache on your face', '7');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (2, 'Growler in the Night', 5, 'As spooky as it is delicious', '4.5');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (6, 'The Zero Index', 2, 'Coach would approve this beer', '5');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (6, 'Slap on the Grist', 3, 'Letting you off easy, this time', '10');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (6, 'Cowtown Stout', 4, 'From the banks of the Olentangy', '12');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (3, 'Method Signature', 6, 'Access your tastebuds', '6');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (3, 'Unchecked Exception', 5, 'You''ll get it at run-time', '3');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (3, 'API IPA', 1, 'A palindrome to put you to REST', '4.5');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (7, 'Honey Shark', 1, 'The sweetest tooth in the sea', '10');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (7, 'Slap on the Grist', 3, 'Letting you off easy, this time', '10');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (7, 'Cowtown Stout', 4, 'From the banks of the Olentangy', '12');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (8, 'Method Signature', 6, 'Access your tastebuds', '6');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (8, 'Unchecked Exception', 5, 'You''ll get it at run-time', '3');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (8, 'API IPA', 1, 'A palindrome to put you to REST', '4.5');

INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (5, 'Growler in the Night', 5, 'As spooky as it is delicious', '4.5');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (5, 'The Zero Index', 2, 'Coach would approve this beer', '5');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (5, 'Cowtown Stout', 4, 'From the banks of the Olentangy', '12');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (5, 'Method Signature', 6, 'Access your tastebuds', '6');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (5, 'Unchecked Exception', 5, 'You''ll get it at run-time', '3');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (5, 'API IPA', 1, 'A palindrome to put you to REST', '4.5');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (5, 'Honey Shark', 1, 'The sweetest tooth in the sea', '10');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (5, 'Slap on the Grist', 3, 'Letting you off easy, this time', '10');
INSERT INTO beers (brewery_id, name, beer_type_id, description, ABV) VALUES (5, 'API IPA', 1, 'A palindrome to put you to REST', '4.5');


INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (1, 'I love that it felt like no one knew my name', 5, 'Excellent ambience', 'Brewnonymous');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (2, 'Parking is tough downtown, but it is worth it for this brewery', 4, 'Great staff, will be coming back!', 'Donna1959');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (3, 'Seemed limited, Like they were keeping something from me', 2, 'Meh beer', 'NoseyMan');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (4, 'It can be nice when people step aside and let you pass, but at some point it gets weird when everyone does it', 3, 'Alright beer', 'CaliforniaK1d');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (5, 'I went for a beer and left with friends who helped me make a pool table', 4, 'Wow, what a spot!', 'Gr8fu11');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (6, 'Under the radar, where it belongs', 1, 'Dork beer', 'Anonymous');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (2, 'The heart of midwest brewing', 5, 'Phenomenal environment!', 'CoachStevePassword123');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (3, 'They know what they do and they do it well', 5, 'Wonderful beer', 'HipsterShmipster');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (4, 'Good time with family, pleasant staff, perfect service', 5, 'Outstanding experience', 'Karen');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (8, 'I came to have a good time, I left with three different blueprints for a doghouse', 1, 'Lumber nerds', 'yousername');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (7, 'Milquetoast but charming', 4, 'Well-mannered and unsurprising', 'Nunya Bidness');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (1, 'It seemed like they got this beer from someone who made it in their basement', 1, 'Bootleg beer', 'Sharkbait');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (1, 'So nice not to be cooped up anymore', 5, 'Wonderful scenery', 'Backintothefryingpan');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (2, 'Good staff but I dont like beer', 4, 'Fun place, cant wait to return!', 'PaulAndCo');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (3, 'My family liked it but I wanted more from the selection', 2, 'Less than I had hoped for', 'DancesWithBeers');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (4, 'Not bad, nothing to write home about.', 3, 'Good time, not great', 'CorinneFromCanada');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (5, 'I just like trying new things, this was new', 4, 'Pretty okay!', 'TrialRun');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (6, 'Perfect Friday', 5, 'Excellent place on a night out with friends', 'Goose');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (2, 'Has an air of pretension, but overall live up to the billing', 4, 'well acquitted', 'Writing4U');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (3, 'Even if the staff was rude, and they werent, I loved my time there', 5, 'Everything was delicious', 'Jreviews');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (4, 'Best place to come in Columbus', 5, 'Ive been all over town and this is, hands down, where I want to be', 'Bishla');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (8, 'I didnt expect the best part of the brewery to be its patrons', 4, 'Made some new friends', 'Beerficionado');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (7, 'Underwhelming', 2, 'Wanted more top shelf options', 'FrankTheBank');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (1, 'Seemed like they werent trying hard enough', 1, 'Very much average', 'MikeyMike');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (9, 'Best place to come in Columbus', 5, 'Ive been all over town and this is, hands down, where I want to be', 'Bishla');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (10, 'I didnt expect the best part of the brewery to be its patrons', 4, 'Made some new friends', 'Beerficionado');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (11, 'Underwhelming', 2, 'Wanted more top shelf options', 'FrankTheBank');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (12, 'Seemed like they werent trying hard enough', 1, 'Very much average', 'MikeyMike');

INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (5, 'Best place to come in Columbus', 5, 'Ive been all over town and this is, hands down, where I want to be', 'Bishla');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (5, 'I didnt expect the best part of the brewery to be its patrons', 4, 'Made some new friends', 'Beerficionado');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (5, 'Underwhelming', 2, 'Wanted more top shelf options', 'FrankTheBank');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (5, 'Seemed like they werent trying hard enough', 1, 'Very much average', 'MikeyMike');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (5, 'I love that it felt like no one knew my name', 5, 'Excellent ambience', 'Brewnonymous');
INSERT INTO brewery_reviews (brewery_id, description, stars, title, username) VALUES (5, 'Parking is tough downtown, but it is worth it for this brewery', 4, 'Great staff, will be coming back!', 'Donna1959');


INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (1, 'Unbelievably smooth', 5, 5, 5, 5, 5, 'Great taste!', 'BlownAwayUser');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (1, 'The kind of beer with which you could lose yourself', 4, 3, 2, 5, 4, 'A new challenge every time', 'Enbrewsiast');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (2, 'Too much going on', 2, 2, 2, 2, 2, 'Yucky', 'Vinny');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (2, 'This beer opened my palate, I hope to try more peculiar flavors from here on', 5, 4, 4, 5, 5, 'Nice beer', 'SomeGuyNamedJeff');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (3, 'Im sure some people will like the complexity, but I just dont get it', 1, 1, 1, 1, 1, 'Busy beer', 'OnesAndZeros');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (3, 'A beer for sipping, the floral notes are as subtle as they are plentiful', 3, 2, 3, 1, 2, 'Deeply complex beer', 'Snobbybeerlife');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (4, 'Crisp and clean finish for a hearty beer', 5, 5, 4, 5, 5, 'Wonderful', 'BennyThePoo');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (4, 'There is something I cant put my finger on but I love this beer', 4, 3, 2, 3, 1, 'Worst beer', 'Satistfried');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (5, 'Average at best', 2, 2, 2, 2, 2, 'Meh beer', 'TwentyTwentywon');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (5, 'The flavor is fine but the finish lingers far too long', 2, 3, 2, 1, 2, 'Sticky beer', 'EnBrewthiast');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (6, 'I called my daughter and told her to avoid this beer', 1, 1, 1, 1, 1, 'Too strong', 'Grandma1951');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (6, 'A bit extreme but I enjoy having one on a night out', 5, 4, 5, 4, 5, 'Not for everyone', 'EZ2PLZ');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (7, 'Im not sure what this beer is going for but I like it a lot', 4, 3, 4, 3, 2, 'Blast beer', 'Anonymous');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (7, 'I felt like this beer would put hair on my chest', 4, 4, 4, 3, 5, 'Acquired taste', 'JavaBrew');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (8, 'Really sweet up front with a harsh finish', 5, 4, 4, 4, 5, 'Strong and resonant', 'Tomador');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (8, 'Dont want to talk about it', 1, 1, 1, 1, 1, 'I suggest you sip and move on', 'Brewmaysa');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (9, 'Sharp as a knife, a hipsters dream', 5, 4, 4, 5, 5, 'You probably havent heard of it', 'QueenRana');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (9, 'Its best to nurse this beer, its delicious but stronger than you think', 2, 3, 2, 2, 1, 'So pungent', 'Shephurd1995');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (10, 'Balanced from front to back', 4, 4, 3, 3, 4, 'Everything under control', 'HopPop1955');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (10, 'I could have a dozen of these if I were younger', 5, 5, 5, 5, 5, 'Wonderful beer', 'AbrewhamLincoln');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (11, 'The finish is so smooth you can forget its a beer', 4, 3, 4, 2, 3, 'Blast beer', 'ActuallyCher');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (11, 'Too soft, It was like drinking seltzer and the flavors suffered', 1, 2, 2, 1, 1, 'Bomb beer', 'Mar-keemark');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (12, 'I drank this beer on Halloween and nothing spooky happened, but it was delicious', 4, 5, 4, 4, 4, 'Well-done brewers', 'Anthotron');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (12, 'Dont buy it as a growler, it will go flat', 1, 2, 1, 1, 1, 'lame', 'Sharkbait');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (13, 'Unbelievably smooth', 5, 5, 4, 5, 5, 'So tasty its immediately rewarding', 'Ninetynine');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (13, 'An excellent jumping off point', 5, 4, 5, 5, 5, 'Tasty, and so light its like youve had nothing to drink', 'Redbaloons');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (14, 'Sharp up front, then smooth. I could drink a few of these', 5, 5, 5, 5, 5, 'Unique in a great way', 'Are-y-Oh');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (14, 'Bites the tongue, I dont like it', 2, 3, 2, 1, 1, 'Too Bitter', 'SpeedW4gon');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (15, 'Heavy beer, great for sharing', 3, 2, 3, 3, 3, 'Pair it with a salad or something', 'Reddy4beddy');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (15, 'I want to feel light and this beer made me feel like I should go to bed', 1, 1, 2, 1, 1, 'Too heavy', 'HereIAm');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (16, 'I felt like every so often I would taste some static and I wasnt sure why', 2, 2, 3, 2, 1, 'Okay but confusing', 'KoachSteev');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (16, 'Would have been better if the return type wasnt the spins', 2, 1, 1, 2, 2, 'Dont drink ten of these', 'Laura');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (17, 'I wasnt really familiar, i had only had test driven brews. This was terrible', 0, 1, 0, 0, 0, 'Bootleg beer', 'ElRey');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (18, 'This beer was so good it put me to REST', 5, 5, 5, 5, 5, 'Bootleg beer', 'Kurious');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (18, 'Ive been waiting since the start of UNIX time for a beer like this', 5, 5, 5, 5, 5, 'Must be their Magnum Opus', 'BeerGeek');

INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (25, 'Dont want to talk about it', 1, 1, 1, 1, 1, 'I suggest you sip and move on', 'Brewmaysa');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (25, 'Sharp as a knife, a hipsters dream', 5, 4, 4, 5, 5, 'You probably havent heard of it', 'QueenRana');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (26, 'Its best to nurse this beer, its delicious but stronger than you think', 2, 3, 2, 2, 1, 'So pungent', 'Shephurd1995');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (26, 'Balanced from front to back', 4, 4, 3, 3, 4, 'Everything under control', 'HopPop1955');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (27, 'I could have a dozen of these if I were younger', 5, 5, 5, 5, 5, 'Wonderful beer', 'AbrewhamLincoln');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (27, 'The finish is so smooth you can forget its a beer', 4, 3, 4, 2, 3, 'Blast beer', 'ActuallyCher');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (27, 'Too soft, It was like drinking seltzer and the flavors suffered', 1, 2, 2, 1, 1, 'Bomb beer', 'Mar-keemark');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (29, 'I drank this beer on Halloween and nothing spooky happened, but it was delicious', 4, 5, 4, 4, 4, 'Well-done brewers', 'Anthotron');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (28, 'Dont buy it as a growler, it will go flat', 1, 2, 1, 1, 1, 'lame', 'Sharkbait');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (30, 'Unbelievably smooth', 5, 5, 4, 5, 5, 'So tasty its immediately rewarding', 'Ninetynine');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (30, 'An excellent jumping off point', 5, 4, 5, 5, 5, 'Tasty, and so light its like youve had nothing to drink', 'Redbaloons');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (31, 'Sharp up front, then smooth. I could drink a few of these', 5, 5, 5, 5, 5, 'Unique in a great way', 'Are-y-Oh');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (32, 'Bites the tongue, I dont like it', 2, 3, 2, 1, 1, 'Too Bitter', 'SpeedW4gon');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (33, 'Heavy beer, great for sharing', 3, 2, 3, 3, 3, 'Pair it with a salad or something', 'Reddy4beddy');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (33, 'I want to feel light and this beer made me feel like I should go to bed', 1, 1, 2, 1, 1, 'Too heavy', 'HereIAm');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (32, 'I felt like every so often I would taste some static and I wasnt sure why', 2, 2, 3, 2, 1, 'Okay but confusing', 'KoachSteev');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (31, 'Would have been better if the return type wasnt the spins', 2, 1, 1, 2, 2, 'Dont drink ten of these', 'Laura');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (31, 'I wasnt really familiar, i had only had test driven brews. This was terrible', 0, 1, 0, 0, 0, 'Bootleg beer', 'ElRey');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (31, 'This beer was so good it put me to REST', 5, 5, 5, 5, 5, 'Great beer', 'Kurious');
INSERT INTO beer_reviews (beer_id, description, overall, look, smell, feel, taste, title, username) VALUES (33, 'Ive been waiting since the start of UNIX time for a beer like this', 5, 5, 5, 5, 5, 'Must be their Magnum Opus', 'BeerGeek');


COMMIT TRANSACTION;