package ro.medapp1;


import android.content.Intent;

public class Med {
	private static Integer idCounter=0;
	private String id;
	private String name;
	private String administrationMethod;
	private String description;
	private String unit;
	private int dosage;
	//ignored by Windows Azure
	transient private Intent alarmIntent;
	private int interval;
	private int firstDoseHour;
	private int firstDoseMinute;
	private int startDateDay;
	private int startDateMonth;
	private int startDateYear;
	private int nextDoseHour;
	private int nextDoseMinute;
	private int nextDoseDay;
	private int nextDoseMonth;
	private int nextDoseYear;
	private int endDateDay;
	private int endDateMonth;
	private int endDateYear;
	private int intentID;
	
	public Med(){}


	public Med(String name, String description, String administration,
			int dosage, String unit, int interval, int startTimeHour,
			int startTimeMinute, int startDateDay, int startDateMonth,
			int startDateYear, int nextDoseHour, int nextDoseMinute,
			int nextDoseDay, int nextDoseMonth, int nextDoseYear,
			int endDateDay, int endDateMonth, int endDateYear, int intentID) {

		this.id=(idCounter++).toString();
		this.name = name;
		this.description = description;
		this.administrationMethod = administration;
		this.dosage = dosage;
		this.interval = interval;
		this.unit = unit;
		this.intentID = intentID;
		
		this.startDateDay = startDateDay;
		this.startDateMonth = startDateMonth;
		this.startDateYear = startDateYear;
		
		this.endDateDay = endDateDay;
		this.endDateMonth = endDateMonth;
		this.endDateYear = endDateYear;
		
		this.firstDoseHour = startTimeHour;
		this.firstDoseMinute = startTimeMinute;
		
		this.nextDoseHour = nextDoseHour; 
		this.nextDoseMinute = nextDoseMinute;
		this.nextDoseDay = nextDoseDay;
		this.nextDoseMonth = nextDoseMonth; 
		this.nextDoseYear = nextDoseYear;
	}
	
	public int getNextDoseDay() {
		return nextDoseDay;
	}
	
	public int getNextDoseHour() {
		return nextDoseHour;
	}
	
	public int getNextDoseMinute() {
		return nextDoseMinute;
	}
	
	public int getNextDoseMonth() {
		return nextDoseMonth;
	}
	
	public int getNextDoseYear() {
		return nextDoseYear;
	}

	public int getInterval() {
		return interval;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public String getAdministrationMethod() {
		return administrationMethod;
	}

	public String getDescription() {
		return description;
	}

	public int getDosage() {
		return dosage;
	}

	public String getName() {
		return name;
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


	public int getStartDateDay() {
		return startDateDay;
	}

	public int getStartDateMonth() {
		return startDateMonth;
	}

	public int getStartDateYear() {
		return startDateYear;
	}
	
	public int getIntentID() {
		return intentID;
	}
	
	public Intent getAlarmIntent() {
		return alarmIntent;
	}
	
	public void setNextDoseDay(int nextDoseDay) {
		this.nextDoseDay = nextDoseDay;
	}
	
	public void setNextDoseHour(int nextDoseHour) {
		this.nextDoseHour = nextDoseHour;
	}
	
	public void setNextDoseMinute(int nextDoseMinute) {
		this.nextDoseMinute = nextDoseMinute;
	}
	
	public void setNextDoseMonth(int nextDoseMonth) {
		this.nextDoseMonth = nextDoseMonth;
	}
	
	public void setNextDoseYear(int nextDoseYear) {
		this.nextDoseYear = nextDoseYear;
	}
	
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	public void setIntentID(int intentID) {
		this.intentID = intentID;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
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

	public void setName(String name) {
		this.name = name;
	}

	
	public String getId() {
		return id;
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

	public void setStartDateDay(int startDateDay) {
		this.startDateDay = startDateDay;
	}

	public void setStartDateMonth(int startDateMonth) {
		this.startDateMonth = startDateMonth;
	}

	public void setStartDateYear(int startDateYear) {
		this.startDateYear = startDateYear;
	}
	
	public void setAlarmIntent(Intent alarmIntent) {
		this.alarmIntent = alarmIntent;
	}



	public static Integer getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(Integer idCounter) {
		Med.idCounter = idCounter;
	}

	@Override
	public boolean equals(Object o) {
		Med x = (Med) o;
		return name.equals(x.name);
	}
}
