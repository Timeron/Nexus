var app = angular.module("nexus", ['ngResource', 'ngRoute', 'Config', 'Search', 'JTaskHelp', 'DatePicker', 'EditTask', 'MPImage']);

app.factory("Histories", function($resource) {
	return $resource("/timeron-nexus/v1/jt/historyTask", 
			{}, 
			{query: { method: "GET", isArray: true }
	});
});

app.factory("Notes", function($resource) {
	return $resource("/timeron-nexus/v1/jt/notesTask", 
			{}, 
			{query: { method: "GET", isArray: true }
	});
});

app.factory("AddNote", function($resource){
	return $resource("/timeron-nexus/v1/jt/addNote", 
			{}, 
			{query: { method: "POST", isArray: false }
	});
});

app.factory("TaskService", function($resource){
	return {
		getTask : function(){
			return $resource("/timeron-nexus/v1/jt/getTask", 
					{}, 
					{query: { method: "POST", isArray: false }
			});
		}
	};
});

app.factory("GetTask", function($resource){
	return $resource("/timeron-nexus/v1/jt/getTask", 
			{}, 
			{
				query: { method: "POST", isArray: false }
			}
		);
});


app.service("JTaskService", function($http, $q){
	
	//get
//	var path = "http://timeron.ddns.net:8080/timeron-nexus/";
	var path = "http://localhost:8080/timeron-nexus/v1/jt";
	
	var getAllProjects = $q.defer();
	$http.get(path+"/getAllProjects").then(function(data){
		getAllProjects.resolve(data);
	});
	
	this.getProjects = function(){
		return getAllProjects.promise;
	};
	
	//post
	
	this.addNewProject = function(newProjectName, newProjectDescription, newPrefix){
		addProject = $q.defer();
		$http.post(path+"/addProject", 
				{
					name: newProjectName, 
					description: newProjectDescription,
					prefix: newPrefix
				})
		.success(function(data){
			return addProject.resolve(data);
		})
		.error(function(data){
			return data;
		});
		return addProject.promise;
	};
	
	this.addNewTask = function(tProjectId, tTaskSummary, tType, tPriority, tDescription, tDate, tWorkExpected){
		addTask = $q.defer();
		$http.post(path+"/addTask", 
				{
					projectId: tProjectId,
					summary: tTaskSummary,
					taskTypeId: tType,
					priority: tPriority,
					description: tDescription,
					endDateLong: tDate,
					workExpected: tWorkExpected
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
		$http.post(path+"/getProjectTasks", 
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
		$http.post(path+"/getProjectTask", 
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
		update = $q.defer();
		$http.post(path+"/updateTask", task)
		.success(function(data){
			return update.resolve(data);
		})
		.error(function(data){
			return data;
		});
		return update.promise;
	};
	
	this.getHistory = function(task){
		historyPromise = $q.defer();
		$http.post(path+"/historyTask", task)
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

app.directive("taskselection", function($rootScope){
	
	return {
		restrict: "EA",
		scope: { marked: '@'},
		link: function(scope, element, attrs){
			scope.now = new Date();
			scope.endDateDate = new Date(attrs.enddate);
			scope.workExpectedDate = attrs.workexpected;
			
			if(scope.endDateDate.getTime()-scope.workExpectedDate < scope.now.getTime()){
				element[0].style.backgroundColor = "#F00";
			}else if(scope.endDateDate.getTime()-(2*scope.workExpectedDate) < scope.now.getTime()){
				element[0].style.backgroundColor = "#FA0";
			}else{
				element.bind('mouseover', function(){
					if(attrs.marked !== 'true'){
						element[0].style.backgroundColor = "#E0FFE0";
					}
				});
				element.bind('mouseout', function(){
					if(attrs.marked !== 'true'){
						element[0].style.backgroundColor = "#FFF";
					}
				});
			}
			
		},
		controller: function($scope, $element, $attrs){
			
			$attrs.$observe('marked', function(e) {
				if($scope.endDateDate.getTime()-$scope.workExpectedDate < $scope.now.getTime()){
					$element[0].style.backgroundColor = "#F00";
				}else if($scope.endDateDate.getTime()-(2*$scope.workExpectedDate) < $scope.now.getTime()){
					$element[0].style.backgroundColor = "#FA0";
				}else{
					if(e==='true'){
						$element[0].style.backgroundColor = "#AAFFAA";
					}else{
						$element[0].style.backgroundColor = "#FFF";
					}
				}
			});
		}
	};
}),

//*************************
//Controller
//*************************

app.controller("JTaskBoardCtr", function($rootScope, $scope, $http, $element, JTaskService, $location){
	$rootScope.projects = [];
	$rootScope.projectId;
	$scope.timers = false;
	
	var projectPromise = JTaskService.getProjects();
	projectPromise.then(function(data){
		$rootScope.projects = angular.fromJson(data.data);
	});
	
	$scope.openBoard = function(){
		$location.path('/');
		$rootScope.projectId = 0;
		$rootScope.setAllProjectsInScope();
		
	};
	
	$scope.openProject = function(project){
		$location.path('/');
		$rootScope.projectId = project.id;
		$rootScope.project = project;
		$rootScope.setProjectInScope();
	};
	
	$scope.openPreviouseProject = function(){
		$location.path('/');
		$rootScope.setProjectInScope();
	};
	
	$scope.extendProject = function(index){

	};
	
	$scope.openProjectSearch = function(){
		$location.path('/projectSearch');
	};
	
	$rootScope.polishDate = function(tempDate){
		if(tempDate === undefined){
			return " - ";
		}
		var months = [
				{id: 0, name: "Styczeń", days: 31},
             	{id: 1, name: "Luty", days: 29}, 
             	{id: 2, name: "Marzec", days: 31}, 
             	{id: 3, name: "Kwiecień", days: 30}, 
             	{id: 4, name: "Maj", days: 31}, 
             	{id: 5, name: "Czerwiec", days: 30}, 
             	{id: 6, name: "Lipiec", days: 31}, 
             	{id: 7, name: "Sierpień", days: 31},
             	{id: 8, name: "Wrzesień", days: 30},
             	{id: 9, name: "Padźiernik", days: 31},
             	{id: 10, name: "Listopad", days: 30},
             	{id: 11, name: "Grudzień", days: 31}
		];
		var date = new Date(tempDate);
		return date.getDate()+" "+months[date.getMonth()].name+ " " +date.getFullYear()+" "+date.getHours()+":"+date.getMinutes();
	};
	
	$scope.chasTermins = function(){
		if($rootScope.taskDetails === undefined){
			return false;
		}else{
			if(($rootScope.taskDetails.endDate === new Date(0) || $rootScope.taskDetails.endDate === undefined) && $rootScope.taskDetails.workExpected === 0){
				return false;
			}else{
				return true;
			}
		}
	};
	
	$scope.msToDaysandHours = function(tempTime){
		if(tempTime === 0){
			return " - ";
		}
		var hours = tempTime / (1000*60*60);
		var days = Math.floor(hours/24);
		hours = hours%24;
		if(days === 1){
			return days+" dzień "+hours+"h";
		}else{
			return days+" dni "+hours+"h";
		}
		
	};
	

});

app.controller("JTaskProjectCtr", function($rootScope, $scope, $http, JTaskService, $location){
	$scope.wait = [];
	$scope.toDo = [];
	$scope.inProgress = [];
	$scope.inReview = [];
	$scope.done = [];
	
	$scope.taskDetails;
	
	$rootScope.setProjectInScope = function(){
		$rootScope.splitToColumn($rootScope.project.tasks);
	};
	
	$rootScope.setAllProjectsInScope = function(){
//		var path = "http://timeron.ddns.net:8080/timeron-nexus/";
		var path = "http://localhost:8080/timeron-nexus/v1/jt";
		
		$http.get(path+"/getAllTasksInOneProject")
		.success(function(data){
			$rootScope.project = angular.fromJson(data);
			$rootScope.splitToColumn($rootScope.project.tasks);
		})
		.error(function(data){
			return data;
		});
		
	};
	
	$scope.getTaskDetails = function(task){
		$rootScope.taskDetails = task;
		angular.forEach($scope.inProgress, function(t){
			if(t.id === task.id){
				t.marked = true;
			}else{
				t.marked = false;
			}
		});
		angular.forEach($scope.inReview, function(t){
			if(t.id === task.id){
				t.marked = true;
			}else{
				t.marked = false;
			}
		});
		angular.forEach($scope.wait, function(t){
			if(t.id === task.id){
				t.marked = true;
			}else{
				t.marked = false;
			}
		});
		angular.forEach($scope.toDo, function(t){
			if(t.id === task.id){
				t.marked = true;
			}else{
				t.marked = false;
			}
		});
		angular.forEach($scope.done, function(t){
			if(t.id === task.id){
				t.marked = true;
			}else{
				t.marked = false;
			}
		});
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
	
	$rootScope.taskClose = function(task){
		index = $scope.done.indexOf(task);
		$scope.done.splice(index, 1);
		task.status = 6;
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
	
	$scope.setTaskInNewWindow = function(task){
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
	
		$rootScope.taskDetails = task;
		$location.path('/task/');
	};
	
	$scope.searchTask = function(){
		$location.path('/taskSearch/');
	};
});

//new modals

app.controller("JTaskNewProjectCtr", function($rootScope, $scope, $http, JTaskService){
//	$scope.projectId;
//	$scope.projectName;
	$scope.newProjectName;
	$scope.newProjectDescription;
	$scope.newProjectPrefix;
	
	$rootScope.projects = [];
	
	$scope.addProject = function() {
		var addNewProjectPromise = JTaskService.addNewProject($scope.newProjectName, $scope.newProjectDescription, $scope.newProjectPrefix);
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
	$scope.workExpectedDay = 0;
	$scope.workExpected = 0;
	$scope.date = 0;
	$scope.time = 0;
	
	$scope.saveTask = function(){
		if($scope.timers){
			$scope.date = $scope.date + $scope.time;
		}else{
			$scope.date = 0;
			$scope.workExpected = 0;
		}
		
		$scope.addTaskPromise = JTaskService.addNewTask($rootScope.projectId, $scope.newSummary, $scope.newType.id, $scope.newPriority.id, $scope.newDescription, $scope.date, $scope.workExpected);
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

app.controller("TaskController", function($rootScope, $scope, $q, JTaskService, Histories, Notes, AddNote, GetTask){
	$scope.task;
	$scope.histories;
	$scope.notes;
	$scope.newNote;
	$scope.hideNotes = true;
	$scope.hideHistory = true;
	$scope.test;
	
	var getTask = function(){
		var task = GetTask.query({id: $rootScope.taskDetails.id}, function(data){
			return data.object;
		});
		return task;
	};
	$scope.task = getTask();
	
	$scope.getHistory = function(task){
		$scope.hideNotes = true;
		$scope.hideHistory = false;
		Histories.query({ id: task.id }, function(data) {
			$scope.histories = data;
		});
	};
	
	$scope.isStatusChange = function(history){
		if(history.hasOwnProperty("status")){
			return true;
		}
		return false;
	};
	
	$scope.isNote = function(history){
		if(history.hasOwnProperty("note")){
			return true;
		}
		return false;
	};
	
	$scope.isUpdate = function(history){
		if(history.hasOwnProperty("message")){
			return true;
		}
		return false;
	}
	
	$scope.addNote = function(){
		AddNote.query(
				{
					content: $scope.newNote,
					taskId: $rootScope.taskDetails.id
				 }, function(data){
			
		});
	};
	
	$scope.getNotes = function(task){
		$scope.hideNotes = false;
		$scope.hideHistory = true;
		Notes.query({ id: task.id }, function(data) {
			$scope.notes = data;
		});
	};
	
	var updateStatus = function(task, insex){
		task.status = insex;
		task.updateMessageStatus = task.status;
		
		$scope.update = JTaskService.updateTask(task);
		$scope.update.then(function(data){
			$scope.task = data.object;
		});
	};
	
	$scope.taskCloseFromTaskWindow = function(task){
		updateStatus(task, 6);
	};
	
	$scope.taskOpenFromTaskWindow = function(task){
		updateStatus(task, 2);
	};
	

	
	$scope.buttonClose = function(){
		if($scope.task.status === 6){
			return true;
		}else{
			return false;
		}
	};
	
	$scope.buttonOpen = function(){
		if($scope.task.status === 6){
			return false;
		}else{
			return true;
		}
	};

	
});