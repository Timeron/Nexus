﻿
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container">
	<!-- Example row of columns -->
	<div class="row">
		<form:form commandName="person">
			<div class="form-group, col-md-6">
				<div class="form-group">
					<label for="imie">Imie</label>
					<form:input type="text" class="form-control" path="firstName"
						placeholder="Imie" />
				</div>
				<div class="form-group">
					<label for="nazwisko">Nazwisko</label>
					<form:input type="text" cssClass="form-control" path="lastName"
						placeholder="Nazwisko" />
				</div>
				<div class="form-group">
					<label for="pseudo">Pseudo</label>
					<form:input type="text" cssClass="form-control" path="pseudo"
						placeholder="Pseudo" />
				</div>
			</div>
			<div class="form-group, col-md-6">
				<div class="form-group">
					<label for="Email">Email</label>
					<form:input type="text" cssClass="form-control" path="email"
						placeholder="Email" />
				</div>
				<div class="form-group">
					<label for="Telefon">Telefon</label>
					<form:input type="text" cssClass="form-control" path="phone1"
						placeholder="Telefon 1" />
				</div>
				<div class="form-group">
					<label for="Telefon">Telefon</label>
					<form:input type="text" cssClass="form-control" path="phone2"
						placeholder="Telefon 2" />
				</div>
				<div class="form-group">
					<label for="Telefon">Telefon</label>
					<form:input type="text" cssClass="form-control" path="phone3"
						placeholder="Telefon 3" />
				</div>
			</div>
			<div class="form-group, col-md-6">
				<div class="form-group">
					<label for="Adres">Adres</label>
					<form:input type="text" cssClass="form-control" path="address"
						placeholder="Adres" />
				</div>
				<div class="form-group">
					<label for="Miasto">Miasto</label>
					<form:input type="text" cssClass="form-control" path="city"
						placeholder="Miasto" />
				</div>
				<div class="form-group">
					<label for="Kraj">Kraj</label>
					<form:input type="text" cssClass="form-control" path="country"
						placeholder="Kraj" />
				</div>
			</div>
			<div class="form-group col-md-6">
				<label for="Urodziny">Urodziny</label> <br>
				<div class="form-group, col-md-4">
					<form:select path="birthdayYear" class="form-control">
						<form:option value="NONE" label="rok" />
						<form:options items="${years}" />
					</form:select>
				</div>
				<div class="form-group, col-md-4">
					<form:select path="birthdayMonth" cssClass="form-control">
						<form:option value="NONE" label="miesiąc" />
						<form:options items="${months}" />
					</form:select>
				</div>
				<div class="form-group, col-md-4">
					<form:select path="birthdayDay" cssClass="form-control">
						<form:option value="NONE" label="dzień" />
						<form:options items="${days}" />
					</form:select>
				</div>
			</div>
			<div class="form-group, col-md-6">
				<label for="Imieniny">Imieniny</label><br>
				<div class="form-group, col-md-4">
					<form:select path="nameDayMonth" cssClass="form-control">
						<form:option value="NONE" label="miesiąc" />
						<form:options items="${months}" />
					</form:select>
				</div>
				<div class="form-group, col-md-4">
					<form:select path="nameDayDay" cssClass="form-control">
						<form:option value="NONE" label="dzień" />
						<form:options items="${days}" />
					</form:select>
				</div>
			</div>
			<div class="form-group, col-md-12">
				<br>
				<div class="form-group">
					<label for="Opis">Opis</label>
					<form:textarea path="description" class="form-control" rows="3" placeholder="Opis"></form:textarea>
				</div>
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
		$('#person').submit(function() {
			alert("tets");
			return true; // return false to cancel form action
		});
	});
</script>
