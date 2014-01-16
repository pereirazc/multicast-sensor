/**
 * Dashboard controllers.
 */
define(["angular"], function(angular) {
  "use strict";

  var SensorCtrl = function($rootScope, $scope, userService, sensorService, feedService, $routeParams, $location) {
    $scope.user = userService.getUser();

    sensorService.getSensor(userService.getToken(), $routeParams.sensorId).success(
      function(data, status, headers, response) {
          $scope.sensor = data;
          $rootScope.pageTitle = $scope.sensor.label;
      }
    );

    $scope.editSensor = function(sensorId) {
        $scope.modal = {};
        $scope.modal.title = 'Edit Sensor...';
        $scope.modal.ok = function(sensor) {
            sensorService.updateSensor(userService.getToken(), sensorId, sensor).success(
                function() {
                    angular.element("#sensorModal").modal("hide");
                    $scope.sensor = sensor;
                }
            );
        };
        $scope.modal.cancel = function() {
            angular.element("#sensorModal").modal("hide");
        };
    };

    $scope.deleteSensor = function(sensorId) {

        $scope.modal = {};

        $scope.modal.title = 'Confirmation';
        $scope.modal.content = 'Are you sure you want to remove the sensor?';

        $scope.modal.yes = function() {

            sensorService.deleteSensor(userService.getToken(), sensorId).success(
                function() {
                    angular.element("#confirmModal").modal("hide");
                    angular.element("#confirmModal").on('hidden.bs.modal', function () {
                        $location.path('dashboard/');
                        $scope.$apply();
                    });
                }
            );
        };
        $scope.modal.no = function() {
            angular.element("#confirmModal").modal("hide");
        };

    };

    $scope.createFeed = function(sensorId) {
        $scope.modal = {};
        $scope.feed = {};
        $scope.modal.title = 'New Feed...';
        $scope.modal.ok = function(feed) {
            feedService.createFeed(userService.getToken(), sensorId, feed).success(
                function(data, status, headers, response) {
                    angular.element("#feedModal").modal("hide");
                    angular.element("#feedModal").on('hidden.bs.modal', function () {
                        $location.path('dashboard/'.concat(sensorId).concat('/feeds/').concat(data.feedId));
                        $scope.$apply();
                    });
                }
            );
        };
        $scope.modal.cancel = function() {
            angular.element("#feedModal").modal("hide");
        };
    };

    $scope.feedDetails = function(sensorId, feedId) {
        $location.path('dashboard/'.concat(sensorId).concat('/feeds/').concat(feedId));
    };

    $scope.deleteFeed = function(sensorId, feedId) {

        $scope.modal = {};

        $scope.modal.title = 'Confirmation';
        $scope.modal.content = 'Are you sure you want to remove the feed?';

        $scope.modal.yes = function() {

            feedService.deleteFeed(userService.getToken(), sensorId, feedId).success(
                function() {
                    angular.element("#confirmModal").modal("hide");
                    angular.element("#confirmModal").on('hidden.bs.modal', function () {
                        console.log('dashboard/'.concat(sensorId));
                        sensorService.getSensor($routeParams.sensorId).success(
                            function(data, status, headers, response) {
                                $scope.sensor = data;
                                $rootScope.pageTitle = $scope.sensor.label;
                            }
                        );
                        $scope.$apply();
                    });
                }
            );
        };
        $scope.modal.no = function() {
            angular.element("#confirmModal").modal("hide");
        };

    };

  };
  SensorCtrl.$inject = ["$rootScope", "$scope", "userService", "sensorService","feedService", "$routeParams", "$location"];

  return {
    SensorCtrl: SensorCtrl
    //SensorModalCtrl: SensorModalCtrl
  };

});
