
module.controller('procesoMigracionCtrl', ['$scope', '$http', '$route', function ($scope, $http, $route) {
        console.log("inicializando app");
        $scope.newsoporte = {};
        $scope.newsoporteRNA = {};
        $scope.newsoporteLicencias = {};
        $scope.newsoporteResidencias = {};
        $scope.newsoportePersonas = {};
        $scope.newsoporteRestricciones = {};
        $scope.datosFormulario = {};
        $scope.verMsjOk = false;
        $scope.verMsjError = false;
        $scope.verRNA = false;
        $scope.verRNC = false;
        $scope.verBotones = true;
        $scope.pageSize = 10;
        $scope.currentPage = 1;
        $scope.organismosTransito = {};
        $scope.cargue = {};
        $scope.arhivoCargado = false;
        var servicio;
        ////////LLAMADO DE FUNCIONES///////////////
        $scope.listarOrganismosTransito = listarOrganismosTransito;
        $scope.importarRNC = importarRNC;
        $scope.listarOrganismosTransito();
        $scope.ocultarMensaje = ocultarMensaje;
        $scope.limpiarMensaje = limpiarMensaje;
//        $scope.procesaArchivo = procesaArchivo;

        $scope.importarRNA = importarRNA;
        $scope.imprimirBoletinRnaProceso = imprimirBoletinRnaProceso;
        $scope.verImprimirBoletin = false;
        //////////////FUNCIONES//////////////////// 
        
        $(".fileinput-remove-button").mouseup(function () {
            $('#files').fileinput('reset');
            $('#archivo').val('');
            $('#nombre').val('');
            $('.percent').text('0%');
        });
        
        var handleFileSelect = function (evt) {
            var files = evt.target.files;
            var file = files[0];
            if (files && file) {
                if (file.type === 'application/x-zip-compressed') {
                    var reader = new FileReader();
                    reader.onload = function (readerEvt) {
                        var binaryString = readerEvt.target.result;
                        $scope.cargue.archivoEmpaquetado = btoa(binaryString);
                        $scope.cargue.nombreArchivoEmpaquetado = file.name;
                        if ($scope.cargue.nombreArchivoEmpaquetado === null) {
                            $scope.arhivoCargado = true;
                        }
                    };
                    reader.readAsBinaryString(file);
                    $scope.archivoNoValido = false;
                } else {
                    $scope.archivoNoValido = true;
                }
            } else {
                $scope.arhivoCargado = true;
            }
        };


        if (window.File && window.FileReader && window.FileList && window.Blob) {
            document.getElementById('archivoEmpaquetado').addEventListener('change', handleFileSelect, false);
        } else {
            $scope.mostrarMensaje('Navegador no soportado para la carga de archivos');
        }

        function importarRNA() {
            $scope.errores = {};

            $scope.newsoporteRNA.organismoTransito = $scope.datosFormulario.idSecretaria;
            $scope.newsoporteRNA.tipoRegistro = '1';
            $scope.newsoporteRNA.tipoCargue = $scope.datosFormulario.tipoCargue;
            $scope.newsoporteRNA.archivoEmpaquetado = $('#archivo').val();
            $scope.newsoporteRNA.nombreArchivoEmpaquetado = $('#nombre').val();
            $scope.newsoporteRNA.tutela = $scope.datosFormulario.tutela;



            console.log(JSON.stringify($scope.newsoporteRNA));

            var errorval = false;
            if (!$scope.datosFormulario.idSecretaria || $scope.datosFormulario.idSecretaria === "") {
                errorval = true;
                $scope.errores.idSecretaria = 'Campo obligatorio';
            }

            if (!$scope.datosFormulario.tipoCargue) {
                errorval = true;
                $scope.errores.tipoCargue = 'Campo obligatorio';
            }

            if ($scope.newsoporteRNA.nombreArchivoEmpaquetado === '[object Object]') {
                $scope.errores.fileadjunto = 'El archivo es obligatorio.';
                errorval = true;
            }

            if (!$scope.newsoporteRNA.tutela) {
                $scope.errores.tutela = 'El archivo es obligatorio.';
                errorval = true;
            }
            
            
            if (errorval === false){
                $('#dlgEspera').modal();
                $('#dlgEspera').modal({backdrop: 'static', keyboard: false}); 
                $http.post('./webresources/procesomigracionrna/uploadRNA', $scope.newsoporteRNA,
                        {}).success(function (data, status, headers, config) {

                    if (data.codmensaje === "OK") {
                        $scope.mostrarMensajeExito("Proceso finalizado exitosamente.");
                        $scope.resultado = data.object;
                        $('#dlgEspera').modal('hide');
                        verImprimirBoletin = true;
                        $scope.generarReporte(resultado.codCarga);

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
        
        function imprimirBoletinRnaProceso(parametro){
            $scope.generarReporte(parametro);
        }

        function importarRNC() {
            $scope.errores = {};
            var error = false;
            if (!$scope.cargue.archivoEmpaquetado) {
                error = true;
                $scope.errores.archivoEmpaquetado = 'El archivo es obligatorio.';
            }
            //Validaciones secretaria
            if (!$scope.datosFormulario.idSecretaria) {
                error = true;
                $scope.errores.idSecretaria = 'Campo obligatorio';
            }
            //Validaciones tipo cargue
            if (!$scope.datosFormulario.tipoCargue) {
                error = true;
                $scope.errores.tipoCargue = 'Campo obligatorio';
            }
            //Validaciones secretaria
            if (!$scope.datosFormulario.idSecretaria) {
                error = true;
                $scope.errores.idSecretaria = 'Campo obligatorio';
            }
            //Validaciones tipo cargue
            if (!$scope.datosFormulario.tipoCargue) {
                error = true;
                $scope.errores.tipoCargue = 'Campo obligatorio';
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
            if (!error) {
                $('#dlgEspera').modal();
                $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
                $scope.organismosTransito.idSecretaria = $scope.datosFormulario.idSecretaria.idSecretaria;
                $scope.newsoporte.organismoTransito = $scope.organismosTransito;
                $scope.newsoporte.tipoRegistro = $scope.datosFormulario.tipoCargue;
                $scope.newsoporte.nroTicket = $scope.datosFormulario.nroTicket;
                $scope.newsoporte.tipoCargue = 'PROCMIGRARNC';//Proceso migración RNC
                $scope.newsoporte.archivoEmpaquetado = $scope.cargue.archivoEmpaquetado;
                $scope.newsoporte.nombreArchivoEmpaquetado = $scope.cargue.nombreArchivoEmpaquetado;
                $http.post('./webresources/procesomigracionrnc/upload', $scope.newsoporte,
                        {}).success(function (data, status, headers, config) {
                    if (data.codmensaje === 'OK') {
                        $scope.generarReporte(data.codCarga);
                        $('#dlgEspera').modal('hide');
                    } else {
                        if (data.codmensaje === 'ERROR') {
                            $scope.mostrarMensajeError(data.mensaje);
                        }
                    }
                }).error(function (data, status, headers, config) {
                    $scope.mostrarMensajeError = "Error en los servicios, status " + status;
                });
            }
        }

        //////////////BOTONES//////////////////////
        $scope.regresar = function () {
            $scope.verRNA = false;
            $scope.verRNC = false;
            $scope.verBotones = true;
        };
        $scope.mostrarRNA = function () {
            $scope.verRNA = true;
            $scope.verRNC = false;
            $scope.verBotones = false;
        };
        $scope.mostrarRNC = function () {
            $scope.verRNA = false;
            $scope.verRNC = true;
            $scope.verBotones = false;
        };
        //////////////UTILITARIOS//////////////////
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
        function limpiarMensaje() {
            $('#dlgEspera').modal('hide');
            $scope.verMsjOk = false;
            $scope.verMsjError = false;
        }

        function ocultarMensaje() {
            $(document).ready(function () {
                setTimeout(function () {
                    $(".messages").fadeOut(3500);
                }, 4000);
            });
            window.scroll(0, 0);
        }

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

        $scope.generarReporte = function (codCarga) {
            $('#dlgEspera').modal();
            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});
            window.open('./webresources/listarboletin/generarboletin/' + codCarga + '.zip');
            $('#dlgEspera').modal('hide');
        };

    }]);
