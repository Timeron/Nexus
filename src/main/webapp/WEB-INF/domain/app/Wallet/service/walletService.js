var app = angular.module("WalletService", ['ngResource']);

app.factory("AddAccount", function($resource){
	return $resource("/nexus/v1/wallet/addAccount", 
			{}, 
			{query: { method: "POST", isArray: false }}
		);
});

app.factory("AddNewRecord", function($resource){
	return $resource("/nexus/v1/wallet/addNewRecord", 
			{}, 
			{query: { method: "POST", isArray: false }}
		);
});

app.factory("GetAllRecordTypes", function($resource){
	return $resource("/nexus/v1/wallet/getAllRecordTypes", 
			{}, 
			{query: { method: "GET", isArray: true }}
		);
});

app.factory("GetAllUserAccounts", function($resource){
	return $resource("/nexus/v1/wallet/getAllUserAccounts", 
			{}, 
			{query: { method: "GET", isArray: true }}
		);
});