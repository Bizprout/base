baseApp.controller("UserMappingController", function($scope, $location, $http, $timeout, $q, $filter, $localStorage, $mdDialog) {

	console.log("UserMappingController loaded.....");

	//*******DTO to store the form values for add when populated*******************************

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


	$scope.onmapclick= function(){

		$scope.UserMappingDTO = {
				"userid" 	:0,
				"cmpId" 	:0,
				"screenId"	:"",
				"screenName":""

		};	

		$scope.UserMappingResultDTO={
				"username" : "",
				"companyName":""
		}
		console.log("AJ:User Mapping Loaded");


		//**************search option for multiselect***********************************************
		$scope.searchTerm;
		$scope.clearSearchTerm = function() {
			$scope.searchTerm = '';
		};

		//********************select all/ deselect for multiselect options**************************
		$scope.allSelected = false;
		$scope.selectText = "Select All";
		$scope.optionallnone!=$scope.companylist;

		$scope.toggleSeleted = function() {
			$scope.allSelected = !$scope.allSelected;

			$scope.optionallnone = $scope.allSelected;


			//********Change the text************
			if($scope.allSelected){
				$scope.selectText = "Deselect All";
			} else {
				$scope.selectText = "Select All";
			}
		};

		//******************to get Users for select User dropdown*************************************
		$scope.isLoadinguser=true;

		$http({
			method 	: "GET",
			url 	: "user/getusers",
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config){

			//*******options for user names and default selected option**********************************

			$scope.usernames=data;

			$scope.isLoadinguser=false;

		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});

		//******************to get Company List for Company dropdown**********************************
		$scope.isLoadingcompany=true;
		$http({
			method 	: "GET",
			url		 : "company/getcompanyidname",
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config){

			//*******options for user names and default selected option********************************
			$scope.companylist=data;

			$scope.isLoadingcompany=false;

		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});

		//******Autocomplete dropdown default options for Select User*******************************
		$scope.usernameselectOptions = {
				displayText: 'Select Username',
				emptyListText: 'Oops! The list is empty',
				emptySearchResultText: 'Sorry, couldn\'t find "$0"'
		};
		//******Autocomplete dropdown default options for Select User*******************************
		$scope.cmpNameselectOptions = {
				displayText: 'Select Company Name',
				emptyListText: 'Oops! The list is empty',
				emptySearchResultText: 'Sorry, couldn\'t find "$0"'
		};

		//******************to get Screens List for select Screen dropdown*************************************
		$scope.isLoadingscreens=true;

		$http({
			method : "GET",
			url : "usermapping/getscreens",
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config){

			//*******options for user names and default selected option**********************************

			$scope.screenslist=data;

			$scope.isLoadingscreens=false;

		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
		
		$scope.oncompanychanged=function(){
			
			$scope.isLoadingscreens=true;
			
			$http({
				method : "POST",
				url : "usermapping/getScreensMapped",
				data: {"cmpId":$scope.UserMappingDTO.cmpId, "userid":$scope.UserMappingDTO.userid},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(datascreenids, status, headers, config){

				//*******options for client Names*********
				
				$scope.screensdataids=datascreenids;
									
				if(datascreenids!="")
				{
					var screenidsmapped = datascreenids.screenId.split(',');
					
					$scope.UserMappingDTO.screenId=screenidsmapped;
					
					$scope.isLoadingscreens=false;
				}
				else
				{
					$scope.UserMappingDTO.screenId=[];
					$scope.isLoadingscreens=false;
				}
												
			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		};

		$scope.updateUserMapping=function(userMappingDTO){
			
			
			//delete previous records where cmpid and userid
			
			if($scope.screensdataids!="")
			{
				$http({
					method : "POST",
					url : "usermapping/deleteScreensByCmpidUserid",
					data: {"cmpId":$scope.UserMappingDTO.cmpId, "userid":$scope.UserMappingDTO.userid},
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(delresp, status, headers, config){
					
				}).error(function(data, status, headers, config){
					
				});
			}
			
			var sids=(userMappingDTO.screenId).toString();	

			var a = {"userid": userMappingDTO.userid,"cmpId": userMappingDTO.cmpId,"screenId":sids};
			console.log(a);
			$http({
				method 	: "POST",
				url 	: "usermapping/updateUserMapping",
				data	: a,
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config){

				if(data[0]==='success')
				{
					$scope.alerts = { type: 'success' ,msg: 'User Mapped!'};
					$scope.showSuccessAlert = true;
					$scope.showerror=false;

					$scope.UserMappingDTO.userid=0;
					$scope.UserMappingDTO.cmpId=0;
					$scope.UserMappingDTO.screenId='';


					$scope.onmapclick();
				}
				else if(data[0]==='failure')
				{
					$scope.alerts = { type: 'danger' ,msg: 'User not Mapped!'};
					$scope.showSuccessAlert = true;
				}
				else
				{
					$scope.alerts = { type: 'danger'};
					$scope.errdata=data;
					$scope.showerror=true;
					$scope.showSuccessAlert = false;
				}

				//*******options for user names and default selected option********************************

//				$scope.companylist=data;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		}
		
		$scope.clear=function(){
			$scope.UserMappingDTO.userid=0;
			$scope.UserMappingDTO.cmpId=0;
			$scope.UserMappingDTO.screenId='';
		}
	}

//	*******when Report tab is clicked********************

	$scope.onreportclick=function(){

		console.log("Report Clicked......");

		//View Users - Report================================================================================================

		$scope.currentPage=1;
		$scope.itemsPerPage=5;
		
		var usersdata=[];
		$scope.usersdata = []; //declare an empty array
		
		var screenslist=[];
		$scope.screenslist=[];
		
		$scope.isLoading=true;
		
		$http({
			method : "GET",
			url : "usermapping/getscreens",
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config){

			//*******options for user names and default selected option**********************************

			$scope.screenslist=data;

		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
				
		$http.get("usermapping/getUserScreens").success(function(response){ 
			
			angular.forEach(response, function(obj){
				
				var screenids=[];
				var screennames=[];
											
					var screenids = obj["screenId"].split(',');
					
					angular.forEach(screenids, function(scr){						
						
						angular.forEach($scope.screenslist, function(scrlist){
														
							if(scr.indexOf(scrlist.sid.toString()) !== -1)
							{
								screennames.push(scrlist.screenName);
							}
						});					
						
					});			
					
					usersdata.push({"username":obj["userdto"].username,"companyname":obj["compdto"].tallyCmpName,"screens":screennames.toString()});
			  });
			
			$scope.usersdata=usersdata;
						
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
				saveAs(blob, "User_Access_Report.xls");
			});
			
		};
	};

});