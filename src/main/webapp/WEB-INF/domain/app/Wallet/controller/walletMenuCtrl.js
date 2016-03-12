var app = angular.module("WalletMenu", []);

app
		.controller(
				"WalletMenuCtrl",
				function($scope, $rootScope, AddAccount, GetAllRecordTypes,
						AddNewRecord, UpdateRecord, GetAllUserAccounts, AddType,
						GetTypesValidForParent, UpdateTypes) {
					$scope.incomeCurrentDescription = "Wydatek";
					$scope.newRecordCurrentDescription = "Operacja";
					$scope.newRecordCurrentButtonDescription = "Transfer";
					// new account
					$scope.newAccountName = "";
					$scope.newAccountDescription = "";
					// new record
					$scope.amount = 0;
//					$scope.operationDate = 0;
//					$scope.operationTime = 0;
					$scope.newRecord = {};
					$scope.newRecord.transfer = false;
//					$scope.type = ""; //not used?
//					$scope.recordType = "";
					$scope.types = GetAllRecordTypes.query();
					$scope.accounts = GetAllUserAccounts.query();
					
					// new type
					$scope.newTypeName = "";
					$scope.newDefaultValue = 0;
					$scope.newTypeColor = 0;
					$scope.newTypeIcon = "";
					$scope.newParentType;
					$scope.typesValidForParent = GetTypesValidForParent.query();
					// edit types
					$scope.copyOfTypes = [];
					$scope.glyphicons = [ {
						name : "glyphicon-asterisk"
					}, {
						name : "glyphicon-plus"
					}, {
						name : "glyphicon-euro"
					}, {
						name : "glyphicon-eur"
					}, {
						name : "glyphicon-minus"
					}, {
						name : "glyphicon-cloud"
					}, {
						name : "glyphicon-envelope"
					}, {
						name : "glyphicon-pencil"
					}, {
						name : "glyphicon-glass"
					}, {
						name : "glyphicon-music"
					}, {
						name : "glyphicon-search"
					}, {
						name : "glyphicon-heart"
					}, {
						name : "glyphicon-star"
					}, {
						name : "glyphicon-star-empty"
					}, {
						name : "glyphicon-user"
					}, {
						name : "glyphicon-film"
					}, {
						name : "glyphicon-th-large"
					}, {
						name : "glyphicon-th"
					}, {
						name : "glyphicon-th-list"
					}, {
						name : "glyphicon-ok"
					}, {
						name : "glyphicon-remove"
					}, {
						name : "glyphicon-zoom-in"
					}, {
						name : "glyphicon-zoom-out"
					}, {
						name : "glyphicon-off"
					}, {
						name : "glyphicon-signal"
					}, {
						name : "glyphicon-cog"
					}, {
						name : "glyphicon-trash"
					}, {
						name : "glyphicon-home"
					}, {
						name : "glyphicon-file"
					}, {
						name : "glyphicon-time"
					}, {
						name : "glyphicon-road"
					}, {
						name : "glyphicon-download-alt"
					}, {
						name : "glyphicon-download"
					}, {
						name : "glyphicon-upload"
					}, {
						name : "glyphicon-inbox"
					}, {
						name : "glyphicon-play-circle"
					}, {
						name : "glyphicon-repeat"
					}, {
						name : "glyphicon-refresh"
					}, {
						name : "glyphicon-list-alt"
					}, {
						name : "glyphicon-lock"
					}, {
						name : "glyphicon-flag"
					}, {
						name : "glyphicon-headphones"
					}, {
						name : "glyphicon-barcode"
					}, {
						name : "glyphicon-tag"
					}, {
						name : "glyphicon-tags"
					}, {
						name : "glyphicon-book"
					}, {
						name : "glyphicon-bookmark"
					}, {
						name : "glyphicon-print"
					}, {
						name : "glyphicon-camera"
					}, {
						name : "glyphicon-facetime-video"
					}, {
						name : "glyphicon-picture"
					}, {
						name : "glyphicon-map-marker"
					}, {
						name : "glyphicon-tint"
					}, {
						name : "glyphicon-edit"
					}, {
						name : "glyphicon-share"
					}, {
						name : "glyphicon-plus-sign"
					}, {
						name : "glyphicon-minus-sign"
					}, {
						name : "glyphicon-exclamation-sign"
					}, {
						name : "glyphicon-gift"
					}, {
						name : "glyphicon-leaf"
					}, {
						name : "glyphicon-fire"
					}, {
						name : "glyphicon-eye-open"
					}, {
						name : "glyphicon-eye-close"
					}, {
						name : "glyphicon-warning-sign"
					}, {
						name : "glyphicon-plane"
					}, {
						name : "glyphicon-calendar"
					}, {
						name : "glyphicon-shopping-cart"
					}, {
						name : "glyphicon-folder-open"
					}, {
						name : "glyphicon-hdd"
					}, {
						name : "glyphicon-bullhorn"
					}, {
						name : "glyphicon-bell"
					}, {
						name : "glyphicon-thumbs-up"
					}, {
						name : "glyphicon-thumbs-down"
					}, {
						name : "glyphicon-globe"
					}, {
						name : "glyphicon-wrench"
					}, {
						name : "glyphicon-tasks"
					}, {
						name : "glyphicon-filter"
					}, {
						name : "glyphicon-briefcase"
					}, {
						name : "glyphicon-dashboard"
					}, {
						name : "glyphicon-paperclip"
					}, {
						name : "glyphicon-heart-empty"
					}, {
						name : "glyphicon-phone"
					}, {
						name : "glyphicon-usd"
					}, {
						name : "glyphicon-gbp"
					}, {
						name : "glyphicon-flash"
					}, {
						name : "glyphicon-send"
					}, {
						name : "glyphicon-cutlery"
					}, {
						name : "glyphicon-transfer"
					}, {
						name : "glyphicon-earphone"
					}, {
						name : "glyphicon-phone-alt"
					}, {
						name : "glyphicon-tower"
					}, {
						name : "glyphicon-stats"
					}, {
						name : "glyphicon-tree-conifer"
					}, {
						name : "glyphicon-tree-deciduous"
					}, {
						name : "glyphicon-cd"
					}, {
						name : "glyphicon-equalizer"
					}, {
						name : "glyphicon-tent"
					}, {
						name : "glyphicon-blackboard"
					}, {
						name : "glyphicon-bed"
					}, {
						name : "glyphicon-apple"
					}, {
						name : "glyphicon-hourglass"
					}, {
						name : "glyphicon-lamp"
					}, {
						name : "glyphicon-piggy-bank"
					}, {
						name : "glyphicon-scale"
					}, {
						name : "glyphicon-ice-lolly"
					}, {
						name : "glyphicon-ice-lolly-tasted"
					}, {
						name : "glyphicon-education"
					}, {
						name : "glyphicon-oil"
					}, {
						name : "glyphicon-grain"
					}, {
						name : "glyphicon-sunglasses"
					} ];
					$scope.addIconToTypeHide = true;

					window.mobilecheck = function() {
						var check = false;
						(function(a) {
							if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino/i
									.test(a)
									|| /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i
											.test(a.substr(0, 4)))
								check = true
						})(navigator.userAgent || navigator.vendor
								|| window.opera);
						return false;
					};

					if (window.mobilecheck()) {
						window.location.href = "http://timeron.ddns.net:8080/nexus/wallet/mobile";
						// window.location.href =
						// "http://localhost:8080/nexus/wallet/mobile";
					}

					$scope.getTypes = function() {
						GetAllRecordTypes.query({}, function(data) {
							$scope.types = data;
						});

					};

					$scope.getTypesValidForParent = function() {
						GetTypesValidForParent.query({}, function(data) {
							$scope.typesValidForParent = data;
						});
					};

					$scope.getCopyOfTypes = function() {
						var i = 0;
						$scope.copyOfTypes = $scope.types;
						$scope.getTypesValidForParent();
						angular.forEach($scope.copyOfTypes, function(t) {
							t.typesValidForParent = $scope.typesValidForParent;
							t.addIconToTypeHide = true;
							t.index = i;
							i++;
						});
					};

					$scope.addAccount = function() {
						AddAccount.query({
							name : $scope.newAccountName,
							description : $scope.newAccountDescription
						}, function(data) {
							$scope.message = data;
						});
					};

					$scope.updateTypes = function(uTypes) {
						UpdateTypes.query({
							types : uTypes
						}, function(data) {
							$scope.types = data;
						});
					};

					$scope.addRecord = function() {
						if (!$scope.newRecord.transfer) {
							AddNewRecord.query({
								value : $scope.newRecord.amount,
								description : $scope.newRecord.description,
								income : $scope.newRecord.income,
								transfer : $scope.newRecord.transfer,
								date : $scope.operationDate
										+ $scope.operationTime,
								recordTypeId : $scope.newRecord.type.id,
								accountId : $rootScope.selectedAccount.id
							}, function(data) {
								$scope.message = data;
							});
						} else {
							AddNewRecord.query({
								value : $scope.newRecord.amount,
								description : $scope.newRecord.description,
								transfer : $scope.newRecord.transfer,
								date : $scope.operationDate
										+ $scope.operationTime,
								accountId : $rootScope.selectedAccount.id,
								destynationAccountId : $scope.newRecord.account.id
							}, function(data) {
								$scope.message = data;
							});
						}

					};
					
					$scope.editRecord = function() {
						if (!$scope.recordToEdit.transfer) {
							UpdateRecord.query({
								id : $scope.recordToEdit.id,
								value : $scope.recordToEdit.value,
								description : $scope.recordToEdit.description,
								income : $scope.recordToEdit.income,
								transfer : $scope.recordToEdit.transfer,
								date : $scope.operationDate
										+ $scope.operationTime,
								recordTypeId : $scope.recordToEdit.type.id,
								accountId : $rootScope.selectedAccount.id
							}, function(data) {
								$scope.message = data;
							});
						} else {
							UpdateRecord.query({
								id : $scope.recordToEdit.id,
								value : $scope.recordToEdit.value,
								description : $scope.recordToEdit.description,
								transfer : $scope.recordToEdit.transfer,
								date : $scope.operationDate
										+ $scope.operationTime,
								accountId : $rootScope.selectedAccount.id,
								destynationAccountId : $scope.recordToEdit.account.id
							}, function(data) {
								$scope.message = data;
							});
						}
					};

					$scope.changeIncome = function() {
						if ($scope.newRecord.income) {
							$scope.newRecord.income = false;
							$scope.incomeCurrentDescription = "Wydatek";
						} else {
							$scope.newRecord.income = true;
							$scope.incomeCurrentDescription = "Dochód";
						}
					};

					$scope.changeTransfer = function() {
						if ($scope.newRecord.transfer) {
							$scope.newRecord.transfer = false;
							$scope.newRecordCurrentDescription = "Operacja";
							$scope.newRecordCurrentButtonDescription = "Transfer";
						} else {
							$scope.newRecord.transfer = true;
							$scope.newRecordCurrentDescription = "Transfer";
							$scope.newRecordCurrentButtonDescription = "Operacja";
						}
					};

					$scope.addType = function() {
						AddType.query({
							name : $scope.newTypeName,
							defaultValue : $scope.newDefaultValue,
							color : $scope.newTypeColor,
							icon : $scope.newTypeIcon,
							parentId : $scope.newParentType.id
						}, function(data) {
							$scope.message = data;
						});
					};

					$scope.getValidTypeForParentById = function(typeId) {
						var i = 0;
						var index;
						angular.forEach($scope.typesValidForParent,
								function(t) {
									if (t.id === typeId) {
										index = i;
									}
									;
									i++;
								});
						return index;
					};

					$scope.addIconToType = function(index) {
						$scope.copyOfTypes[index].addIconToTypeHide = false;
					};

					$scope.closeAddIconToType = function(index) {
						$scope.copyOfTypes[index].addIconToTypeHide = true;
					};

					$scope.setIconToType = function(index, icon) {
						$scope.copyOfTypes[index].icon = icon;
					};
					
					$scope.getTypeColor = function(typeId) {
						var color = "#FFF";
						if(typeId !== ""){
							angular.forEach($scope.types, function(t) {
								if (t.id === typeId) {
									color = t.color;
								}
							});
						}
						return color;
					};
				});



app.controller("AddRecordCtrl", function() {

});