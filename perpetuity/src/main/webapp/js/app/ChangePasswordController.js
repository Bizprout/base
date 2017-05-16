baseApp.controller("ChangePasswordController", function($scope, $location, $http, $timeout, $q, $filter, $localStorage) {
	
	console.log("ChangePasswordController loaded...");
	//TODO angular constants
	
	$scope.cmpname=$localStorage.cmpname;

	
	// **********switch flag for success message**********
	$scope.switchBool = function (value) {
		$scope[value] = !$scope[value];
	};
	
	$scope.changePassDTO={
	};
});