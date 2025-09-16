-- Clear
DROP TABLE IF EXISTS city, district;
DROP TYPE IF EXISTS terr_type, dist_type, weat_type;

-- Query to create enum types needed
CREATE TYPE terr_type AS ENUM ('Coast', 'Mountains', 'Plain', 'Valley');
CREATE TYPE dist_type AS ENUM ('Comercial', 'Industrial', 'Natural', 'Residential', 'Service');
CREATE TYPE weat_type AS ENUM ('Cool', 'Cold', 'Hot', 'Warm');

-- Query to create tables
CREATE TABLE city (
	id INT PRIMARY KEY,
	name VARCHAR(50) UNIQUE,
	postal_code INT,
	population INT CHECK (population > 0),
	t_type terr_type,
	w_type weat_type
);

CREATE TABLE district (
	id INT PRIMARY KEY,
	city VARCHAR(50) REFERENCES city(name),
	name VARCHAR(50),
	d_type dist_type
);