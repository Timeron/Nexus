package com.nexus.apps.wallet.form;

import com.nexus.apps.form.MainForm;
import com.timeron.NexusDatabaseLibrary.Entity.WalletType;

public class WalletAddTypeResultForm extends MainForm{

	private WalletType walletType;

	public WalletType getWalletType() {
		return walletType;
	}

	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}
	
}
