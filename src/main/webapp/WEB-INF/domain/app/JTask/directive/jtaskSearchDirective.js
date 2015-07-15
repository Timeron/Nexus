/**
 * 
 */

var dir = angular.module('SearchDirective', []);

dir.directive("projectsearchpresentation", function(){
	return {
		restrict: "AE",
		template: "<div class='fog' data-ng-click='openProject(project)'><div><div class='name'>{{project.name}}</div><div>{{project.description}}</div></div>",
		link : function(scope, element, attrs){
			element.bind('mouseover', function(){
				element[0].childNodes[0].style.backgroundColor = "rgba(0, 0, 0, 0)";
			});
			element.bind('mouseout', function(){
				element[0].childNodes[0].style.backgroundColor = "rgba(0, 0, 0, 0.3)";
				
			});
			element.bind('mousedown', function(){
				element[0].childNodes[0].style.backgroundColor = "rgba(0, 0, 0, 0.8)";
			});
			
		}
	};
});