import java.util.ArrayList;

public class RentedCars {

    private final ArrayList<String> rentedCars = new ArrayList<> ();

    public RentedCars() {
    }

    public int getCarsNo() {
        return rentedCars.size();
    }

    public void addCar(String plateNumber) {
        rentedCars.add(plateNumber);
    }

    public void removeCar(String plateNumber){
        rentedCars.remove(plateNumber);
    }

    public void printCarList(){
        for (String rentedCar : rentedCars) {
            System.out.println(rentedCar);
        }
    }
}
