package com.timeron.nexus.apps.wallet.service.interfaces;

import java.util.List;
import java.util.Map;

import com.timeron.nexus.apps.wallet.service.dto.KeyValueListDTO;
import com.timeron.nexus.apps.wallet.service.dto.RecordDTO;

public interface WalletRecordService {

	/**
	 * Wyciąga rekordy przypisane do konkretnego dnia jako mapę dni i listę rekordów
	 * Zakres jeden miesiąc od 1 do ostatniego dnia miesiąca.
	 * 
	 * @param accountId
	 * @param year
	 * @param month
	 * 
	 * @return mapa dni jako Integer i lista rekordów w kolejności typów.
	 */
	List<KeyValueListDTO<Integer, RecordDTO>> getRecordsByDay(int accountId, int year, int month);

}
