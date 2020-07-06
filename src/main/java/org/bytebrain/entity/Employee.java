package org.bytebrain.entity;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "employee")
@XmlType(name = "", propOrder = { "firstName", "lastName", "onshoreWages", "locations" })
public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String firstName;

	private String lastName;

	private Wages onshoreWages;

	private List<Locations> locations;

	// When usage of constructor, follow
	// https://stackoverflow.com/questions/4387296/jaxb-and-constructors
	// public Employee(String firstName, String lastName) {
	// this.firstName = firstName;
	// this.lastName = lastName;
	// }

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

	public Wages getOnshoreWages() {
		return onshoreWages;
	}

	public void setOnshoreWages(Wages onshoreWages) {
		this.onshoreWages = onshoreWages;
	}

	public List<Locations> getLocations() {
		return locations;
	}

	public void setLocations(List<Locations> locations) {
		this.locations = locations;
	}

}
