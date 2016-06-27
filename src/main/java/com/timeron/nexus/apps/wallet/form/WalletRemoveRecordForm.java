package com.timeron.nexus.apps.wallet.form;

import com.timeron.NexusDatabaseLibrary.Entity.WalletRecord;
import com.timeron.nexus.apps.form.MainForm;

public class WalletRemoveRecordForm extends MainForm{
	
	private WalletRecord walletRecord;
	private boolean remove;
	
	public WalletRecord getWalletRecord() {
		return walletRecord;
	}
	public void setWalletRecord(WalletRecord walletRecord) {
		this.walletRecord = walletRecord;
	}
	public boolean isRemove() {
		return remove;
	}
	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	
	
}
