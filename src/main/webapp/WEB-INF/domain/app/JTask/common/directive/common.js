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
})

.directive("removeFromView", function(){
	return {
		restrict: "AE",
		controller: function($scope, $element, $attrs){
			$attrs.$observe('hide', function(e) {
				if(e === "false"){
					$element[0].hidden = true;
				}else{
					$element[0].hidden = false;
				}
			});
		}
	};
});