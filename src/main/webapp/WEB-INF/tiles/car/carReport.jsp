<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="<c:url value='/resources/js/chart/SingleBarChart.js'/>"></script>
<style>
<!--
h1{
	width: 960px;
}

.charts {
	font: 10px sans-serif;
	float: none;
}

#chart1 {
	width: 65%;
	height: 600px;
	float: left;
}

#chart2 {
	width: 35%;
	height: 300px;
	float: left;
}

#chart3 {
	width: 35%;
	height: 300px;
	float: left;
}

.bar {
	fill: steelblue;
}

.bar:hover {
	fill: brown;
}

.axis {
	font: 10px sans-serif;
}

.axis path,.axis line {
	fill: none;
	stroke: #000;
	shape-rendering: crispEdges;
}

.x.axis path {
	display: none;
}






.axis path,
.axis line {
  fill: none;
  stroke: #000;
  shape-rendering: crispEdges;
}

.x.axis path {
  display: none;
}

.line {
  fill: none;
  stroke: steelblue;
  stroke-width: 1.5px;
}

.overlay {
  fill: none;
  pointer-events: all;
}

.focus circle {
  fill: none;
  stroke: steelblue;
}

.range {
    fill: #eee;
    opacity: .4;
    pointer-events: all;
    shape-rendering: crispEdges;
}
-->
</style>

<div>
	<h1>Streszczenie</h1>
	<div class="row">
		<h3>Srednie spalanie: <b>${form.averageFuelConsumption } l/km</b></h3>
		<h3>Spalanie ostatniego przejazdu: <b>${form.lastAverageFuelConsumption } l/km</b></h3>
		<h3>Ostatni przejazd: <b>${form.lastDistance } km</b></h3>
		<h3>Ostatnie spalanie: <b>${form.lastFuel }</b></h3>
		<h3>Całkowity dystans: <b>${form.totalDistance }</b></h3>
		<h3>Zatankowano: <b>${form.totalFuel }</b></h3>
	</div>
</div>





<c:choose>
	<c:when test="${not empty form.records}">
		<h1>Wykres</h1>
		<div class="charts">
			<div id="chart1"></div>
			<div id="chart2"></div>
			<div id="chart3"></div>
		</div>
		<h1>Rekordy</h1>
		<table class="table table-striped">
			<tr>
				<th>Litry</th>
				<th>Kilometry</th>
				<th>Miasto</th>
				<th>Mieszany</th>
				<th>Data</th>

				<th>Edytuj/Usuń</th>
			</tr>
			<c:forEach items="${form.records}" var="record">
				<tr>
					<td>${record.liters}</td>
					<td>${record.distance}</td>
					<td>${record.city}</td>
					<td>${record.mixed}</td>
					<td>${record.date}</td>

					<td><a id="transferButton" class="btn btn-primary"
						href="/nexus/car/carEditRecord?id=${record.id}"> <span
							class="glyphicon glyphicon-edit" aria-hidden="true"> </span>
					</a> <a id="transferButton" class="btn btn-danger"
						href="/nexus/car/carRemoveRecord?id=${record.id}"> <span
							class="glyphicon glyphicon-trash" aria-hidden="true"> </span>
					</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		empty
		</c:otherwise>
</c:choose>



<script>
var stringDistance = '${form.chartDistance}';
var jsonDistance = JSON.parse(stringDistance);
var dataDistance = jsonDistance.chart;

var stringRefuel = '${form.chartRefuel}';
var jsonRefuel = JSON.parse(stringRefuel);
var dataRefuel = jsonRefuel.chart;

console.log(dataDistance);
singleBarChart("#chart3", $('#chart2').width(), $('#chart2').height()-50, dataRefuel, "Litry (L)", "zł");
singleBarChart("#chart2", $('#chart2').width(), $('#chart2').height()-50, dataDistance, "Dystens (km)", "km");

var data = ${form.chart};
var string = '${form.chart}';
var json = JSON.parse(string);
var data = json.chart;
var width = $('#chart1').width();
var height = $('#chart1').height();
console.log(string);
console.log(data);

var margin = {top: 10, right: 20, bottom: 65, left: 40},
 width = width - margin.left - margin.right,
 height = height - margin.top - margin.bottom;

var x = d3.scale.ordinal()
.rangeRoundBands([0, width], .1);

var y = d3.scale.linear()
.range([height, 0]);

var xAxis = d3.svg.axis()
.scale(x)
.orient("bottom");

var yAxis = d3.svg.axis()
.scale(y)
.orient("left")
.ticks(10, "L/km");

var svg = d3.select("#chart1").append("svg")
.attr("width", width + margin.left + margin.right)
.attr("height", height + margin.top + margin.bottom)
.append("g")
.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

data.forEach(function(d) {
	x.domain(data.map(function(d) { return d.interval; }));
	y.domain([0, d3.max(data, function(d) { return d.value; })]);
});

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
  .text("spalanie");

svg.selectAll(".bar")
  .data(data)
.enter().append("rect")
  .attr("class", "bar")
  .attr("x", function(d) { return x(d.interval); })
  .attr("width", x.rangeBand())
  .attr("y", function(d) { return y(d.value); })
  .attr("height", function(d) { return height - y(d.value); });



function type(d) {
d.value = +d.value;
return d;
}



</script>