-- Different general and basic queries
SELECT * FROM district;
SELECT * FROM city WHERE population > 300000;
SELECT
	name,
	population,
	CASE
		WHEN population < 100000 THEN 'Not even a city'
		WHEN population BETWEEN 100000 AND 500000 THEN 'Normal city'
		ELSE 'The big city'
	END AS city_category
FROM city;

-- Some Join query
SELECT city.name AS city_name,
	district.name AS district_name,
	city.postal_code, 
	city.population AS city_population, 
	city.t_type AS terrain_type,
	city.w_type AS weather_type, 
	district.d_type AS district_type
	FROM city INNER JOIN district ON city.name = district.city;