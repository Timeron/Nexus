<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div data-ng-app="wallet">
	<div>


		<!-- 	menu -->
		<div class="menu" data-ng-controller="WalletMenuCtrl">

			<!-- Modal -->
			<div class="modal" id="addTypeModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Nowe Konto</h4>
						</div>
						<div class="modal-body">
							<div id="newProject" class="modalTest">
								<table>
									<tr>
										<td class="formName">Nazwa</td>
										<td><input type="text" data-ng-model="newTypeName"></td>
									</tr>
									<tr>
										<td class="formName">Wartość domyślna</td>
										<td><input type="checkbox"
											data-ng-model="newDefaultValue"></td>
									</tr>
									<tr data-ng-if="!transfer">
										<td class="formName">Typ główny</td>
										<td><select data-ng-model="$parent.newParentType"
											ng-options="type.name for type in typesValidForParent">
												<option value=""></option>
										</select></td>
									</tr>
									<tr>
										<td class="formName">Kolor</td>
										<td><input type="text" data-ng-model="newTypeColor"></td>
									</tr>
									<tr>
										<td class="formName">Ikona</td>
										<td><input type="text" data-ng-model="newTypeIcon"></td>
									</tr>
								</table>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Zamknij</button>
							<button type="button" class="btn btn-primary"
								data-ng-click="addType()" data-dismiss="modal">Zapisz</button>
						</div>
					</div>
				</div>
			</div>

			<!-- Modal -->
			<div class="modal" id="editTypesModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width: 40%">
					<div class="modal-content" >
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Wybierz typ</h4>
						</div>
						<div class="modal-body">
							<div id="editType" class="modalEditType">
								<table>
									<tr>
										<th class="center">nazwa</th>
										<th class="center">ikona</th>
										<th class="center" colspan="2">kolor</th>
										<th class="center editTypeTableRow">dochód</th>
									</tr>
									<tr ng-repeat="type in copyOfTypes">
										<td><input type="text" data-ng-model="type.name" value="{{type.name}}" size="30"></td>
										<td class="editTypeTableRow">
											<button class="btn btn-default" ng-click="addIconToType(type.index)">
												<span class="glyphicon {{type.icon}} spaceBufor center"></span>
											</button>
											<div toggle toggleTime="1000" hide="{{type.addIconToTypeHide}}" class="addIconToType" >
												<div class="addIconToTypeMenu" >
													<div class="buttonClose" ng-click="closeAddIconToType(type.index)"><span class="glyphicon glyphicon-remove"></span></div>
												</div>
												<div class="glyphiconButton" ng-click="setIconToType(type.index, glyphicon.name)" ng-repeat="glyphicon in glyphicons">
													<span class="glyphicon {{glyphicon.name}}"></span>
												</div>
											</div>
										</td>
										<td class="editTypeTableRow">
											<input type="text" value="{{type.color}}" data-ng-model="type.color" size="5" maxlength="7">
										</td>
										<td style="background-color: {{type.color}}; color: {{type.color}}" class="spaceBufor editTypeTableRow">X</td>
										<td class="center">
											<input data-ng-model="type.defaultValue" type="checkbox" ng-checked="{{type.defaultValue}}">
										</td>
									</tr>
								</table>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Zamknij</button>
							<button type="button" class="btn btn-primary"
								data-ng-click="updateTypes(copyOfTypes)" data-dismiss="modal">Zapisz</button>
						</div>
					</div>
				</div>
			</div>

			<!-- Modal -->
			<div class="modal" id="addAccountModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Nowe Konto</h4>
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
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Zamknij</button>
							<button type="button" class="btn btn-primary"
								data-ng-click="addAccount()" data-dismiss="modal">Zapisz</button>
						</div>
					</div>
				</div>
			</div>


			<!-- Modal -->
			<div class="modal" id="addRecordModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true"
				data-ng-controller="AddRecordCtrl">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">{{newRecordCurrentDescription}}</h4>
							<button type="button" class="btn btn-default toRight"
								model="income" data-ng-click="changeTransfer()">{{newRecordCurrentButtonDescription}}</button>
						</div>
						<div class="modal-body" style="width: 100%; float: left">
							<div id="newProject" class="modalTest">
								<table class="tableForm">
									<tr>
										<td class="formName" style="width: 25%">Kwota</td>
										<td><input type="text" data-ng-model="amount"></td>
									</tr>
									<tr>
										<td class="formName">Opis</td>
										<td><textarea cols="50" rows="4"
												data-ng-model="newAccountDescription"></textarea></td>
									</tr>
									<tr data-ng-if="!transfer">
										<td class="formName">Typ</td>
										<td><select data-ng-model="$parent.type"
											ng-options="type.name for type in types">
												<option value=""></option>
										</select></td>
									</tr>
									<tr data-ng-if="transfer">
										<td class="formName">Na konto</td>
										<td><select data-ng-model="$parent.account"
											ng-options="account.name for account in accounts">
												<option value=""></option>
										</select></td>
									</tr>
									<tr data-ng-if="!transfer">
										<td></td>
										<td><button type="button" class="btn btn-default"
												model="income" data-ng-click="changeIncome()">{{incomeCurrentDescription}}</button></td>
									</tr>
								</table>
								<table style="width: 100%">
									<tr>
										<td style="width: 22%"></td>
										<td><div class="font-m space-top-m">Dzień:</div> <datepicker
												start="2010" offset="1" range="2030" model="operationDate"></datepicker>
											<div class="font-m space-top-m">Godzina:</div> <timepicker
												now="true" model="operationTime"></timepicker></td>
									</tr>
								</table>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Zamknij</button>
							<button type="button" class="btn btn-primary"
								data-ng-click="newRecord()" data-dismiss="modal">Zapisz</button>
						</div>
					</div>
				</div>
			</div>
			<div class="topMainMenu">
				<div class="btn-group">
					<button class="btn btn-primary btn-ms" role="button"
						data-toggle="modal" data-target="#addAccountModal">Dodaj
						Konto</button>
					<button class="btn btn-primary btn-ms" role="button"
						data-toggle="modal" data-target="#addRecordModal">Dodaj
						Operacje</button>
					<button class="btn btn-primary btn-ms" role="button"
						data-toggle="modal" data-target="#addTypeModal"
						data-ng-click="getTypesValidForParent()">Dodaj Typ</button>
					<div class="btn-group">
						<button type="button" class="btn btn-primary dropdown-toggle"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							Typy <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a role="button" data-toggle="modal"
								data-target="#addTypeModal" data-ng-click="getTypes()">Dodaj
									Typ</a></li>
							<li><a role="button" data-toggle="modal"
								data-target="#editTypesModal" data-ng-click="getCopyOfTypes()">Edytuj
									Typ</a></li>
							<li role="separator" class="divider"></li>
						</ul>
					</div>


					<!-- 			<a class="btn btn-primary btn-ms" role="button" data-ng-click="addOperation()">Dodaj nowy typ</a>  -->
				</div>
			</div>
		</div>
		<div id="presentation">
			<div data-ng-controller="WalletMainCtrl">
				<aside id="leftAside">
					<div ng-repeat="account in accounts">
						<div ng-click="selectAccount(account)" class="clickable accounts">{{account.name}}</div>
					</div>
				</aside>
				<main id="main">
				<section class="details">
					<div class="sectionName">Szczegóły:</div>
					<div class="accountSum">
						Srodki na koncie:
						<p>{{selectedAccount.sum}} zł</p>
					</div>
					<div class="accountName">
						Konto:
						<p>{{selectedAccount.name}}</p>
					</div>
					<div class="accountDesc">
						Opis:
						<p>{{selectedAccount.description}}</p>
					</div>
				</section>
				<section class="chart">

					<div class="sectionName">Wykresy:</div>
					<div class="lineChart"></div>
					<div class="pieChart svgCollapse"></div>

					<lineChart data="{{data}}"></lineChart>
					<div class="collapse">
						<div pieChart data="{{pieData}}" r="240" ir="200" width="900"
							height="500"></div>
						<div pieChart data="{{subPieData}}" r="200" ir="0" width="900"
							height="500"></div>
					</div>

				</section>
				<section class="operations">
					<div class="sectionName">Operacje:</div>
					<div class="recordRowHeader bold border-bottom">
						<div class="recordTypeIcon recordHeader">Typ</div>
						<div class="recordTransfer recordHeader">Transfer</div>
						<div class="recordValue recordHeader">kwota</div>
						<div class="recordDate recordHeader">data</div>
					</div>
					<div mark-line mark="#A0C0DC" nmark="#DDD" class="recordRow"
						ng-repeat="record in selectedAccount.records">
						<div recordTypeIcon type="{{recordTypeIcon(record.recordTypeId)}}" class="recordTypeIcon">{{record.recordTypeId}}</div>
						<div class="recordTransfer">{{setTransferAccount(record.destynationAccountId,
							record.sourceWalletAccountId, record.income)}}</div>
						<div class="recordValue" change-font-color value="#E22"
							valid={{!record.income}}>{{record.value}}</div>
						<div class="recordDate">{{record.date | date:'d-MM-yyyy
							HH:mm' : 'UTC'}}</div>
					</div>
				</section>
				</main>

			</div>
		</div>
		<div data-ng-view></div>
	</div>

</div>