<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	<!-- Modal -->
	<div class="modal fade" id="modalNewNote" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true" data-ng-controller="TaskController">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Notatka</h4>
				</div>
				<div class="modal-body">
					<div id="newProject" class="modalTest" >
						<div>
							Dodaj notatkę:
							<textarea cols="78" rows="8" data-ng-model="newNote"></textarea>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" data-ng-click="addNote()" data-dismiss="modal">Save changes</button>
				</div>
			</div>
		</div>
	</div>

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
			<div class="taskDetailsPriority">Status: {{task.statusDescription}}</div>
			<div id="taskMainWindowContent">
				<div class="btn-group topMenu">
					<button type="button" class="btn btn-primary btn-xs" data-ng-click="taskCloseFromTaskWindow(task)" data-ng-disabled="buttonClose()">Zamknij</button>
					<button type="button" class="btn btn-primary btn-xs" data-ng-click="taskOpenFromTaskWindow(task)" data-ng-disabled="buttonOpen()">Otwórz</button>
				</div>
				<div class="btn-group topMenu">
					<button type="button" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#modalNewNote">Notatka</button>
				</div>
				

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
						<button type="button" class="btn btn-primary btn-xs" data-ng-click="getNotes(task)">Notatki</button>
						<!-- 								<button type="button" class="btn btn-primary btn-xs">Załączniki</button> -->
					</div>
					<div class="taskWindowDetailsList" data-ng-hide="hideHistory">
						<div class="detailsName">Aktywność</div>
						<div class="taskWindowDetailsEvent" data-ng-repeat="history in histories">
							<div data-ng-if="isStatusChange(history)">
								<div class="border-bottom">
									<div class="taskWindowDetailsEventName">Zmiana statusu</div>
									<div class="eventDate">{{history.created}}</div>
								</div>
								<div class="historyEventContent">status:
									{{history.status}}</div>
							</div>
							<div data-ng-if="isNote(history)">
								<div class="border-bottom">
									<div class="taskWindowDetailsEventName">Nowa notatka</div>
									<div class="eventDate">{{history.created}}</div>
								</div>
								<div class="historyEventContent">notatka: {{history.note}}
								</div>
							</div>
						</div>
						
						<!-- 							<div class="taskHAttachment">Załączniki</div> -->
					</div>
					<div class="taskWindowDetailsList" data-ng-hide="hideNotes">
						<div class="detailsName">Notatki</div>
						<div class="taskWindowDetailsElement" data-ng-repeat="note in notes">
							<div class="border-bottom">
								<div class="taskWindowDetailsEventName">Notatka</div>
								<div class="eventDate">{{note.created}}</div>
							</div>
							<div class="historyEventContent">notatka: {{note.content}}
							</div>
						</div>
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