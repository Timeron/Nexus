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

dir.directive("taskinline", function(){
	return {
		restrict: "AE",
		link : function(scope, element, attrs){
			if((attrs.index % 2) > 0){
				element[0].style.backgroundColor = "#F5F5F5";
			};
			element.bind('mouseover', function(){
				if(attrs.marked !== 'true'){
					element[0].style.backgroundColor = "#E0FFE0";
				}
			});
			element.bind('mouseout', function(){
				if(attrs.marked !== 'true'){
					if((attrs.index % 2) > 0){
						element[0].style.backgroundColor = "#F5F5F5";
					}else{
						element[0].style.backgroundColor = "#FFF";
					}
				}
			});
		},
	};
});
