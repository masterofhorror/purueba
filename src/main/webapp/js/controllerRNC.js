module.controller('reporteRNCCtrl', ['$scope', '$http', '$route', function ($scope, $http, $route) {
        console.log("inicializando app");
        $scope.datosFormulario = {};
        $scope.verMsjError = false;
        $scope.pageSize = 5;
        $scope.currentPage = 1;
        $scope.ulrbase = './webresources/';
        $scope.tipo;
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
            
            var url1 = 'consultamigrarnc/';
//            var url2 = 'consultarnc/';
            var servicio;
            var urlfinal;
            if (parametro === 1) {
                urlfinal = url1 + "rncmigra/";
            } else if (parametro === 2) {
                urlfinal = url1 + "rncintermedia/";
            } else if (parametro === 3) {
                urlfinal = url1 + "rncrechazos/";
            } else if (parametro === 4) {
                urlfinal = url1 + "rncfinal/";
            }
            ;
           
            $scope.errores = {};
            var error = false;
            if (!$scope.datosFormulario.tipodoc) {
                error = true;
                $scope.errores.tipodoc = 'Campo obligatorio';
            }
            if (!$scope.datosFormulario.nrodocumento) {
                error = true;
                $scope.errores.nrodocumento = 'Campo obligatorio';
            }
            var regExpNum = /^[0-9]+$/;
            if(!regExpNum.test($scope.datosFormulario.nrodocumento)){
                error = true;
                $scope.errores.nrodocumento = 'Solo se admiten caracteres de tipo númericos';
            }

            if (error) {
                return;
            }
             $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
            servicio = $scope.ulrbase + urlfinal + $scope.datosFormulario.tipodoc + "/" + $scope.datosFormulario.nrodocumento;
//            console.log(servicio);
            $http.get(servicio).
                    success(function (data, status, headers, config) {
                        if (data.codmensaje === "OK") {
                            $scope.resultados = data.object;
                            $('#dlgEspera').modal('hide');
                            $scope.tipo = parametro;
                        } else {
                            if (data.codmensaje === "ERROR") {
                                $scope.mostrarMensajeError(data.mensaje);
                            }

                        }

                    }).error(function (data, status, headers, config) {
                //alert(status);

                $scope.mostrarMensajeError('Error al consultar la información, por favor intente más tarde.');
            });
        };
    }]);

