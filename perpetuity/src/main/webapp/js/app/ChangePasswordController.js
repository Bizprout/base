baseApp.controller("ChangePasswordController", function($scope, $location, $http, $timeout, $q, $filter) {
	
	console.log("ChangePasswordController loaded...");
	//TODO angular constants
	
	// **********switch flag for success message**********
	$scope.switchBool = function (value) {
		$scope[value] = !$scope[value];
	};
	
	$scope.changePassDTO={
	};
});