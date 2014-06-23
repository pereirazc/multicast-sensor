/**
 * Dashboard routes.
 */
define(["angular", "./controllers", "common"], function(angular, controllers) {
  'use strict';

  var mod = angular.module("dashboard.routes", ['multicast.common']);

  mod.config(['$stateProvider', function($stateProvider) {
      $stateProvider
      .state("home.dashboard",  {
            url: "dashboard",
            views: {
                'content': {
                    templateUrl: "/assets/templates/dashboard/dashboard.html",
                    controller:controllers.DashboardCtrl,
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

                        sensors: ['$q','sensorService', function($q, sensorService) {
                            var deferred = $q.defer();
                            sensorService.getAllSensors().then(
                                function (result) {
                                    console.log(result.data);
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

        });
  }]);
  return mod;
});
