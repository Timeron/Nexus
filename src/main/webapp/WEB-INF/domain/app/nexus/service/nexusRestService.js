var app = angular.module("NexusService", ['ngResource']);

app.factory("AddApplication", function($resource){
	return $resource("/nexus/v1/nx/addApp", 
			{}, 
			{query: { method: "POST", isArray: false }
	});
});

app.factory("GetApplication", function($resource){
	return $resource("/nexus/v1/nx/getApp", 
			{}, 
			{query: { method: "POST", isArray: false }
	});
});

app.factory("GetUsersToManageAccessToApplication", function($resource){
	return $resource("/nexus/v1/nx/getUsersToManageAccessToApplication", 
			{}, 
			{query: { method: "POST", isArray: false }
	});
});