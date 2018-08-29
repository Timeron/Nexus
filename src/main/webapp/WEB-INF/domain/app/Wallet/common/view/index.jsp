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
									<tr data-ng-if="!newRecord.transfer">
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
				<div class="modal-dialog" style="width: 45%">
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
										<th class="center"></th>
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
										<td style="background-color: {{getTypeColor(type.parentId)}}; color: {{getTypeColor(type.parentId)}}" class="spaceBuforS">X</td>
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
				aria-labelledby="myModalLabel" aria-hidden="true">
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
										<td><input type="text" data-ng-model="newRecord.amount"></td>
									</tr>
									<tr>
										<td class="formName">Opis</td>
										<td><textarea cols="50" rows="4"
												data-ng-model="newRecord.description"></textarea></td>
									</tr>
									<tr data-ng-if="!newRecord.transfer">
										<td class="formName">Typ</td>
										<td><select data-ng-model="$parent.newRecord.type"
											ng-options="type.name for type in types">
												<option value=""></option>
										</select></td>
									</tr>
									<tr data-ng-if="newRecord.transfer">
										<td class="formName">Na konto</td>
										<td><select data-ng-model="$parent.newRecord.account"
											ng-options="account.name for account in accounts">
												<option value=""></option>
										</select></td>
									</tr>
									<tr data-ng-if="!newRecord.transfer">
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
								data-ng-click="addRecord()" data-dismiss="modal">Zapisz</button>
						</div>
					</div>
				</div>
			</div>
			
			<!-- Modal -->
			<div class="modal" id="editRecordModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">{{newRecordCurrentDescription}}</h4>
<!-- 							<button type="button" class="btn btn-default toRight" -->
<!-- 								model="income" data-ng-click="changeTransfer()">{{newRecordCurrentButtonDescription}}</button> -->
						</div>
						<div class="modal-body" style="width: 100%; float: left">
							<div id="editRecord" class="modalTest">
								<table class="tableForm">
									<tr>
										<td class="formName" style="width: 25%">Kwota</td>
										<td><input type="text" data-ng-model="recordToEdit.value"></td>
									</tr>
									<tr>
										<td class="formName">Opis</td>
										<td>
											<textarea cols="50" rows="4"
												data-ng-model="recordToEdit.description">
											</textarea>
										</td>
									</tr>
									<tr data-ng-if="!recordToEdit.transfer">
										<td class="formName">Typ</td>
										<td>{{getTypeName(recordToEdit.recordTypeId)}}</br>
											<select data-ng-model="$parent.recordToEdit.type"
											ng-options="type.name for type in types">
												<option value=""></option>
											</select>
										</td>
									</tr>
									<tr data-ng-if="recordToEdit.transfer">
										<td class="formName">Na konto</td>
										<td><select data-ng-model="$parent.recordToEdit.account"
											ng-options="account.name for account in accounts">
												<option value=""></option>
										</select></td>
									</tr>
									<tr data-ng-if="!recordToEdit.transfer">
										<td></td>
										<td><button type="button" class="btn btn-default"
												model="income" data-ng-click="changeIncome()">{{incomeCurrentDescription}}</button></td>
									</tr>
								</table>
								<table style="width: 100%">
									<tr>
										<td style="width: 22%"></td>
										<td><div class="font-m space-top-m">Dzień: </div> <datepicker
												start="2010" offset="1" range="2030" model="operationDate" init="{{recordToEdit.date}}"></datepicker>
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
								data-ng-click="editRecord()" data-dismiss="modal">Zapisz</button>
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
					<section class="details" id="details">
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
					
					<ul class="nav nav-tabs" role="tablist" id="menuSectionContainer">
		    			<li role="presentation"><a href="#Charts" aria-controls="Charts" role="tab" data-toggle="tab">Wykresy</a></li>
		    			<li role="presentation"><a href="#Records" aria-controls="Records" role="tab" data-toggle="tab">Operacje</a></li>
		    			<li role="presentation"><a href="#Statistics" aria-controls="Statistics" role="tab" data-toggle="tab">Statystyka</a></li>
		    			<li role="presentation"><a href="#Calendar" aria-controls="Calendar" role="tab" data-toggle="tab" ng-click="calendar()">Kalendarz</a></li>
		  			</ul>
					
					<div class="tab-content">
						<section role="tabpanel" class="chart tab-pane fade" id="Charts">
							<div class="sectionContainer">
								<div class="sectionName">Wykresy:</div>
								<div class="lineChart"></div>
			
								<lineChart data="{{data}}"></lineChart>
							</div>
						</section>
						<section role="tabpanel" class="chart tab-pane fade" id="Statistics">
							<div class="sectionContainer">
								<div class="sectionMenu"><button class="btn btn-primary btn-sm" ng-click="getStats('false')">Wydatki</button><button  class="btn btn-primary btn-sm" ng-click="getStats('true')">Dochody</button></div>
								<div class="sectionName">Statystyka:</div>
								<div class="chartBox">
									<div class="pieChart svgCollapse"></div>
									<div class="collapse">
									<div pieChart data="{{subPieData}}" r="130" ir="10" width="600" nameText="false"
										height="500"></div>
									<div pieChart data="{{pieData}}" r="240" ir="130" width="600" nameText="false"
										height="500"></div>
									
								</div>
									<div class="legend">
										<div class="bold border-bottom">Typy operacji</div>
										<div ng-repeat="children in pieData">
											<div class="legendRow clickable" data-toggle="tooltip" data-placement="top" title="{{children.value}} zł" ng-click="pieCliked(children);">
												<div style="background-color: {{children.color}}; color: {{children.color}}" class="legendColor">X</div>
												<div class="legendLabel">{{children.key}}</div>
											</div>
										</div>
									</div>
									<div class="legend">
										<div class="bold border-bottom">Grupy typów operacji</div>
										<div ng-repeat="children in subPieData">
											<div class="legendRow clickable" data-toggle="tooltip" data-placement="top" title="{{children.value}} zł">
												<div style="background-color: {{children.color}}; color: {{children.color}}" class="legendColor">X</div>
												<div class="legendLabel">{{children.key}}</div>
											</div>
										</div>
									</div>
									
								</div>
								<div class="chartBox">
									<div class="sectionName">{{selectedType.name}}</div>
									<div class="col-md-12">
										<div class="col-md-3">
											<select class="form-control" data-ng-model="selectedTypeTemp" ng-options="type.name for type in types">
												<option value=""></option>
											</select><button class="btn btn-sm btn-default" ng-click="loadTypeStats(selectedTypeTemp)">Załaduj</button>
										</div>
									</div>
									<div class="typeStatisticChart"></div>
									<div barChart data="{{typeStatisticData}}"></div>
								</div>
								<div>
									<div>Statystyka grupy typów</div>
									<div class="col-md-12 multiBarChartMenu">
										<div class="col-md-12 ">
											<div class="bold border-bottom">Typy operacji</div>
											<div ng-repeat="type in types">
												<div class="legendRow clickable" data-toggle="tooltip" data-placement="top">
													<input type="checkbox" class="checkbox" ng-model="type.multiBarChartMenu">
													<div style="background-color: {{type.color}}; color: {{type.color}};" class="legendColor">X</div>
													<div class="legendLabel">{{type.name}}</div>
												</div>
											</div>
										</div>
										<div class="col-md-12 ">
											<button class="btn btn-sm btn-default btn-filter" ng-click="multiBarChartSearch()">Filtruj</button>
										</div>
									</div>
								</div>
								<div multiBarChart class="multiBarChart" data="{{testtest.chart}}" colors="{{testtest.chart}}" ></div>
							</div>
						</section>
						<section role="tabpanel" class="operations tab-pane fade" id="Records">
							<div class="sectionContainer">
								<div class="sectionName">Operacje:</div>
								<div class="recordRowHeader bold border-bottom">
									<div class="recordTypeIcon recordHeader">Typ</div>
									<div class="recordTransfer recordHeader">Transfer</div>
									<div class="recordValue recordHeader">kwota</div>
									<div class="recordDate recordHeader">data</div>
									<div class="recordOption recordHeader"></div>
								</div>
								<div mark-line mark="#A0C0DC" nmark="#FFF" class="recordRow"
									ng-repeat="record in selectedAccount.records">
									<div recordTypeIcon type="{{recordTypeIcon(record.recordTypeId)}}" class="recordTypeIcon">{{record.recordTypeId}}</div>
									<div class="recordTransfer">{{setTransferAccount(record.destynationAccountId,
										record.sourceWalletAccountId, record.income)}}</div>
									<div class="recordValue" change-font-color value="#E22"
										valid={{!record.income}}>{{record.value}}</div>
									<div class="recordDate">{{record.date | date:'d-MM-yyyy
										HH:mm' : 'UTC'}}</div>
									<div class="recordOption">
										<button class="btn btn-primary btn-xs" role="button" 
										data-toggle="modal" data-target="#editRecordModal" ng-click="setToEdit(record)">
										<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
										</button>
									</div>
								</div>
							</div>
						</section>
					<section role="tabpanel" class="calendar tab-pane fade"
						id="Calendar">
						<div class="sectionContainer">
							<div class="sectionName">Kalendarz:</div>
							<div class="calendarTable">
								<div class="headerRow">
									<div class="menuBar">
										<div class="menuItem goBack" ng-click="goBack()"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true" style="font-size:25px;"></span></div>
										<div class="date">
											<div class="menuItem year">{{calendarYear}}</div>
											<div class="menuItem month">{{calendarMonthMap[calendarMonth]}}</div>
										</div>
										<div class="menuItem goNext" ng-click="goNext()"><span class="glyphicon glyphicon-arrow-right" aria-hidden="true" style="font-size:25px;"></span></div>
									</div>
									<div class="header">Poniedziałek</div>
									<div class="header">Wtorek</div>
									<div class="header">Środa</div>
									<div class="header">Czwartek</div>
									<div class="header">Piątek</div>
									<div class="header">Sobota</div>
									<div class="header">Niedziela</div>
								</div>
								<div class="days">
									<div class="day" ng-repeat="entry in calendarData">
										<div calendar-day key="entry.key" values="entry.value" ></div>
									</div>
								</div>
							</div>
					</section>

				</div>
				</main>

			</div>
		</div>
		<div data-ng-view></div>
	</div>
	
	
	<div>
</div>

</div>