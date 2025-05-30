package carapp.car;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

//Unified Car class for carsBrand and carsType
public class Car {

	//fields
	private CarsBrand brandInfo;
	private CarsType typeInfo;

	//no args constructor
	public Car() {
	}

	//all args constructor
	public Car(CarsBrand brandInfo, CarsType typeInfo) {
		this.brandInfo = brandInfo;
		this.typeInfo = typeInfo;
	}

	//getters and setters
	public CarsBrand getBrandInfo() {
		return brandInfo;
	}

	public void setBrandInfo(CarsBrand brandInfo) {
		this.brandInfo = brandInfo;
	}

	public CarsType getTypeInfo() {
		return typeInfo;
	}

	public void setTypeInfo(CarsType typeInfo) {
		this.typeInfo = typeInfo;
	}

	//get converted price
    public String getConvertedPrice() {
        switch (typeInfo.getType().toUpperCase()) {
            case "SUV":
                return typeInfo.getPrices().getOrDefault("EUR", BigDecimal.ZERO) + " EUR";
            case "SEDAN":
                return typeInfo.getPrices().getOrDefault("JPY", BigDecimal.ZERO) + " JPY";
            case "TRUCK":
                return typeInfo.getPrices().getOrDefault("USD", BigDecimal.ZERO) + " USD";
            default:
                return typeInfo.getPrices().getOrDefault("USD", BigDecimal.ZERO) + " USD";
        }
    }
	
    //to Map method to serialize json print
    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", typeInfo.getType());
        map.put("model", typeInfo.getModel());
        map.put("brand", brandInfo.getBrand());
        map.put("releaseDate", brandInfo.getReleaseDate());
        map.put("convertedPrice", getConvertedPrice());
        return map;
    }
	
	
	
}
