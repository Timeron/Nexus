var app = angular.module('WalletMain', []);

app.controller('WalletMainCtrl', function($scope, GetAllAccountsAndRecords) {
	$scope.accounts = [];
	
	GetAllAccountsAndRecords.query({}, function(data) {
		if (data.success) {
			$scope.accounts = data.object;
		} else {
			$scope.errorMessage = data.message;
			return null;
		}

	});

});