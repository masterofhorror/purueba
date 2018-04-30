/**
 * calendar - datapickerDirective.
 *
 * @author Hmoreno
 * @version 1.2.0
 **/
(function () {
    'use strict';

    angular.module('SagirApp.directives').directive('calendar', function () {
        return {
            require: 'ngModel',
            replace: true,
            scope: true,
            link: function (scope, element, attributes, ngModel) {
                $(element).datepicker({
                    format: 'dd/mm/yyyy',
                    startDate: attributes.startdate ? attributes.startdate : '01/01/1990',
                    endDate: attributes.enddate ? attributes.enddate : '31/12/2100',
                    autoclose: true,
                    todayHighlight: true,
                    language: 'es',
                    onChangeDate: function (dateText) {
                        scope.$apply(function () {
                            ngModel.$setViewValue(dateText);
                        });
                    }
                }).on('changeDate', function (selected) {
                    if (!angular.isUndefined(attributes.update)) {
                        var range = (!angular.isUndefined(attributes.rangedays) && attributes.rangedays !== null) ? attributes.rangedays : null;
                        var startDate = new Date(selected.date.valueOf());
                        var endDate = new Date(selected.date.valueOf());

                        var reggex = /(\d{2})\/(\d{2})\/(\d{4})/;
                        var dateArray = reggex.exec(attributes.enddate);
                        var endDateParam = new Date(dateArray[3], (dateArray[2] - 1), dateArray[1], '0', '0', '0');

                        $('#' + attributes.update).datepicker('setStartDate', startDate);
                        if (range !== null) {
                            endDate.setDate(parseInt(startDate.getDate()) + parseInt(range));

                            if (endDateParam.getTime() < endDate.getTime()) {
                                endDate = endDateParam;
                            }
                        } else {
                            endDate = new Date(endDateParam);
                        }

                        $('#' + attributes.update).datepicker('setEndDate', endDate);

                        reggex = /(\d{2})\/(\d{2})\/(\d{4})/;
                        dateArray = reggex.exec($('#' + attributes.update).val());
                        if (!angular.isUndefined($('#' + +attributes.update).val()) && $('#' + +attributes.update).val() !== '' &&
                                (new Date(dateArray[3], (dateArray[2] - 1), dateArray[1], '0', '0', '0') > endDate
                                        || new Date(dateArray[3], (dateArray[2] - 1), dateArray[1], '0', '0', '0') < startDate)) {
                            $('#' + attributes.update).datepicker('setDate', startDate);
                        }
                    }
                });
            }
        };
    });

})();




