<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">


	<div class="row">
		<form:form commandName="site" action="addLinkPackageToSiteResult"
			id="formSite">
			<div class="form-group col-md-12">
				<h1>Strona</h1>
			</div>
			<div class="form-group col-md-12">
				<div class="form-group col-md-2" id="divSiteId">
					<label for="Id strony">Nazwa strony</label>
					<form:input type="text" class="form-control" path="id" id="siteId"
						placeholder="${siteTemp.id}" readonly="true" />
				</div>
				<div class="form-group col-md-5" id="divSiteUrl">
					<label for="Id strony">Nazwa strony</label>
					<form:input type="text" class="form-control" path="url"
						id="siteUrl" placeholder="${siteTemp.url}" />
				</div>
				<div class="form-group col-md-5" id="divSiteName">
					<label for="Id strony">Nazwa strony</label>
					<form:input type="text" class="form-control" path="name"
						id="siteName" placeholder="${siteTemp.name}" readonly="true" />
				</div>
			</div>
			<br>
			<div class="form-group col-md-12">
				<h1>Pakiety linków</h1>
			</div>
			<br>
			<div class="form-group col-md-12">
				<div class="form-group col-md-3">
					<label for="imie">Nazwa</label>
				</div>
				<div class="form-group col-md-5">
					<label for="nazwisko">Url</label>
				</div>
				<div class="form-group col-md-3">
					<label for="nazwisko">Typ Strony</label>
				</div>
				<div class="form-group col-md-1">
					<label for="nazwisko">Usuń</label>
				</div>
				<div class="form-group col-md-3" >
					<div id="divPackageName">
						<form:input type="text" class="form-control"
							path="observedLinksPackage[0].name" placeholder="Name"
							id="packageName" />
					</div>
				</div>
				<div class="form-group col-md-5" >
					<div id="divPackageUrl">
						<form:input type="text" class="form-control"
							path="observedLinksPackage[0].url" placeholder="Url"
							id="packageUrl" />
					</div>
				</div>
				<div class="form-group, col-md-3" >
					<div id="divPackageSiteType">
						<form:select path="observedLinksPackage[0].siteType.description"
							class="form-control" id="packageSiteType">
							<form:option value="NONE" label="typ strony" />
							<form:options items="${siteTypes}" />
						</form:select>
					</div>
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

<script>
	$(function() {
		$('#formSite')
				.submit(
						function() {
							var packageName = document
									.getElementById("packageName").value;
							var packageUrl = document
									.getElementById("packageUrl").value;
							var packageSiteType = document
									.getElementById("packageSiteType").value;

							removeValidation();
							if (checkIfExist(packageName, "divPackageName")
									& checkIfExist(packageUrl, "divPackageUrl")
									& checkIfExist(packageSiteType,
											"divPackageSiteType")) {
								return true;
							}
							return false;
						});
	});
</script>

