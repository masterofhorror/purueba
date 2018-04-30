(function () {
    'use strict';

    angular.module('SagirApp.controllers').controller('reprocesarRNACtrl', reprocesarRNAController);

    reprocesarRNAController.$inject = ['$scope', '$http', 'expresionRegular'];

    function reprocesarRNAController($scope, $http, expresionRegular) {
        // Definición Variables
        $scope.datosFormulario = {};
        $scope.urlBase = './webresources/reprocesarrna/';
        $scope.pageSize = 10;
        $scope.currentPage = 1;
        $scope.verConsulta = false;
        var servicio;
        // Definición de funciones
        $scope.consulta = consulta;
        $scope.procesarTodo = procesarTodo;
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
        function consulta() {
            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.codCarga) {
                error = true;
                $scope.errores.codCarga = 'Campo obligatorio';
            } else {
                var expreg = /^\d*$/;
                if (!expreg.test($scope.datosFormulario.codCarga)) {
                    error = true;
                    $scope.errores.codCarga = 'El código de carga debe ser númerico';
                }
            }

            if (!error) {
                $('#dlgEspera').modal();
                servicio = $scope.urlBase + 'consulta/' + $scope.datosFormulario.codCarga;
                $http.get(servicio)
                        .success(function (data, status, headers, config) {
                            if (data.codmensaje === 'OK') {
                                $scope.resultadoConsulta = data.object;
                                $scope.mostrarConsulta();
                                $('#dlgEspera').modal('hide');
                            } else {
                                $scope.mostrarMensajeInfo(data.mensaje);
                            }
                        }).error(function (data, status, headers, config) {
                    $scope.mostrarMensajeError('Error consultando la programación de cargues. Por favor intente mas tarde.');
                });
            }
        }


        function procesarTodo() {
            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.codCarga) {
                error = true;
                $scope.errores.codCarga = 'Campo obligatorio';
            } else {
                var expreg = /^\d*$/;
                if (!expreg.test($scope.datosFormulario.codCarga)) {
                    error = true;
                    $scope.errores.codCarga = 'El código de carga debe ser númerico';
                }
            }

            if (!error) {
                $('#dlgEspera').modal();
                servicio = $scope.urlBase + 'procesacarga/' + $scope.datosFormulario.codCarga;
                $http.get(servicio)
                        .success(function (data, status, headers, config) {
                            if (data.codmensaje === 'OK') {
                                $scope.mostrarMensajeSuccess(data.mensaje);
                                $('#dlgEspera').modal('hide');
                                $scope.consulta();
                            } else {
                                $scope.mostrarMensajeInfo(data.mensaje);
                            }
                        }).error(function (data, status, headers, config) {
                    $scope.mostrarMensajeError('Error consultando la programación de cargues. Por favor intente mas tarde.');
                });
            }
        }
        ;

        function procesar(placa) {
            $('#dlgEspera').modal();
            servicio = $scope.urlBase + 'procesaplaca/' + $scope.datosFormulario.codCarga + '/' + placa;
            $http.get(servicio)
                    .success(function (data, status, headers, config) {
                        if (data.codmensaje === 'OK') {
                            $scope.mostrarMensajeSuccess(data.mensaje);
                            $('#dlgEspera').modal('hide');
                            $scope.consulta();
                        } else {
                            $scope.mostrarMensajeInfo(data.mensaje);
                        }
                    }).error(function (data, status, headers, config) {
                $scope.mostrarMensajeError('Error consultando la programación de cargues. Por favor intente mas tarde.');
            });
        }
        ;

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