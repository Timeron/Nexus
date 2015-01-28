package com.nexus.apps.wallet.form;

import java.util.List;

import com.google.gson.Gson;
import com.nexus.apps.form.MainForm;
import com.timeron.NexusDatabaseLibrary.Entity.WalletAccount;
import com.timeron.NexusDatabaseLibrary.Entity.WalletRecord;

public class WalletAccountForm extends MainForm{

	private WalletAccount walletAccount;
	private List<WalletRecord> walletRecords;
	private WalletRecord newRecord;
	private String draft;

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

	public String getDraft() {
		return draft;
	}

	public void setDraft(String draft) {
		this.draft = draft;
	}

	
	
}
