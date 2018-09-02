var app = angular.module('WalletMain', []);

app.controller('WalletMainCtrl', function($scope, $rootScope, GetAllAccountsAndRecords, CurrentUser, CurrentUserPOST, GetRecordsForAccountByDayPOST, GetSumForAccountByType, GetSumForAccountByParentType, GetAllRecordTypes, GetSumForTypeInTypeHierarchy, GetSumForTypeForStatistics, GetSumForTypesForStatistics, GetRecordsByDay) {
	$scope.accounts = [];
	$rootScope.selectedAccount;
	$scope.user = "-";
	$scope.userPOST = "-";
	$scope.data = [];
	$scope.pieData = [];
	$scope.subPieData = [];
	$scope.typeStatisticData = [];
	$rootScope.types = GetAllRecordTypes.query();
	$scope.selectedType;
	$scope.selectedTypeTemp;
	$scope.incomeViewFlag = "false";
	$rootScope.recordToEdit = {};
	$scope.testtest = [];
	$scope.calendarData = [];
	$scope.calendarYear = (new Date()).getFullYear();
	$scope.calendarMonth = (new Date()).getMonth() + 1; //get month (value is counted from 0)
	$scope.calendarMonthMap = ["Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"];
	
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
		$rootScope.selectedAccount = account;
		
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
		
		GetSumForTypeForStatistics.query({account: account.id, type:$scope.selectedType, income: $scope.incomeViewFlag}, function(data){ //9
			$scope.typeStatisticData = data;
		});
		
	};
	
	$scope.getStats = function(income){
		
		if(income !== $scope.incomeViewFlag){
			$scope.incomeViewFlag = income;
		
			GetSumForTypeInTypeHierarchy.query({accountId: $rootScope.selectedAccount.id, income: income}, function(data){
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
	
	$scope.pieCliked = function(d){
		if($scope.selectedType === undefined || d.order!==$scope.selectedType.id){
			angular.forEach($scope.types, function(t){
				if(t.id === d.order){
					$scope.selectedType = t;
					$scope.selectedTypeTemp = t;
				}
			});
			GetSumForTypeForStatistics.query({account: $rootScope.selectedAccount.id, type:$scope.selectedType.id, income: $scope.incomeViewFlag}, function(data){ //9
				cleanCanvas(".typeStatisticChart");
				$scope.typeStatisticData = data;
			});
		};
	};
	
	$scope.multiBarChartSearch = function(){
		var types = [];
		angular.forEach($scope.types, function(t){
			if(t.multiBarChartMenu == true){
				types.push(t.id);
			}
		});
		console.log(types);

		GetSumForTypesForStatistics.query({account: 1, types: types, income: false}, function(d){
			var data = [];
			angular.forEach(d.object.kayValues, function(o){
				var row = {};
				row['key'] = o.key;
				angular.forEach(o.values, function(v){
					row[v.key] = v.value;
				});
				data.push(row);
			});
			$scope.testtest.chart = data;
			$scope.testtest.property = d.object.property;
		});
	};
	
	$scope.loadTypeStats = function(type){
		$scope.selectedType = type;
		GetSumForTypeForStatistics.query({account: $rootScope.selectedAccount.id, type:$scope.selectedType.id, income: $scope.incomeViewFlag}, function(data){ //9
			cleanCanvas(".typeStatisticChart");
			$scope.typeStatisticData = data;
		});
	};
	
	$scope.setToEdit = function(record){
		$rootScope.recordToEdit = record;
	};
	
	$('#Charts').tab('show');

	$scope.test = function(){
		console.log("Dupa");
	};
	
	$rootScope.getTypeName = function(typeId){
		var typeName = "";
		angular.forEach($scope.types, function(entry){
			if(entry.id === typeId){
				typeName = entry.name;
			}
		});
		return typeName;
	};
	
	$scope.calendar = function(){
		GetRecordsByDay.query({accountId: $rootScope.selectedAccount.id, year: $scope.calendarYear, month: $scope.calendarMonth}, function(data){
			if (data.success) {
				$scope.calendarData = data.object;
			} else {
				$scope.errorMessage = data.message;
				return null;
			}
		});
	};
	
	$scope.goBack = function(){
		$scope.calendarMonth--;
		if($scope.calendarMonth <= 0){
			$scope.calendarMonth = 12;
			$scope.calendarYear--;
		}
		console.log($scope.calendarYear +" - "+$scope.calendarMonth );
		$scope.calendar();
	};
	
	$scope.goNext = function(){
		$scope.calendarMonth++;
		if($scope.calendarMonth > 12){
			$scope.calendarMonth = 1;
			$scope.calendarYear++;
		}
		console.log($scope.calendarYear +" - "+$scope.calendarMonth );
		$scope.calendar();
	};
	
});

var cleanCanvas = function(object){
	d3.select(object).selectAll("svg").remove();
};