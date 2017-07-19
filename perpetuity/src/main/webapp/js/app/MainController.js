baseApp.controller("MainController", function($scope, $location, $http, $rootScope, $mdDialog, $filter, $localStorage) {
			
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
	
	$scope.logrepo=function(){
		$location.path("/logreport");
	}
	
	$scope.logoutsess=function(){
		
		var confirm = $mdDialog.confirm()
		.title('Are You Sure You Want to Logout?')
		.ok('OK')
		.cancel('Cancel');

		$mdDialog.show(confirm).then(function() {
			
			//store the logout time for the user
			
			$scope.logoutdatetime=new Date();
			
			$http({
				method : "POST",
				url : "usercounter/updatelogoutdatetime",
				data:{"userid":$localStorage.userid, "logindatetime":$localStorage.logindatetime, "logoutdatetime":$scope.logoutdatetime},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config){
				
			}).error(function(data, status, headers, config){
				
			});
			
			//logout session
			$http({
				method : "POST",
				url : "session/logout",
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config){
				
			}).error(function(data, status, headers, config){
				
			});
			
			$localStorage.$reset();
			$location.path("/");
		});
	};

});