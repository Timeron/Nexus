var app = angular.module("nexus", ['ngResource']);

app.factory("Post", function($resource) {
	return $resource("v1/historyTask", 
			{}, 
			{query: { method: "GET", isArray: true }
	});
});


app.service("JTaskService", function($http, $q){
	
	//get
//	var path = "http://timeron.ddns.net:8080/timeron-nexus/";
	var path = "http://localhost:8080/timeron-nexus/";
	
	var getAllProjects = $q.defer();
	$http.get(path+"/jtask/v1/getAllProjects").then(function(data){
		getAllProjects.resolve(data);
	});
	
	this.getProjects = function(){
		return getAllProjects.promise;
	};
	
	//post
	
	this.addNewProject = function(newProjectName, newProjectDescription){
		addProject = $q.defer();
		$http.post(path+"/jtask/v1/addProject", 
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
		$http.post(path+"/jtask/v1/addTask", 
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
		$http.post(path+"/jtask/v1/getProjectTasks", 
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
		$http.post(path+"/jtask/v1/getProjectTask", 
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
	
	this.updateTask = function(task){
		addProject = $q.defer();
		$http.post(path+"/jtask/v1/updateTask", task)
		.success(function(data){
			return addProject.resolve(data);
		})
		.error(function(data){
			return data;
		});
		return addProject.promise;
	};
	
	this.getHistory = function(task){
		historyPromise = $q.defer();
		$http.post(path+"/jtask/v1/historyTask", task)
		.success(function(data){
			return historyPromise.resolve(data);
		})
		.error(function(data){
			return data;
		});
		return historyPromise.promise;
	};
});

//Factory

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
	
	$scope.openBoard = function(){
		setAllProjectsInScope();
	};
	
	$scope.openProject = function(index){
		$rootScope.projectId = $rootScope.projects[index].id;
		setProjectInScope();
	};
	
	$scope.extendProject = function(index){

	};
});

app.controller("JTaskProjectCtr", function($rootScope, $scope, $http, JTaskService){
	$rootScope.project;
	$scope.wait = [];
	$scope.toDo = [];
	$scope.inProgress = [];
	$scope.inReview = [];
	$scope.done = [];
	
	$scope.taskDetails;
	
	setProjectInScope = function(){
		angular.forEach($rootScope.projects, function(p){
			if(p.id === $rootScope.projectId){
				$rootScope.project = p;
				$rootScope.splitToColumn($rootScope.project.tasks);
			}
		});
	};
	
	setAllProjectsInScope = function(){
//		var path = "http://timeron.ddns.net:8080/timeron-nexus/";
		var path = "http://localhost:8080/timeron-nexus/";
		
		$http.get(path+"/jtask/v1/getAllTasksInOneProject")
		.success(function(data){
			$rootScope.project = angular.fromJson(data);
			$rootScope.splitToColumn($rootScope.project.tasks);
		})
		.error(function(data){
			return data;
		});
		
	};
	
	$scope.getTaskDetails = function(task){
		$scope.taskDetails = task;
	};
	
	$scope.taskDirestionPreviousNext = function (task, direction){
		switch(task.status){
			case 1 : 
				index = $scope.wait.indexOf(task);
				if(index != -1) {
					$scope.wait.splice(index, 1);
				}
				if(direction === 'next'){
					task.status += 1;
					$scope.toDo.push(task);
				}
				break;
			case 2 :
				index = $scope.toDo.indexOf(task);
				if(index != -1) {
					$scope.toDo.splice(index, 1);
				}
				if(direction === 'next'){
					task.status += 1;
					$scope.inProgress.push(task);
				}else{
					task.status -= 1;
					$scope.wait.push(task);
				}
				break;
			case 3 :
				index = $scope.inProgress.indexOf(task);
				if(index != -1) {
					$scope.inProgress.splice(index, 1);
				}
				if(direction === 'next'){
					task.status += 1;
					$scope.inReview.push(task);
				}else{
					task.status -= 1;
					$scope.toDo.push(task);
				}
				break;
			case 4 :
				index = $scope.inReview.indexOf(task);
				if(index != -1) {
					$scope.inReview.splice(index, 1);
				}
				if(direction === 'next'){
					task.status += 1;
					$scope.done.push(task);
				}else{
					task.status -= 1;
					$scope.inProgress.push(task);
				}
				break;
			case 5 :
				index = $scope.done.indexOf(task);
				if(index != -1) {
					$scope.done.splice(index, 1);
				}
				if(direction === 'previous'){
					task.status -= 1;
					$scope.inReview.push(task);
				}
				break;
			default : 
				break;
		}
		task.updateMessageStatus = task.status;
		JTaskService.updateTask(task);
	};
	
	$scope.taskDirestionNext = function(task){
		task.status += 1;
	};
	
	$rootScope.splitToColumn = function(tasks){
		$scope.wait = [];
		$scope.toDo = [];
		$scope.inProgress = [];
		$scope.inReview = [];
		$scope.done = [];
		angular.forEach(tasks, function(t){
			switch(t.status){
				case 1:
					$scope.wait.push(t);
					break;
				case 2:
					$scope.toDo.push(t);
					break;
				case 3:
					$scope.inProgress.push(t);
					break;
				case 4:
					$scope.inReview.push(t);
					break;
				case 5:
					$scope.done.push(t);
					break;
				default:
					break;
			}
		});
	};
});

//new modals

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
					$rootScope.splitToColumn($rootScope.project.tasks);
				});
			}
		});
		
	};
	
	
});

app.controller("TaskController", function($rootScope, $scope, JTaskService, Post){
	$scope.task;
	$scope.histories;
	
	$rootScope.setTaskInNewWindow = function(task){
		$scope.task = task;
		switch(task.taskTypeId){
		case 1 : 
			$scope.task.taskType = "TASK";
			break;
		case 2 : 
			$scope.task.taskType = "BUG";
			break;
		case 3 : 
			$scope.task.taskType = "IMPROVMENT";
			break;
		default :
			break;
		}
	};
	
	$scope.getHistory = function(task){
		Post.query({ id: task.id }, function(data) {
			$scope.histories = data;
		});
	};
	
	$scope.isStatusChange = function(history){
		if(history.hasOwnProperty("status")){
			return true;
		}
		return false;
	};
	
});