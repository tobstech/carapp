package carapp.car;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

//carsType xml fields map - type, model, prices
public class CarsType {
	
	private String type;
	private String model;
	Map<String, BigDecimal> prices = new HashMap<>();
	
	public CarsType() {
	}

	public CarsType(String type, String model, Map<String, BigDecimal> prices) {
		this.type = type;
		this.model = model;
		this.prices = prices;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Map<String, BigDecimal> getPrices() {
		return prices;
	}

	public void setPrices(Map<String, BigDecimal> prices) {
		this.prices = prices;
	}
	
	
}
