var app = angular.module("Release", ['jTaskService']);

app.controller("ReleaseCtr", function($rootScope, $scope, $element, SaveRelease){
	$scope.version = "";
	$scope.comment = "";
	
	$scope.addRelease = function(){
		console.log($rootScope.project.id);
		var release = {version: $scope.version, comment: $scope.comment, projectId: $rootScope.project.id};
		SaveRelease.query(release, function(data) {
			$scope.message = data;
		});
	}
});