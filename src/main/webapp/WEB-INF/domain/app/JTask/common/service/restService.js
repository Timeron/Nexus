var app = angular.module("jTaskService", []);

app.factory("Version", function($resource) {
	return $resource("/nexus/v1/jt/appVersion", 
			{}, 
			{query: { method: "GET", isArray: false }
	});
});

app.factory("GetAllTasksInOneProject", function($resource) {
	return $resource("/nexus/v1/jt/getAllTasksInOneProject", 
			{}, 
			{query: { method: "GET", isArray: false }
	});
});

app.factory("AllProjectTasks", function($resource) {
	return $resource("/nexus/v1/jt/allProjectTasks", 
			{}, 
			{query: { method: "GET", isArray: true }
	});
});

app.factory("AddNewTask", function($resource){
	return $resource("/nexus/v1/jt/addTask", 
			{},
			{query: { method: "POST", isArray: false }	
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

app.factory("Histories", function($resource) {
	return $resource("/nexus/v1/jt/historyTask", 
			{}, 
			{query: { method: "GET", isArray: true }
	});
});

app.factory("Notes", function($resource) {
	return $resource("/nexus/v1/jt/notesTask", 
			{}, 
			{query: { method: "GET", isArray: true }
	});
});
//not needed? AllProjectTasks
app.factory("GetAllProjectTasks", function($resource) {
	return $resource("/nexus/v1/jt/getProjectTasks", 
			{}, 
			{query: { method: "GET", isArray: true }
	});
});

app.factory("GetAllProjects", function($resource) {
	return $resource("/nexus/v1/jt/getAllProjects", 
			{}, 
			{query: { method: "GET", isArray: true }
	});
});

app.factory("AddNote", function($resource){
	return $resource("/nexus/v1/jt/addNote", 
			{}, 
			{query: { method: "POST", isArray: false }
	});
});

app.factory("TaskService", function($resource){
	return {
		getTask : function(){
			return $resource("/nexus/v1/jt/getTask", 
					{}, 
					{query: { method: "POST", isArray: false }
			});
		}
	};
});

app.factory("GetTask", function($resource){
	return $resource("/nexus/v1/jt/getTask", 
			{}, 
			{
				query: { method: "POST", isArray: false }
			}
		);
});

app.factory("AddNewProject", function($resource){
	return $resource("/nexus/v1/jt/addProject", 
			{}, 
			{
				query: { method: "POST", isArray: false }
			}
		);
});

app.factory("SetMainTask", function($resource){
	return $resource("/nexus/v1/jt/setMainTask", 
			{}, 
			{
				query: { method: "POST", isArray: false }
			}
		);
});

app.factory("CheckConnection", function($resource){
	return $resource("/nexus/v1/checkConnection", 
			{}, 
			{
				query: { method: "GET", isArray: false }
			}
		);
});
