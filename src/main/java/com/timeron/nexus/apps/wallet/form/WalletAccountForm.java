package com.timeron.nexus.apps.wallet.form;

import java.util.List;

import com.timeron.NexusDatabaseLibrary.Entity.WalletAccount;
import com.timeron.NexusDatabaseLibrary.Entity.WalletRecord;
import com.timeron.nexus.apps.form.MainForm;

public class WalletAccountForm extends MainForm{

	private WalletAccount walletAccount;
	private List<WalletAccount> accounts;
	private List<WalletRecord> walletRecords;
	private WalletRecord newRecord;
	private String chart;
	private float sum = 0;

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

	public String getChart() {
		return chart;
	}

	public void setChart(String chart) {
		this.chart = chart;
	}

	public float getSum() {
		return sum;
	}

	public void setSum(float sum) {
		this.sum = sum;
	}

	public List<WalletAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<WalletAccount> accounts) {
		this.accounts = accounts;
	}
	
	
	
}
