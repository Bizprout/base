baseApp.controller("LogReportController", function($scope, $location, $http, $timeout, $q, $filter, $localStorage, $mdDialog) {

	$scope.cmpname=$localStorage.cmpname;
	
	if($localStorage.userid===undefined)
	{
		$location.path("/");
	}


	// **********switch flag for success message**********
	$scope.switchBool = function (value) {
		$scope[value] = !$scope[value];
	};

	$scope.currentPage=1;
	$scope.itemsPerPage=5;

	$scope.onlogrepoclick=function(){
		
		$scope.search='';
		
		if($localStorage.cmpid!="")
		{
			$scope.isLoadinglogrepo=true;
			$http({
				method : "POST",
				url : "synclog/getalldatabycmpid",
				data : {"cmpid":$localStorage.cmpid},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config){
				
				$scope.logdata=data;
				
				$scope.isLoadinglogrepo=false;

			}).error(function(data, status, headers, config){

			});
			
			
			$scope.sort = function(keyname){
				$scope.sortKey = keyname;   //set the sortKey to the param passed
				$scope.reverse = !$scope.reverse; //if true make it false and vice versa
			};
			
			$scope.exportData = function () {

				var confirm = $mdDialog.confirm()
				.title('Would you like to Export Table data to Excel?')
				.ok('OK')
				.cancel('Cancel');

				$mdDialog.show(confirm).then(function() {
					var blob = new Blob([document.getElementById('exportableall').innerHTML], {
						type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
					});
					saveAs(blob, "Log_Report.xls");
				});
			};
		}
		else
		{
			$scope.alerts = { type: 'danger' ,msg: 'Company not Selected!'};
			$scope.showSuccessAlert = true;
		}
			
	};

});