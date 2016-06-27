package com.timeron.nexus.apps.wallet.form;

import com.timeron.NexusDatabaseLibrary.Entity.WalletRecord;
import com.timeron.nexus.apps.form.MainForm;

public class WalletAddRecordResultForm extends MainForm{
	
	private WalletRecord walletRecord;

	public WalletRecord getWalletRecord() {
		return walletRecord;
	}

	public void setWalletRecord(WalletRecord walletRecord) {
		this.walletRecord = walletRecord;
	}
	
	

}
