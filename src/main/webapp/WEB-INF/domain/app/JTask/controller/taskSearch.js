var app = angular.module('TaskSearch', ['jTaskService']);

app.controller('TaskSearchCtrl', function($scope, $rootScope, AllProjectTask){
	$scope.tasks = [];
//	$scope.statuses = ['oczekuje','do zrobienia', 'w toku', 'weryfikacja', 'ukończone', 'zamknięte'];
	$scope.statuses = ['wait','to do', 'in progress', 'in review', 'done', 'closed'];
	
	
	AllProjectTask.query({ id: $rootScope.projectId }, function(data) {
		$scope.tasks = data;
	});

});