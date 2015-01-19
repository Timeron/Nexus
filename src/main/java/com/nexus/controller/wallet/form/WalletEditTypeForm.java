package com.nexus.controller.wallet.form;

import java.util.List;

import com.nexus.dao.entity.WalletType;

public class WalletEditTypeForm {

	private WalletType walletType;
	private WalletType newWalletType;
	private List<WalletType> walletTypes;
	private Integer newWalletTypeParentTypeId;
	
	public WalletType getWalletType() {
		return walletType;
	}
	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}
	public WalletType getNewWalletType() {
		return newWalletType;
	}
	public void setNewWalletType(WalletType newWalletType) {
		this.newWalletType = newWalletType;
	}
	public List<WalletType> getWalletTypes() {
		return walletTypes;
	}
	public void setWalletTypes(List<WalletType> walletTypes) {
		this.walletTypes = walletTypes;
	}
	public Integer getNewWalletTypeParentTypeId() {
		return newWalletTypeParentTypeId;
	}
	public void setNewWalletTypeParentTypeId(Integer newWalletTypeParentTypeId) {
		this.newWalletTypeParentTypeId = newWalletTypeParentTypeId;
	}
	
	
}
