baseApp.controller("LandingController", function($scope, $location, $http, $rootScope, $mdDialog, $filter, $localStorage) {
	
	console.log("LandingController loaded..");
	
	if ("/" === $location.path()) {
		$("#menu").hide();
	} else {
		$("#menu").show();
	}
	
	$scope.landingcmpId=$localStorage.cmpid;

	$scope.user=$localStorage.user;
	
	//last logged on

	$http({
		method : "POST",
		url : "usercounter/getlastlogindatetime",
		data: {"userid":$localStorage.userid},
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(datalogin, status, headers, config){

		$scope.lastlogin=datalogin[0].logindatetime;

	}).error(function(data, status, headers, config){
		// called asynchronously if an error occurs
		// or server returns response with an error status.
	});
	
	$scope.companyselectOptions = {
			displayText: 'Select Company',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};
	
	if($scope.landingcmpId!=null)
	{
		$scope.linkEnabled = true;
	}
	
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

	//******************to get Company List for Company dropdown**********************************
	$http({
		method : "GET",
		url : "company/getcompanyidname",
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data, status, headers, config){

		//*******options for company names********************************
		$scope.companies=data;

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

		$scope.linkEnabled = true;
	};
	
});