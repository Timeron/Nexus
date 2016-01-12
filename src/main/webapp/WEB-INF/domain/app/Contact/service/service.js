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

app.factory("GetContactDetails", function($resource){
	return $resource("/nexus/v1/contact/getContactDetails", 
			{}, 
			{query: { method: "GET", isArray: false }}
		);
});

app.factory("UpdateContact", function($resource){
	return $resource("/nexus/v1/contact/updateContact", 
			{}, 
			{query: { method: "POST", isArray: false }}
		);
});

app.factory("AddEvent", function($resource){
	return $resource("/nexus/v1/contact/addEvent", 
			{}, 
			{query: { method: "POST", isArray: false }}
		);
});