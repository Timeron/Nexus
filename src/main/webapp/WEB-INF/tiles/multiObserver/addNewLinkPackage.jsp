
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">
	<!-- Example row of columns -->
	<div class="row">
		<form:form commandName="site" action="addLinkPackageToSite">
			<div class="form-group">
				<label for="Strona">Strona</label> <br>
				<div class="form-group">
					<form:select path="id" class="form-control">
						<form:option value="NONE" label="wybierz stronę" />
						<form:options items="${siteTemp}" itemValue="id" itemLabel="name" />
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<a href="./" class="btn btn-default">Cofnij</a>
				<button type="reset" class="btn btn-default">Resetuj</button>
				<input type="submit" value="Dalej" class="btn btn-default" />
			</div>
		</form:form>
	</div>
</div>