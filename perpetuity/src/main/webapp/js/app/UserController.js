baseApp.controller("UserController", function($scope, $location, $http, $timeout, $q, $filter) {

	console.log("UserController loaded.....");

/*	//**********By default add screen will be enabled and edit will be disabled**********
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
	};	*/

	// **********switch flag for success message**********
	$scope.switchBool = function (value) {
		$scope[value] = !$scope[value];
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
			"usertype" : "",
			"userstatus" : ""
	};	

	//*******options for user types and default selected option*********
	$scope.usertype = [{name:"Select User Type", id:"null"}, {name:"PP Admin", id:0}, {name:"PP SuperAdmin", id:1}];
	$scope.userDTO.usertype = $scope.usertype[0].id;

	//*******options for user status and default selected option*********
	$scope.userstatus = [{name:"Active", id:0}, {name:"Inactive", id:1}];
	$scope.userDTO.userstatus = $scope.userstatus[0].id;	

	//to get Edit Username dropdown
	$http({
		method : "GET",
		url : "user/getusernames",
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data, status, headers, config){

		//*******options for user names and default selected option*********
		
		$scope.usernames=data;
		
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

	//Create user=======================================================================================================
	$scope.createuser=function(userDTO){

		console.log("inside Create User..");
		
		//call user add service
		if($scope.userDTO.username.length!=0 && $scope.userDTO.password.length!=0 && $scope.userDTO.confirmpassword.length!=0 && $scope.userDTO.usertype!="null" && $scope.userDTO.userstatus>=0)
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

	//Edit User data====================================================================================================

	//*******DTO to store the form values for edit when populated**********
	$scope.edituserDTO = {
			"username" : "",
			"editusername" : "",
			"usertype" : "",
			"userstatus" : ""
	};	
	
	$scope.eclear=function(){
		$scope.edituserDTO.username='';
		$scope.edituserDTO.editusername='';
		$scope.edituserDTO.usertype = $scope.usertype[0].id;
		$scope.edituserDTO.userstatus = $scope.userstatus[0].id;
	}

	$scope.populateuserdata=function(){

		$scope.edituserDTO.editusername=$scope.edituserDTO.username;

		$http({
			method : "POST",
			url : "user/getuserdata",
			data: {"username":$scope.edituserDTO.username},
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config){

			$scope.edituserDTO.usertype = data.usertype;
			$scope.edituserDTO.userstatus = data.userstatus; 

		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});		
		
		$scope.edituser=function(edituserDTO){

			console.log("inside Edit User..");
			
			//$scope.edituserDTO.usertype=$scope.usertype;
			
			console.log(edituserDTO);

			//call user add service

		if($scope.edituserDTO.username.length!=0 && $scope.edituserDTO.editusername.length!=0 && $scope.edituserDTO.usertype!="null" && $scope.edituserDTO.userstatus>=0)
			{
				$http({
					method : "POST",
					url : "user/edit",
					data : edituserDTO,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data, status, headers, config){

					if(data==="success")
					{
						$scope.alerts = { type: 'success', msgtype: 'Success!' ,msg: 'User Updated!'};
						$scope.showSuccessAlert = true;

						$scope.edituserDTO.username='';
						$scope.edituserDTO.editusername='';
						$scope.edituserDTO.usertype = $scope.usertype[0].id;
						$scope.edituserDTO.userstatus = $scope.userstatus[0].id;
					}
					
				}).error(function(data, status, headers, config){
					// called asynchronously if an error occurs
					// or server returns response with an error status.	

						$scope.alerts = { type: 'danger', msgtype: 'Failure!' ,msg: 'User not Updated!'};
						$scope.showSuccessAlert = true;
				});
		
			}
			else
			{
				$scope.alerts = { type: 'danger', msgtype: 'Error!' ,msg: 'All Fields should be Filled up.'};
				$scope.showSuccessAlert = true;
			}
		};
		
	};

	//View Users - Report================================================================================================

	$scope.users = []; //declare an empty array
	$http.get("user/getusersreport").success(function(response){ 
		$scope.users = response;  //ajax request to fetch data into $scope.data
	});

	$scope.sort = function(keyname){
		$scope.sortKey = keyname;   //set the sortKey to the param passed
		$scope.reverse = !$scope.reverse; //if true make it false and vice versa
	};


	$scope.viewreport=function(){

		$location.path('userreports');

		//to get Edit Username dropdown
		$http({
			method : "GET",
			url : "user/getusersreport",
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config){			

			//*******options for user names and default selected option*********

			$scope.viewrepo=data;

		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});

	};

	$scope.exportData = function () {
		var blob = new Blob([document.getElementById('exportable').innerHTML], {
			type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
		});
		saveAs(blob, "Report.xls");
	};

});