var app = angular.module("jTaskService", []);

app.factory("Version", function($resource) {
	return $resource("v1/appVersion", 
			{}, 
			{query: { method: "GET", isArray: false }
	});
});


