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
	$scope.message = "";
	
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
				if(data.success){
					alert("Rekord dodany");
				}else{
					alert(data.message);
				}
				
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
		$scope.amount = 0;
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
	
	var calculate = function(valueStr){
		if(valueStr.toString().indexOf("+") > -1){
			var v = valueStr.split("+");
			value = parseFloat(v[0])+parseFloat(v[1]);
			console.log(value);
			return value;
		}
		if(valueStr.toString().indexOf("-") > -1){
			var v = valueStr.split("-");
			value = parseFloat(v[0])-parseFloat(v[1]);
			console.log(value);
			return value;
		}
		if(valueStr.toString().indexOf("*") > -1){
			var v = valueStr.split("*");
			value = parseFloat(v[0])*parseFloat(v[1]);
			console.log(value);
			return value;
		}
		if(valueStr.toString().indexOf("/") > -1){
			var v = valueStr.split("/");
			value = (parseFloat(v[0])/parseFloat(v[1])).toFixed(2);
			console.log(value);
			return value;
		}
		return valueStr;
	};
	
	$scope.set = function(value){
		if(value==="C"){
			$scope.amount = $scope.amount.toString().substring(0, $scope.amount.toString().length-1);
		}else{
			if($scope.amount !== ""){
				console.log("$scope.amount "+$scope.amount);
				if(value === "=" || value === "+" || value === "-" || value === "*" || value === "/"){
					if(value=="="){
						value="";
					}
					$scope.amount = calculate($scope.amount);
					$scope.amount = $scope.amount.toString()+value;
				}else{
					$scope.amount = $scope.amount.toString() + value;
				}
				
			}else{
				$scope.amount = value;
			}
		}
		
//		console.log(value);
//		if($scope.amounts != undefined && $scope.amounts.indexOf("+") > -1){
//			var v = $scope.amounts.split("+");
//			$scope.amount = v[0]+v[1];
//		}else{
//			$scope.amount += value;
//		}
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