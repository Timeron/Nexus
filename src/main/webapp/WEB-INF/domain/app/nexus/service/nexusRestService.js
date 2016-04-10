var app = angular.module("NexusService", ['ngResource']);

app.factory("AddApplication", function($resource){
	return $resource("/nexus/v1/nx/addApp", 
			{}, 
			{query: { method: "POST", isArray: false }
	});
});