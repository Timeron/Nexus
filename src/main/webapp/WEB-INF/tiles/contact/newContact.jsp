
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
			<div class="form-group, col-md-6">
				<div class="form-group">
					<label for="Urodziny">Urodziny</label>
<%-- 					<form:input type="text" cssClass="form-control, col-md-3" --%>
<%-- 						path="birthdayYear" placeholder="Rok" /> --%>

					<form:select path="birthdayYear" cssClass="form-control, col-md-6">
						<form:option value="NONE" label="--- Select ---" />
						<form:options items="${years}" />
					</form:select>
					<form:select path="birthdayMonth" cssClass="form-control, col-md-6">
						<form:option value="NONE" label="--- Select ---" />
						<form:options items="${months}" />
					</form:select>
					<form:select path="birthdayDay" cssClass="form-control, col-md-6">
						<form:option value="NONE" label="--- Select ---" />
						<form:options items="${days}" />
					</form:select>
				</div>

				<div class="form-group">
					<label for="Imieniny">Imieniny</label>
					<form:input type="text" cssClass="form-control, col-md-6"
						path="nameDayMonth" placeholder="Miesiąc" />
					<form:input type="text" cssClass="form-control, col-md-3"
						path="nameDayDay" placeholder="Dzień" />
				</div>



			</div>
			<div class="form-group, col-md-12">
				<br>
				<div class="form-group">
					<label for="Opis">Opis</label>
					<form:input type="text" cssClass="form-control" path="description"
						placeholder="Opis" />
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
<script>
	(function($) {
		$.fn.datepicker.dates['pl'] = {
			days : [ "Niedziela", "Poniedziałek", "Wtorek", "Środa",
					"Czwartek", "Piątek", "Sobota", "Niedziela" ],
			daysShort : [ "Nie", "Pn", "Wt", "Śr", "Czw", "Pt", "So", "Nie" ],
			daysMin : [ "N", "Pn", "Wt", "Śr", "Cz", "Pt", "So", "N" ],
			months : [ "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj",
					"Czerwiec", "Lipiec", "Sierpień", "Wrzesień",
					"Październik", "Listopad", "Grudzień" ],
			monthsShort : [ "Sty", "Lu", "Mar", "Kw", "Maj", "Cze", "Lip",
					"Sie", "Wrz", "Pa", "Lis", "Gru" ],
			today : "Dzisiaj",
			weekStart : 1
		};
	}(jQuery));
</script>