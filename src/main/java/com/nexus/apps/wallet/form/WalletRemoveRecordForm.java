package com.nexus.apps.wallet.form;

import com.nexus.apps.form.MainForm;
import com.nexus.dao.entity.WalletRecord;

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
