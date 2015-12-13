var app = angular.module("ContactService", ['ngResource']);

app.factory("AddContact", function($resource){
	return $resource("/nexus/v1/contact/addContact", 
			{}, 
			{query: { method: "POST", isArray: false }}
		);
});

app.factory("GetContacts", function($resource){
	return $resource("/nexus/v1/contact/getContacts", 
			{}, 
			{query: { method: "GET", isArray: false }}
		);
});