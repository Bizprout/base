baseApp.controller("AddEditController", function($scope, $location) {
		
	$scope.showadd = true;
	$scope.showedit = false;
	
	$scope.routedirect = function(value) {
		
		console.log("inside routedirect..");
		
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
	
	

});