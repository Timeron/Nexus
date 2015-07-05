<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div data-ng-app="nexus" >
	<div data-ng-controller = "JTaskBoardCtr">
<!-- 	menu -->
	<div class="bs-example" >
		<div class="btn-group">
			<a class="btn btn-primary btn-ms" role="button" data-ng-click="openBoard()">Tablica</a> 
			<a class="btn btn-primary btn-ms" role="button" data-toggle="modal" data-target="#myModal">Nowy Projekt</a> 
			<a class="btn btn-primary btn-ms" role="button" href="/timeron-nexus/jtask/projects">Szukaj Projektu</a>
		</div>
	</div>	
	
	<div data-ng-view></div>
	</div>
	
</div>