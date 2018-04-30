module.controller('carguePuntualCtrl', ['$scope', '$http', '$route', function ($scope, $http, $route) {
        console.log("inicializando app");
        $scope.datosFormulario = {};
        $scope.pageSize = 50;

        $scope.ulrbase = './webresources/carguepuntual/';

        $scope.tipo;
        $scope.errores = {};
        $scope.verMsjOk = false;
        $scope.verMsjError = false;
        $scope.verResultado = false;
        $scope.ocultarMensaje = ocultarMensaje;
        $scope.limpiarMensaje = limpiarMensaje;
        $scope.aceptarEliminar = aceptarEliminar;

        $scope.buscar = buscar;

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

        function buscar(parametro) {
            $scope.errores = {};
            $scope.tipo = parametro;
            $scope.resultado = [];
            var servicio;

            if (parametro !== 3) {
                if ($scope.datosFormulario.placa) {
                    if (parametro === 1) {
                        servicio = $scope.ulrbase + "cargue/" + $scope.datosFormulario.placa;

                        $http.get(servicio).
                                success(function (data, status, headers, config) {
                                    if (data.codmensaje === "OK") {
                                        $scope.verResultado = true;
                                        $scope.mostrarMensajeExito(data.mensaje);
                                        $scope.resultado = data.object;
                                    } else {
                                        if (data.codmensaje === "ERROR") {
                                            $scope.mostrarMensajeError(data.mensaje);
                                        }
                                    }

                                }).error(function (data, status, headers, config) {
                            //alert(status);
                            if (data === null) {
                                $scope.mostrarMensajeError('La placa es un dato obligatorio.');
                            }
                            $scope.mostrarMensajeError('Error al consultar la información, por favor intente más tarde.');
                        });

                    }

                    if (parametro === 2) {
                        servicio = $scope.ulrbase + "marcar/" + $scope.datosFormulario.placa;

                        $http.get(servicio).
                                success(function (data, status, headers, config) {
                                    if (data.codmensaje === "OK") {
                                        $scope.verResultado = true;
                                        $scope.mostrarMensajeExito(data.mensaje);
                                        $scope.resultado = data.object;
                                    } else {
                                        if (data.codmensaje === "ERROR") {
                                            $scope.mostrarMensajeError(data.mensaje);
                                        }
                                    }

                                }).error(function (data, status, headers, config) {
                            //alert(status);
                            if (data === null) {
                                $scope.mostrarMensajeError('La placa es un dato obligatorio.');
                            }
                            $scope.mostrarMensajeError('Error al consultar la información, por favor intente más tarde.');
                        });
                    }

                } else {
                    $scope.errores.placa = 'Este campo es obligatorio.';
                }
            } else {
                $('#dlgConfirmar').modal();
            }
        }


        function aceptarEliminar() {
            $('#dlgConfirmar').modal('hide');
            var servicio;
            servicio = $scope.ulrbase + "eliminaexport";

            $http.get(servicio).
                    success(function (data, status, headers, config) {
                        if (data.codmensaje === "OK") {
                            $scope.mostrarMensajeExito(data.mensaje);

                        } else {
                            if (data.codmensaje === "ERROR") {
                                $scope.mostrarMensajeError(data.mensaje);
                            }
                        }

                    }).error(function (data, status, headers, config) {
                //alert(status);
                if (data === null) {
                    $scope.mostrarMensajeError('Error al consultar la información, por favor intente más tarde..');
                }
                $scope.mostrarMensajeError('Error al consultar la información, por favor intente más tarde.');
            });
        }

    }]);