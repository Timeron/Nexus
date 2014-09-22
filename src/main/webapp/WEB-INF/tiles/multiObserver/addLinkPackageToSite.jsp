<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">


	<div class="row">
		<form:form commandName="site" action="addLinkPackageToSiteResult">
			<div class="form-group col-md-12">
				<h1>Strona</h1>
			</div>
			<div class="form-group col-md-12">
				<div class="form-group col-md-2">
					<label for="Id strony">Nazwa strony</label>
					<form:input type="text" class="form-control" path="id"
						placeholder="${siteTemp.id}" readonly="true" />
				</div>
				<div class="form-group col-md-5">
					<label for="Id strony">Nazwa strony</label>
					<form:input type="text" class="form-control" path="url"
						placeholder="${siteTemp.url}" readonly="true" />
				</div>
				<div class="form-group col-md-5">
					<label for="Id strony">Nazwa strony</label>
					<form:input type="text" class="form-control" path="name"
						placeholder="${siteTemp.name}" readonly="true" />
				</div>
			</div>
			<br>
			<div class="form-group col-md-12">
				<h1>Pakiety linków</h1>
			</div>
			<br>
			<div class="form-group col-md-12">
				<div class="form-group col-md-4">
					<label for="imie">Nazwa</label>
				</div>
				<div class="form-group col-md-5">
					<label for="nazwisko">Url</label>
				</div>
				<div class="form-group col-md-2">
					<label for="nazwisko">Typ Strony</label>
				</div>
				<div class="form-group col-md-1">
					<label for="nazwisko">Usuń</label>
				</div>
				<div class="form-group col-md-4">
					<form:input type="text" class="form-control"
						path="observedLinksPackage[0].name" placeholder="Name" />
				</div>
				<div class="form-group col-md-5">
					<form:input type="text" cssClass="form-control"
						path="observedLinksPackage[0].url" placeholder="Url" />
				</div>
				<div class="form-group, col-md-2">
					<form:select path="observedLinksPackage[0].siteType.description"
						cssClass="form-control">
						<form:option value="NONE" label="typ strony" />
						<form:options items="${siteTypes}" />
					</form:select>
				</div>
				<div class="form-group col-md-1">
					<button type="button" class="btn btn-danger">-</button>
				</div>
			</div>
			<div class="form-group col-md-12">
				<div class="form-group, col-md-12">
					<button type="button" class="btn btn-success col-md-12">+</button>
				</div>
			</div>
			<div class="form-group, col-md-12">
				<br> <br> <a href="./" class="btn btn-default">Cofnij</a>
				<button type="reset" class="btn btn-default">Resetuj</button>
				<input type="submit" value="Zapisz" class="btn btn-default" />
			</div>
		</form:form>
	</div>
</div>