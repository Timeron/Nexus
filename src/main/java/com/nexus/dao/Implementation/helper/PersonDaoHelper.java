package com.nexus.dao.Implementation.helper;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.nexus.dao.Implementation.Enum.QueryComparator;
import com.nexus.dao.Implementation.Enum.QuerySeparator;
import com.nexus.dao.entity.NexusPerson;

public class PersonDaoHelper {

	static Logger log = Logger.getLogger(PersonDaoHelper.class.getName());

	public String buildQuerySearchPerson(NexusPerson nexusPerson) {
		String query = "FROM NexusPerson";
		Map<String, String> attributes = new TreeMap<String, String>();

		if (nexusPerson.getAddress() != null
				&& !nexusPerson.getAddress().isEmpty()) {
			attributes.put("address", nexusPerson.getAddress());
		}
		if (nexusPerson.getBirthday() != null) {
			attributes.put("birthday", nexusPerson.getBirthday().toString());
		}
		if (nexusPerson.getCity() != null && !nexusPerson.getCity().isEmpty()) {
			attributes.put("city", nexusPerson.getCity());
		}
		if (nexusPerson.getCountry() != null
				&& !nexusPerson.getCountry().isEmpty()) {
			attributes.put("country", nexusPerson.getCountry());
		}
		if (nexusPerson.getEmail() != null && !nexusPerson.getEmail().isEmpty()) {
			attributes.put("email", nexusPerson.getEmail());
		}
		if (nexusPerson.getFirstName() != null
				&& !nexusPerson.getFirstName().isEmpty()) {
			attributes.put("firstName", nexusPerson.getFirstName());
		}
		if (nexusPerson.getLastName() != null
				&& !nexusPerson.getLastName().isEmpty()) {
			attributes.put("lastName", nexusPerson.getLastName());
		}
		if (nexusPerson.getNameDay() != null) {
			attributes.put("nameDay", nexusPerson.getNameDay().toString());
		}
		if (nexusPerson.getPseudo() != null
				&& !nexusPerson.getPseudo().isEmpty()) {
			attributes.put("pseudo", nexusPerson.getPseudo());
		}

		query += createQuery(attributes, QuerySeparator.AND,
				QueryComparator.EQUALS);
		log.info(query);
		return query;
	}

	private String createQuery(Map<String, String> attributesMap,
			QuerySeparator separator, QueryComparator comparator) {
		String query = "";
		String comparatorStr = "";
		switch (comparator) {
		case EQUALS:
			comparatorStr = " = ";
			break;
		case LIKE:
			comparatorStr = " like ";
			break;
		case DIFFERENT:
			comparatorStr = " <> ";
			break;
		case BIGER:
			comparatorStr = " > ";
			break;
		case SMALLER:
			comparatorStr = " < ";
			break;
		case BIGEREQUALS:
			comparatorStr = " => ";
			break;
		case SMALLEREQUALS:
			comparatorStr = " =< ";
			break;
		}

		boolean firstAtr = true;

		if (!attributesMap.isEmpty()) {
			query += " WHERE ";
			for (Entry<String, String> attribute : attributesMap.entrySet()) {
				if (!firstAtr) {
					query += " " + separator;
				}
				firstAtr = false;
				query += attribute.getKey() + comparatorStr + "'"
						+ attribute.getValue() + "'";

			}
		}
		return query;
	}

	public String buildGetAllPerson() {
		String query = "FROM NexusPerson";
		return query;
	}
}
