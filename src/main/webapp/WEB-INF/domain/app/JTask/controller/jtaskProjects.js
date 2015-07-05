var app = angular.module("project", []);

app.controller("JTaskProjectsCtr", function($rootScope, $scope, $element, JTaskService){
	$scope.projects = [];
	
	var projectPromise = JTaskService.getProjects();
	projectPromise.then(function(data){
		$scope.projects = angular.fromJson(data.data);
	});
});