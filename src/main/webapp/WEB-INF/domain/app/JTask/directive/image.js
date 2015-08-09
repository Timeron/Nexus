var app = angular.module("MPImage", []);

app.directive("mpImage", function(){
	return {
		restrict: "EA",
		template: "<img src='{{image}}' title='scope.title'>",
		replease: true,
		link: function(scope, element, attrs){
			scope.image = "/timeron-nexus/resources/image/avatar/"+attrs.image+"35.png";
			scope.title = attrs.title;
		}
	};
});