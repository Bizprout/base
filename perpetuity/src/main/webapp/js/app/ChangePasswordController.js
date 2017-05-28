baseApp.controller("ChangePasswordController", function($scope, $location, $http, $timeout, $q, $filter, $localStorage) {

	console.log("ChangePasswordController loaded...");

	$scope.cmpname=$localStorage.cmpname;
	
	if($localStorage.cmpid===undefined)
	{
		$location.path("/home");
	}
	
	if($localStorage.userid===undefined)
	{
		$location.path("/");
	}


	// **********switch flag for success message**********
	$scope.switchBool = function (value) {
		$scope[value] = !$scope[value];
	};


	$scope.currentpassword="";
	$scope.newpassword="";
	$scope.confirmpassword="";


	$scope.changepassword=function(){

		if($scope.currentpassword==='')
		{
			$scope.alerts = { type: 'danger' ,msg: 'Current Password is Required!'};
			$scope.showSuccessAlert = true;
		}
		else if($scope.newpassword==='')
		{
			$scope.alerts = { type: 'danger' ,msg: 'New Password is Required!'};
			$scope.showSuccessAlert = true;
		}
		else if($scope.confirmpassword==='')
		{
			$scope.alerts = { type: 'danger' ,msg: 'Confirm Password is Required!'};
			$scope.showSuccessAlert = true;
		}
		else if($scope.newpassword!=$scope.confirmpassword)
		{
			$scope.alerts = { type: 'danger' ,msg: 'New Password and Confirm Password Does not Match!'};
			$scope.showSuccessAlert = true;
		}
		else if($scope.newpassword.length<8)
		{
			$scope.alerts = { type: 'danger' ,msg: 'New Password Should be Min 8 Characters Long!'};
			$scope.showSuccessAlert = true;
		}
		else
		{
			$http({
				method : "POST",
				url : "user/getuserdatabyid",
				data : {"userid":$localStorage.userid},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config){

				if($scope.currentpassword!=data.password)
				{
					$scope.alerts = { type: 'danger' ,msg: 'Current Password does not Match!'};
					$scope.showSuccessAlert = true;
				}
				else
				{
					$http({
						method : "POST",
						url : "user/changepassword",
						data : {"password":$scope.newpassword, "userid":$localStorage.userid},
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data, status, headers, config){

						if(data[0]==="success")
						{
							$scope.alerts = { type: 'success' ,msg: 'Password Updated!'};
							$scope.showSuccessAlert = true;
							$scope.showerror=false;

							$scope.currentpassword="";
							$scope.newpassword="";
							$scope.confirmpassword="";
						}
						else if(data[0]==="failure")
						{
							$scope.alerts = { type: 'danger' ,msg: 'Password not Updated!'};
							$scope.showSuccessAlert = true;
						}
						else
						{
							$scope.alerts = { type: 'danger'};
							$scope.errdata=data[0];
							$scope.showerror=true;
							$scope.showSuccessAlert = false;
						}

					}).error(function(data, status, headers, config){

						$scope.alerts = { type: 'danger' ,msg: 'Password not Updated!'};
						$scope.showSuccessAlert = true;
					});
				}

			}).error(function(data, status, headers, config){

				$scope.alerts = { type: 'danger' ,msg: 'Current Password does not Match!'};
				$scope.showSuccessAlert = true;
			});
		}
	};

});