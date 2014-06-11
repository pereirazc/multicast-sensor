/**
 * Dashboard, shown after user is logged in.
 * dashboard/main.js is the entry module which serves as an entry point so other modules only have
 * to include a single module.
 */
define(['angular', './routes', './services', './controllers'], function(angular, routes, services, controllers) {
    'use strict';

    var mod = angular.module('multicast.sensor', ["ngRoute", 'sensor.routes', 'sensor.services', 'ui-rangeSlider',
                                                    'frapontillo.bootstrap-switch']);

    mod.controller("SensorCtrl", controllers.SensorCtrl);

    return mod;

});
