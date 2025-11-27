package models;
public class SeaTransport extends Transport {
    private double depth;
    private String port;

    public SeaTransport(String id , String name , int capacity , double speed , String port){
        super(id, name, capacity, speed);
        this.port = port;
        this.depth = 0;
    }

    @Override
    public String getType(){
        return "SEA";
    }

    @Override
    public void displayInfo(){
        System.out.printf("%s - Port : %s | Depth : %.1f m | %s%n", name, port, depth, isAvailable ? "Available " : "Busy");
    }

    public void setSail(){
        System.out.printf("%s is setting sail!%n", name);
        this.depth = 100;
    }

    public void dock(){
        System.out.printf("%s is docking!%n", name);
        this.depth = 0;
    }

    public String getPort(){
        return port;
    }

    public double getDepth(){
        return depth;
    }
}
