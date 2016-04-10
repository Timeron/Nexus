<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div ng-app="NexusAddApp" ng-controller="NexusAddAppCtrl">
	<div>
		<div>Dodaj aplikacje</div>
	</div>
	<div>
		<div>Nazwa: </div>
		<div><input type="text" data-ng-model="appName"></div>{{appName}}
	</div>
	<div>
		<div>Opis: </div>
		<div><input type="text" data-ng-model="appDescription"></div>
	</div>
	<div>
		<div>Klucz: </div>
		<div><input type="text" data-ng-model="appKey"></div>
	</div>
	<div class="btn btn-default" ng-click="save()" role="button">Zapisz</div>
</div>