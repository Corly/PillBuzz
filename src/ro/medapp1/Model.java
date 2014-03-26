package ro.medapp1;
public class Model{
	 

    private String title;
    private String counter;
 
    private boolean isGroupHeader = false;
 
    public Model(String title) {
        this(title,null);
        isGroupHeader = true;
    }
    public Model( String title, String counter) {
        super();
        this.title = title;
        this.counter = counter;
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
 


}