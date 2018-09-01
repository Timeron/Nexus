var app = angular.module('CalendarMonthDir', []);

app.directive("calendarDay", function($rootScope){
	
	return {
		restrict: "AE",
		template: 	'<div class="calendarDayContent day{{key}}"><div class="key">{{key}}</div>'+
		'<div ng-repeat="value in values"><div class="record income_{{value.income}}" style="background-color: {{getTypeColor(value.recordTypeId)}};" title="{{getTypeName(value.recordTypeId)}} &#013;{{value.value}} zł">'+
		'<span class="glyphicon {{getTypeIcon(value.recordTypeId)}}" aria-hidden="true"></span>'+
		'<div></div>'+
		'</div>',
		scope: {
			key: "=",
			values: "="
					
		},
		link : function(scope, element, attrs){
			
		},
		controller : function($scope, $element, $attrs) {
			console.log("!!!!!!!!!!!!"+ $rootScope.types);
		
			$scope.getTypeColor = function(id){
				var type = getType(id);
				return type.color;
			}
			
			$scope.getTypeName = function(id){
				var type = getType(id);
				return type.name;
			}
			
			$scope.getTypeIcon = function(id){
				var type = getType(id);
				return type.icon;
			}
			
			var getType = function(id){
				var type;
				angular.forEach($rootScope.types, function(t){
					if(id === t.id){
						type = t;
					}
				});
				return type;
			}
		}
		
		
	};
});