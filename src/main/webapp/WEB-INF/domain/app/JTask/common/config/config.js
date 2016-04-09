var appConf = angular.module('Config', ['TaskSearch', 'ProjectConfig']);
appConf.config(function($routeProvider){
	$routeProvider
		.when('/', {
			templateUrl: 'dashboard'
		})
		.when('/task/', {
			templateUrl: 'task'
		})
		.when('/projectSearch/', {
			templateUrl: 'projectSearch'
		})
		.when('/taskSearch/', {
			templateUrl: 'taskSearch',
			controller: 'TaskSearchCtrl'
		})
		.when('/projectConfig/', {
			templateUrl: 'projectConfig',
			controller: 'ProjectConfigCtrl'
		})
		.otherwise({
			template: 'Niepoprawny url'
		});
});