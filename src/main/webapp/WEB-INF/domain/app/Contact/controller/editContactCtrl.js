var app = angular.module("EditContactCtrl", []);

app.controller("EditContactCtrl", function($scope, $rootScope, UpdateContact){
	$scope.years = getIntegerList(1900, 2016);
	$scope.months = dataValueMonths;
	$scope.days = dataValueDays;
	
	$rootScope.setTempContact = function(){
		$scope.tempContact = $rootScope.contact;
		var birthday = $rootScope.contact.birthday.split(" ");
		var nameday = $rootScope.contact.nameday.split(" ");
		$scope.tempContact.birthdayYear = parseInt(birthday[2]);
		$scope.tempContact.birthdayMonth = plMonthToNumber(birthday[1]).id;
		$scope.tempContact.birthdayDay = parseInt(birthday[0]);
		$scope.tempContact.nameDayMonth = plMonthToNumber(nameday[1]).id;
		$scope.tempContact.nameDayDay = parseInt(nameday[0]);
		
		console.log($scope.tempContact.birthdayYear);
	};
	
	$scope.updateContact = function(){
		UpdateContact.query({
			id: $scope.tempContact.id,
			firstName: $scope.tempContact.firstName,
			lastName: $scope.tempContact.lastName,
			pseudo: $scope.tempContact.pseudo,
			emailPrv: $scope.tempContact.emailPrv,
			emailOffice: $scope.tempContact.emailOffice,
			phone1: $scope.tempContact.phone1,
			phone2: $scope.tempContact.phone2,
			phone3: $scope.tempContact.phone3,
			address: $scope.tempContact.address,
			city: $scope.tempContact.city,
			country: $scope.tempContact.country,
			birthdayYear: $scope.tempContact.birthdayYear,
			birthdayMonth: $scope.tempContact.birthdayMonth,
			birthdayDay: $scope.tempContact.birthdayDay,
			nameDayMonth: $scope.tempContact.nameDayMonth,
			nameDayDay: $scope.tempContact.nameDayDay,
			description: $scope.tempContact.description,
			tags: $scope.tempContact.tags
		}, function(data){
			
		});
	};
});

var getIntegerList = function(start, end){
	var years = [];
	for(var i=start; i<=end; i++){
		years.push(i);
	}
	console.log("Done");
	return years;
};

var plMonthToNumber = function(month){
	switch (month) {
	    case "stycznia":
	    	return 1;
	        break;
	    case "lutego":
	    	return 2;
	    	break;
	    case "marca":
	    	return dataValueMonths[2];
	        break;
	    case "kwietnia":
	    	return dataValueMonths[3];
	    	break;
	    case "maja":
	    	return dataValueMonths[4];
	        break;
	    case "czerwca":
	    	return dataValueMonths[5];
	    	break;
	    case "lipca":
	    	return dataValueMonths[6];
	        break;
	    case "sierpnia":
	    	return dataValueMonths[7];
	    	break;
	    case "września":
	    	return dataValueMonths[8];
	        break;
	    case "pażdziernika":
	    	return dataValueMonths[9];
	    	break;
	    case "listopada":
	    	return dataValueMonths[10];
	        break;
	    case "grudnia":
	    	return dataValueMonths[11];
	    	break;
}
};