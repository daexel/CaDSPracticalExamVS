/**
 * 
 */
package cads.parser.generated;


/**
 * @author daexel
 *
 */
public class Message {
	public Message(ServiceType type,int direction, int strength, int transactionID) {
		this.type=type;
		this.strength=strength;
		this.transaktionID=transactionID;
		this.direction=direction;
	}
 private ServiceType type;
 private int strength;
 private int transaktionID;
 private int direction; //1 UpLeftOpen, 0 DownRightClose
 
 
public ServiceType getType() {
	return type;
}
public int getDirection() {
	return direction;
}
public void setDirection(int direction) {
	this.direction = direction;
}
public void setType(ServiceType type) {
	this.type = type;
}
public int getStrength() {
	return strength;
}
public void setStrength(int strength) {
	this.strength = strength;
}
public int getTransaktionID() {
	return transaktionID;
}
public void setTransaktionID(int transaktionID) {
	this.transaktionID = transaktionID;
}
 
 
 
}
