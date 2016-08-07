package com.timeron.nexus.apps.wallet.constant;

public enum TimePeriod {

	DAY(1), WEEK(2), MONTH(3), YEAR(4), All(5);

	private int value;

	private TimePeriod(int value) {
		this.value = value;
	}
}
