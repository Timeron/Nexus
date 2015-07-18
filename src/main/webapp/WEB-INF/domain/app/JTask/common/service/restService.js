var app = angular.module("jTaskService", []);

app.factory("Version", function($resource) {
	return $resource("v1/appVersion", 
			{}, 
			{query: { method: "GET", isArray: false }
	});
});

app.factory("AllProjectTask", function($resource) {
	return $resource("v1/allProjectTask", 
			{}, 
			{query: { method: "GET", isArray: true }
	});
});


