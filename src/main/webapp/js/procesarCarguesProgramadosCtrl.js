(function () {
    'use strict';

    angular.module('SagirApp.controllers').controller('procesarCarguesProgramadosCtrl', procesarCarguesProgramadosController);

    procesarCarguesProgramadosController.$inject = ['$scope', '$http', 'expresionRegular'];

    function procesarCarguesProgramadosController($scope, $http, expresionRegular) {
        // Definición Variables
        $scope.resultadoConsulta = {};
        $scope.resultado = {};
        $scope.newsoporteRNA = {};
        $scope.datosFormulario = {};
        $scope.organismosTransito = {};
        var servicio;

        $scope.pantallaPrincipal = true;

        $scope.pageSizeGeneral = 10;
        $scope.currentPageGeneral = 1;

        // Definición de funciones
        $scope.cargarInformacion = cargarInformacion;
        $scope.abrirProcesar = abrirProcesar;
        $scope.procesar = procesar;
        $scope.cancelarProgramar = cancelarProgramar;
        $scope.cancelar = cancelar;
        $scope.programar = programar;
        $scope.regresar = regresar;
        $scope.listarOrganismosTransito = listarOrganismosTransito;
        $scope.importarRNA = importarRNA;

        $scope.mostrarMensajeSuccess = mostrarMensajeSuccess;
        $scope.mostrarMensajeInfo = mostrarMensajeInfo;
        $scope.mostrarMensajeError = mostrarMensajeError;
        $scope.ocultarMensaje = ocultarMensaje;
        $scope.limpiarMensaje = limpiarMensaje;
        $scope.generarReporte = generarReporte;


        //
        $scope.listarOrganismosTransito();
        $scope.cargarInformacion();
        
        $(".fileinput-remove-button").mouseup(function () {
            $('#files').fileinput('reset');
            $('#archivo').val('');
            $('#nombre').val('');
            $('.percent').text('0%');
        });
        

        // Implementación
        function cargarInformacion() {
            $('#dlgEspera').modal();

            $http.get('./webresources/programacionCargue/consulta')
                    .success(function (data, status, headers, config) {
                        if (data.codmensaje === 'OK') {
                            $scope.resultadoConsulta = data.object;

                            $('#dlgEspera').modal('hide');
                        } else {
                            $scope.mostrarMensajeInfo(data.mensaje);
                        }
                    }).error(function (data, status, headers, config) {
                $scope.mostrarMensajeError('Error consultando la programación de cargues. Por favor intente mas tarde.');
            });
        }

        function abrirProcesar() {
            if ($scope.resultadoConsulta) {
                $('#dlgEspera').modal('hide');
                $('#msgConfirm').html('Esta acción ejecuta los cargues programados másivamente, ¿está seguro que desea continuar?');
                $('#dlgConfirm').modal();
            } else {
                $scope.mostrarMensajeInfo('No existe cargues programados.');
            }
        }

        function procesar() {
            $('#dlgEspera').modal();
            $http.get('./webresources/programacionCargue/procesamiento')
                    .success(function (data, status, headers, config) {
                        if (data.codmensaje === 'OK') {
                            $scope.mostrarMensajeSuccess(data.mensaje);
                            $scope.resultado = data.object;

                            data.codCargaProgramado.forEach(function (valor){
                                console.log(">>>>"+valor);
                                var valor2 = valor.codCarga;
                                $scope.generarReporte(valor2);
                            });
                            $scope.cargarInformacion();
                            $('#dlgEspera').modal('hide');
                        } else {
                            $scope.mostrarMensajeInfo(data.mensaje);
                        }
                    }).error(function (data, status, headers, config) {
                $scope.mostrarMensajeError('Error procesando los cargues programados. Por favor intente mas tarde.');
            });
        }

        function cancelar() {

        }
        
        

        function importarRNA() {
            $scope.errores = {};
            $scope.newsoporteRNA = {};

            $scope.newsoporteRNA.organismoTransito = $scope.datosFormulario.idSecretaria;
            $scope.newsoporteRNA.tipoRegistro = '1';
            $scope.newsoporteRNA.tipoCargue = $scope.datosFormulario.tipoCargue;
            $scope.newsoporteRNA.archivoEmpaquetado = $('#archivo').val();
            $scope.newsoporteRNA.nombreArchivoEmpaquetado = $('#nombre').val();
            $scope.newsoporteRNA.tutela = '2';

            console.log(JSON.stringify($scope.newsoporteRNA));

            var errorval = false;
            if (!$scope.datosFormulario.idSecretaria || $scope.datosFormulario.idSecretaria === "") {
                errorval = true;
                $scope.errores.idSecretaria = 'Campo obligatorio';
            }

            if (!$scope.datosFormulario.tipoCargue || $scope.datosFormulario.tipoCargue === "") {
                errorval = true;
                $scope.errores.tipoCargue = 'Campo obligatorio';
            }

            if ($scope.newsoporteRNA.nombreArchivoEmpaquetado === '[object Object]' || $scope.newsoporteRNA.nombreArchivoEmpaquetado === "") {
                $scope.errores.fileadjunto = 'El archivo es obligatorio.';
                errorval = true;
            }

            if (errorval === false) {
                $('#dlgEspera').modal();
                $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
                $http.post('./webresources/programacionCargue/uploadRNA', $scope.newsoporteRNA,
                        {}).success(function (data, status, headers, config) {

                    if (data.codmensaje === "OK") {
                        $scope.mostrarMensajeSuccess(data.mensaje);
                        $scope.resultado = data.object;
                        $('#files').fileinput('reset');
                        $('#archivo').val('');
                        $('#nombre').val('');
                        $('.percent').text('0%');
                        $scope.datosFormulario.tipoCargue = "";
                        $scope.datosFormulario.idSecretaria = "";

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

        function programar() {
            $scope.pantallaPrincipal = false;
            //angular.element($('#programarCargue')).scope().mostrarPantalla();
        }

        function regresar() {
            $scope.pantallaPrincipal = true;
        }

        function cancelarProgramar(actualizar, mensaje) {
            $scope.pantallaPrincipal = true;

            if (actualizar) {
                $scope.cargarInformacion();
                if (mensaje) {
                    $scope.mostrarMensajeSuccess(mensaje);
                }
            } else {
                if (mensaje) {
                    $scope.mostrarMensajeInfo(mensaje);
                }
            }
        }

        //////////////////////// UTILITARIOS ////////////////////////

        function mostrarMensajeSuccess(mensaje) {
            $scope.limpiarMensaje();

            $scope.mensajeSuccess = true;
            $('#msgSuccess').html(mensaje);
            $(".messages").fadeIn();

            $scope.ocultarMensaje();
        }

        function mostrarMensajeInfo(mensaje) {
            $scope.limpiarMensaje();

            $scope.mensajeInfo = true;
            $('#msgInfo').html(mensaje);
            $(".messages").fadeIn();

            $scope.ocultarMensaje();
        }

        function mostrarMensajeError(mensaje) {
            $scope.limpiarMensaje();

            $scope.mensajeError = true;
            $('#msgError').html(mensaje);
            $(".messages").fadeIn();

            $scope.ocultarMensaje();
        }

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

            $scope.mensajeSuccess = false;
            $scope.mensajeInfo = false;
            $scope.mensajeError = false;
        }

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
        
        function generarReporte(codCarga) {
            $('#dlgEspera').modal();
            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
            window.open('./webresources/listarboletin/generarboletin/' + codCarga + '.zip');
            $('#dlgEspera').modal('hide');
        };

    }
})();