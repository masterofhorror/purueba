
module.controller('actualizaInformacionCtrl', ['$scope', '$http', '$route', function ($scope, $http, $route) {
        console.log("inicializando app");
        $scope.datosFormulario = {};
        $scope.verMsjOk = false;
        $scope.verMsjError = false;
        $scope.ulrbase = "./webresources/";
        $scope.pageSize = 10;
        $scope.currentPage = 1;
        $scope.listarOt = [];
        $scope.currentPage = 1;
        var servicio;
        //Variables de visualización (ng-show)
        $scope.verEstadoVehiculo = false;
        $scope.verFormatoPlaca = false;
        $scope.verServicio = false;
        $scope.verRadicado = false;
        $scope.verBotones = true;

        //Declaración de las funciones
        $scope.ocultarMensaje = ocultarMensaje;
        $scope.limpiarMensaje = limpiarMensaje;
        $scope.limpiarFormulario = limpiarFormulario;


        $scope.pageChangeHandler = function (num) {
            console.log('meals page changed to ' + num);
        };

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
        
        function limpiarFormulario(){
            $scope.datosFormulario = {};
        }

        $scope.regresar = function () {
            $scope.verEstadoVehiculo = false;
            $scope.verFormatoPlaca = false;
            $scope.verServicio = false;
            $scope.verRadicado = false;
            $scope.verBotones = true;
            $scope.limpiarFormulario();
        };

        $scope.estadoVehiculo = function () {
            $scope.verEstadoVehiculo = true;
            $scope.verFormatoPlaca = false;
            $scope.verServicio = false;
            $scope.verRadicado = false;
            $scope.verBotones = false;
        };

        $scope.formatoPlaca = function () {
            $scope.verEstadoVehiculo = false;
            $scope.verFormatoPlaca = true;
            $scope.verServicio = false;
            $scope.verRadicado = false;
            $scope.verBotones = false;
        };

        $scope.servicio = function () {
            $scope.verEstadoVehiculo = false;
            $scope.verFormatoPlaca = false;
            $scope.verServicio = true;
            $scope.verRadicado = false;
            $scope.verBotones = false;
        };

        //----------------------------------------------------------------------
        //----------------------------------------------------------------------
        //------------------ACTUALIZACION ESTADO DEL VEHICULO-------------------
        //----------------------------------------------------------------------
        //----------------------------------------------------------------------
        $scope.cambioEstado = cambioEstado;
        $scope.confirmacion = confirmacion;
        $scope.consultaEstado = consultaEstado;
        $scope.mostrarInfoEstado = function () {
            $scope.verConsultaEstado = true;
        };

        function confirmacion() {

            bootbox.confirm(
                    $scope.mensajeConfirmacion, function (result) {
                        if (result) {
                            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
                            servicio = $scope.ulrbase + "estadovehiculo/cambioestadocancelado/"
                                    + $scope.datosFormulario.placaEstado + "/" + $scope.datosFormulario.estado
                                    + "/" + $scope.datosFormulario.nroTicketEstado;
                            $http.get(servicio).
                                    success(function (data, status, headers, config) {
                                        if (data.codmensaje === "OK") {
                                            $scope.mostrarMensajeExito(data.mensaje);
                                            $scope.consultaEstado();
                                            $('#dlgEspera').modal('hide');
                                        } else {
                                            if (data.codmensaje === "ERROR") {
                                                $scope.mostrarMensajeError(data.mensaje);
                                            }
                                        }
                                    }).error(function (data, status, headers, config) {
                                $scope.mostrarMensajeError('Error al consultar la información, por favor intente más tarde.');
                            });
                        }
                    });
        }
        ;

        function cambioEstado() {
            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.placaEstado) {
                error = true;
                $scope.errores.placaEstado = 'Campo obligatorio';
            } else {
                var expreg = /^[A-Z]{1}[A-Z]?[0-9]?[A-Z]?[0-9]?[0-9]{2}[A-Z]?[0-9]$/;
                var expreg2 = /^[A-Z]{3}[0-9]{2}$/;
                var expreg3 = /^[A-Z]{3}[0-9]{2}[A-Z]$/;
                if (!expreg.test($scope.datosFormulario.placaEstado)) {
                    if (!expreg2.test($scope.datosFormulario.placaEstado)) {
                        if (!expreg3.test($scope.datosFormulario.placaEstado)) {
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
            if (!$scope.datosFormulario.nroTicketEstado) {
                error = true;
                $scope.errores.nroTicketEstado = 'Campo obligatorio';
            } else {
                var expregTicket = /^INC[0-9]{11}/;
                if (!expregTicket.test($scope.datosFormulario.nroTicketEstado)) {
                    error = true;
                    $scope.errores.nroTicketEstado = 'El número de ticket ingresado no es valido.';
                }
            }
            if (!$scope.datosFormulario.estado) {
                error = true;
                $scope.errores.estado = 'Campo obligatorio';
            }

            if (!error) {
                $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
                servicio = $scope.ulrbase + "estadovehiculo/cambioestadovehiculo/" + $scope.datosFormulario.placaEstado
                        + "/" + $scope.datosFormulario.estado + "/" + $scope.datosFormulario.nroTicketEstado;
                $http.get(servicio).
                        success(function (data, status, headers, config) {
                            if (data.codmensaje === "OK") {
                                $scope.mostrarMensajeExito(data.mensaje);
                                $scope.consultaEstado();
                                $('#dlgEspera').modal('hide');
                            } else {
                                if (data.codmensaje === "VALIDADOR") {
                                    $scope.mensajeConfirmacion = data.mensaje;
                                    $scope.confirmacion();
                                    $('#dlgEspera').modal('hide');
                                } else {
                                    if (data.codmensaje === "ERROR") {
                                        $scope.mostrarMensajeError(data.mensaje);
                                    }
                                }
                            }
                        }).error(function (data, status, headers, config) {
                    $scope.mostrarMensajeError('Error al consultar la información, por favor intente más tarde.');
                });
            }
        }
        ;

        function consultaEstado() {

            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.placaEstado) {
                error = true;
                $scope.errores.placaEstado = 'Campo obligatorio';
            } else {
                var expreg = /^[A-Z]{1}[A-Z]?[0-9]?[A-Z]?[0-9]?[0-9]{2}[A-Z]?[0-9]$/;
                var expreg2 = /^[A-Z]{3}[0-9]{2}$/;
                var expreg3 = /^[A-Z]{3}[0-9]{2}[A-Z]$/;
                if (!expreg.test($scope.datosFormulario.placaEstado)) {
                    if (!expreg2.test($scope.datosFormulario.placaEstado)) {
                        if (!expreg3.test($scope.datosFormulario.placaEstado)) {
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
                servicio = $scope.ulrbase + "estadovehiculo/consultaestadovehiculo/" + $scope.datosFormulario.placaEstado;
                $http.get(servicio).
                        success(function (data, status, headers, config) {
                            if (data.codmensaje === "OK") {
                                $scope.resultadoConsultaEstado = data.object;
                                $scope.mostrarInfoEstado();
                                $('#dlgEspera').modal('hide');
                            } else {
                                if (data.codmensaje === "ERROR") {
                                    $scope.mostrarMensajeError(data.mensaje);
                                }
                            }
                        }).error(function (data, status, headers, config) {
                    alert("Error en los servicios, status " + status);
                });
            }
        }
        ;

        //----------------------------------------------------------------------
        //----------------------------------------------------------------------
        //------------------ACTUALIZACION FORMATO DE PLACA----------------------
        //----------------------------------------------------------------------
        //----------------------------------------------------------------------

        $scope.mostrarInfoFormatoPlaca = function () {
            $scope.verConsulta = true;
            $scope.verDetalle = false;
        };

        $scope.mostrarDetalle = function () {
            $scope.verConsulta = false;
            $scope.verDetalle = true;
        };

        $scope.formatoNuevoAAntiguo = function () {

            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.placaFormato) {
                error = true;
                $scope.errores.placaFormato = 'Campo obligatorio';
            } else {
                var expreg = /[A-Z]{3}?[0-9]{3}$/;
                if (!expreg.test($scope.datosFormulario.placaFormato)) {
                    error = true;
                    $scope.errores.placaFormato = 'El número de placa ingresado no es formato nuevo.';
                }
            }
            if (!$scope.datosFormulario.nroTicketFormato) {
                error = true;
                $scope.errores.nroTicketFormato = 'Campo obligatorio';
            } else {
                var expregTicket = /^INC[0-9]{11}/;
                if (!expregTicket.test($scope.datosFormulario.nroTicketFormato)) {
                    error = true;
                    $scope.errores.nroTicketFormato = 'El número de ticket ingresado no es valido.';
                }
            }

            if (!error) {
                $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
                servicio = $scope.ulrbase + "cambioformatoplaca/antiguonuevo/" + $scope.datosFormulario.placaFormato + "/" + $scope.datosFormulario.nroTicketFormato;
                $http.get(servicio).
                        success(function (data, status, headers, config) {
                            if (data.codmensaje === "OK") {
                                $scope.mostrarMensajeExito(data.mensaje);
                                $scope.consulta();
                                $('#dlgEspera').modal('hide');
                            } else {
                                if (data.codmensaje === "ERROR") {
                                    $scope.mostrarMensajeError(data.mensaje);
                                }
                            }
                        }).error(function (data, status, headers, config) {
                    $scope.mostrarMensajeError('Error al consultar la información, por favor intente más tarde.');
                });
            }
        }
        ;

        $scope.formatoAntiguoANuevo = function () {
            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.placaFormato) {
                error = true;
                $scope.errores.placaFormato = 'Campo obligatorio';
            } else {
                var expreg = /[A-Z]{2}?[0-9]{4}$/;
                if (!expreg.test($scope.datosFormulario.placaFormato)) {
                    $scope.errores.placaFormato = 'El número de placa ingresado no es formato antigüo.';
                }
            }
            if (!$scope.datosFormulario.nroTicketFormato) {
                error = true;
                $scope.errores.nroTicketFormato = 'Campo obligatorio';
            } else {
                var expregTicket = /^INC[0-9]{11}/;
                if (!expregTicket.test($scope.datosFormulario.nroTicketFormato)) {
                    error = true;
                    $scope.errores.nroTicketFormato = 'El número de ticket ingresado no es valido.';
                }
            }
            if (!error) {
                $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
                servicio = $scope.ulrbase + "cambioformatoplaca/nuevoantiguo/" + $scope.datosFormulario.placaFormato + "/" + $scope.datosFormulario.nroTicketFormato;
                $http.get(servicio).
                        success(function (data, status, headers, config) {
                            if (data.codmensaje === "OK") {
                                $scope.mostrarMensajeExito(data.mensaje);
                                $scope.consulta();
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
            if (!$scope.datosFormulario.placaFormato) {
                error = true;
                $scope.errores.placaFormato = 'Campo obligatorio';
            } else {
                var expreg = /^[A-Z]{1}[A-Z]?[0-9]?[A-Z]?[0-9]?[0-9]{2}[A-Z]?[0-9]$/;
                var expreg2 = /^[A-Z]{3}[0-9]{2}$/;
                var expreg3 = /^[A-Z]{3}[0-9]{2}[A-Z]$/;
                if (!expreg.test($scope.datosFormulario.placaFormato)) {
                    if (!expreg2.test($scope.datosFormulario.placaFormato)) {
                        if (!expreg3.test($scope.datosFormulario.placaFormato)) {
                            error = true;
                            $scope.errores.placaFormato = 'El número de placa ingresado no es un formato valido.';
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
                servicio = $scope.ulrbase + "cambioformatoplaca/consultacambioformato/" + $scope.datosFormulario.placaFormato;
                $http.get(servicio).
                        success(function (data, status, headers, config) {
                            if (data.codmensaje === "OK") {
                                $scope.resultadoConsultaFormato = data.object;       
                                $scope.mostrarInfoFormatoPlaca();
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

        $scope.consultaDetalle = function (placa, boletin) {
            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
            servicio = $scope.ulrbase + "cambioformatoplaca/consultacambioformatodetalle/" + $scope.datosFormulario.placaFormato + '/' + boletin;
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

        //----------------------------------------------------------------------
        //----------------------------------------------------------------------
        //------------------ACTUALIZACION TIPO DE SERVICIO----------------------
        //----------------------------------------------------------------------
        //----------------------------------------------------------------------
        $scope.listarTiposServicio = listarTiposServicio;
        $scope.listarTiposServicio();

        $scope.mostrarInfoServicio = function () {
            $scope.verConsultaServicio = true;
        };

        function listarTiposServicio() {
            servicio = $scope.ulrbase + "cambioservicio/consultatiposervicio";
            $http.get(servicio).success(function (data, status, headers, config) {
                if (data.codmensaje === "OK") {
                    $scope.listarServicio = data.object;
                } else {
                    if (data.codmensaje === "ERROR") {
                        $scope.mostrarMensajeError(data.mensaje);
                    }
                }
            }).error(function (data, status, headers, config) {
                $scope.mostrarMensajeError("Error al consultar la información");
            });
        }
        ;

        $scope.cambioServicio = function () {
            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.placaServicio) {
                error = true;
                $scope.errores.placaServicio = 'Campo obligatorio';
            } else {
                var expreg = /^[A-Z]{1}[A-Z]?[0-9]?[A-Z]?[0-9]?[0-9]{2}[A-Z]?[0-9]$/;
                var expreg2 = /^[A-Z]{3}[0-9]{2}$/;
                var expreg3 = /^[A-Z]{3}[0-9]{2}[A-Z]$/;
                if (!expreg.test($scope.datosFormulario.placaServicio)) {
                    if (!expreg2.test($scope.datosFormulario.placaServicio)) {
                        if (!expreg3.test($scope.datosFormulario.placaServicio)) {
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
            if (!$scope.datosFormulario.nroTicketServicio) {
                error = true;
                $scope.errores.nroTicketServicio = 'Campo obligatorio';
            } else {
                var expregTicket = /^INC[0-9]{11}/;
                if (!expregTicket.test($scope.datosFormulario.nroTicketServicio)) {
                    error = true;
                    $scope.errores.nroTicketServicio = 'El número de ticket ingresado no es valido.';
                }
            }
            if (!$scope.datosFormulario.servicio) {
                error = true;
                $scope.errores.servicio = 'Campo obligatorio';
            }
            if (!error) {
                $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
                servicio = $scope.ulrbase + "cambioservicio/proceso/" + $scope.datosFormulario.placaServicio + "/" + $scope.datosFormulario.servicio + "/" + $scope.datosFormulario.nroTicketServicio;
                $http.get(servicio).
                        success(function (data, status, headers, config) {
                            if (data.codmensaje === "OK") {
                                $scope.mostrarMensajeExito("Se realizó el cambio de servicio de forma exitosa");
                                $scope.consultaVehiculo();
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

        $scope.consultaVehiculo = function () {

            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.placaServicio) {
                error = true;
                $scope.errores.placaServicio = 'Campo obligatorio';
            } else {
                var expreg = /^[A-Z]{1}[A-Z]?[0-9]?[A-Z]?[0-9]?[0-9]{2}[A-Z]?[0-9]$/;
                var expreg2 = /^[A-Z]{3}[0-9]{2}$/;
                var expreg3 = /^[A-Z]{3}[0-9]{2}[A-Z]$/;
                if (!expreg.test($scope.datosFormulario.placaServicio)) {
                    if (!expreg2.test($scope.datosFormulario.placaServicio)) {
                        if (!expreg3.test($scope.datosFormulario.placaServicio)) {
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
                servicio = $scope.ulrbase + "cambioservicio/consulta/" + $scope.datosFormulario.placaServicio;
                $http.get(servicio).success(function (data, status, headers, config) {
                    if (data.codmensaje === "OK") {
                        $scope.resultadoConsultaServicio = data.object;
                        $scope.mostrarInfoServicio();
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
    }]);
