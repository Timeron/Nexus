<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">
	<!-- Example row of columns -->
	<div class="row">
		<h1>Dodaj stronę.</h1>
		<form:form commandName="site" action="addLinkPackageToSite"
			id="formSite">
			<div class="form-group" id="divName">
				<label for="imie">Nazwa</label>
				<form:input type="text" class="form-control" path="name" id="name"
					placeholder="Nazwa" for="inputSuccess2" />
			</div>
			<div class="form-group" id="divUrl">
				<label for="nazwisko">Url</label>
				<form:input type="text" cssClass="form-control" path="url" id="url"
					placeholder="Url" />
			</div>
			<div class="form-group">
				<form:checkbox path="valid" placeholder="false" />
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
			
			if(checkIfExist(name, "divName") & checkIfExist(url, "divUrl")){
				return true;
			}
			return false;
		});
	});
	
	

</script>
