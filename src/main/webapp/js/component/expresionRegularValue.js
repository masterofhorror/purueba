/**
 * value - expresionRegular.
 * 
 * @author Hmoreno
 * @version 1.0.0
 */
(function () {
    'use strict';

    angular.module('SagirApp.values').value('expresionRegular', {
        formatoAlfanumerico: function () {
            return /^[0-9a-zA-Z]+$/;
        },
        formatoAlfanumericoSimbolo: function () {
            return /^[0-9a-zA-ZñÑáéíóúÁÉÍÓÚ\s\*\.\,\!\¡\¿\?\#\°_-]+$/;
        },
        formatoNumerico: function () {
            return /^[0-9]+$/;
        },
        formatoEmail: function () {
            return /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,4})+$/;
        }
    });

})();