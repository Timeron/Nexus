var app = angular.module("ChartDir", []);

app.directive("linechart", function() {
	return {
		restrict : "AE",
		link : function(scope, element, attrs) {

		},
		controller : function($scope, $element, $attrs) {
			$attrs.$observe('data', function(e) {

				var data = $.parseJSON(e);
				if (Object.prototype.toString.call(data) === '[object Array]'
						&& data.length > 0) {

					d3.select(".lineChart").selectAll("svg").remove();

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

					var xAxis = d3.svg.axis().scale(x).orient("bottom")
							.innerTickSize(-height).outerTickSize(0)
							.tickPadding(10);

					var yAxis = d3.svg.axis().scale(y).orient("left")
							.innerTickSize(-width).outerTickSize(0)
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

app
		.directive(
				"piechart",
				function() {

					return {
						restrict : "AE",
						// template : "<div class='pieChart'></div>",
						link : function(scope, element, attrs) {
//							var r = attrs.r, ir = attrs.ir, width = attrs.width, height = attrs.height;
//							var pie = d3.layout.pie().sort(null).value(
//									function(d) {
//										return d.value;
//									});
//
//							// kontener svg
//							var svg = d3.select(".pieChart").append("svg")
//									.attr("width", width)
//									.attr("height", height).append("g").attr(
//											"transform",
//											"translate(" + width / 2 + ","
//													+ height / 2 + ")");
//
//							var arc = d3.svg.arc().outerRadius(r).innerRadius(
//									ir);
//
//							data.forEach(function(d) {
//								d.value = +d.value;
//							});
//
//							var g = svg.selectAll(".arc").data(pie(data))
//									.enter().append("g").attr("class", "arc");
//
//							g.append("path").attr("d", arc).style("fill",
//									function(d) {
//										return d.data.color;
//									});
//
//							g.append("text").attr("transform", function(d) {
//								return "translate(" + arc.centroid(d) + ")";
//							}).attr("dy", ".35em").style("text-anchor",
//									"middle").text(function(d) {
//								return d.data.type;
//							});
						},
						scope : {
							r : "=",
							ir : "=",
							width : "=",
							height : "="
						},
						controller : function($scope, $element, $attrs) {
							$attrs.$observe('data', function(e) {
								var data = $.parseJSON(e);
								var data1 = [ {
									key : "<5",
									value : 2704659,
									color : "#98abc5"
								}, {
									key : "5-13",
									value : 4499890,
									color : "#8a89a6"
								}, {
									key : "14-17",
									value : 2159981,
									color : "#7b6888"
								}, {
									key : "18-24",
									value : 3853788,
									color : "#6b486b"
								}, {
									key : "25-44",
									value : 14106543,
									color : "#00F"
								}, {
									key : "45-64",
									value : 8819342,
									color : "#d0743c"
								}];
//								if(data.lenght > 0){
									var r = $attrs.r, ir = $attrs.ir, width = $attrs.width, height = $attrs.height;
									var pie = d3.layout.pie().sort(null).value(
											function(d) {
												return d.value;
											});
	
									// kontener svg
									var svg = d3.select(".pieChart").append("svg")
											.attr("width", width)
											.attr("height", height).append("g").attr(
													"transform",
													"translate(" + width / 2 + ","
															+ height / 2 + ")");
	
									var arc = d3.svg.arc().outerRadius(r).innerRadius(
											ir);
	
									console.log(data);
									data.forEach(function(d) {
										d.value = +d.value;
									});
	
									var g = svg.selectAll(".arc").data(pie(data))
											.enter().append("g").attr("class", "arc");
	
									g.append("path").attr("d", arc).style("fill",
											function(d) {
												return d.data.color;
											});
	
									g.append("text").attr("transform", function(d) {
										return "translate(" + arc.centroid(d) + ")";
									}).attr("dy", ".35em").style("text-anchor",
											"middle").text(function(d) {
										return d.data.key;
									});
//								}
							});
						}
					};
				});