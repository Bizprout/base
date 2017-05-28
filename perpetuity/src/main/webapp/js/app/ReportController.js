baseApp.controller("ReportController", function($scope, $location, $http, $timeout, $q, $filter,$mdDialog, $localStorage) {

	console.log("ReportController loaded...");	
	
	$scope.cmpname=$localStorage.cmpname;

	if($localStorage.cmpid===undefined)
	{
		$location.path("/home");
	}

	if($localStorage.userid===undefined)
	{
		$location.path("/");
	}
	
	//TODO angular constants

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
			displayText: 'Select Report Name',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};


	$scope.reportDTO = {
			"cmpId" 	: $localStorage.cmpid,
			"reportName": "",
			"fromDate"	: "",
			"toDate"	: "",
			"baseGrp"	: ""
				
	};	

	$scope.TrialBalanceDTO = [{
			"cmpId" 	: $localStorage.cmpid,
			"name"		: "",
			"baseGrp"	: "",
			"category"	: "",
			"opDrAmt"	: 0.00,
			"opCrAmt"	: 0.00,
			"vchDrAmt"	: 0.00,
			"vchCrAmt"	: 0.00
			
	}];
	$scope.VouchersDTO= {
			"cmpId"			: $localStorage.cmpid, 
		    "vchId"			: "",
		    "vchDate"		: "",
		    "vchTypeId"		: 0,
		    "vchNumber"		: "",
		    "partyLedger"	: "",
		    "isOptional"	: 0,
		    "isCancelled"	: 0,
		    "isDeleted"		: 0,
		    "authBy"		: "",
		    "vchAmt"		: 123.45,
		    "vchNarration"	: "",
		    "syncStatus"	: 0
			
			
	}
	
	$scope.VouchersDTO= {
			"cmpId"			: $localStorage.cmpid, 
		    "vchId"			: "",
		    "vchDate"		: "",
		    "vchTypeId"		: 0,
		    "vchNumber"		: "",
		    "partyLedger"	: "",
		    "isOptional"	: 0,
		    "isCancelled"	: 0,
		    "isDeleted"		: 0,
		    "authBy"		: "",
		    "vchAmt"		: 0.00,
		    "vchNarration"	: "",
		    "syncStatus"	: 0
			
			
	}
	
	$scope.reportDTO.fromDate = new Date();
	$scope.reportDTO.toDate = new Date();
	$scope.currentPage=1;
	$scope.itemsPerPage=15;
// based on report name on change selection  
	$scope.selectReport=function(){
		
		$scope.isLoadingreports=true;
		
		console.log("inside report selection...");
		//*******DTO to store the form values for reports**********
		var repName	= $scope.reportDTO.reportName;
		var fromDate = ($filter('date')($scope.reportDTO.fromDate, 'yyyy-MM-dd'));
		var toDate 	 = ($filter('date')($scope.reportDTO.toDate, 'yyyy-MM-dd'));
		var repdata ={"cmpId":$localStorage.cmpid,"reportName":$scope.reportDTO.reportName,"fromDate":fromDate,"toDate":toDate};
		
		$scope.sort = function(keyname){
		      $scope.sortKey = keyname;   //set the sortKey to the param passed
		      $scope.reverse = !$scope.reverse; //if true make it false and vice versa
		    };
						   
			
		$http({
			method 	: "POST",
			url 	: "reports/getdata",
			data	: repdata,
			headers	: {
						'Content-Type': 'application/json'
					   }
		}).success(function(tbdata, status, headers, config){

			$scope.data=tbdata;			
			
			$scope.isLoadingreports=false;

		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
	};
	
	$scope.reports = ["Trial Balance","Daybook","Payment Register","Sales Register"];
	
	$scope.exportData = function () {
		var repName	= $scope.reportDTO.reportName;

		var confirm = $mdDialog.confirm()
		.title('Would you like to Export Table data to Excel?')
		.ok('OK')
		.cancel('Cancel');

		$mdDialog.show(confirm).then(function() {
			
				var blob = new Blob([document.getElementById('exportableall').innerHTML], {
					type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
				});
				saveAs(blob, repName+".xls");
		});


	};	
	
});