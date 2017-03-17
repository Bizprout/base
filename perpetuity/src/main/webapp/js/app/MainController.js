baseApp.controller("MainController", function($scope, $location) {
	
	$scope.navbar_show=false;
	
	$scope.onClick = function(value) {

		console.log("value is " + value);
		$location.path(value);
		console.log(window.location);
	}

});