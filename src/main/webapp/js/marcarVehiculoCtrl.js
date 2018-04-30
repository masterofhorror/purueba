module.controller('controllerMarcarVehiculo', ['$scope', '$http', '$route', function ($scope, $http, $route) {
        console.log("inicializando app");
        $scope.datosFormulario = {};
        $scope.verMsjOk = false;
        $scope.verMsjError = false;
        $scope.verMsjAdvertencia = false;
        $scope.verConsultaProd = false;
        $scope.verConsultaMigra = false;
        $scope.verMensaje = false;
        $scope.ulrbase = "./webresources/marcarvehiculo/";
        $scope.pageSize = 100;
        $scope.currentPage = 1;
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
        $scope.mostrarMensajeAdvertencia = function (mensaje) {
            $scope.limpiarMensaje();

            $scope.verMsjAdvertencia = true;
            $('#msgResultadoRegAdvertencia').html(mensaje);
            $(".messages").fadeIn();

            $scope.ocultarMensaje();
        };

        $scope.mostrarConsProd = function () {
            $scope.verConsultaProd = true;
            $scope.verConsultaMigra = false;
        };
        $scope.mostrarConsMigra = function () {
            $scope.verConsultaProd = false;
            $scope.verConsultaMigra = true;
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

        $scope.consultaProd = function () {

            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.placa) {
                error = true;
                $scope.errores.placa = 'Campo obligatorio';
            } else {
                var expreg = /^[A-Z]{1}[A-Z]?[0-9]?[A-Z]?[0-9]?[0-9]{2}[A-Z]?[0-9]$/;
                var expreg2 = /^[A-Z]{3}[0-9]{2}$/;
                var expreg3 = /^[A-Z]{3}[0-9]{2}[A-Z]$/;
                if (!expreg.test($scope.datosFormulario.placa)) {
                    if (!expreg2.test($scope.datosFormulario.placa)) {
                        if (!expreg3.test($scope.datosFormulario.placa)) {
                            error = true;
                            $scope.errores.placa = 'El número de placa ingresado no es un formato valido.';
                        } else {
                            error = false;
                        }
                    } else {
                        error = false;
                    }
                } else {
                    error = false;
                }
            }

            if (!error) {
                $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
                servicio = $scope.ulrbase + "consultaprod/" + $scope.datosFormulario.placa;
                $http.get(servicio).
                        success(function (data, status, headers, config) {
                            if (data.codmensaje === "OK") {
                                $scope.resultadoConsulta = data.object;
                                $scope.mostrarConsProd();
                                $('#dlgEspera').modal('hide');
                            } else {
                                if (data.codmensaje === "ERROR") {
                                    $scope.mostrarMensajeError(data.mensaje);
                                }
                            }
                        }).error(function (data, status, headers, config) {
                    $scope.mostrarMensajeError("Error en los servicios, status " + status);
                });
            }
        };

        $scope.consultaMigra = function () {

            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.placa) {
                error = true;
                $scope.errores.placa = 'Campo obligatorio';
            } else {
                var expreg = /^[A-Z]{1}[A-Z]?[0-9]?[A-Z]?[0-9]?[0-9]{2}[A-Z]?[0-9]$/;
                var expreg2 = /^[A-Z]{3}[0-9]{2}$/;
                var expreg3 = /^[A-Z]{3}[0-9]{2}[A-Z]$/;
                if (!expreg.test($scope.datosFormulario.placa)) {
                    if (!expreg2.test($scope.datosFormulario.placa)) {
                        if (!expreg3.test($scope.datosFormulario.placa)) {
                            error = true;
                            $scope.errores.placa = 'El número de placa ingresado no es un formato valido.';
                        } else {
                            error = false;
                        }
                    } else {
                        error = false;
                    }
                } else {
                    error = false;
                }
            }

            if (!error) {
                $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
                servicio = $scope.ulrbase + "consultamigra/" + $scope.datosFormulario.placa;
                $http.get(servicio).
                        success(function (data, status, headers, config) {
                            if (data.codmensaje === "OK") {
                                $scope.resultadoConsulta = data.object;
                                $scope.mostrarConsMigra();
                                $('#dlgEspera').modal('hide');
                            } else {
                                if (data.codmensaje === "ERROR") {
                                    $scope.mostrarMensajeError(data.mensaje);
                                }
                            }
                        }).error(function (data, status, headers, config) {
                    $scope.mostrarMensajeError("Error en los servicios, status " + status);
                });
            }
        };

        $scope.marcarVehiProduccion = function () {

            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.placa) {
                error = true;
                $scope.errores.placa = 'Campo obligatorio';
            } else {
                var expreg = /^[A-Z]{1}[A-Z]?[0-9]?[A-Z]?[0-9]?[0-9]{2}[A-Z]?[0-9]$/;
                var expreg2 = /^[A-Z]{3}[0-9]{2}$/;
                var expreg3 = /^[A-Z]{3}[0-9]{2}[A-Z]$/;
                if (!expreg.test($scope.datosFormulario.placa)) {
                    if (!expreg2.test($scope.datosFormulario.placa)) {
                        if (!expreg3.test($scope.datosFormulario.placa)) {
                            error = true;
                            $scope.errores.placa = 'El número de placa ingresado no es un formato valido.';
                        } else {
                            error = false;
                        }
                    } else {
                        error = false;
                    }
                } else {
                    error = false;
                }
            }
            if (!$scope.datosFormulario.nroTicket) {
                error = true;
                $scope.errores.nroTicket = 'Campo obligatorio';
            } else {
                var expregTicket = /^INC[0-9]{11}/;
                if (!expregTicket.test($scope.datosFormulario.nroTicket)) {
                    error = true;
                    $scope.errores.nroTicket = 'El número de ticket ingresado no es valido.';
                }
            }
            if (!$scope.datosFormulario.observacion) {
                error = true;
                $scope.errores.observacion = 'Campo obligatorio';
            }

            if (!error) {
                $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
                servicio = $scope.ulrbase + "marcavehiprod/" + $scope.datosFormulario.placa + "/"
                        + $scope.datosFormulario.nroTicket + "/" + $scope.datosFormulario.observacion;
                $http.get(servicio).success(function (data, status, headers, config) {
                    if (data.codmensaje === "OK") {
                        $scope.mostrarMensajeExito(data.mensaje);
                        $('#dlgEspera').modal('hide');
                    } else {
                        if (data.codmensaje === "ERROR") {
                            $scope.mostrarMensajeError(data.mensaje);
                        }
                    }
                }).error(function (data, status, headers, config) {
                    $scope.mostrarMensajeError("Error en los servicios, status " + status);
                });
            }
        };

        $scope.marcarVehiMigracion = function () {

            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.placa) {
                error = true;
                $scope.errores.placa = 'Campo obligatorio';
            } else {
                var expreg = /^[A-Z]{1}[A-Z]?[0-9]?[A-Z]?[0-9]?[0-9]{2}[A-Z]?[0-9]$/;
                var expreg2 = /^[A-Z]{3}[0-9]{2}$/;
                var expreg3 = /^[A-Z]{3}[0-9]{2}[A-Z]$/;
                if (!expreg.test($scope.datosFormulario.placa)) {
                    if (!expreg2.test($scope.datosFormulario.placa)) {
                        if (!expreg3.test($scope.datosFormulario.placa)) {
                            error = true;
                            $scope.errores.placa = 'El número de placa ingresado no es un formato valido.';
                        } else {
                            error = false;
                        }
                    } else {
                        error = false;
                    }
                } else {
                    error = false;
                }
            }
            if (!$scope.datosFormulario.nroTicket) {
                error = true;
                $scope.errores.nroTicket = 'Campo obligatorio';
            } else {
                var expregTicket = /^INC[0-9]{11}/;
                if (!expregTicket.test($scope.datosFormulario.nroTicket)) {
                    error = true;
                    $scope.errores.nroTicket = 'El número de ticket ingresado no es valido.';
                }
            }
            if (!$scope.datosFormulario.observacion) {
                error = true;
                $scope.errores.observacion = 'Campo obligatorio';
            }
            if (!error) {
                $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
                servicio = $scope.ulrbase + "marcavehimigra/" + $scope.datosFormulario.placa + "/"
                        + $scope.datosFormulario.nroTicket + "/" + $scope.datosFormulario.observacion;
                $http.get(servicio).success(function (data, status, headers, config) {
                    if (data.codmensaje === "OK") {
                        $scope.mensaje = data.mensaje;
                        $('#dlgEspera').modal('hide');
                    }
                    if (data.codmensaje === "ERROR") {
                        $scope.mostrarMensajeError(data.mensaje);
                    }
                    if (data.codmensaje === "ADVERTENCIA") {
                        $scope.mostrarMensajeError(data.mensaje);
                    }
                }).error(function (data, status, headers, config) {
                    $scope.mostrarMensajeError("Error en los servicios, status " + status);
                });
            }
        };

    }]);
