baseApp.controller("AddEditController", function($scope, $location, $http) {

	$scope.showadd = true;
	$scope.showedit = false;
	

	$scope.routedirect = function(value) {

		if('Add'===value)
		{
			$scope.showedit = false;
			$scope.showadd = true;
		}
		else
		{
			$scope.showedit = true;
			$scope.showadd = false;
		}
	};

	$(function(){
		$('.example1').jqCron();
	});
	
	


	//$scope.usertypes = [{name:"User Type", id:""}, {name:"PP Admin", id:0}, {name:"PP SuperAdmin", id:1}];

	//$scope.usertypes = ["User Type","PP Admin","PP SuperAdmin"];
	
	//$scope.usertypemodel = $scope.usertypes[0];
	/*$scope.usertype = function(){
	};*/

	$
	$scope.clientnames = [
	                      "Client Name",    
	                      "Bizprout Corporate Solutions Pvt. Ltd.",
	                      "Tiramisu",
	                      "Cenduit"
	                      ];
	$scope.clientmodel = $scope.clientnames[0];    	

	$scope.masteritems = [
	                      "Select Master Type",    
	                      "Ledgers",
	                      "Groups",
	                      "Cost Categories",
	                      "Cost Centers",
	                      "Voucher Types"
	                      ];
	$scope.mastermodel = $scope.masteritems[0];

	$scope.ppitems = [
	                  "Select PP Parent Name",    
	                  "Direct Expenses",
	                  "InDirect Expenses",
	                  "Direct Incomes",
	                  "InDirect Incomes",
	                  "Sales Account",
	                  "Purchase Account"
	                  ];
	$scope.ppmodel = $scope.ppitems[0];

	$scope.companylist = [
	                      "Select Company",    
	                      "Bizprout_1",
	                      "Tiramisu_4",
	                      "Perpetuity_587"
	                      ];
	$scope.companymodel = $scope.companylist[0];
	
	$scope.mapppitems = [
	                      "Map to PP Master",    
	                      "Bizprout_1",
	                      "Tiramisu_4",
	                      "Perpetuity_587"
	                      ];
	$scope.mapppmodel = $scope.mapppitems[0];
	
	$scope.reportitems = [
	                      "Select Report",    
	                      "Bizprout",
	                      "Tiramisu",
	                      "Perpetuity"
	                      ];
	$scope.reportmodel = $scope.reportitems[0];

	$('#company_select').selectpicker({        
	});

	$('#screen_select').selectpicker({        
	});
	
	$('#tally_master_name').selectpicker({        
	});
	
	


});