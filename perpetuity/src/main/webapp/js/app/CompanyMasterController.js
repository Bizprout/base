baseApp.config(function($mdDateLocaleProvider) {
	$mdDateLocaleProvider.formatDate = function(date) {
		return moment(date).format('DD-MMM-YYYY');
	};
});
baseApp.controller("CompanyMasterController", function($scope, $location, $http, $timeout, $q, $filter, $mdpTimePicker) {

	console.log("CompanyMasterController loaded.....");
	
	// **********switch flag for success message**********
	$scope.switchBool = function (value) {
		$scope[value] = !$scope[value];
	};

	//*******DTO to store the form values for Company masters**********
	$scope.companyDTO = {
			"tallyCmpName" : "",
			"clientId" : 0,
			"tallyGUID": "",
			"appFromDate" : "",
			"uploadTimer": "",
			"dnldTimer": "",
			"maxRetrial": 0,
			"status": ""
	};	

	//******Autocomplete dropdown default options for Select Company********
	$scope.companyselectOptions = {
			displayText: 'Select Company',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};

	//******Autocomplete dropdown default options for Select Client********
	$scope.clientselectOptions = {
			displayText: 'Select Client',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};

	//******Autocomplete dropdown default options for Select company status********
	$scope.companystatusOptions = {
			displayText: 'Select Company Status',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};

	$scope.oncreateeditclick=function(){

		console.log("create clicked....");

		$scope.companyDTO.appFromDate = new Date();
		$scope.companyDTO.uploadTimer = new Date();
		$scope.companyDTO.dnldTimer = new Date();
		
		this.showTimePicker = function(ev) {
			$mdpTimePicker($scope.companyDTO.uploadTimer, {
				targetEvent: ev
			}).then(function(selectedDate) {
				$scope.companyDTO.uploadTimer = selectedDate;
			});;
		}

		this.showTimePicker = function(ev) {
			$mdpTimePicker($scope.companyDTO.dnldTimer, {
				targetEvent: ev
			}).then(function(selectedDate) {
				$scope.companyDTO.dnldTimer = selectedDate;
			});;
		}
		
		$scope.stats=["Active", "Inactive"];

		//******************to get Company List for Company dropdown**********************************
		$http({
			method : "GET",
			url : "usermapping/getcompanylist",
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config){

			//*******options for company names********************************
			$scope.companylist=data;

		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});		
		
		$scope.onchangeselectcompany=function(){
			console.log("company selected");
			
			$http({
				method : "POST",
				url : "company/getclientstatus",
				data : {"tallyCmpName":$scope.companyDTO.tallyCmpName},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config){

				//*******options for company names********************************

				$scope.clientid=data.clientId;
				$scope.status=data.status;
				
				if($scope.clientid===0 && $scope.status==="Inactive")
				{
					$http({
						method : "GET",
						url : "client/getclientidname",
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(dataclientnames, status, headers, config){

						//*******options for user names and default selected option*********

						$scope.clientnames=dataclientnames;
												
						$scope.clientselected=function(){
														
							$scope.companyDTO.tallyGUID=data.tallyGUID;
							$scope.companyDTO.appFromDate=new Date(data.appFromDate);
							$scope.companyDTO.uploadTimer=new Date(data.appFromDate+" "+data.uploadTimer);
							$scope.companyDTO.dnldTimer=new Date(data.appFromDate+" "+data.dnldTimer);
							$scope.companyDTO.maxRetrial=data.maxRetrial;
							$scope.companyDTO.status=data.status;
							
							if(data.appFromDate===null){$scope.companyDTO.appFromDate='';}
							if(data.uploadTimer===null){$scope.companyDTO.uploadTimer='';}
							if(data.dnldTimer===null){$scope.companyDTO.dnldTimer='';}
						};

					}).error(function(data, status, headers, config){
						// called asynchronously if an error occurs
						// or server returns response with an error status.
					});
				}
				else
				{					
					$http({
						method : "GET",
						url : "client/getclientidname",
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(dataclientnames, status, headers, config){
						
						$scope.clientnames=dataclientnames;
												
						$scope.companyDTO.clientId=data.clientId;
						$scope.companyDTO.tallyGUID=data.tallyGUID;
						$scope.companyDTO.appFromDate=new Date(data.appFromDate);
						$scope.companyDTO.uploadTimer=new Date(data.appFromDate+" "+data.uploadTimer);
						$scope.companyDTO.dnldTimer=new Date(data.appFromDate+" "+data.dnldTimer);
						$scope.companyDTO.maxRetrial=data.maxRetrial;
						$scope.companyDTO.status=data.status;
						
						if(data.appFromDate===null){$scope.companyDTO.appFromDate='';}
						if(data.uploadTimer===null){$scope.companyDTO.uploadTimer='';}
						if(data.dnldTimer===null){$scope.companyDTO.dnldTimer='';}
												
					}).error(function(data, status, headers, config){
						// called asynchronously if an error occurs
						// or server returns response with an error status.
					});
					
				}

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});		
			
		};
		
		$scope.updatecompany=function(companyDTO)
		{			
			console.log("inside updatecompany..");
			
			if($scope.companyDTO.clientId!=0 && 
				$scope.companyDTO.tallyCmpName.length!=0 && 
				$scope.companyDTO.appFromDate.length!=0 &&
				$scope.companyDTO.uploadTimer!='' && 
				$scope.companyDTO.dnldTimer!='' &&
				$scope.companyDTO.maxRetrial.length!=0 &&
				$scope.companyDTO.status.length!=0) 
			{	
				
				$http({
					method : "POST",
					url : "company/update",
					data : companyDTO,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data, status, headers, config){
					
					if(data==="success")
					{
						$scope.alerts = { type: 'success', msgtype: 'Success!' ,msg: 'Company Updated'};
						$scope.showSuccessAlert = true;

						$scope.companyDTO.clientId='';
						$scope.companyDTO.tallyCmpName='';
						$scope.companyDTO.tallyGUID='';
						$scope.companyDTO.appFromDate=''; 
						$scope.companyDTO.uploadTimer='';  
						$scope.companyDTO.dnldTimer=''; 
						$scope.companyDTO.maxRetrial=''; 
						$scope.companyDTO.status='';
					}
					
				}).error(function(data, status, headers, config){
					if (data === "failure") {		
						
						$scope.alerts = { type: 'failure', msgtype: 'Failure!' ,msg: 'Company not Updated'};
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
		
		$scope.clear=function(){
			
			$scope.companyDTO.clientId='';
			$scope.companyDTO.tallyCmpName='';
			$scope.companyDTO.tallyGUID='';
			$scope.companyDTO.appFromDate=''; 
			$scope.companyDTO.uploadTimer='';  
			$scope.companyDTO.dnldTimer=''; 
			$scope.companyDTO.maxRetrial=''; 
			$scope.companyDTO.status='';
		};
	};
	
	$scope.onreportclick=function(){
		
		console.log("Report clicked...");
		
		$scope.currentPage=1;
		$scope.itemsPerPage=5;
		
		//View Company - Report================================================================================================

		$scope.company = []; //declare an empty array
		$http.get("company/getcompanydata").success(function(response){ 
			$scope.company = response;  //ajax request to fetch data into $scope.data
		});

		$scope.sort = function(keyname){
			$scope.sortKey = keyname;   //set the sortKey to the param passed
			$scope.reverse = !$scope.reverse; //if true make it false and vice versa
		};


		$scope.exportData = function () {
			var blob = new Blob([document.getElementById('exportableall').innerHTML], {
				type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
			});
			saveAs(blob, "Company_Master_Report.xls");
		};
	};
});