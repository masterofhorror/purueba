'use strict';

// Declare app level module which depends on filters, and services
var module = angular.module('SagirApp', [
    'ngRoute',
    'SagirApp.controllers',
    'SagirApp.directives',
    'SagirApp.values',
    'SagirApp.factories',
    'angularUtils.directives.dirPagination'
]);

module.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/home', {templateUrl: 'content/home.html', controller: 'homeCtrl'});
        $routeProvider.when('/logout', {templateUrl: 'content/login/login.html', controller: 'logoutCtrl'});
        $routeProvider.when('/modificarPerfil', {templateUrl: 'content/modificarPerfil.html', controller: 'modificarPerfilCtrl'});

        $routeProvider.when('/reporteRNA', {templateUrl: 'content/reporteRNA.html', controller: 'reporteRNACtrl'});
        $routeProvider.when('/reporteRNC', {templateUrl: 'content/reporteRNC.html', controller: 'reporteRNCCtrl'});
        $routeProvider.when('/cambioPropietario', {templateUrl: 'content/cambioPropietario.html', controller: 'controllerCambioPropietario'});
        //Se comentarea esta opción ya que queda incluida en la opción de actualización información.
        //$routeProvider.when('/cambioFormatoPlaca', {templateUrl: 'content/cambioFormatoPlaca.html', controller: 'controllerCambioFormatoPlaca'});
        $routeProvider.when('/cambioResidencia', {templateUrl: 'content/cambioResidencia.html', controller: 'controllerCambioResidencia'});
        //Se comentarea esta opción ya que queda incluida en la opción de actualización información.
        //$routeProvider.when('/cambioEstado', {templateUrl: 'content/cambioEstado.html', controller: 'controllerCambioEstado'});
        $routeProvider.when('/modificacionesRealizadas', {templateUrl: 'content/modificacionesRealizadas.html', controller: 'modificacionesRealizadasCtrl'});
        $routeProvider.when('/marcaVehiculoMalCargado', {templateUrl: 'content/marcaVehiculoMalCargado.html', controller: 'controllerMarcarVehiculo'});
        $routeProvider.when('/cargaRepotenciacion', {templateUrl: 'content/cargaRepotenciacion.html', controller: 'controllerCargaRepotenciacion'});
        $routeProvider.when('/consultaRangoPlaca', {templateUrl: 'content/consultaRangoPlaca.html', controller: 'consultaRangoPlacaCtrl'});
        $routeProvider.when('/consultaDatosMigrados', {templateUrl: 'content/consultaDatosMigrados.html', controller: 'consultaDatosMigradosCtrl'});
        $routeProvider.when('/cargueArchivoTemporal', {templateUrl: 'content/cargueArchivoTemporal.html', controller: 'cargueArchivoTemporalCtrl'});
        $routeProvider.when('/listaBoletin', {templateUrl: 'content/listaBoletin.html', controller: 'controllerlistaBoletin'});
        $routeProvider.when('/procesoMigracion', {templateUrl: 'content/procesoMigracion.html', controller: 'procesoMigracionCtrl'});
        $routeProvider.when('/asignacionRangos', {templateUrl: 'content/asignacionRangos.html', controller: 'asignacionRangosCtrl'});
        $routeProvider.when('/consultaDeclaracionImportacion', {templateUrl: 'content/consultaDeclaracionImportacion.html', controller: 'consultaDeclaracionImportacionCtrl'});
        $routeProvider.when('/reprocesarRNA', {templateUrl: 'content/reprocesarRNA.html', controller: 'reprocesarRNACtrl'});
        $routeProvider.when('/export', {templateUrl: 'content/export.html', controller: 'exportCtrl'});
        $routeProvider.when('/carguePuntual', {templateUrl: 'content/carguePuntual.html', controller: 'carguePuntualCtrl'});
        $routeProvider.when('/actualizacionInformacion', {templateUrl: 'content/actualizacionInformacion.html', controller: 'actualizaInformacionCtrl'});
        $routeProvider.when('/cargueCia', {templateUrl: 'content/cargueCia.html', controller: 'cargueCiaCtrl'});
        $routeProvider.when('/procesarCarguesProgramados', {templateUrl: 'content/procesamientoCarguesProgramados.html', controller: 'procesarCarguesProgramadosCtrl'});
        
        
        $routeProvider.otherwise({redirectTo: '/home'});
    }]);

module.config(["$httpProvider", function ($httpProvider) {
        $httpProvider.interceptors.push('httpInterceptorSession');
    }]);
