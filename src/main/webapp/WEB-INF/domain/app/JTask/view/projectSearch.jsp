<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div data-ng-controller="JTaskSearchTaskCtrl" class="view">
	<div data-ng-repeat="project in projects" >
		<div projectSearchPresentation class="projectSearchPresentation">{{project.name}}</div>
	</div>
</div>