<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div data-ng-controller="TaskController">
	<!-- Modal -->
	<div class="modal fade" id="modalNewNote" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Notatka</h4>
				</div>
				<div class="modal-body">
					<div id="newProject" class="modalTest">
						<div>
							Dodaj notatkę:
							<textarea cols="78" rows="8" data-ng-model="newNote"></textarea>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
					<button type="button" class="btn btn-primary"
						data-ng-click="addNote()" data-dismiss="modal">Zapisz</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="modalAssign" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-ng-controller="TaskChangeUserController">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Przypisz do osoby</h4>
				</div>
				<div class="modal-body">
					<div mark_user_line data-ng-repeat="user in users">
						<div class="border-bottom" data-ng-click="assignTask(user)">
							<span mp-image="{{user.nickLogo}}" /> {{user.firstName}}
							{{user.lastName}}
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Zamknij</button>
				</div>
			</div>
		</div>
	</div>


	<!-- Modal -->
	<div class="modal fade" id="modalAssignToTask" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
		data-ng-controller="TaskSearchCtrl">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Przypisz do Taska</h4>
				</div>
				<div class="modal-body">
					<div mark-line data-ng-repeat="task in tasks">
						<div class="border-bottom" data-ng-click="addMainTask(task.id)">{{task.name}}
							{{task.summary}}</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Zamknij</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="editTaskModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"
		data-ng-controller="EditTaskCtrl">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Nowy Task</h4>
				</div>
				<div class="modal-body">
					<table class="formContainer">
						<tbody>
							<tr>
								<td class="bold">Nazwa:</td>
								<td><input type="text" data-ng-model="newSummary"></td>
							</tr>
							<tr>
								<td class="bold">Type:</td>
								<td><select ng-init="newType = taskTypes[newTypeId-1]"
									ng-model="newType"
									ng-options="taskType.name for taskType in taskTypes">
										<option value=""></option>
								</select></td>
							</tr>
							<tr>
								<td>Priorytet:</td>
								<td><select
									ng-init="newPriority = priorities[newPriority-1]"
									ng-model="newPriority"
									ng-options="priority.id for priority in priorities">
										<option value=""></option>
								</select></td>
							</tr>
							<tr class="separator">
								<td colspan="2"><hr></td>
							</tr>
							<tr>
								<td>Opis:</td>
								<td><textarea cols="40" rows="5"
										data-ng-model="newDescription"></textarea></td>
							</tr>
							<tr class="separator">
								<td colspan="2"><hr></td>
							</tr>
							<tr>
								<td>Terminy</td>
								<td><input type="checkbox" data-ng-model="timers"></td>
							</tr>
							<tr>
								<td>Ostateczny termin:</td>
								<td>
									<div class="font-m space-top-m">Dzień:</div> <datepicker
										init="{{newDate}}" offset="0" range="20" model="date"
										disable="{{timers}}"></datepicker> <!--YYYY-MM-DD hh:mm:ss.s -->
									<div class="font-m space-top-m">Godzina:</div> <timepicker
										init="{{newDate}}" model="time"></timepicker>
								</td>
							</tr>
							<tr>
								<td style="height: 100px">Przewidywany czas pracy:</td>
								<td><postponedpicker init={{workExpected}}
										model="workExpected"></postponedpicker> <!--YYYY-MM-DD hh:mm:ss.s --></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
					<button type="button" class="btn btn-primary"
						data-ng-click="updateTask()" data-dismiss="modal">Zapisz</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal-header">
		<h4 class="modal-title" id="myModalLabel">Task:
			{{taskDetails.name}}</h4>
		<h6 class="paragraph-m" id="myModalLabel">Główny
			<span data-ng-click="setTaskInNewWindow(taskDetails.mainTaskId)">{{taskDetails.mainTaskName}}</span></h6>
	</div>
	<div id="taskWindow" class="modal-body">
		<div id=taskMainWindow>
			<div class="taskDetailsName"
				data-ng-click="setTaskInNewWindow(taskDetails)">{{taskDetails.name}}</div>
			<div class="taskDetailsTaskTypeId">{{taskDetails.taskType}}</div>
			<div class="taskDetailsPriority">Piorytet:
				{{taskDetails.priority}}</div>
			<div class="taskDetailsPriority">Status:
				{{taskDetails.statusDescription}}</div>

			<div id="taskMainWindowContent">
				<div class="btn-group topMenu">
					<button type="button" class="btn btn-primary btn-xs"
						data-ng-click="taskCloseFromTaskWindow(task)"
						data-ng-disabled="buttonClose()">Zamknij</button>
					<button type="button" class="btn btn-primary btn-xs"
						data-ng-click="taskOpenFromTaskWindow(task)"
						data-ng-disabled="buttonOpen()">Otwórz</button>
				</div>
				<div class="btn-group topMenu">
					<button type="button" class="btn btn-primary btn-xs"
						data-toggle="modal" data-target="#editTaskModal">Edytuj</button>
					<button type="button" class="btn btn-primary btn-xs"
						data-toggle="modal" data-target="#modalNewNote">Notatka</button>
					<button type="button" class="btn btn-primary btn-xs"
						data-toggle="modal" data-target="#modalAssign">Przypisz
						do osoby</button>
					<button type="button" class="btn btn-primary btn-xs"
						data-toggle="modal" data-target="#modalAssignToTask"
						data-ng-click="allTasksForProject()">Przypisz do Taska</button>
				</div>


				<div class="taskDetailsSummary">
					Opis:
					<p>{{taskDetails.summary}}</p>
				</div>
				<div class="taskDetailsDescription">
					Szczegóły:
					<p>{{taskDetails.description}}</p>
				</div>
				<div class="taskDetailsDates">
					<div class="taskDetailsCreated">Dodany:
						{{taskDetails.created}}</div>
					<div class="taskDetailsUpdated">Zmieniony:
						{{taskDetails.updated}}</div>
				</div>

				<div id="taskWindowDetails">
					<div class="btn-group">
						<button type="button" class="btn btn-primary btn-xs"
							data-ng-click="getHistory(task)">Aktywność</button>
						<button type="button" class="btn btn-primary btn-xs"
							data-ng-click="getNotes(task)">Notatki</button>
						<!-- 								<button type="button" class="btn btn-primary btn-xs">Załączniki</button> -->
					</div>
					<div class="taskWindowDetailsList" data-ng-hide="hideHistory">
						<div class="detailsName">Aktywność</div>
						<div class="taskWindowDetailsEvent"
							data-ng-repeat="history in histories">
							<div data-ng-if="isStatusChange(history)">
								<div class="border-bottom">
									<div class="taskWindowDetailsEventName">Zmiana statusu</div>
									<div class="eventDate">{{polishDate(history.created)}}</div>
								</div>
								<div class="historyEventContent">status:
									{{history.status}}</div>
							</div>
							<div data-ng-if="isNote(history)">
								<div class="border-bottom">
									<div class="taskWindowDetailsEventName">Nowa notatka</div>
									<div class="eventDate">{{polishDate(history.created)}}</div>
								</div>
								<div class="historyEventContent">notatka: {{history.note}}</div>
							</div>
							<div data-ng-if="isUpdate(history)">
								<div class="border-bottom">
									<div class="taskWindowDetailsEventName">Edycja</div>
									<div class="eventDate">{{polishDate(history.created)}}</div>
								</div>
								<div class="historyEventContent">{{history.message}}</div>
							</div>
						</div>

						<!-- 							<div class="taskHAttachment">Załączniki</div> -->
					</div>
					<div class="taskWindowDetailsList" data-ng-hide="hideNotes">
						<div class="detailsName">Notatki</div>
						<div class="taskWindowDetailsElement"
							data-ng-repeat="note in notes">
							<div class="border-bottom">
								<div class="taskWindowDetailsEventName">Notatka</div>
								<div class="eventDate">{{polishDate(note.created)}}</div>
							</div>
							<div class="historyEventContent">notatka: {{note.content}}
							</div>
						</div>
					</div>
				</div>

			</div>
			<!-- 			<div id="taskMainWindowStatistics">???</div> -->

		</div>
		<div id="taskRightBar">
			<div class="content">
				<div class="">
					<p class='border-bottom'>Przypisany do:</p>
					<p class="bold paragraph-m">
						{{taskDetails.user.firstName}} {{taskDetails.user.lastName}} <span
							mp-image='{{taskDetails.user.nickLogo}}'
							title="{{taskDetails.user.firstName}} {{taskDetails.user.lastName}}"></span>
					</p>
				</div>
				<div class="border-bottom">Terminy:</div>
				<div class="paragraph-m font-m">Zakończenie:
					{{polishDate(taskDetails.endDate)}}</div>
				<div class="paragraph-m font-m">Przewidywany czes:
					{{msToDaysandHours(taskDetails.workExpected)}}</div>
			</div>
		</div>
	</div>

	<!-- 	<div class="modal-footer"> -->
	<!-- 		<button type="button" class="btn btn-default" data-dismiss="modal">Zamknij</button> -->
	<!-- 		<button type="button" class="btn btn-primary" -->
	<!-- 			data-ng-click="addProject()" data-dismiss="modal">Zapisz</button> -->
	<!-- 	</div> -->
</div>
<!-- 	</div> -->
<!-- </div> -->