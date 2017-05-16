baseApp.controller("LoginController", function($scope, $location, $http, $rootScope, $mdDialog, $filter, $localStorage) {

	console.log("LoginController loaded..");

	//$localStorage.$reset();

	// **********switch flag for success message**********
	$scope.switchBool = function (value) {
		$scope[value] = !$scope[value];
	};

	// Appending dialog to document.body to cover sidenav in docs app
	// Modal dialogs should fully cover application
	// to prevent interaction outside of dialog

	if ("/" === $location.path()) {
		$("#menu").hide();
	} else {
		$("#menu").show();
	}
	
	$scope.logout=function(){
		console.log("Logout");
	};

	$scope.loginDTO = {
			"username" : "",
			"password" : ""
	};	

	$scope.login = function(loginDTO) {
		console.log("inside login..");

		if($scope.loginDTO.username==="")
		{
			$scope.alerts = { type: 'danger' ,msg: 'Username cannot be Empty!'};
			$scope.showSuccessAlert = true;
		}
		else if($scope.loginDTO.password==="")
		{
			$scope.alerts = { type: 'danger' ,msg: 'Password cannot be Empty!'};
			$scope.showSuccessAlert = true;
		}
		else
		{
			$scope.isLoading=true;
			
			$http({
				method : "POST",
				url : "login/authe",
				data: loginDTO,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config){

				if (data.username === loginDTO.username && data.password === loginDTO.password) {
					// angular.element('#login-modal').hide();
					// $localStorage("navbar_show",true);
					// $scope.navbarShow=true;

					$localStorage.user=data.username;
					$localStorage.userid=data.userid;
					$localStorage.usertype=data.usertype;
					$localStorage.logindatetime=new Date();

					//insert user counter
					$http({
						method : "POST",
						url : "usercounter/insert",
						data: {"userid":data.userid, "logindatetime":$localStorage.logindatetime},
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(datausercounter, status, headers, config){

					}).error(function(data, status, headers, config){
						// called asynchronously if an error occurs
						// or server returns response with an error status.
					});

					$(".modal-backdrop").hide();
					$location.path('home/');
					
					$scope.isLoading=false;

				} else {

					$location.path('/');
					$scope.alerts = { type: 'danger' ,msg: 'Not a Valid User!'};
					$scope.showSuccessAlert = true;
				}

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		}
	};

});