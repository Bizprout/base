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

baseApp.run(function($rootScope, $localStorage, $location) {
	var lastDigestRun = new Date();
	setInterval(function () {
		var now = Date.now();
		if (now - lastDigestRun > 10 * 60 * 1000) {
			$localStorage.$reset();
			$location.path('/');
		}
	}, 1000);

	$rootScope.$watch(function() {
		lastDigestRun = new Date();
	});
});

//ngRoute for routing - navigating to pages
//AxelSoft plugin for Autocomplete dropdown menu
//angularUtils.directives.dirPagination plugin for report pagination
//btorfs.multiselect plugin for multiselect dropdown