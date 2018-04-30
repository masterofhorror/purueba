//----------controller reporteRNA
module.controller('controllerCambioPropietario', ['$scope', '$http', '$route', function ($scope, $http, $route) {
        console.log("inicializando app");
        $scope.datosFormulario = {};
        $scope.newsoporte = {};
        $scope.errores = {};
        $scope.verMsjOk = false;
        $scope.verMsjError = false;
        $scope.verConsulta = false;
        $scope.verHistorico = false;
        $scope.verDetalleHistorico = false;
        $scope.verBoletin = false;
        $scope.pageSize = 10;
        $scope.currentPage = 1;
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

        $scope.mostrarBoletin = function () {
            $scope.verConsulta = false;
            $scope.verHistorico = false;
            $scope.verBoletin = true;
            $scope.verDetalleHistorico = false;
        };

        $scope.mostrarHistorico = function () {
            $scope.verConsulta = false;
            $scope.verHistorico = true;
            $scope.verBoletin = false;
            $scope.verDetalleHistorico = false;
        };

        $scope.mostrarDetalleHistorico = function () {
            $scope.verConsulta = false;
            $scope.verHistorico = false;
            $scope.verBoletin = false;
            $scope.verDetalleHistorico = true;
        };

        $scope.mostrarCargados = function () {
            $scope.verConsulta = true;
            $scope.verHistorico = false;
            $scope.verBoletin = false;
            $scope.verDetalleHistorico = false;
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


        $scope.uploadSoporte = function () {

            $scope.errores = {};
            var errorval = false;
            var nombreArchivo = $('#nombre').val();
            if ($('#nombre').val().length === 0) {
                errorval = true;
                $scope.errores.fileadjunto = 'Campo obligatorio';
            } else {
                var expregNombreArchivo = /^[0-9]{7,8}(mprop.dat)|^[0-9]{7,8}(mprop.DAT)|^[0-9]{7,8}(MPROP.dat)|^[0-9]{7,8}(MPROP.DAT)|\\.p7z/;
                if (!expregNombreArchivo.test(nombreArchivo)) {
                    errorval = true;
                    $scope.errores.fileadjunto = 'El nombre del archivo no corresponde con el formato esperado.';
                }
            }
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
            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
            $scope.newsoporte.archivo = $('#archivo').val();
            $scope.newsoporte.nombre = $('#nombre').val();
            $scope.newsoporte.nroTicket = $scope.datosFormulario.nroTicket;
            console.log(JSON.stringify($scope.newsoporte));

            $http.post('./webresources/cargueArchivosProp/upload', $scope.newsoporte,
                    {}).success(function (data, status, headers, config) {
                if (data.estado === 'PROCESADO') {
                    $scope.loadCargas();
                    $('#dlgEspera').modal('hide');
                    $scope.newsoporte = {};
                    $("#files").val('');
                    $('#archivo').val('');
                    $('#nombre').val('');
                } else {
                    if (data.estado === 'RECHAZADO') {
                        $scope.mostrarMensajeError(data.mensaje);
                        $scope.loadCargas();
                        $('#dlgEspera').modal('hide');
                        $scope.newsoporte = {};
                        $("#files").val('');
                        $('#archivo').val('');
                        $('#nombre').val('');
                    }
                }
            }).error(function (data, status, headers, config) {
                $scope.mostrarMensajeError("Error en los servicios, status " + status);
            });


            console.log(JSON.stringify($scope.newsoporte));
            $('#files').val("");
            $('#files').fileinput('reset');
        };

        $scope.loadCargas = function () {
            $http.get('./webresources/estadocargue').
                    success(function (data, status, headers, config) {
                        $scope.resultados = data;
                        $scope.mostrarCargados();
                    }).error(function (data, status, headers, config) {
                $scope.mostrarMensajeError("Error en los servicios, status " + status);
            });
        };

//        $scope.loadCargas();

        $scope.boletin = function (idCarga) {
            $http.get('./webresources/boletin/cambioprop/' + idCarga).
                    success(function (data, status, headers, config) {
                        if (data.codmensaje === "OK") {
                            $scope.resultadoBoletin = data.object;
                            $scope.mostrarBoletin();
                        } else {
                            $scope.mensaje = data.mensaje;
                        }
                    }).error(function (data, status, headers, config) {
                $scope.mostrarMensajeError("Error en los servicios, status " + status);
            });
        };

        $scope.mostrarFecha = function () {
            $scope.mostrarHistorico();
        };

        $scope.historicoCargue = function () {
            $scope.error = {};
            var error = false;
            var fechaHistorico = $('#fechaInicial').val();
            if (!fechaHistorico || fechaHistorico === 'dd/mm/yyyy') {
                error = true;
                $scope.errores.fecha = 'Campo obligatorio';
            }
            if (!error) {
                $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
                $scope.datosFormulario.fechaInicial = fechaHistorico;
                $http.post('./webresources/boletin/archivoshistoricos', $scope.datosFormulario, {}).
                        success(function (data, status, headers, config) {
                            if (data.codmensaje === "OK") {
                                $scope.resultadoHistorico = data.object;
                                $scope.mostrarDetalleHistorico();
                                $('#dlgEspera').modal('hide');
                                $('#fechaInicial').val('dd/mm/yyyy');
                            } else {
                                if (data.codmensaje === "ERROR") {
                                    $scope.mostrarMensajeError(data.mensaje);
                                }
                            }
                        })
                        .error(function (data, status, headers, config) {
                            $scope.mostrarMensajeError("Error en los servicios, status " + status);
                        });
            }
        };


    }]);

