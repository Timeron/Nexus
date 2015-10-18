<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div data-ng-app="nexus" data-ng-controller="JTaskProjectCtr">
	<div data-ng-controller="JTaskBoardCtr">
		<div data-ng-controller="NexusConnectionCtrl" >
			<div remove-from-view hide="{{error}}">
				<div class="error">{{connectionError}}</div>
			</div>
		</div>
		<!-- 	menu -->
		<div class="bs-example">
			<div class="btn-group">
				<a class="btn btn-primary btn-ms" role="button"
					data-ng-click="openPreviouseProject()">Tablica</a> <a
					class="btn btn-primary btn-ms" role="button"
					data-ng-click="openBoard()">Wszystkie projekty</a> <a
					class="btn btn-primary btn-ms" role="button" data-toggle="modal"
					data-target="#addNewProjectModal">Nowy Projekt</a> <a
					class="btn btn-primary btn-ms" role="button"
					data-ng-click="openProjectSearch()">Szukaj Projektu</a> <a
					class="btn btn-primary btn-ms" role="button" data-toggle="modal"
					data-target="#helpModal">Pomoc</a>
			</div>
		</div>

		<div data-ng-view></div>
	</div>

</div>