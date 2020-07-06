package org.bytebrain.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Wages")
@XmlType(name = "", propOrder = { "mobileAllowance", "internetAllowance", "grossSalary", "benefit", "bonus",
		"deductions", "currency" })
public class Wages implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement
	private Double mobileAllowance;
	@XmlElement
	private Double internetAllowance;
	@XmlElement
	private Double grossSalary;
	@XmlElement
	private Double benefit;
	@XmlElement
	private Double bonus;
	@XmlElement
	private Double deductions;
	@XmlElement
	private String currency;

	// public Wages(Double mobileAllowance, Double internetAllowance, Double
	// grossSalary, Double benefit, Double bonus,
	// Double deductions, String currency) {
	// super();
	// this.mobileAllowance = mobileAllowance;
	// this.internetAllowance = internetAllowance;
	// this.grossSalary = grossSalary;
	// this.benefit = benefit;
	// this.bonus = bonus;
	// this.deductions = deductions;
	// this.currency = currency;
	// }

	public Double getMobileAllowance() {
		return mobileAllowance;
	}

	public void setMobileAllowance(Double mobileAllowance) {
		this.mobileAllowance = mobileAllowance;
	}

	public Double getInternetAllowance() {
		return internetAllowance;
	}

	public void setInternetAllowance(Double internetAllowance) {
		this.internetAllowance = internetAllowance;
	}

	public Double getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(Double grossSalary) {
		this.grossSalary = grossSalary;
	}

	public Double getBenefit() {
		return benefit;
	}

	public void setBenefit(Double benefit) {
		this.benefit = benefit;
	}

	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}

	public Double getDeductions() {
		return deductions;
	}

	public void setDeductions(Double deductions) {
		this.deductions = deductions;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
