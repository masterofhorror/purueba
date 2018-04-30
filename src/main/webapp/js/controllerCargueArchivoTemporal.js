module.controller('cargueArchivoTemporalCtrl', ['$scope', '$http', '$route', function ($scope, $http, $route) {
        console.log("inicializando app");
        $scope.newsoporte = {};
        $scope.datosFormulario = {};
        $scope.pageSize = 50;

        $scope.tipo;
        $scope.errores = {};
        $scope.verMsjOk = false;
        $scope.verMsjError = false;
        $scope.verResultado = false;
        $scope.ocultarMensaje = ocultarMensaje;
        $scope.limpiarMensaje = limpiarMensaje;

        $scope.buscar = buscar;
        $scope.borrar = borrar;
        $scope.abrirBorrar = abrirBorrar;
        
        $(".fileinput-remove-button").mouseup(function () {
            $('#files').fileinput('reset');
            $('#archivo').val('');
            $('#nombre').val('');
            $('.percent').text('0%');
        });

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

            $scope.newsoporte.tempArchivo = $('#archivo').val();
            $scope.newsoporte.tempNombre = $('#nombre').val();
            console.log(JSON.stringify($scope.newsoporte));



            if ($scope.newsoporte.tempNombre === '[object Object]' || $scope.newsoporte.tempNombre === "") {
                $scope.errores.fileadjunto = 'El archivo es obligatorio.';
                errorval = true;
            }

            if (errorval === false) {
                $('#dlgEspera').modal();
                $http.post('./webresources/cargueArchivoTemp/upload', $scope.newsoporte,
                        {}).success(function (data, status, headers, config) {

                    if (data.codmensaje === "OK") {
                        $scope.mostrarMensajeExito(data.mensaje);
                        $('#files').fileinput('reset');
                        $('#archivo').val('');
                        $('#nombre').val('');
                        $('.percent').text('0%');

                    } else {
                        if (data.codmensaje === "ERROR") {
                            $scope.mostrarMensajeError(data.mensaje);
                        }
                    }

                }).error(function (data, status, headers, config) {
                    alert("Error en los servicios, status " + status);
                });

            }


        }
        function abrirBorrar() {
                $('#dlgEspera').modal('hide');
                $('#msgConfirm').html('Esta acción elimina la información de la tabla CARGUE_TEMPORAL, ¿está seguro que desea continuar?');
                $('#dlgConfirm').modal();
        }

        function borrar() {
            $scope.errores = {};
            var servicio;

            servicio = './webresources/cargueArchivoTemp/delete';

            $http.get(servicio).
                    success(function (data, status, headers, config) {
                        if (data.codmensaje === "OK") {
                            $scope.mostrarMensajeExito("Informacion borrada exitosamente.");

                        } else {
                            if (data.codmensaje === "ERROR") {
                                $scope.mostrarMensajeExito("Error, no se pudo borrar la informacion.");
                            }
                        }

                    }).error(function (data, status, headers, config) {
                //alert(status);
                if (data === null) {
                    $scope.mostrarMensajeError('No se pudo eliminar la informacion.');
                }
                $scope.mostrarMensajeError('No se pudo eliminar la informacion.');
            });
        }

    }]);