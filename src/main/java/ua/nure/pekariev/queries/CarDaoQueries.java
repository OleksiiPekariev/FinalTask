package ua.nure.pekariev.queries;

public final class CarDaoQueries {

	public static final String GET_CAR_BY_ID_QUERY = "SELECT car.id, car_name.name, car_model.model, "
			+ "car_class.class, car.state_number, car.year, car.rent_value_per_day, car.equipment_information, "
			+ "car.air_conditioner, car.navigation FROM car, name_model, car_name, car_model, car_class "
			+ "WHERE car.manuf_model_id = name_model.id AND car_name.id = name_model.id_name "
			+ "AND car_model.id = name_model.id_model AND car_class.id = name_model.id_class AND car.id = ?";

	public static final String GET_CAR_BY_STATE_NUMBER = "SELECT * FROM car, name_model, car_name, car_model, car_class "
			+ "WHERE car.manuf_model_id = name_model.id AND car_name.id = name_model.id_name "
			+ "AND car_model.id = name_model.id_model AND car_class.id = name_model.id_class AND car.state_number = ?";

	public static final String GET_ALL_CAR_NAMES_QUERY = "SELECT name FROM car_name";

	public static final String GET_CAR_MODELS_OF_SELECTED_CAR_NAME_QUERY = "SELECT car_model.model FROM car_name, car_model, name_model "
			+ "WHERE car_model.id = name_model.id_model AND car_name.id = name_model.id_name AND car_name.name = ?";

	public static final String GET_ALL_CAR_CLASSES_QUERY = "SELECT class FROM car_class";

	public static final String GET_ALL_REGISTERED_CAR_NAMES_QUERY = "SELECT DISTINCT car_name.name FROM car, name_model, car_name "
			+ "WHERE car.manuf_model_id = name_model.id AND car_name.id = name_model.id_name ORDER BY car_name.name";

	public static final String GET_AVAIALABLE_CARS_QUERY = "SELECT car.id, car_name.name, car_model.model, car_class.class, car.state_number, "
			+ "car.year, car.rent_value_per_day, car.equipment_information, car.air_conditioner, car.navigation "
			+ "FROM car, name_model, car_name, car_model, car_class "
			+ "WHERE car.manuf_model_id = name_model.id AND car_name.id = name_model.id_name "
			+ "AND car_model.id = name_model.id_model AND car_class.id = name_model.id_class "
			+ "%CAR_NAMES% %CAR_CLASSES% ORDER BY name, %orderBy% LIMIT ? OFFSET ?";

	public static final String GET_CARS_IN_ORDERS_QUERY = "SELECT car.id, car_name.name, car_model.model, car_class.class, car.state_number, "
			+ "car.year, car.rent_value_per_day, car.equipment_information, car.air_conditioner, car.navigation "
			+ "FROM car, name_model, car_name, car_model, car_class "
			+ "WHERE car.manuf_model_id = name_model.id AND car_name.id = name_model.id_name "
			+ "AND car_model.id = name_model.id_model AND car_class.id = name_model.id_class "
			+ "AND car.id IN (SELECT id_car FROM \"order\" WHERE status <= 2) "
			+ "%CAR_NAMES% %CAR_CLASSES% ORDER BY name, %orderBy% LIMIT ? OFFSET ?";

	public static final String COUNT_AVAIALABLE_CARS_QUERY = "SELECT count(*) FROM car, name_model, car_name, car_model, car_class "
			+ "WHERE car.manuf_model_id = name_model.id AND car_name.id = name_model.id_name "
			+ "AND car_model.id = name_model.id_model AND car_class.id = name_model.id_class "
			+ "%CAR_NAMES% %CAR_CLASSES%";

	public static final String COUNT_CARS_IN_ORDER_QUERY = "SELECT count(*) FROM car, name_model, car_name, car_model, car_class "
			+ "WHERE car.manuf_model_id = name_model.id AND car_name.id = name_model.id_name "
			+ "AND car_model.id = name_model.id_model AND car_class.id = name_model.id_class "
			+ "AND car.id IN (SELECT id_car FROM \"order\" WHERE status <= 2) %CAR_NAMES% %CAR_CLASSES%";

	public static final String CREATE_CAR_QUERY = "INSERT INTO car (manuf_model_id, state_number, year, rent_value_per_day, equipment_information, air_conditioner, navigation) values((SELECT name_model.id "
			+ "FROM name_model, car_name, car_model, car_class WHERE name_model.id_name = car_name.id "
			+ "AND name_model.id_model = car_model.id AND name_model.id_class = car_class.id AND car_name.name = ? AND car_model.model = ?),?,?,?,?,?,?)";

	public static final String UPDATE_CAR_QUERY = "UPDATE car SET manuf_model_id =(SELECT name_model.id "
			+ "FROM name_model, car_name, car_model, car_class WHERE name_model.id_name = car_name.id "
			+ "AND name_model.id_model = car_model.id AND name_model.id_class = car_class.id "
			+ "AND car_name.name = ? AND car_model.model = ?), state_number = ?, year = ?, rent_value_per_day = ?, equipment_information = ?, air_conditioner = ?, navigation = ? WHERE id = ?";

	public static final String DELETE_CAR_QUERY = "DELETE FROM car WHERE car.id = ?";
}
