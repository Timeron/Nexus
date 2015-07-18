var appConf = angular.module('Config', ['TaskSearch']);
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
		.otherwise({
			template: 'Niepoprawny url'
		});
});