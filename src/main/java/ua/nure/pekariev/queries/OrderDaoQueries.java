package ua.nure.pekariev.queries;

public final class OrderDaoQueries {

	public static final String INSERT_NEW_ORDER_QUERY = "INSERT INTO \"order\"(id_user, id_car, starts, expires, total_rent_value, days_amount, driver) "
			+ "VALUES(?,?,?,?,?,?,?)";

	public static final String GET_ORDER_BY_ID_QUERY = "SELECT \"order\".id, account.first_name, account.last_name, account.email, account.passport_data, account.address, account.password, account.phone,account.active, "
			+ "role.id AS role_id, role.role, "
			+ "car_name.name, car_model.model, car_class.class, car.state_number, car.year, car.rent_value_per_day, car.equipment_information,car.air_conditioner, car.navigation, "
			+ "\"order\".created, \"order\".confirmed, \"order\".starts, \"order\".expires, \"order\".total_rent_value,\"order\".status, \"order\".status_comment, "
			+ "\"order\".driver, \"order\".days_amount, \"order\".paid "
			+ "FROM \"order\", account, car, name_model, car_name, car_model, car_class, account_role, role "
			+ "WHERE \"order\".id_user = account.id "
			+ "AND \"order\".id_car = car.id AND car.manuf_model_id = name_model.id AND car_name.id = name_model.id_name "
			+ "AND account_role.id_account = account.id AND account_role.id_role = role.id "
			+ "AND car_model.id = name_model.id_model AND car_class.id = name_model.id_class AND \"order\".id = ?";

	public static final String GET_ORDER_WITHIN_DATES_QUERY = "SELECT \"order\".id, account.first_name, account.last_name, account.email, account.passport_data, account.address, account.password, account.phone,account.active, role.id AS role_id, role.role, car_name.name, car_model.model, car_class.class, car.state_number, car.year, car.rent_value_per_day, car.equipment_information,car.air_conditioner, car.navigation, \"order\".created, \"order\".confirmed, \"order\".starts, \"order\".expires, \"order\".total_rent_value,\"order\".status, \"order\".status_comment, \"order\".driver, \"order\".days_amount, \"order\".paid FROM \"order\", account, car, name_model, car_name, car_model, car_class, account_role, role  WHERE \"order\".id_user = account.id AND \"order\".id_car = car.id AND car.manuf_model_id = name_model.id AND car_name.id = name_model.id_name AND account_role.id_account = account.id AND account_role.id_role = role.id AND car_model.id = name_model.id_model AND car_class.id = name_model.id_class AND \"order\".id_car = ? AND ((? BETWEEN \"order\".starts AND \"order\".expires) OR (? BETWEEN  \"order\".starts AND \"order\".expires) OR (\"order\".starts BETWEEN ? AND ? )) AND \"order\".status <= '2'";

	public static final String GET_ORDERS_WITH_STATUS_QUERY = "SELECT \"order\".id, account.first_name, account.last_name, account.email, account.passport_data, account.address, account.password, account.phone,account.active, role.id AS role_id, role.role, car_name.name, car_model.model, car_class.class, car.state_number, car.year, car.rent_value_per_day, car.equipment_information,car.air_conditioner, car.navigation, \"order\".created, \"order\".confirmed, \"order\".starts, \"order\".expires, \"order\".total_rent_value, \"order\".status, \"order\".status_comment, \"order\".driver, \"order\".days_amount, \"order\".paid FROM \"order\", account, car, name_model, car_name, car_model, car_class, account_role, role WHERE \"order\".id_user = account.id AND \"order\".id_car = car.id AND car.manuf_model_id = name_model.id AND car_name.id = name_model.id_name AND account_role.id_account = account.id AND account_role.id_role = role.id AND car_model.id = name_model.id_model AND car_class.id = name_model.id_class AND \"order\".status = ? %CAR_NAMES% %CAR_CLASSES% ORDER BY paid, %orderBy% LIMIT ? OFFSET ? ";

	public static final String GET_ALL_ORDERS = "SELECT \"order\".id, account.first_name, account.last_name, account.email, account.passport_data, account.address, account.password, account.phone,account.active, role.id AS role_id, role.role, car_name.name, car_model.model, car_class.class, car.state_number, car.year, car.rent_value_per_day, car.equipment_information,car.air_conditioner, car.navigation, \"order\".created, \"order\".confirmed, \"order\".starts, \"order\".expires, \"order\".total_rent_value, \"order\".status, \"order\".status_comment, \"order\".driver, \"order\".days_amount, \"order\".paid FROM \"order\", account, car, name_model, car_name, car_model, car_class, account_role, role WHERE \"order\".id_user = account.id AND \"order\".id_car = car.id AND car.manuf_model_id = name_model.id AND car_name.id = name_model.id_name AND account_role.id_account = account.id AND account_role.id_role = role.id AND car_model.id = name_model.id_model AND car_class.id = name_model.id_class %CAR_NAMES% %CAR_CLASSES% ORDER BY status, paid, %orderBy% LIMIT ? OFFSET ? ";

	public static final String UPDATE_ORDER_STATUS_QUERY = "UPDATE \"order\" SET status = ? WHERE id = ? ";

	public static final String CONFIRMATION_DATE_ORDER_QUERY = "UPDATE \"order\" SET confirmed = now() WHERE id = ? ";

	public static final String UPDATE_ORDER_DECLINE_REASON_QUERY = "UPDATE \"order\" SET status_comment = ? WHERE id = ? ";

	public static final String UPDATE_FINISHED_ORDERS_QUERY = "UPDATE \"order\" SET status = '4' WHERE expires < now() AND status <= 2";

	public static final String SELECT_ACTIVE_ORDERS_FOR_USER_QUERY = "SELECT \"order\".id, account.first_name, account.last_name, account.email, account.passport_data, account.address, account.password, account.phone,account.active, role.id AS role_id, role.role, car_name.name, car_model.model, car_class.class, car.state_number, car.year, car.rent_value_per_day, car.equipment_information,car.air_conditioner, car.navigation, \"order\".created, \"order\".confirmed, \"order\".starts, \"order\".expires, \"order\".total_rent_value, \"order\".status, \"order\".status_comment, \"order\".driver, \"order\".days_amount, \"order\".paid  FROM \"order\", account, car, name_model, car_name, car_model, car_class, account_role, role WHERE \"order\".id_user = account.id AND \"order\".id_car = car.id AND car.manuf_model_id = name_model.id AND car_name.id = name_model.id_name AND account_role.id_account = account.id AND account_role.id_role = role.id AND car_model.id = name_model.id_model AND car_class.id = name_model.id_class AND account.id = ? AND status <= 4 ORDER BY %orderBy% LIMIT ? OFFSET ?";

	public static final String SELECT_HISTORY_ORDERS_FOR_USER_QUERY = "SELECT \"order\".id, account.first_name, account.last_name, account.email, account.passport_data, account.address, account.password, account.phone,account.active, role.id AS role_id, role.role, car_name.name, car_model.model, car_class.class, car.state_number, car.year, car.rent_value_per_day, car.equipment_information,car.air_conditioner, car.navigation, \"order\".created, \"order\".confirmed, \"order\".starts, \"order\".expires, \"order\".total_rent_value, \"order\".status, \"order\".status_comment, \"order\".driver, \"order\".days_amount, \"order\".paid  FROM \"order\", account, car, name_model, car_name, car_model, car_class, account_role, role WHERE \"order\".id_user = account.id AND \"order\".id_car = car.id AND car.manuf_model_id = name_model.id AND car_name.id = name_model.id_name AND account_role.id_account = account.id AND account_role.id_role = role.id AND car_model.id = name_model.id_model AND car_class.id = name_model.id_class AND account.id = ? AND status >= 8 ORDER BY %orderBy% LIMIT ? OFFSET ?";

	public static final String COUNT_ORDERS_WITH_STATUS_QUERY = "SELECT count(*) FROM \"order\" WHERE \"order\".status = ?";

	public static final String COUNT_ACTIVE_ORDERS_QUERY = "SELECT count(*) FROM \"order\" WHERE \"order\".status <= 4 AND id_user = ?";

	public static final String COUNT_HISTORY_ORDERS_QUERY = "SELECT count(*) FROM \"order\" WHERE \"order\".status >= 8 AND id_user = ?";

	public static final String ASSIGNE_ORDER_AS_PAID_QUEY = "UPDATE \"order\" SET paid = 'TRUE' WHERE id = ? ";

	public static final String GET_ORDERS_FOR_CAR_QUERY = "SELECT \"order\".id, account.first_name, account.last_name, account.email, account.passport_data, account.address, account.password, account.phone,account.active, role.id AS role_id, role.role, car_name.name, car_model.model, car_class.class, car.state_number, car.year, car.rent_value_per_day, car.equipment_information,car.air_conditioner, car.navigation, \"order\".created, \"order\".confirmed, \"order\".starts, \"order\".expires, \"order\".total_rent_value,\"order\".status, \"order\".status_comment, \"order\".driver, \"order\".days_amount, \"order\".paid FROM \"order\", account, car, name_model, car_name, car_model, car_class, account_role, role WHERE \"order\".id_user = account.id AND \"order\".id_car = car.id AND car.manuf_model_id = name_model.id AND car_name.id = name_model.id_name AND account_role.id_account = account.id AND account_role.id_role = role.id AND car_model.id = name_model.id_model AND car_class.id = name_model.id_class AND car.id = ? AND \"order\".status<='4'";
}
