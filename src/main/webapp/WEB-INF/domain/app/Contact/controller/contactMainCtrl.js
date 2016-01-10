var app = angular.module("ContactMainCtrl", [ 'ContactService', 'CurtainDir' ]);

app.controller("ContactCtrl", function($scope, $rootScope, GetContacts, GetContactDetails) {
	$rootScope.contacts = [];
	$rootScope.contact;
	$rootScope.contactSectionHide = true;

	GetContacts.query({}, function(data) {
		if (data.success) {
			$rootScope.contacts = data.object;
		}
	});
	
	$scope.setContact = function(contact){
		GetContactDetails.query({contactId: contact.id}, function(data) {
			if (data.success) {
				$rootScope.contact = data.object;
				$rootScope.tempContact = data.object;
				$rootScope.contactSectionHide = "false";
			}
		});
	};

});