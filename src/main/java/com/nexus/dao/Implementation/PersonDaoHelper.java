package com.nexus.dao.Implementation;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.nexus.dao.Implementation.Enum.QueryComparator;
import com.nexus.dao.Implementation.Enum.QuerySeparator;
import com.nexus.dao.entity.NexusPerson;

public class PersonDaoHelper {

	public String buildQuerySearchPerson(NexusPerson nexusPerson){
		String query = "FROM NexusPerson WHERE ";
		Map<String, String> attributes = new TreeMap<String, String>();
		
		if(nexusPerson.getAddress()!=null && !nexusPerson.getAddress().isEmpty()){
			attributes.put("Address", nexusPerson.getAddress());
		}
		if(nexusPerson.getBirthday()!=null){
			attributes.put("Birthday", nexusPerson.getBirthday().toString());
		}
		if(nexusPerson.getCity()!=null && !nexusPerson.getCity().isEmpty()){
			attributes.put("City", nexusPerson.getCity());
		}
		if(nexusPerson.getCountry()!=null && !nexusPerson.getCountry().isEmpty()){
			attributes.put("Country", nexusPerson.getCountry());
		}
		if(nexusPerson.getEmail()!=null && !nexusPerson.getEmail().isEmpty()){
			attributes.put("Email", nexusPerson.getEmail());
		}
		if(nexusPerson.getFirstName()!=null && !nexusPerson.getFirstName().isEmpty()){
			attributes.put("FirstName", nexusPerson.getFirstName());
		}
		if(nexusPerson.getLastName()!=null && !nexusPerson.getLastName().isEmpty()){
			attributes.put("LastName", nexusPerson.getLastName());
		}
		if(nexusPerson.getNameDay()!=null){
			attributes.put("NameDay", nexusPerson.getNameDay().toString());
		}
		if(nexusPerson.getPseudo()!=null && !nexusPerson.getPseudo().isEmpty()){
			attributes.put("Pseudo", nexusPerson.getPseudo());
		}
		
		query += createQuery(attributes, QuerySeparator.AND, QueryComparator.EQUALS);
		
		return query;
	}
	
	private String createQuery(Map<String, String> attributesMap, QuerySeparator separator, QueryComparator comparator){
		String query = "";
		String comparatorStr = "";
		switch(comparator){
			case EQUALS : comparatorStr = " = "; 
				break;
			case LIKE : comparatorStr = " like "; 
				break;
			case DIFFERENT : comparatorStr = " <> "; 
				break;
			case BIGER : comparatorStr = " > "; 
				break;
			case SMALLER : comparatorStr = " < "; 
				break;
			case BIGEREQUALS : comparatorStr = " => "; 
				break;
			case SMALLEREQUALS : comparatorStr = " =< "; 
				break;
		}
		
		boolean firstAtr = true;
		
		for (Entry<String, String> attribute : attributesMap.entrySet()) {
			if(firstAtr){
				query += " "+separator;
				firstAtr = false;
			}
			query += attribute.getKey()+" "+comparatorStr;
		}
		query += ";";
		System.out.println(query);
		return query;
	}
}
