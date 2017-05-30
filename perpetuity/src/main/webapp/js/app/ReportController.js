baseApp.controller("ReportController", function($scope, $location, $http, $timeout, $q, $filter, $mdDialog, $localStorage) {

	$scope.cmpname=$localStorage.cmpname;

	if($localStorage.cmpid===undefined)
	{
		$location.path("/home");
	}

	if($localStorage.userid===undefined)
	{
		$location.path("/");
	}

	$scope.expand = function(vote) {
		vote.show = true;
	}


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



	$scope.onreportclick=function(){

		$http({
			method 	: "GET",
			url 	: "client/getclientidname",
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

		$scope.generatereport=function(){
		};

		$scope.clear=function(){			
			$scope.reportDTO.client='';
			$scope.reportDTO.cmpId='';
			$scope.reportDTO.reportname='';
			$scope.vchAmt = 1234.56;
		}
	};
	$scope.reportDTO = {
			"client" 	: 0,
			"cmpId" 	: $localStorage.cmpid,
			"reportName": "",
			"fromDate"	: "",
			"toDate"	: "",
			"baseGrp"	: "",
			"expanded"	: ""

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
	$scope.DaybookDTO={
			"cmpId"	: $localStorage.cmpid,
			"vchId"	: "",
			"vchType":"",
			"vchNumber":"",
			"vchDate":"",
			"ledgerName":"",
			"vchAmount"	: 0.00,	
			"expanded"	: ""
	}
	$scope.DaybookLedgersDTO={
			"vchId"	: "",
			"ledgerName":"",
			"vchAmount"	: 0.00,
			"vchAmountType"	: "",
			"expanded"	: ""
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
//	based on report name on change selection  
	$scope.selectReport=function(){


		//*******DTO to store the form values for reports**********
		var repName	= $scope.reportDTO.reportName;
		var fromDate = ($filter('date')($scope.reportDTO.fromDate, 'yyyy-MM-dd'));
		var toDate 	 = ($filter('date')($scope.reportDTO.toDate, 'yyyy-MM-dd'));
		var repdata ={"cmpId":$localStorage.cmpid,"reportName":$scope.reportDTO.reportName,"fromDate":fromDate,"toDate":toDate};

		$scope.sort = function(keyname){
			$scope.sortKey = keyname;   //set the sortKey to the param passed
			$scope.reverse = !$scope.reverse; //if true make it false and vice versa
		};


		$scope.isLoadingreports=true;

		$http({
			method 	: "POST",
			url 	: "reports/getdata",
			data	: repdata,			
			headers	: {
				'Content-Type': 'application/json'
			}
		}).success(function(tbdata, status, headers, config){
			
			if(tbdata.length>0)
			{
				$scope.data=tbdata;
			}
			else
			{
				$mdDialog.show(
						$mdDialog.alert()
						.parent(angular.element(document.querySelector('#popupContainer')))
						.clickOutsideToClose(true)
						.textContent('Please Select a Date Range!')
						.ok('Ok!'));
			}

			$scope.isLoadingreports=false;			


		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
	};

	$scope.getDetailedRep = function(voucherId){
		var repName	= $scope.reportDTO.reportName;
		var fromDate = ($filter('date')($scope.reportDTO.fromDate, 'yyyy-MM-dd'));
		var toDate 	 = ($filter('date')($scope.reportDTO.toDate, 'yyyy-MM-dd'));
		var vchdata= voucherId;

		$scope.expanded= !expanded;

		$http({
			method 	: "POST",
			url 	: "reports/getledgerdata",
			data	: "vchdata="+vchdata,
			headers	: {
				'Content-Type': 'application/x-www-form-urlencoded'	
			}
		}).success(function(data, status, headers, config){

			$scope.vchleddata=data;


		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});
	};

	$scope.showAdvanced = function($event,vchdata) {
		var voucherid = vchdata.vchId;
		$http({
			method   : "POST",
			url   : "reports/getledgerdata",
			data  : "vchdata="+voucherid,  
			headers  : {
				'Content-Type': 'application/x-www-form-urlencoded'
			}
		}).success(function(data, status, headers, config){

			$scope.vchleddata=data;
			var parentEl = angular.element(document.body);
			$mdDialog.show({
				parent: parentEl,
				targetEvent: $event,
				templateUrl : "html/reports/VoucherLedgers.html",
				locals: {
					items: $scope.vchleddata
				},

				controller: DialogController
			});
			function DialogController($scope, $mdDialog, items) {
				$scope.items = items;

				$scope.closeDialog = function() {
					$mdDialog.hide();
				}
				$scope.cancel = function() {
					$mdDialog.cancel();
				};
			}


		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});



	};

	$scope.reports = ["Trial Balance","Daybook","Payment Register","Sales Register","Journal Register","Purchase Register"];

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