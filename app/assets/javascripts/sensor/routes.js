/**
 * Sensor routes.
 */
define(['angular', './controllers', './services', 'common'], function(angular, controllers) {

  'use strict';

  var mod = angular.module('sensor.routes', ['ngRoute', 'multicast.common']);
  mod.config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when("/dashboard/:sensorId",  {templateUrl: "/assets/templates/sensor/sensor-dashboard.html",  controller:controllers.SensorCtrl,
            resolve: {

                user: ['$q', 'userService', function($q, userService) {
                    var deferred = $q.defer();
                    var user = userService.getUser();
                    if (user) {
                        deferred.resolve(user);
                    } else {
                        deferred.reject();
                    }
                    return deferred.promise;
                }],

                sensor: ['$window', '$q','$route', 'sensorService', function($window, $q, $route, sensorService) {
                    console.log($route.current.params.sensorId);
                    var deferred = $q.defer();
                    sensorService.getSensor($route.current.params.sensorId).then(
                        function (result) {
                            deferred.resolve(result.data);
                        },
                        function (err) {
                            deferred.reject(err);
                        }
                    );
                    return deferred.promise;
                }]
            }
         });
  }]);
  return mod;
});
