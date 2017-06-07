baseApp.config(function($mdDateLocaleProvider) {
	$mdDateLocaleProvider.formatDate = function(date) {
		return moment(date).format('DD-MMM-YYYY');
	};
});
baseApp.controller("CompanyMasterController", function($scope, $location, $http, $timeout, $q, $filter, $mdpTimePicker, $localStorage, $mdDialog) {

	$scope.cmpname=$localStorage.cmpname;

	if($localStorage.usertype!="PPsuperadmin" && ($localStorage.cmpid===undefined))
	{
		$location.path("/home");
	}
	else if($localStorage.usertype==="PPsuperadmin" && ($localStorage.cmpid===undefined || $localStorage.cmpid!=0))
	{
		$location.path("/home");
	}

	if($localStorage.userid===undefined)
	{
		$location.path("/");
	}
	
	//get the screens mapped for the userid and cmpid

	if($localStorage.usertype!="PPsuperadmin")
	{
		$http({
			method : "POST",
			url : "usermapping/getScreensMapped",
			data: {"cmpId":$localStorage.cmpid, "userid":$localStorage.userid},
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(datascreenids, status, headers, config){

			//*******options for client Names*********

			if(datascreenids!="")
			{
				var screenidsmapped = datascreenids.screenId.split(',');
			}
			else
			{
				$location.path("/home");
			}

			//get all the screens list and ids

			$http({
				method : "GET",
				url : "usermapping/getscreens",
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(datascreens, status, headers, config){

				var companyscreen=$filter('filter')(datascreens, {screenName: "Company Master"}, true)[0];


				if(screenidsmapped.indexOf(companyscreen.sid.toString()) === -1)
				{
					$location.path("/home");
				}

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});	
	}

	if($localStorage.usertype!="PPsuperadmin")
	{
		$scope.compdisable=true;
		$scope.clientdisable=true;
	}
	else
	{
		$scope.compdisable=false;
		$scope.clientdisable=false;
	}

	// **********switch flag for success message**********
	$scope.switchBool = function (value) {
		$scope[value] = !$scope[value];
	};

	//*******DTO to store the form values for Company masters**********
	$scope.companyDTO = {
			"cmpId" : 0,
			"clientId" : 0,
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
		$scope.isLoadingcompany=true;

		$http({
			method : "GET",
			url : "company/getcompanyidnameall",
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config){

			//*******options for company names********************************
			$scope.companylist=data;

			$scope.companyDTO.cmpId=$localStorage.cmpid;

			$scope.isLoadingcompany=false;

			$http({
				method : "POST",
				url : "company/getclientstatus",
				data : {"cmpId":$localStorage.cmpid},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config){

				//*******options for company names********************************

				$scope.clientid=data.clientId;
				$scope.status=data.status;

				if($scope.clientid===0 && $scope.status==="Inactive")
				{
					$scope.isLoadingclient=true;
					$scope.isLoadingbookfrom=true;
					$scope.isLoadingtallyguid=true;
					$scope.isLoadingsyncdate=true;
					$scope.isLoadinguploadtimer=true;
					$scope.isLoadingdownloadtimer=true;
					$scope.isLoadingnoofretrials=true;
					$scope.isLoadingcompanystatus=true;

					$http({
						method : "GET",
						url : "client/getclientidname",
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(dataclientnames, status, headers, config){

						//*******options for user names and default selected option*********

						$scope.clientnames=dataclientnames;

						$scope.tallyGUID=data.tallyGUID;
						$scope.companyDTO.appFromDate=new Date(data.appFromDate);
						$scope.companyDTO.uploadTimer=new Date(data.appFromDate+" "+data.uploadTimer);
						$scope.companyDTO.dnldTimer=new Date(data.appFromDate+" "+data.dnldTimer);
						$scope.companyDTO.maxRetrial=data.maxRetrial;
						$scope.companyDTO.status=data.status;
						$scope.bookfrom=$filter('date')(data.bookfrom, 'dd-MMM-yyyy');

						$scope.isLoadingclient=false;
						$scope.isLoadingbookfrom=false;
						$scope.isLoadingtallyguid=false;
						$scope.isLoadingsyncdate=false;
						$scope.isLoadinguploadtimer=false;
						$scope.isLoadingdownloadtimer=false;
						$scope.isLoadingnoofretrials=false;
						$scope.isLoadingcompanystatus=false;


						if(data.appFromDate===null){$scope.companyDTO.appFromDate='';}
						if(data.uploadTimer===null){$scope.companyDTO.uploadTimer='';}
						if(data.dnldTimer===null){$scope.companyDTO.dnldTimer='';}

					}).error(function(data, status, headers, config){
						// called asynchronously if an error occurs
						// or server returns response with an error status.
					});
				}
				else
				{	
					$scope.isLoadingclient=true;
					$scope.isLoadingbookfrom=true;
					$scope.isLoadingtallyguid=true;
					$scope.isLoadingsyncdate=true;
					$scope.isLoadinguploadtimer=true;
					$scope.isLoadingdownloadtimer=true;
					$scope.isLoadingnoofretrials=true;
					$scope.isLoadingcompanystatus=true;

					$http({
						method : "GET",
						url : "client/getclientidname",
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(dataclientnames, status, headers, config){

						$scope.clientnames=dataclientnames;
						$scope.companyDTO.clientId=data.clientId;
						$scope.tallyGUID=data.tallyGUID;
						$scope.companyDTO.appFromDate=new Date(data.appFromDate);
						$scope.companyDTO.uploadTimer=new Date(data.appFromDate+" "+data.uploadTimer);
						$scope.companyDTO.dnldTimer=new Date(data.appFromDate+" "+data.dnldTimer);
						$scope.companyDTO.maxRetrial=data.maxRetrial;
						$scope.companyDTO.status=data.status;
						$scope.bookfrom=$filter('date')(data.bookfrom, 'dd-MMM-yyyy');

						$scope.isLoadingclient=false;
						$scope.isLoadingbookfrom=false;
						$scope.isLoadingtallyguid=false;
						$scope.isLoadingsyncdate=false;
						$scope.isLoadinguploadtimer=false;
						$scope.isLoadingdownloadtimer=false;
						$scope.isLoadingnoofretrials=false;
						$scope.isLoadingcompanystatus=false;

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

		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});	


		$scope.oncompanychange=function(){

			$http({
				method : "POST",
				url : "company/getclientstatus",
				data : {"cmpId":$scope.companyDTO.cmpId},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config){

				//*******options for company names********************************

				$scope.clientid=data.clientId;
				$scope.status=data.status;

				if($scope.clientid===0 && $scope.status==="Inactive")
				{
					$scope.isLoadingclient=true;
					$scope.isLoadingbookfrom=true;
					$scope.isLoadingtallyguid=true;
					$scope.isLoadingsyncdate=true;
					$scope.isLoadinguploadtimer=true;
					$scope.isLoadingdownloadtimer=true;
					$scope.isLoadingnoofretrials=true;
					$scope.isLoadingcompanystatus=true;

					$http({
						method : "GET",
						url : "client/getclientidname",
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(dataclientnames, status, headers, config){

						//*******options for user names and default selected option*********

						$scope.clientnames=dataclientnames;

						$scope.tallyGUID=data.tallyGUID;
						$scope.companyDTO.appFromDate=new Date(data.appFromDate);
						$scope.companyDTO.uploadTimer=new Date(data.appFromDate+" "+data.uploadTimer);
						$scope.companyDTO.dnldTimer=new Date(data.appFromDate+" "+data.dnldTimer);
						$scope.companyDTO.maxRetrial=data.maxRetrial;
						$scope.companyDTO.status=data.status;
						$scope.bookfrom=$filter('date')(data.bookfrom, 'dd-MMM-yyyy');

						$scope.isLoadingclient=false;
						$scope.isLoadingbookfrom=false;
						$scope.isLoadingtallyguid=false;
						$scope.isLoadingsyncdate=false;
						$scope.isLoadinguploadtimer=false;
						$scope.isLoadingdownloadtimer=false;
						$scope.isLoadingnoofretrials=false;
						$scope.isLoadingcompanystatus=false;


						if(data.appFromDate===null){$scope.companyDTO.appFromDate='';}
						if(data.uploadTimer===null){$scope.companyDTO.uploadTimer='';}
						if(data.dnldTimer===null){$scope.companyDTO.dnldTimer='';}

					}).error(function(data, status, headers, config){
						// called asynchronously if an error occurs
						// or server returns response with an error status.
					});
				}
				else
				{	
					$scope.isLoadingclient=true;
					$scope.isLoadingbookfrom=true;
					$scope.isLoadingtallyguid=true;
					$scope.isLoadingsyncdate=true;
					$scope.isLoadinguploadtimer=true;
					$scope.isLoadingdownloadtimer=true;
					$scope.isLoadingnoofretrials=true;
					$scope.isLoadingcompanystatus=true;

					$http({
						method : "GET",
						url : "client/getclientidname",
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(dataclientnames, status, headers, config){

						$scope.clientnames=dataclientnames;
						$scope.companyDTO.clientId=data.clientId;
						$scope.tallyGUID=data.tallyGUID;
						$scope.companyDTO.appFromDate=new Date(data.appFromDate);
						$scope.companyDTO.uploadTimer=new Date(data.appFromDate+" "+data.uploadTimer);
						$scope.companyDTO.dnldTimer=new Date(data.appFromDate+" "+data.dnldTimer);
						$scope.companyDTO.maxRetrial=data.maxRetrial;
						$scope.companyDTO.status=data.status;
						$scope.bookfrom=$filter('date')(data.bookfrom, 'dd-MMM-yyyy');

						$scope.isLoadingclient=false;
						$scope.isLoadingbookfrom=false;
						$scope.isLoadingtallyguid=false;
						$scope.isLoadingsyncdate=false;
						$scope.isLoadinguploadtimer=false;
						$scope.isLoadingdownloadtimer=false;
						$scope.isLoadingnoofretrials=false;
						$scope.isLoadingcompanystatus=false;

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
			$scope.companyDTO.status=$scope.companyDTO.status;

			if($scope.companyDTO.appFromDate!=null ||
					$scope.companyDTO.uploadTimer!=null || 
					$scope.companyDTO.dnldTimer!=null ||
					$scope.companyDTO.maxRetrial!=null ||
					$scope.companyDTO.status.length!=0) 
			{
				
				if($scope.companyDTO.cmpId===0 && $localStorage.usertype==="PPsuperadmin")
				{
					$scope.alerts = { type: 'danger', msg: 'Company Selection is Required!'};
					$scope.showSuccessAlert = true;
				}
				else if($scope.companyDTO.appFromDate===null)
				{
					$scope.alerts = { type: 'danger', msg: 'Sync Date From is Required!'};
					$scope.showSuccessAlert = true;
				}
				else if((Date.parse($scope.bookfrom)) > (Date.parse($filter('date')($scope.companyDTO.appFromDate, 'dd-MMM-yyyy'))))
				{
					$scope.alerts = { type: 'danger', msg: 'Sync From Date should be greater than or equal to Books From Date!'};
					$scope.showSuccessAlert = true;
				}
				else if($scope.companyDTO.uploadTimer===null)
				{
					$scope.alerts = { type: 'danger', msg: 'Upload Timer is Required!'};
					$scope.showSuccessAlert = true;
				}
				else if($scope.companyDTO.dnldTimer===null)
				{
					$scope.alerts = { type: 'danger', msg: 'Download Timer is Required!'};
					$scope.showSuccessAlert = true;
				}
				else if($scope.companyDTO.maxRetrial===null)
				{
					$scope.alerts = { type: 'danger', msg: 'No. of Retrials is Required!'};
					$scope.showSuccessAlert = true;
				}
				else if($scope.companyDTO.status.length===0)
				{
					$scope.alerts = { type: 'danger', msg: 'Company Status is Required!'};
					$scope.showSuccessAlert = true;
				}
				else
				{
					if($scope.companyDTO.status==="Inactive" && $localStorage.usertype==="PPAdmin")
					{
						var confirm = $mdDialog.confirm()
						.title('Are you sure you want to make this Company Inactive?')
						.textContent('Note: You will be Redirected to the Home Page.')
						.ok('OK')
						.cancel('Cancel');

						$mdDialog.show(confirm).then(function() {

							$scope.isLoadingresponse=true;
							
							$http({
								method : "POST",
								url : "company/update",
								data : companyDTO,
								headers : {
									'Content-Type' : 'application/json'
								}
							}).success(function(data, status, headers, config){

								if(data[0]==="success")
								{
									$scope.alerts = { type: 'success', msg: 'Company Updated!'};
									$scope.showSuccessAlert = true;
									$scope.showerror=false;
									
									$scope.isLoadingresponse=false;

									$localStorage.cmpid='';
									$localStorage.cmpname='';
									$location.path('/home');
								}
								else if(data[0]==="failure")
								{
									$scope.alerts = { type: 'danger', msg: 'Company not Updated!'};
									$scope.showSuccessAlert = true;
									$scope.showerror=false;
									
									$scope.isLoadingresponse=false;
								}
								else
								{
									if(data.length>0)
									{
										$scope.alerts = { type: 'danger'};
										$scope.errdata=data[0];
										$scope.showerror=true;
										$scope.showSuccessAlert = false;
										
										$scope.isLoadingresponse=false;
									}
									else
									{
										$scope.alerts = { type: 'danger', msg: 'Company not Updated!'};
										$scope.showSuccessAlert = true;
										$scope.showerror=false;
										
										$scope.isLoadingresponse=false;
									}									
								}

							}).error(function(data, status, headers, config){

								$scope.alerts = { type: 'danger', msg: 'Company not Updated!'};
								$scope.showSuccessAlert = true;
								
								$scope.isLoadingresponse=false;
							});
						});
					}
					else
					{
						if($scope.companyDTO.status==="Inactive")
						{
							var confirm = $mdDialog.confirm()
							.title('Are you sure you want to make this Company Inactive?')
							.ok('OK')
							.cancel('Cancel');

							$mdDialog.show(confirm).then(function() {
								
								$scope.isLoadingresponse=true;
								
								$http({
									method : "POST",
									url : "company/update",
									data : companyDTO,
									headers : {
										'Content-Type' : 'application/json'
									}
								}).success(function(data, status, headers, config){

									if(data[0]==="success")
									{
										$scope.alerts = { type: 'success', msg: 'Company Updated!'};
										$scope.showSuccessAlert = true;
										$scope.showerror=false;
										
										$scope.isLoadingresponse=false;

										$scope.oncreateeditclick();
									}
									else if(data[0]==="failure")
									{
										$scope.alerts = { type: 'danger', msg: 'Company not Updated!'};
										$scope.showSuccessAlert = true;
										$scope.showerror=false;
									}
									else
									{
										if(data.length>0)
										{
											$scope.alerts = { type: 'danger'};
											$scope.errdata=data[0];
											$scope.showerror=true;
											$scope.showSuccessAlert = false;
											
											$scope.isLoadingresponse=false;
										}
										else
										{
											$scope.alerts = { type: 'danger', msg: 'Company not Updated!'};
											$scope.showSuccessAlert = true;
											$scope.showerror=false;
											
											$scope.isLoadingresponse=false;
										}
									}

								}).error(function(data, status, headers, config){

									$scope.alerts = { type: 'danger', msg: 'Company not Updated!'};
									$scope.showSuccessAlert = true;
									
									$scope.isLoadingresponse=false;
								});
							});
						}
						else
						{
							$scope.isLoadingresponse=true;
							
							$http({
								method : "POST",
								url : "company/update",
								data : companyDTO,
								headers : {
									'Content-Type' : 'application/json'
								}
							}).success(function(data, status, headers, config){

								if(data[0]==="success")
								{
									$scope.alerts = { type: 'success', msg: 'Company Updated!'};
									$scope.showSuccessAlert = true;
									$scope.showerror=false;
									
									$scope.isLoadingresponse=false;

									$scope.oncreateeditclick();
								}
								else if(data[0]==="failure")
								{
									$scope.alerts = { type: 'danger', msg: 'Company not Updated!'};
									$scope.showSuccessAlert = true;
									$scope.showerror=false;
									
									$scope.isLoadingresponse=false;
								}
								else
								{
									if(data.length>0)
									{
										$scope.alerts = { type: 'danger'};
										$scope.errdata=data[0];
										$scope.showerror=true;
										$scope.showSuccessAlert = false;
										
										$scope.isLoadingresponse=false;
									}
									else
									{
										$scope.alerts = { type: 'danger', msg: 'Company not Updated!'};
										$scope.showSuccessAlert = true;
										$scope.showerror=false;
										
										$scope.isLoadingresponse=false;
									}
								}

							}).error(function(data, status, headers, config){

								$scope.alerts = { type: 'danger', msg: 'Company not Updated!'};
								$scope.showSuccessAlert = true;
								
								$scope.isLoadingresponse=false;
							});
						}


					}					
				}
			}
			else
			{
				$scope.alerts = { type: 'danger', msg: 'All Mandatory Fields should be Filled up!'};
				$scope.showSuccessAlert = true;
			}
		};
	};

	$scope.onreportclick=function(){

		$scope.currentPage=1;
		$scope.itemsPerPage=5;

		$scope.search='';

		//View Company - Report================================================================================================

		$scope.company = []; //declare an empty array
		$scope.isLoading=true;
		
		if($localStorage.cmpid===0)
		{
			$http({
				method : "GET",
				url : "company/getcompanydataall",
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(reportdata, status, headers, config){

				$scope.company = reportdata;  //ajax request to fetch data into $scope.data
				$scope.isLoading=false;

			}).error(function(data, status, headers, config){

			});		

		}
		else
		{
			$http({
				method : "POST",
				url : "company/getcompanydata",
				data : {"cmpId":$localStorage.cmpid},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(reportdata, status, headers, config){

				$scope.company = reportdata;  //ajax request to fetch data into $scope.data
				$scope.isLoading=false;

			}).error(function(data, status, headers, config){

			});	
		}	

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
				saveAs(blob, "Company_Master_Report.xls");
			});
		};
	};
});