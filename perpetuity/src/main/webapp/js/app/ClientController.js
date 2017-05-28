baseApp.controller("ClientController", function($scope, $location, $http, $timeout, $q, $filter, $localStorage, $mdDialog) {

	console.log("ClientController loaded.....");

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

	$scope.clientDTO={
			"clientName": "",
			"contactPerson": "",
			"contactEmail": "",
			"contactTelPhone": "",
			"status": "Active"
	};	

	$scope.eclientDTO={
			"clientId": 0,
			"clientName": "",
			"contactPerson": "",
			"contactEmail": "",
			"contactTelPhone": "",
			"status": ""
	};	

	// **********************When Create tab is clicked*****************************************************************

	$scope.oncreateclick=function(){

		console.log("Create Clicked...");
		
		$scope.clientDTO.clientName='';
		$scope.clientDTO.contactPerson='';
		$scope.clientDTO.contactEmail='';
		$scope.clientDTO.contactTelPhone = '';

		$scope.clientstatus = ["Active", "Inactive"];

		//*********Clear all the fields when clear button pressed*******
		$scope.clear=function(){

			$scope.clientDTO.clientName='';
			$scope.clientDTO.contactPerson='';
			$scope.clientDTO.contactEmail='';
			$scope.clientDTO.contactTelPhone = '';
			//$scope.clientDTO.status = $scope.clientstatus[0];
		}

		//************when submit button is pressed validate and post to the service************

		//Create Client=======================================================================================================
		$scope.createclient=function(clientDTO){

			console.log("inside Create client..");

			//call user add service
			if($scope.clientDTO.contactEmail===undefined)
			{
				$scope.alerts = { type: 'danger', msg: 'E-Mail Address is not Valid!'};
				$scope.showSuccessAlert = true;
			}
			else if($scope.clientDTO.clientName.length!=0 || $scope.clientDTO.contactEmail.length!=0) 
			{	
				if($scope.clientDTO.clientName===undefined)
				{
					$scope.alerts = { type: 'danger', msg: 'Client Name cannot be Empty!'};
					$scope.showSuccessAlert = true;
				}
				else if($scope.clientDTO.contactEmail===undefined)
				{
					$scope.alerts = { type: 'danger', msg: 'Email cannot be Empty!'};
					$scope.showSuccessAlert = true;
				}
				else
				{
					$http({
						method : "POST",
						url : "client/add",
						data : clientDTO,
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data, status, headers, config){

						if(data[0]==="success")
						{
							$scope.alerts = { type: 'success', msg: 'Client Created'};
							$scope.showSuccessAlert = true;
							$scope.showerror=false;

							$scope.clientDTO.clientName='';
							$scope.clientDTO.contactPerson='';
							$scope.clientDTO.contactEmail='';
							$scope.clientDTO.contactTelPhone = '';
							//$scope.clientDTO.status = $scope.clientstatus[0];
						}
						else if(data[0]==="failure")
						{
							$scope.alerts = { type: 'danger', msg: 'Client not Created'};
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

						$scope.alerts = { type: 'danger', msg: 'Client not Created'};
						$scope.showSuccessAlert = true;

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


	// **********************When Edit tab is clicked*****************************************************************

	$scope.oneditclick=function(){

		console.log("Edit clicked...");		
		
		$scope.eclientDTO.clientId='';
		$scope.eclientDTO.clientName='';
		$scope.eclientDTO.contactPerson='';
		$scope.eclientDTO.contactEmail='';
		$scope.eclientDTO.contactTelPhone = '';
		$scope.eclientDTO.status = '';

		//******Autocomplete dropdown default options for select Client name********
		$scope.clientnameselectOptions = {
				displayText: 'Select Client Name',
				emptyListText: 'Oops! The list is empty',
				emptySearchResultText: 'Sorry, couldn\'t find "$0"'
		};

		//******Autocomplete dropdown default options for Client status********
		$scope.clientstatusselectOptions = {
				displayText: 'Select Client Status',
				emptyListText: 'Oops! The list is empty',
				emptySearchResultText: 'Sorry, couldn\'t find "$0"'
		};

		$scope.isLoadingclientname=true;

		//to get Edit client name dropdown
		$http({
			method : "GET",
			url : "client/getclientnames",
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config){

			//*******options for user names and default selected option*********

			$scope.clientnames=data;
			$scope.isLoadingclientname=false;

		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});

		//***************when eclientDTO is not filled and clicked edit then error msg shows up*****************

		$scope.editclient=function(eclientDTO){
			
			console.log($scope.eclientDTO.status.length);

			if($scope.eclientDTO.clientId==="" && $scope.eclientDTO.clientName==="" && $scope.eclientDTO.contactEmail==="" && $scope.eclientDTO.status==="")
			{
				$scope.alerts = { type: 'danger', msg: 'All Mandatory Fields should be Filled up.'};
				$scope.showSuccessAlert = true;
			}
		};

		$scope.populateeditclient=function(){

			//to get selected clientname data

			$scope.isLoadingcontactperson=true;
			$scope.isLoadingemail=true;
			$scope.isLoadingphone=true;
			$scope.isLoadingclientstatus=true;

			$http({
				method : "POST",
				url : "client/getclientdata",
				data : {"clientId": $scope.eclientDTO.clientId},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config){

				//*******options for selected client name*********

				$scope.eclientDTO.clientName=data.clientName;
				$scope.eclientDTO.contactPerson=data.contactPerson;
				$scope.eclientDTO.contactEmail=data.contactEmail;
				$scope.eclientDTO.contactTelPhone=data.contactTelPhone;
				$scope.eclientDTO.status=data.status;

				$scope.isLoadingcontactperson=false;
				$scope.isLoadingemail=false;
				$scope.isLoadingphone=false;
				$scope.isLoadingclientstatus=false;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});

			$scope.editclient=function(eclientDTO){

				console.log("inside Edit client..");

				//call user add service

				if($scope.eclientDTO.clientId>0 || $scope.eclientDTO.clientName!=undefined || $scope.eclientDTO.contactEmail!=undefined || $scope.eclientDTO.status.length!=0)
				{
					if($scope.eclientDTO.clientId===0)
					{
						$scope.alerts = { type: 'danger', msg: 'Please select Client Name!'};
						$scope.showSuccessAlert = true;
					}
					else if($scope.eclientDTO.clientName===undefined)
					{
						$scope.alerts = { type: 'danger', msg: 'Edit Client Name cannot be Empty!'};
						$scope.showSuccessAlert = true;
					}					
					else if($scope.eclientDTO.contactEmail===undefined)
					{
						$scope.alerts = { type: 'danger', msg: 'Email cannot be Empty!'};
						$scope.showSuccessAlert = true;
					}
					else if($scope.eclientDTO.status.length===0)
					{
						$scope.alerts = { type: 'danger', msg: 'Client Status cannot be Empty!'};
						$scope.showSuccessAlert = true;
					}
					else
					{
						if($scope.eclientDTO.status==="Inactive")
						{
							var confirm = $mdDialog.confirm()
							.title('Are you sure you want to make this Client Inactive?')
							.textContent('Note: All the Companies Mapped to this Client will be Inactive.')
							.ok('OK')
							.cancel('Cancel');
							
							$mdDialog.show(confirm).then(function() {
								$http({
									method : "POST",
									url : "client/edit",
									data : eclientDTO,
									headers : {
										'Content-Type' : 'application/json'
									}
								}).success(function(data, status, headers, config){

									if(data[0]==="success")
									{
										$http({
											method : "POST",
											url : "company/updatecompanystatus",
											data : {"clientId": $scope.eclientDTO.clientId, "status": $scope.eclientDTO.status},
											headers : {
												'Content-Type' : 'application/json'
											}
										}).success(function(dataa, status, headers, config){

										}).error(function(data, status, headers, config){
											$scope.alerts = { type: 'danger', msg: 'Client not Updated!'};
											$scope.showSuccessAlert = true;
										});

										if(data[0]==="success")
										{
											$scope.alerts = { type: 'success', msg: 'Client Updated!'};
											$scope.showSuccessAlert = true;
											$scope.showerror=false;

											$scope.eclientDTO.clientId='';
											$scope.eclientDTO.clientName='';
											$scope.eclientDTO.contactPerson='';
											$scope.eclientDTO.contactEmail='';
											$scope.eclientDTO.contactTelPhone = '';
											$scope.eclientDTO.status = '';
											
											$scope.oneditclick();
										}
										else if(data[0]==="failure")
										{
											$scope.alerts = { type: 'danger', msg: 'Client not Updated!'};
											$scope.showSuccessAlert = true;
										}
										else
										{
											$scope.alerts = { type: 'danger'};
											$scope.errdata=data[0];
											$scope.showerror=true;
											$scope.showSuccessAlert = false;
										}
									}
									else if(data[0]==="failure")
									{
										$scope.alerts = { type: 'danger', msg: 'Client not Updated!'};
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
									// called asynchronously if an error occurs
									// or server returns response with an error status.	

									$scope.alerts = { type: 'danger',msg: 'Client not Updated!'};
									$scope.showSuccessAlert = true;
								});
							});
						}
						else
						{
							$http({
								method : "POST",
								url : "client/edit",
								data : eclientDTO,
								headers : {
									'Content-Type' : 'application/json'
								}
							}).success(function(data, status, headers, config){

								if(data[0]==="success")
								{
									$http({
										method : "POST",
										url : "company/updatecompanystatus",
										data : {"clientId": $scope.eclientDTO.clientId, "status": $scope.eclientDTO.status},
										headers : {
											'Content-Type' : 'application/json'
										}
									}).success(function(dataa, status, headers, config){

									}).error(function(data, status, headers, config){
										$scope.alerts = { type: 'danger', msg: 'Client not Updated!'};
										$scope.showSuccessAlert = true;
									});

									if(data[0]==="success")
									{
										$scope.alerts = { type: 'success', msg: 'Client Updated!'};
										$scope.showSuccessAlert = true;
										$scope.showerror=false;

										$scope.eclientDTO.clientId='';
										$scope.eclientDTO.clientName='';
										$scope.eclientDTO.contactPerson='';
										$scope.eclientDTO.contactEmail='';
										$scope.eclientDTO.contactTelPhone = '';
										$scope.eclientDTO.status = '';
										
										$scope.oneditclick();
									}
									else if(data[0]==="failure")
									{
										$scope.alerts = { type: 'danger', msg: 'Client not Updated!'};
										$scope.showSuccessAlert = true;
									}
									else
									{
										$scope.alerts = { type: 'danger'};
										$scope.errdata=data[0];
										$scope.showerror=true;
										$scope.showSuccessAlert = false;
									}
								}
								else if(data[0]==="failure")
								{
									$scope.alerts = { type: 'danger', msg: 'Client not Updated!'};
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
								// called asynchronously if an error occurs
								// or server returns response with an error status.	

								$scope.alerts = { type: 'danger',msg: 'Client not Updated!'};
								$scope.showSuccessAlert = true;
							});
						}
					}
				}
				else
				{
					$scope.alerts = { type: 'danger', msg: 'All Fields should be Filled up.'};
					$scope.showSuccessAlert = true;
				}
			};
		};
	};

//	*******when Report tab is clicked********************

	$scope.onreportclick=function(){

		console.log("Report Clicked......");
		
		$scope.search='';

		//View Users - Report================================================================================================

		$scope.currentPage=1;
		$scope.itemsPerPage=5;

		$scope.clientalldata = []; //declare an empty array
		$scope.isLoading=true;
		$http.get("client/getalldetails").success(function(response){ 
			$scope.clientalldata = response;  //ajax request to fetch data into $scope.data
			$scope.isLoading=false;
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
				saveAs(blob, "Client_Report.xls");
			});


		};
	};	


});