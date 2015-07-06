package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity(name="Employee")
public class Employee {

	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String firstName;
	
	private String lastName;  

	private Category category;

	public int getId() {  
	    return id;  
	}

	public void setId(int id) {  
	    this.id = id;  
	}

	public String getFirstName() {  
	    return firstName;  
	}  

	public void setFirstName(String firstName) {  
	    this.firstName = firstName;  
	}  

	public String getLastName() {  
	    return lastName;  
	}

	public void setLastName(String lastName) {  
	    this.lastName = lastName;  
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}  