
module.controller('controllerCambioFormatoPlaca', ['$scope', '$http', '$route', function ($scope, $http, $route) {
        console.log("inicializando app");
        $scope.datosFormulario = {};
        $scope.verMsjOk = false;
        $scope.verMsjError = false;
        $scope.verConsulta = false;
        $scope.verDetalle = false;
        $scope.pageSize = 10;
        $scope.currentPage = 1;
        $scope.ulrbase = './webresources/cambioformatoplaca/';
        var servicio;
        $scope.ocultarMensaje = ocultarMensaje;
        $scope.limpiarMensaje = limpiarMensaje;

        $scope.mostrarMensajeExito = function (mensaje) {
            $scope.limpiarMensaje();

            $scope.verMsjOk = true;
            $('#msgResultadoRegOk').html(mensaje);
            $(".messages").fadeIn();
            $scope.ocultarMensaje();
        };
        $scope.mostrarMensajeError = function (mensaje) {
            $scope.limpiarMensaje();

            $scope.verMsjError = true;
            $('#msgResultadoRegError').html(mensaje);
            $(".messages").fadeIn();

            $scope.ocultarMensaje();
        };

        $scope.mostrarInfo = function () {
            $scope.verConsulta = true;
            $scope.verDetalle = false;
        };

        $scope.mostrarDetalle = function () {
            $scope.verConsulta = false;
            $scope.verDetalle = true;
        };

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

            $scope.verMsjOk = false;
            $scope.verMsjError = false;
        }

        $scope.formatoNuevoAAntiguo = function () {

            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.placa) {
                error = true;
                $scope.errores.placa = 'Campo obligatorio';
            } else {
                var expreg = /[A-Z]{3}?[0-9]{3}$/;
                if (!expreg.test($scope.datosFormulario.placa)) {
                    error = true;
                    $scope.errores.placa = 'El número de placa ingresado no es formato nuevo.';
                }
            }
            if (!$scope.datosFormulario.nroTicket) {
                error = true;
                $scope.errores.nroTicket = 'Campo obligatorio';
            } else {
                var expregTicket = /^INC[0-9]{13}/;
                if (!expregTicket.test($scope.datosFormulario.nroTicket)) {
                    error = true;
                    $scope.errores.nroTicket = 'El número de ticket ingresado no es valido.';
                }
            }

            if (!error) {
                $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
                servicio = $scope.ulrbase + "antiguonuevo/" + $scope.datosFormulario.placa + "/" + $scope.datosFormulario.nroTicket;
                $http.get(servicio).
                        success(function (data, status, headers, config) {
                            if (data.codmensaje === "OK") {
                                $scope.mostrarMensajeExito(data.mensaje);
                            } else {
                                if (data.codmensaje === "ERROR") {
                                    $scope.mostrarMensajeError(data.mensaje);
                                    $('#dlgEspera').modal('hide');
                                }
                            }
                        }).error(function (data, status, headers, config) {
                    $scope.mostrarMensajeError('Error al consultar la información, por favor intente más tarde.');
                });
            }
        };

        $scope.formatoAntiguoANuevo = function () {
            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.placa) {
                error = true;
                $scope.errores.placa = 'Campo obligatorio';
            } else {
                var expreg = /[A-Z]{2}?[0-9]{4}$/;
                if (!expreg.test($scope.datosFormulario.placa)) {
                    $scope.errores.placa = 'El número de placa ingresado no es formato antigüo.';
                }
            }
            if (!$scope.datosFormulario.nroTicket) {
                error = true;
                $scope.errores.nroTicket = 'Campo obligatorio';
            } else {
                var expregTicket = /^INC[0-9]{13}/; 
                if (!expregTicket.test($scope.datosFormulario.nroTicket)) {
                    error = true;
                    $scope.errores.nroTicket = 'El número de ticket ingresado no es valido.';
                }
            }
            if (!error) {
                $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
                servicio = $scope.ulrbase + "nuevoantiguo/" + $scope.datosFormulario.placa + "/" + $scope.datosFormulario.nroTicket;
                $http.get(servicio).
                        success(function (data, status, headers, config) {
                            if (data.codmensaje === "OK") {
                                $scope.mostrarMensajeExito(data.mensaje);
                                $('#dlgEspera').modal('hide');
                            } else {
                                if (data.codmensaje === "ERROR") {
                                    $scope.mostrarMensajeError(data.mensaje);
                                }
                            }
                            console.log(data.mensaje);
                        }).error(function (data, status, headers, config) {
                    $scope.mostrarMensajeError('Error al consultar la información, por favor intente más tarde.');
                });
            }
        };

        $scope.consulta = function () {
            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.placa) {
                error = true;
                $scope.errores.placa = 'Campo obligatorio';
            } else {
                var expreg = /^[A-Z]{1}[A-Z]?[0-9]?[A-Z]?[0-9]?[0-9]{2}[A-Z]?[0-9]$/;
                if (!expreg.test($scope.datosFormulario.placa)) {
                    error = true;
                    $scope.errores.placa = 'El número de placa ingresado no es formato de placa valido.';
                }
            }
            if (!error) {
                $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
                servicio = $scope.ulrbase + "consultacambioformato/" + $scope.datosFormulario.placa;
                $http.get(servicio).
                        success(function (data, status, headers, config) {
                            if (data.codmensaje === "OK") {
                                $scope.resultadoConsulta = data.object;
                                $scope.mostrarInfo();
                                $('#dlgEspera').modal('hide');
                            } else {
                                $scope.mostrarMensajeError(data.mensaje);
                            }
                        }).error(function (data, status, headers, config) {
                    $scope.mostrarMensajeError("Error en los servicios, status " + status);
                });
            }
        };

        $scope.consultaDetalle = function (placa, boletin) {
            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
            servicio = $scope.ulrbase + "consultacambioformatodetalle/" + placa + '/' + boletin;
            $http.get(servicio).
                    success(function (data, status, headers, config) {
                        if (data.codmensaje === "OK") {
                            $scope.resultadoDetalle = data.object;
                            $scope.mostrarDetalle();
                            $('#dlgEspera').modal('hide');
                        } else {
                            $scope.mostrarMensajeError(data.mensaje);

                        }
                    }).error(function (data, status, headers, config) {
                $scope.mostrarMensajeError("Error en los servicios, status " + status);
            });
        };

    }]);


