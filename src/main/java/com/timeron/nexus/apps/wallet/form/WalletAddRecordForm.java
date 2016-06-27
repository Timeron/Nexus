package com.timeron.nexus.apps.wallet.form;

import java.util.List;

import com.timeron.NexusDatabaseLibrary.Entity.WalletAccount;
import com.timeron.NexusDatabaseLibrary.Entity.WalletRecord;
import com.timeron.NexusDatabaseLibrary.Entity.WalletType;
import com.timeron.nexus.apps.form.MainForm;

public class WalletAddRecordForm extends MainForm {

	private WalletRecord walletRecord;
	private Integer walletTypeId;
	private WalletAccount walletAccount;
	private Integer walletAccountId;
	private Integer destinationAccountId;
	private String date;
	
	private List<WalletType> walletTypes;
	private List<WalletAccount> walletAccounts;
	
	public WalletRecord getWalletRecord() {
		return walletRecord;
	}
	public void setWalletRecord(WalletRecord walletRecord) {
		this.walletRecord = walletRecord;
	}
	public List<WalletType> getWalletTypes() {
		return walletTypes;
	}
	public void setWalletTypes(List<WalletType> walletTypes) {
		this.walletTypes = walletTypes;
	}
	public List<WalletAccount> getWalletAccounts() {
		return walletAccounts;
	}
	public void setWalletAccounts(List<WalletAccount> walletAccounts) {
		this.walletAccounts = walletAccounts;
	}
	public Integer getWalletTypeId() {
		return walletTypeId;
	}
	public void setWalletTypeId(Integer walletTypeId) {
		this.walletTypeId = walletTypeId;
	}
	public WalletAccount getWalletAccount() {
		return walletAccount;
	}
	public void setWalletAccount(WalletAccount walletAccount) {
		this.walletAccount = walletAccount;
	}
	public Integer getWalletAccountId() {
		return walletAccountId;
	}
	public void setWalletAccountId(Integer walletAccountId) {
		this.walletAccountId = walletAccountId;
	}
	public Integer getDestinationAccountId() {
		return destinationAccountId;
	}
	public void setDestinationAccountId(Integer destinationAccountId) {
		this.destinationAccountId = destinationAccountId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;		
	}
	

}
