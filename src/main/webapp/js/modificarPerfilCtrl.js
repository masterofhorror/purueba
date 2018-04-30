(function () {
    'use strict';

    angular.module('SagirApp.controllers').controller('modificarPerfilCtrl', modificarPerfilController);

    modificarPerfilController.$inject = ['$scope', '$http', 'expresionRegular'];

    function modificarPerfilController($scope, $http, expresionRegular) {
        //Definicion Variables
        $scope.errores = {};
        $scope.email = '';
        $scope.claveActual = '';
        $scope.claveNueva = '';
        $scope.claveNuevaConfir = '';
        $scope.usuario = {};
        $scope.correos = [];
        $scope.mostrarPanelClave = false;

        //Definicion de funciones
        $scope.cargarUsuario = cargarUsuario;
        $scope.agregarCorreo = agregarCorreo;
        $scope.eliminarCorreo = eliminarCorreo;
        $scope.actualizarUsuario = actualizarUsuario;
        $scope.cancelar = cancelar;
        $scope.guardarClave = guardarClave;
        $scope.actualizarContrasena = actualizarContrasena;
        $scope.cancelarClave = cancelarClave;
        $scope.cancelarCorreo = cancelarCorreo;
        $scope.cambiarContrasena = cambiarContrasena;
        $scope.cargarTipoDocumento = cargarTipoDocumento;

        $scope.mostrarMensajeSuccess = mostrarMensajeSuccess;
        $scope.mostrarMensajeInfo = mostrarMensajeInfo;
        $scope.mostrarMensajeError = mostrarMensajeError;
        $scope.ocultarMensaje = ocultarMensaje;
        $scope.limpiarMensaje = limpiarMensaje;

        //
        $scope.cargarUsuario();

        //Implementacion       
        function cargarUsuario() {
            $('#dlgEspera').modal();
            $http.get('./webresources/usuario', {})
                    .success(function (data, status, headers, config) {
                        $scope.usuario = data;

                        if ($scope.usuario.correos) {
                            $scope.correos = $scope.usuario.correos;
                        }

                        $('#dlgEspera').modal('hide');
                    }).error(function (data, status, headers, config) {
                $scope.mostrarMensajeError('Error consultando el usuario, por favor intente mas tarde.');
            });
        }

        function agregarCorreo() {
            $scope.errores = {};

            if (!$scope.email) {
                $scope.errores.email = "Debe ingresar el correo electrónico";
            } else {
                if ($scope.email.length > 50) {
                    $scope.errores.email = "El valor ingresado excede el tamaño máximo para el campo";
                } else if (!expresionRegular.formatoEmail().test($scope.email)) {
                    $scope.errores.email = "El formato del correo electrónico no es válido. Ejemplo: user@example.com.co";
                } else {
                    for (var i in $scope.correos) {
                        if ($scope.correos[i] === $scope.email) {
                            $scope.errores.email = "El correo ya se encuentra asociado";
                        }
                    }
                }
            }

            if (Object.keys($scope.errores).length === 0) {
                $scope.correos.push($scope.email);
                $scope.email = '';
            }
        }

        function cancelarCorreo() {
            $scope.errores = {};
            $scope.email = '';
        }

        function eliminarCorreo($index) {
            $scope.correos.splice($index, 1);
        }

        function actualizarUsuario() {
            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});

            $scope.errores = {};

            if ($scope.usuario.nombres) {
                if ($scope.usuario.nombres.length > 50) {
                    $scope.errores.nombres = "El valor ingresado excede el tamaño máximo para el campo";
                }
            } else {
                $scope.errores.nombres = "El campo es obligatorio";
            }

            if ($scope.usuario.apellidos) {
                if ($scope.usuario.apellidos.length > 50) {
                    $scope.errores.apellidos = "El valor ingresado excede el tamaño máximo para el campo";
                }
            } else {
                $scope.errores.apellidos = "El campo es obligatorio";
            }

            if ($scope.usuario.direccion) {
                if ($scope.usuario.direccion.length > 50) {
                    $scope.errores.direccion = "El valor ingresado excede el tamaño máximo para el campo";
                }
            } else {
                $scope.errores.apellidos = "El campo es obligatorio";
            }

            if ($scope.usuario.telefono) {
                if ($scope.usuario.telefono.length > 20) {
                    $scope.errores.telefono = "El valor ingresado excede el tamaño máximo para el campo";
                }
            } else {
                $scope.errores.telefono = "El campo es obligatorio";
            }

            if (!$scope.usuario.genero) {
                $scope.errores.genero = "Debe seleccionar el genero";
            }

            if ($scope.correos.length === 0) {
                $scope.errores.correos = "Debe ingresar un correo como minimo";
            }

            if (Object.keys($scope.errores).length === 0) {

                $scope.usuario.correos = $scope.correos;

                $http.post("./webresources/usuario/actualizar", $scope.usuario)
                        .success(function (data, status, headers, config) {
                            if (data.codmensaje === 'OK') {
                                $scope.mostrarMensajeSuccess(data.mensaje);
                                $scope.cancelar();
                            } else {
                                $scope.mostrarMensajeInfo(data.mensaje);
                            }
                        })
                        .error(function (data, status, headers, config) {
                            $scope.mostrarMensajeError('Error registrando la información del usuario, por favor intente mas tarde.');
                        });
            } else {
                $scope.mostrarMensajeInfo('Debe ingresar los campos requeridos.');
            }
        }


        function guardarClave() {
            $('#dlgEspera').modal({backdrop: 'static', keyboard: false});

            $scope.errores = {};

            if (!$scope.claveActual) {
                $scope.errores.claveActual = 'Este campo es obligatorio';
            }

            if (!$scope.claveNueva) {
                $scope.errores.claveNueva = 'Este campo es obligatorio';
            }

            if (!$scope.claveNuevaConfir) {
                $scope.errores.claveNuevaConfir = 'Este campo es obligatorio';
            }

            if ($scope.claveNueva && $scope.claveNuevaConfir) {
                if ($scope.claveNueva !== $scope.claveNuevaConfir) {
                    $scope.errores.claveNuevaConfir = 'Las contraseñas no coinciden';
                } else {
                    $scope.usuario.contrasena = $scope.claveNuevaConfir;
                }
            }

            if (Object.keys($scope.errores).length === 0) {

                $http.post("./webresources/usuario/validarClave", {'login': $scope.usuario.documento, 'password': $scope.claveActual})
                        .success(function (data, status, headers, config) {
                            if (data) {
                                $scope.actualizarContrasena();
                            } else {
                                $scope.errores.claveActual = 'La contraseña es incorrecta';
                                
                                $('#dlgEspera').modal('hide');
                            }
                        })
                        .error(function (data, status, headers, config) {
                            $scope.mostrarMensajeError('Error al registrar la contraseña del usuario, por favor intente mas tarde.');
                        });
            } else {
                $scope.mostrarMensajeInfo('Debe ingresar los campos requeridos');
            }
        }

        function actualizarContrasena() {

            $http.post("./webresources/usuario/actualizarContrasena", $scope.usuario)
                    .success(function (data, status, headers, config) {
                        if (data.codmensaje === 'OK') {
                            $scope.mostrarMensajeSuccess(data.mensaje);
                            $scope.cancelarClave();
                        } else {
                            $scope.mostrarMensajeInfo(data.mensaje);
                        }
                    })
                    .error(function (data, status, headers, config) {
                        $scope.mostrarMensajeError('Error al registrar la contraseña del usuario, por favor intente mas tarde.');
                    });
        }

        function cancelarClave() {
            $scope.claveNueva = '';
            $scope.claveNuevaConfir = '';
            $scope.claveActual = '';
            $scope.usuario.contrasena = '';
            $scope.mostrarPanelClave = false;
            $scope.errores = {};
        }

        function cancelar() {
            $('#dlgEspera').modal();

            $scope.correos = [];
            $scope.usuario = {};
            $scope.cargarUsuario();
             $scope.errores = {};

            $('#dlgEspera').modal('hide');
        }

        function cambiarContrasena() {
            $scope.mostrarPanelClave = true;
            $scope.claveNuevaConfir = '';
            $scope.usuario.contrasena = '';
        }

        function cargarTipoDocumento(item) {
            if (item === 'D') {
                return 'Carnet Diplomático';
            } else if (item === 'C') {
                return 'Cédula Ciudadania';
            } else if (item === 'N') {
                return 'Nit';
            } else if (item === 'E') {
                return 'Cédula de Extranjería';
            } else if (item === 'P') {
                return 'Pasaporte';
            } else if (item === 'U') {
                return 'Registro Civil';
            } else if (item === 'T') {
                return 'Tarjeta de Identidad';
            }
            return item;
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
