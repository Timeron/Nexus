var app = angular.module("ProjectConfig", ['jTaskService']);

app.controller("ProjectConfigCtrl", function($scope, $rootScope, GetUsersToManageAccessToProject, SaveAccessToProject){
	$scope.users = [];
	$scope.owners = [];
	$scope.usersTemp = [];
	$scope.ownersTemp = [];
	
	$scope.getUsers = function(){
		GetUsersToManageAccessToProject.query({projectId: $rootScope.project.id}, function(data){
			console.log(data);
			$scope.users = data.object.users2;
			$scope.owners = data.object.users1;
			angular.copy($scope.users, $scope.usersTemp);
			angular.copy($scope.owners, $scope.ownersTemp);
		});
	};
	
	$scope.buttonProjectClose = function(){
		console.log("buttonClose");
	};
	
	$scope.buttonProjectOpen = function(){
		console.log("buttonOpen");
	};
	
	$scope.getUsers();
	
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
	
	$scope.test = function(d){
		console.log(d);
	};
});