package models;

public abstract class Transport {
    protected String id;
    protected String name;
    protected int capacity;
    protected double speed;
    protected boolean isAvailable;

    public Transport(String id , String name , int capacity , double speed ){
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.speed = speed;
        this.isAvailable = true;
    }

    public String getId() {
        return id;
    }
    public String getName(){
        return name;
    }
    public int getCapacity(){
        return capacity;
    }
    public double getSpeed(){
        return speed;
    }
    public boolean isAvailable(){
        return isAvailable;
    }
    public void setAvailable(boolean available){
        isAvailable = available;
    }
    public abstract String getType();
    public abstract void displayInfo();

    @Override
    public String toString(){
        return String.format("%s [ID : %s , Capacity : %d , Speed : %.1f km/h , Available : %s]",name,id,capacity,speed,isAvailable );
    }
}
