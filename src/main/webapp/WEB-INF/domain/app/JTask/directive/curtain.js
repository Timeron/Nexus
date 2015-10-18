var app = angular.module("CurtainDir", []);

app.directive("curtain", function(){
	return {
		restrict: "AE",
		link: function(scope, element, attrs){

		},
		controller: function($scope, $element, $attrs){
			$attrs.$observe('hide', function(e) {
				console.log(e);
				if(e === "true"){
					$element.hide(500);
				}else{
					$element.toggle(500);
				}
			});
		}
	
	};
});