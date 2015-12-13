<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div data-ng-app="contact">

<!-- Modals -->
	<!-- Modal -->
	<div class="modal" id="addContact" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" data-ng-controller="AddContactCtrl">
		<div class="modal-dialog" style="width: 50%">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Nowe Kontakt</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="form-group, col-md-6">
							<div class="form-group">
								<label for="imie">Imie</label> <input type="text"
									class="form-control" data-ng-model="firstName"
									placeholder="Imie" />
							</div>
							<div class="form-group">
								<label for="nazwisko">Nazwisko</label> <input type="text"
									class="form-control" data-ng-model="lastName"
									placeholder="Nazwisko" />
							</div>
							<div class="form-group">
								<label for="pseudo">Pseudo</label> <input type="text"
									class="form-control" data-ng-model="pseudo"
									placeholder="Pseudo" />
							</div>
						</div>
						<div class="form-group, col-md-6">
							<div class="form-group">
								<label for="Email">Email Prywatny</label> <input type="text"
									class="form-control" data-ng-model="emailPrv" placeholder="Email" />
							</div>
							<div class="form-group">
								<label for="Email">Email Służbowy</label> <input type="text"
									class="form-control" data-ng-model="emailOffice" placeholder="Email" />
							</div>
							<div class="form-group">
								<label for="Telefon">Telefon</label> <input type="text"
									class="form-control" data-ng-model="phone1"
									placeholder="Telefon 1" />
							</div>
							<div class="form-group">
								<label for="Telefon">Telefon</label> <input type="text"
									class="form-control" data-ng-model="phone2"
									placeholder="Telefon 2" />
							</div>
							<div class="form-group">
								<label for="Telefon">Telefon</label> <input type="text"
									class="form-control" data-ng-model="phone3"
									placeholder="Telefon 3" />
							</div>
						</div>
						<div class="form-group, col-md-6">
							<div class="form-group">
								<label for="Adres">Adres</label> <input type="text"
									class="form-control" data-ng-model="address"
									placeholder="Adres" />
							</div>
							<div class="form-group">
								<label for="Miasto">Miasto</label> <input type="text"
									class="form-control" data-ng-model="city" placeholder="Miasto" />
							</div>
							<div class="form-group">
								<label for="Kraj">Kraj</label> <input type="text"
									class="form-control" data-ng-model="country" placeholder="Kraj" />
							</div>
						</div>
						<div class="form-group col-md-6">
							<label for="Urodziny">Urodziny</label> <br>
							<div class="form-group, col-md-4">
								<select data-ng-model="birthdayYear" class="form-control">
									<option ng-repeat="year in years" value="{{year}}">{{year}}</option>
								</select>
							</div>
							<div class="form-group, col-md-4">
								<select data-ng-model="birthdayMonth" class="form-control">
									<option ng-repeat="month in months" value="{{month.id}}">{{month.value}}</option>
								</select>
							</div>
							<div class="form-group, col-md-4">
							<select data-ng-model="birthdayDay" class="form-control">
									<option ng-repeat="day in days" value="{{day.id}}">{{day.value}}</option>
								</select>
							</div>
						</div>
						<div class="form-group, col-md-6">
							<label for="Imieniny">Imieniny</label><br>
							<div class="form-group, col-md-4">
								<select data-ng-model="nameDayMonth" class="form-control">
									<option ng-repeat="month in months" value="{{month.id}}">{{month.value}}</option>
								</select>
							</div>
							<div class="form-group, col-md-4">
								<select data-ng-model="nameDayDay" class="form-control">
									<option ng-repeat="day in days" value="{{day.id}}">{{day.value}}</option>
								</select>
							</div>
						</div>
						<div class="form-group, col-md-12">
							<br>
							<div class="form-group">
								<label for="Opis">Opis</label>
								<textarea data-ng-model="description" class="form-control" rows="3"
									placeholder="Opis"></textarea>
							</div>
							<div class="form-group">
								<label for="Opis">Tagi</label>
								<textarea data-ng-model="tags" class="form-control" rows="3"
									placeholder="Tagi"></textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Zamknij</button>
					<button type="button" class="btn btn-primary"
						data-ng-click="addContact()" data-dismiss="modal">Zapisz</button>
				</div>
			</div>
		</div>
	</div>

	<div class="topMainMenu">
		<div class="btn-group">
			<button class="btn btn-primary btn-ms" role="button"
				data-toggle="modal" data-target="#addContact">Nowy Kontakt</button>
			<button class="btn btn-primary btn-ms" role="button"
				data-toggle="modal" data-target="#searchContact">Szukaj
				kontaktu</button>
			<button class="btn btn-primary btn-ms" role="button"
				data-toggle="modal" data-target="#editContact">Edytuj
				kontakt</button>
		</div>
	</div>
	<div data-ng-controller="ContactCtrl">{{test}}</div>

</div>