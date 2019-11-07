package reflection;

public class TestCar {

    private String name;
    private String model;
    private String kuzov;
    private String fuel;
    private int door;
    private long power;
    private byte numberOfSpeed;

    public TestCar(String name, String model, String kuzov, String fuel, int door, long power, byte numberOfSpeed) {
        this.name = name;
        this.model = model;
        this.kuzov = kuzov;
        this.fuel = fuel;
        this.door = door;
        this.power = power;
        this.numberOfSpeed = numberOfSpeed;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getKuzov() {
        return kuzov;
    }

    public String getFuel() {
        return fuel;
    }

    public int getDoor() {
        return door;
    }

    public long getPower() {
        return power;
    }

    public byte getNumberOfSpeed() {
        return numberOfSpeed;
    }
}
