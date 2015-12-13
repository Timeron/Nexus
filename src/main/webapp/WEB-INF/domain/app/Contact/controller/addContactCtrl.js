var app = angular.module("AddContactCtrl", []);

app.controller("AddContactCtrl", function($scope, AddContact){
	$scope.years = getIntegerList(1900, 2016);
	$scope.months = dataValueMonths;
	$scope.days = dataValueDays;
	
	$scope.firstName = "";
	$scope.lastName = "";
	$scope.pseudo = "";
	$scope.emailPrv = "";
	$scope.emailOffice = "";
	$scope.phone1 = "";
	$scope.phone2 = "";
	$scope.phone3 = "";
	$scope.address = "";
	$scope.city = "";
	$scope.country = "";
	$scope.birthdayYear = "";
	$scope.birthdayMonth = "";
	$scope.birthdayDay = "";
	$scope.nameDayMonth = "";
	$scope.nameDayDay = "";
	$scope.description = "";
	$scope.tags = "";
	
	$scope.addContact = function(){
		AddContact.query({
			firstName: $scope.firstName,
			lastName: $scope.lastName,
			pseudo: $scope.pseudo,
			emailPrv: $scope.emailPrv,
			emailOffice: $scope.emailOffice,
			phone1: $scope.phone1,
			phone2: $scope.phone2,
			phone3: $scope.phone3,
			address: $scope.address,
			city: $scope.city,
			country: $scope.country,
			birthdayYear: $scope.birthdayYear,
			birthdayMonth: $scope.birthdayMonth,
			birthdayDay: $scope.birthdayDay,
			nameDayMonth: $scope.nameDayMonth,
			nameDayDay: $scope.nameDayDay,
			description: $scope.description,
			tags: $scope.tags
		}, function(data){

		});
	};
});

var getIntegerList = function(start, end){
	var years = [];
	for(var i=start; i<=end; i++){
		years.push(i);
	}
	return years;
};