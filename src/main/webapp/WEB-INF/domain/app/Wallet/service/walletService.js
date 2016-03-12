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

app.factory("UpdateRecord", function($resource){
	return $resource("/nexus/v1/wallet/updateRecord", 
			{}, 
			{query: { method: "POST", isArray: false }}
		);
});

app.factory("GetAllAccountsAndRecords", function($resource){
	return $resource("/nexus/v1/wallet/getAllAccountsAndRecords", 
			{}, 
			{query: { method: "GET", isArray: false }}
		);
});

app.factory("GetAllRecordTypes", function($resource){
	return $resource("/nexus/v1/wallet/getAllRecordTypes", 
			{}, 
			{query: { method: "GET", isArray: true }}
		);
});

app.factory("GetSumForTypeInTypeHierarchy", function($resource){
	return $resource("/nexus/v1/wallet/getSumForTypeInTypeHierarchy", 
			{}, 
			{query: { method: "POST", isArray: true }}
		);
});

app.factory("GetAllUserAccounts", function($resource){
	return $resource("/nexus/v1/wallet/getAllUserAccounts", 
			{}, 
			{query: { method: "GET", isArray: true }}
		);
});

app.factory("GetRecordsForAccountByDayPOST", function($resource){
	return $resource("/nexus/v1/wallet/getRecordsForAccountByDay", 
			{}, 
			{query: { method: "POST", isArray: true }}
		);
});

app.factory("GetSumForAccountByType", function($resource){
	return $resource("/nexus/v1/wallet/getSumForAccountByType", 
			{}, 
			{query: { method: "POST", isArray: true }}
		);
});

app.factory("GetSumForAccountByParentType", function($resource){
	return $resource("/nexus/v1/wallet/getSumForAccountByParentType", 
			{}, 
			{query: { method: "POST", isArray: true }}
		);
});

app.factory("GetSumForTypeForStatistics", function($resource){
	return $resource("/nexus/v1/wallet/getSumForTypeForStatistics", 
			{}, 
			{query: { method: "POST", isArray: true }}
		);
});

//type tab
app.factory("AddType", function($resource){
	return $resource("/nexus/v1/wallet/addType", 
			{},
			{query: {method: "POST", isArray: false}}
		);
});

app.factory("UpdateTypes", function($resource){
	return $resource("/nexus/v1/wallet/updateTypes", 
			{}, 
			{query: {method:"POST", isArray: true}}
		);
});

app.factory("GetTypesValidForParent", function($resource){
	return $resource("/nexus/v1/wallet/getTypesValidForParent", 
			{},
			{query: {method: "GET", isArray: true}}
		);
});

//common Nexus rests
app.factory("CurrentUser", function($resource){
	return $resource("/nexus/v1/currentUser", 
			{}, 
			{query: { method: "GET", isArray: false }}
		);
});

app.factory("CurrentUserPOST", function($resource){
	return $resource("/nexus/v1/currentUserPOST", 
			{}, 
			{query: { method: "POST", isArray: false }}
		);
});



