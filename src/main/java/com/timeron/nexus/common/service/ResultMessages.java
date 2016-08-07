package com.timeron.nexus.common.service;

public class ResultMessages {


	public static final String RECORD_ADDED = "Rekord pomyślnie dodany";
	public static final String RECORD_ADD_ERROR = "Operacja nie zostało dodana";
	
	public static final String CANNOT_RECEIVE_USER = "Nie udało się pobrać użytkowników";
	
	public static final String NO_CONNECTION = "CONNECTION_ERROR";
	public static final String DATABASE_ISSUE = "Błąd bazy danych";
	
	/**
	 * Account
	 */
	public static final String RECORD_ADD_NO_ACCOUNT = "Brakuje danych o koncie";
	public static final String ACCOUNT_ADDED = "Konto dodane pomyślnie";
	public static final String ACCOUNT_ADD_ERROR = "Konto nie zostało dodane";

	public static final String PERSON_NOT_DETECTED = "Błąd! Proszę się przelogować";
	public static final String PERSON_NOT_EXIST = "Błąd! Użytkownik nie istnieje w bazie danych";
	
	/**
	 * Application
	 */
	public static final String APPLICATION_CANNOT_BE_ADDED = "aplikacja nie dodana! Wystąpił błąd";
	public static final String USER_CANNOT_BE_CONNECTED_TO_APPLICATION = "nie udało się połączyć usera z aplikacją.";
	
}
