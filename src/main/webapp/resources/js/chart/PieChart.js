function SimplePieChart(div, _r, _i){
	
	var salesData = [ {
		id : 1,
		value : 45,
		label : "Basic",
		color : "#3366CC"
	}, {
		id : 2,
		value : 5,
		label : "Plus",
		color : "#DC3912"
	}, {
		id : 3,
		value : 25,
		label : "Lite",
		color : "#FF9900"
	}, {
		id : 4,
		value : 35,
		label : "Elite",
		color : "#109618"
	}, {
		id : 5,
		value : 15,
		label : "Delux",
		color : "#990099"
	} ];
	
	var svg = d3.select(div).append("svg").attr("width", 700).attr("height",500);
	
	var gradPie={};
	
	var pie = d3.layout.pie().sort(function(a,b){return a.value-b.value;}).value(function(d) {return d.value;});
			
	createGradients = function(defs, colors, r){	
		var gradient = defs.selectAll('.gradient')
			.data(colors).enter().append("radialGradient")
			.attr("id", function(d,i){return "gradient" + i;})
			.attr("gradientUnits", "userSpaceOnUse")
			.attr("cx", "0").attr("cy", "0").attr("r", r).attr("spreadMethod", "pad");
			
		gradient.append("stop").attr("offset", "0%").attr("stop-color", function(d){ return d;});

		gradient.append("stop").attr("offset", "30%")
			.attr("stop-color",function(d){ return d;})
			.attr("stop-opacity", 1);
			
		gradient.append("stop").attr("offset", "70%")
			.attr("stop-color",function(d){ return "black";})
			.attr("stop-opacity", 1);
	};
	
	gradPie.draw = function(id, data, cx, cy, r){
		var gPie = d3.select("#"+id).append("g")
			.attr("transform", "translate(" + cx + "," + cy + ")");
			
		createGradients(gPie.append("defs"), data.map(function(d){ return d.color; }), 2.5*r);

		gPie.selectAll("path").data(pie(data))
			.enter().append("path").attr("class", "pie")
			.attr("fill", function(d,i){ return "url(#gradient"+ i+")";})
			.attr("date-id", function(d,i){ return i;})
			.attr("d", d3.svg.arc().outerRadius(r).innerRadius(r-_i))
			.each(function(d) { this._current = d; });
	};
	
	gradPie.transition = function(id, data, r) {
		function arcTween(a) {
		  var i = d3.interpolate(this._current, a);
		  this._current = i(0);
		  return function(t) { return d3.svg.arc().outerRadius(r)(i(t));  };
		}
		
		d3.select("#"+id).selectAll("path").data(pie(data))
			.transition().duration(750).attrTween("d", arcTween); 
	};
	
	this.gradPie = gradPie;
	
	svg.append("g").attr("id", "salespie");

	gradPie.draw("salespie", salesData, 210, 210, _r);

//	function changeData() {
//		gradPie.transition("salespie", salesData, 160);
//	};
	
	$(".pie").click(function(){
		_value = salesData[$(this).attr("date-id")].value;
		_data = [{id : 10, value : _value, label : "Test", color : "#999"}, {id : 9, value : _value, label : "Test", color : "#AAA"}];
		gradPie.draw("test", _data, 210, 210, 50);
		console.log();
		
//		_value = salesData[$(this).attr("date-id")].value;
//		_data = [{id : 10, value : _value, label : "Test", color : "#999"}];
//		alert(_value);
//		console.log(_data[0].id);
//		gradPie.transition("testPie", _data, 50);
		
	});
	
};