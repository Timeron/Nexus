var app = angular.module('TaskSearch', ['jTaskService']);

app.controller('TaskSearchCtrl', function($scope, $rootScope, AllProjectTasks){
	$scope.tasks = [];
//	$scope.statuses = ['oczekuje','do zrobienia', 'w toku', 'weryfikacja', 'ukończone', 'zamknięte'];
	$scope.statuses = ['wait','to do', 'in progress', 'in review', 'done', 'closed'];
	
	
	AllProjectTasks.query({ id: $rootScope.projectId }, function(data) {
		$scope.tasks = data;
	});

});