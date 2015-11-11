var app = angular.module("ChartDir", []);

app.directive("linechart", function() {
	return {
		restrict : "AE",
		link : function(scope, element, attrs) {
			console.log(attrs);
		},
		controller : function($scope, $element, $attrs) {
			$attrs.$observe('data', function(e) {
				console.log(e);
				var data = $.parseJSON(e);
				if (Object.prototype.toString.call(data) === '[object Array]' && data.length > 0) {

					d3.select("svg").remove();
					
					data = $.parseJSON(e);

					var margin = {
						top : 20,
						right : 20,
						bottom : 30,
						left : 50
					}, width = 960 - margin.left - margin.right, height = 450
							- margin.top - margin.bottom;

					var parseDate = d3.time.format("%Y-%b-%d").parse;

					var x = d3.time.scale().range([ 0, width ]);

					var y = d3.scale.linear().range([ height, 0 ]);

					var xAxis = d3.svg.axis().scale(x).orient("bottom").innerTickSize(-height)
				    .outerTickSize(0)
				    .tickPadding(10);

					var yAxis = d3.svg.axis().scale(y).orient("left").innerTickSize(-width)
				    .outerTickSize(0)
				    .tickPadding(10);

					var area = d3.svg.area().x(function(d) {
						return x(d.key);
					}).y0(height).y1(function(d) {
						return y(d.value);
					});

					var svg = d3.select(".lineChart").append("svg").attr(
							"width", width + margin.left + margin.right).attr(
							"height", height + margin.top + margin.bottom)
							.append("g").attr(
									"transform",
									"translate(" + margin.left + ","
											+ margin.top + ")");

					data.forEach(function(d) {
						d.key = parseDate(d.key);
						d.value = +d.value;
					});

					x.domain(d3.extent(data, function(d) {
						return d.key;
					}));
					y.domain([ 0, d3.max(data, function(d) {
						return d.value;
					}) ]);

					svg.append("path").datum(data).attr("class", "area").attr(
							"d", area);

					svg.append("g").attr("class", "x axis").attr("transform",
							"translate(0," + height + ")").call(xAxis);

					svg.append("g").attr("class", "y axis").call(yAxis).append(
							"text").attr("transform", "rotate(-90)").attr("y",
							6).attr("dy", ".71em").style("text-anchor", "end")
							.text("Price ($)");
				}
			});
		}
	};

});