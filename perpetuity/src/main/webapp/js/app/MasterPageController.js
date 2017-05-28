baseApp.controller("MasterPageController", function($scope, $location, $http, $localStorage, $mdDialog, $filter) {

	$scope.cmpname=$localStorage.cmpname;
	
	$scope.hideusers=true;
	$scope.hideclients=true;
	$scope.hidecompany=true;
	$scope.hideppmasters=true;
	
	$scope.userbuttondisable=true;
	$scope.clientbuttondisable=true;
	$scope.companybuttondisable=true;
	$scope.ppmasterbuttondisable=true;

	if($localStorage.cmpid===undefined)
	{
		$location.path("/home");
	}

	if($localStorage.userid===undefined)
	{
		$location.path("/");
	}


	if($localStorage.usertype!="PPsuperadmin")
	{	
		$scope.isLoadingmasters=true;
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
				
				if(screenidsmapped.indexOf(userscreen.sid.toString()) !== -1)
				{
					$scope.hideusers=false;
				}
				else
				{
					$scope.hideusers=true;
				}

				if(screenidsmapped.indexOf(clientscreen.sid.toString()) !== -1)
				{
					$scope.hideclients=false;
				}
				else
				{
					$scope.hideclients=true;
				}

				if(screenidsmapped.indexOf(companyscreen.sid.toString()) !== -1)
				{
					$scope.hidecompany=false;
				}
				else
				{
					$scope.hidecompany=true;
				}

				if(screenidsmapped.indexOf(ppscreen.sid.toString()) !== -1)
				{
					$scope.hideppmasters=false;
				}
				else
				{
					$scope.hideppmasters=true;
				}
				
				$scope.isLoadingmasters=false;
				
				$scope.userbuttondisable=false;
				$scope.clientbuttondisable=false;
				$scope.companybuttondisable=false;
				$scope.ppmasterbuttondisable=false;

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
		$scope.isLoadingmasters=true;
		
		$scope.hideusers=false;
		$scope.hideclients=false;
		$scope.hidecompany=false;
		$scope.hideppmasters=false;
		
		$scope.userbuttondisable=false;
		$scope.clientbuttondisable=false;
		$scope.companybuttondisable=false;
		$scope.ppmasterbuttondisable=false;
		
		$scope.isLoadingmasters=false;
	}

});