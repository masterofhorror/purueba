(function() {
	'use strict';

	angular.module('SagirApp.factories').factory('httpInterceptorSession', function($q, $injector) {
		return {
			'request' : function(config) {
				if(config.url !== './webresources/sesion' && config.url.indexOf('./js/') === -1) {
					var http = $injector.get('$http');
					
					http.get('./webresources/sesion').success(function(data, status, headers, config) {
						if(data === false) {
							window.location.href = './login.html#/login';
						}
					}).error(function(data, status, headers, config) {});
				}
				return config || $q.when(config);
			}
		}

	});

})();