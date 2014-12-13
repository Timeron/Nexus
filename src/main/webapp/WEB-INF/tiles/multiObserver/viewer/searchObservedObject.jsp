<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="container-fluid">
	<h1>Wyszukaj objekt</h1>

	<form:form commandName="form" action="searchObservedObject"
		id="searchParameters">
		<div class="form-group" id="divId">
			<label for="name">Id</label>
			<form:input type="text" class="form-control" path="searchParameters.id"
				id="name" placeholder="Id" />
		</div>
		<div class="form-group" id="divName">
			<label for="name">Nazwa</label>
			<form:input type="text" class="form-control" path="searchParameters.name"
				id="name" placeholder="Nazwa" />
		</div>
		<div class="form-group" id="divProductKay">
			<label for="productKay">Product Kay</label>
			<form:input type="text" cssClass="form-control" path="searchParameters.productKay"
				id="productKay" placeholder="Product Kay" />
		</div>
		<div class="form-group, col-md-12">
			<a href="./" class="btn btn-default">Cofnij</a>
			<button type="reset" class="btn btn-default">Resetuj</button>
			<input type="submit" value="Zapisz" class="btn btn-default" />
		</div>
	</form:form>



</div>
