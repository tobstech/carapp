package carapp;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import carapp.car.Car;
import carapp.car.CarsBrand;
import carapp.car.CarsType;
import carapp.utils.Currency;
import carapp.utils.Utils;

public class CarApp {
	
	private static String filterByBrand;
	private static Double  filterByPrice;
	
	public static void main(String[] args) {
		
		filterByBrand = "Toyota"; //enter brand name to filter by
		filterByPrice = 50000.0; //enter price to filter by
		
		filesProcessor(filterByBrand, filterByPrice);
		
	}
	
	//file processor
	private static void filesProcessor(String filterByBrand, Double  filterByPrice) {
		
		try {
			//parse and process CAR (XML)
			List<CarsType> carTypes = Utils.loadCarsTypeFromXml("carsType.xml");
			
			//parse and process CAR (CSV)
			Map<String, CarsBrand> brandMap = Utils.loadCarsBrandFromCsv("src/main/resources/CarsBrand.csv");
			
			//map model to brand
			Map<String, String> modelToBrand = Utils.mapModelToBrand();
			
			// Convert a list of CarType objects into a list of Car objects using model-brand mapping and brand metadata
			List<Car> cars = carTypes.stream()
					.map(carType -> {							
							String brandName = modelToBrand.get(carType.getModel());
							CarsBrand carsBrand = brandMap.get(brandName);
							return carsBrand != null ? new Car(carsBrand, carType) : null;
					})  
					.filter(Objects::nonNull) // Filter out any nulls
					.collect(Collectors.toList()); // Collect the result into a list

		    // Filter and sort car
	        List<Car> result = cars.stream()
	            .filter(car -> car.getBrandInfo().getBrand().equalsIgnoreCase(filterByBrand)) //filter by brand
	            .filter(car -> car.getTypeInfo().getPrices().get(Currency.USD.name()).compareTo(new BigDecimal(filterByPrice)) <= 0) //filter by price
	            .sorted(Comparator.comparing((Car car) -> car.getBrandInfo().getReleaseDate()).reversed()) //sort by brand and release date
	            .collect(Collectors.toList());
			
	        //printing result in different format
	        Utils.printInTable(result);
	        Utils.printInXml(result);
	        Utils.printInJson(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	

}
