var app = angular.module('WalletMain', []);

app.controller('WalletMainCtrl', function($scope, GetAllAccountsAndRecords, CurrentUser, CurrentUserPOST, GetRecordsForAccountByDayPOST, GetSumForAccountByType, GetSumForAccountByParentType ) {
	$scope.accounts = [];
	$scope.selectedAccount;
	$scope.user = "-";
	$scope.userPOST = "-";
	$scope.data = [];
	$scope.pieData = [];
	$scope.subPieData = [];
	
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
		
		GetRecordsForAccountByDayPOST.query({id: account.id}, function(d){
			$scope.data = d;
		});
		
		GetSumForAccountByType.query({accountId: account.id, income: "false"}, function(d){
			$scope.pieData = d;
		});
		
		GetSumForAccountByParentType.query({accountId: account.id, income: "false"}, function(d){
			$scope.subPieData = d;
		});
	};
	
	$scope.setTransferAccount = function(destinationAccountId, sourceAccountId, income){
		if(destinationAccountId != 0){
			var str = "";
			angular.forEach($scope.accounts, function(acc){
				if(income){
					if(acc.id == sourceAccountId){
						str = acc.name+" >>";
					}
				}else{
					if(acc.id == destinationAccountId){
						str = ">> "+acc.name;
					}
				}
				
			});
			return str;
		}else{
			return " -";
		}
	};

});