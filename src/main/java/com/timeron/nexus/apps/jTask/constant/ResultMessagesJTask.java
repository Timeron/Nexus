package com.timeron.nexus.apps.jTask.constant;

import com.timeron.nexus.common.service.ResultMessages;

public class ResultMessagesJTask extends ResultMessages{

	/**
	 * JTask
	 */
	public static final String TASK_NOT_ADDED = "Wystąpił błąd: Task nie dodany!";
	public static final String CANNOT_UPDATE_TASK = "nie udało się uaktualnić taksa";
	public static final String CANNOT_SAVE_HISTORY = "nie udało się zapisać historii";
	
	public static final String PROJECT_NOT_FOUND = "Nie znalezniono projektu";
	public static final String TASK_CANNOT_BE_FOUND_TASK = "Task nie istnieje w bazie";
	public static final String PROJECTS_ARE_DIFFERENT = "nie można wykonać dla różnych projektów";
	
	/**
	 * Release
	 */
	public static final String NEED_PROJECT_SELECTED = "Projekt jest wymagany";
	public static final String VERSION_EMPTY = "Wersja jest wymagana";
	public static final String VERSION_TOO_LONG = "Wersja może mieć tylko 10 znaków";
	
}
