var app = angular.module("ContactMainCtrl", [ 'ContactService' ]);

app.controller("ContactCtrl", function($scope, GetContacts) {
	$scope.contacts = [];

	GetContacts.query({}, function(data) {
		if (data.success) {
			$scope.contacts = data.object;
		}
	});

});