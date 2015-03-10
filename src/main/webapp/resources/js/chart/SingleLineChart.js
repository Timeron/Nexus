function singleLineFieldChart(div, divWidth, divHeight, data, axisText, unit){	

var dateFormat = "%b %d, %Y %I:%M:%S %p";
	
	var margin = {top : 20, right : 20, 
					bottom : 30, left : 50}, 
					width = $(window).width() - margin.left - margin.right - 100, 
					height = 500 - margin.top - margin.bottom;

	var parseDate = d3.time.format(dateFormat).parse,
	bisectDate = d3.bisector(function(d) { return d.date; }).left,
	format = d3.format(",.2f"),
	formatValue = function(d) { return format(d)+"unit";};

	var x = d3.time.scale().range([ 0, width ]);

	var y = d3.scale.linear().range([ height, 0 ]);

	var xAxis = d3.svg.axis().scale(x).orient("bottom");

	var yAxis = d3.svg.axis().scale(y).orient("left");

	var area = d3.svg.area().x(function(d) {
		return x(d.date);
	}).y0(height).y1(function(d) {
		return y(d.value);
	});

	var svg = d3.select("#chart")
				.append("svg")
				.attr("width", width + margin.left + margin.right)
				.attr("height", height + margin.top + margin.bottom)
				.append("g")
				.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

	data.forEach(function(d) {
		d.date = parseDate(d.date);
		d.value = +d.value;
	});

	x.domain(d3.extent(data, function(d) {
		return d.date;
	}));
	y.domain([ 0, d3.max(data, function(d) {
		return d.value;
	}) ]);

	svg.append("path")
		.datum(data)
			.attr("class", "area")
			.attr("d", area)
			.style("stroke", "green")
			.style("fill", "#AFA")
			.style("stroke-width", "2.5px");

	svg.append("g")
		.attr("class", "x axis")
		.attr("transform","translate(0," + height + ")")
		.call(xAxis);

	svg.append("g")
		.attr("class", "y axis")
		.call(yAxis)
		.append("text")
		.attr("transform", "rotate(-90)")
		.attr("y", 6).attr("dy", ".71em")
		.style("text-anchor", "end")
		.text(yDescription);
	//***************
	var focus = svg.append("g")
	  .attr("class", "focus")
	  .style("display", "none");
	
	focus.append("circle")		//budowa markera (zaznaczenie danych na wykresie)
	  .attr("r", 5);
	
	focus.append("text")		//formatowanie textu na markerze
	  .attr("x", 4)
	  .attr("y", -15)
	  .attr("dy", ".45em");
	
	svg.append("rect")
	  .attr("class", "overlay")
	  .attr("width", width)
	  .attr("height", height)
	  .on("mouseover", function() { focus.style("display", null); })
	  .on("mouseout", function() { focus.style("display", "none"); })
	  .on("mousemove", mousemove);
	
	function mousemove() {
	var x0 = x.invert(d3.mouse(this)[0]),
	    i = bisectDate(data, x0, 1),
	    d0 = data[i - 1],
	    d1 = data[i],
	    d = x0 - d0.date > d1.date - x0 ? d1 : d0;
	focus.attr("transform", "translate(" + x(d.date) + "," + y(d.value) + ")");
	focus.select("text").text(formatValue(d.value));
	}
}