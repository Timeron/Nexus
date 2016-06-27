var app = angular.module("shopListService", ["ngResource"]);

app.factory("GetShopList", function($resource){
	return $resource("/nexus/v1/sl/getShopList", 
			{}, 
			{query: { method: "GET", isArray: true }}
		);
});

app.factory("UpdateProduct", function($resource){
	return $resource("/nexus/v1/sl/updateProduct", 
			{}, 
			{query: { method: "POST", isArray: false }}
		);
});

app.factory("SaveProduct", function($resource){
	return $resource("/nexus/v1/sl/saveProduct", 
			{}, 
			{query: { method: "POST", isArray: false }}
		);
});