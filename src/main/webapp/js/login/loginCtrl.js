(function () {
    'use strict';

    /* Controller Login */
    angular.module('SagirApp.controllers').controller('loginCtrl', loginController);

    loginController.$inject = ['$scope', '$http', 'expresionRegular'];

    function loginController($scope, $http, expresionRegular) {
        // Definición Variables
        $scope.datosFormulario = {};
        $scope.errores = {};

        // Definición de funciones
        $scope.login = login;
        $scope.validarForm = validarForm;
        $scope.mostrarMensaje = mostrarMensaje;

        function login() {
            $('#dlgEspera').modal();

            if (!$scope.validarForm()) {

                $http.post('./webresources/autenticacion/login', $scope.datosFormulario).success(function (data, status, headers, config) {
                    if (data.codmensaje === 'OK') {
                        window.location.href = './index.html';
                    } else {
                        mostrarMensaje(data.mensaje);
                    }

                }).error(function (data, status, headers, config) {
                    mostrarMensaje('La plataforma presenta inconvenientes, por favor intente más tarde.');

                });
            }
            $('#dlgEspera').modal('hide');
        }
        ;

        function validarForm() {
            $scope.errores = {};
            var error = false;

            if (!$scope.datosFormulario.login) {
                $scope.errores.login = 'El campo es obligatorio';
                error = true;
            } else {
                if (!expresionRegular.formatoAlfanumericoSimbolo().test($scope.datosFormulario.login)) {
                    error = true;
                    $scope.errores.login = 'El campo contiene carácteres no válidos';
                }
            }

            if (!$scope.datosFormulario.password) {
                $scope.errores.password = 'El campo es obligatorio';
                error = true;
            } else {
                if (!expresionRegular.formatoAlfanumericoSimbolo().test($scope.datosFormulario.password)) {
                    error = true;
                    $scope.errores.password = 'El campo contiene carácteres no válidos';
                }
            }

            return error;
        }

        function mostrarMensaje(mensaje) {
            $('#dlgEspera').modal('hide');
            $('#msgConsulta').html(mensaje);
            $('#dlgConsulta').modal();
        }

    }

})();