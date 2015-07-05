var appConf = angular.module('Config', []);
appConf.config(function($routeProvider){
	$routeProvider
		.when('/', {
			templateUrl: 'dashboard'
		})
		.when('/task/', {
			templateUrl: 'task'
		})
		.otherwise({
			template: 'dupa'
		});
});