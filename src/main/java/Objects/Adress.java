package Objects;

public class Adress {

	private int adressId;
	private String street;
	private int number;
	private String country;
	private String postalCode;
	private String description;
	private String location;
	
	public Adress(int adressID, String street, int number, String country, String postalCode, String description, String location) {
		this.adressId = adressID;
		this.setStreet(street);
		this.setNumber(number);
		this.country = country;
		this.setPostalCode(postalCode);
		this.setDescription(description);
		this.setLocation(location);
	}
	
	public Adress(String street, int number, String country, String postalCode, String description, String location) {
		this.setStreet(street);
		this.setNumber(number);
		this.country = country;
		this.setPostalCode(postalCode);
		this.setDescription(description);
		this.setLocation(location);
	}
	
	public String getDescription() {
		return description;
	}


	public String getLocation() {
		return location;
	}


	public void setDescription(String description) {
		if (description != null){
			this.description = description;
		}else{
			this.description = "";
		}
	}


	public void setLocation(String location) {
		if (location != null){
			this.location = location;
		}else{
			this.location = "";
		}
	}


	public int getAdressId() {
		return adressId;
	}

	public void setAdressId(int adressId) {
		this.adressId = adressId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		if (street != null){
			this.street = street;
		}else{
			this.street = "";
		}
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		if (postalCode != null){
			this.postalCode = postalCode;
		}else{
			this.postalCode = "";
		}
	}
}
