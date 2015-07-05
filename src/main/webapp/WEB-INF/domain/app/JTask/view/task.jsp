<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- <div class="modal fade" id="taskDetailsModal" tabindex="-1"	role="dialog" aria-labelledby="taskDetailsModalLabel"	aria-hidden="true" > -->
<!-- 	<div class="modal-dialog"> -->
<div data-ng-controller="TaskController">
	<div class="modal-header">
		<h4 class="modal-title" id="myModalLabel">Task {{task.name}}</h4>
	</div>
	<div class="modal-body">
		<div id=taskMainWindow>
			<div class="taskDetailsName"
				data-ng-click="setTaskInNewWindow(taskDetails)">{{task.name}}</div>
			<div class="taskDetailsTaskTypeId">{{task.taskType}}</div>
			<div class="taskDetailsPriority">Piorytet: {{task.priority}}</div>
			<div id="taskMainWindowContent">
				<div class="btn-group"></div>

				<div class="taskDetailsSummary">
					Opis:
					<p>{{task.summary}}</p>
				</div>
				<div class="taskDetailsDescription">
					Szczegóły:
					<p>{{task.description}}</p>
				</div>
				<div class="taskDetailsDates">
					<div class="taskDetailsCreated">Dodany: {{task.created}}</div>
					<div class="taskDetailsUpdated">Zmieniony: {{task.updated}}</div>
				</div>

				<div id="taskWindowDetails">
					<div class="btn-group">
						<button type="button" class="btn btn-primary btn-xs"
							data-ng-click="getHistory(task)">Aktywność</button>
						<!-- 								<button type="button" class="btn btn-primary btn-xs">Notatki</button> -->
						<!-- 								<button type="button" class="btn btn-primary btn-xs">Załączniki</button> -->
					</div>
					<div class="taskHistory">
						<div class="detailsName">Aktywność</div>
						<div class="historyEvent" data-ng-repeat="history in histories">
							<div data-ng-if="isStatusChange(history)">
								<div class="border-bottom">
									<div class="historyEventName">Zmiana statusu</div>
									<div class="historyEventDate">{{history.created}}</div>
								</div>
								<div class="historyEventContent">status:
									{{history.status}}</div>
							</div>

						</div>
						<!-- 							<div class="taskNotes">Notatki</div> -->
						<!-- 							<div class="taskHAttachment">Załączniki</div> -->
					</div>
				</div>

			</div>
			<div id="taskMainWindowStatistics"></div>

		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<button type="button" class="btn btn-primary"
			data-ng-click="addProject()" data-dismiss="modal">Save
			changes</button>
	</div>
</div>
<!-- 	</div> -->
<!-- </div> -->