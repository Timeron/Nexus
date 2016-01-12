var app = angular.module("AddEventCtrl", [ 'DropdownMultiselectDir' ]);

app.controller("AddEventCtrl", function($scope, $rootScope, AddEvent, GetContacts) {
	$scope.years = getIntegerList(1850, 2016);
	$scope.months = dataValueMonths;
	$scope.days = dataValueDays;
	
	$scope.eventName = "";
	$scope.description = "";
	$scope.eventYear = 0;
	$scope.eventMonth = 0;
	$scope.eventDay = 0;
	
	$scope.selected_items = [];
	$scope.selected_objects = [];
	$scope.contactList = [];
	
	GetContacts.query({}, function(data) {
		if (data.success) {
			angular.forEach(data.object, function(c){
				var name = c.firstName+" "+c.lastName;
				if(c.nick !== ""){
					name = name+" / "+c.nick;
				}
				contact = {id:c.id, name:name, assignable:true};
				$scope.contactList.push(contact);
			});
		}
	});
	
	
	$scope.roles = $scope.contactList;
	
	$scope.$watch("selected_items",
			function(e) {
				$scope.selected_objects = [];
				angular.forEach(e, function(item){
					angular.forEach($rootScope.contacts, function(c){
						if(item === c.id){
							$scope.selected_objects.push(c);
						}
					});
				});
			}, true);

	$scope.addEvent = function(){
		AddEvent.query(
				{
					name: $scope.eventName,
					description: $scope.eventDescription,
					eventYear: $scope.eventYear,
					eventMonth: $scope.eventMonth,
					eventDay: $scope.eventDay,
					contacts: $scope.selected_objects
				}
			);
	};
	


});
