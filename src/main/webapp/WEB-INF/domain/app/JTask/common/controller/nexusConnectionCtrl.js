var app = angular.module("NexusConnection", ['CommonJTaskDirective']);

app.controller("NexusConnectionCtrl", function($scope, $interval, $timeout, CheckConnection){
	$scope.error = false;
	$scope.connectionError = "";
	var checkConection = function(){
		message = "Problem z uzyskaniem połączenia z serwerem! Skontaktuj się z administratorem!";
		CheckConnection.query({}, function success(data){
			if(data.messages[0] === "1F4SD"){
				message = "";
				$scope.error = false;
			}else{
				message = data.messages+"!";
				$scope.error = true;
			}
			$scope.connectionError = message;
		}, function err(error){
			if(error.statusText !== ""){
				$scope.connectionError = error.statusText;
				$scope.error = true;
			}else{
				if(error.status == 0){
					$scope.connectionError = "Serwer nie odpowiada. Skontaktuj się z administratorem!";
					$scope.error = true;
				}
			}
		});
	};
	$interval(checkConection, 120000);

});