<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div data-ng-app="Car" data-ng-controller="CarMainCtrl">
	<div id="topMenu">
		<div id="close" ng-click="cleanNewRecord()">
			<div class="glyphicon glyphicon-remove icon"></div>
		</div>
		<div id="ok" ng-click="newRecord()">
			<div class="glyphicon glyphicon-ok icon" ></div>
		</div>
	</div>
	
	<div id="scene">
		<div id="date" ng-click="getDate()">
			<div class="date" dateToString date="{{operationDate}}">{{date}}<span class="glyphicon glyphicon-calendar"></span></div>
		</div>
		<div id="mainValue" ng-click="getOil()">
			<div class="value">{{oil}}</div>
			<div class="unit">L</div>
		</div>
		<div id="secondValue" ng-click="getDistance()">
			<div class="value">{{distance}}</div>
			<div class="unit">km</div>
		</div>
	</div>
	
	<div id="list">
		<div class="header">{{message}}</div>
		<div class="rows">
			<div ng-repeat="operation in operations" >
				<div class="listRow"><p class="date">{{operation.dateString}}</p><p class="distance">{{operation.distance}} km</p><p class="oil">{{operation.liters}} L</p></div>
			</div>
		</div>
	</div>
	
	
	<div id="calculator" popupMenu hide="{{oilCalculatorHide}}">
		<div id="operators">
			<div class="calculatorButtonOperator" ng-click="setOil('/')"><p>/</p></div>
			<div class="calculatorButtonOperator" ng-click="setOil('*')"><p>*</p></div>
			<div class="calculatorButtonOperator" ng-click="setOil('-')"><p>-</p></div>
			<div class="calculatorButtonOperator" ng-click="setOil('+')"><p>+</p></div>
			<div class="calculatorButtonOperator" ng-click="setOil('=')"><p>=</p></div>
		</div>
		<div id="numbers">
			<div class="calculatorButton" ng-click="setOil(1)"><p>1</p></div><div class="calculatorButton" ng-click="setOil(2)"><p>2</p></div><div class="calculatorButton" ng-click="setOil(3)"><p>3</p></div>
			<div class="calculatorButton" ng-click="setOil(4)"><p>4</p></div><div class="calculatorButton" ng-click="setOil(5)"><p>5</p></div><div class="calculatorButton" ng-click="setOil(6)"><p>6</p></div>
			<div class="calculatorButton" ng-click="setOil(7)"><p>7</p></div><div class="calculatorButton" ng-click="setOil(8)"><p>8</p></div><div class="calculatorButton" ng-click="setOil(9)"><p>9</p></div>
			<div class="calculatorButton" ng-click="setOil('.')"><p>,</p></div><div class="calculatorButton" ng-click="setOil(0)"><p>0</p></div><div class="calculatorButton" ng-click="setOil('C')"><p class="">C</p></div>
		</div>
	</div>
	
	<div id="calculator" popupMenu hide="{{distanceCalculatorHide}}">
		<div id="operators">
			<div class="calculatorButtonOperator" ng-click="setDistance('/')"><p>/</p></div>
			<div class="calculatorButtonOperator" ng-click="setDistance('*')"><p>*</p></div>
			<div class="calculatorButtonOperator" ng-click="setDistance('-')"><p>-</p></div>
			<div class="calculatorButtonOperator" ng-click="setDistance('+')"><p>+</p></div>
			<div class="calculatorButtonOperator" ng-click="setDistance('=')"><p>=</p></div>
		</div>
		<div id="numbers">
			<div class="calculatorButton" ng-click="setDistance(1)"><p>1</p></div><div class="calculatorButton" ng-click="setDistance(2)"><p>2</p></div><div class="calculatorButton" ng-click="setDistance(3)"><p>3</p></div>
			<div class="calculatorButton" ng-click="setDistance(4)"><p>4</p></div><div class="calculatorButton" ng-click="setDistance(5)"><p>5</p></div><div class="calculatorButton" ng-click="setDistance(6)"><p>6</p></div>
			<div class="calculatorButton" ng-click="setDistance(7)"><p>7</p></div><div class="calculatorButton" ng-click="setDistance(8)"><p>8</p></div><div class="calculatorButton" ng-click="setDistance(9)"><p>9</p></div>
			<div class="calculatorButton" ng-click="setDistance('.')"><p>,</p></div><div class="calculatorButton" ng-click="setDistance(0)"><p>0</p></div><div class="calculatorButton" ng-click="setDistance('C')"><p class="">C</p></div>
		</div>
	</div>
	
	<div popupMenu hide="{{dateHide}}" id="getDate">
		<div id="topMenuDate">
			<div id="closeDate" ng-click="closeGetDate()">
				<div class="glyphicon glyphicon-remove icon" ></div>
			</div>
			<div id="ok" ng-click="setGetDate()">
			<div class="glyphicon glyphicon-ok icon" ></div>
		</div>
		</div>
		<div>
			<datepickermobile model="operationDate"></datepickermobile>
		</div>
	</div>
</div>