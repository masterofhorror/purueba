
module.controller('controllerlistaBoletin', ['$scope', '$http', '$route', function ($scope, $http, $route) {
        console.log("inicializando app");
        $scope.datosFormulario = {};
        $scope.verMsjOk = false;
        $scope.verMsjError = false;
        $scope.verCodCarga = false;
        $scope.verTipoRegistro = false;
        $scope.verIdSecretaria = false;
        $scope.verRangoFecha = false;
        $scope.verResultado = false;
        $scope.ulrbase = './webresources/listarboletin';
        $scope.pageSize = 10;
        $scope.currentPage = 1;
        $scope.listarOt = [];
        $scope.currentPage = 1;
        var servicio;
        $scope.ocultarMensaje = ocultarMensaje;
        $scope.limpiarMensaje = limpiarMensaje;

        $scope.pageChangeHandler = function (num) {
            console.log('meals page changed to ' + num);
        };
        $scope.listarOrganismosTransito = listarOrganismosTransito;
        $scope.consultaPorTipoRegistro = consultaPorTipoRegistro;
        $scope.consultaPorRangoFecha = consultaPorRangoFecha;
        $scope.consultaPorCodCarga = consultaPorCodCarga;
        $scope.consultaPorIdOt = consultaPorIdOt;
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

        $scope.codCarga = function () {
            $scope.verCodCarga = true;
            $scope.verTipoRegistro = false;
            $scope.verIdSecretaria = false;
            $scope.verRangoFecha = false;
            $scope.verResultado = false;
        };

        $scope.tipoRegistro = function () {
            $scope.verCodCarga = false;
            $scope.verTipoRegistro = true;
            $scope.verIdSecretaria = false;
            $scope.verRangoFecha = false;
            $scope.verResultado = false;
        };

        $scope.secretaria = function () {
            $scope.verCodCarga = false;
            $scope.verTipoRegistro = false;
            $scope.verIdSecretaria = true;
            $scope.verRangoFecha = false;
            $scope.verResultado = false;
        };

        $scope.rangoFechas = function () {
            $scope.verCodCarga = false;
            $scope.verTipoRegistro = false;
            $scope.verIdSecretaria = false;
            $scope.verRangoFecha = true;
            $scope.verResultado = false;
        };

        $scope.mostrarResultado = function () {
            $scope.verCodCarga = false;
            $scope.verTipoRegistro = false;
            $scope.verIdSecretaria = false;
            $scope.verRangoFecha = false;
            $scope.verResultado = true;
        };

        /**
         * Consulta por el código de carga
         * @returns {undefined}
         */
        function consultaPorCodCarga() {

            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.codCarga) {
                error = true;
                $scope.errores.codCarga = 'Campo obligatorio';
            } else {
                var expreg = /^\d*$/;
                if (!expreg.test($scope.datosFormulario.codCarga)) {
                    error = true;
                    $scope.errores.codCarga = 'El código de carga es únicamente númerico';
                }
            }
            if (error) {
                return;
            }
            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
            servicio = $scope.ulrbase + "/carga/" + $scope.datosFormulario.codCarga;
            $http.get(servicio).success(function (data, status, headers, config) {
                if (data.codmensaje === "OK") {
                    $scope.resultadoCodCarga = data.object;
                    $scope.mostrarResultado();
                    $('#dlgEspera').modal('hide');
                    $scope.datosFormulario = {};
                } else {
                    if (data.codmensaje === "ERROR") {
                        $scope.mostrarMensajeError(data.mensaje);
                    }
                }
            }).error(function (data, status, headers, config) {
                $scope.mostrarMensajeError("Error al consultar la información, por favor intente más tarde.")
            });
        }
        ;

        /**
         * Consulta por el tipo de registro
         * @returns {undefined}
         */
        function consultaPorTipoRegistro() {

            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.tipoRegistro) {
                error = true;
                $scope.errores.tipoRegistro = 'Campo obligatorio';
            }
            if (error) {
                return;
            }
            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
            servicio = $scope.ulrbase + "/tiporegistro/" + $scope.datosFormulario.tipoRegistro;
            $http.get(servicio).success(function (data, status, headers, config) {
                if (data.codmensaje === "OK") {
                    $scope.resultadoCodCarga = data.object;
                    $scope.mostrarResultado();
                    $('#dlgEspera').modal('hide');
                    $scope.datosFormulario = {};
                } else {
                    if (data.codmensaje === "ERROR") {
                        $scope.mostrarMensajeError(data.mensaje);
                    }
                }
            }).error(function (data, status, headers, config) {
                $scope.mostrarMensajeError("Error al consultar la información, por favor intente más tarde.")
            });
        }
        ;


        /**
         * Consulta por un rango de fechas
         * @returns {undefined}
         */
        function consultaPorRangoFecha() {

            $scope.errores = {};
            var error = false;
            var fechaInicial = $('#fechaInicial').val();
            var fechaFinal = $('#fechaFinal').val();
            if (!fechaInicial || fechaInicial === 'dd/mm/yyyy') {
                error = true;
                $scope.errores.fechaInicial = 'Campo obligatorio';
            }

            if (!fechaFinal || fechaFinal === 'dd/mm/yyyy') {
                error = true;
                $scope.errores.fechaFin = 'Campo obligatorio';
            }

            if (fechaInicial > fechaFinal) {
                error = true;
                $scope.errores.fechaInicial = 'La fecha inicial debe ser menor a la fecha final';
            }
            if (!error) {
                $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
                $scope.datosFormulario.fechaInicial = fechaInicial;
                $scope.datosFormulario.fechaFinal = fechaFinal;
                $http.post($scope.ulrbase + '/rangofecha', $scope.datosFormulario, {})
                        .success(function (data, status, headers, config) {
                            if (data.codmensaje === "OK") {
                                $scope.resultadoCodCarga = data.object;
                                $scope.mostrarResultado();
                                $('#dlgEspera').modal('hide');
                                $('#fechaInicial').val('dd/mm/yyyy');
                                $('#fechaFinal').val('dd/mm/yyyy');
                            } else {
                                if (data.codmensaje === "ERROR") {
                                    $scope.mostrarMensajeError(data.mensaje);
                                }
                            }
                        }).error(function (data, status, headers, config) {
                    $scope.mostrarMensajeError("Error al consultar la información, por favor intente más tarde.")
                });
            }
        }
        ;


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

        /***
         * Consulta por id del Organismo de Tránsito seleccionado
         * @returns {undefined}
         */
        function consultaPorIdOt() {

            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.idSecretaria) {
                error = true;
                $scope.errores.idSecretaria = 'Campo obligatorio';
            }
            if (error) {
                return;
            }
            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
            servicio = $scope.ulrbase + '/idsecretaria/' + $scope.datosFormulario.idSecretaria;
            $http.get(servicio)
                    .success(function (data, status, headers, config) {
                        if (data.codmensaje === "OK") {
                            $scope.resultadoCodCarga = data.object;
                            $scope.mostrarResultado();
                            $('#dlgEspera').modal('hide');
                            $scope.datosFormulario = {};
                        } else {
                            if (data.codmensaje === "ERROR") {
                                $scope.mostrarMensajeError(data.mensaje);
                            }
                        }
                    }).error(function (data, status, headers, config) {
                $scope.mostrarMensajeError("Error al consultar la información, por favor intente más tarde.");
            });
        }
        ;

        $scope.generarReporte = function (codCarga) {
            $('#dlgEspera').modal();
            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
            window.open($scope.ulrbase + '/generarboletin/' + codCarga + '.zip');
            $('#dlgEspera').modal('hide');
        }; 

    }]);
