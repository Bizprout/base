baseApp.controller("TallyMappingController", function($scope, $location, $http, $timeout, $q, $filter, $localStorage, $mdDialog) {


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
			"cmpId" : $localStorage.cmpid,
			"mastertype":"",
			"tallyMasterId" : 0,
			"ppId":0
	};	

	$scope.onmapclick=function(){

		$scope.ppmapDTO.mastertype='';
		$scope.ppmapDTO.tallyMasterId='';
		$scope.ppmapDTO.ppId='';

		$scope.mastertypes = ["Ledger","Group","Cost Category","Cost Centre","Voucher Type"];


		$scope.masterchanged=function(ppmapDTO){
			
			$scope.isLoadingmaptoppmaster=true;
			
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
				
				$scope.isLoadingmaptoppmaster=false;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
			
			$scope.isLoadingtallymasternames=true;
			
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
				
				$scope.isLoadingtallymasternames=false;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		};

		$scope.ppmasternamechanged=function(ppmapDTO){

			$scope.isLoadingtallymasternames=true;
			
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
				
				$scope.isLoadingtallymasternames=false;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		};


		$scope.mappingpp=function(ppmapDTO){

			if($scope.ppmapDTO.mastertype.length>0 || $scope.ppmapDTO.ppId>0 || $scope.ppmapDTO.cmpId>0 || $scope.ppmapDTO.tallyMasterId.length>0)
			{				
				if($scope.ppmapDTO.mastertype==='')
				{
					$scope.alerts = { type: 'danger', msg: 'Master Type is Required!'};
					$scope.showSuccessAlert = true;
				}
				else if($scope.ppmapDTO.ppId===0)
				{
					$scope.alerts = { type: 'danger', msg: 'Map to PP Master is Required!'};
					$scope.showSuccessAlert = true;
				}
				else if($scope.ppmapDTO.tallyMasterId.length===0)
				{
					$scope.alerts = { type: 'danger', msg: 'Tally Master Name is Required!'};
					$scope.showSuccessAlert = true;
				}				
				//call insert
				else
				{
					//delete the records for the cmpid and ppid

					if($scope.datamapid.length>0)
					{
						$http({
							method : "POST",
							url : "pptallymapping/deleteppidcmpid",
							data: {"ppId":$scope.ppmapDTO.ppId,"cmpId":$scope.ppmapDTO.cmpId},
							headers : {
								'Content-Type' : 'application/json'
							}
						}).success(function(deldata, status, headers, config){

						}).error(function(deldata, status, headers, config){

						});

					}

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
								$scope.alerts = { type: 'success' ,msg: 'PP Masters to tally Mapped'};
								$scope.showSuccessAlert = true;
								$scope.showerror=false;

								$scope.ppmapDTO.client='';
								$scope.ppmapDTO.mastertype='';
								$scope.ppmapDTO.tallyMasterId='';
								$scope.ppmapDTO.ppId='';

								$scope.onmapclick();
							}
							else if(successdata[0]==='failure')
							{
								$scope.alerts = { type: 'danger' ,msg: 'PP Masters to tally not Mapped'};
								$scope.showSuccessAlert = true;
								$scope.showerror=false;
							}
							else
							{
								if(successdata.length>0)
								{
									$scope.alerts = { type: 'danger'};
									$scope.errdata=successdata;
									$scope.showerror=true;
									$scope.showSuccessAlert = false;
								}
								else
								{
									$scope.alerts = { type: 'danger' ,msg: 'PP Masters to tally not Mapped'};
									$scope.showSuccessAlert = true;
									$scope.showerror=false;
								}
								
							}

						}).error(function(data, status, headers, config){
							if (data === "failure") {		

								$scope.alerts = { type: 'danger' ,msg: 'PP Masters to tally not Mapped'};
								$scope.showSuccessAlert = true;
							}
						});

					});
				}
			}
			else
			{
				$scope.alerts = { type: 'danger' ,msg: 'All Mandatory Fields should be Filled up.'};
				$scope.showSuccessAlert = true;
			}
		};


		$scope.clear=function(){
			$scope.ppmapDTO.client='';
			$scope.ppmapDTO.mastertype='';
			$scope.ppmapDTO.tallyMasterId='';
			$scope.ppmapDTO.ppId='';
		};

	};

	//***********when Import/Export tab is clicked**********************************
	$scope.onimpexpclick=function(){
		
		$http({
			method : "POST",
			url : "pptallymapping/setsessioncmpid",
			data : {"cmpId":$localStorage.cmpid},
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config){

		}).error(function(data, status, headers, config){

		});
		
		$http({
			method : "POST",
			url : "company/getclientstatus",
			data: {"cmpId":$localStorage.cmpid},
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
			.textContent("Note: Please Save the Excel file as Excel Workbook and Upload.")
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
			
			$scope.isLoadingimportfile=true;

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
							$scope.alerts = { type: 'success' ,msg: "File - "+ file.name +" Uploaded Successfully!"};
							$scope.showSuccessAlert = true;
							$scope.showerror=false;
						}
						else if(response[0]==="failure")
						{
							$scope.alerts = { type: 'danger' ,msg: "File- "+ file.name +" not Uploaded!"};
							$scope.showSuccessAlert = true;
							$scope.showerror=false;
						}
						else
						{
							if(response.length>0)
							{
								$scope.alerts = { type: 'danger'};
								$scope.errdata=response;
								$scope.showerror=true;
								$scope.showSuccessAlert=false;
							}
							else
							{
								$scope.alerts = { type: 'danger' ,msg: "File- "+ file.name +" not Uploaded!"};
								$scope.showSuccessAlert = true;
								$scope.showerror=false;
							}
						}
						
						$scope.isLoadingimportfile=false;

					})
					.error(function(response){

					});
				}
				else
				{
					$scope.alerts = { type: 'danger' ,msg: "Please Upload Excel Files only!"};
					$scope.showSuccessAlert = true;
				}

			}
			else
			{
				$scope.alerts = { type: 'danger' ,msg: "No file Selected!"};
				$scope.showSuccessAlert = true;
			}
		};
	};

	$scope.currentPage=1;
	$scope.itemsPerPage=10;

	$scope.onreportclick=function(){

		$scope.search='';

		//View tally pp mapping - Report================================================================================================


		$scope.savereportdata=function(){

			if($scope.reportmastertype===undefined)
			{
				$scope.alerts = { type: 'danger' ,msg: 'Master Type is Required!'};
				$scope.showSuccessAlert = true;
			}
		}

		$scope.tallyppmapdata=[];

		$scope.reportmasterchanged=function(){
			
			$scope.isLoading=true;
			
			if($localStorage.cmpid>0 && $scope.reportmastertype.length!=0)
			{
				$http({
					method : "POST",
					url : "pptallymapping/gettallyppmappingdata",
					data: {"cmpId":$localStorage.cmpid, "masterType":$scope.reportmastertype},
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(reportdata, status, headers, config){

					//*******options for client Names*********

					$scope.tallyppmapdata=reportdata;

					$http({
						method : "POST",
						url : "pptallymapping/getppmasternames",
						data: {"mastertype":$scope.reportmastertype, "cmpid":$localStorage.cmpid},
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(reportdatappmasternames, status, headers, config){

						//*******options for client Names*********

						$scope.reportppmasternames=reportdatappmasternames;
						
						$scope.isLoading=false;
						
					}).error(function(data, status, headers, config){
						// called asynchronously if an error occurs
						// or server returns response with an error status.
					});

				}).error(function(data, status, headers, config){
					// called asynchronously if an error occurs
					// or server returns response with an error status.
				});
						

				$scope.savereportdata=function(){			

					if($scope.reportmastertype!=undefined)
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

							if(successdata==='success')
							{
								$scope.alerts = { type: 'success' ,msg: 'PP Masters to tally Mapped'};
								$scope.showSuccessAlert = true;

								$scope.reportmastertype='';
								$scope.tallyppmapdata=[];
							}
							else
							{
								$scope.alerts = { type: 'danger' ,msg: 'PP Masters to tally not Mapped'};
								$scope.showSuccessAlert = true;
							}


						}).error(function(data, status, headers, config){
							// called asynchronously if an error occurs
							// or server returns response with an error status.
						});
					}
					else
					{
						$scope.alerts = { type: 'danger' ,msg: 'All Mandatory Fields should be Filled up!'};
						$scope.showSuccessAlert = true;
					}

				};				
			}
			else
			{
				$scope.alerts = { type: 'danger' ,msg: 'All Mandatory Fields should be Filled up.'};
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
				$scope.alerts = { type: 'danger' ,msg: 'No data to Export!.'};
				$scope.showSuccessAlert = true;
			}

		};

	};
});
