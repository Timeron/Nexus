<div data-ng-app="nexus">

	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" data-ng-controller="JTaskNewProjectCtr">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Modal title</h4>
				</div>
				<div class="modal-body">
					<div id="newProject" class="modalTest" >
						<div>
							<input type="text" data-ng-model="newProjectName">
							{{newProjectName}} <input type="text" data-ng-model="newProjectDescription">
							{{newProjectDescription}}
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
	
	
	<div id="dashboard" class="view" data-ng-controller = "JTaskBoardCtr">	
<!-- 	menu -->
	<div class="bs-example">
		<div class="btn-group">
			<a class="btn btn-primary btn-ms" role="button" data-ng-click="openBoard()">Tablica</a> 
			<a class="btn btn-primary btn-ms" role="button" data-toggle="modal" data-target="#myModal">Nowy Projekt</a> 
			<a class="btn btn-primary btn-ms" role="button" href="/timeron-nexus/jtask/projects">Szukaj Projektu</a>
		</div>
	</div>	
	
<!-- 	lista projektów -->

		<div data-ng-repeat="project in projects">
			<div>
				<projectColumn index="{{$index}}" class="column" >
					<div class="projectColumnName btn btn-success" data-ng-click="openProject($index)">{{project.name}}</div>
					<div class="projectColumnExtend btn btn-success" data-ng-click="extendProject($index)" ><span class="glyphicon glyphicon-circle-arrow-down" aria-hidden="true"></span></div>
				</projectColumn>
			</div>
		</div>
	</div>
	
<!-- 	tablica projektów -->
	<div id="project" class="view" data-ng-controller = "JTaskProjectCtr">
		<div class="projectHeader">
			<div class="projectName">
				{{project.name}}
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
								<div class="task" data-ng-repeat="task in wait">
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
									<td class="taskSummary">{{task.summary}}</td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</projectBoardColumn>
			<projectBoardColumn>
				<div class="columnName">
					<div class="taskCounter">({{toDo.length}})</div>
					<p>To Do</p>
				</div>
				<div class="task" data-ng-repeat="task in toDo">
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
									<td class="taskSummary">{{task.summary}}</td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</projectBoardColumn>
			<projectBoardColumn>
				<div class="columnName">
					<div class="taskCounter">({{inProgress.length}})</div>
					<p>In progress</p>
				</div>
				<div class="task" data-ng-repeat="task in inProgress">
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
									<td class="taskSummary">{{task.summary}}</td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</projectBoardColumn>
			<projectBoardColumn>
				<div class="columnName">
					<div class="taskCounter">({{inReview.length}})</div>
					<p>In review</p>
				</div>
								<div class="task" data-ng-repeat="task in inReview">
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
									<td class="taskSummary">{{task.summary}}</td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</projectBoardColumn>
			<projectBoardColumn>
				<div class="columnName">
					<div class="taskCounter">({{done.length}})</div>
					<p>Done</p>
				</div>
								<div class="task" data-ng-repeat="task in done">
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
									<td class="taskSummary">{{task.summary}}</td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</projectBoardColumn>
			<projectBoardColumn>
				<div class="columnName">
					<div class="taskCounter">(id of task)</div>
					<p>Details</p>
				</div>
			</projectBoardColumn>
		</div>
	</div>

</div>