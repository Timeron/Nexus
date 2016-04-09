<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<!-- Modal -->
	<div class="modal fade" id="helpModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" data-ng-controller="HelpCrtl">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Pomoc</h4>
				</div>
				<div class="modal-body">
					<div id="about" class="modalTest" >
						<div class="bold font-l border-bottom">Informacje o aplikacji</div>
						<div class="paragraph-l">
							<div class="appName font-l bold">{{appName}}</div>
							<div class="appVarsion">Wersja: <span class="bold">{{appVarsion}}</span></div>
							<div class="space-top-l paragraph-l"><p class="border-bottom">O wersji:</p><p>{{comment}}</p></div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
					<button type="button" class="btn btn-primary" data-ng-click="addProject()" data-dismiss="modal">Zapisz</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="addNewProjectModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" data-ng-controller="JTaskNewProjectCtr">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Nowy Projekt</h4>
				</div>
				<div class="modal-body">
					<div id="newProject" class="modalTest" >
						<table>
							<tr>
								<td class="formName">Nazwa Projektu</td>
								<td><input type="text" data-ng-model="newProjectName"></td>
							</tr>
							<tr>
								<td class="formName">Opis</td>
								<td><input type="text" data-ng-model="newProjectDescription"></td>
							</tr>
							<tr>
								<td class="formName">Prefix dla zadań</td>
								<td><input type="text" data-ng-model="newProjectPrefix"></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
					<button type="button" class="btn btn-primary" data-ng-click="addProject()" data-dismiss="modal">Zapisz</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="newTaskModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" data-ng-controller="JTaskNewTaskCtr"> 
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Nowy Task</h4>
				</div>
				<div class="modal-body">
					<table class="formContainer">
						<tbody>
							<tr>
								<td class="bold">Nazwa: </td>
								<td><input type="text" data-ng-model="newSummary"></td>
							</tr>
							<tr>
								<td class="bold">Type: </td>
								<td>
									<select ng-model="newType" ng-options="taskType.name for taskType in taskTypes">
      									<option value="1"></option>
    								</select>
								</td>
							</tr>
							<tr>
								<td>Priorytet: </td>
								<td>
									<select ng-model="newPriority" ng-options="priority.id for priority in priorities">
      									<option value=""></option>
    								</select>
								</td>
							</tr>
							<tr class="separator"><td colspan="2"><hr></td></tr>
							<tr>
								<td>Opis: </td>
								<td><textarea cols="40" rows="5" data-ng-model="newDescription"></textarea></td>
							</tr>
							<tr class="separator"><td colspan="2"><hr></td></tr>
							<tr>
								<td>Terminy </td>
								<td><input type="checkbox" data-ng-model="timers"></td>
							</tr>
							<tr>
								<td>Ostateczny termin: </td>
								<td>
									<div class="font-m space-top-m">Dzień:</div><datepicker offset="0" range="20" model="date" disable="{{timers}}"></datepicker><!--YYYY-MM-DD hh:mm:ss.s -->
									<div class="font-m space-top-m">Godzina:</div><timepicker model="time"></timepicker>
								</td>
							</tr>
							<tr>
								<td  style="height: 100px">Przewidywany czas pracy: </td>
								<td>
 									<postponedpicker model="workExpected"></postponedpicker><!--YYYY-MM-DD hh:mm:ss.s -->
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
					<button type="button" class="btn btn-primary" data-ng-click="saveTask()" data-dismiss="modal">Zapisz</button>
				</div>
			</div>
		</div>
	</div>
	
<div id="dashboard" class="view" >	
	
<!-- 	lista projektów -->
<div data-ng-controller = "JTaskBoardCtr" >
	<div curtain hide="{{!curtain}}" class="projectsCurtain">
		<div data-ng-repeat="project in projects">
			<div>
				<projectColumn index="{{$index}}" class="column" >
					<div class="projectColumnName btn btn-success" data-ng-click="openProject(project)">{{project.name}}</div>
					<div class="projectColumnExtend btn btn-success" data-ng-click="extendProject($index)" ><span class="glyphicon glyphicon-circle-arrow-down" aria-hidden="true"></span></div>
				</projectColumn>
			</div>
		</div>
	</div>
	<div class="curtainButton clickable" data-ng-click="projectsCurtain()">
		<div ng-switch on="curtain">
			<div ng-switch-when="false">
				<span class="glyphicon glyphicon-chevron-down"></span>
			</div>
			<div ng-switch-when="true">
				<span class="glyphicon glyphicon-chevron-up"></span>
			</div>
		</div>
	</div>
</div>
	
<!-- 	tablica projektów -->
	<div id="project" class="view">
		<div class="projectHeader">
			<div class="projectName">
				{{project.name}}
			</div>
			<div class="headerMenu btn-group-vertical" data-ng-show="projectId">
				<div class="addTask btn btn-primary btn-xs" data-ng-click="projectConfig()">Ustawienia <span class="glyphicon glyphicon-plus" aria-hidden="true"></div>
				<div class="addTask btn btn-primary btn-xs" data-toggle="modal" data-target="#newTaskModal">Dodaj Task <span class="glyphicon glyphicon-plus" aria-hidden="true"></div>
				<div class="addTask btn btn-primary btn-xs" data-ng-click="searchTask()">Szukaj Tasków <span class="glyphicon glyphicon-search" aria-hidden="true"></div>
			</div>
		</div>
		<div>
			<projectBoardColumn>
				<div class="columnName">
					<div class="taskCounter">({{wait.length}})</div>
					<p>Wait</p>
				</div>
				<div taskSelection endDate="{{task.endDate}}" workExpected="{{task.workExpected}}" marked="{{task.marked}}" class="task" data-ng-repeat="task in wait" data-ng-click="getTaskDetails(task)">
					<div class="type-{{task.taskTypeId}} "></div>
					<div class="taskContent">
						<table class="taksTable">
							<tbody>
								<tr>
									<td class="taskIconContainer{{task.taskTypeId}}"></td>
									<td class="taskName bold">{{task.name}}</td>
									<td width="35"><img src='<c:url value="/resources/image/avatar/{{task.user.nickLogo}}35.png" />' title="{{task.user.firstName}} {{task.user.lastName}}"></td>
								</tr>
								<tr>
									<td class="priority">{{task.priority}}</td>
									<td><div class="taskSummary">{{task.summary}}</div></td>
									<td></td>
								</tr>
							</tbody>
						</table>
						<div class="btn-group btnTaskDirestion">
							<div class="btn btn-default btn-xs" data-ng-click="taskDirestionPreviousNext(task, 'next')"><span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></div>
						</div>
					</div>
				</div>
			</projectBoardColumn>
			<projectBoardColumn>
				<div class="columnName">
					<div class="taskCounter">({{toDo.length}})</div>
					<p>To Do</p>
				</div>
				<div taskSelection endDate="{{task.endDate}}" workExpected="{{task.workExpected}}" marked="{{task.marked}}" class="task" data-ng-repeat="task in toDo" data-ng-click="getTaskDetails(task)">
					<div class="type-{{task.taskTypeId}}"></div>
					<div class="taskContent">
						<table class="taksTable" >
							<tbody>
								<tr>
									<td class="taskIconContainer{{task.taskTypeId}}"></td>
									<td class="taskName bold">{{task.name}}</td>
									<td width="35"><img src='<c:url value="/resources/image/avatar/{{task.user.nickLogo}}35.png" />' title="{{task.user.firstName}} {{task.user.lastName}}"></td>
								</tr>
								<tr>
									<td class="priority">{{task.priority}}</td>
									<td><div class="taskSummary">{{task.summary}}</div></td>
									<td></td>
								</tr>
							</tbody>
						</table>
						<div class="btn-group btnTaskDirestion">
							<div class="btn btn-default btn-xs" data-ng-click="taskDirestionPreviousNext(task, 'previous')"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></div>
							<div class="btn btn-default btn-xs" data-ng-click="taskDirestionPreviousNext(task, 'next')"><span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></div>
						</div>
					</div>
				</div>
			</projectBoardColumn>
			<projectBoardColumn>
				<div class="columnName">
					<div class="taskCounter">({{inProgress.length}})</div>
					<p>In progress</p> 
				</div>
				<div taskSelection endDate="{{task.endDate}}" workExpected="{{task.workExpected}}" marked="{{task.marked}}" class="task" data-ng-repeat="task in inProgress" data-ng-click="getTaskDetails(task)">
					<div class="type-{{task.taskTypeId}}"></div>
					<div class="taskContent">
						<table class="taksTable">
							<tbody>
								<tr>
									<td class="taskIconContainer{{task.taskTypeId}}"></td>
									<td class="taskName bold">{{task.name}}</td>
									<td width="35"><img src='<c:url value="/resources/image/avatar/{{task.user.nickLogo}}35.png" />' title="{{task.user.firstName}} {{task.user.lastName}}"></td>
								</tr>
								<tr>
									<td class="priority">{{task.priority}}</td>
									<td><div class="taskSummary">{{task.summary}}</div></td>
									<td></td>
								</tr>
							</tbody>
						</table>
						<div class="btn-group btnTaskDirestion">
							<div class="btn btn-default btn-xs" data-ng-click="taskDirestionPreviousNext(task, 'previous')"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></div>
							<div class="btn btn-default btn-xs" data-ng-click="taskDirestionPreviousNext(task, 'next')"><span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></div>
						</div>
					</div>
				</div>
			</projectBoardColumn>
			<projectBoardColumn>
				<div class="columnName">
					<div class="taskCounter">({{inReview.length}})</div>
					<p>In review</p>
				</div>
				<div taskSelection endDate="{{task.endDate}}" workExpected="{{task.workExpected}}" marked="{{task.marked}}" class="task" data-ng-repeat="task in inReview" data-ng-click="getTaskDetails(task)">
					<div class="type-{{task.taskTypeId}}"></div>
					<div class="taskContent">
						<table class="taksTable">
							<tbody>
								<tr>
									<td class="taskIconContainer{{task.taskTypeId}}"></td>
									<td class="taskName bold">{{task.name}}</td>
									<td width="35"><img src='<c:url value="/resources/image/avatar/{{task.user.nickLogo}}35.png" />' title="{{task.user.firstName}} {{task.user.lastName}}"></td>
								</tr>
								<tr>
									<td class="priority">{{task.priority}}</td>
									<td><div class="taskSummary">{{task.summary}}</div></td>
									<td></td>
								</tr>
							</tbody>
						</table>
						<div class="btn-group btnTaskDirestion">
							<div class="btn btn-default btn-xs" data-ng-click="taskDirestionPreviousNext(task, 'previous')"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></div>
							<div class="btn btn-default btn-xs" data-ng-click="taskDirestionPreviousNext(task, 'next')"><span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></div>
						</div>
					</div>
				</div>
			</projectBoardColumn>
			<projectBoardColumn>
				<div class="columnName">
					<div class="taskCounter">({{done.length}})</div>
					<p>Done</p>
				</div>
				<div taskSelection marked="{{task.marked}}" class="task" data-ng-repeat="task in done" data-ng-click="getTaskDetails(task)">
					<div class="type-{{task.taskTypeId}}"></div>
					<div class="taskContent">
						<table class="taksTable">
							<tbody>
								<tr>
									<td class="taskIconContainer{{task.taskTypeId}}"></td>
									<td class="taskName bold">{{task.name}}</td>
									<td width="35"><img src='<c:url value="/resources/image/avatar/{{task.user.nickLogo}}35.png" />' title="{{task.user.firstName}} {{task.user.lastName}}"></td>
								</tr>
								<tr>
									<td class="priority">{{task.priority}}</td>
									<td><div class="taskSummary">{{task.summary}}</div></td>
									<td></td>
								</tr>
							</tbody>
						</table>
						<div class="btn-group btnTaskDirestion">
							<div class="btn btn-default btn-xs" data-ng-click="taskDirestionPreviousNext(task, 'previous')"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></div>
							<div class="btn btn-default btn-xs" data-ng-click="taskClose(task)"><span class="glyphicon glyphicon-remove" aria-hidden="true"></div>
						</div>
					</div>
				</div>
			</projectBoardColumn>
			<projectBoardColumn>
				<div class="columnName">
					<div class="taskCounter">(id: {{taskDetails.name}})</div>
					<p>Details</p>
				</div>
				<div id="taskDetails">
<!-- 					<div class="taskDetailsName clickable" role="button" data-toggle="modal" data-target="#taskDetailsModal" data-ng-click="setTaskInNewWindow(taskDetails)">{{taskDetails.name}}</div> -->
<!-- 					<div class="taskDetailsName clickable" role="button" data-toggle="modal" data-target="#taskDetailsModal" data-ng-click="setTaskInNewWindow(taskDetails)" >{{taskDetails.name}}</div> -->
					<a class="taskDetailsName clickable" data-ng-click="setTaskInNewWindow(taskDetails)" >{{taskDetails.name}}</a>
					<p class="taskDetailsMainTaskName" >{{taskDetails.mainTaskName}}</p>
					<div class="taskDetailsPriority">Piorytet: {{taskDetails.priority}}</div>
					
					<div class="taskDetailsSummary">Opis: <p>{{taskDetails.summary}}</p></div>
					<div class="taskDetailsDescription">Szczegóły: <p ng-bind-html="taskDetails.description | trusted"></p></div>
					<div class="taskDetailsSummary border-bottom" data-ng-if="chasTermins()">
						<div class="space-top-xxl border-bottom">Terminy:</div> 
						<div class="paragraph-m font-m">Zakończenie: {{polishDate(taskDetails.endDate)}}</div>
						<div class="paragraph-m font-m">Przewidywany czes: {{msToDaysandHours(taskDetails.workExpected)}}</div>
						
					</div>
					<div class="taskDetailsDates">
						<div class="taskDetailsCreated">Dodany: {{taskDetails.created}}</div>
						<div class="taskDetailsUpdated">Zmieniony: {{taskDetails.updated}}</div>
					</div>
				</div>
			</projectBoardColumn>
		</div>
	</div>
</div>