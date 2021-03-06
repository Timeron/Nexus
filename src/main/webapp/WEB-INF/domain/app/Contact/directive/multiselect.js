var app_directives = angular.module('DropdownMultiselectDir', []);

app_directives.directive('dropdownMultiselect', function(){
   return {
       restrict: 'E',
       scope:{           
            model: '=',
            options: '=',
            pre_selected: '=preSelected'
       },
       template: "<div class='btn-group' data-ng-class='{open: open}'>"+
        "<button class='btn btn-small'>Wybierz</button>"+
                "<button class='btn btn-small dropdown-toggle' data-ng-click='open=!open;openDropdown()'><span class='caret'></span></button>"+
                "<ul class='dropdown-menu multiselect' aria-labelledby='dropdownMenu'>" + 
                    "<li><a data-ng-click='selectAll()'><i class='glyphicon glyphicon-ok-sign'></i>  Zaznacz wszystkie</a></li>" +
                    "<li><a data-ng-click='deselectAll();'><i class='glyphicon glyphicon-remove-sign'></i>  Odznacz wszystkie</a></li>" +       
                    "<li><input type='text'	class='form-control' placeholder='Wyszukaj' data-ng-model='search' /></li>" +   
                    "<li class='divider'></li>" +
                    "<li data-ng-repeat='option in options | filter : search'> <a data-ng-click='setSelectedItem()'>{{option.name}}<span data-ng-class='isChecked(option.id)'></span></a></li>" +                                        
                "</ul>" +
            "</div>" ,
       controller: function($scope){
           
           $scope.openDropdown = function(){        
                    $scope.selected_items = [];
                    if(angular.isArray($scope.pre_selected)){
	                    for(var i=0; i<$scope.pre_selected.length; i++){
	                    	$scope.selected_items.push($scope.pre_selected[i].id);
	                    }
                    }
            };
           
            $scope.selectAll = function () {
                $scope.model = _.pluck($scope.options, 'id');
            };            
            $scope.deselectAll = function() {
                $scope.model=[];
            };
            $scope.setSelectedItem = function(){
                var id = this.option.id;
                if (_.contains($scope.model, id)) {
                    $scope.model = _.without($scope.model, id);
                } else {
                    $scope.model.push(id);
                }
                return false;
            };
            $scope.isChecked = function (id) {                 
                if (_.contains($scope.model, id)) {
                    return 'glyphicon glyphicon-ok-sign pull-right';
                }
                return false;
            };                                 
       }
   };
});