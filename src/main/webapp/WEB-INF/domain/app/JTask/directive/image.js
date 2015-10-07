var app = angular.module("MPImage", []);

app.directive("mpImage", function(){
	return {
		restrict: "EA",
		template: "<img src='{{image}}' title='{{title}}'>",
		replease: true,
		link: function(scope, element, attrs){
			scope.title = attrs.title;
			attrs.$observe('mpImage', function(e) {
				scope.image = "/nexus/resources/image/avatar/"+e+"35.png";
			});
		}
	
	};
});