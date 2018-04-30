var expresion_fecha = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;

module.controller('cargueCiaCtrl', ['$scope', '$http', '$route', function ($scope, $http, $route) {
        console.log("inicializando app");
        $scope.newsoporte = {};
        $scope.datosFormulario = {};
        $scope.pageSize = 50;

        $scope.fecha = new Date();
        $scope.fechaActual = '';

        $scope.tipo;
        $scope.errores = {};
        $scope.verMsjOk = false;
        $scope.verMsjError = false;
        $scope.verResultado = false;
        //$scope.verGenerarBol = true;
        $scope.verListar = false;
        $scope.ocultarMensaje = ocultarMensaje;
        $scope.limpiarMensaje = limpiarMensaje;
        $scope.listarCia = listarCia;
        $scope.ulrbase = "./webresources/cargueCia/";

        $scope.buscar = buscar;
        $scope.boletin = boletin;
        $scope.impBolUnico = impBolUnico;
        $scope.bolUnico = bolUnico;
        $scope.boletinIndividual = boletinIndividual;

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

        listarCia();

        $scope.fechaActual = ('0' + $scope.fecha.getDate()).slice(-2) + '/' + ('0' + ($scope.fecha.getMonth() + 1)).slice(-2) + '/' + $scope.fecha.getFullYear();

        $(".fileinput-remove-button").mouseup(function () {
            $('#files').fileinput('reset');
            $('#archivo').val('');
            $('#nombre').val('');
            $('.percent').text('0%');
        });

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

        function listarCia() {
            servicio = "./webresources/cargueCia/listarcia";
            $http.get(servicio).success(function (data, status, headers, config) {
                if (data.codmensaje === "OK") {
                    $scope.listarCia = data.object;
                } else {
                    if (data.codmensaje === "ERROR") {
                        $scope.mostrarMensajeError(data.mensaje);
                    }
                }
            }).error(function (data, status, headers, config) {
                $scope.mostrarMensajeError("Error al consultar la información");
            });

        }

        function buscar(parametro) {
            $scope.errores = {};
            $scope.tipo = parametro;
            //$scope.resultado = [];
            var errorval = false;

            $scope.newsoporte.archivo = $('#archivo').val();
            $scope.newsoporte.nombre = $('#nombre').val();
            $scope.newsoporte.tipoCargue = 'CIA';
            $scope.newsoporte.fechaEnvio = $scope.datosFormulario.fechaEnvio;

            console.log(JSON.stringify($scope.newsoporte));

            if (!$scope.datosFormulario.fechaEnvio || $scope.datosFormulario.fechaEnvio === "") {
                $scope.errores.fechaEnvio = 'Campo obligatorio';
                errorval = true;
            }

            if ($scope.newsoporte.nombre === '[object Object]' || $scope.newsoporte.nombre === "") {
                $scope.errores.fileadjunto = 'El archivo es obligatorio.';
                errorval = true;
            }
            
            if ($scope.datosFormulario.nombreCia === ""){
                $scope.errores.nombreCia = 'Campo obligatorio';
                errorval = true;
            }

            try {
                $scope.newsoporte.nitCia = $scope.datosFormulario.nombreCia.nit;
            } catch (err) {
                $scope.errores.nombreCia = 'Campo obligatorio';
                errorval = true;
            }

            if (errorval === false) {
                $('#dlgEspera').modal();
                $http.post('./webresources/cargueArchivosCiaServices/uploadCia', $scope.newsoporte,
                        {}).success(function (data, status, headers, config) {

                    if (data.codmensaje === "OK") {
                        $scope.mostrarMensajeExito("Archivo cargado exitosamente.");
                        $scope.resultado = data.object;
                        $scope.verResultado = true;
                        $scope.generarReporte(data.object.codCarga);
                        $scope.datosFormulario.nombreCia = "";
                        $scope.datosFormulario.fechaEnvio = "";
                        $scope.newsoporte.nombre = "";
                        $('#files').fileinput('reset');
                        $('#archivo').val('');
                        $('#nombre').val('');
                        $('.percent').text('0%');

                    } else {
                        if (data.codmensaje === "ERROR") {
                            $scope.mostrarMensajeError(data.mensaje);
                            $scope.resultado = data.object;
                            //$scope.verResultado = true;
                        }
                    }

                }).error(function (data, status, headers, config) {
                    alert("Error en los servicios, status " + status);
                });

            }
        }

        function boletin(parametro) {
            codCarga = parametro;
            $scope.generarReporte(parametro);
        }

        function impBolUnico(parametro) {
            var error = true;
            if (!parametro) {
                $scope.errores.bolUnico = 'Campo obligatorio';
                error = false;
            }

            if (error === true) {
                codCarga = parametro;
                $scope.boletinIndividual(codCarga);
            }
        }

        function bolUnico(parametro) {
            if ($scope.verListar) {
                $scope.verListar = false;
            } else {
                $scope.verListar = true;
            }
        }

        $scope.generarReporte = function (codCarga) {

            $('#dlgEspera').modal();
            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
            window.open($scope.ulrbase + 'reportePorCargue/' + codCarga);
            //($scope.ulrbase + 'generarpdf/' + codCarga + '.pdf');
            $('#dlgEspera').modal('hide');
        };


        function boletinIndividual(codCarga) {
            $('#dlgEspera').modal();
            servicio = "./webresources/cargueCia/consultacargacia/" + codCarga;
            $http.get(servicio).success(function (data, status, headers, config) {
                if (data.codmensaje === "OK") {
                    $scope.generarReporte(codCarga);
                    $scope.mostrarMensajeExito("Boletin generado con exito.");
                    $scope.datosFormulario.bolUnico = "";
                    $('#dlgEspera').modal('hide');
                } else {
                    if (data.codmensaje === "ERROR") {
                        $scope.mostrarMensajeError(data.mensaje);
                        $scope.datosFormulario.bolUnico = "";
                        $scope.datosFormulario.nombreCia = "";

                    }
                }
            }).error(function (data, status, headers, config) {
                $scope.mostrarMensajeError("Error al consultar la información");
            });

        }

    }]);

