package carapp.car;

import java.time.LocalDate;

//carsBrand model from csv - brand, releaseDate
public class CarsBrand {

	private String brand;
	private LocalDate releaseDate;

	public CarsBrand() {
	}

	public CarsBrand(String brand, LocalDate releaseDate) {
		this.brand = brand;
		this.releaseDate = releaseDate;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}
	
}
