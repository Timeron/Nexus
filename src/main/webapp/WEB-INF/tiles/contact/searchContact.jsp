
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">
	<div class="row">
		<form:form commandName="person" >
			<div class="form-group">
				<label for="exampleInputEmail1">Imie</label>
				<form:input type="text" class="form-control" path="firstName"
					placeholder="Imie" />
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">Nazwisko</label>
				<form:input type="text" cssClass="form-control" path="lastName"
					placeholder="Nazwisko" />
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">Pseudo</label>
				<form:input type="text" cssClass="form-control" path="pseudo"
					placeholder="Pseudo" />
			</div>
			
			<input type="submit" value="Szukaj" class="btn btn-default" />
			
		</form:form>	
	</div>
</div>