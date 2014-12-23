package com.nexus.controller.wallet.form;

import com.nexus.dao.entity.WalletType;
import com.nexus.form.MainForm;

public class WalletAddTypeResultForm extends MainForm{

	private WalletType walletType;

	public WalletType getWalletType() {
		return walletType;
	}

	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}
	
}
