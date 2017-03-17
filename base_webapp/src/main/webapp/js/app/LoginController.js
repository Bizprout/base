baseApp.controller("LoginController", function($scope, $location) {

	$scope.login = function(name, password) {
		console.log("inside login..");
		if ('om' === name && 'pass' === password) {
			$location.path('home');
		} else {
			$location.path('login');
		}
	};

});