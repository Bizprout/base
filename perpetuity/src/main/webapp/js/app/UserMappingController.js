baseApp.controller("UserMappingController", function($scope, $location, $http, $timeout, $q, $filter) {
	
	console.log("UserMappingController loaded.....");
	
	//*******DTO to store the form values for add when populated**********
	$scope.usermappingDTO = {
			"username" : "",
			"companyName":""
	};	
	
    $scope.vegetables = ['Corn' ,'Onions' ,'Kale' ,'Arugula' ,'Peas', 'Zucchini'];
    $scope.searchTerm;
    $scope.clearSearchTerm = function() {
      $scope.searchTerm = '';
    };

	
	//to get Company List for Company dropdown
	$http({
		method : "GET",
		url : "usermapping/getcompanylist",
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data, status, headers, config){

		//*******options for user names and default selected option*********
		console.log(data);
		
		$scope.companylist=data;
		
	}).error(function(data, status, headers, config){
		// called asynchronously if an error occurs
		// or server returns response with an error status.
	});
	
	//******Autocomplete dropdown default options for Select User********
	$scope.usernameselectOptions = {
			displayText: 'Select Username',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};
	
	//to get Users for select User dropdown
	$http({
		method : "GET",
		url : "user/getusernames",
		headers : {
			'Content-Type' : 'application/json'
		}
	}).success(function(data, status, headers, config){

		//*******options for user names and default selected option*********
		
		$scope.usernames=data;
		
	}).error(function(data, status, headers, config){
		// called asynchronously if an error occurs
		// or server returns response with an error status.
	});
	
});