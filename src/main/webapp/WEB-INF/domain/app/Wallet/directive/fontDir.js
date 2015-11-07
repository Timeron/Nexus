var app = angular.module("FontDir", []);

app.directive("changeFontColor", function() {
	return {
		restrict : "A",
		link : function(scope, element, attrs) {

		},
		controller : function($scope, $element, $attrs) {
			$attrs.$observe('valid', function(e) {
				if (e === "true") {
					$element[0].style.color = $attrs.value;
				}
			});
		}
	};
})

.directive("markLine", function(){
	return {
		restrict: "AE",
		link: function(scope, element, attrs){
			element.bind('mouseover', function(){
				element[0].style.backgroundColor = attrs.mark;
			});
			element.bind('mouseout', function(){
				element[0].style.backgroundColor = attrs.nmark;
			});
//			element.attr('data-dismiss', 'modal');
			
		}
	};
});