(function () {
    'use strict';

    angular.module('SagirApp.controllers').controller('exportCtrl', exportController);

    exportController.$inject = ['$scope', '$http', 'expresionRegular'];

    function exportController($scope, $http, expresionRegular) {
        // Definición Variables
        $scope.datosFormulario = {};
        $scope.urlBase = './webresources/export/';
        $scope.pageSize = 10;
        $scope.currentPage = 1;
        $scope.verConsulta = false;
        $scope.currentPage1 = 1;
        $scope.currentPage2 = 1;
        $scope.currentPage3 = 1;
        $scope.pageChangeHandler = function (num) {
            console.log('meals page changed to ' + num);
        };

        var servicio;
        // Definición de funciones
        $scope.consulta = consulta;
        $scope.procesar = procesar;

        $scope.mostrarMensajeSuccess = mostrarMensajeSuccess;
        $scope.mostrarMensajeInfo = mostrarMensajeInfo;
        $scope.mostrarMensajeError = mostrarMensajeError;
        $scope.ocultarMensaje = ocultarMensaje;
        $scope.limpiarMensaje = limpiarMensaje;
        $scope.mostrarConsulta = mostrarConsulta;

        function mostrarConsulta() {
            $scope.verConsulta = true;
        }
        ;
        // Implementación
        function consulta(parametro) {
            $scope.resultados = [];
            $scope.tipo = parametro;

            if (parametro === 1) {
                servicio = $scope.urlBase + 'pendientesmigrar';
            } else if (parametro === 2) {
                servicio = $scope.urlBase + 'migrados';
            } else if (parametro === 3) {
                servicio = $scope.urlBase + 'resultado';
            }
            ;

            $('#dlgEspera').modal();

            $http.get(servicio)
                    .success(function (data, status, headers, config) {
                        if (data.codmensaje === 'OK') {
                            $scope.resultadoConsulta = data.object;
                            $scope.mostrarConsulta();
                            $('#dlgEspera').modal('hide');
                        } else {
                            $scope.mostrarMensajeInfo(data.mensaje);
                            $scope.verConsulta = false;
                        }
                    }).error(function (data, status, headers, config) {
                $scope.mostrarMensajeError('Error consultando la programación de cargues. Por favor intente mas tarde.');
            });
        }

        function procesar() {
            $('#dlgEspera').modal();
            $http.get(servicio)
                    .success(function (data, status, headers, config) {
                        if (data.codmensaje === 'OK') {
                            $scope.mostrarMensajeSuccess(data.mensaje);
                            $('#dlgEspera').modal('hide');
                        } else {
                            $scope.mostrarMensajeInfo(data.mensaje);
                        }
                    }).error(function (data, status, headers, config) {
                $scope.mostrarMensajeError('Error consultando la programación de cargues. Por favor intente mas tarde.');
            });
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