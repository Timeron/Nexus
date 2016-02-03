var app = angular.module("OccasionsCtrl", []);

app.controller("OccasionsCtrl", function($scope, $rootScope, GetOccasions){
	$scope.occasions = [];
	
	GetOccasions.query({}, function(d){
		console.log(d.object);
		$scope.occasions =  d.object;
	});
});