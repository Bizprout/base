baseApp.controller("MainController", function($scope, $location) {

	$scope.onClick = function(value) {

		console.log("value is " + value);
		$location.path(value);
		console.log(window.location);
	}

});