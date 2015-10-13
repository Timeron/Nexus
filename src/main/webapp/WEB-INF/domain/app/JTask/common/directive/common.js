var dir = angular.module('CommonJTaskDirective', [])

.directive("markLine", function(){
	return {
		restrict: "AE",
		link: function(scope, element, attrs){
			element.bind('mouseover', function(){
				element[0].style.backgroundColor = "#E0FFE0";
			});
			element.bind('mouseout', function(){
				element[0].style.backgroundColor = "#FFF";
			});
			element.attr('data-dismiss', 'modal');
			
		}
	};
});