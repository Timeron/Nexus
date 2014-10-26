<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div class="container">
	<div class="form-group col-md-12">
		<h1>Strona</h1>
	</div>
	<div class="form-group col-md-12">
		<div class="form-group col-md-2" id="divSiteId">
			<label for="Id strony">Nazwa strony</label> <input type="text"
				class="form-control"
				placeholder="${form.site.id}"
				readonly="true" />
		</div>
		<div class="form-group col-md-5" id="divSiteUrl">
			<label for="Id strony">Nazwa strony</label> <input type="text"
				class="form-control"
				placeholder="${form.site.url}"
				readonly="true" />
		</div>
		<div class="form-group col-md-5" id="divSiteName">
			<label for="Id strony">Nazwa strony</label> <input type="text"
				class="form-control"
				placeholder="${form.site.name}"
				readonly="true" />
		</div>
	</div>

	<div class="row">

		<form:form commandName="form"
			action="addLinksPackageToOldSiteResult" id="formSite">
			<input type="hidden" name="site.id"
				value="${form.site.id}" />
			<input type="hidden" name="site.name"
				value="${form.site.name}" />
			<input type="hidden" name="site.url"
				value="${form.site.url}" />
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
			</div>
			<div class="form-group col-md-12" id="addPackage">
				<div class="form-group col-md-3">
					<div id="divPackageName_0">
						<form:input type="text" class="form-control"
							path="observedLinksPackage[0].name" placeholder="Name"
							id="packageName_0" />
					</div>
				</div>
				<div class="form-group col-md-5">
					<div id="divPackageUrl_0">
						<form:input type="text" class="form-control"
							path="observedLinksPackage[0].url" placeholder="Url" id="packageUrl_0" />
					</div>
				</div>
				<div class="form-group, col-md-3">
					<div id="divPackageSiteType_0">
						<form:select path="observedLinksPackage[0].siteType.description"
							class="form-control" id="packageSiteType_0">
							<form:options items="${form.siteTypes}" />
						</form:select>
					</div>
				</div>
				<div class="form-group col-md-1">
					<button type="button" class="btn btn-danger">-</button>
				</div>
			</div>
			<div class="form-group col-md-12">
				<div class="form-group, col-md-12">
					<button type="button" class="btn btn-success col-md-12" id="addNextPackage">+</button>
				</div>
			</div>
			<div class="form-group, col-md-12">
				<br> <br> <a href="./" class="btn btn-default">Cofnij</a>
				<button type="reset" class="btn btn-default">Resetuj</button>
				<input type="submit" value="Zapisz" class="btn btn-default" />
			</div>
		</form:form>
		<p id="test"></p>


		<%-- 		<form:form commandName="form" --%>
		<%-- 			action="addLinksPackageFromFileResult" id="formSite"> --%>
		<!-- 			<div class="form-group col-md-12"> -->
		<!-- 				<h1>Pakiet z pliku</h1> -->
		<!-- 			</div> -->
		<!-- 			<div class="form-group, col-md-12"> -->
		<%-- 				<form:input type="file" path="file" /> --%>
		<!-- 				<p class="help-block">Wybierz plik z pakietami.</p> -->
		<!-- 			</div> -->
		<!-- 			<div class="form-group, col-md-12"> -->
		<!-- 				<br> <br> <a href="./" class="btn btn-default">Cofnij</a> -->
		<!-- 				<button type="reset" class="btn btn-default">Resetuj</button> -->
		<!-- 				<input type="submit" value="Zapisz" class="btn btn-default" /> -->
		<!-- 			</div> -->
		<%-- 		</form:form> --%>
	</div>
</div>

<script>

	$(function() {
		$('#formSite')
				.submit(
						function() {
							var packageName = document
									.getElementById("packageName_0").value;
							var packageUrl = document
									.getElementById("packageUrl_0").value;
							var packageSiteType = document
									.getElementById("packageSiteType_0").value;

							removeValidation();
							if (checkIfExist(packageName, "divPackageName_0")
									& checkIfExist(packageUrl, "divPackageUrl_0")
									& checkIfExist(packageSiteType,
											"divPackageSiteType_0")) {
								return true;
							}
							return false;
						});
	});
	var packageNr = 0;
	var packageOptionsArray = new Array();
	var packageOptions = "";
	alert(packageOptions);
	
	<c:forEach items="${form.siteTypes}" var="pack" varStatus="packStatus"> 
		packageOptionsArray.push("${pack.description}");
 	</c:forEach> 
 	
 	packageOptionsArray.forEach(getOptions);
	function getOptions(element, index, array) {
		packageOptions = packageOptions+"<option value='"+element+"'>"+element+"</option>";
	};
	
	$( "#addNextPackage" ).click(function() {
		packageNr++;
		$("#addPackage").append(
				"<div class='form-group col-md-3'><div id='divPackageName_"+packageNr+"'>"+
				"<input id='packageName_"+packageNr+"' name='observedLinksPackage["+packageNr+"].name' placeholder='Name' type='text' class='form-control' value>"+
				"</div></div><div class='form-group col-md-5'><div id='divPackageUrl_"+packageNr+"'>"+
				"<input id='packageName_"+packageNr+"' name='observedLinksPackage["+packageNr+"].url' placeholder='Url' type='text' class='form-control' value>"+
				"</div></div><div class='form-group, col-md-3'><div id='divPackageSiteType_"+packageNr+"'>"+
				"<select id='packageSiteType_"+packageNr+"' name='observedLinksPackage["+packageNr+"].siteType.description' class='form-control'>"+
				packageOptions+
				"</select>"+
				"</div></div><div class='form-group col-md-1'><button type='button' class='btn btn-danger'>-</button></div>"
		);
	});
	

	
</script>


