var appConf = angular.module('Config', []);
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
		.otherwise({
			template: 'dupa'
		});
});