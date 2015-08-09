var app = angular.module("UserCrtl", ['jTaskService']);

app.controller("TaskChangeUserController", function($scope, $rootScope, Users, AssignTaskToUser){
	$scope.users;
	
	Users.query({}, function(data){
		$scope.users = data;
	});
	
	$scope.assignTask = function(user){
		AssignTaskToUser.query({taskId: $rootScope.taskDetails.id, userId: user.id}, function(data){
			angular.forEach($rootScope.projects, function(project){
				if(project.id === $rootScope.taskDetails.projectId){
					angular.forEach(project.tasks, function(task){
						if(task.id === $rootScope.taskDetails.id){
							task.user = user;
						}
					});
				}
			});
			return data;
		});
	};
})

.directive("markUserLine", function(){
	return {
		restrict: "AE",
		link: function(scope, element, attrs){
			element.bind('mouseover', function(){
				element[0].style.backgroundColor = "#E0FFE0";
			});
			element.bind('mouseout', function(){
				element[0].style.backgroundColor = "#FFF";
			});
			element.bind('click', function(){
				scope.assignTask(scope.user);
			});
			element.attr('data-dismiss', 'modal');
			
		}
	};
});