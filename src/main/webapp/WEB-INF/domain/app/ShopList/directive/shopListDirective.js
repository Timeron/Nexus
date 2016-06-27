var app = angular.module("ShopListDirective", []);

app.directive("buy", function(){
	return {
		restrict: "AE",
		link: function(scope, element, attrs){

		},
		controller: function($scope, $element, $attrs){
			$attrs.$observe('buy', function(e) {
				if(e === "true"){
					$element[0].style.backgroundColor = "#A6A6FF";
					console.log($element[0].children[0].innerHTML = "<span class='glyphicon glyphicon-unchecked' aria-hidden='true'></span>");
				}else{
					$element[0].style.backgroundColor = "#55DD55";
					console.log($element[0].children[0].innerHTML = "<span class='glyphicon glyphicon-check' aria-hidden='true'></span>");
				}
			});
		}
	
	};
});
