baseApp.controller("MainController", function($scope, $location, $http, $rootScope, $mdDialog, $filter, $localStorage) {
	
	console.log("Main Controller Loaded");
		
	if ("/" === $location.path()) {
		$("#menu").hide();
	} else {
		$("#menu").show();
	}
	
	$scope.mainmenu=function(){
		$location.path("/home");
	};
	
	$scope.changpass=function(){
		$location.path("/changepassword");
	};
	
	$scope.logoutsess=function(){
		
		$http({
			method : "POST",
			url : "/logout",
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config){
			
		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
	};
	
	$scope.onClick = function(value) {

		console.log("value is " + value);
		$location.path(value);
		console.log(window.location);
	}

});