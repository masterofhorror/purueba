(function () {
    'use strict';

    angular.module('SagirApp.controllers').controller('logoutCtrl', logoutController);

    logoutController.$inject = ['$scope', '$http'];

    function logoutController($scope, $http) {
        // Definición de funciones
        $scope.logout = logout;
        $scope.mostrarMensaje = mostrarMensaje;

        //
        $scope.logout();

        // Implementación
        function logout() {
            $http.get('./webresources/autenticacion/logout').success(function (data, status, headers, config) {
                window.location.href = './login.html#/login';

            }).error(function (data, status, headers, config) {
                mostrarMensaje('La plataforma presenta inconvenientes, intente más tarde.');
            });
        }

        function mostrarMensaje(mensaje) {
            $('#dlgEspera').modal('hide');
            $('#msgConsulta').html(mensaje);
            $('#dlgConsulta').modal();
        }

    }

})();