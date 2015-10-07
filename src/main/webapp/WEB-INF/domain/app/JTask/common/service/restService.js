var app = angular.module("jTaskService", []);

app.factory("Version", function($resource) {
	return $resource("/nexus/v1/jt/appVersion", 
			{}, 
			{query: { method: "GET", isArray: false }
	});
});

app.factory("AllProjectTask", function($resource) {
	return $resource("/nexus/v1/jt/allProjectTask", 
			{}, 
			{query: { method: "GET", isArray: true }
	});
});

app.factory("UpdateTask", function($resource){
	return $resource("/nexus/v1/jt/updateTask", 
			{},
			{query: { method: "POST", isArray: false }	
	});
});

app.factory("Users", function($resource) {
	return $resource("/nexus/v1/jt/allUsers", 
			{}, 
			{query: { method: "GET", isArray: true }
	});
});

app.factory("AssignTaskToUser", function($resource){
	return $resource("/nexus/v1/jt/assignTaskToUser", 
			{}, 
			{query: { method: "POST", isArray: false }
	});
});