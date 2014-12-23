package com.nexus.controller.wallet.form;

import java.util.List;

import com.nexus.dao.entity.WalletAccount;
import com.nexus.dao.entity.WalletRecord;
import com.nexus.form.MainForm;

public class WalletAccountForm extends MainForm{

	private WalletAccount walletAccount;
	private List<WalletRecord> walletRecords;
	private WalletRecord newRecord;

	public List<WalletRecord> getWalletRecords() {
		return walletRecords;
	}

	public void setWalletRecords(List<WalletRecord> walletRecords) {
		this.walletRecords = walletRecords;
	}

	public WalletRecord getNewRecord() {
		return newRecord;
	}

	public void setNewRecord(WalletRecord newRecord) {
		this.newRecord = newRecord;
	}

	public WalletAccount getWalletAccount() {
		return walletAccount;
	}

	public void setWalletAccount(WalletAccount walletAccount) {
		this.walletAccount = walletAccount;
	}
	
	
	
}
