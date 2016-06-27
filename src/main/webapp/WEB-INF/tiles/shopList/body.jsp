<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div ng-app="shopList">
	<div ng-controller="shopListController">
		<div id="shopListHeader">
			<div class="name">Lista zakupów</div><div class="add" ng-click="addNew()"><span class="glyphicon glyphicon-plus"></span></div>
		</div>
		<div id="addNew" ng-show="showAdd">
			<div class="row">
				<div class="field">nazwa: </div>
				<div class="input"><input class="form-control" type="text" ng-model="newProductName" /></div>
			</div>
			<div class="row">
				<div class="field">ilość: </div>
				<div class="input"><input class="form-control" type="text" ng-model="newProductNumber" /></div>
			</div>
			<div class="row">
				<div class="field">gabaryt: </div>
				<div class="input"><select class="form-control"
									ng-model="newProductSize"
									ng-options="size.id for size in sizes">
										<option value=""></option>
								</select></div>
			</div>
			<div class="row">
				<div class="field">piorytet: </div>
				<div class="input"><select class="form-control"
									ng-model="newProductPriority"
									ng-options="priority.id for priority in priorities">
										<option value=""></option>
								</select></div>
			</div>
			<button class="saveNew btn btn-default" ng-click="save()">Zapisz</button>
		</div>
		<div id="shopListBody">
			<div class="productRow" ng-repeat="product in products" ng-click="buy(product)" buy="{{product.buy}}">
				<div class="productCheck"></div>
				<div class="productName">{{product.name}}</div>
				<div class="productNumber">{{product.number}}</div>
				<div class="productNumber">{{product.size}}</div>
<!-- 				<div class="productNumber">{{product.priority}}</div> -->
			</div>
		</div>
		<div id="footer">Footer</div>
	</div>
</div>