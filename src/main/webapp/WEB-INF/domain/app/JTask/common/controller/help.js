var help = angular.module("JTaskHelp", ['jTaskService']);

help.controller("HelpCrtl", function($scope, Version){
	$scope.appName = "";
	$scope.appVarsion = "";
	$scope.comment = "";
	
	Version.query({ name: "JTask" }, function(data) {
		$scope.appName = data.app;
		$scope.appVarsion = data.version;
		$scope.comment = data.comment;
	});
});