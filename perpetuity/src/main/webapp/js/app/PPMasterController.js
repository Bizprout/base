baseApp.controller("PPMasterController", function($scope, $location, $http, $timeout, $q, $filter, $localStorage, $mdDialog) {

	console.log("PPMasterController loaded...");
	//TODO angular constants

	$scope.cmpname=$localStorage.cmpname;

	$scope.hidecostcat=true;

	// **********switch flag for success message**********
	$scope.switchBool = function (value) {
		$scope[value] = !$scope[value];
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

	//******Autocomplete dropdown default options for Select pp parent name********
	$scope.costcatselectOptions = {
			displayText: 'Select Cost Category',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};

	//*******DTO to store the form values for pp standard masters**********
	$scope.ppmasterDTO = {
			"cmpid" : $localStorage.cmpid,
			"mastertype" : "",
			"ppmastername":"",
			"ppparentname" : "",
			"costcategory" : ""	
	};	

	$scope.editppmasterDTO = {
			"mastertype" : "",
			"ppmastername":"",
			"editppmastername":"",
			"ppparentname" : "",
			"costcategory" : ""
	};	

	$scope.mastertypes = ["Ledger","Group","Cost Category","Cost Centre","Voucher Type"];


	//***********when Create tab is clicked**********************************
	$scope.oncreateclick=function(){

		console.log("Create Clicked....");

		$scope.mastertypechanged=function(){

			if(($scope.ppmasterDTO.mastertype==="Ledger" || $scope.ppmasterDTO.mastertype==="Group"))
			{
				$scope.hidecostcat=true;
				$scope.ppparentnames=["Assets","Liabilities","Expenses","Income"];
			}
			else if($scope.ppmasterDTO.mastertype==="Cost Category")
			{
				$scope.hidecostcat=true;
				$scope.ppparentnames=["Primary"];
			}
			else if($scope.ppmasterDTO.mastertype==="Cost Centre"){

				$scope.hidecostcat=false;
				$scope.costcategories=["Primary"];
				$scope.ppparentnames=["Primary"];

				$http({
					method : "POST",
					url : "ppmaster/getppmastersname",
					data : {"mastertype":"Cost Category"},
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(dataccat, status, headers, config){

					for(var key1 in dataccat){
						$scope.costcategories.push(dataccat[key1]);
					}

				}).error(function(data, status, headers, config){
					// called asynchronously if an error occurs
					// or server returns response with an error status.
				});
			}
			else if($scope.ppmasterDTO.mastertype==="Voucher Type")
			{
				$scope.hidecostcat=true;
				$scope.ppparentnames=["Contra","Credit Note","Debit Note","Delivery Note","Job Work In Order","Job Work Out Order","Journal","Material In","Material Out","Memorandum","Payment","Physical Stock","Purchase","Purchase Order","Receipt","Receipt Note","Rejections In","Rejections Out","Reversing Journal","Sales","Sales Order","Stock Journal"];
			}

			if($scope.ppmasterDTO.mastertype==="Ledger")
			{
				$http({
					method : "POST",
					url : "ppmaster/getppmastersname",
					data : {"mastertype":"Group"},
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data, status, headers, config){

					for(var key in data){
						$scope.ppparentnames.push(data[key]);
					}

				}).error(function(data, status, headers, config){
					// called asynchronously if an error occurs
					// or server returns response with an error status.
				});
			}
			else
			{
				$http({
					method : "POST",
					url : "ppmaster/getppmastersname",
					data : {"mastertype":$scope.ppmasterDTO.mastertype},
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data, status, headers, config){

					for(var key in data){
						$scope.ppparentnames.push(data[key]);
					}

				}).error(function(data, status, headers, config){
					// called asynchronously if an error occurs
					// or server returns response with an error status.
				});
			}
		};

		//Create pp Standard Masters=======================================================================================================
		$scope.createppmasters=function(ppmasterDTO){

			console.log("inside Create pp masters..");

			//call pp master add service
			if($scope.ppmasterDTO.mastertype.length!=0 && $scope.ppmasterDTO.ppmastername.length!=0 && $scope.ppmasterDTO.ppparentname.length!=0)
			{	
				if($scope.ppmasterDTO.mastertype==='Cost Centre' && $scope.ppmasterDTO.costcategory.length===0)
				{
					$scope.alerts = { type: 'danger', msgtype: 'Error!' ,msg: 'All Fields should be Filled up.'};
					$scope.showSuccessAlert = true;
				}
				else
				{
					$http({
						method : "POST",
						url : "ppmaster/add",
						data : ppmasterDTO,
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data, status, headers, config){

						if(data[0]==="success")
						{
							$scope.alerts = { type: 'success', msgtype: 'Success!' ,msg: 'PP Masters Created'};
							$scope.showSuccessAlert = true;
							$scope.showerror=false;

							$scope.ppmasterDTO.mastertype='';
							$scope.ppmasterDTO.ppmastername='';
							$scope.ppmasterDTO.ppparentname='';
							$scope.ppmasterDTO.costcategory='';

							$scope.hidecostcat=true;
						}
						else if(data[0]==="failure")
						{
							$scope.alerts = { type: 'danger', msgtype: 'Failure!' ,msg: 'PP Masters not Created'};
							$scope.showSuccessAlert = true;
						}
						else
						{
							$scope.alerts = { type: 'danger', msgtype: 'Error!'};
							$scope.errdata=data[0];
							$scope.showerror=true;
						}
					}).error(function(data, status, headers, config){

						$scope.alerts = { type: 'danger', msgtype: 'Failure!' ,msg: 'PP Masters not Created'};
						$scope.showSuccessAlert = true;
					});	
				}		
			}	 	
			else
			{
				$scope.alerts = { type: 'danger', msgtype: 'Error!' ,msg: 'All Fields should be Filled up.'};
				$scope.showSuccessAlert = true;
			}
		};

		//*********Clear all the fields when clear button pressed*******
		$scope.clear=function(){

			$scope.ppmasterDTO.mastertype='';
			$scope.ppmasterDTO.ppmastername='';
			$scope.ppmasterDTO.ppparentname='';
			$scope.ppmasterDTO.costcategory='';
		}
	};	


	//***********when Edit tab is clicked**********************************
	$scope.oneditclick=function(){
		//to get pp masters list based on master type selection

		console.log("Edit Clicked....");

		$scope.editppmasterDTO.mastertype='';
		$scope.editppmasterDTO.ppmastername='';
		$scope.editppmasterDTO.editppmastername='';
		$scope.editppmasterDTO.ppparentname='';
		$scope.editppmasterDTO.costcategory='';

		$scope.getppmasternamelist=function(){

			$scope.editppmasterDTO.ppmastername='';
			$scope.editppmasterDTO.editppmastername = '';
			$scope.editppmasterDTO.ppparentname = '';
			$scope.editppmasterDTO.costcategory = '';

			$scope.isLoading = true;

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
				$scope.isLoading = false;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		};

		$scope.ppmasternamechange=function(){

			$scope.isLoading = true;

			if(($scope.editppmasterDTO.mastertype==="Ledger" || $scope.editppmasterDTO.mastertype==="Group"))
			{
				$scope.hidecostcat=true;
				$scope.ppparentnames=["Assets","Liabilities","Expenses","Income"];
			}
			else if($scope.editppmasterDTO.mastertype==="Cost Category")
			{
				$scope.hidecostcat=true;
				$scope.ppparentnames=["Primary"];
			}
			else if($scope.editppmasterDTO.mastertype==="Cost Centre"){

				$scope.hidecostcat=false;
				$scope.costcategories=["Primary"];
				$scope.ppparentnames=["Primary"];

				$http({
					method : "POST",
					url : "ppmaster/getppmastersname",
					data : {"mastertype":"Cost Category"},
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(dataccat, status, headers, config){

					for(var key1 in dataccat){
						$scope.costcategories.push(dataccat[key1]);
					}
					$scope.isLoading = false;

				}).error(function(data, status, headers, config){
					// called asynchronously if an error occurs
					// or server returns response with an error status.
				});
			}
			else if($scope.editppmasterDTO.mastertype==="Voucher Type")
			{
				$scope.hidecostcat=true;
				$scope.ppparentnames=["Contra","Credit Note","Debit Note","Delivery Note","Job Work In Order","Job Work Out Order","Journal","Material In","Material Out","Memorandum","Payment","Physical Stock","Purchase","Purchase Order","Receipt","Receipt Note","Rejections In","Rejections Out","Reversing Journal","Sales","Sales Order","Stock Journal"];
			}

			if($scope.editppmasterDTO.mastertype==="Ledger")
			{
				$http({
					method : "POST",
					url : "ppmaster/getppmastersname",
					data : {"mastertype":"Group"},
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data, status, headers, config){

					for(var key in data){
						$scope.ppparentnames.push(data[key]);
					}					
					$scope.isLoading = false;


				}).error(function(data, status, headers, config){
					// called asynchronously if an error occurs
					// or server returns response with an error status.
				});
			}
			else
			{
				$http({
					method : "POST",
					url : "ppmaster/getppmastersname",
					data : {"mastertype":$scope.editppmasterDTO.mastertype},
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data, status, headers, config){

					var index = data.indexOf($scope.editppmasterDTO.ppmastername);
					data.splice(index, 1);

					for(var key in data){
						$scope.ppparentnames.push(data[key]);
					}
					$scope.isLoading = false;

				}).error(function(data, status, headers, config){
					// called asynchronously if an error occurs
					// or server returns response with an error status.
				});
			}

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

				if($scope.editppmasterDTO.mastertype==="Cost Centre")
				{
					$scope.editppmasterDTO.costcategory=data[0].costcategory;
				}

				$scope.editppmasterDTO.ppparentname=data[0].ppparentname;

				$scope.isLoading = false;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		};


		$scope.editppmasters=function(editppmasterDTO){

			console.log("inside Edit PP Masters..");

			//$scope.edituserDTO.usertype=$scope.usertype;

			//call user add service

			if($scope.editppmasterDTO.mastertype.length!=0 && $scope.editppmasterDTO.ppmastername.length!=0 && $scope.editppmasterDTO.editppmastername.length!=0 && $scope.editppmasterDTO.ppparentname.length!=0)
			{
				if($scope.editppmasterDTO.mastertype==='Cost Centre' && $scope.editppmasterDTO.costcategory.length===0)
				{
					$scope.alerts = { type: 'danger', msgtype: 'Error!' ,msg: 'All Fields should be Filled up.'};
					$scope.showSuccessAlert = true;
				}
				else
				{
					$http({
						method : "POST",
						url : "ppmaster/edit",
						data : editppmasterDTO,
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data, status, headers, config){

						if(data[0]==="success")
						{
							$scope.alerts = { type: 'success', msgtype: 'Success!' ,msg: 'PP Masters Updated!'};
							$scope.showSuccessAlert = true;
							$scope.showerror=false;

							$scope.editppmasterDTO.mastertype='';
							$scope.editppmasterDTO.ppmastername='';
							$scope.editppmasterDTO.editppmastername = '';
							$scope.editppmasterDTO.ppparentname = '';
							$scope.editppmasterDTO.costcategory = '';
						}
						else if(data[0]==="failure")
						{
							$scope.alerts = { type: 'danger', msgtype: 'Failure!' ,msg: 'PP Masters not Updated!'};
							$scope.showSuccessAlert = true;
						}
						else
						{
							$scope.alerts = { type: 'danger', msgtype: 'Error!'};
							$scope.errdata=data[0];
							$scope.showerror=true;
						}

					}).error(function(data, status, headers, config){

						$scope.alerts = { type: 'danger', msgtype: 'Failure!' ,msg: 'PP Masters not Updated!'};
						$scope.showSuccessAlert = true;
					});
				}
			}
			else
			{
				$scope.alerts = { type: 'danger', msgtype: 'Error!' ,msg: 'All Fields should be Filled up.'};
				$scope.showSuccessAlert = true;
			}
		};

		$scope.eclear=function(){

			$scope.editppmasterDTO.mastertype='';
			$scope.editppmasterDTO.ppmastername='';
			$scope.editppmasterDTO.editppmastername='';
			$scope.editppmasterDTO.ppparentname='';
			$scope.editppmasterDTO.costcategory='';
		}
	};

	//***********when Import/Export tab is clicked**********************************
	$scope.onimpexpclick=function(){
		console.log("Import/Export clicked....");



		$scope.exportppformat=function(){

			var confirm = $mdDialog.confirm()
			.title('Would you like to Export Excel Format?')
			.ok('OK')
			.cancel('Cancel');

			$mdDialog.show(confirm).then(function() {
				var blob = new Blob([document.getElementById('ppexcelexport').innerHTML], {
					type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
				});
				saveAs(blob, "PPMaster_upload_format.xls");
			});
		};


		$scope.ppmasterimport=function(file){

			var file = $scope.myFile;
			var uploadUrl = "ppmaster/ppmasteruploadfile";

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

	//***********when Report tab is clicked**********************************
	$scope.currentPage=1;
	$scope.itemsPerPage=5;

	$scope.onreportclick=function(){

		console.log("Report clicked....");

		//View PP Master - Report================================================================================================

		$scope.ppmasterdata = []; //declare an empty array
		$scope.isLoading = true;
		$http.get("ppmaster/getppmasterdata").success(function(response){ 
			$scope.isLoading = false;
			$scope.ppmasterdata = response;  //ajax request to fetch data into $scope.data
		});

		$scope.sort = function(keyname){
			$scope.sortKey = keyname;   //set the sortKey to the param passed
			$scope.reverse = !$scope.reverse; //if true make it false and vice versa
		};


		$scope.exportData = function () {

			var blob = new Blob([document.getElementById('exportable').innerHTML], {
				type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
			});
			saveAs(blob, "PPMaster_Report.xls");
		};
	};



});