<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">
	<!-- Example row of columns -->
	<div class="row">
		<h1>Dodaj stronę.</h1>
		<form:form commandName="form" action="addLinkPackageToNewSite"
			id="formSite">
			<div class="form-group" id="divName">
				<label for="name">Nazwa</label>
				<form:input type="text" class="form-control" path="site.name"
					id="name" placeholder="Nazwa" />
			</div>
			<div class="form-group" id="divUrl">
				<label for="url">Url</label>
				<form:input type="text" cssClass="form-control" path="site.url"
					id="url" placeholder="Url" />
			</div>
			<div class="form-group" id="articlesDivXPath">
				<label for="articlesDivXPath">articlesDivXPath</label>
				<form:input type="text" cssClass="form-control"
					path="site.articlesDivXPath" id="articlesDivXPath"
					placeholder="articlesDivXPath" />
			</div>
			<div class="form-group" id="nextXPath">
				<label for="nextXPath">nextXPath</label>
				<form:input type="text" cssClass="form-control"
					path="site.nextXPath" id="nextXPath" placeholder="nextXPath" />
			</div>
			<div class="form-group" id="productNameXPath">
				<label for="productNameXPath">productNameXPath</label>
				<form:input type="text" cssClass="form-control"
					path="site.productNameXPath" id="productNameXPath"
					placeholder="NameXPath" />
			</div>
			<div class="form-group" id="productURLXPath">
				<label for="productURLXPath">productURLXPath</label>
				<form:input type="text" cssClass="form-control"
					path="site.productURLXPath" id="productURLXPath"
					placeholder="URLXPath" />
			</div>
			<div class="form-group" id="productPriceXPath">
				<label for="productPriceXPath">productPriceXPath</label>
				<form:input type="text" cssClass="form-control"
					path="site.productPriceXPath" id="productPriceXPath"
					placeholder="PriceXPath" />
			</div>
			<div class="form-group" id="productNewPriceXPath">
				<label for="productNewPriceXPath">productNewPriceXPath</label>
				<form:input type="text" cssClass="form-control"
					path="site.productNewPriceXPath" id="productNewPriceXPath"
					placeholder="NewPriceXPath" />
			</div>
			<div class="form-group" id="productOldPriceXPath">
				<label for="productOldPriceXPath">productOldPriceXPath</label>
				<form:input type="text" cssClass="form-control"
					path="site.productOldPriceXPath" id="productOldPriceXPath"
					placeholder="OldPriceXPath" />
			</div>
			<div class="form-group" id="productKayXPath">
				<label for="productKayXPath">productKayXPath</label>
				<form:input type="text" cssClass="form-control"
					path="site.productKayXPath" id="productKayXPath"
					placeholder="KayXPath" />
			</div>
			<div class="form-group">
				<form:checkbox path="site.valid" placeholder="false" />
				aktywna
			</div>
			<div class="form-group, col-md-12">
				<a href="./" class="btn btn-default">Cofnij</a>
				<button type="reset" class="btn btn-default">Resetuj</button>
				<input type="submit" value="Zapisz" class="btn btn-default" />
			</div>
		</form:form>
	</div>
</div>

<script>
	$(function() {
		$('#formSite').submit(function() {
			var name = document.getElementById("name").value;
			var url = document.getElementById("url").value;

			removeValidation();

			if (checkIfExist(name, "divName") & checkIfExist(url, "divUrl")) {
				return true;
			}
			return false;
		});
	});
</script>
