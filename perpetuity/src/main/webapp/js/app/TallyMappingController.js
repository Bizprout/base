baseApp.controller("TallyMappingController", function($scope, $location, $http, $timeout, $q, $filter, $localStorage, $mdDialog) {

	console.log("TallyMappingController loaded...");
	//TODO angular constants

	$scope.cmpname=$localStorage.cmpname;


	// **********switch flag for success message**********
	$scope.switchBool = function (value) {
		$scope[value] = !$scope[value];
	};

	//******Autocomplete dropdown default options for Select client********
	$scope.clientselectOptions = {
			displayText: 'Select Client',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};

	//******Autocomplete dropdown default options for Select company********
	$scope.companyselectOptions = {
			displayText: 'Select Company',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};

	//******Autocomplete dropdown default options for Select master type********
	$scope.mastertypeselectOptions = {
			displayText: 'Select Master Type',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};

	//******Autocomplete dropdown default options for Select tally master name********
	$scope.tallymasterelectOptions = {
			displayText: 'Select Tally Master Name',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};

	//******Autocomplete dropdown default options for Select pp master name********
	$scope.ppmasterselectOptions = {
			displayText: 'Select PP Master Name',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};

	//*******DTO to store the form values for tally masters mapping to PP masters**********
	$scope.ppmapDTO = {
			"client" : 0,
			"cmpId" : 0,
			"mastertype":"",
			"tallyMasterId" : 0,
			"ppId":0
	};	

	$scope.onmapclick=function(){

		console.log("Map Clicked....");

		$scope.mastertypes = ["Ledger","Group","Cost Category","Cost Centre","Voucher Type"];

		$http({
			method : "GET",
			url : "client/getclientidname",
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(dataclientnames, status, headers, config){

			//*******options for client Names*********

			$scope.clientnames=dataclientnames;

		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});


		$scope.clientchanged=function(){

			$scope.ppmapDTO.mastertype='';

			$http({
				method : "POST",
				url : "pptallymapping/getcompanyidname",
				data: {"clientId":$scope.ppmapDTO.client},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(datacompnames, status, headers, config){

				//*******options for client Names*********

				$scope.companies=datacompnames;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		};

		$scope.companychanged=function(){
			$scope.ppmapDTO.mastertype='';
		};

		$scope.masterchanged=function(){

			console.log("inside masterchanged...");

			$http({
				method : "POST",
				url : "pptallymapping/getppmasternames",
				data: {"mastertype":$scope.ppmapDTO.mastertype, "cmpid":$scope.ppmapDTO.cmpId},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(datappmasternames, status, headers, config){

				//*******options for client Names*********

				$scope.ppmasternames=datappmasternames;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});

			$http({
				method : "POST",
				url : "pptallymapping/gettallymasternames",
				data: {"masterType":$scope.ppmapDTO.mastertype, "cmpId":$scope.ppmapDTO.cmpId},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(datatallymasternames, status, headers, config){

				//*******options for client Names*********

				$scope.tallymasternames=datatallymasternames;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		};

		$scope.ppmasternamechanged=function(){

			$http({
				method : "POST",
				url : "pptallymapping/gettallymasterids",
				data: {"cmpId":$scope.ppmapDTO.cmpId, "ppId":$scope.ppmapDTO.ppId},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(datatallymasterids, status, headers, config){

				//*******options for client Names*********

				$scope.ppmapDTO.tallyMasterId=datatallymasterids;

				$scope.datamapid=datatallymasterids;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		};


		$scope.mappingpp=function(){

			if($scope.ppmapDTO.client>0 && $scope.ppmapDTO.mastertype.length>0 && $scope.ppmapDTO.ppId>0 && $scope.ppmapDTO.cmpId>0 && $scope.ppmapDTO.tallyMasterId.length>0)
			{
				//call update
				if($scope.datamapid	>0)
				{
					angular.forEach($scope.ppmapDTO.tallyMasterId, function(value, key) {

						$http({
							method : "POST",
							url : "pptallymapping/update",
							data: {"ppId":$scope.ppmapDTO.ppId,"cmpId":$scope.ppmapDTO.cmpId, "tallyMasterId":$scope.ppmapDTO.tallyMasterId[key]},
							headers : {
								'Content-Type' : 'application/json'
							}
						}).success(function(successdata, status, headers, config){

							//*******options for client Names*********

							if(successdata[0]==='success')
							{
								$scope.alerts = { type: 'success', msgtype: 'Success!' ,msg: 'PP Masters to tally Mapped'};
								$scope.showSuccessAlert = true;
								$scope.showerror=false;

								$scope.ppmapDTO.client='';
								$scope.ppmapDTO.cmpId='';
								$scope.ppmapDTO.mastertype='';
								$scope.ppmapDTO.tallyMasterId='';
								$scope.ppmapDTO.ppId='';
							}
							else if(successdata[0]==='failure')
							{
								$scope.alerts = { type: 'danger', msgtype: 'Failure!' ,msg: 'PP Masters to tally not Mapped'};
								$scope.showSuccessAlert = true;
							}
							else
							{
								$scope.alerts = { type: 'danger', msgtype: 'Error!'};
								$scope.errdata=successdata;
								$scope.showerror=true;
							}

						}).error(function(data, status, headers, config){
							if (data === "failure") {		

								$scope.alerts = { type: 'danger', msgtype: 'Failure!' ,msg: 'PP Masters to tally not Mapped'};
								$scope.showSuccessAlert = true;
							}
						});

					});

				}
				else //call insert
				{
					angular.forEach($scope.ppmapDTO.tallyMasterId, function(value, key) {

						$http({
							method : "POST",
							url : "pptallymapping/insert",
							data: {"ppId":$scope.ppmapDTO.ppId,"cmpId":$scope.ppmapDTO.cmpId, "tallyMasterId":$scope.ppmapDTO.tallyMasterId[key]},
							headers : {
								'Content-Type' : 'application/json'
							}
						}).success(function(successdata, status, headers, config){

							//*******options for client Names*********

							if(successdata[0]==='success')
							{
								$scope.alerts = { type: 'success', msgtype: 'Success!' ,msg: 'PP Masters to tally Mapped'};
								$scope.showSuccessAlert = true;
								$scope.showerror=false;

								$scope.ppmapDTO.client='';
								$scope.ppmapDTO.cmpId='';
								$scope.ppmapDTO.mastertype='';
								$scope.ppmapDTO.tallyMasterId='';
								$scope.ppmapDTO.ppId='';
							}
							else if(successdata[0]==='failure')
							{
								$scope.alerts = { type: 'danger', msgtype: 'Failure!' ,msg: 'PP Masters to tally not Mapped'};
								$scope.showSuccessAlert = true;
							}
							else
							{
								$scope.alerts = { type: 'danger', msgtype: 'Error!'};
								$scope.errdata=successdata;
								$scope.showerror=true;
							}

						}).error(function(data, status, headers, config){
							if (data === "failure") {		

								$scope.alerts = { type: 'danger', msgtype: 'Failure!' ,msg: 'PP Masters to tally not Mapped'};
								$scope.showSuccessAlert = true;
							}
						});

					});
				}
			}
			else
			{
				$scope.alerts = { type: 'danger', msgtype: 'Error!' ,msg: 'All Fields should be Filled up.'};
				$scope.showSuccessAlert = true;
			}
		};


		$scope.clear=function(){
			$scope.ppmapDTO.client='';
			$scope.ppmapDTO.cmpId='';
			$scope.ppmapDTO.mastertype='';
			$scope.ppmapDTO.tallyMasterId='';
			$scope.ppmapDTO.ppId='';
		};

	};

	//***********when Import/Export tab is clicked**********************************
	$scope.onimpexpclick=function(){
		console.log("Import/Export clicked....");

		$http({
			method : "POST",
			url : "company/getclientstatus",
			data: {"tallyCmpName":$localStorage.cmpname},
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(response, status, headers, config){

			//*******options for client Names*********

			$scope.clientname=response.client.clientName;

		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});

		$scope.onppmappingexport=function(){

			var confirm = $mdDialog.confirm()
			.title('Would you like to Export Excel Format?')
			.ok('OK')
			.cancel('Cancel');

			$mdDialog.show(confirm).then(function() {
				var blob = new Blob([document.getElementById('ppmappingexcelexport').innerHTML], {
					type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
				});
				saveAs(blob, "PPMapping_upload_format.xls");
			});			
		};

		$scope.onppmappingimport=function(file){

			var file = $scope.myFile;
			var uploadUrl = "pptallymapping/ppmastermappinguploadfile";

			if(file!=undefined)
			{
				if(file.type==="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" || file.type==="application/vnd.ms-excel")
				{
					var fd = new FormData();
					fd.append('file', file);
					$http.post(uploadUrl, fd, {
						transformRequest: angular.identity,
						headers: {'Content-Type': undefined}
					})
					.success(function(response){

						if(response[0]==="success")
						{
							$scope.alerts = { type: 'success', msgtype: 'Success!' ,msg: "File - "+ file.name +" Uploaded Successfully!"};
							$scope.showSuccessAlert = true;
						}
						else if(response[0]==="failure")
						{
							$scope.alerts = { type: 'danger', msgtype: 'Failure!' ,msg: "File- "+ file.name +" not Uploaded!"};
							$scope.showSuccessAlert = true;
						}						

					})
					.error(function(response){

					});
				}
				else
				{
					$scope.alerts = { type: 'danger', msgtype: 'Error!' ,msg: "Please Upload Excel Files only!"};
					$scope.showSuccessAlert = true;
				}

			}
			else
			{
				$scope.alerts = { type: 'danger', msgtype: 'Error!' ,msg: "No file Selected!"};
				$scope.showSuccessAlert = true;
			}
		};
	};

	$scope.currentPage=1;
	$scope.itemsPerPage=10;

	$scope.onreportclick=function(){
		console.log("Report clicked....");

		//View tally pp mapping - Report================================================================================================

		$scope.reportclientchanged=function(){

			$scope.reportmastertype='';

			$http({
				method : "POST",
				url : "pptallymapping/getcompanyidname",
				data: {"clientId":$scope.reportclientid},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(reportdatacompnames, status, headers, config){

				//*******options for client Names*********

				$scope.reportcompanies=reportdatacompnames;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		};

		$scope.reportcompanychanged=function(){
			$scope.reportmastertype='';
		};

		$scope.savereportdata=function(){

			console.log($scope.reportclientid+"---"+$scope.reportcmpid+"---"+$scope.reportmastertype);

			if($scope.reportclientid===undefined || $scope.reportcmpid===undefined || $scope.reportmastertype.length===0)
			{
				$scope.alerts = { type: 'danger', msgtype: 'Error!' ,msg: 'All Fields should be Filled up.'};
				$scope.showSuccessAlert = true;
			}
		}

		$scope.tallyppmapdata=[];

		$scope.reportmasterchanged=function(){

			$scope.clientname=$scope.clientnames[0].clientName;
			$scope.companyname=$scope.reportcompanies[0].tallyCmpName;

			if($scope.reportcmpid>0 && $scope.reportmastertype.length!=0)
			{
				$http({
					method : "POST",
					url : "pptallymapping/gettallyppmappingdata",
					data: {"cmpId":$scope.reportcmpid, "masterType":$scope.reportmastertype},
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(reportdata, status, headers, config){

					//*******options for client Names*********

					$scope.tallyppmapdata=reportdata;

					$http({
						method : "POST",
						url : "pptallymapping/getppmasternames",
						data: {"mastertype":$scope.reportmastertype, "cmpid":$scope.reportcmpid},
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(reportdatappmasternames, status, headers, config){

						//*******options for client Names*********

						$scope.reportppmasternames=reportdatappmasternames;

						//$scope.reportppmasterid[4917]="rishwanth";


					}).error(function(data, status, headers, config){
						// called asynchronously if an error occurs
						// or server returns response with an error status.
					});

				}).error(function(data, status, headers, config){
					// called asynchronously if an error occurs
					// or server returns response with an error status.
				});


				$scope.savereportdata=function(){			

					if($scope.reportclientid!=undefined && $scope.reportcmpid!=undefined && $scope.reportmastertype!=undefined)
					{
						$scope.datapushed = [];
						angular.forEach($scope.tallyppmapdata, function (mainobj) {

							if(mainobj.ppid!=null)
							{
								$scope.datapushed.push({'cmpId':mainobj.cmpId, 'masterId':mainobj.masterId, 'ppid':mainobj.ppid});
							}						
						});

						$http({
							method : "POST",
							url : "pptallymapping/saveppmapping",
							data: $scope.datapushed,
							headers : {
								'Content-Type' : 'application/json'
							}
						}).success(function(successdata, status, headers, config){

							//*******options for client Names*********

							console.log(successdata);

							if(successdata==='success')
							{
								$scope.alerts = { type: 'success', msgtype: 'Success!' ,msg: 'PP Masters to tally Mapped'};
								$scope.showSuccessAlert = true;

								$scope.reportclientid='';
								$scope.reportcmpid='';
								$scope.reportmastertype='';
								$scope.tallyppmapdata=[];
							}
							else
							{
								$scope.alerts = { type: 'danger', msgtype: 'Failure!' ,msg: 'PP Masters to tally not Mapped'};
								$scope.showSuccessAlert = true;
							}


						}).error(function(data, status, headers, config){
							// called asynchronously if an error occurs
							// or server returns response with an error status.
						});
					}
					else
					{
						$scope.alerts = { type: 'danger', msgtype: 'Failure!' ,msg: 'All fields are Mandatory!'};
						$scope.showSuccessAlert = true;
					}

				};				
			}
			else
			{
				$scope.alerts = { type: 'danger', msgtype: 'Error!' ,msg: 'All Fields should be Filled up.'};
				$scope.showSuccessAlert = true;
			}
		};		

		$scope.sort = function(keyname){
			$scope.sortKey = keyname;   //set the sortKey to the param passed
			$scope.reverse = !$scope.reverse; //if true make it false and vice versa
		};


		$scope.exportData = function () {

			if($scope.tallyppmapdata.length>0)
			{
				var blob = new Blob([document.getElementById('exportableall').innerHTML], {
					type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
				});
				saveAs(blob, "Tally_PP_Mapping_Report.xls");
			}
			else
			{
				$scope.alerts = { type: 'danger', msgtype: 'Error!' ,msg: 'No data to Export!.'};
				$scope.showSuccessAlert = true;
			}

		};

	};
});
