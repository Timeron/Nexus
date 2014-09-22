<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
${site}<br>
<a class="btn btn-primary btn-ms" role="button" href="addLinkPackageToSite?siteId=${site.id}">Nowy pakiet linków do strony</a>



<div class="container">
	<!-- Example row of columns -->
	<div class="row">
		<form:form commandName="observedLinksPackage" action="addLinkPackageToSiteResult">
			<div class="form-group">
				<label for="Id strony">Id strony</label>
				<form:input type="text" class="form-control" path="site"
					placeholder="${siteId}" readonly="readonly"/>
			</div>
			<div class="form-group">
				<label for="imie">Nazwa</label>
				<form:input type="text" class="form-control" path="name"
					placeholder="Name" />
			</div>
			<div class="form-group">
				<label for="nazwisko">Url</label>
				<form:input type="text" cssClass="form-control" path="url"
					placeholder="Url" />
			</div>
			<div class="form-group, col-md-12">
				<a href="./" class="btn btn-default">Cofnij</a>
				<button type="reset" class="btn btn-default">Resetuj</button>
				<input type="submit" value="Zapisz" class="btn btn-default" />
			</div>
		</form:form>
	</div>
</div>