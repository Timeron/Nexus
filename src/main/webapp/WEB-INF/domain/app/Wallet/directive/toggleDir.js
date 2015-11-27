var app = angular.module("ToggleDir", []);

app.directive("toggle", function(){
	return {
		restrict: "AE",
		link: function(scope, element, attrs){

		},
		controller: function($scope, $element, $attrs){
			$attrs.$observe('hide', function(e) {
				if(e === "true"){
					$element.hide();
				}else{
					$element.toggle($attrs.toggletime);
				}
			});
		}
	
	};
});