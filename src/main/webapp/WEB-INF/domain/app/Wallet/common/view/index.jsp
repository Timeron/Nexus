<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div data-ng-app="wallet" >
	<div>
	<!-- 	menu -->
	<div class="menu" data-ng-controller="WalletMenuCtrl">
	
	<!-- Modal -->
	<div class="modal" id="addAccountModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Nowe Konto</h4>
				</div>
				<div class="modal-body">
					<div id="newProject" class="modalTest" >
						<table>
							<tr>
								<td class="formName">Nazwa Konta</td>
								<td><input type="text" data-ng-model="newAccountName"></td>
							</tr>
							<tr>
								<td class="formName">Opis</td>
								<td><input type="text" data-ng-model="newAccountDescription"></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Zamknij</button>
					<button type="button" class="btn btn-primary" data-ng-click="addAccount()" data-dismiss="modal">Zapisz</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- Modal -->
	<div class="modal" id="addRecordModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" data-ng-controller="AddRecordCtrl">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">{{newRecordCurrentDescription}}</h4>
					<button type="button" class="btn btn-default toRight" model="income" data-ng-click="changeTransfer()">{{newRecordCurrentButtonDescription}}</button>
				</div>
				<div class="modal-body" style="width:100%; float:left">
					<div id="newProject" class="modalTest" >
						<table class="tableForm">
							<tr>
								<td class="formName" style="width:25%">Kwota</td>
								<td><input type="text" data-ng-model="amount"></td>
							</tr>
							<tr>
								<td class="formName">Opis</td>
								<td><textarea cols="50" rows="4" data-ng-model="newAccountDescription" ></textarea></td>
							</tr>
							<tr data-ng-if="!transfer">
								<td class="formName">Typ</td>
								<td>
									<select data-ng-model="$parent.type" ng-options="type.name for type in types">
      									<option value=""></option>
    								</select>
    							</td>
							</tr>
							<tr data-ng-if="transfer">
								<td class="formName">Na konto</td>
								<td>
									<select data-ng-model="$parent.account" ng-options="account.name for account in accounts">
      									<option value=""></option>
    								</select>
    							</td>
							</tr>
							<tr data-ng-if="!transfer">
								<td></td>
								<td><button type="button" class="btn btn-default" model="income" data-ng-click="changeIncome()">{{incomeCurrentDescription}}</button></td>
							</tr>
						</table>
						<table style="width:100%">
							<tr>
								<td style="width:22%"></td>
								<td><div class="font-m space-top-m">Dzień:</div><datepicker start="2010" offset="1" range="2030" model="operationDate"></datepicker>
								<div class="font-m space-top-m">Godzina:</div><timepicker now="true" model="operationTime"></timepicker></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Zamknij</button>
					<button type="button" class="btn btn-primary" data-ng-click="newRecord()" data-dismiss="modal">Zapisz</button>
				</div>
			</div>
		</div>
	</div>
	<div class="topMainMenu">
		<div class="btn-group">
			<a class="btn btn-primary btn-ms" role="button" data-toggle="modal" data-target="#addAccountModal">Dodaj Konto</a>
			<a class="btn btn-primary btn-ms" role="button" data-toggle="modal" data-target="#addRecordModal">Dodaj Operacje</a>
<!-- 			<a class="btn btn-primary btn-ms" role="button" data-ng-click="addOperation()">Dodaj wpis</a>  -->
<!-- 			<a class="btn btn-primary btn-ms" role="button" data-ng-click="addOperation()">Dodaj nowy typ</a>  -->
		</div>
	</div>
	</div>	
	<div id="presentation">
		<div data-ng-controller="WalletMainCtrl" >
			<aside id="leftAside">
				<div ng-repeat="account in accounts"><div ng-click="selectAccount(account)" class="clickable accounts">{{account.description}}</div></div>
			</aside>
			<main id="main">
				<section class="details"><div class="sectionName">Szczegóły:</div>
					<div class="accountSum">Srodki na koncie: <p>{{selectedAccount.sum}} zł</p></div>
					<div class="accountName">Konto: <p>{{selectedAccount.name}}</p></div>
					<div class="accountDesc">Opis: <p>{{selectedAccount.description}}</p></div>
				</section>
				<section class="chart"><div class="sectionName">Wykresy:</div>
				</section>
				<section class="operations">
					<div class="sectionName">Operacje:</div>
					<div class="recordRowHeader bold border-bottom">
						<div class="recordTypeIcon recordHeader">Typ</div><div class="recordTransfer recordHeader">Transfer</div><div class="recordValue recordHeader">kwota</div><div class="recordDate recordHeader">data</div>
					</div>
					<div mark-line mark="#A0C0DC" nmark="#DDD" class="recordRow" ng-repeat="record in selectedAccount.records">
						<div class="recordTypeIcon">{{record.recordTypeId}}</div>
						<div class="recordTransfer">{{setTransferAccount(record.destynationAccountId, record.income)}}</div>
						<div class="recordValue" change-font-color value="#E22" valid={{!record.income}}>{{record.value}}</div>
						<div class="recordDate">{{record.date | date:'d-MM-yyyy HH:mm' : 'UTC'}}</div>
					</div>
				</section>
			</main>
			
		</div>
	</div>
	<div data-ng-view></div>
	</div>
	
</div>