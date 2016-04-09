var app = angular.module("OccasionsCtrl", []);

app.controller("OccasionsCtrl", function($scope, $rootScope, GetOccasions){
	$scope.occasions = [];
	
	GetOccasions.query({}, function(d){
		$scope.occasions =  d.object;
	});
});