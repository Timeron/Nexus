<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" data-ng-click="addProject()" data-dismiss="modal">Save changes</button>
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
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" data-ng-click="addProject()" data-dismiss="modal">Save changes</button>
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
					<h4 class="modal-title" id="myModalLabel">Add new task</h4>
				</div>
				<div class="modal-body">
					<table class="formContainer">
						<tbody>
							<tr>
								<td class="bold">Summary: </td>
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
								<td>Priority: </td>
								<td>
									<select ng-model="newPriority" ng-options="priority.id for priority in priorities">
      									<option value=""></option>
    								</select>
								</td>
							</tr>
							<tr class="separator"><td colspan="2"><hr></td></tr>
							<tr>
								<td>Description: </td>
								<td><textarea cols="40" rows="5" data-ng-model="newDescription"></textarea></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" data-ng-click="saveTask()" data-dismiss="modal">Save changes</button>
				</div>
			</div>
		</div>
	</div>
	
<div id="dashboard" class="view" >	
	
<!-- 	lista projektów -->
<div data-ng-controller = "JTaskBoardCtr" >
	<div data-ng-repeat="project in projects">
		<div>
			<projectColumn index="{{$index}}" class="column" >
				<div class="projectColumnName btn btn-success" data-ng-click="openProject(project)">{{project.name}}</div>
				<div class="projectColumnExtend btn btn-success" data-ng-click="extendProject($index)" ><span class="glyphicon glyphicon-circle-arrow-down" aria-hidden="true"></span></div>
			</projectColumn>
		</div>
	</div>
</div>
	
<!-- 	tablica projektów -->
	<div id="project" class="view">
		<div class="projectHeader">
			<div class="projectName">
				{{project.name}}
<!-- 				{{project.name}} -->
			</div>
			<div class="headerMenu">
				<div class="addTask btn btn-primary btn-xs" data-toggle="modal" data-target="#newTaskModal">Add Task <span class="glyphicon glyphicon-plus" aria-hidden="true"></div>
			</div>
		</div>
		<div>
			<projectBoardColumn>
				<div class="columnName">
					<div class="taskCounter">({{wait.length}})</div>
					<p>Wait</p>
				</div>
				<div class="task" data-ng-repeat="task in wait" data-ng-click="getTaskDetails(task)">
					<div class="type-{{task.taskTypeId}}"></div>
					<div class="taskContent">
						<table class="taksTable">
							<tbody>
								<tr>
									<td class="taskIconContainer"></td>
									<td class="taskName bold">{{task.name}}</td>
									<td class="userIcon"></td>
								</tr>
								<tr>
									<td class="priority">{{task.priority}}</td>
									<td><div class="taskSummary">{{task.summary}}</div></td>
									<td></td>
								</tr>
							</tbody>
						</table>
						<div class="btn-group btnTaskDirestion">
							<div class="btn btn-default btn-xs" data-ng-click="taskDirestionPreviousNext(task, 'next')">></div>
						</div>
					</div>
				</div>
			</projectBoardColumn>
			<projectBoardColumn>
				<div class="columnName">
					<div class="taskCounter">({{toDo.length}})</div>
					<p>To Do</p>
				</div>
				<div class="task" data-ng-repeat="task in toDo" data-ng-click="getTaskDetails(task)">
					<div class="type-{{task.taskTypeId}}"></div>
					<div class="taskContent">
						<table class="taksTable" >
							<tbody>
								<tr>
									<td class="taskIconContainer"></td>
									<td class="taskName bold">{{task.name}}</td>
									<td class="userIcon"></td>
								</tr>
								<tr>
									<td class="priority">{{task.priority}}</td>
									<td><div class="taskSummary">{{task.summary}}</div></td>
									<td></td>
								</tr>
							</tbody>
						</table>
						<div class="btn-group btnTaskDirestion">
							<div class="btn btn-default btn-xs" data-ng-click="taskDirestionPreviousNext(task, 'previous')"><</div>
							<div class="btn btn-default btn-xs" data-ng-click="taskDirestionPreviousNext(task, 'next')">></div>
						</div>
					</div>
				</div>
			</projectBoardColumn>
			<projectBoardColumn>
				<div class="columnName">
					<div class="taskCounter">({{inProgress.length}})</div>
					<p>In progress</p>
				</div>
				<div class="task" data-ng-repeat="task in inProgress" data-ng-click="getTaskDetails(task)">
					<div class="type-{{task.taskTypeId}}"></div>
					<div class="taskContent">
						<table class="taksTable">
							<tbody>
								<tr>
									<td class="taskIconContainer"></td>
									<td class="taskName bold">{{task.name}}</td>
									<td class="userIcon"></td>
								</tr>
								<tr>
									<td class="priority">{{task.priority}}</td>
									<td><div class="taskSummary">{{task.summary}}</div></td>
									<td></td>
								</tr>
							</tbody>
						</table>
						<div class="btn-group btnTaskDirestion">
							<div class="btn btn-default btn-xs" data-ng-click="taskDirestionPreviousNext(task, 'previous')"><</div>
							<div class="btn btn-default btn-xs" data-ng-click="taskDirestionPreviousNext(task, 'next')">></div>
						</div>
					</div>
				</div>
			</projectBoardColumn>
			<projectBoardColumn>
				<div class="columnName">
					<div class="taskCounter">({{inReview.length}})</div>
					<p>In review</p>
				</div>
				<div class="task" data-ng-repeat="task in inReview" data-ng-click="getTaskDetails(task)">
					<div class="type-{{task.taskTypeId}}"></div>
					<div class="taskContent">
						<table class="taksTable">
							<tbody>
								<tr>
									<td class="taskIconContainer"></td>
									<td class="taskName bold">{{task.name}}</td>
									<td class="userIcon"></td>
								</tr>
								<tr>
									<td class="priority">{{task.priority}}</td>
									<td><div class="taskSummary">{{task.summary}}</div></td>
									<td></td>
								</tr>
							</tbody>
						</table>
						<div class="btn-group btnTaskDirestion">
							<div class="btn btn-default btn-xs" data-ng-click="taskDirestionPreviousNext(task, 'previous')"><</div>
							<div class="btn btn-default btn-xs" data-ng-click="taskDirestionPreviousNext(task, 'next')">></div>
						</div>
					</div>
				</div>
			</projectBoardColumn>
			<projectBoardColumn>
				<div class="columnName">
					<div class="taskCounter">({{done.length}})</div>
					<p>Done</p>
				</div>
				<div class="task" data-ng-repeat="task in done" data-ng-click="getTaskDetails(task)">
					<div class="type-{{task.taskTypeId}}"></div>
					<div class="taskContent">
						<table class="taksTable">
							<tbody>
								<tr>
									<td class="taskIconContainer"></td>
									<td class="taskName bold">{{task.name}}</td>
									<td class="userIcon"></td>
								</tr>
								<tr>
									<td class="priority">{{task.priority}}</td>
									<td><div class="taskSummary">{{task.summary}}</div></td>
									<td></td>
								</tr>
							</tbody>
						</table>
						<div class="btn-group btnTaskDirestion">
							<div class="btn btn-default btn-xs" data-ng-click="taskDirestionPreviousNext(task, 'previous')"><</div>
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
					<div class="taskDetailsPriority">Piorytet: {{taskDetails.priority}}</div>
					<div class="taskDetailsSummary">Opis: <p>{{taskDetails.summary}}</p></div>
					<div class="taskDetailsDescription">Szczegóły: <p>{{taskDetails.description}}</p></div>
					<div class="taskDetailsDates">
						<div class="taskDetailsCreated">Dodany: {{taskDetails.created}}</div>
						<div class="taskDetailsUpdated">Zmieniony: {{taskDetails.updated}}</div>
					</div>
				</div>
			</projectBoardColumn>
		</div>
	</div>
</div>