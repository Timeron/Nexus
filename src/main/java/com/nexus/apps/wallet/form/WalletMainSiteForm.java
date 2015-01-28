package com.nexus.apps.wallet.form;

import java.util.List;

import com.timeron.NexusDatabaseLibrary.Entity.WalletAccount;
import com.timeron.NexusDatabaseLibrary.Entity.WalletRecord;

public class WalletMainSiteForm {

	private List<WalletAccount> accounts;
	private List<WalletRecord> records;
	private List<Float> recordsValue;

	public List<WalletAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<WalletAccount> accounts) {
		this.accounts = accounts;
	}

	public List<WalletRecord> getRecords() {
		return records;
	}

	public void setRecords(List<WalletRecord> records) {
		this.records = records;
	}

	public List<Float> getRecordsValue() {
		return recordsValue;
	}

	public void setRecordsValue(List<Float> recordsValue) {
		this.recordsValue = recordsValue;
	}
	
}
