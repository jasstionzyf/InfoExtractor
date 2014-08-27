
public class Item {
	private String description=null;

private String name=null;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Item(String name, String description) {
	super();
	this.name = name;
	this.description = description;
}
public Item() {
	super();
	// TODO Auto-generated constructor stub
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
}
