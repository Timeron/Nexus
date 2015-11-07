var app = angular.module('WalletMain', []);

app.controller('WalletMainCtrl', function($scope, GetAllAccountsAndRecords, CurrentUser, CurrentUserPOST) {
	$scope.accounts = [];
	$scope.selectedAccount;
	$scope.user = "-";
	$scope.userPOST = "-";
	
	CurrentUser.query({}, function(data){
		$scope.user = data;
		
	});
	
	CurrentUserPOST.query({}, function(data){
		$scope.userPOST = data;
		
	});
	
	GetAllAccountsAndRecords.query({}, function(data) {
		if (data.success) {
			$scope.accounts = data.object;
		} else {
			$scope.errorMessage = data.message;
			return null;
		}

	});
	
	$scope.selectAccount = function(account){
		$scope.selectedAccount = account;
	};
	
	$scope.setTransferAccount = function(accountId){

		if(accountId !== 0){
			angular.forEach($scope.accounts, function(acc){
				if(acc.id = accountId){
					return acc.name;
				}
			});
		}else{
			return " -";
		}
	};

});