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
	
	this.addNewProject = function(newProjectName, newProjectDescription){
		addProject = $q.defer();
		$http.post("http://timeron.ddns.net:8080/timeron-nexus/v1/jtask/addProject", 
				{
					name: newProjectName, 
					description: newProjectDescription
				})
		.success(function(data){
			return addProject.resolve(data);
		})
		.error(function(data){
			return data;
		});
		return addProject.promise;
	};
	
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
	
	this.getAllProjectTasks = function(projectId){
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

app.directive("projectcolumn", function($window){
	return {
		restrict: "AE",
		link: function(scope, element, attrs){
			var scrWidth = 0;
			var w = angular.element($window);
	        scope.getWindowDimensions = function () {
	            return {
	                'h': w.height(),
	                'w': w.width()
	            };
	        };
	        scope.$watch(scope.getWindowDimensions, function (newValue, oldValue) {
	            scope.windowHeight = newValue.h;
	            scope.windowWidth = newValue.w;
	            scrWidth = scope.windowWidth-6;
				width = (scrWidth / scope.projects.length);
				console.log(width);
				element[0].style.float = "left";
				if(width >= 250){
					element[0].style.width = width+"px";
				}else{
					maxCol = Math.round(scrWidth / 250);
					width = scrWidth / maxCol;
					element[0].style.width = width+"px";
					
					if(attrs.projectColumn >= maxCol){
						element[0].style.display = "none";
					}
				}
	        }, true);

	        w.bind('resize', function () {
	            scope.$apply();
	        });
		}
	};
});

app.directive("projectboardcolumn", function($window){
	return {
		restrict: "AE",
		link: function(scope, element, attrs){
			
			var w = angular.element($window);
	        scope.getWindowDimensions = function () {
	            return {
	                'h': w.height(),
	                'w': w.width()
	            };
	        };
	        scope.$watch(scope.getWindowDimensions, function (newValue, oldValue) {
	            scope.windowHeight = newValue.h;
	            scope.windowWidth = newValue.w;
	            scrWidth = scope.windowWidth-6;
				width = scrWidth / 6;
				element[0].style.float = "left";
				element[0].style.width = width+"px";
				element[0].style.padding = "3px";
	        }, true);

	        w.bind('resize', function () {
	            scope.$apply();
	        });
			
			
			
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
	$scope.messages = [];
	$scope.errorMessages = [];
	
	var projectPromise = JTaskService.getProjects();
	projectPromise.then(function(data){
		$rootScope.projects = angular.fromJson(data.data);
	});
	
	$scope.openProject = function(index){
		$rootScope.projectId = $rootScope.projects[index].id;
		setProjectInScope();
	};
	
	$scope.extendProject = function(index){
		console.log("extend "+index);
	};
});

app.controller("JTaskProjectCtr", function($rootScope, $scope, JTaskService){
	$rootScope.project;
	
	setProjectInScope = function(){
		angular.forEach($rootScope.projects, function(p){
			if(p.id === $rootScope.projectId){
				$rootScope.project = p;
				return null;
			}
		});
		console.log("project");
		console.log($rootScope.project);
	};
	

});

app.controller("JTaskNewProjectCtr", function($rootScope, $scope, $http, JTaskService){
	$scope.projectId;
	$scope.projectName;
	$rootScope.projects = [];
	
	$scope.addProject = function() {
		var addNewProjectPromise = JTaskService.addNewProject($scope.newProjectName, $scope.newProjectDescription);
		addNewProjectPromise.then(function(data){
			if(data.success == true){
				$scope.messages = data.messages;
				$rootScope.projects.push(data.object);
			}
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
		$scope.addTaskPromise = JTaskService.addNewTask($rootScope.projectId, $scope.newSummary, $scope.newType.id, $scope.newPriority.id, $scope.newDescription);
		$scope.addTaskPromise.then(function(status){
			if(status.success == true){
				projectTasks = JTaskService.getAllProjectTasks($rootScope.projectId);
				projectTasks.then(function(data){
					$rootScope.project.tasks = data;
				});
				
			}
		});
		
	};
});