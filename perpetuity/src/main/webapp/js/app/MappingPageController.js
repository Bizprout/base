baseApp.controller("MappingPageController", function($scope, $location, $http, $localStorage, $mdDialog, $filter) {
	
	$scope.cmpname=$localStorage.cmpname;
	
	$scope.hideusermapping=true;
	$scope.hideppmapping=true;
	
	$scope.usermapbuttondisable=true;
	$scope.ppmapbuttondisable=true;
	
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
		$scope.isLoadingmapping=true;
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
								
				var usermappingscreen=$filter('filter')(datascreens, {screenName: "User Mapping"})[0];
				var ppmappingscreen=$filter('filter')(datascreens, {screenName: "PP Mapping"})[0];
				
				if(screenidsmapped.indexOf(usermappingscreen.sid.toString()) !== -1)
				{
					$scope.hideusermapping=false;
				}
				else
				{
					$scope.hideusermapping=true;
				}

				if(screenidsmapped.indexOf(ppmappingscreen.sid.toString()) !== -1)
				{
					$scope.hideppmapping=false;
				}
				else
				{
					$scope.hideppmapping=true;
				}
				
				$scope.isLoadingmapping=false;
				
				$scope.usermapbuttondisable=false;
				$scope.ppmapbuttondisable=false;

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
		$scope.isLoadingmapping=true;
		
		$scope.hideusermapping=false;
		$scope.hideppmapping=false;
		
		$scope.usermapbuttondisable=false;
		$scope.ppmapbuttondisable=false;
		
		$scope.isLoadingmapping=false;
	}
	
	

});