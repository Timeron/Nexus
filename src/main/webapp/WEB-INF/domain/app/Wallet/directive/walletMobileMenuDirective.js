/**
 * 
 */

var app = angular.module('ViewDirective', []);

app.directive("income", function(){
	return {
		restrict: "AE",
		link : function(scope, element, attrs){
			attrs.$observe('income', function(e) {
				if(e==="true"){
					element[0].style.color = "#8cbfff";
				}else{
					element[0].style.color = "#ffffff";
				}
			});
		}
	};
});

app.directive("popupmenu", function(){
	return {
		restrict: "AE",
		link : function(scope, element, attrs){
			attrs.$observe('hide', function(e) {
				if(e==="true"){
					element.hide();
				}else{
					element.show();
				}
			});
		}
	};
});

app.directive("transfericon", function(){
	return {
		restrict: "AE",
		link : function(scope, element, attrs){
			attrs.$observe('transfericon', function(e) {
				if(e === "false"){
					element.attr('class', 'transferIcon glyphicon glyphicon-random');
//					attrs.$set('class', 'transferIcon glyphicon glyphicon-random');
				}else{
					element.attr('class', 'transferIcon glyphicon glyphicon-share');
//					attrs.$set('class', 'glyphicon glyphicon-share');
				}
			});
		}
	};
});