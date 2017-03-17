baseApp.controller("LoginController", function($scope, $location) {
	
	$scope.navbar_show=true;
	
	if("/"===$location.path())
	{
		$scope.navbar_show=false;
	}
	else
	{
		$scope.navbar_show=true;
	}

	$scope.login = function(name, password) {
		console.log("inside login..");
		if ('om' === name && 'pass' === password) {
			$location.path('home/');
		} else {
			$location.path('/');
		}
	};

});