var datepicker = angular.module("DatePicker", []);

datepicker.directive("datepicker", function(){
	var currentYear = new Date().getFullYear();
	var currentMonth = new Date().getMonth();
	var currentDay = new Date().getDate();
	var months = [
	             	{id: 0, name: "Styczeń", days: 31},
	             	{id: 1, name: "Luty", days: 29}, 
	             	{id: 2, name: "Marzec", days: 31}, 
	             	{id: 3, name: "Kwiecień", days: 30}, 
	             	{id: 4, name: "Maj", days: 31}, 
	             	{id: 5, name: "Czerwiec", days: 30}, 
	             	{id: 6, name: "Lipiec", days: 31}, 
	             	{id: 7, name: "Sierpień", days: 31},
	             	{id: 8, name: "Wrzesień", days: 30},
	             	{id: 9, name: "Padźiernik", days: 31},
	             	{id: 10, name: "Listopad", days: 30},
	             	{id: 11, name: "Grudzień", days: 31}
	            ];
	return {
		restrict: "E",
		template: 	'<div class="form-inline">'+
					'<select class="form-control input-sm" data-ng-change="datepickerChange()" ng-model="year" ng-options="y for y in years" ng-disabled="datepickerDisabled"></select>'+
					'<select class="form-control input-sm" data-ng-change="datepickerChange()" ng-model="month" ng-options="m.name for m in months" ng-disabled="datepickerDisabled"></select>'+
					'<select class="form-control input-sm" data-ng-change="datepickerChange()" ng-model="day" ng-options="d for d in days" ng-disabled="datepickerDisabled"></select>'+
					'</div>',
		replace: true,
		link: function(scope, element, attrs){
			scope.months = months;
			scope.years = [];
			scope.days = [];
			var start = 0;
			var range = 0;
			var offset = 0;
			
			if(attrs.start === 0 || attrs.start === undefined){
				start = currentYear;
			}else{
				start = parseInt(attrs.start);
			}
			
			if(attrs.range === 0 || attrs.range === undefined){
				range = currentYear+10;
			}else{
				range = parseInt(attrs.range);
			}
			
			if(attrs.offset === 0 || attrs.offset === undefined){
				offset = 1;
			}else{
				offset = parseInt(attrs.offset);
			}
			console.log(start);
			console.log(range);
			console.log(offset);
			
            for (var i = start; i < range+1; i += offset){
                scope.years.push(i);
            }
            for (var i = 1; i <= months[currentMonth].days; i++){
            	scope.days.push(i);
            }
            if(attrs.init !== ""){ //jeśli nie jest ustawiona data to pomijamy
	            if(attrs.init !== undefined){
					var init = new Date(attrs.init);
					scope.year = init.getFullYear();
					scope.month = months[init.getMonth()];
					scope.day = init.getDate();
				}else{
					scope.year = currentYear;
		            scope.month = months[currentMonth];
		            scope.day = currentDay;
				}
	            scope[attrs.model] = new Date(scope.year, scope.month.id, scope.day, 0, 0, 0, 0).getTime();
            }
            scope.datepicker = attrs.model;
		},
		controller: function($scope, $element, $attrs){
			$scope.datepickerChange = function(){
				$scope[$scope.datepicker] = new Date($scope.year, $scope.month.id, $scope.day, 0, 0, 0, 0).getTime();
			};
			$attrs.$observe('disable', function(e) {
				if(e === "false"){
					$scope.datepickerDisabled = "disabled";
				}else{
					$scope.datepickerDisabled = "";
				}
			});
			$scope.$watch();
		}
	};
});

datepicker.directive("timepicker", function(){
	var currentHour = 0;
	var currentMinute = 0;
	return {
		restrict: "E",
		template: 	'<div class="form-inline">'+
					'<select class="form-control input-sm" data-ng-change="timepickerChange()" ng-model="hour" ng-options="h for h in hours" ng-disabled="datepickerDisabled"></select> : '+
					'<select class="form-control input-sm" data-ng-change="timepickerChange()" ng-model="minute" ng-options="m for m in minutes" ng-disabled="datepickerDisabled"></select>'+
					'</div>',
		link: function(scope, element, attrs){
			scope.hours = [];
			scope.minutes = [];
			if(attrs.now === "true"){
				currentHour = new Date().getHours();
				currentMinute = new Date().getMinutes();
			}
			for(var i = 0; i<24; i++){
				scope.hours.push(i);
			}
			for(var i = 0; i<60; i++){
				scope.minutes.push(i);
			}
			
			if(attrs.init !== undefined && attrs.init !== ""){
				var init = new Date(attrs.init);
				scope.hour = init.getHours();
				scope.minute = init.getMinutes();
			}else{
				scope.hour = currentHour;
				scope.minute = currentMinute;
			}
			
			scope.timepicker = attrs.model;
			scope[attrs.model] = ((scope.hour*60) + scope.minute)*60*1000;
		},
		controller: function($scope){
			$scope[$scope.timepicker] = 0;
			$scope.timepickerChange = function(){
				$scope[$scope.timepicker] = (($scope.hour*60) + $scope.minute)*60*1000;
			};
		}
	};
});

datepicker.directive("postponedpicker", function(){
	return {
		restrict: "E",
		template: 	'<div class="form-inline">'+
					'<div>Dni: <select class="form-control input-sm" data-ng-change="postponedpickerChange()" ng-model="postponedDay" ng-options="d for d in postponedDays" ng-disabled="datepickerDisabled"><option value=""></option></select> '+
					'Godzin: <select class="form-control input-sm" data-ng-change="postponedpickerChange()" ng-model="postponedHours" ng-options="h for h in hours" ng-disabled="datepickerDisabled"><option value=""></option></select></div>'+
					'</div>',
		link: function(scope, element, attrs){
			scope.hours = [];
			scope.postponedDays = [];
			for(var i = 0; i<24; i++){
				scope.hours.push(i);
			}
			for(var i = 0; i<100; i++){
				scope.postponedDays.push(i);
			}
			var day = Math.floor(attrs.init / 86400000);
			var hours = (attrs.init % 86400000)/(1000*60*60);
			scope.postponedDay = day;
			scope.postponedHours = hours;
			scope.postponedpicker = attrs.model;
		},
		controller: function($scope){
			$scope.postponedpickerChange = function(){
				$scope[$scope.postponedpicker] = (($scope.postponedDay*24) + $scope.postponedHours)*60*60*1000;
			};
		}
	};
});
