baseApp.controller("UserController", function($scope, $location, $http, $timeout, $q, $filter, $localStorage, $mdDialog) {

	$scope.cmpname=$localStorage.cmpname;

	// **********switch flag for success message**********
	$scope.switchBool = function (value) {
		$scope[value] = !$scope[value];
	};

	if($localStorage.usertype!="PPsuperadmin" && $localStorage.cmpid===undefined)
	{
		$location.path("/home");
	}

	if($localStorage.userid===undefined)
	{
		$location.path("/");
	}

	//*******DTO to store the form values for add when populated**********
	$scope.userDTO = {
			"username" : "",
			"emailid" : "",
			"mobile" : "",
			"usertype" : "PPAdmin",
			"userstatus" : "Active"
	};	

	//*******DTO to store the form values for edit when populated**********
	$scope.edituserDTO = {
			"username" : "",
			"editusername" : "",
			"emailid" : "",
			"mobile" : "",
			"usertype" : "PPAdmin",
			"userstatus" : ""
	};	

	// **********************When Add tab is clicked*****************************************************************

	$scope.onaddclick=function(){

		$scope.userDTO.username='';
		$scope.userDTO.mobile='';

		//******Autocomplete dropdown default options for Select Username********
		$scope.usernameselectOptions = {
				displayText: 'Select Username',
				emptyListText: 'Oops! The list is empty',
				emptySearchResultText: 'Sorry, couldn\'t find "$0"'
		};

		//*******options for user types and default selected option*********
		/*$scope.usertype = [{name:"Select User Type", id:"null"}, {name:"PP Admin", id:0}, {name:"PP SuperAdmin", id:1}];
		$scope.userDTO.usertype = $scope.usertype[0].id;*/

		//*******options for user status and default selected option*********
		$scope.userstatus = ["Active", "Inactive"];
		/*$scope.userDTO.userstatus = $scope.userstatus[0];*/	

		/*	//*******options for Access rights to all companies or screens*********
		$scope.access = [{name:"Yes", id:0}, {name:"No", id:1}];
		$scope.userDTO.access = $scope.access[0].id;*/

		//*********Clear all the fields when clear button pressed*******
		$scope.clear=function(){

			$scope.userDTO.username='';
			//$scope.userDTO.usertype = $scope.usertype[0].id;
			//$scope.userDTO.emailid='';
			$scope.userDTO.mobile='';
		}

		//************when submit button is pressed validate and post to the service************

		//Create user=======================================================================================================
		$scope.createuser=function(userDTO){

			if($scope.userDTO.username!="")
			{
				//call user add service
				if($scope.userDTO.username==="") //&& $scope.userDTO.usertype!="null"
				{
					$scope.alerts = { type: 'danger' ,msg: 'Username is Required!'};
					$scope.showSuccessAlert = true;
				}
				else
				{
					$scope.userDTO.emailid=$scope.userDTO.username;

					var confirm = $mdDialog.confirm()
					.title('Confirm Your Email ID')
					.textContent($scope.userDTO.emailid)
					.ok('OK')
					.cancel('Cancel');

					$mdDialog.show(confirm).then(function() {
						$http({
							method : "POST",
							url : "user/add",
							data : userDTO,
							headers : {
								'Content-Type' : 'application/json'
							}
						}).success(function(data, status, headers, config){

							if(data[0]==="success")
							{
								$scope.alerts = { type: 'success', msg: 'User Created and an Email has been sent with the Username and an Auto Generated Password!'};
								$scope.showSuccessAlert = true;
								$scope.showerror=false;

								$scope.userDTO.username='';
								//$scope.userDTO.usertype = $scope.usertype[0].id;
								//$scope.userDTO.emailid='';
								$scope.userDTO.mobile='';
							}
							else if (data[0] === "failure") {		

								$scope.alerts = { type: 'danger', msg: 'User not Created!'};
								$scope.showSuccessAlert = true;
								$scope.showerror=false;
							}
							else
							{
								if(data.length>0)
								{
									$scope.alerts = { type: 'danger'};
									$scope.errdata=data;
									$scope.showerror=true;
									$scope.showSuccessAlert = false;
								}
								else
								{
									$scope.alerts = { type: 'danger', msg: 'User not Created'};
									$scope.showSuccessAlert = true;
									$scope.showerror=false;
								}
							}
						}).error(function(data, status, headers, config){

							$scope.alerts = { type: 'failure', msg: 'User not Created'};
							$scope.showSuccessAlert = true;

						});	
					});
				}		
			}	 	
			else
			{
				$scope.alerts = { type: 'danger', msg: 'All Mandatory Fields should be Filled up.'};
				$scope.showSuccessAlert = true;
			}
		};
	};

	//*****************when edit tab is clicked*****************************************

	$scope.oneditclick=function(){

		$scope.edituserDTO.username='';
		$scope.edituserDTO.editusername='';
		//$scope.edituserDTO.usertype = $scope.usertype[0].id;
		$scope.edituserDTO.userstatus = '';
		//$scope.edituserDTO.emailid='';
		$scope.edituserDTO.mobile='';

		//******Autocomplete dropdown default options for Select user status********
		$scope.userstatusselectOptions = {
				displayText: 'Select User Status',
				emptyListText: 'Oops! The list is empty',
				emptySearchResultText: 'Sorry, couldn\'t find "$0"'
		};

		//to get Edit Username dropdown
		$scope.isLoadingusername = true;
		$http({
			method : "GET",
			url : "user/getusernames",
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config){

			//*******options for user names and default selected option*********

			$scope.usernames=data;

			$scope.isLoadingusername = false;

		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});

		$scope.edituser=function(edituserDTO){

			if($scope.edituserDTO.username==="" && $scope.edituserDTO.editusername==="" && $scope.edituserDTO.userstatus==="")
			{
				$scope.alerts = { type: 'danger', msg: 'All Mandatory Fields should be Filled up.'};
				$scope.showSuccessAlert = true;
			}
		};

		//Edit User data====================================================================================================

		$scope.populateuserdata=function(){

			$scope.edituserDTO.editusername=$scope.edituserDTO.username;

			$scope.isLoadingmobile = true;
			$scope.isLoadinguserstatus = true;

			$http({
				method : "POST",
				url : "user/getuserdata",
				data: {"username":$scope.edituserDTO.username},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config){

				//$scope.edituserDTO.usertype = data.usertype;
				$scope.edituserDTO.emailid=data.emailid;
				$scope.edituserDTO.mobile=data.mobile;
				$scope.edituserDTO.userstatus = data.userstatus; 

				$scope.isLoadingmobile = false;
				$scope.isLoadinguserstatus = false;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});		

			$scope.edituser=function(edituserDTO){

				//$scope.edituserDTO.usertype=$scope.usertype;

				//call user add service
				if($scope.edituserDTO.username!=undefined || $scope.edituserDTO.editusername!=undefined || $scope.edituserDTO.userstatus!=undefined)
				{
					if($scope.edituserDTO.username===undefined)//&& $scope.edituserDTO.usertype!="null"
					{
						$scope.alerts = { type: 'danger', msg: 'Username is Mandatory!'};
						$scope.showSuccessAlert = true;
					}
					else if($scope.edituserDTO.editusername===undefined)
					{
						$scope.alerts = { type: 'danger', msg: 'Edit Username Should be a Valid Email ID!'};
						$scope.showSuccessAlert = true;
					}
					else if($scope.edituserDTO.userstatus===undefined)
					{
						$scope.alerts = { type: 'danger', msg: 'User Status is Mandatory!'};
						$scope.showSuccessAlert = true;
					}
					else
					{
						$scope.edituserDTO.emailid=$scope.edituserDTO.editusername;

						$http({
							method : "POST",
							url : "user/edit",
							data : edituserDTO,
							headers : {
								'Content-Type' : 'application/json'
							}
						}).success(function(data, status, headers, config){

							if(data[0]==="success")
							{
								$scope.alerts = { type: 'success', msg: 'User Updated!'};
								$scope.showSuccessAlert = true;
								$scope.showerror=false;

								$scope.edituserDTO.username='';
								$scope.edituserDTO.editusername='';
								//$scope.edituserDTO.usertype = $scope.usertype[0].id;
								$scope.edituserDTO.userstatus = '';
								//$scope.edituserDTO.emailid='';
								$scope.edituserDTO.mobile='';

								$scope.oneditclick();
							}
							else if(data[0]==="failure")
							{
								$scope.alerts = { type: 'danger', msg: 'User not Updated!'};
								$scope.showSuccessAlert = true;
								$scope.showerror=false;
							}
							else
							{
								if(data.length>0)
								{
									$scope.alerts = { type: 'danger'};
									$scope.errdata=data;
									$scope.showerror=true;
									$scope.showSuccessAlert = false;
								}
								else
								{
									$scope.alerts = { type: 'danger', msg: 'User not Updated!'};
									$scope.showSuccessAlert = true;
									$scope.showerror=false;
								}


							}

						}).error(function(data, status, headers, config){
							// called asynchronously if an error occurs
							// or server returns response with an error status.	

							$scope.alerts = { type: 'danger', msg: 'User not Updated!'};
							$scope.showSuccessAlert = true;
						});
					}
				}
				else
				{
					$scope.alerts = { type: 'danger', msg: 'All Fields should be Filled up.'};
					$scope.showSuccessAlert = true;
				}
			};

		};

		$scope.eclear=function(){
			$scope.edituserDTO.username='';
			$scope.edituserDTO.editusername='';
			//$scope.edituserDTO.usertype = $scope.usertype[0].id;
			$scope.edituserDTO.userstatus = '';
			//$scope.edituserDTO.emailid='';
			$scope.edituserDTO.mobile='';
		}

	};

	//*******when Report tab is clicked********************

	$scope.currentPage=1;
	$scope.itemsPerPage=5;

	$scope.onreportclick=function(){

		$scope.search='';

		//View Users - Report================================================================================================

		$scope.users = []; //declare an empty array
		$scope.isLoading = true;
		$http.get("user/getusersreport").success(function(response){ 
			$scope.users = response;  //ajax request to fetch data into $scope.data
			$scope.isLoading = false;
		});

		$scope.sort = function(keyname){
			$scope.sortKey = keyname;   //set the sortKey to the param passed
			$scope.reverse = !$scope.reverse; //if true make it false and vice versa
		};


		$scope.exportData = function () {

			var confirm = $mdDialog.confirm()
			.title('Would you like to Export Table data to Excel?')
			.ok('OK')
			.cancel('Cancel');

			$mdDialog.show(confirm).then(function() {
				var blob = new Blob([document.getElementById('exportableall').innerHTML], {
					type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
				});
				saveAs(blob, "User_Master_Report.xls");
			});
		};
	};	

});