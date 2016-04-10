<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
${application.name}
<div ng-app="NexusAppConfig" ng-controller="NexusAppConfigCtrl">



	<div id="taskWindow" class="modal-body">
		<div id="projectConfigMainWindowContent">
			<div class="btn-group topMenu">
				<button type="button" class="btn btn-primary btn-xs"
					ng-click="getUsers(${application.id})">Ustaw
					dostęp</button>
			</div>
			<div id="projectUserAccess" class="space-top-xxl">
				<div class="space"></div>
				<div class="col-md-9" id="projectConfigAccess">
					<div class="col-md-4">
						<select multiple class="form-control">
							<option ng-repeat="user in usersTemp" ng-dblclick="addAccess(user)">{{user.firstName}}
								{{user.lastName}} - {{user.nick}}</option>
						</select>
					</div>
					<div class="col-md-4">
						<select multiple class="form-control">
							<option ng-repeat="owner in ownersTemp" ng-dblclick="removeAccess(owner)">{{owner.firstName}}
								{{owner.lastName}} - {{owner.nick}}</option>
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

</div>