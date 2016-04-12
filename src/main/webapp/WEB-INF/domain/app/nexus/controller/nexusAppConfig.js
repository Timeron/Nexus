var app = angular.module("NexusAppConfig", ['NexusService']);

app.controller("NexusAppConfigCtrl", function($scope, GetUsersToManageAccessToApplication, SaveAccessToApplication){
	
	$scope.users = [];
	$scope.owners = [];
	$scope.usersTemp = [];
	$scope.ownersTemp = [];
	$scope.application = {};
	
	$scope.getUsers = function(appId){
		$scope.application.id = appId;
		GetUsersToManageAccessToApplication.query({id: appId}, function(data){
			$scope.users = data.object.users2;
			$scope.owners = data.object.users1;
			angular.copy($scope.users, $scope.usersTemp);
			angular.copy($scope.owners, $scope.ownersTemp);
		});
	};
	
	$scope.addAccess = function(user){
		$scope.ownersTemp.push(user);
		index = $scope.usersTemp.indexOf(user);
		$scope.usersTemp.splice(index, 1);
	};
	
	$scope.removeAccess = function(user){
		$scope.usersTemp.push(user);
		index = $scope.ownersTemp.indexOf(user);
		$scope.ownersTemp.splice(index, 1);
	};
	
	$scope.saveAccess = function(){
		SaveAccessToApplication.query({application: $scope.application, users: $scope.ownersTemp}, function(data){
			console.log(data);
		});
	};
	
	$scope.cancleAccess = function(){
		angular.copy($scope.users, $scope.usersTemp);
		angular.copy($scope.owners, $scope.ownersTemp);
	};
});