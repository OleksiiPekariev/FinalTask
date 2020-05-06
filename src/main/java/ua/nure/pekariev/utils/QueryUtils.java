package ua.nure.pekariev.utils;

public class QueryUtils {

	public static String generateCarQueryWithParameters(String queryToReplace, String carNameSelection,
			String carClassesSelection, String sortBy) {
		String newQuery = queryToReplace.replace("%CAR_NAMES%", carNameSelection).replace("%CAR_CLASSES%",
				carClassesSelection);
		try {
			switch (sortBy) {
			case "model_DESC":
				return newQuery.replace("%orderBy%", "model DESC");

			case "rent_value_ASC":
				return newQuery.replace("%orderBy%", "rent_value_per_day ASC");

			case "rent_value_DESC":
				return newQuery.replace("%orderBy%", "rent_value_per_day DESC");

			default:
				return newQuery.replace("%orderBy%", "model ASC");
			}
		} catch (NullPointerException e) {
			return newQuery.replace("%orderBy%", "model ASC");
		}
	}

	public static String generateOrderQueryWithParameters(String queryToReplace, String carNameSelection,
			String carClassesSelection, String sortBy) {
		String newQuery = queryToReplace.replace("%CAR_NAMES%", carNameSelection).replace("%CAR_CLASSES%",
				carClassesSelection);
		try {
			switch (sortBy) {
			case "lastName_ASC":
				return newQuery.replace("%orderBy%", "account.last_name ASC");

			case "lastName_DESC":
				return newQuery.replace("%orderBy%", "account.last_name DESC");

			case "expDate_ASC":
				return newQuery.replace("%orderBy%", "expires ASC");

			case "expDate_DESC":
				return newQuery.replace("%orderBy%", "expires DESC");

			case "startDate_ASC":
				return newQuery.replace("%orderBy%", "starts ASC");

			case "startDate_DESC":
				return newQuery.replace("%orderBy%", "starts DESC");

			default:
				return newQuery.replace("%orderBy%", "expires ASC");
			}
		} catch (NullPointerException e) {
			return newQuery.replace("%orderBy%", "expires ASC");
		}
	}

	public static String orderByForClient(String query, String sortBy) {
		try {
			switch (sortBy) {
			case "carName_ASC":
				return query.replace("%orderBy%", "car_name ASC");

			case "carName_DESC":
				return query.replace("%orderBy%", "car_name DESC");

			case "expDate_ASC":
				return query.replace("%orderBy%", "expires ASC");

			case "expDate_DESC":
				return query.replace("%orderBy%", "expires DESC");

			case "startDate_ASC":
				return query.replace("%orderBy%", "starts ASC");

			case "startDate_DESC":
				return query.replace("%orderBy%", "starts DESC");

			default:
				return query.replace("%orderBy%", "expires ASC");
			}
		} catch (NullPointerException e) {
			return query.replace("%orderBy%", "expires ASC");
		}
	}

	public static String generateCarQueryWithParameters(String queryToReplace, String carNameSelection,
			String carClassesSelection) {
		String newQuery = queryToReplace.replace("%CAR_NAMES%", carNameSelection).replace("%CAR_CLASSES%",
				carClassesSelection);
		return newQuery;
	}

	public static String getSelectionQueryPart(String columnName, String[] parametersArray) {
		StringBuilder builder = new StringBuilder();
		try {
			if (parametersArray.length != 0) {
				builder.append("AND (");
				for (int i = 0; i < parametersArray.length; i++) {
					builder.append(columnName).append(" = '").append(parametersArray[i]).append("'");
					if (i != parametersArray.length - 1) {
						builder.append(" OR ");
					} else {
						builder.append(")");
					}
				}
			}
		} catch (NullPointerException e) {
			return builder.toString();
		}
		return builder.toString();

	}

}
