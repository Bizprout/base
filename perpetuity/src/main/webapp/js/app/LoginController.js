baseApp.controller("LoginController", function($scope, $location) {

	console.log("LoginController loaded..");

	if ("/" === $location.path()) {
		$("#menu").hide();
	} else {
		$("#menu").show();
	}

	$scope.loginDTO = {
		"username" : "",
		"password" : ""
	};

	$scope.login = function(loginDTO) {
		console.log("inside login..");
		if ('om' === loginDTO.username && 'pass' === loginDTO.password) {
			// angular.element('#login-modal').hide();
			$(".modal-backdrop").hide();
			$location.path('home/');
			// $localStorage("navbar_show",true);
			// $scope.navbarShow=true;

		} else {
			$location.path('/');
		}
	};

});