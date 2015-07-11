/**
 * 
 */

var dir = angular.module('SearchDirective', []);

dir.directive("projectsearchpresentation", function(){
	return {
		restrict: "AE",
		template: "<div class='fog'><div>{{project.name}}</div></div><div>{{project.name}}</div>",
		link : function(scope, element, attrs){
			element.bind('mouseover', function(){
				element[0].childNodes[0].style.opacity = 0.0;
			});
			element.bind('mouseout', function(){
				element[0].childNodes[0].style.opacity = 0.2;
				
			});
			
		}
	};
});