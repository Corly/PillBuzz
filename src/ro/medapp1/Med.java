package ro.medapp1;

public class Med {
	private String name;
	private String administrationMethod;
	private String description;
	private String previousDrugs;
	private String drugInteraction;
	private String picture;
	private int dosage;
	private int firstDoseHour;
	private int firstDoseMinute;
	private int startDateDay;
	private int startDateMonth;
	private int startDateYear;
	private int endDateDay;
	private int endDateMonth;
	private int endDateYear;
	
	public String getAdministrationMethod() {
		return administrationMethod;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getDosage() {
		return dosage;
	}
	
	public int getEndDateDay() {
		return endDateDay;
	}
	
	public int getEndDateMonth() {
		return endDateMonth;
	}
	
	public int getEndDateYear() {
		return endDateYear;
	}
	
	public int getFirstDoseHour() {
		return firstDoseHour;
	}
	
	public int getFirstDoseMinute() {
		return firstDoseMinute;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public int getStartDateDay() {
		return startDateDay;
	}
	
	public int getStartDateMonth() {
		return startDateMonth;
	}
	
	public int getStartDateYear() {
		return startDateYear;
	}
	
	public String getDrugInteraction() {
		return drugInteraction;
	}
	
	public String getPreviousDrugs() {
		return previousDrugs;
	}
	
	public void setAdministrationMethod(String administrationMethod) {
		this.administrationMethod = administrationMethod;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setDosage(int dosage) {
		this.dosage = dosage;
	}
	
	public void setEndDateDay(int endDateDay) {
		this.endDateDay = endDateDay;
	}
	
	public void setEndDateMonth(int endDateMonth) {
		this.endDateMonth = endDateMonth;
	}
	
	public void setEndDateYear(int endDateYear) {
		this.endDateYear = endDateYear;
	}
	
	public void setFirstDoseHour(int firstDoseHour) {
		this.firstDoseHour = firstDoseHour;
	}
	
	public void setFirstDoseMinute(int firstDoseMinute) {
		this.firstDoseMinute = firstDoseMinute;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public void setStartDateDay(int startDateDay) {
		this.startDateDay = startDateDay;
	}
	
	public void setStartDateMonth(int startDateMonth) {
		this.startDateMonth = startDateMonth;
	}
	
	public void setStartDateYear(int startDateYear) {
		this.startDateYear = startDateYear;
	}
	
	public void setDrugInteraction(String drugInteraction) {
		this.drugInteraction = drugInteraction;
	}
	
	public void setPreviousDrugs(String previousDrugs) {
		this.previousDrugs = previousDrugs;
	}
}
