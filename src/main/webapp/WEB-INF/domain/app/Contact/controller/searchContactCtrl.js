var app = angular.module("SearchContact", []);

app.controller("SearchContactCtrl", function($scope, $rootScope, GetContactDetails){
	$scope.Stest = "Stest!!!";
	
	$scope.setContact = function(contact){
		GetContactDetails.query({contactId: contact.id}, function(data) {
			if (data.success) {
				$rootScope.contact = data.object;
				$rootScope.contactSectionHide = "false";
			}
		});
	};
});