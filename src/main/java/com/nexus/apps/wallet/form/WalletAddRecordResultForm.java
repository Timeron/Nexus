package com.nexus.apps.wallet.form;

import com.nexus.apps.form.MainForm;
import com.timeron.NexusDatabaseLibrary.Entity.WalletRecord;

public class WalletAddRecordResultForm extends MainForm{
	
	private WalletRecord walletRecord;

	public WalletRecord getWalletRecord() {
		return walletRecord;
	}

	public void setWalletRecord(WalletRecord walletRecord) {
		this.walletRecord = walletRecord;
	}
	
	

}
