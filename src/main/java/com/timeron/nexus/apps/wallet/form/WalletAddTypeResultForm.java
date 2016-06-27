package com.timeron.nexus.apps.wallet.form;

import com.timeron.NexusDatabaseLibrary.Entity.WalletType;
import com.timeron.nexus.apps.form.MainForm;

public class WalletAddTypeResultForm extends MainForm{

	private WalletType walletType;

	public WalletType getWalletType() {
		return walletType;
	}

	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}
	
}
