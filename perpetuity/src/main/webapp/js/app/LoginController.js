baseApp.controller("LoginController", function($scope, $location) {
	
	console.log("LoginController loaded..");
	
	if("/"===$location.path())
	{
		$("#menu").hide();
	}
	else
	{
		$("#menu").show();
	}
	

	$scope.login = function(name, password) {		
		console.log("inside login..");
		if ('om' === name && 'pass' === password) {
			//angular.element('#login-modal').hide();
			$(".modal-backdrop").hide();
			$location.path('home/');
			//$localStorage("navbar_show",true);
			//$scope.navbarShow=true;		
			
		} else {
			$location.path('/');
		}
	};

});