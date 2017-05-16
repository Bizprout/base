baseApp.controller("UserMappingController", function($scope, $location, $http, $timeout, $q, $filter, $localStorage) {
	
	console.log("UserMappingController loaded.....");
		
	$scope.cmpname=$localStorage.cmpname;

	//*******DTO to store the form values for add when populated*******************************
	$scope.usermappingDTO = {
			"username" : "",
			"companyName":""
	};	

	//**************search option for multiselect***********************************************
	$scope.searchTerm;
	$scope.clearSearchTerm = function() {
		$scope.searchTerm = '';
	};

	//********************select all/ deselect for multiselect options**************************
	$scope.allSelected = false;
	$scope.selectText = "Select All";
	$scope.optionallnone!=$scope.companylist;

	$scope.toggleSeletedcompany = function() {
		$scope.allSelected = !$scope.allSelected;

		$scope.optionallnone = $scope.allSelected;


		//********Change the text************
		if($scope.allSelected){
			$scope.selectText = "Deselect All";
		} else {
			$scope.selectText = "Select All";
		}
	};

	//******************to get Company List for Company dropdown**********************************
	$http({
		method : "GET",
		url : "usermapping/getcompanylist",
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data, status, headers, config){

		//*******options for user names and default selected option********************************
		console.log(data);

		$scope.companylist=data;

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

	//******************to get Users for select User dropdown*************************************
	$http({
		method : "GET",
		url : "user/getusernames",
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data, status, headers, config){

		//*******options for user names and default selected option**********************************

		$scope.usernames=data;

	}).error(function(data, status, headers, config){
		// called asynchronously if an error occurs
		// or server returns response with an error status.
	});

});