var app = angular.module("NexusAddApp", ["NexusService"]);

app.controller("NexusAddAppCtrl", function($scope, AddApplication){
	
	$scope.appName = "";
	$scope.appDescription = "";
	$scope.appKey = "";
	
	$scope.save = function(){
		AddApplication.query({name: $scope.appName, description: $scope.appDescription, key: $scope.appKey}, function(data){
			console.log("BUJA!!!!!!!!"+data);
		});
	};
});