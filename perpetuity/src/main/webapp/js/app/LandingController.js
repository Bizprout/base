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
	

	if($localStorage.cmpid===undefined || $localStorage.cmpid==='')
	{
		if($localStorage.usertype==="PPsuperadmin")
		{
			$scope.masterslinkenabled = true;
		}
		$scope.mappinglinkenabled = false;
		$scope.reportslinkenabled = false;
	}
	else
	{
		if($localStorage.usertype!="PPsuperadmin")
		{	
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
									
					var userscreen=$filter('filter')(datascreens, {screenName: "User Master"})[0];
					var clientscreen=$filter('filter')(datascreens, {screenName: "Client Master"})[0];
					var companyscreen=$filter('filter')(datascreens, {screenName: "Company Master"})[0];
					var ppscreen=$filter('filter')(datascreens, {screenName: "PP Master"})[0];
					var usermappingscreen=$filter('filter')(datascreens, {screenName: "User Mapping"})[0];
					var ppmappingscreen=$filter('filter')(datascreens, {screenName: "PP Mapping"})[0];
					var reportscreen=$filter('filter')(datascreens, {screenName: "Reports"})[0];
					
					if(screenidsmapped.indexOf(userscreen.sid.toString()) !== -1 || 
							screenidsmapped.indexOf(clientscreen.sid.toString()) !== -1 ||
							screenidsmapped.indexOf(companyscreen.sid.toString()) !== -1 ||
							screenidsmapped.indexOf(ppscreen.sid.toString()) !== -1)
					{
						$scope.masterslinkenabled = true;
					}
					else
					{
						$scope.masterslinkenabled = false;
					}

					if(screenidsmapped.indexOf(usermappingscreen.sid.toString()) !== -1 ||
							screenidsmapped.indexOf(ppmappingscreen.sid.toString()) !== -1)
					{
						$scope.mappinglinkenabled = true;
					}
					else
					{
						$scope.mappinglinkenabled = false;
					}

					if(screenidsmapped.indexOf(reportscreen.sid.toString()) !== -1)
					{
						$scope.reportslinkenabled = true;
					}
					else
					{
						$scope.reportslinkenabled = false;
					}

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
			$scope.masterslinkenabled = true;
			$scope.mappinglinkenabled = true;
			$scope.reportslinkenabled = true;
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

			if ("/home" === $location.path() && $localStorage.cmpid===undefined) {
				$mdDialog.show(
						$mdDialog.alert()
						.parent(angular.element(document.querySelector('#popupContainer')))
						.clickOutsideToClose(true)
						.title('Hi '+$scope.user+'!')
						.textContent('Please Select the Company on the top right Corner to Continue...')
						.ariaLabel('Alert Dialog Demo')
						.ok('Got it!')
						.openFrom('#left')
						.closeTo('#right')
				);
			}

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
				
				$scope.isLoadinglandcomp=false;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});

			if ("/home" === $location.path() && $localStorage.cmpid===undefined) {
				$mdDialog.show(
						$mdDialog.alert()
						.parent(angular.element(document.querySelector('#popupContainer')))
						.clickOutsideToClose(true)
						.title('Hi '+$scope.user+'!')
						.textContent('Please Select the Company on the top right Corner to Continue...')
						.ariaLabel('Alert Dialog Demo')
						.ok('Got it!')
						.openFrom('#left')
						.closeTo('#right')
				);
			}
		}
		else
		{			
			if ("/home" === $location.path() && $localStorage.cmpid===undefined) {
				
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

		$rootScope.cmpid=$scope.landingcmpId;

		$localStorage.cmpid =$rootScope.cmpid;

		$scope.cmp=$filter('filter')($scope.companies, {cmpId:$rootScope.cmpid})[0];

		$rootScope.cmpname=$scope.cmp.tallyCmpName;

		$localStorage.cmpname=$rootScope.cmpname;

		
		if($localStorage.usertype!="PPsuperadmin")
		{	
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
									
					var userscreen=$filter('filter')(datascreens, {screenName: "User Master"})[0];
					var clientscreen=$filter('filter')(datascreens, {screenName: "Client Master"})[0];
					var companyscreen=$filter('filter')(datascreens, {screenName: "Company Master"})[0];
					var ppscreen=$filter('filter')(datascreens, {screenName: "PP Master"})[0];
					var usermappingscreen=$filter('filter')(datascreens, {screenName: "User Mapping"})[0];
					var ppmappingscreen=$filter('filter')(datascreens, {screenName: "PP Mapping"})[0];
					var reportscreen=$filter('filter')(datascreens, {screenName: "Reports"})[0];
					
					if(screenidsmapped.indexOf(userscreen.sid.toString()) !== -1 || 
							screenidsmapped.indexOf(clientscreen.sid.toString()) !== -1 ||
							screenidsmapped.indexOf(companyscreen.sid.toString()) !== -1 ||
							screenidsmapped.indexOf(ppscreen.sid.toString()) !== -1)
					{
						$scope.masterslinkenabled = true;
					}
					else
					{
						$scope.masterslinkenabled = false;
					}

					if(screenidsmapped.indexOf(usermappingscreen.sid.toString()) !== -1 ||
							screenidsmapped.indexOf(ppmappingscreen.sid.toString()) !== -1)
					{
						$scope.mappinglinkenabled = true;
					}
					else
					{
						$scope.mappinglinkenabled = false;
					}

					if(screenidsmapped.indexOf(reportscreen.sid.toString()) !== -1)
					{
						$scope.reportslinkenabled = true;
					}
					else
					{
						$scope.reportslinkenabled = false;
					}

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
			$scope.masterslinkenabled = true;
			$scope.mappinglinkenabled = true;
			$scope.reportslinkenabled = true;
		}
		
	};

});