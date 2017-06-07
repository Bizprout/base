baseApp.controller("LandingController", function($scope, $location, $http, $rootScope, $mdDialog, $filter, $localStorage) {

	if ("/" === $location.path()) {
		$("#menu").hide();
	} else {
		$("#menu").show();
	}

	$scope.landingcmpId=$localStorage.cmpid;

	$scope.user=$localStorage.user;

	if($localStorage.userid===undefined)
	{
		$location.path("/");
	}

	//last logged on

	$scope.demo = {
			tipDirection: 'bottom'
	};

	$http({
		method : "POST",
		url : "usercounter/getlastlogindatetime",
		data: {"userid":$localStorage.userid},
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(datalogin, status, headers, config){

		if(datalogin!="")
		{
			$scope.lastlogin=datalogin.logindatetime;
		}


	}).error(function(data, status, headers, config){
		// called asynchronously if an error occurs
		// or server returns response with an error status.
	});

	$scope.companyselectOptions = {
			displayText: 'Select Company',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};

	if($localStorage.cmpid===undefined || $localStorage.cmpid===0)
	{
		if($localStorage.usertype==="PPsuperadmin")
		{
			$scope.mastershide=false;
			$scope.mappinghide=true;
			$scope.reportshide=true;
			
			$scope.masterslinkenabled = true;
			$scope.mappinglinkenabled = false;
			$scope.reportslinkenabled = false;
		}
	}
	else
	{		
		if($localStorage.usertype!="PPsuperadmin")
		{
			$scope.mastershide=true;
			$scope.mappinghide=true;
			$scope.reportshide=true;
			
			$scope.isLoadinglandingscreens=true;	
			
			//get the screens mapped for the userid and cmpid
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

				//get all the screens list and ids

				$http({
					method : "GET",
					url : "usermapping/getscreens",
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(datascreens, status, headers, config){

					//*******options for user names and default selected option**********************************

					var userscreen=$filter('filter')(datascreens, {screenName: "User Master"}, true)[0];
					var clientscreen=$filter('filter')(datascreens, {screenName: "Client Master"}, true)[0];
					var companyscreen=$filter('filter')(datascreens, {screenName: "Company Master"}, true)[0];
					var ppscreen=$filter('filter')(datascreens, {screenName: "PP Master"}, true)[0];
					var usermappingscreen=$filter('filter')(datascreens, {screenName: "User Mapping"}, true)[0];
					var ppmappingscreen=$filter('filter')(datascreens, {screenName: "PP Mapping"}, true)[0];
					var reportscreen=$filter('filter')(datascreens, {screenName: "Reports"}, true)[0];

					if(screenidsmapped.indexOf(userscreen.sid.toString()) !== -1 || 
							screenidsmapped.indexOf(clientscreen.sid.toString()) !== -1 ||
							screenidsmapped.indexOf(companyscreen.sid.toString()) !== -1 ||
							screenidsmapped.indexOf(ppscreen.sid.toString()) !== -1)
					{
						$scope.mastershide=false;
						$scope.masterslinkenabled = true;
					}
					else
					{
						$scope.mastershide=true;
						$scope.masterslinkenabled = false;
					}

					if(screenidsmapped.indexOf(usermappingscreen.sid.toString()) !== -1 ||
							screenidsmapped.indexOf(ppmappingscreen.sid.toString()) !== -1)
					{
						$scope.mappinghide=false;
						$scope.mappinglinkenabled = true;
					}
					else
					{
						$scope.mappinghide=true;
						$scope.mappinglinkenabled = false;
					}

					if(screenidsmapped.indexOf(reportscreen.sid.toString()) !== -1)
					{
						$scope.reportshide=false;
						$scope.reportslinkenabled = true;
					}
					else
					{
						$scope.reportshide=true;
						$scope.reportslinkenabled = false;
					}
					
					$scope.isLoadinglandingscreens=false;

				}).error(function(datascreens, status, headers, config){
					// called asynchronously if an error occurs
					// or server returns response with an error status.
				});

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		}
		else
		{
			if($localStorage.cmpid!=0)
			{
				$scope.mastershide=false;
				$scope.mappinghide=false;
				$scope.reportshide=false;
				
				$scope.masterslinkenabled = true;
				$scope.mappinglinkenabled = true;
				$scope.reportslinkenabled = true;
			}

		}
	}


	//******************to get Company List for Company dropdown**********************************

	//check companies mapped for the logged in user

	$scope.companies=[];

	$scope.isLoadinglandcomp=true;

	$http({
		method : "POST",
		url : "usermapping/getCompaniesMapped",
		data: {"userid":$localStorage.userid},
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(datacomp, status, headers, config){

		if(datacomp.length>0)
		{
			angular.forEach(datacomp, function(comp){
				$scope.companies.push(comp.compdto);
			});

			$scope.isLoadinglandcomp=false;

		}
		else if($localStorage.usertype==="PPsuperadmin" && datacomp.length===0)
		{
			$http({
				method : "GET",
				url : "company/getcompanyidname",
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config){

				//*******options for company names********************************
				$scope.companies=data;

				$scope.companies.push({"cmpId":0, "tallyCmpName":"Not Applicable"});
				
				if($localStorage.cmpid===undefined)
				{
					$scope.landingcmpId=0;
					
					$scope.masterslinkenabled = true;
					$scope.mappinglinkenabled = false;
					$scope.reportslinkenabled = false;
				}


				$localStorage.cmpid =$scope.landingcmpId;
				
				$scope.cmp=$filter('filter')($scope.companies, {cmpId:$localStorage.cmpid}, true)[0];
				
				$localStorage.cmpname=$scope.cmp.tallyCmpName;

				$scope.isLoadinglandcomp=false;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		}
		else
		{			
			if ("/home" === $location.path() && datacomp.length===0) {

				var confirm = $mdDialog.alert()
				.parent(angular.element(document.querySelector('#popupContainer')))
				.clickOutsideToClose(true)
				.title('Hi '+$scope.user+'!')
				.textContent('You have No Companies Mapped.')
				.ok('Ok')
				.openFrom('#left')
				.closeTo('#right');

				$scope.isLoadinglandcomp=false;

				$mdDialog.show(confirm).then(function() {
					$localStorage.$reset();
					$location.path("/");
				});	
			}
		}

	}).error(function(data, status, headers, config){
		// called asynchronously if an error occurs
		// or server returns response with an error status.
	});


	$scope.landingcompanychange=function(){
				
		$localStorage.cmpid =$scope.landingcmpId;

		$scope.cmp=$filter('filter')($scope.companies, {cmpId:$localStorage.cmpid}, true)[0];

		$localStorage.cmpname=$scope.cmp.tallyCmpName;


		if($localStorage.usertype!="PPsuperadmin")
		{							
			
			$scope.mastershide=true;
			$scope.mappinghide=true;
			$scope.reportshide=true;
					
			$scope.isLoadinglandingscreens=true;
			
			//get the screens mapped for the userid and cmpid
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

				//get all the screens list and ids

				$http({
					method : "GET",
					url : "usermapping/getscreens",
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(datascreens, status, headers, config){

					//*******options for user names and default selected option**********************************

					var userscreen=$filter('filter')(datascreens, {screenName: "User Master"}, true)[0];
					var clientscreen=$filter('filter')(datascreens, {screenName: "Client Master"}, true)[0];
					var companyscreen=$filter('filter')(datascreens, {screenName: "Company Master"}, true)[0];
					var ppscreen=$filter('filter')(datascreens, {screenName: "PP Master"}, true)[0];
					var usermappingscreen=$filter('filter')(datascreens, {screenName: "User Mapping"}, true)[0];
					var ppmappingscreen=$filter('filter')(datascreens, {screenName: "PP Mapping"}, true)[0];
					var reportscreen=$filter('filter')(datascreens, {screenName: "Reports"}, true)[0];

					if(screenidsmapped.indexOf(userscreen.sid.toString()) !== -1 || 
							screenidsmapped.indexOf(clientscreen.sid.toString()) !== -1 ||
							screenidsmapped.indexOf(companyscreen.sid.toString()) !== -1 ||
							screenidsmapped.indexOf(ppscreen.sid.toString()) !== -1)
					{
						$scope.mastershide=false;
						$scope.masterslinkenabled = true;
					}
					else
					{
						$scope.mastershide=true;
						$scope.masterslinkenabled = false;
					}

					if(screenidsmapped.indexOf(usermappingscreen.sid.toString()) !== -1 ||
							screenidsmapped.indexOf(ppmappingscreen.sid.toString()) !== -1)
					{
						$scope.mappinghide=false;
						$scope.mappinglinkenabled = true;
					}
					else
					{
						$scope.mappinghide=true;
						$scope.mappinglinkenabled = false;
					}

					if(screenidsmapped.indexOf(reportscreen.sid.toString()) !== -1)
					{
						$scope.reportshide=false;
						$scope.reportslinkenabled = true;
					}
					else
					{
						$scope.reportshide=true;
						$scope.reportslinkenabled = false;
					}
					
					$scope.isLoadinglandingscreens=false;

				}).error(function(datascreens, status, headers, config){
					// called asynchronously if an error occurs
					// or server returns response with an error status.
				});

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		}
		else
		{
			if($localStorage.cmpid===0)
			{
				$scope.mappinghide=true;
				$scope.reportshide=true;
				
				$scope.mappinglinkenabled = false;
				$scope.reportslinkenabled = false;
			}
			else
			{
				$scope.mappinghide=false;
				$scope.reportshide=false;
				
				$scope.mappinglinkenabled = true;
				$scope.reportslinkenabled = true;
			}
			$scope.masterslinkenabled = true;

		
		}

	};

});