var app = angular.module("wallet", ['WalletService', 'DatePicker', 'ViewDirective']);

app.controller("WalletMenuCtrl", function($scope, $rootScope, AddAccount, GetAllRecordTypes, AddNewRecord, GetAllUserAccounts){
	$scope.income = true;
	$scope.transfer = false;
	$scope.types = [];
	$scope.accounts = [];
	$scope.targetAccount;
	$scope.type;
	$scope.amount = "";
	$scope.operationDate = 0;
	$scope.operationTime = 0;
	$scope.currentAccount;
	
	$scope.targetAccountsHide = true;
	$scope.accountsHide = true;
	$scope.typeHide = true;
	$scope.dateHide = true;
	
	var setTypes = function(){
		$scope.types = GetAllRecordTypes.query({}, function(data){
			$scope.types = data;
			$scope.type = data[0];
		});
	};
	
	var setAccounts = function(){
		$scope.accounts = GetAllUserAccounts.query({}, function(data){
			$scope.accounts = data;
			$scope.currentAccount = data[0];
		});
	};
	
	setTypes();
	setAccounts();
	
	$scope.newRecord = function(){
		$scope.amount = $scope.amount.replace(",", ".");
		if(!$scope.transfer){
			AddNewRecord.query({
				value : $scope.amount,
				description : $scope.description,
				income : $scope.income,
				transfer : $scope.transfer,
				date : $scope.operationDate + $scope.operationTime,
				recordTypeId : $scope.type.id,
				accountId : $scope.currentAccount.id
			}, function(data) {
				$scope.message = data;
			});
		}else{
			AddNewRecord.query({
				value : $scope.amount,
				description : $scope.description,
				transfer : $scope.transfer,
				date : $scope.operationDate + $scope.operationTime,
				accountId : $scope.currentAccount.id,
				destynationAccountId : $scope.targetAccount.id
			}, function(data) {
				$scope.message = data;
			});
		}
	};
	
	$scope.changeIncomeTrue = function(){
		$scope.income = true;
	};
	
	$scope.changeIncomeFalse = function(){
		$scope.income = false;
	};
	
	$scope.changeTransfer = function(){
		if($scope.transfer){
			$scope.transfer = false;
		}else{
			$scope.transfer = true;
		} 
	};
	
	$scope.set = function(value){
		$scope.amount += value;
	};
	
	$scope.getAccounts = function(){
		$scope.accountsHide = false;
	};
	
	$scope.closeGetAccounts = function(){
		$scope.accountsHide = true;
	};
	
	$scope.getTargetAccounts = function(){
		$scope.targetAccountsHide = false;
	};
	
	$scope.closeGetTargetAccounts = function(){
		$scope.targetAccountsHide = true;
	};
	
	$scope.getTypes = function(){
		$scope.typeHide = false;
	};
	
	$scope.closeGetTypes = function(){
		$scope.typeHide = true;
	};
	
	
	$scope.setCurrentAccount = function(id){
		angular.forEach($scope.accounts, function(acc){
			if(acc.id === id){
				$scope.currentAccount = acc;
				$scope.accountsHide = true;
			}
		});
	};
	
	$scope.setTargetAccount = function(id){
		angular.forEach($scope.accounts, function(acc){
			if(acc.id === id){
				$scope.targetAccount = acc;
				$scope.targetAccountsHide = true;
			}
		});
	};
	
	$scope.setTransfer = function(){
		if($scope.transfer){
			$scope.transfer = false;
		}else{
			$scope.transfer = true;
		}
	};
	
	$scope.setCurrentType = function(id){
		angular.forEach($scope.types, function(t){
			if(t.id === id){
				$scope.type = t;
				$scope.typeHide = true;
			}
		});
	};
	
	$scope.getDate = function(){
		console.log("show");
		$scope.dateHide = false;
	};
	
	$scope.closeGetDate = function(){
		console.log("close");
		$scope.dateHide = true;
	};
	
});