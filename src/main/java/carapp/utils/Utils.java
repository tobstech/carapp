package carapp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import carapp.CarApp;
import carapp.car.Car;
import carapp.car.CarsBrand;
import carapp.car.CarsType;


public class Utils {	
	
	public Utils() {
	}

	// Parses car types and prices from an XML file
    public static List<CarsType> loadCarsTypeFromXml(String xmlFile) throws Exception {
    	
        List<CarsType> cars = new ArrayList<>();
        
        InputStream is = CarApp.class.getResourceAsStream("/" + xmlFile);
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
        
        NodeList nodes = document.getElementsByTagName(XmlTags.car.name());

        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);
            String type = e.getElementsByTagName(XmlTags.type.name()).item(0).getTextContent();
            String model = e.getElementsByTagName(XmlTags.model.name()).item(0).getTextContent();
            BigDecimal usd = new BigDecimal(e.getElementsByTagName(XmlTags.price.name()).item(0).getTextContent());

            Map<String, BigDecimal> prices = new HashMap<>();
            prices.put(Currency.USD.name(), usd);

            NodeList priceNodes = e.getElementsByTagName(XmlTags.prices.name()).item(0).getChildNodes();
            for (int j = 0; j < priceNodes.getLength(); j++) {
                if (priceNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                    Element p = (Element) priceNodes.item(j);
                    prices.put(p.getAttribute("currency"), new BigDecimal(p.getTextContent()));
                }
            }
            cars.add(new CarsType(type, model, prices));
        }
        return cars;
    }
	
    // Parses car brands and release dates from the CSV file
    public static Map<String, CarsBrand> loadCarsBrandFromCsv(String csvPath) throws IOException {
        Map<String, CarsBrand> map = new HashMap<>();
        List<String> lines = Files.readAllLines(Paths.get(csvPath));
        for (int i = 1; i < lines.size(); i++) {
            String[] p = lines.get(i).split(",");
            if (p.length == 2) {
            	
            	String rawDate = p[1].replace("/", "-").trim(); // remove spaces
            	rawDate = rawDate.replace("\"", ""); // remove quotes if any
            	LocalDate date = LocalDate.parse(rawDate, DateTimeFormatter.ofPattern("MM-dd-yyyy"));

            	p[0] = p[0].replace("\"", "");
            	
                //LocalDate date = LocalDate.parse(p[1].replace("/", "-"), java.time.format.DateTimeFormatter.ofPattern("MM-dd-yyyy"));
                map.put(p[0], new CarsBrand(p[0], date));
            }
        }
        return map;
    }
	
	//manually map brand to model because the xml file on contains model names and csv only contains brand names and release date with no reference to model
    public static Map<String, String> mapModelToBrand(){
    	
    	Map<String, String> modelToBrand = new HashMap<>();
		modelToBrand.put("RAV4", "Toyota");
		modelToBrand.put("Civic", "Honda");
		modelToBrand.put("F-150", "Ford");
		modelToBrand.put("Model X", "Tesla");
		modelToBrand.put("330i", "BMW");
		modelToBrand.put("Q5", "Audi");
		modelToBrand.put("Silverado", "Chevrolet");
		modelToBrand.put("C-Class", "Mercedes-Benz");
		modelToBrand.put("Rogue", "Nissan");
		modelToBrand.put("Elantra", "Hyundai");	
    	
		return modelToBrand;
    	
    }
    
	// Displays filtered car data in table format
    public static void printInTable(List<Car> cars) {
		System.out.println("\n==== TABLE OUTPUT ====");
		System.out.printf("%-10s %-10s %-15s %-12s %-20s\n", "Type", "Model", "Brand", "Release Date",
				"Converted Price");
		for (Car car : cars) {
			System.out.printf("%-10s %-10s %-15s %-12s %-20s\n", car.getTypeInfo().getType(),
					car.getTypeInfo().getModel(), car.getBrandInfo().getBrand(), car.getBrandInfo().getReleaseDate(),
					car.getConvertedPrice());
		}
	}
    
    // Outputs filtered car data in JSON format using Jackson
    public static void printInJson(List<Car> cars) throws IOException {
        System.out.println("\n==== JSON OUTPUT ====");
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.registerModule(new JavaTimeModule()); // ✅ Add this line
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Optional: pretty format

        List<Map<String, Object>> jsonList = cars.stream()
        		.map(car -> car.toMap()).collect(Collectors.toList());
        System.out.println(mapper.writeValueAsString(jsonList));
    }
	
    // Outputs filtered car data in a basic XML format
    public static void printInXml(List<Car> cars) {
        System.out.println("\n==== XML OUTPUT ====");
        System.out.println("<cars>");
        for (Car car : cars) {
            System.out.println("  <car>");
            System.out.println("    <type>" + car.getTypeInfo().getType() + "</type>");
            System.out.println("    <model>" + car.getTypeInfo().getModel() + "</model>");
            System.out.println("    <brand>" + car.getBrandInfo().getBrand() + "</brand>");
            System.out.println("    <releaseDate>" + car.getBrandInfo().getReleaseDate() + "</releaseDate>");
            System.out.println("    <convertedPrice>" + car.getConvertedPrice() + "</convertedPrice>");
            System.out.println("  </car>");
        }
        System.out.println("</cars>");
    }
	

}
