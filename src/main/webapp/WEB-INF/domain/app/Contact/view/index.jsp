<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div data-ng-app="contact" >

<!-- Modals -->
	<!-- Modal -->
	<div class="modal" id="addContact" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Nowe Kontakt</h4>
				</div>
				<div class="modal-body">
					<div id="newProject" class="modalTest">
						<table>
							<tr>
								<td class="formName">Nazwa Konta</td>
								<td><input type="text" data-ng-model="newAccountName"></td>
							</tr>
							<tr>
								<td class="formName">Opis</td>
								<td><input type="text"
									data-ng-model="newAccountDescription"></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Zamknij</button>
					<button type="button" class="btn btn-primary"
						data-ng-click="addAccount()" data-dismiss="modal">Zapisz</button>
				</div>
			</div>
		</div>
	</div>

	<div class="bs-example">
		<div class="btn-group">
			<button class="btn btn-primary btn-ms" role="button" data-toggle="modal" data-target="#addContact">Nowy Kontakt</button>
			<button class="btn btn-primary btn-ms" role="button" data-toggle="modal" data-target="#searchContact">Szukaj kontaktu</button>
			<button class="btn btn-primary btn-ms" role="button" data-toggle="modal" data-target="#editContact">Edytuj kontakt</button>
		</div>
	</div>
<div data-ng-controller="ContactCtrl">
{{test}}
</div>

</div>