//----------controller reporteRNA
module.controller('consultaRangoPlacaCtrl', ['$scope', '$http', '$route', function ($scope, $http, $route) {
        console.log("inicializando app");
        $scope.datosFormulario = {};
        $scope.pageSize = 30;
        $scope.currentPage1 = 1;
        $scope.currentPage2 = 1;
        $scope.currentPage3 = 1;
        
        $scope.pageChangeHandler = function(num) {
            console.log('meals page changed to ' + num);
        };      
        
        $scope.ulrbase = './webresources/consultarangoplaca/';
        $scope.tipo;
        $scope.errores = {};
        $scope.verMsjOk = false;
        $scope.verMsjError = false;
        $scope.ocultarMensaje = ocultarMensaje;
        $scope.limpiarMensaje = limpiarMensaje;
        $scope.verRangoPlacas = false;
        $scope.verRangoTotal = false;
        $scope.verRangoProduccion = false;

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
            var errorval = false;
            
            
            if (!$scope.datosFormulario.placaInicial){
                $scope.errores.placaInicial = 'Este campo es obligatorio.';
                errorval = true;
            }
            
            if (!$scope.datosFormulario.placaFinal){
                $scope.errores.placaFinal = 'Este campo es obligatorio.';
                errorval = true;
            }
            
            if (errorval === false){
                rangoplaca(parametro);
            }
            
            
        }
        
        //$scope.rangoplaca = function (parametro) {
        function rangoplaca(parametro) {
                        $scope.resultados = [];
                        //$scope.tipo = parametro;
                        var servicio;
                        var urlfinal;
                        if (parametro === 1) {
                            urlfinal = "rangoplaca/";
                            servicio = $scope.ulrbase + urlfinal + $scope.datosFormulario.placaInicial + '/' + $scope.datosFormulario.placaFinal;
                        } else if (parametro === 2) {
                            urlfinal =  "rangoplacatotal/";
                            servicio = $scope.ulrbase + urlfinal;
                        } else if (parametro === 3) {
                            urlfinal =  "rangoplacaRNA/";
                            servicio = $scope.ulrbase + urlfinal;
                        }
                        ;

                        $http.get(servicio).
                                success(function (data, status, headers, config) {
                                    if (data.codmensaje === "OK") {
                                        $scope.resultados = data.object;
                                        if (parametro === 1){
                                            $scope.verRangoPlacas = true;
                                        }
                                        
                                        if (parametro === 2){
                                            $scope.verRangoTotal = true;
                                        }
                                        
                                        if (parametro === 3){
                                            $scope.verRangoProduccion = true;
                                        }
                                        
                                    } else {
                                        if (data.codmensaje === "ERROR") {
                                            $scope.mostrarMensajeError(data.mensaje);
                                        }
                                    }

                                }).error(function (data, status, headers, config) {
                            //alert(status);
                            if (data === null) {
                                $scope.mostrarMensajeError('Debe ingresar un número de placa.');
                            }
                            $scope.mostrarMensajeError('Error al consultar la información, por favor intente más tarde.');
                        });
                    };

    }]);

