/**
 * 
 */
var app = angular.module('CalendarMonthDir', []);

app.directive("calendarDay", function($rootScope){
	
	return {
		restrict: "AE",
		template: 	'<div class="calendarDayContent day{{key}}"><div class="key">{{key}}</div>'+
		'<div ng-repeat="value in values"><div class="record income_{{value.income}}" style="background-color: {{getTypeColor(value.recordTypeId)}};" title="{{getTypeName(value.recordTypeId)}} 1&#013;{{value.value}} zł">'+
		'<span class="glyphicon {{getTypeIcon(value.recordTypeId)}}" aria-hidden="true"></span>'+
		'<div></div>'+
		'</div>',
		scope: {
			key: "=",
			values: "="
//			types: "="
					
		},
		link : function(scope, element, attrs){
			
		},
		controller : function($scope, $element, $attrs) {
			console.log("!!!!!!!!!!!!"+ $rootScope.types);
//			$attrs.$observe('key', function(e) {
//				console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!111111"+ $scope.key);
//			});
//			$attrs.$observe('values', function(e) {
//				console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!222222"+ e);
//			});
			
			$scope.getTypeColor = function(id){
				return $rootScope.types[id].color;
			}
			
			$scope.getTypeName = function(id){
				return $rootScope.types[id].name;
			}
			
			$scope.getTypeIcon = function(id){
				return $rootScope.types[id].icon;
			}
		}
	};
});