module.controller('controllerCambioResidencia', ['$scope', '$http', '$route', function ($scope, $http, $route) {
        console.log("inicializando app");
        $scope.datosFormulario = {};
        $scope.verMsjOk = false;
        $scope.verMsjError = false;
        $scope.verConsulta = false;
        $scope.verMensaje = false;
        $scope.ulrbase = "./webresources/";
        $scope.pageSize = 10;
        $scope.currentPage = 1;
        var servicio;
        $scope.ocultarMensaje = ocultarMensaje;
        $scope.limpiarMensaje = limpiarMensaje;
        $scope.cambioResidencia = cambioResidencia;

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

        function cambioResidencia() {

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

            if (error) {
                return;
            }
            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
            servicio = $scope.ulrbase + "cambioresidencia/" + $scope.datosFormulario.placa + "/" + $scope.datosFormulario.nroTicket;
            $http.get(servicio).
                    success(function (data) {
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
        ;

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
            if (error) {
                return;
            }
            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
            servicio = $scope.ulrbase + "estadovehiculo/consultaestadovehiculo/" + $scope.datosFormulario.placa;
            $http.get(servicio).
                    success(function (data, status, headers, config) {
                        if (data.codmensaje === "OK") {
                            $scope.resultadoConsulta = data.object;
                            $scope.mostrarInfo();
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
        ;
    }]);
