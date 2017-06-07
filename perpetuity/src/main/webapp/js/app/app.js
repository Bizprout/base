var baseApp=angular.module("BaseApp",['ngRoute', 'ngAnimate', 'AxelSoft', 'angularUtils.directives.dirPagination', 'btorfs.multiselect', 'ngMaterial', 'ngMessages', 'mdPickers', 'ngStorage', 'ngMdIcons','http-auth-interceptor']);

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


baseApp.run(function($rootScope, $mdDialog, $localStorage, $location,$http, USER_ROLES, $q, $timeout,
		AuthSharedService) {
	var lastDigestRun = new Date();
	var intervalId = setInterval(function () {
		var now = Date.now();
				
		if ((now - lastDigestRun > 10 * 60 * 1000) && "/" != $location.path()) {
			$localStorage.$reset();
			$location.path('/');

			$mdDialog.show(
					$mdDialog.alert()
					.clickOutsideToClose(true)
					.title('Session Timed Out')
					.textContent('Your Session has been Timed out. Please Login Again!')
					.ok('Ok')
			);
		}
	}, 1000);

	$rootScope.$watch(function() {
		lastDigestRun = new Date();
	});

	$rootScope.$on('event:auth-forbidden', function(rejection) {
		$rootScope.$evalAsync(function() {
			$location.path('/login').replace();
		});
	});
	$rootScope.$on('event:auth-loginRequired', function(event, data) {
		if ($rootScope.loadingAccount && data.status !== 401) {
			$rootScope.requestedUrl = $location.path();
			$location.path('/');
		} else {			
			$rootScope.authenticated = false;
			$rootScope.loadingAccount = false;
			$location.path('/');
		};		
	});
});

baseApp.constant('USER_ROLES', {
	ADMIN : 'PPAdmin',
	SUPER_ADMIN : 'PPsuperadmin'
});

baseApp.factory('AuthSharedService', function( $rootScope, $http,$localStorage,
		authService) {

	return {
		// other functions ...
		getAccount : function() {
			$rootScope.loadingAccount = true;
			$http.get('security/account').then(function(response) {
				authService.loginConfirmed(response.data);
			});
		},
		isAuthorized : function(authorizedRoles) {
			if (!angular.isArray(authorizedRoles)) {
				authorizedRoles = [ authorizedRoles ];
			}
			var isAuthorized = false;

			angular.forEach(authorizedRoles, function(authorizedRole) {						
				var authorized = (authorizedRole==='ROLE_PPAdmin');
				if (authorized) {
					isAuthorized = true;
				}
			});
			return isAuthorized;
		}
	};
});
