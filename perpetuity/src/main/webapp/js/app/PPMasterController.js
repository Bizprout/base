baseApp.controller("PPMasterController", function($scope, $location, $http, $timeout, $q, $filter) {
	
	console.log("PPMasterController loaded...");
	
	// **********switch flag for success message**********
	$scope.switchBool = function (value) {
		$scope[value] = !$scope[value];
	};
	
	//*******DTO to store the form values for pp standard masters**********
	$scope.ppmasterDTO = {
			"cmpid" : 1,
			"mastertype" : "",
			"ppmastername":"",
			"ppparentname" : ""
	};	
	
	$scope.editppmasterDTO = {
			"mastertype" : "",
			"ppmastername":"",
			"editppmastername":"",
			"ppparentname" : ""
	};	
	
	//******Autocomplete dropdown default options for Select master type********
	$scope.mastertypeselectOptions = {
			displayText: 'Select Master Type',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};
	
	//******Autocomplete dropdown default options for Select pp master name********
	$scope.ppmasternameselectOptions = {
			displayText: 'Select PP Master Name',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};
	
	//******Autocomplete dropdown default options for Select pp parent name********
	$scope.ppparentnameselectOptions = {
			displayText: 'Select PP Parent Name',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};
	
	$scope.mastertypes = ["Ledger","Group","Cost Categories","Cost Center","Voucher Type"];
	$scope.ppparentnames=["Assets","Liabilities","Expenses","Income"];
	
	//*********Clear all the fields when clear button pressed*******
	$scope.clear=function(){

		$scope.ppmasterDTO.mastertype='';
		$scope.ppmasterDTO.ppmastername='';
		$scope.ppmasterDTO.ppparentname='';
	}
	
	$scope.eclear=function(){

		$scope.editppmasterDTO.mastertype='';
		$scope.editppmasterDTO.ppmastername='';
		$scope.editppmasterDTO.editppmastername='';
		$scope.editppmasterDTO.ppparentname='';
	}
	
	//Create pp Standard Masters=======================================================================================================
	$scope.createppmasters=function(ppmasterDTO){

		console.log("inside Create pp masters..");
		
		//call pp master add service
		if($scope.ppmasterDTO.mastertype.length!=0 && $scope.ppmasterDTO.ppmastername.length!=0 && $scope.ppmasterDTO.ppparentname.length!=0)
		{		
				$http({
					method : "POST",
					url : "ppmaster/add",
					data : ppmasterDTO,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).then(
						function mySucces(response) {

							if(response.data==="success")
							{
								$scope.alerts = { type: 'success', msgtype: 'Success!' ,msg: 'PP Masters Created'};
								$scope.showSuccessAlert = true;

								$scope.ppmasterDTO.mastertype='';
								$scope.ppmasterDTO.ppmastername='';
								$scope.ppmasterDTO.ppparentname='';
							}
						},
						function myError(response) {

							if (response.statusText === "failure") {		

								$scope.alerts = { type: 'failure', msgtype: 'Failure!' ,msg: 'PP Masters not Created'};
								$scope.showSuccessAlert = true;
							}

						});			
		}	 	
		else
		{
			$scope.alerts = { type: 'danger', msgtype: 'Error!' ,msg: 'All Fields should be Filled up.'};
			$scope.showSuccessAlert = true;
		}
	}
	
	//to get pp masters list based on master type selection
	
	$scope.getppmasternamelist=function(){
		$http({
			method : "POST",
			url : "ppmaster/getppmastersname",
			data : {"mastertype":$scope.editppmasterDTO.mastertype},
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config){

			//*******options for pp Masters name based on master type selection**********************************

			$scope.eppmasternames=data;

		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
	};
	
	$scope.ppmasternamechange=function(){
		
		$scope.editppmasterDTO.editppmastername=$scope.editppmasterDTO.ppmastername;
		
		$http({
			method : "POST",
			url : "ppmaster/getppparentname",
			data : {"mastertype":$scope.editppmasterDTO.mastertype, "ppmastername":$scope.editppmasterDTO.ppmastername},
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config){

			//*******options for pp Masters name based on master type selection**********************************

			$scope.editppmasterDTO.ppparentname=data[0];

		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
	};
	
	
	$scope.editppmasters=function(editppmasterDTO){

		console.log("inside Edit PP Masters..");
		
		//$scope.edituserDTO.usertype=$scope.usertype;
		
		console.log(editppmasterDTO);

		//call user add service

	if($scope.editppmasterDTO.mastertype.length!=0 && $scope.editppmasterDTO.ppmastername.length!=0 && $scope.editppmasterDTO.editppmastername.length!=0 && $scope.editppmasterDTO.ppparentname.length!=0)
		{
			$http({
				method : "POST",
				url : "ppmaster/edit",
				data : editppmasterDTO,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config){

				if(data==="success")
				{
					$scope.alerts = { type: 'success', msgtype: 'Success!' ,msg: 'PP Masters Updated!'};
					$scope.showSuccessAlert = true;

					$scope.editppmasterDTO.mastertype='';
					$scope.editppmasterDTO.ppmastername='';
					$scope.editppmasterDTO.editppmastername = '';
					$scope.editppmasterDTO.ppparentname = '';
				}
				
			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.	

					$scope.alerts = { type: 'danger', msgtype: 'Failure!' ,msg: 'PP Masters not Updated!'};
					$scope.showSuccessAlert = true;
			});
	
		}
		else
		{
			$scope.alerts = { type: 'danger', msgtype: 'Error!' ,msg: 'All Fields should be Filled up.'};
			$scope.showSuccessAlert = true;
		}
	};
	
	
	
});