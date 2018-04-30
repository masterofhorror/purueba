(function () {
    'use strict';

    angular.module('SagirApp.controllers').controller('asignacionRangosCtrl', asignacionRangosController);

    asignacionRangosController.$inject = ['$scope', '$http', 'expresionRegular'];

    function asignacionRangosController($scope, $http, expresionRegular) {
        // Definición Variables
        $scope.filtroConsulta = {};
        $scope.errores = {};
        $scope.totalItems = 0;
        $scope.items = new Map();
        $scope.itemsPagina = [];

        $scope.rango = {};
        $scope.organismosTransito = [];

        $scope.pantallaPrincipal = true;
        $scope.verResultado = false;

        $scope.pageSizeGeneral = 10;
        $scope.currentPageGeneral = 1;

        $scope.fecha = new Date();

        // Definición de funciones
        $scope.cargarParametrizacion = cargarParametrizacion;
        $scope.buscar = buscar;
        $scope.buscarDetalle = buscarDetalle;
        $scope.limpiar = limpiar;
        $scope.abrirNuevo = abrirNuevo;
        $scope.guardar = guardar;
        $scope.confirmarGuardar = confirmarGuardar;
        $scope.cancelar = cancelar;

        $scope.pageChangeHandlerDetalle = pageChangeHandlerDetalle;

        $scope.mostrarMensajeSuccess = mostrarMensajeSuccess;
        $scope.mostrarMensajeInfo = mostrarMensajeInfo;
        $scope.mostrarMensajeError = mostrarMensajeError;
        $scope.ocultarMensaje = ocultarMensaje;
        $scope.limpiarMensaje = limpiarMensaje;

        // 
        $scope.cargarParametrizacion();

        // Implementación
        function cargarParametrizacion() {
            $scope.fechaActual = ('0' + $scope.fecha.getDate()).slice(-2) + '/' + ('0' + ($scope.fecha.getMonth() + 1)).slice(-2) + '/' + $scope.fecha.getFullYear();

            $http.get('./webresources/common/listarot')
                    .success(function (data, status, headers, config) {
                        if (data.codmensaje === 'OK') {
                            $scope.organismosTransito = data.object;
                        } else {
                            $scope.mostrarMensajeInfo(data.mensaje);
                        }
                    }).error(function (data, status, headers, config) {
                $scope.mostrarMensajeError('Error consultando el listado de orgainismos de tránsito. Por favor intente mas tarde.');
            });
        }

        function buscar() {
            $scope.errores = {};

            if (!$scope.filtroConsulta.fecha && $scope.filtroConsulta.organismoTransito) {
                $scope.errores.fecha = 'Campo obligatorio';
            }

            if ($scope.filtroConsulta.fecha && !$scope.filtroConsulta.organismoTransito) {
                $scope.errores.organismoTransito = 'Campo obligatorio';
            }

            if ($scope.filtroConsulta.placa) {
                if (!expresionRegular.formatoAlfanumerico().test($scope.filtroConsulta.placa)) {
                    $scope.errores.placa = 'El campo contiene caracteres invalidos';
                } else if ($scope.filtroConsulta.placa.length > 10) {
                    $scope.errores.placa = 'El campo excede el tamaño máximo';
                }
            }

            if (Object.keys($scope.filtroConsulta).length === 0) {
                $scope.mostrarMensajeInfo('Debe seleccionar por lo menos un criterio de búsqueda');
            } else {
                if (Object.keys($scope.errores).length === 0) {
                    $('#dlgEspera').modal();
                    $scope.items = new Map();

                    $http.post('./webresources/asignacionRangos/totalItems', $scope.filtroConsulta)
                            .success(function (data, status, headers, config) {
                                $scope.totalItems = data;

                                if ($scope.totalItems === 0) {
                                    $scope.mostrarMensajeInfo('No existen resultados para los filtros ingresados.');
                                } else {
                                    $scope.buscarDetalle(1);

                                    $scope.verResultado = true;
                                }
                            }).error(function (data, status, headers, config) {
                        $scope.mostrarMensajeError($filter('translate')('DETALLE_INVENTARIO.ERROR.CONSULTA_TOTAL_ITEMS'));
                    });
                }
            }
        }

        function buscarDetalle(page) {
            if (!$scope.items.get(page)) {
                var offset = (page * $scope.pageSizeGeneral) - $scope.pageSizeGeneral;

                $scope.filtroConsulta.limit = $scope.pageSizeGeneral;
                $scope.filtroConsulta.offset = offset;

                $http.post('./webresources/asignacionRangos/items', $scope.filtroConsulta)
                        .success(function (data, status, headers, config) {
                            $scope.items.put(page, data);
                            $scope.itemsPagina = data;

                            $('#dlgEspera').modal('hide');
                        }).error(function (data, status, headers, config) {
                    $scope.mostrarMensajeError('Error consultado la asignación de rangos');
                });
            } else {
                $scope.itemsPagina = $scope.items.get(page);
            }
        }

        function pageChangeHandlerDetalle(pageNumber) {
            $scope.buscarDetalle(pageNumber);
        }

        function limpiar() {
            $scope.filtroConsulta = {};
            $scope.errores = {};
            
            $scope.items = new Map();
            $scope.itemsPagina = [];
             $scope.totalItems = 0;

            $scope.pantallaPrincipal = true;
            $scope.verResultado = false;
        }

        function abrirNuevo() {
            $scope.rango = {};
            $scope.pantallaPrincipal = false;

            $scope.errores = {};
        }

        function guardar() {
            $scope.errores = {};

            if (!$scope.rango.organismoTransito) {
                $scope.errores.organismoTransito = 'Campo obligatorio';
            }

            if (!$scope.rango.especieVenal) {
                $scope.errores.especieVenal = 'Campo obligatorio';
            }

            if (!$scope.rango.desde) {
                $scope.errores.desde = 'Campo obligatorio';
            }

            if (!$scope.rango.hasta) {
                $scope.errores.hasta = 'Campo obligatorio';
            }

            if (!$scope.rango.fechaAsignacion) {
                $scope.errores.fechaAsignacion = 'Campo obligatorio';
            }

            if (!$scope.rango.resolucion) {
                $scope.errores.resolucion = 'Campo obligatorio';
            }

            if (!$scope.rango.observaciones) {
                $scope.errores.observaciones = 'Campo obligatorio';
            }

            if (Object.keys($scope.errores).length === 0) {
                $('#dlgEspera').modal('hide');
                $('#msgConfirm').html('Esta acción registrara el rango ingresado, ¿está seguro que desea continuar?');
                $('#dlgConfirm').modal();
            }
        }

        function confirmarGuardar() {
            if (Object.keys($scope.errores).length === 0) {
                $('#dlgEspera').modal();

                $http.post('./webresources/asignacionRangos/guardar', $scope.rango)
                        .success(function (data, status, headers, config) {
                            if (data.codmensaje === 'OK') {
                                $scope.limpiar();

                                $scope.mostrarMensajeSuccess(data.mensaje);
                            } else {
                                $scope.mostrarMensajeInfo(data.mensaje);
                            }
                        }).error(function (data, status, headers, config) {
                    $scope.mostrarMensajeError('Error registrando el rango');
                });
            }
        }

        function cancelar() {
            $scope.limpiar();
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

    }
})();

