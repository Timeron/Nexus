
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">
	<!-- Example row of columns -->
	<div class="row">
		<form:form commandName="site">
			<label for="Strona">Strona</label>
			<br>
			<div class="form-group, col-md-4">
				<form:select path="site" class="form-control">
					<form:option value="NONE" label="wybierz stronę" />
					<form:options items="sites" />
				</form:select>
			</div>
		</form:form>
	</div>
</div>