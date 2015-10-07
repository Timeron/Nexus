var app = angular.module("Car", ['Directive', 'DatePicker', 'CarService']);

app.controller("CarMainCtrl", function($scope, $rootScope, AddRecord, GetRecords){
	var messages = ["Operacje", "Ilość litrów", "Dodaj dystans"];
	$scope.oilMode = false;
	$scope.oil = "0";
	$scope.distance = "0";
	$scope.date = "10-04-2015";
	$scope.message = messages[0];
	$scope.operations = GetRecords.query({}, function(data){});
	
	$scope.oilCalculatorHide = true;
	$scope.distanceCalculatorHide = true;
	$scope.dateHide = true;
	
	$scope.operationDate = new Date();

	$scope.newRecord = function(){
		AddRecord.query({
			liters : $scope.oil,
			distance : $scope.distance,
			date : $scope.operationDate
		}, function(data){
			if(date.success === "true"){
				$scope.oil = "0";
				$scope.distance = "0";
				$scope.operationDate = new Date();
			}
			$scope.message = data.messages[0];
			$scope.operations = GetRecords.query({}, function(data){});
			
		});
	};
	
	$scope.changeOilMode = function(val){
		if(val === 'true'){
			$scope.oilMode = true;
		}else{
			$scope.oilMode = false;
		}
	};
	
	$scope.getOil = function(){
		$scope.distanceCalculatorHide = true;
		$scope.oilCalculatorHide = false;
		$scope.message = messages[1];
	};
	
	$scope.getDistance = function(){
		$scope.oilCalculatorHide = true;
		$scope.distanceCalculatorHide = false;
		$scope.message = messages[2];
	};
	
	$scope.setOil = function(val){
		if($scope.oil === '0'){
			$scope.oil = "";
		}
		$scope.oil += val;
	};
	
	$scope.setDistance = function(val){
		if($scope.distance === '0'){
			$scope.distance = "";
		}
		$scope.distance += val;
	};
	
	$scope.getDate = function(){
		$scope.distanceCalculatorHide = true;
		$scope.oilCalculatorHide = true;
		$scope.dateHide = false;
	};
	
	$scope.closeGetDate = function(){
		$scope.operationDate = 0;
		$scope.operationDate = new Date();
		$scope.dateHide = true;
	};
	
	$scope.setGetDate = function(){
		$scope.dateHide = true;
	};
	
	$scope.cleanNewRecord = function(){
		$scope.operationDate = 0;
		$scope.oil = "0";
		$scope.distance = "0";
		$scope.operationDate = new Date();
	};
	

});