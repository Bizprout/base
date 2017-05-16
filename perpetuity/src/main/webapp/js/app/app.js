var baseApp=angular.module("BaseApp",['ngRoute', 'AxelSoft', 'angularUtils.directives.dirPagination', 'btorfs.multiselect', 'ngMaterial', 'ngMessages', 'mdPickers', 'ngStorage', 'ngMdIcons']);


baseApp.directive('fileModel', ['$parse', function ($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function(){
				scope.$apply(function(){
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
}]);

baseApp.directive('myLink', function() {
	return {
		scope: {
			enabled: '=myLink'
		},
		link: function(scope, element, attrs) {
			element.bind('click', function(event) {
				if(!scope.enabled) {
					event.preventDefault();
				}
			});

		}
	};
});

baseApp.run(function($interval, $localStorage, $location){
	$interval( function(){		  
		$localStorage.$reset();
		$location.path('/');
		// delete all the required localStorage variables if the page is active more than 10 min
	}, 1000*60*10);
	
	
});

//ngRoute for routing - navigating to pages
//AxelSoft plugin for Autocomplete dropdown menu
//angularUtils.directives.dirPagination plugin for report pagination
//btorfs.multiselect plugin for multiselect dropdown