var app = angular.module("CarService", ['ngResource']);

app.factory("AddRecord", function($resource){
	return $resource("/nexus/v1/car/addRecord", 
			{}, 
			{query: { method: "POST", isArray: false }}
		);
});

app.factory("GetRecords", function($resource){
	return $resource("/nexus/v1/car/getRecords", 
			{}, 
			{query: { method: "GET", isArray: true }}
		);
});