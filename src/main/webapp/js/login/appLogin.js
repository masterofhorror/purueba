'use strict';

	var module = angular.module('SagirApp', [
	    'ngRoute',
	    'SagirApp.controllers',
	    'SagirApp.values'        
	]);
	
	module.config(['$routeProvider', function ($routeProvider) {
		  $routeProvider.when('/login', {templateUrl: 'content/login/login.html', controller: 'loginCtrl'});
		  
		  $routeProvider.otherwise({redirectTo: '/login'}); 
	}]);

