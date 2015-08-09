var app = angular.module("EditTask", ['jTaskService']);

app.controller("EditTaskCtrl", function($rootScope, $scope, UpdateTask){
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

	$scope.newSummary = $rootScope.taskDetails.summary;
	$scope.newType = $rootScope.taskDetails.taskType;
	$scope.newTypeId = $rootScope.taskDetails.taskTypeId;
	$scope.newPriority = $rootScope.taskDetails.priority;
	$scope.newDescription = $rootScope.taskDetails.description;
	$scope.workExpected = $rootScope.taskDetails.workExpected;
	$scope.newDate = $rootScope.taskDetails.endDate;
	$scope.date = 0;
	$scope.time = 0;

	$scope.updateTask = function(){
		var task = {};
		task.id = $rootScope.taskDetails.id;
		task.summary = $scope.newSummary;
		task.taskTypeId = $scope.newType.id;
		task.priority = $scope.newPriority.id;
		task.description = $scope.newDescription;
		if($scope.timers){
			task.workExpected = $scope.workExpected;
			task.endDateLong = $scope.date + $scope.time;
		}else{
			task.workExpected = 0;
			task.endDateLong = 0;
		}
		
		
		UpdateTask.query(task, function(data) {
			$scope.message = data;
		});
		
		$rootScope.taskDetails.summary = $scope.newSummary; 
		$rootScope.taskDetails.taskType = $scope.newType.name;
		$rootScope.taskDetails.taskTypeId = $scope.newType.id;
		$rootScope.taskDetails.priority = $scope.newPriority.id;
		$rootScope.taskDetails.description = $scope.newDescription;
		if($scope.timers){
			if($scope.workExpected !== 0){
				$rootScope.taskDetails.workExpected = $scope.workExpected;
			}
			if(($scope.date + $scope.time) !== 0){
				$rootScope.taskDetails.endDate = new Date($scope.date + $scope.time);
			}
		}
	};
});