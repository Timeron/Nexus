var app = angular.module("Directive", []);

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

app.directive("datetostring", function(){
	return{
		restrict: "AE",
		link : function(scope, element, attrs){
			attrs.$observe('date', function(e) {
				var d = new Date(parseInt(e));
				console.log(d.toString("dd-MM-yyyy"));
				scope.date = d.getFullYear()+" / "+d.getMonth()+" / "+d.getDate();
			});
		}
	};
});