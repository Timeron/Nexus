var app = angular.module("wallet", ['WalletService', 'DatePicker']);

app.controller("WalletMenuCtrl", function($scope, $rootScope, AddAccount, GetAllRecordTypes, AddNewRecord, GetAllUserAccounts){
	$rootScope.currentAccount = 16;
	$scope.incomeCurrentDescription = "Wydatek";
	$scope.newRecordCurrentDescription = "Operacja";
	$scope.newRecordCurrentButtonDescription = "Transfer";
//	new account
	$scope.newAccountName = "";
	$scope.newAccountDescription = "";
//	new record
	$scope.amount = 0;
	$scope.operationDate = 0;
	$scope.operationTime = 0;
	$scope.income = false;
	$scope.transfer = false;
	$scope.type;
	$scope.account;
	$scope.types = GetAllRecordTypes.query();
	$scope.accounts = GetAllUserAccounts.query();
	
	$scope.addAccount = function(){
		AddAccount.query({
			name : $scope.newAccountName,
			description : $scope.newAccountDescription
		}, function(data) {
			$scope.message = data;
		});
	};
	
	$scope.newRecord = function(){
		if(!$scope.transfer){
			AddNewRecord.query({
				value : $scope.amount,
				description : $scope.description,
				income : $scope.income,
				transfer : $scope.transfer,
				date : $scope.operationDate + $scope.operationTime,
				recordTypeId : $scope.type.id,
				accountId : $rootScope.currentAccount
			}, function(data) {
				$scope.message = data;
			});
		}else{
			AddNewRecord.query({
				value : $scope.amount,
				description : $scope.description,
				transfer : $scope.transfer,
				date : $scope.operationDate + $scope.operationTime,
				accountId : $rootScope.currentAccount,
				destynationAccountId : $scope.account.id
			}, function(data) {
				$scope.message = data;
			});
		}
		
	};
	
	$scope.changeIncome = function(){
		if($scope.income){
			$scope.income = false;
			$scope.incomeCurrentDescription = "Wydatek";
		}else{
			$scope.income = true;
			$scope.incomeCurrentDescription = "Dochód";
		} 
	};
	
	$scope.changeTransfer = function(){
		if($scope.transfer){
			$scope.transfer = false;
			$scope.newRecordCurrentDescription = "Operacja";
			$scope.newRecordCurrentButtonDescription = "Transfer";
		}else{
			$scope.transfer = true;
			$scope.newRecordCurrentDescription = "Transfer";
			$scope.newRecordCurrentButtonDescription = "Operacja";
		} 
	};
});