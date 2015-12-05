var app = angular.module("TypeDir", []);

app.directive("recordtypeicon", function(){
	return {
		restrict : "A",
		replace: true,
		template: "<div data-toggle='tooltip' data-placement='right' title='{{type.name}}' class='recordTypeIcon'><span class='glyphicon {{type.icon}}'></span></div>",
		link : function(scope, element, attrs) {
			scope.type = angular.fromJson(attrs.type);
			console.log(element[0].style.color);
			element[0].style.backgroundColor = scope.type.color;
			if(compareColor(element[0].style.backgroundColor, "rgb(80, 80, 80)") > 0){
				element[0].style.color = '#000';
			}else{
				element[0].style.color = '#FFF';
			}
		}
	};
});

function compareColor(color1, color2) {
    var digits1 = /(.*?)rgb\((\d+), (\d+), (\d+)\)/.exec(color1);
    var digits2 = /(.*?)rgb\((\d+), (\d+), (\d+)\)/.exec(color2);

    var red1 = parseInt(digits1[2]);
    var green1 = parseInt(digits1[3]);
    var blue1 = parseInt(digits1[4]);
    
    var red2 = parseInt(digits2[2]);
    var green2 = parseInt(digits2[3]);
    var blue2 = parseInt(digits2[4]);
    
    var colorSum1 = red1 + green1 + blue1;
    var colorSum2 = red2 + green2 + blue2;
    
    return colorSum1 - colorSum2;
};