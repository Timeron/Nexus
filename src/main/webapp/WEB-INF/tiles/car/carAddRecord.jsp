<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div class="container">
	<!-- Example row of columns -->
	<div class="row">
		<h1>Spalanie</h1>
		<form:form commandName="form" action="addRecordResult">
			<div class="form-group">
			<label>Przebieg w kilometrach</label>
				<form:input type="text" class="form-control" path="record.liters"
					placeholder="Przebieg" />
			</div>
			<div class="form-group">
				<label for="opis">Litry paliwa</label>
				<form:input type="text" class="form-control" path="record.distance"
					placeholder="Litry" />
			</div>
			<div class="form-group, col-md-12">
				<a href="./" class="btn btn-default">Cofnij</a>
				<button type="reset" class="btn btn-default">Resetuj</button>
				<input type="submit" value="Zapisz" class="btn btn-default" />
			</div>
		</form:form>
	</div>
</div>