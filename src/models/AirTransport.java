package models;
public class AirTransport extends Transport {
    private double altitude;
    private String airline;

    public AirTransport(String id , String name , int capacity , double speed , String airline){
        super(id, name, capacity, speed);
        this.airline = airline;
        this.altitude = 0;
    }

    @Override
    public String getType(){
        return "AIR";
    }

    @Override
    public void displayInfo(){
        System.out.printf("%s - Airline : %s | Altitude : %.1f ft | %s%n",name, airline,altitude,isAvailable ? "Available ": "Busy");
    }

    public void takeoff(){
        System.out.printf("%s is taking off !%n",name);
        this.altitude = 35000;
    }

    public void land (){
        System.out.printf("%s is landing !%n",name);
        this.altitude = 0;
    }

    public String getAirline (){
        return airline;
    }
    public double getAltitude(){
        return altitude;
    }
    
}
