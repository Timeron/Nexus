var app = angular.module("NexusAppConfig", ['NexusService']);

app.controller("NexusAppConfigCtrl", function($scope, GetUsersToManageAccessToApplication){
	
	$scope.users = [];
	$scope.owners = [];
	$scope.usersTemp = [];
	$scope.ownersTemp = [];
	
	$scope.getUsers = function(appId){
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
		SaveAccessToProject.query({projectId: $rootScope.project.id, users: $scope.ownersTemp}, function(data){
			console.log(data.success);
		});
	};
	
	$scope.cancleAccess = function(){
		angular.copy($scope.users, $scope.usersTemp);
		angular.copy($scope.owners, $scope.ownersTemp);
	};
});