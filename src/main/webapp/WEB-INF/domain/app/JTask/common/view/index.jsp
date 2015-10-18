<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div data-ng-app="nexus" data-ng-controller="JTaskProjectCtr">
	<div data-ng-controller="JTaskBoardCtr">
		<div data-ng-controller="NexusConnectionCtrl">
			<div remove-from-view hide="{{error}}">
				<div class="error">{{connectionError}}</div>
			</div>
		</div>
		<!-- 	menu -->
		<div class="topMainMenu">
			<div class="btn-group">
				<div type="button" class="btn btn-primary btn-ms" role="button" data-ng-click="openPreviouseProject()">Tablica</div>
				<div type="button" class="btn btn-primary btn-ms" role="button" data-ng-click="openBoard()">Wszystkie projekty</div>
				<div type="button" class="btn btn-primary btn-ms" role="button" data-toggle="modal" data-target="#addNewProjectModal">Nowy Projekt</div>
				<div class="btn-group">
					<div type="button" class="btn btn-primary btn-ms" role="button" data-ng-click="openProjectSearch()">Szukaj Projektu</div>
				
					<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<span class="caret"></span>
					</button>
					<ul class="dropdown-menu" style="width: 100%">
					    <li><a data-ng-click="openProjectSearch()">Szukaj Projektu</a></li>
					    <li role="separator" class="divider"></li>
					    <li data-ng-repeat="project in projects">
					    	<a data-ng-click="openProject(project)">{{project.name}}</a>
					    </li>
					</ul>
				</div>
				<div type="button" class="btn btn-primary btn-ms" role="button" data-toggle="modal" data-target="#helpModal">Pomoc</div>
			</div>
		</div>

		<div data-ng-view></div>
	</div>

</div>