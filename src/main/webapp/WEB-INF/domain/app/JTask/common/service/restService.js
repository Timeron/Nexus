var app = angular.module("jTaskService", []);

app.factory("Version", function($resource) {
	return $resource("/timeron-nexus/v1/jt/appVersion", 
			{}, 
			{query: { method: "GET", isArray: false }
	});
});

app.factory("AllProjectTask", function($resource) {
	return $resource("/timeron-nexus/v1/jt/allProjectTask", 
			{}, 
			{query: { method: "GET", isArray: true }
	});
});

app.factory("UpdateTask", function($resource){
	return $resource("/timeron-nexus/v1/jt/updateTask", 
			{},
			{query: { method: "POST", isArray: false }	
	});
});