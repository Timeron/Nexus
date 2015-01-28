package com.nexus.apps.wallet.form;

import java.util.List;

import com.nexus.apps.form.MainForm;
import com.timeron.NexusDatabaseLibrary.Entity.WalletAccount;
import com.timeron.NexusDatabaseLibrary.Entity.WalletRecord;
import com.timeron.NexusDatabaseLibrary.Entity.WalletType;

public class WalletEditRecordForm extends MainForm {

	private WalletRecord walletRecord;
	private List<WalletAccount> walletAccount;
	private Integer walletTypeId;
	private Integer walletAccountId;
	private Integer destinationAccountId;
	private List<WalletType> walletTypes;
	
	public WalletRecord getWalletRecord() {
		return walletRecord;
	}
	public void setWalletRecord(WalletRecord walletRecord) {
		this.walletRecord = walletRecord;
	}
	public List<WalletAccount> getWalletAccount() {
		return walletAccount;
	}
	public void setWalletAccount(List<WalletAccount> walletAccount) {
		this.walletAccount = walletAccount;
	}
	public Integer getWalletTypeId() {
		return walletTypeId;
	}
	public void setWalletTypeId(Integer walletTypeId) {
		this.walletTypeId = walletTypeId;
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
	public List<WalletType> getWalletTypes() {
		return walletTypes;
	}
	public void setWalletTypes(List<WalletType> walletTypes) {
		this.walletTypes = walletTypes;
	}
	
}
