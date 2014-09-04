
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">
	<!-- Example row of columns -->
	<div class="row">
		<form:form commandName="site">
			<div class="form-group">
				<label for="imie">Nazwa</label>
				<form:input type="text" class="form-control" path="name"
					placeholder="Imie" />
			</div>
			<div class="form-group">
				<label for="nazwisko">Url</label>
				<form:input type="text" cssClass="form-control" path="url"
					placeholder="Nazwisko" />
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
