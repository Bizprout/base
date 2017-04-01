baseApp.controller("UserController", function($scope, $location, $http, $timeout, $q) {

	console.log("UserController loaded.....");

/*	$('#example').DataTable( {
		dom: 'Bfrtip',
		buttons: [
		          'copy', 'csv', 'excel', 'pdf', 'print'
		          ]
	});*/

	//**********By default add screen will be enabled and edit will be disabled**********
	$scope.showadd = true;
	$scope.showedit = false;

	//**********Switch b/w add and edit**********
	$scope.routedirect = function(value) {

		console.log("inside routedirect..");

		if('Add'===value)
		{
			$scope.showedit = false;
			$scope.showadd = true;
		}
		else
		{
			$scope.showedit = true;
			$scope.showadd = false;
		}
	};	

	// **********switch flag for success message**********
	$scope.switchBool = function (value) {
		$scope[value] = !$scope[value];
	};

	//******Autocomplete dropdown default options for User Type********
	$scope.userselectOptions = {
			displayText: 'Select User Type',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};

	//******Autocomplete dropdown default options for Select Username********
	$scope.usernameselectOptions = {
			displayText: 'Select Username',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};


	//*******DTO to store the form values for add when populated**********
	$scope.userDTO = {
			"username" : "",
			"password" : "",
			"confirmpassword" : "",
			"usertype" : 0,
			"userstatus" : 0
	};	

	//*******DTO to store the form values for edit when populated**********
	$scope.EdituserDTO = {
			"userid" : "",
			"username" : "",
			"usertype" : 0,
			"userstatus" : 0
	};	

	//*******options for user types and default selected option*********
	$scope.usertype = [{name:"PP Admin", id:0}, {name:"PP SuperAdmin", id:1}];
	$scope.userDTO.usertype = $scope.usertype[0].id;

	//*******options for user status and default selected option*********
	$scope.userstatus = [{name:"Active", id:0}, {name:"Inactive", id:1}];
	$scope.userDTO.userstatus = $scope.userstatus[0].id;

	//$scope.usernames =[{userid:1, username:"banesh"}, {userid:3, username:"rishwanth1"}];

	//to get Edit Username dropdown
	$scope.userDTO={};
	$http({
		method : "GET",
		url : "user/getusers",
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data, status, headers, config){

		console.log(data);
		
		//*******options for user names and default selected option*********

		$scope.userDTO=data;
	}).error(function(data, status, headers, config){
		// called asynchronously if an error occurs
        // or server returns response with an error status.
	});


	//*********Clear all the fields when clear button pressed*******
	$scope.clear=function(){

		$scope.userDTO.username='';
		$scope.userDTO.password='';
		$scope.userDTO.confirmpassword='';
		$scope.userDTO.usertype = $scope.usertype[0].id;
		$scope.userDTO.userstatus = $scope.userstatus[0].id;
	}

	//************when submit button is pressed validate and post to the service************
	$scope.createuser=function(userDTO){

		console.log("inside Create User..");

		//$scope.userDTO.usertype = $scope.userDTO.usertype.id;

		console.log(userDTO);

		//call user add service
		if($scope.userDTO.username.length!=0 && $scope.userDTO.password.length!=0 && $scope.userDTO.confirmpassword.length!=0 && $scope.userDTO.usertype>=0 && $scope.userDTO.userstatus>=0)
		{		
			if($scope.userDTO.password===$scope.userDTO.confirmpassword)
			{
				$http({
					method : "POST",
					url : "user/add",
					data : userDTO,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).then(
						function mySucces(response) {

							//alert(response.data);

							if(response.data==="success")
							{
								$scope.alerts = { type: 'success', msgtype: 'Success!' ,msg: 'User Created'};
								$scope.showSuccessAlert = true;

								$scope.userDTO.username='';
								$scope.userDTO.password='';
								$scope.userDTO.confirmpassword='';
								$scope.userDTO.usertype = $scope.usertype[0].id;
								$scope.userDTO.userstatus = $scope.userstatus[0].id;
							}
						},
						function myError(response) {

							if (response.statusText === "failure") {		

								$scope.alerts = { type: 'failure', msgtype: 'Failure!' ,msg: 'User not Created'};
								$scope.showSuccessAlert = true;
							}

						});		
			}
			else
			{
				$scope.alerts = { type: 'danger', msgtype: 'Error!' ,msg: 'Password and Confirm Password does not match'};
				$scope.showSuccessAlert = true;
			}			
		}	 	
		else
		{
			$scope.alerts = { type: 'danger', msgtype: 'Error!' ,msg: 'All Fields should be Filled up.'};
			$scope.showSuccessAlert = true;
		}
	}



	/*
	 $scope.edituser=function(usernamesmodel,editusername,usertypemodel,userstatusmodel){

		console.log("inside Edit User..");

		if(usertypemodel==="PP Admin"){usertypemodel="0";}
		else{usertypemodel="1";}

		if(userstatusmodel==="Active"){userstatusmodel="0";}
		else{userstatusmodel="1";}

		var parameter = JSON.stringify({
			"username" 		: username,
			"usertype" 		: usertypemodel,
			"userstatus" 	: userstatusmodel
		});

		//alert(parameter);

		//call user add service

		$http({
			method : "POST",
			url : "user/edit",
			data : parameter,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).then(
				function mySucces(response) {

					//alert(response.data);

					if(response.data==="success")
					{
						$scope.result="Data Updated!";
					}

				},
				function myError(response) {

					if (response.statusText === "failure") {		

						//alert(response.statusText);

						$scope.result="Data not Updated!";

					}

				});		
	}*/

});