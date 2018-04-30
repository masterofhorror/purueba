(function () {
    'use strict';

    angular.module('SagirApp.controllers').controller('homeCtrl', homeController);

    homeController.$inject = ['$scope', '$http'];

    function homeController($scope, $http) {
        // Definición Variables
        $scope.usuario = {};
        $scope.autoridad = {};
        $scope.opcionesMenu = [];

        // Definición de funciones
        $scope.cargarInformacionUsuario = cargarInformacionUsuario;
        $scope.cargarOpcionesMenu = cargarOpcionesMenu;
        $scope.mostrarMensaje = mostrarMensaje;

        //
        $scope.cargarInformacionUsuario();
        $scope.cargarOpcionesMenu();

        // Implementación
        function cargarInformacionUsuario() {
            $http.get('./webresources/sesion/usuario').success(function (data, status, headers, config) {
                $scope.usuario = data;
            }).error(function (data, status, headers, config) {
                $scope.mostrarMensaje("Error al cargar la información del usuario en sesion");
            });
        }

        function cargarOpcionesMenu() {
            $http.get('./webresources/sesion/cargueMenu').success(function (data, status, headers, config) {
                $scope.opcionesMenu = data;

                for (var i in $scope.opcionesMenu) {
                    if (i > 0) {
                        $scope.opcionesMenu[i].subMenu = [];
                        var items = [];
                        for (var j in $scope.opcionesMenu[i].opcionesMenu) {
                            items.push($scope.opcionesMenu[i].opcionesMenu[j]);

                            if ((parseInt(j) !== 0 && (parseInt(j) + 1) % 4 === 0) || (parseInt(j) === $scope.opcionesMenu[i].opcionesMenu.length - 1)) {
                                $scope.opcionesMenu[i].subMenu.push(items);
                                items = [];
                            }
                        }
                    }
                }
            }).error(function (data, status, headers, config) {
                $scope.mostrarMensaje("Error al cargar el menú de la aplicación");
            });
        }

        function mostrarMensaje(mensaje) {
            $('#dlgEspera').modal('hide');
            $('#msgConsulta').html(mensaje);
            $('#dlgConsulta').modal();
        }

    }

})();