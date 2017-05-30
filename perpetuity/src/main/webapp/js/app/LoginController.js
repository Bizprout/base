baseApp.controller("LoginController", function($scope, $location, $route, $http, $rootScope, $mdDialog, $filter, $localStorage) {

	//$localStorage.$reset();
	
	 var formLogin = angular.element('#login-form');
     var formForgot = angular.element('#forgot-form');
     var formOTP = angular.element('#otp-form');
     var divForms = angular.element('#div-forms');
     var modalAnimateTime = 400;
     
     $scope.forgotclick=function(){
    	 modalAnimate(formLogin, formForgot);
     };
     
     $scope.forgotclickemail=function(){
    	 modalAnimate(formForgot, formOTP);
     };
     
     $scope.home=function(){
    	$location.path('/');
    	$route.reload();
    	window.location.reload(); 
     };
     
     function modalAnimate(oldForm, newForm) {
         var oldH = oldForm.height();
         var newH = newForm.height();
         divForms.css("height", oldH);
         oldForm.fadeToggle(modalAnimateTime, function () {
             divForms.animate({height: newH}, modalAnimateTime, function () {
                 newForm.fadeToggle(modalAnimateTime);
                 
             });
         });
     }
	
	if ("/" === $location.path()) {
		$("#menu").hide();
	} else {
		$("#menu").show();
	}

	// **********switch flag for success message**********
	$scope.switchBool = function (value) {
		$scope[value] = !$scope[value];
	};

	// Appending dialog to document.body to cover sidenav in docs app
	// Modal dialogs should fully cover application
	// to prevent interaction outside of dialog

	$scope.loginDTO = {
			"username" : "",
			"password" : ""
	};

	$scope.login = function(loginDTO) {
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
			var config = {
					params : {
						'username' : $scope.loginDTO.username,
						'password' : $scope.loginDTO.password,
					},
					ignoreAuthModule : 'ignoreAuthModule'
				};
			
			$http.post('authenticate', '', config).success(function(data, status, headers, config){
				
				 if (data.username) {
										
					$localStorage.user=data.username;
					$localStorage.userid=data.userid;
					$localStorage.usertype=data.usertype;
					$scope.usertype=$localStorage.usertype;
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
					$scope.error = false;
					$scope.isLoading=false;

				}

			}).error(function(data, status, headers, config){	
				
				if(data==="Auth Failed")
				{
					$location.path('/');
					$scope.alerts = { type: 'danger' ,msg: 'Not a Valid User!'};
					$scope.showSuccessAlert = true;
					$scope.isLoading=false;
				}

			});
		}
	};

	$scope.forgotpassword=function(forgotpasswordemailid){

		$scope.isLoadingforgotpassworduser=true;

		if(forgotpasswordemailid===undefined)
		{
			$scope.alerts = { type: 'danger' ,msg: 'Email ID is Not Valid!'};
			$scope.showSuccessAlert = true;
			$scope.isLoadingforgotpassworduser=false;
		}
		else
		{
			$http({
				method : "POST",
				url : "user/getuserdata",
				data: {"username":forgotpasswordemailid},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(datauser, status, headers, config){

				if(datauser!="")
				{
					if(datauser.userstatus!="Active")
					{
						$scope.alerts = { type: 'danger' ,msg: 'Email ID is Inactive!'};
						$scope.showSuccessAlert = true;
						$scope.isLoadingforgotpassworduser=false;
					}
					else
					{
						$http({
							method : "POST",
							url : "user/forgotpassword",
							data: {"username":datauser.username},
							headers : {
								'Content-Type' : 'application/json'
							}
						}).success(function(data, status, headers, config){

							if(data[0]==="success")
							{
								$scope.alerts = { type: 'success', msg: 'Email has been sent!'};
								$scope.showSuccessAlert = true;
								$scope.isLoadingforgotpassworduser=false;
							}
							else
							{
								$scope.alerts = { type: 'danger', msg: 'Email not sent!'};
								$scope.showSuccessAlert = true;
								$scope.isLoadingforgotpassworduser=false;
							}

						}).error(function(data, status, headers, config){
							// called asynchronously if an error occurs
							// or server returns response with an error status.
						});
					}
				}
				else
				{
					$scope.alerts = { type: 'danger' ,msg: 'Email ID is Not Registered!'};
					$scope.showSuccessAlert = true;
					$scope.isLoadingforgotpassworduser=false;
				}

			}).error(function(datauser, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		}
	};

});