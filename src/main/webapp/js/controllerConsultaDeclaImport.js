module.controller('consultaDeclaracionImportacionCtrl', ['$scope', '$http', '$route', function ($scope, $http, $route) {
        console.log("inicializando app");
        $scope.datosFormulario = {};
        $scope.pageSize = 50;

        $scope.ulrbase = './webresources/consultadadeclaimport/';

        $scope.tipo;
        $scope.errores = {};
        $scope.verMsjOk = false;
        $scope.verMsjError = false;
        $scope.verResultado = false;
        $scope.ocultarMensaje = ocultarMensaje;
        $scope.limpiarMensaje = limpiarMensaje;

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

            if ($scope.datosFormulario.declaracion) {
                $scope.verResultado = true;
                
                servicio = $scope.ulrbase + $scope.datosFormulario.declaracion;
                
                $http.get(servicio).
                                success(function (data, status, headers, config) {
                                    
                                    if (data.codmensaje === "OK") {
                                        $scope.resultado = data.object;
                                        
                                    } else {
                                        if (data.codmensaje === "ERROR") {
                                            $scope.mostrarMensajeError(data.mensaje);
                                        }
                                    }

                                }).error(function (data, status, headers, config) {
                            //alert(status);
                            if (data === null) {
                                $scope.mostrarMensajeError('Debe ingresar un número de declaracion.');
                            }
                            $scope.mostrarMensajeError('Error al consultar la información, por favor intente más tarde.');
                        });
            } else {
                $scope.errores.declaracion = 'Este campo es obligatorio.';
            }

        }
    }]);