<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">
	<!-- Example row of columns -->
	<div class="row">
		<h1>Dodaj kategorię produktu.</h1>
		<form:form commandName="form" action="addProductCategoryResult">
			<div class="form-group">
				<label for="opis">Opis</label>
				<form:input type="text" class="form-control" path="productCategory.description"
					placeholder="Opis" />
			</div>
			<div class="form-group, col-md-12">
				<a href="./" class="btn btn-default">Cofnij</a>
				<button type="reset" class="btn btn-default">Resetuj</button>
				<input type="submit" value="Zapisz" class="btn btn-default" />
			</div>
		</form:form>
		<c:forEach items="${form.allProductCategorys}" var="productCategorys">
			<p>${productCategorys.description}</p>
		</c:forEach>
	</div>
</div>
