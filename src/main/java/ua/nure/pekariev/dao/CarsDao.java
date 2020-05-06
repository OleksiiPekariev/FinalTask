package ua.nure.pekariev.dao;

import java.util.List;

import ua.nure.pekariev.model.Car;
import ua.nure.pekariev.model.Checkable;

public interface CarsDao {

	List<Checkable> getAllCarNames();

	List<String> getAllCarModelsForName(String carName);

	List<Checkable> getAllCarClasses();

	List<Checkable> getAllRegisteredCarNames();

	Car getCar(Long carId);

	List<Car> getAvailableCars(String[] carNames, String[] carClasses, String sortBy, int limit, int offset);

	List<Car> getCarsInOrders(String[] carNames, String[] carClasses, String sortBy, int limit, int offset);

	Long countAllAvailableCars(String[] carNames, String[] carClasses);

	Long countCheckedCarsInOrders(String[] carNames, String[] carClasses);

	Car getCarByStateNumber(String stateNumber);

	Car createCar(Car car);

	Car editCar(Car carToEdit);

	boolean removeCar(Car car);

}
