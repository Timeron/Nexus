<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>

var test = [];
//var test = $(".tdRecordValue").val();
alert(test);
test = <&% form.recordsValue %>
alert(test);

	var canvas = d3.select("body")
				.append("svg")
				.attr("width", 500)
				.attr("height", 500);
	
	var bars = canvas.selectAll("rect")
				.data(test)
				.enter()
					.append("rect")
					.attr("width", function(d) {
						return d; })
					.attr("height", 50)
					.attr("y", function(d, i){return i*100; });
</script>