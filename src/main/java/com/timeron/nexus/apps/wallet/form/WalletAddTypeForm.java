package com.timeron.nexus.apps.wallet.form;

import java.util.List;

import com.timeron.NexusDatabaseLibrary.Entity.WalletType;
import com.timeron.nexus.apps.form.MainForm;

public class WalletAddTypeForm extends MainForm{

	private WalletType walletType;
	private List<WalletType> walletTypes;
	private Integer walletTypeId;
	
	public WalletType getWalletType() {
		return walletType;
	}
	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}
	public List<WalletType> getWalletTypes() {
		return walletTypes;
	}
	public void setWalletTypes(List<WalletType> walletTypes) {
		this.walletTypes = walletTypes;
	}
	public Integer getWalletTypeId() {
		return walletTypeId;
	}
	public void setWalletTypeId(Integer walletTypeId) {
		this.walletTypeId = walletTypeId;
	}
	
	
}
