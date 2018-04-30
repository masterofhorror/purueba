//----------controller reporteRNA
module.controller('reporteRNACtrl', ['$scope', '$http', '$route', function ($scope, $http, $route) {
        console.log("inicializando app");
        $scope.datosFormulario = {};
        $scope.pageSize = 5;
        $scope.currentPage = 1;
        $scope.ulrbase = './webresources/';
        $scope.tipo;
        $scope.verMsjOk = false;
        $scope.verMsjError = false;
        $scope.currentPage1 = 1;
        $scope.currentPage2 = 1;
        $scope.currentPage3 = 1;
        $scope.currentPage4 = 1;
        $scope.ocultarMensaje = ocultarMensaje;
        $scope.limpiarMensaje = limpiarMensaje;

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

        $scope.consultar = function (parametro) {

            $scope.resultados = [];
            var url1 = 'consultamigrarna/';
            var url2 = 'consultarna/';
            var servicio;
            var urlfinal;
            if (parametro === 1) {
                urlfinal = url1 + "consultaporplaca/";
            } else if (parametro === 2) {
                urlfinal = url1 + "consultaintermedia/";
            } else if (parametro === 3) {
                urlfinal = url1 + "consultarechazo/";
            } else if (parametro === 4) {
                urlfinal = url1 + "consultaboletin/";
            } else if (parametro === 5) {
                urlfinal = url1 + "consultafinalmigra/";
            } else if (parametro === 6) {
                urlfinal = url2 + "consultarporplaca/";
            }
            ;

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
            servicio = $scope.ulrbase + urlfinal + $scope.datosFormulario.placa;

            $http.get(servicio).
                    success(function (data, status, headers, config) {
                        if (data.codmensaje === "OK") {
                            $scope.tipo = parametro;
                            $scope.resultados = data.object;
                            $('#dlgEspera').modal('hide');
                        } else {
                            if (data.codmensaje === "ERROR") {
                                $scope.mostrarMensajeError(data.mensaje);
                                $scope.tipo === 0;
                            }
                        }

                    }).error(function (data, status, headers, config) {
                //alert(status);  
                alert('Error al consultar la información, por favor intente más tarde.');
            });
        };
    }]);

