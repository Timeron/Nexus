package com.timeron.nexus.apps.wallet.rest.helper;

import java.security.Principal;
import java.util.List;

import com.timeron.nexus.apps.wallet.service.dto.PieChartDTO;
import com.timeron.nexus.apps.wallet.service.dto.SumForAccountByType;

public interface WalletRestServiceHelperInterface {

	/**
	 * Zwraca sumy rekordów (przychód lub wydatek) dla poszczególnych typów pod wskazanym kontem. 
	 * @param sumForAccountByType
	 * 							- określa konto i rodzaj sumy (przychód lub wydatek)
	 * @param principal
	 * 							- jaki user sprawdza
	 * @return
	 */
	List<PieChartDTO> getSumForAccountByParentType(
			SumForAccountByType sumForAccountByType, Principal principal);

}
