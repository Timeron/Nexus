package com.nexus.controller.wallet.form;

import com.nexus.dao.entity.WalletRecord;
import com.nexus.form.MainForm;

public class WalletAddRecordResultForm extends MainForm{
	
	private WalletRecord walletRecord;

	public WalletRecord getWalletRecord() {
		return walletRecord;
	}

	public void setWalletRecord(WalletRecord walletRecord) {
		this.walletRecord = walletRecord;
	}
	
	

}
