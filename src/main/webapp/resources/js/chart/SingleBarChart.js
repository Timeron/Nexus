function singleBarChart(div, divWidth, divHeight, data, axisText, unit){	
	
	var margin = {top: 10, right: 50, bottom: 20, left: 50},
	width = divWidth - margin.left - margin.right,
	height = divHeight - margin.top - margin.bottom;
	
	var parseDate = d3.time.format("%b %d, %Y %I:%M:%S %p").parse,		//formatowanie daty wchodzącej do wykresu
	bisectDate = d3.bisector(function(d) { return d.date; }).left,		//
	format = d3.format(",.2f"),											//formatowanie wartości która bedzie wyświetlana po najechaniu na wykres
	formatValue = function(d) { return format(d)+unit; };				//foramtowanie do tekstu wartości która bedzie wyświetlona
	
	var x = d3.time.scale()			//zasięg wykresu w szerokości
	.range([0, width]);
	
	var y = d3.scale.linear()		//zasięg wykresu na wysokości
	.range([height, 0]);
	
	var xAxis = d3.svg.axis()		//skala X na dole wtkresu
	.scale(x)
	.orient("bottom");
	
	var yAxis = d3.svg.axis()		//skala Y po lewej stronie wtkresu
	.scale(y)
	.orient("left");
	
	var line = d3.svg.line()
	.x(function(d) { return x(d.date); })
	.y(function(d) { return y(d.value); });
	
	var svg = d3.select(div).append("svg")					//umiejscowienie wykresu w htmlu
	.attr("width", width + margin.left + margin.right)		//długość wykresu
	.attr("height", height + margin.top + margin.bottom)	//wysokość wykresu
	.append("g")											//grupowanie
	.attr("transform", "translate(" + margin.left + "," + margin.top + ")");	//przesuniecie grupy o marginesy
	
	data.forEach(function(d) {			//formatowanie danych wejsciowych
		d.date = parseDate(d.date);
		d.value = +d.value;
	});
	    
	data.sort(function(a, b) {			//sposób sortowania daty
		return a.date - b.date;
	});
	
	x.domain([data[0].date, data[data.length - 1].date]);
	y.domain(d3.extent(data, function(d) { return d.value; }));
	
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
	  .text(axisText);   
	
	svg.append("path")		//budowanie lini na wykresie
	  .datum(data)			//dodanie danych
	  .attr("class", "line")	//rozpoznanie przez css jako class="line"
	  .attr("d", line);		//przekazanie danych 
	
	var focus = svg.append("g")
	  .attr("class", "focus")
	  .style("display", "none");
	
	focus.append("circle")		//budowa markera (zaznaczenie danych na wykresie)
	  .attr("r", 4.5);
	
	focus.append("text")		//formatowanie textu na markerze
	  .attr("x", 9)
	  .attr("dy", ".35em");
	
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
