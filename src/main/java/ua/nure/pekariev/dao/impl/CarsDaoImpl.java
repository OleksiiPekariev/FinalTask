package ua.nure.pekariev.dao.impl;

import static ua.nure.pekariev.queries.CarDaoQueries.UPDATE_CAR_QUERY;
import static ua.nure.pekariev.queries.CarDaoQueries.DELETE_CAR_QUERY;
import static ua.nure.pekariev.queries.CarDaoQueries.COUNT_AVAIALABLE_CARS_QUERY;
import static ua.nure.pekariev.queries.CarDaoQueries.COUNT_CARS_IN_ORDER_QUERY;
import static ua.nure.pekariev.queries.CarDaoQueries.CREATE_CAR_QUERY;
import static ua.nure.pekariev.queries.CarDaoQueries.GET_ALL_CAR_CLASSES_QUERY;
import static ua.nure.pekariev.queries.CarDaoQueries.GET_ALL_CAR_NAMES_QUERY;
import static ua.nure.pekariev.queries.CarDaoQueries.GET_ALL_REGISTERED_CAR_NAMES_QUERY;
import static ua.nure.pekariev.queries.CarDaoQueries.GET_AVAIALABLE_CARS_QUERY;
import static ua.nure.pekariev.queries.CarDaoQueries.GET_CARS_IN_ORDERS_QUERY;
import static ua.nure.pekariev.queries.CarDaoQueries.GET_CAR_BY_ID_QUERY;
import static ua.nure.pekariev.queries.CarDaoQueries.GET_CAR_BY_STATE_NUMBER;
import static ua.nure.pekariev.queries.CarDaoQueries.GET_CAR_MODELS_OF_SELECTED_CAR_NAME_QUERY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.nure.pekariev.dao.CarsDao;
import ua.nure.pekariev.dao.impl.rsh.CarResultSetHandler;
import ua.nure.pekariev.dao.impl.rsh.ListCarResultSetHandler;
import ua.nure.pekariev.exceptions.ApplicationException;
import ua.nure.pekariev.model.Car;
import ua.nure.pekariev.model.CarClass;
import ua.nure.pekariev.model.CarName;
import ua.nure.pekariev.model.Checkable;
import ua.nure.pekariev.utils.ConnectionUtils;
import ua.nure.pekariev.utils.QueryUtils;

public class CarsDaoImpl implements CarsDao {

	@Override
	public Car getCar(Long carId) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(GET_CAR_BY_ID_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY)) {
			st.setLong(1, carId);
			ResultSet rs = st.executeQuery();
			return new CarResultSetHandler().handle(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Car getCar(Connection connection, Long carId) {
		try (PreparedStatement st = connection.prepareStatement(GET_CAR_BY_ID_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY)) {
			st.setLong(1, carId);
			ResultSet rs = st.executeQuery();
			return new CarResultSetHandler().handle(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Car getCarByStateNumber(String stateNumber) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(GET_CAR_BY_STATE_NUMBER,
				ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			st.setString(1, stateNumber);
			ResultSet rs = st.executeQuery();
			return new CarResultSetHandler().handle(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Checkable> getAllCarNames() {
		List<Checkable> carNames = new ArrayList<>();
		Connection connection = ConnectionUtils.getConnection();
		try (Statement st = connection.createStatement()) {
			ResultSet rs = st.executeQuery(GET_ALL_CAR_NAMES_QUERY);
			while (rs.next()) {
				CarName name = new CarName();
				name.setName(rs.getString("name"));
				carNames.add(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return carNames;
	}

	@Override
	public List<String> getAllCarModelsForName(String carName) {
		List<String> carModels = new ArrayList<>();
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(GET_CAR_MODELS_OF_SELECTED_CAR_NAME_QUERY,
				ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			st.setString(1, carName);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				carModels.add(rs.getString("model"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return carModels;
	}

	@Override
	public List<Checkable> getAllRegisteredCarNames() {

		List<Checkable> carNames = new ArrayList<>();
		Connection connection = ConnectionUtils.getConnection();
		try (Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(GET_ALL_REGISTERED_CAR_NAMES_QUERY)) {

			while (rs.next()) {
				CarName name = new CarName();
				name.setName(rs.getString("name"));
				carNames.add(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return carNames;
	}

	@Override
	public List<Checkable> getAllCarClasses() {
		List<Checkable> carClasses = new ArrayList<>();
		Connection connection = ConnectionUtils.getConnection();
		try (Statement st = connection.createStatement()) {
			ResultSet rs = st.executeQuery(GET_ALL_CAR_CLASSES_QUERY);

			while (rs.next()) {
				CarClass className = new CarClass();
				className.setName(rs.getString("class"));
				carClasses.add(className);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return carClasses;
	}

	@Override
	public List<Car> getAvailableCars(String[] carNames, String[] carClasses, String sortBy, int limit, int offset) {
		String carNameSelection = QueryUtils.getSelectionQueryPart("name", carNames);
		String carClassesSelection = QueryUtils.getSelectionQueryPart("car_class.class", carClasses);
		String resultQuery = QueryUtils.generateCarQueryWithParameters(GET_AVAIALABLE_CARS_QUERY, carNameSelection,
				carClassesSelection, sortBy);
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(resultQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY)) {
			st.setInt(1, limit);
			st.setInt(2, offset);
			ResultSet rs = st.executeQuery();
			return new ListCarResultSetHandler().handle(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Car> getCarsInOrders(String[] carNames, String[] carClasses, String sortBy, int limit, int offset) {
		String carNameSelection = QueryUtils.getSelectionQueryPart("name", carNames);
		String carClassesSelection = QueryUtils.getSelectionQueryPart("car_class.class", carClasses);
		String resultQuery = QueryUtils.generateCarQueryWithParameters(GET_CARS_IN_ORDERS_QUERY, carNameSelection,
				carClassesSelection, sortBy);
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(resultQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY)) {
			st.setInt(1, limit);
			st.setInt(2, offset);
			ResultSet rs = st.executeQuery();
			return new ListCarResultSetHandler().handle(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long countAllAvailableCars(String[] carNames, String[] carClasses) {
		String carNameSelection = QueryUtils.getSelectionQueryPart("name", carNames);
		String carClassesSelection = QueryUtils.getSelectionQueryPart("car_class.class", carClasses);
		String resultQuery = QueryUtils.generateCarQueryWithParameters(COUNT_AVAIALABLE_CARS_QUERY, carNameSelection,
				carClassesSelection);
		Connection connection = ConnectionUtils.getConnection();
		try (Statement st = connection.createStatement()) {
			ResultSet rs = st.executeQuery(resultQuery);
			if (rs.next()) {
				return rs.getLong("count");
			} else {
				return 0L;
			}
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public Long countCheckedCarsInOrders(String[] carNames, String[] carClasses) {
		String carNameSelection = QueryUtils.getSelectionQueryPart("name", carNames);
		String carClassesSelection = QueryUtils.getSelectionQueryPart("car_class.class", carClasses);
		String resultQuery = QueryUtils.generateCarQueryWithParameters(COUNT_CARS_IN_ORDER_QUERY, carNameSelection,
				carClassesSelection);
		Connection connection = ConnectionUtils.getConnection();
		try (Statement st = connection.createStatement()) {
			ResultSet rs = st.executeQuery(resultQuery);
			if (rs.next()) {
				return rs.getLong("count");
			} else {
				return 0L;
			}
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public Car createCar(Car car) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(CREATE_CAR_QUERY, Statement.RETURN_GENERATED_KEYS)) {
			st.setString(1, car.getManufacturer());
			st.setString(2, car.getModel());
			st.setString(3, car.getStateNumber());
			st.setInt(4, car.getYear());
			st.setInt(5, car.getRentValuePerDay());
			st.setString(6, car.getEquipmentInformation());
			st.setBoolean(7, car.isAirConditioner());
			st.setBoolean(8, car.isNavigation());
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			Long insertedCarId = rs.getLong("id");
			return getCar(connection, insertedCarId);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public Car editCar(Car carToEdit) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(UPDATE_CAR_QUERY, Statement.RETURN_GENERATED_KEYS)) {
			st.setString(1, carToEdit.getManufacturer());
			st.setString(2, carToEdit.getModel());
			st.setString(3, carToEdit.getStateNumber());
			st.setInt(4, carToEdit.getYear());
			st.setInt(5, carToEdit.getRentValuePerDay());
			st.setString(6, carToEdit.getEquipmentInformation());
			st.setBoolean(7, carToEdit.isAirConditioner());
			st.setBoolean(8, carToEdit.isNavigation());
			st.setLong(9, carToEdit.getId());
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			Long insertedCarId = rs.getLong("id");
			return getCar(connection, insertedCarId);
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public boolean removeCar(Car car) {
		Connection connection = ConnectionUtils.getConnection();
		try (PreparedStatement st = connection.prepareStatement(DELETE_CAR_QUERY, Statement.RETURN_GENERATED_KEYS)) {
			st.setLong(1, car.getId());
			return st.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}
}
