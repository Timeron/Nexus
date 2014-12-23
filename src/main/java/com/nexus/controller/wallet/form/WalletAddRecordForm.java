package com.nexus.controller.wallet.form;

import java.util.List;

import com.nexus.dao.entity.WalletAccount;
import com.nexus.dao.entity.WalletRecord;
import com.nexus.dao.entity.WalletType;
import com.nexus.form.MainForm;

public class WalletAddRecordForm extends MainForm {

	private WalletRecord walletRecord;
	private Integer walletTypeId;
	private WalletAccount walletAccount;
	private Integer walletAccountId;
	private Integer destinationAccountId;
	
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
	

}
