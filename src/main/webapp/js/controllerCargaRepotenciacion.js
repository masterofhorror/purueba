
module.controller('controllerCargaRepotenciacion', ['$scope', '$http', '$route', function ($scope, $http, $route) {
        console.log("inicializando app");
        $scope.newsoporte = {};
        $scope.datosFormulario = {};
        $scope.verMsjOk = false;
        $scope.verMsjError = false;
        $scope.verConsultaProd = false;
        $scope.verDetalle = false;
        $scope.verMensaje = false;
        $scope.ulrbase = "./webresources/carguerepotencion/";
        $scope.pageSize = 100;
        $scope.currentPage = 1;
        var servicio;
        $scope.ocultarMensaje = ocultarMensaje;
        $scope.limpiarMensaje = limpiarMensaje;
        $scope.listarOrganismosTransito = listarOrganismosTransito;
        $scope.listarOrganismosTransito();

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

        $scope.mostrarConsProd = function () {
            $scope.verConsultaProd = true;
            $scope.verDetalle = false;
        };

        $scope.mostrarDetalle = function () {
            $scope.verConsultaProd = false;
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

        $scope.consulta = function () {

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
                servicio = $scope.ulrbase + "consulta/" + $scope.datosFormulario.placa;
                $http.get(servicio)
                        .success(function (data, status, headers, config) {
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


        $scope.sinSolicitudes = function () {
            $scope.mostrarMensajeError("El vehículo ingresado no registra solicitudes ante el RUNT");
        };

        $scope.consultaDetalle = function () {
            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
            servicio = $scope.ulrbase + "consultadetalle/" + $scope.datosFormulario.placa;
            $http.get(servicio)
                    .success(function (data, status, headers, config) {
                        if (data.codmensaje === "OK") {
                            $scope.resultadoDetalle = data.object;
                            $scope.mostrarDetalle();
                            $('#dlgEspera').modal('hide');
                        } else {
                            if (data.codmensaje === "ERROR") {
                                $scope.mostrarMensajeError(data.mensaje);
                            }
                        }
                    }).error(function (data, status, headers, config) {
                $scope.mostrarMensajeError("Error en los servicios, status " + status);
            });
        };

        $scope.uploadSoporte = function () {

            $scope.errores = {};

            var errorval = false;
            if (!$scope.datosFormulario.nroTicket) {
                errorval = true;
                $scope.errores.nroTicket = 'Campo obligatorio';
            } else {
                var expregTicket = /^INC[0-9]{11}/;
                if (!expregTicket.test($scope.datosFormulario.nroTicket)) {
                    errorval = true;
                    $scope.errores.nroTicket = 'El número de ticket ingresado no es valido.';
                }
            }
            var nombreArchivo = $('#nombre').val();
            if (!nombreArchivo) {
                errorval = true;
                $scope.errores.fileadjunto = 'Campo obligatorio';
            } else {
                var expregNombreArchivo = /^[0-9]{7,8}(repot.dat)|^[0-9]{7,8}(repot.DAT)|^[0-9]{7,8}(REPOT.dat)|^[0-9]{7,8}(REPOT.DAT)|\\.p7z/;
                if (!expregNombreArchivo.test(nombreArchivo)) {
                    errorval = true;
                    $scope.errores.fileadjunto = 'El nombre del archivo no corresponde con el formato esperado.';
                }
            }
            if (!$scope.datosFormulario.idSecretaria) {
                errorval = true;
                $scope.errores.idSecretaria = 'Campo obligatorio';
            }

            var error = true;
            if ($('#nombre').val().length > 0) {
                error = false;
            } else {
                $scope.errores.fileadjunto = $filter('translate')('HDESK.VALID.CAMPO_REQ') + " " + $filter('translate')('HDESK.VALID.CAMPO_SIZE') + $scope.fileProperties.size + " bytes";
            }
            if (error) {
                return;
            }

            if (errorval) {
                return;
            }
            var expregTicket = /^INC[0-9]{11}/;
            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
            if (expregTicket.test($scope.datosFormulario.nroTicket)) {
                $scope.newsoporte.archivo = $('#archivo').val();
                $scope.newsoporte.nombre = $('#nombre').val();
                $scope.newsoporte.tipoCargue = 'REPO';
                $scope.newsoporte.nroTicket = $scope.datosFormulario.nroTicket;
                $scope.newsoporte.idSecretaria = $scope.datosFormulario.idSecretaria.idSecretaria;
                console.log(JSON.stringify($scope.newsoporte));

                $http.post('./webresources/cargueArchivosServices/upload', $scope.newsoporte,
                        {}).success(function (data, status, headers, config) {
                    if (data.estado === 'SIN_PROCESAR') {
                        $scope.mostrarMensajeExito("Archivo cargado exitosamente, pendiente procesamiento.");
                        $scope.loadCargas();
                        $('#dlgEspera').modal('hide');
                    } else {
                        if (data.estado === 'RECHAZADO') {
                            $scope.mostrarMensajeError(data.mensaje);
                        }
                    }
                }).error(function (data, status, headers, config) {
                    $scope.mostrarMensajeError = "Error en los servicios, status " + status;
                });


                console.log(JSON.stringify($scope.newsoporte));
                $('#files').val("");
                $('#files').fileinput('reset');
            }
        };

        $scope.procesar = function () {

            $scope.errores = {};
            var error = false;
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

            if (!$scope.datosFormulario.idSecretaria) {
                error = true;
                $scope.errores.idSecretaria = 'Campo obligatorio';
            }

            if (error) {
                return;
            }
            var expregTicket = /^INC[0-9]{11}/;
            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
            if (expregTicket.test($scope.datosFormulario.nroTicket)) {
                servicio = $scope.ulrbase + "procesararchivo/" + $scope.datosFormulario.nroTicket + "/" + $scope.datosFormulario.idSecretaria.idSecretaria;
                $http.get(servicio)
                        .success(function (data, status, headers, config) {
                            if (data.codmensaje === "OK") {
                                $scope.mostrarMensajeExito(data.mensaje);
                                $scope.generarReporte($scope.datosFormulario.nroTicket);
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

        /***
         * Enlista los Organismos de Tránsito que se encuentran en la tabla MIGRARUNT1.ORGANISMOS_TRANSITO
         * @returns {undefined}
         */
        function listarOrganismosTransito() {
            servicio = "./webresources/common/listarot";
            $http.get(servicio).success(function (data, status, headers, config) {
                if (data.codmensaje === "OK") {
                    $scope.listarOt = data.object;
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

        $scope.generarReporte = function (nroTicket) {
            $('#dlgEspera').modal();
            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
            window.open($scope.ulrbase + 'generarpdf/' + nroTicket + '.pdf');
            $('#dlgEspera').modal('hide');
        };
    }]);
