var app = angular.module("nexus", []);

app.service("JTaskService", function($http, $q){
	var deferred = $q.defer();
	$http.get("http://localhost:8080/timeron-nexus/v1/jtask/getAllProjects").then(function(data){
		deferred.resolve(data);
	});
	
	this.getProject = function(){
		return deferred.promise;
	};
});


app.controller("JTask", function($scope, JTaskService){
	console.log("1");
	$scope.data = {
			label: "labelka"
	};
	console.log("2");
	var promise = JTaskService.getProject();
	promise.then(function(data){
		console.log("3");
		$scope.projects = data;
		console.log($scope.projects);
	});
	
});