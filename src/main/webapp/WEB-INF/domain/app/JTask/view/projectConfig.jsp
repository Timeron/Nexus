<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<div class="modal-header">
	<h3 class="modal-title" id="myModalLabel">
		Projekt: <span class="bold">{{project.name}}</span>
	</h3>
	<h5 class="paragraph-m" id="myModalLabel">
		Prefix: <span class="bold">{{project.prefix}}</span>
	</h5>
	<h5 class="paragraph-m" id="myModalLabel">
		Opis: <span class="bold">{{project.description}}</span>
	</h5>
</div>

<div id="taskWindow" class="modal-body">
	<div id="projectConfigMainWindowContent">
		<div class="btn-group topMenu">
			<button type="button" class="btn btn-primary btn-xs"
				data-ng-click="buttonProjectClose()" data-ng-disabled="">Zamknij</button>
			<button type="button" class="btn btn-primary btn-xs"
				data-ng-click="buttonProjectOpen()" data-ng-disabled="">Otwórz</button>
		</div>
		<div class="btn-group topMenu">
			<button type="button" class="btn btn-primary btn-xs"
				data-toggle="modal" data-target="#editTaskModal">Ustaw
				dostęp</button>
		</div>
		<div id="projectUserAccess" class="space-top-xxl">
			<div class="space"></div>
			<div class="col-md-9" id="projectConfigAccess">
				<div class="col-md-4">
					<select multiple class="form-control">
						<option ng-dblclick="addAccess(user)" ng-repeat="user in usersTemp">{{user.firstName}} {{user.lastName}} - {{user.nick}}</option>
					</select>
				</div>
				<div class="col-md-4">
					<select multiple class="form-control">
						<option ng-dblclick="removeAccess(owner)" ng-repeat="owner in ownersTemp">{{owner.firstName}} {{owner.lastName}} - {{owner.nick}}</option>
					</select>
				</div>
				<div class="btn-group topMenu">
					<button type="button" class="btn btn-primary btn-xs"
						data-ng-click="saveAccess()" data-ng-disabled="">Zapisz</button>
					<button type="button" class="btn btn-primary btn-xs"
						data-ng-click="cancleAccess()" data-ng-disabled="">Anuluj</button>
				</div>
			</div>


		</div>
	</div>
</div>