package com.nexus.controller.wallet.form;

import java.util.List;

import com.nexus.dao.DaoImp;
import com.nexus.dao.entity.WalletAccount;
import com.nexus.dao.entity.WalletRecord;
import com.nexus.dao.entity.WalletType;

public class WalletEditRecordForm extends DaoImp {

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
