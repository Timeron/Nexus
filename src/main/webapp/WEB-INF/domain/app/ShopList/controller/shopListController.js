var app = angular
		.module("shopList", [ "shopListService", "ShopListDirective" ]);

app.controller("shopListController", function($scope, GetShopList,
		UpdateProduct, SaveProduct) {

	$scope.products = [];
	$scope.newProductName = "";
	$scope.newProductNumber = "";
	$scope.newProductSize = "";
	$scope.newProductPriority = "";
	$scope.priorities = [ {
		id : 1
	}, {
		id : 2
	}, {
		id : 3
	}, {
		id : 4
	}, {
		id : 5
	}, {
		id : 6
	}, {
		id : 7
	}, {
		id : 8
	}, {
		id : 9
	}, {
		id : 10
	} ];
	$scope.sizes = [{
		id : "gramów"
	}, {
		id : "deko"
	}, {
		id : "kilogramów"
	}, {
		id : "mililitrów"
	}, {
		id : "litrów"
	}, {
		id : "opakowanie"
	}, {
		id : "karton"
	}, {
		id : "1/4"
	}, {
		id : "1/3"
	}, {
		id : "1/2"
	}, {
		id : "sztuka"
	} ];
	$scope.showAdd = false;

	GetShopList.query({}, function(data) {
		console.log(data[0]);
		$scope.products = data;
	});

	$scope.buy = function(product) {
		if (product.buy) {
			product.buy = false;
		} else {
			product.buy = true;
		}

		UpdateProduct.query({
			id : product.id,
			name : product.name,
			size : product.size,
			number : product.number,
			priority : product.id,
			buy : product.buy
		}, function(data) {
			product = data.object;
		});
	};

	$scope.save = function() {
		SaveProduct.query({
			name : $scope.newProductName,
			size : $scope.newProductSize.id,
			number : $scope.newProductNumber,
			priority : $scope.newProductPriority.id,
			buy : true
		}, function(data) {
			product = data.object;
		});
	};
	
	$scope.addNew = function(){
		if ($scope.showAdd) {
			$scope.showAdd = false;
		} else {
			$scope.showAdd = true;
		}
	};

});