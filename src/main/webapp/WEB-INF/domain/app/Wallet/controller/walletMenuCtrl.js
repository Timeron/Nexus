var app = angular.module("wallet", ['WalletService']);

app.controller("WalletMenuCtrl", function($scope, AddAccount){
//	new account
	$scope.newAccountName = "";
	$scope.newAccountDescription = "";
//	new record
	$scope.amount = 0;
	$scope.operationDate;
	$scope.income;
	$scope.type;
	$scope.types = [];
	
	$scope.addAccount = function(){
		console.log($scope.newAccountName);
		console.log("DuPA");
		AddAccount.query({
			name : $scope.newAccountName,
			description : $scope.newAccountDescription
		}, function(data) {
			$scope.message = data;
		});
		
	};
});