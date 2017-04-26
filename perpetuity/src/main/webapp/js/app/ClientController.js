baseApp.controller("ClientController", function($scope, $location, $http, $timeout, $q, $filter) {

	console.log("ClientController loaded.....");

	// **********switch flag for success message**********
	$scope.switchBool = function (value) {
		$scope[value] = !$scope[value];
	};

	$scope.clientDTO={
			"clientName": "",
			"contactPerson": "",
			"contactEmail": "",
			"contactTelPhone": "",
			"status": ""
	};	

	$scope.eclientDTO={
			"clientId": 0,
			"clientName":"",
			"contactPerson": "",
			"contactEmail": "",
			"contactTelPhone": "",
			"status": ""
	};	

	// **********************When Create tab is clicked*****************************************************************

	$scope.oncreateclick=function(){

		console.log("Create Clicked...");

		$scope.clientstatus = ["Active", "Inactive"];
		$scope.clientDTO.status = $scope.clientstatus[0];	

		//*********Clear all the fields when clear button pressed*******
		$scope.clear=function(){

			$scope.clientDTO.clientName='';
			$scope.clientDTO.contactPerson='';
			$scope.clientDTO.contactEmail='';
			$scope.clientDTO.contactTelPhone = '';
			$scope.clientDTO.status = $scope.clientstatus[0];
		}

		//************when submit button is pressed validate and post to the service************

		//Create Client=======================================================================================================
		$scope.createclient=function(clientDTO){

			console.log("inside Create client..");

			console.log(clientDTO);

			//call user add service
			if($scope.clientDTO.clientName.length!=0 && $scope.clientDTO.contactPerson.length!=0 && $scope.clientDTO.contactEmail.length!=0 && $scope.clientDTO.contactTelPhone.length!=0 && $scope.clientDTO.status.length!=0) 
			{		
				$http({
					method : "POST",
					url : "client/add",
					data : clientDTO,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).then(
						function mySucces(response) {

							if(response.data==="success")
							{
								$scope.alerts = { type: 'success', msgtype: 'Success!' ,msg: 'Client Created'};
								$scope.showSuccessAlert = true;

								$scope.clientDTO.clientName='';
								$scope.clientDTO.contactPerson='';
								$scope.clientDTO.contactEmail='';
								$scope.clientDTO.contactTelPhone = '';
								$scope.clientDTO.status = $scope.clientstatus[0];
							}
						},
						function myError(response) {

							if (response.statusText === "failure") {		

								$scope.alerts = { type: 'failure', msgtype: 'Failure!' ,msg: 'Client not Created'};
								$scope.showSuccessAlert = true;
							}

						});		

			}	 	
			else
			{
				$scope.alerts = { type: 'danger', msgtype: 'Error!' ,msg: 'All Fields should be Filled up.'};
				$scope.showSuccessAlert = true;
			}
		};
	};


	// **********************When Edit tab is clicked*****************************************************************

	$scope.oneditclick=function(){

		console.log("Edit clicked...");		

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

		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});

		//***************when eclientDTO is not filled and clicked edit then error msg shows up*****************

		$scope.editclient=function(eclientDTO){

			if($scope.eclientDTO.clientId===0 && $scope.eclientDTO.clientName.length===0 && $scope.eclientDTO.contactPerson.length===0 && $scope.eclientDTO.contactEmail.length===0 && $scope.eclientDTO.contactTelPhone.length===0 && $scope.eclientDTO.status.length===0)
			{
				$scope.alerts = { type: 'danger', msgtype: 'Error!' ,msg: 'All Fields should be Filled up.'};
				$scope.showSuccessAlert = true;
			}
		};

		$scope.populateeditclient=function(){

			//to get selected clientname data
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

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});

			$scope.editclient=function(eclientDTO){

				console.log("inside Edit client..");

				console.log(eclientDTO);

				//call user add service

				if($scope.eclientDTO.clientId>0 && $scope.eclientDTO.clientName.length>0 && $scope.eclientDTO.contactPerson.length!=0 && $scope.eclientDTO.contactEmail.length!=0 && $scope.eclientDTO.contactTelPhone.length!=0 && $scope.eclientDTO.status.length!=0)
				{
					$http({
						method : "POST",
						url : "client/edit",
						data : eclientDTO,
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data, status, headers, config){

						if(data==="success")
						{
								$http({
									method : "POST",
									url : "company/updatecompanystatus",
									data : {"clientId": $scope.eclientDTO.clientId, "status": $scope.eclientDTO.status},
									headers : {
										'Content-Type' : 'application/json'
									}
								}).success(function(data, status, headers, config){

									if(data==="success")
									{
										$scope.alerts = { type: 'success', msgtype: 'Success!' ,msg: 'Client Updated!'};
										$scope.showSuccessAlert = true;

										$scope.eclientDTO.clientId='';
										$scope.eclientDTO.clientName='';
										$scope.eclientDTO.contactPerson='';
										$scope.eclientDTO.contactEmail='';
										$scope.eclientDTO.contactTelPhone = '';
										$scope.eclientDTO.status = '';
									}

								}).error(function(data, status, headers, config){
									$scope.alerts = { type: 'danger', msgtype: 'Failure!' ,msg: 'Client not Updated!'};
									$scope.showSuccessAlert = true;
								});							
						}

					}).error(function(data, status, headers, config){
						// called asynchronously if an error occurs
						// or server returns response with an error status.	

						$scope.alerts = { type: 'danger', msgtype: 'Failure!' ,msg: 'Client not Updated!'};
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
	};

//	*******when Report tab is clicked********************

	$scope.onreportclick=function(){

		console.log("Report Clicked......");

		//View Users - Report================================================================================================

		$scope.currentPage=1;
		$scope.itemsPerPage=5;

		$scope.clientalldata = []; //declare an empty array
		$http.get("client/getalldetails").success(function(response){ 
			$scope.clientalldata = response;  //ajax request to fetch data into $scope.data
		});

		$scope.sort = function(keyname){
			$scope.sortKey = keyname;   //set the sortKey to the param passed
			$scope.reverse = !$scope.reverse; //if true make it false and vice versa
		};


		$scope.exportData = function () {
			var blob = new Blob([document.getElementById('exportableall').innerHTML], {
				type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
			});
			saveAs(blob, "Client_Report.xls");
		};
	};	


});