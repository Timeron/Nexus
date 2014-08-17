
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">
	<!-- Example row of columns -->
	<div class="row">
		<form:form commandName="person">

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
			<div class="form-group">
				<label for="exampleInputEmail1">Email</label>
				<form:input type="text" cssClass="form-control" path="email"
					placeholder="Email" />
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">Telefon</label>
				<form:input type="text" cssClass="form-control" path="phone1"
					placeholder="Telefon 1" />
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">Telefon</label>
				<form:input type="text" cssClass="form-control" path="phone2"
					placeholder="Telefon 2" />
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">Telefon</label>
				<form:input type="text" cssClass="form-control" path="phone3"
					placeholder="Telefon 3" />
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">Adres</label>
				<form:input type="text" cssClass="form-control" path="address"
					placeholder="Adres" />
			</div>

			<div class="form-group">
				<label for="exampleInputEmail1">Miasto</label>
				<form:input type="text" cssClass="form-control" path="city"
					placeholder="Miasto" />
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">Kraj</label>
				<form:input type="text" cssClass="form-control" path="country"
					placeholder="Kraj" />
			</div>
			<div id="datetimepicker1" class="input-append date">
				<label for="exampleInputEmail1">Urodziny</label>
				<form:input type="text" class="form-control" path="birthday" placeholder="Urodziny"/> <span class="add-on"> <i
					cssClass="glyphicon glyphicon-calendar"></i>
				</span>
			</div>
			<br>
			<div id="datetimepicker2" class="input-append date">
				<label for="exampleInputEmail1">Imieniny</label>
				<form:input type="text" class="form-control" path="birthday" placeholder="Imieniny"/> <span class="add-on"> <i
					cssClass="glyphicon glyphicon-calendar"></i>
				</span>
			</div>
			<div class="form-group">
				<label for="exampleInputEmail1">Opis</label>
				<form:input type="text" cssClass="form-control" path="description"
					placeholder="Opis" />
			</div>

			<a href="./" class="btn btn-default" role="button">Cofnij</a>
			<button type="reset" class="btn btn-default">Resetuj</button>
			<input type="submit" value="Zapisz" class="btn btn-default" />

		</form:form>

		<script type="text/javascript">
			$('#datetimepicker1').datetimepicker({
				format : 'yyyy/MM/dd',
				language : 'pl-PL'
			});
			$('#datetimepicker2').datetimepicker({
				format : 'yyyy/MM/dd',
				language : 'pl-PL'
			});
		</script>
	</div>