<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
<!--

.redFont {
	color: #FF0000;
}

#chart {
	width: 100%;
	padding: 15px 10px 50px 10px;
	font: 10px sans-serif;
	text-align: center;
}

.axis path,.axis line {
	fill: none;
	stroke: #000;
	shape-rendering: crispEdges;
	stroke-width: 2.5px;
}

.area {
	fill: steelblue;
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
<div class="container-fluid">
	<div class="bs-example">
		<c:choose>
			<c:when test="${not empty form.accounts}">
				<c:forEach items="${form.accounts}" var="account">
					<div class="btn-group">
						<a class="btn btn-primary btn-ms" role="button"
							href="/timeron-nexus/wallet/walletAccout?id=${account.id}">${account.name}</a>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<div class="container-fluid">

	<h1>${form.walletAccount.name}</h1>
	<div class="bs-example">
		<div class="btn-group">
			<h1>
				<a class="btn btn-primary btn-ms" role="button"
					href="/timeron-nexus/wallet/addRecord?id=${form.walletAccount.id}">+</a>
			</h1>
		</div>
	</div>
	
	<h1>Saldo całkowite:</h1>
	<h3>${form.sum} zł</h3>
	</br>
	<c:choose>

		<c:when test="${not empty form.walletRecords}">

			<h1>Wykres</h1>
			<div id="chart"></div>

			<h1>Rekordy</h1>
			<table class="table table-striped">
				<tr>
					<th>#</th>
					<th>type</th>
					<th>value</th>
					<th>description</th>
					<th>date</th>
					<th>transfer</th>
					<th>income</th>
					<th>Transfer do</th>
				</tr>
				<c:forEach items="${form.walletRecords}" var="record">
					<tr>
						<td>${record.id}</td>
						<td>${record.walletType.icon}</td>
						<c:choose>
							<c:when test="${record.value < 0}">
								<td class="redFont">${record.value}</td>
							</c:when>
							<c:otherwise>
								<td>${record.value}</td>
							</c:otherwise>
						</c:choose>
						<td>${record.description}</td>
						<td>${record.date}</td>
						<td>${record.transfer}</td>
						<td>${record.income}</td>
						<td>${record.destinationWalletAccount.name}</td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
</div>

<style>
#chart {
	width: 100%;
	padding: 15px 10px 50px 10px;
	font: 10px sans-serif;
	text-align: center;
}

.axis path,.axis line {
	fill: none;
	stroke: #000;
	shape-rendering: crispEdges;
	stroke-width: 2.5px;
}

.area {
	fill: steelblue;
}
</style>
<script src="<c:url value="/resources/js/chart/SingleLineChart.js" />"></script>
<script>
	var string = '${form.chart}';
	var json = JSON.parse(string);
	var data = json.chart;
	var yDescription = "Stan konta (zł)";
	singleLineFieldChart("#chart", $("#chart").width(), $("#chart").height(), data, "Stan konta", "zł");
</script>

