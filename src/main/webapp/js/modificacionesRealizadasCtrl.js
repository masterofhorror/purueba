(function () {
    'use strict';

    angular.module('SagirApp.controllers').controller('modificacionesRealizadasCtrl', modificacionesRealizadasController);

    modificacionesRealizadasController.$inject = ['$scope', '$http', 'expresionRegular'];

    function modificacionesRealizadasController($scope, $http, expresionRegular) {
        // Definición Variables
        $scope.filtroConsulta = {};
        $scope.errores = {};

        $scope.resultadoConsulta = {};

        $scope.verResultado = false;

        $scope.pageSizeActualizacion = 5;
        $scope.currentPageActualizacion = 1;

        $scope.pageSizeCasosEspeciales = 5;
        $scope.currentPageCasosEspeciales = 1;

        // Definición de funciones
        $scope.buscar = buscar;
        $scope.cancelar = cancelar;

        $scope.mostrarMensajeSuccess = mostrarMensajeSuccess;
        $scope.mostrarMensajeInfo = mostrarMensajeInfo;
        $scope.mostrarMensajeError = mostrarMensajeError;
        $scope.ocultarMensaje = ocultarMensaje;
        $scope.limpiarMensaje = limpiarMensaje;

        //

        // Implementación
        function buscar() {
            $scope.errores = {};

            if ($scope.filtroConsulta.placa) {
                if (!expresionRegular.formatoAlfanumerico().test($scope.filtroConsulta.placa)) {
                    $scope.errores.placa = 'El valor ingresado contiene caracteres invalidos';
                } else if ($scope.filtroConsulta.placa.length > 10) {
                    $scope.errores.placa = 'El valor ingresado excede el tamaño máximo para el campo';
                }
            } else {
                $scope.errores.placa = 'Este campo es obligatorio';
            }

            if (Object.keys($scope.errores).length === 0) {
                $('#dlgEspera').modal();

                $http.get('./webresources/modificacionesRealizadas/' + $scope.filtroConsulta.placa)
                        .success(function (data, status, headers, config) {
                            if (data.codmensaje === 'OK') {
                                $scope.resultadoConsulta = data.object;
                                $scope.verResultado = true;

                                $('#dlgEspera').modal('hide');
                            } else {
                                $scope.mostrarMensajeInfo(data.mensaje);
                            }
                        }).error(function (data, status, headers, config) {
                    $scope.mostrarMensajeError('Error consultando las modificaciones realizadas. Por favor intente mas tarde.');
                });
            }
        }

        function cancelar() {
            $scope.filtroConsulta = {};
            $scope.errores = {};

            $scope.verResultado = false;
        }

        //////////////////////// UTILITARIOS ////////////////////////

        function mostrarMensajeSuccess(mensaje) {
            $scope.limpiarMensaje();

            $scope.mensajeSuccess = true;
            $('#msgSuccess').html(mensaje);
            $(".messages").fadeIn();

            $scope.ocultarMensaje();
        }

        function mostrarMensajeInfo(mensaje) {
            $scope.limpiarMensaje();

            $scope.mensajeInfo = true;
            $('#msgInfo').html(mensaje);
            $(".messages").fadeIn();

            $scope.ocultarMensaje();
        }

        function mostrarMensajeError(mensaje) {
            $scope.limpiarMensaje();

            $scope.mensajeError = true;
            $('#msgError').html(mensaje);
            $(".messages").fadeIn();

            $scope.ocultarMensaje();
        }

        function ocultarMensaje() {
            $(document).ready(function () {
                setTimeout(function () {
                    $(".messages").fadeOut(3500);
                }, 4000);
            });
            window.scroll(0, 0);
        }

        function limpiarMensaje() {
            $('#dlgEspera').modal('hide');

            $scope.mensajeSuccess = false;
            $scope.mensajeInfo = false;
            $scope.mensajeError = false;
        }

    }
})();
