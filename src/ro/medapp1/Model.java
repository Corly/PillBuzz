package ro.medapp1;



public class Model implements Comparable<Model>{
	 

    private String title;
    private String counter;
    private int hour,minutes;
 
    private boolean isGroupHeader = false;
 
    public Model(String title) {
        this(title,null,0,0);
        isGroupHeader = true;
    }
   


	public Model(String title, String counter, int hour, int minutes) {
		super();
		this.title = title;
		this.counter = counter;
		this.hour = hour;
		this.minutes = minutes;
		this.isGroupHeader = false;
	}



	public int getHour() {
		return hour;
	}



	public int getMinutes() {
		return minutes;
	}



	public void setHour(int hour) {
		this.hour = hour;
	}



	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}



	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCounter() {
		return counter;
	}
	public void setCounter(String counter) {
		this.counter = counter;
	}
	public boolean isGroupHeader() {
		return isGroupHeader;
	}
	public void setGroupHeader(boolean isGroupHeader) {
		this.isGroupHeader = isGroupHeader;
	}

	@Override
	public int compareTo(Model arg0) {
		if(this.hour>arg0.getHour()) return 1;
		else if(this.hour<arg0.getHour()) return -1;
		else if(this.minutes>arg0.getMinutes()) return 1;
		else if(this.minutes<arg0.getMinutes()) return -1;
		else return 0;
	}
	


}