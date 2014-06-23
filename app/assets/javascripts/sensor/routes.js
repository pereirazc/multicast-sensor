/**
 * Sensor routes.
 */
define(['angular', './controllers', './services', 'common'], function(angular, controllers) {

  'use strict';

  var mod = angular.module('sensor.routes', ['multicast.common']);
  mod.config(['$stateProvider', function($stateProvider) {
      $stateProvider
      .state("home.sensor",  {
            url: "dashboard/:sensorId",
                views: {
                    'content': {
                        templateUrl: "/assets/templates/sensor/sensor-dashboard.html",
                        controller:controllers.SensorCtrl,
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
                            sensor: ['$window', '$q','$stateParams', 'sensorService', function($window, $q, $stateParams, sensorService) {
                                var deferred = $q.defer();
                                sensorService.getSensor($stateParams.sensorId).then(
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
                    }
                }
            }
      );
  }]);


  return mod;
});
