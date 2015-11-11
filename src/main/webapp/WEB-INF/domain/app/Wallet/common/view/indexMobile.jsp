<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="calculatorBody" data-ng-app="wallet" data-ng-controller="WalletMenuCtrl" style="width: 100%; float: left">
	<div id="topMenu">
		<div id="close">
			<div class="glyphicon glyphicon-remove icon"></div>
		</div>
		<div id="ok" ng-click="newRecord()">
			<div class="glyphicon glyphicon-ok icon" ></div>
		</div>
	</div>
	<div id="calculatorValue">
		<div id="middleMenu">
			<div class="calendar glyphicon glyphicon-calendar" ng-click="getDate()"></div>
			<div ng-switch on="transfer">
				<div id="income" ng-switch-when="false">
					<div class="income" ng-click="changeIncomeTrue()" income="{{!income}}">Przychód</div>
					<div class="separator">|</div>
					<div class="income" ng-click="changeIncomeFalse()" income="{{income}}">Wydatek</div>
				</div>
			</div>
		</div>
		<div id="valueContainer">
			<div id="currency"><p>PLN</p></div>
			<div id="value"><p>{{amount}}</p></div>
		</div>
	</div>
	<div id="menu">
		<div class="account" ng-click="getAccounts()">Konto<div>{{currentAccount.name}}</div></div>
		<div class="transfer" ng-click="setTransfer()"><div transferIcon="{{transfer}}" class="transferIcon glyphicon glyphicon-random" ></div></div>
		<div ng-switch on="transfer">
			<div ng-switch-when="false">
				<div class="category" ng-click="getTypes()">Kategoria<div>{{type.name}}</div></div>
			</div>
			<div ng-switch-when="true">
				<div class="account" ng-click="getTargetAccounts()">Konto docelowe<div>{{targetAccount.name}}</div></div>
			</div>
		</div>
	</div>
	<div id="calculator">
		<div id="operators">
			<div class="calculatorButtonOperator" ng-click="set('/')"><p>/</p></div>
			<div class="calculatorButtonOperator" ng-click="set('*')"><p>*</p></div>
			<div class="calculatorButtonOperator" ng-click="set('-')"><p>-</p></div>
			<div class="calculatorButtonOperator" ng-click="set('+')"><p>+</p></div>
			<div class="calculatorButtonOperator" ng-click="set('=')"><p>=</p></div>
		</div>
		<div id="numbers">
			<div class="calculatorButton" ng-click="set(1)"><p>1</p></div><div class="calculatorButton" ng-click="set(2)"><p>2</p></div><div class="calculatorButton" ng-click="set(3)"><p>3</p></div>
			<div class="calculatorButton" ng-click="set(4)"><p>4</p></div><div class="calculatorButton" ng-click="set(5)"><p>5</p></div><div class="calculatorButton" ng-click="set(6)"><p>6</p></div>
			<div class="calculatorButton" ng-click="set(7)"><p>7</p></div><div class="calculatorButton" ng-click="set(8)"><p>8</p></div><div class="calculatorButton" ng-click="set(9)"><p>9</p></div>
			<div class="calculatorButton" ng-click="set('.')"><p>,</p></div><div class="calculatorButton" ng-click="set(0)"><p>0</p></div><div class="calculatorButton" ng-click="set('C')"><p class="">C</p></div>
		</div>

	</div>
	
	
	<div popupMenu hide="{{accountsHide}}" id="getAccounts">
		<div id="topMenu">
			<div id="close" ng-click="closeGetAccounts()">
				<div class="glyphicon glyphicon-remove icon" ></div>
			</div>
			<div id="ok">
				<div class="glyphicon glyphicon-ok icon""></div>
			</div>
		</div>
		<div id="accountsMenu" ng-repeat="account in accounts">
			<div class="rowName" ng-click="setCurrentAccount(account.id)">{{account.name}}</div>
		</div>
	</div>
	<div popupMenu hide="{{targetAccountsHide}}" id="getAccounts">
		<div id="topMenu">
			<div id="close" ng-click="closeGetTargetAccounts()">
				<div class="glyphicon glyphicon-remove icon" ></div>
			</div>
			<div id="ok">
				<div class="glyphicon glyphicon-ok icon""></div>
			</div>
		</div>
		<div id="accountsMenu" ng-repeat="account in accounts">
			<div class="rowName" ng-click="setTargetAccount(account.id)">{{account.name}}</div>
		</div>
	</div>
	
	<div popupMenu hide="{{typeHide}}" id="getTypes">
		<div id="topMenu">
			<div id="close" ng-click="closeGetTypes()">
				<div class="glyphicon glyphicon-remove icon" ></div>
			</div>
		</div>
		<div id="typesMenu" ng-repeat="type in types">
			<div class="rowName" ng-click="setCurrentType(type.id)">{{type.name}}</div>
		</div>
	</div>
	
	<div popupMenu hide="{{dateHide}}" id="getDate">
		<div id="topMenu">
			<div id="close" ng-click="closeGetDate()">
				<div class="glyphicon glyphicon-remove icon" ></div>
			</div>
		</div>
		<div>
			<datepickermobile model="operationDate"></datepickermobile>
			<timepicker now="true" model="operationTime"></timepicker>
		</div>
	</div>
</div>

