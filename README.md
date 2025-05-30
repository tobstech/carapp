# Car Filtering Command-Line Application

This is a Java command-line application that parses car data from an XML and a CSV file, applies filtering and sorting logic, and displays the result in table, JSON, and XML formats.

---

## Features

- Parses car data from:
  - XML (`carsType.xml`) — contains car type, model, and multi-currency prices.
  - CSV (`CarsBrand.csv`) — contains brand and release date.

- Filters cars by:
  - Brand (e.g., Toyota, Honda)
  - Price in USD (e.g., ≤ $50,000)

- Supports sorting:
  - By release date (latest to oldest)
  - By price (highest to lowest)

- Outputs data in:
  - Console Table format
  - JSON format
  - XML format

---

## How It Works

1. **Parse XML**:
   - Loads car types and multi-currency prices.
2. **Parse CSV**:
   - Loads brand and release date.
3. **Map model to brand**:
   - Dynamically or manually associate models with brands.
4. **Filter**:
   - By brand name (case-insensitive)
   - By max price in USD
5. **Sort**:
   - By release date (`latest → oldest`) or
   - By price (`highest → lowest`)
6. **Display Output**:
   - In console as a table
   - In pretty-printed JSON
   - In XML format

---

## How to Run

### From Eclipse or IntelliJ
1. Import project as Maven project.
3. Modify filters in `CarApp.java`:
   ```java
   filterByBrand = "Toyota";
   filterByPrice = 50000.0;
   sortBy = "price"; // or "releaseDate"
   
### From Eclipse or IntelliJ
1. mvn clean package
2. java -cp target/CarApp-1.0-SNAPSHOT.jar carapp.CarApp



