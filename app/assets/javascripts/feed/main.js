/**
 * Dashboard, shown after user is logged in.
 * dashboard/main.js is the entry module which serves as an entry point so other modules only have
 * to include a single module.
 */
define(["angular", "highcharts", "./routes", "./controllers", "./services"], function(angular, Highcharts, routes, controllers) {
    'use strict';

    var mod =  angular.module('multicast.feed', [  'ngRoute', 'feed.routes', 'feed.services', 'ui-rangeSlider', 'highcharts-ng',
                                                    'frapontillo.bootstrap-switch']);

    Highcharts.setOptions(
        {
            global: {
                timezoneOffset: (new Date()).getTimezoneOffset()
            }
        }
    );

    mod.controller("FeedCtrl", controllers.FeedCtrl);

    return mod;



});
