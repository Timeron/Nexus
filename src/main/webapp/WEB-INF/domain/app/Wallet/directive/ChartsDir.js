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
						},
						scope : {
							r : "=",
							ir : "=",
							width : "=",
							height : "=",
							nametext : "="
						},
						controller : function($scope, $element, $attrs) {
							
							var clicked = function(d){
								$scope.$parent.pieCliked(d.data);
							};
							
							var tip = d3.tip()
							  .attr('class', 'd3-tip')
							  .html(function(d) {
							    return "<strong>" + d.data.key + "</strong></br><strong>wartość:</strong> <span style='color:red'>" + d.value + "</span>";
							  });
							
							  
							  
							$attrs.$observe('data', function(e) {
								var data = $.parseJSON(e);
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
	
									svg.call(tip);
									
									var arc = d3.svg.arc().outerRadius(r).innerRadius(
											ir);
									var fullValue = 0;
									data.forEach(function(d) {
										d.value = +d.value;
										fullValue += d.value;
									});
	
									var g = svg.selectAll(".arc").data(pie(data))
											.enter().append("g").attr("class", "arc").on("click", clicked);
	
									g.append("path").attr("d", arc).style("fill",
											function(d) {
												return d.data.color;
											}).on('mouseover', tip.show)
										      .on('mouseout', tip.hide);
									
//									g.append("path").attr("d", arc).style("fill",
//											function(d) {
//												return d.data.color;
//											});
	
									g.append("text").attr("transform", function(d) {
										return "translate(" + arc.centroid(d) + ")";
									}).attr("dy", ".35em").style("text-anchor",
											"middle").text(function(d) {
												if($attrs.nametext === "true"){
													if((fullValue / d.value) > 20){
														return "";// d.data.key;
													}else{
														return d.data.key;
													};
												}else{
													return "";
												}
												
												
									});
//								}
							});
						}
					};
				});



app.directive("barchart", function(){
	return {
		restrict : "AE",
		link : function(scope, element, attrs) {
			
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
				
				var margin = {top: 40, right: 20, bottom: 30, left: 40},
			    width = 960 - margin.left - margin.right,
			    height = 500 - margin.top - margin.bottom;

				
				
//			var formatPercent = d3.format(".0%");

			var x = d3.scale.ordinal()
			    .rangeRoundBands([0, width], .1);

			var y = d3.scale.linear()
			    .range([height, 0]);

			var xAxis = d3.svg.axis()
			    .scale(x)
			    .orient("bottom");

			var yAxis = d3.svg.axis()
			    .scale(y)
			    .orient("left");
//			    .tickFormat(formatPercent);

			var tip = d3.tip()
			  .attr('class', 'd3-tip')
			  .offset([-10, 0])
			  .html(function(d) {
			    return "<strong>value:</strong> <span style='color:red'>" + d.value + "</span>";
			  });

			var svg = d3.select(".typeStatisticChart").append("svg")
			    .attr("width", width + margin.left + margin.right)
			    .attr("height", height + margin.top + margin.bottom)
			  .append("g")
			    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

			svg.call(tip);

//				var data = [{key: 'A',value: '.08167'},{key: 'D',value: '.03167'},{key: 'B',value: '.04167'},{key: 'C',value: '.02167'}];
				
			  x.domain(data.map(function(d) { return d.key; }));
			  y.domain([0, d3.max(data, function(d) { return Number(d.value); })]);
			  
			  svg.append("g")
			      .attr("class", "x axis")
			      .attr("transform", "translate(0," + height + ")")
			      .call(xAxis);

			  svg.append("g")
			      .attr("class", "y axis")
			      .call(yAxis)
			    .append("text")
			      .attr("transform", "rotate(-90)")
			      .attr("y", 6)
			      .attr("dy", ".71em")
			      .style("text-anchor", "end")
			      .text("value");

			  svg.selectAll(".bar")
			      .data(data)
			    .enter().append("rect")
			      .attr("class", "bar")
			      .attr("x", function(d) { return x(d.key); })
			      .attr("width", x.rangeBand())
			      .attr("y", function(d) { return y(d.value); })
			      .attr("height", function(d) { return height - y(d.value); })
			      .on('mouseover', tip.show)
			      .on('mouseout', tip.hide);



			function type(d) {
			  d.value = +d.value;
			  return d;
			}
			});
		}
	};
});

app.directive("multibarchart", function(){
	return {
		restrict : "AE",
		link : function(scope, element, attrs) {
			
		},
		controller : function($scope, $element, $attrs) {
			$attrs.$observe('data', function(e) {
				if(e !== ""){
				var object = $.parseJSON(e);
				var data = object;
				var colors = [];
				var property = $scope.testtest.property;
				angular.forEach(data[0], function(v,k){
					if(k !== "key"){
						colors.push(property[k]);
					}
				});
				console.log(colors);

				d3.select(".multiBarChart").selectAll("svg").remove();
				
			var margin = {top: 20, right: 20, bottom: 80, left: 40},
			    width = 960 - margin.left - margin.right,
			    height = 500 - margin.top - margin.bottom;
			
			var x = d3.scale.ordinal()
			    .rangeRoundBands([0, width], .15);

			var y = d3.scale.linear()
			    .rangeRound([height, 0]);

			var color = d3.scale.ordinal()
			    .range(colors);

			var xAxis = d3.svg.axis()
			    .scale(x)
			    .orient("bottom");
			
			var yAxis = d3.svg.axis()
			    .scale(y)
			    .orient("left")
			    .tickFormat(d3.format("1s"));

			var svg = d3.select(".multiBarChart").append("svg")
			    .attr("width", width + margin.left + margin.right)
			    .attr("height", height + margin.top + margin.bottom)
			  .append("g")
			    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

			  color.domain(d3.keys(data[0]).filter(function(key) { return key !== "key"; }));

			  data.forEach(function(d) {
			    var y0 = 0;
			    d.ages = color.domain().map(function(name) { return {name: name, y0: y0, y1: y0 += +d[name]}; });
			    d.total = d.ages[d.ages.length - 1].y1;
			  });

			  x.domain(data.map(function(d) { return d.key; }));
			  y.domain([0, d3.max(data, function(d) { return d.total; })]);

			  svg.append("g")
			      .attr("class", "x axis")
			      .attr("transform", "translate(0," + height + ")")
			      .call(xAxis)			      
					.selectAll("text")  
		            .style("text-anchor", "end")
		            .attr("dx", "-.8em")
		            .attr("dy", ".15em")
		            .attr("transform", "rotate(-65)" );


			  svg.append("g")
			      .attr("class", "y axis")
			      .call(yAxis)
			    .append("text")
			      .attr("transform", "rotate(-90)")
			      .attr("y", 6)
			      .attr("dy", ".71em")
			      .style("text-anchor", "end")
			      .text("value");

			  var state = svg.selectAll(".state")
			      .data(data)
			    .enter().append("g")
			      .attr("class", "g")
			      .attr("transform", function(d) { return "translate(" + x(d.key) + ",0)"; });

			  state.selectAll("rect")
			      .data(function(d) { return d.ages; })
			    .enter().append("rect")
			      .attr("width", x.rangeBand())
			      .attr("y", function(d) { return y(d.y1); })
			      .attr("height", function(d) { return y(d.y0) - y(d.y1); })
			      .style("fill", function(d) { return color(d.name); });

			  var legend = svg.selectAll(".legend")
			      .data(color.domain().slice().reverse())
			    .enter().append("g")
			      .attr("class", "legend")
			      .attr("transform", function(d, i) { return "translate(0," + i * 20 + ")"; });

			  legend.append("rect")
			      .attr("x", width - 18)
			      .attr("width", 18)
			      .attr("height", 18)
			      .style("fill", color);

			  legend.append("text")
			      .attr("x", width - 24)
			      .attr("y", 9)
			      .attr("dy", ".35em")
			      .style("text-anchor", "end")
			      .text(function(d) { return d; });
			}

			});
			
		}
			
	};
});