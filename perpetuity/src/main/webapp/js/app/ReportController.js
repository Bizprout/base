baseApp.controller("ReportController", function($scope, $location, $http, $timeout, $q, $filter, $localStorage) {

	console.log("ReportController loaded...");
	//TODO angular constants
	
	$scope.cmpname=$localStorage.cmpname;

	// **********switch flag for success message**********
	$scope.switchBool = function (value) {
		$scope[value] = !$scope[value];
	};

	//******Autocomplete dropdown default options for Select client********
	$scope.clientselectOptions = {
			displayText: 'Select Client',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};

	//******Autocomplete dropdown default options for Select company********
	$scope.companyselectOptions = {
			displayText: 'Select Company',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};

	//******Autocomplete dropdown default options for Select company********
	$scope.reportselectOptions = {
			displayText: 'Select Report',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};

	//*******DTO to store the form values for reports**********
	$scope.reportDTO = {
			"client" : 0,
			"cmpId" : 0,
			"reportname":""
	};	

	$scope.onreportclick=function(){

		console.log("Generate Reports Clicked..");

		$http({
			method : "GET",
			url : "client/getclientidname",
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(dataclientnames, status, headers, config){

			//*******options for client Names*********

			$scope.clientnames=dataclientnames;

		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});

		$scope.clientchanged=function(){

			$http({
				method : "POST",
				url : "pptallymapping/getcompanyidname",
				data: {"clientId":$scope.reportDTO.client},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(datacompnames, status, headers, config){

				//*******options for client Names*********

				$scope.companies=datacompnames;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		};
		
		$scope.generatereport=function(){
			
			console.log("inside generate reports");			
		};
		
		$scope.clear=function(){			
			$scope.reportDTO.client='';
			$scope.reportDTO.cmpId='';
			$scope.reportDTO.reportname='';
		}
	};

});