var app = angular.module('WalletMain', []);

app.controller('WalletMainCtrl', function($scope, GetAllAccountsAndRecords, CurrentUser, CurrentUserPOST, GetRecordsForAccountByDayPOST, GetSumForAccountByType, GetSumForAccountByParentType, GetAllRecordTypes, GetSumForTypeInTypeHierarchy) {
	$scope.accounts = [];
	$scope.selectedAccount;
	$scope.user = "-";
	$scope.userPOST = "-";
	$scope.data = [];
	$scope.pieData = [];
	$scope.subPieData = [];
	$scope.types = GetAllRecordTypes.query();
	$scope.incomeViewFlag = "false";
	
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
		
//		GetSumForAccountByType.query({accountId: account.id, income: "false"}, function(d){
//			$scope.pieData = d;
//		});
//		
//		GetSumForAccountByParentType.query({accountId: account.id, income: "false"}, function(d){
//			$scope.subPieData = d;
//		});
		
		GetSumForTypeInTypeHierarchy.query({accountId: account.id, income: $scope.incomeViewFlag}, function(data){
			$scope.pieData = [];
			$scope.subPieData = [];
			cleanCanvas(".pieChart");

			angular.forEach(data, function(d){
				$scope.subPieData.push(d);
				if(d.children.length > 0){
					angular.forEach(d.children, function(child){
						if(child.value !== "0"){
							$scope.pieData.push(child);
						}
					});
				}
			});
		});
	};
	
	$scope.getStats = function(income){
		
		if(income !== $scope.incomeViewFlag){
			$scope.incomeViewFlag = income;
		
			GetSumForTypeInTypeHierarchy.query({accountId: $scope.selectedAccount.id, income: income}, function(data){
				cleanCanvas(".pieChart");
				$scope.pieData = [];
				$scope.subPieData = [];
				angular.forEach(data, function(d){
					$scope.subPieData.push(d);
					if(d.children.length > 0){
						angular.forEach(d.children, function(child){
							if(child.value !== "0"){
								$scope.pieData.push(child);
							}
						});
					}
				});
			});
		}
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
	
	$scope.recordTypeIcon = function(typeId){
		var type = "";
		angular.forEach($scope.types, function(t){
			if(typeId === t.id){
				type = t;
			}
		});
		return type;
	};
	
	$('#Charts').tab('show');

});

var cleanCanvas = function(object){
	d3.select(object).selectAll("svg").remove();
}