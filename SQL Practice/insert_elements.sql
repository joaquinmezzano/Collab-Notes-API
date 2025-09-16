-- Clear
DELETE FROM district;
DELETE FROM city;

-- Query to insert a city
INSERT INTO city 
VALUES 
	(1, 'Cordoba', 5000, 1500000, 'Valley', 'Cool'),
	(2, 'Rio Cuarto', 5800, 200000, 'Plain', 'Hot');

-- Query to insert a district in a city
INSERT INTO district VALUES (1, 'Rio Cuarto', 'Pizzarro', 'Residential');