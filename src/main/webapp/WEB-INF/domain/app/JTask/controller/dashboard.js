var app = angular.module("nexus", []);

app.service("JTaskService", function($http, $q){
	
	//get
	
	var getAllProjects = $q.defer();
	$http.get("http://timeron.ddns.net:8080/timeron-nexus/v1/jtask/getAllProjects").then(function(data){
		getAllProjects.resolve(data);
	});
	
	this.getProjects = function(){
		return getAllProjects.promise;
	};
	
	//post
	
	
	
	this.addNewTask = function(tProjectId, tTaskSummary, tType, tPriority, tDescription){
		addTask = $q.defer();
		$http.post("http://timeron.ddns.net:8080/timeron-nexus/v1/jtask/addTask", 
				{
					projectId: tProjectId,
					summary: tTaskSummary,
					taskTypeId: tType,
					priority: tPriority,
					description: tDescription
				})
		.success(function(data){
			return addTask.resolve(data);
		})
		.error(function(data){
			return data;
		});
		return addTask.promise;
	};
	
	this.getAllProjectTask = function(projectId){
		allProjectTask = $q.defer();
		$http.post("http://timeron.ddns.net:8080/timeron-nexus/v1/jtask/getProjectTasks", 
				{
					id: projectId
				})
		.success(function(data){
			return allProjectTask.resolve(data);
		})
		.error(function(data){
			return data;
		});
		return allProjectTask.promise;
	};
	
	this.getProjectTask = function(projectId){
		allProjectTask = $q.defer();
		$http.post("http://timeron.ddns.net:8080/timeron-nexus/v1/jtask/getProjectTask", 
				{
					id: projectId
				})
		.success(function(data){
			return allProjectTask.resolve(data);
		})
		.error(function(data){
			return data;
		});
		return allProjectTask.promise;
	};
});

//Directives

app.directive("projectcolumn", function(){
	return {
		restrict: "AE",
		link: function(scope, element, attrs){
			scrWidth = element[0].parentNode.clientWidth - 20;
			width = (scrWidth / scope.projects.length);
			element[0].style.float = "left";
			if(width >= 300){
				element[0].style.width = width+"px";
			}else{
				maxCol = Math.round(scrWidth / 300);
				width = scrWidth / maxCol;
				element[0].style.width = width+"px";
				
				if(attrs.projectColumn >= maxCol){
					element[0].style.display = "none";
				}
			}
		}
		
	};
});

app.directive("projectboardcolumn", function(){
	return {
		restrict: "AE",
		link: function(scope, element, attrs){
			scrWidth = element[0].parentNode.clientWidth - 20;
			width = scrWidth / 5;
			element[0].style.float = "left";
			element[0].style.width = width+"px";
			element[0].style.padding = "3px";
		}
	};
});

app.directive("inline", function(){
	return {
		restrict: "A",
		link: function(scope, element, attrs){
			parentWidth = element[0].parentNode.clientWidth;
			nextWidth = element[0].nextElementSibling.clientWidth;
			width = parentWidth - nextWidth;
			element[0].style.width = width+"px";
		}
	};
});

//*************************
//Controller
//*************************

app.controller("JTaskBoardCtr", function($rootScope, $scope, $element, JTaskService){
	$rootScope.projects = [];
//	$scope.newProjectName;
//	$scope.newProjectDescription;
	$scope.messages = [];
	$scope.errorMessages = [];
	
	var projectPromise = JTaskService.getProjects();
	projectPromise.then(function(data){
		$rootScope.projects = angular.fromJson(data.data);
	});
	
	$scope.openProject = function(index){
//		var data = JTaskService.getAllProjectTask($rootScope.projects[index].projectId);
//		console.log(data);
		$rootScope.projectId = $rootScope.projects[index].id;
		setProjectInScope();
	};
	
	$scope.extendProject = function(index){
		console.log("extend "+index);
	};
});

app.controller("JTaskProjectCtr", function($rootScope, $scope, JTaskService){
	$scope.project;
	
	setProjectInScope = function(){
		angular.forEach($rootScope.projects, function(p){
			if(p.id === $rootScope.projectId){
				$scope.project = p;
			}
		});
		console.log("project");
		console.log($scope.project);
	};
	

});

app.controller("JTaskNewProjectCtr", function($rootScope, $scope, $http){
	$scope.projectId;
	$scope.projectName;
	$rootScope.projects = [];
	
	$scope.addProject = function(){
		$http.post("http://timeron.ddns.net:8080/timeron-nexus/v1/jtask/addProject", 
				{
					name: $scope.newProjectName, 
					description: $scope.newProjectDescription
				})
		.success(function(data){
			$scope.messages = data.messages;
			$rootScope.projects.push(data.object);
		})
		.error(function(data){
			$scope.errorMessages = data.messages;
		});
	};
});


app.controller("JTaskNewTaskCtr", function($rootScope, $scope, JTaskService){
	$scope.priorities = [
	                     {id:1},
	                     {id:2},
	                     {id:3},
	                     {id:4},
	                     {id:5},
	                     {id:6},
	                     {id:7},
	                     {id:8},
	                     {id:9},
	                     {id:10}
	                     ];
	$scope.taskTypes = [
	                    {id:1, name:"Task"},
	                    {id:2, name:"Bug"},
	                    {id:3, name:"Improvement"}
	                    ];
	$scope.newSummary;
	$scope.newType;
	$scope.newPriority;
	$scope.newDescription;
	
	$scope.saveTask = function(){
		addTaskPromise = JTaskService.addNewTask($rootScope.projectId, $scope.newSummary, $scope.newType.id, $scope.newPriority.id, $scope.newDescription);
		projectPromise = JTaskService.getProjects();
		projectPromise.then(function(data){
			$rootScope.projects = angular.fromJson(data.data);
		});
	};
});