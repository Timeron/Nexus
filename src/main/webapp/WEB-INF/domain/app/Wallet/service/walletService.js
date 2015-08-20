var app = angular.module("WalletService", ['ngResource']);

app.factory("AddAccount", function($resource){
	return $resource("/timeron-nexus/v1/wallet/addAccount", 
			{}, 
			{query: { method: "POST", isArray: false }}
		);
});