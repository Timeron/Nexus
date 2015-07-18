<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="space-top-xxl padding-l">
	Szukaj: <input type="text" data-ng-model="search">
<!-- 	Status: <div class="btn-group"><button ng-repeat="status in statuses" class="btn btn-primary btn-xs" data-ng-click="selectStatus(status)">{{status}}</button></div> -->
	
<!-- 	<select ng-model="searchStatus.statusDescription" ng-options="status for status in statuses"> -->
<!--       			<option value="" selected="to do"></option> -->
<!--     		</select> -->
</div>
<div class="box border-bottom padding-l space-top-xxl"><div class='boxWidth10'>Id Taska</div><div class='boxWidth75'>Opis</div><div class='boxWidth10'>status</div></div>
<div data-ng-repeat="task in tasks | filter: search | filter : searchStatus"  >
	<div class="box padding-l" taskInLine data-ng-click="setTaskInNewWindow(task)">
		<div class='boxWidth10'>{{task.name}}</div>
		<div class='boxWidth75'>{{task.summary}}</div>
		<div class='boxWidth10'>{{task.statusDescription}}</div>
	</div>
</div>