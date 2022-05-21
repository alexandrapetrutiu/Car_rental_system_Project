import java.util.HashMap;
import java.util.Scanner;

public class CarRentalSystem {
    private static Scanner sc = new Scanner(System.in);
    //primul hash retine masinile inchiriate (plate no si nume proprietar)
    private final HashMap<String, String> rentedCars = new HashMap<String, String>(100, 0.5f);

    //al doilea retine proprietarii (nume proprietar , lista de masini inchiriate)
    private final HashMap<String, RentedCars> carOwners = new HashMap<>(100, 0.5f);

    private static String getPlateNo() {
        System.out.println("Introduceti numarul de inmatriculare:");
        return sc.nextLine();
    }

    private static String getOwnerName() {
        System.out.println("Introduceti numele proprietarului:");
        return sc.nextLine();
    }

    // search for a key in hashtable
    private boolean isCarRent(String plateNo) {
        return rentedCars.containsKey(plateNo);
    }

    // get the value associated to a key
    // 1.3
    private String getCarRent(String plateNo) {
        if(!rentedCars.containsKey(plateNo)){
            return "Autovehiculul cu numarul: " + plateNo + " nu se afla pe lista autovehiculelor inchiriate.";
        }else{
            return "Autovehiculul cu numarul: " + plateNo + " este inchiriat de " + rentedCars.get(plateNo);
        }
    }

    // add a new (key, value) pair
    // 1.1
    private void rentCar(String plateNo, String ownerName) {
        if (!rentedCars.containsKey(plateNo)) { // daca nr de inmatricuare nu se afla in lista masinilor inchiriate
            rentedCars.put(plateNo, ownerName); // adaugam noua pereche in lista masinilor inchiriate

            if (!carOwners.containsKey(ownerName)) { // daca o pers nu se afla pe lista celor care au inchiriat o masina
                RentedCars listOfRentedCars = new RentedCars(); // cream o noua lista
                listOfRentedCars.addCar(plateNo); // care va retine toate masinile inchiriate de un proprietar
                carOwners.put(ownerName, listOfRentedCars); // adaugam numele celui care inchiriaza si lista de masini aferenta lui
            } else {
                //daca proprietarul se afla deja pe lista de proprietari, atunci doar adaugam inca o masina pper respective
                carOwners.get(ownerName).addCar(plateNo);
            }
        } else { // daca nr de inmatriculare se afla deja in lista masinilor inchiriate
            System.out.println ( "Autovehiculul cu numarul " + plateNo + " este deja inchiriat de " + rentedCars.get(plateNo));
        }
    }

    // remove an existing (key, value) pair
    // 1.2
    private void returnCar(String plateNo) {
        if (rentedCars.containsKey(plateNo)) {
            System.out.println ("S-a efectuat eliminarea din lista a autovehiculului cu numarul: " + plateNo + " inchiriat de " + rentedCars.get(plateNo));
            rentedCars.remove(plateNo); // stergem masina din lista masinilor inchiriate
            if (carOwners.get(rentedCars.get(plateNo)).getCarsNo() > 1) { //daca o pers a inchiriat mai mult de o masina
                carOwners.get(rentedCars.get(plateNo)).removeCar(plateNo); // stergem masina din lista atribuita fiecarui proprietar
            }else{
                carOwners.remove(rentedCars.get(plateNo)); // daca a inchiriat o singura masina, stergem proprietarul de pe lista chiriasilor
            }
        }else{
            System.out.println ("Autovehiculul cu numarul: " + plateNo + " nu se afla pe lista autovehiculelor inchiriate.");
        }
    }

    // 1.4
    private int totalRented(){
        return rentedCars.size();
    }
    // 2.1
    private int getCarsNo(String ownerName){
        if (carOwners.containsKey(ownerName)){
            return carOwners.get(ownerName).getCarsNo();
        }
        System.out.println("Nu exista autovehicule inchiriate pe numele introdus.");
        return 0;
    }

    // 2.2
    private void getCarsList(String ownerName) {
        if (carOwners.containsKey(ownerName)) {
            System.out.println("Proprietarul cu numele: " + ownerName + " a inchiriat urmatoarele autovehicule:");
            carOwners.get(ownerName).printCarList();
        } else {
            System.out.println("Proprietarul cu numele: " + ownerName + " nu a inchiriat niciun autovehicul.");
        }
    }


        private static void printCommandsList() {
        System.out.println("help         - Afiseaza aceasta lista de comenzi");
        System.out.println("add          - Adauga o noua pereche (masina, sofer)");
        System.out.println("check        - Verifica daca o masina este deja luata");
        System.out.println("remove       - Sterge o masina existenta din hashtable");
        System.out.println("getOwner     - Afiseaza proprietarul curent al masinii");
        System.out.println("totalRented  - Afiseaza numarul de autovehicule inchiriate");
        System.out.println("getCarsNo    - Afiseaza numarul de autovehicule inchiriate de un proprietar");
        System.out.println("getCarsList  - Afiseaza lista de masini inchiriate de un proprietar");
        System.out.println("quit         - Inchide aplicatia");
    }

    public void run() {
        boolean quit = false;
        while (!quit) {
            System.out.println("Asteapta comanda: (help - Afiseaza lista de comenzi)");
            String command = sc.nextLine();
            switch (command) {
                case "help":
                    printCommandsList();
                    break;
                case "add":
                    rentCar(getPlateNo(), getOwnerName());
                    break;
                case "check":
                    System.out.println(isCarRent(getPlateNo()));
                    break;
                case "remove":
                    returnCar(getPlateNo());
                    break;
                case "getOwner":
                    System.out.println(getCarRent(getPlateNo()));
                    break;
                case "quit":
                    System.out.println("Aplicatia se inchide...");
                    quit = true;
                    break;
                case "totalRented":
                    System.out.println(totalRented());
                    break;
                case "getCarsNo":
                    System.out.println(getCarsNo(getOwnerName()));
                    break;
                case "getCarsList":
                    getCarsList(getOwnerName());
                    break;
                default:
                    System.out.println("Unknown command. Choose from:");
                    printCommandsList();
            }
        }
    }
    public static void main(String[] args) {

        // create and run an instance (for test purpose)
        new CarRentalSystem().run();

    }

}
