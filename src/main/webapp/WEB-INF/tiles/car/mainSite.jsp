<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<div class="container">
	<!-- Example row of columns -->
	<div class="row">
		<h3>Srednie spalanie: <b>${form.averageFuelConsumption } l/km</b></h3>
		<h3>Spalanie ostatniego przejazdu: <b>${form.lastAverageFuelConsumption } l/km</b></h3>
		<h3>Ostatni przejazd: <b>${form.lastDistance } km</b></h3>
		<h3>Ostatnie spalanie: <b>${form.lastFuel }</b></h3>
		<h3>Całkowity dystans: <b>${form.totalDistance }</b></h3>
		<h3>Zatankowano: <b>${form.totalFuel }</b></h3>
	</div>
</div>