var help = angular.module("JTaskHelp", ['jTaskService']);

help.controller("HelpCrtl", function($scope, Version){
	$scope.appName = "JTask";
	$scope.appVarsion = "0.0.1";
	$scope.comment = "Pierwsza wersja aplikacji organizującej zadania";
	
	Version.query({ name: "JTask" }, function(data) {
		$scope.appName = data.app;
		$scope.appVarsion = data.version;
		$scope.comment = data.comment;
	});
});