<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="space-top-xxl padding-l">
	Szukaj: <input type="text" data-ng-model="search">

</div>
<div class="box border-bottom padding-l space-top-xxl"><div class='boxWidth10'>Id Taska</div><div class='boxWidth75'>Opis</div><div class='boxWidth10'>status</div></div>
<div class="taskSearchResults">
	<div data-ng-repeat="task in tasks | filter: search | filter : searchStatus track by $index"  >
		<div class=" box padding-l" taskInLine index="{{$index}}" data-ng-click="setTaskInNewWindow(task)">
			<div class='boxWidth10'>{{task.name}}</div>
			<div class='boxWidth75'>{{task.summary}}</div>
			<div class='boxWidth10'>{{task.statusDescription}}</div>
		</div>
	</div>
</div>