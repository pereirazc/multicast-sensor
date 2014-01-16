/**
 * Dashboard controllers.
 */
define(["angular"], function(angular) {
  "use strict";

  var DashboardCtrl = function($rootScope, $scope, userService, sensorService, $location) {

    $rootScope.pageTitle = "Multicast Sensor";

    $scope.user = userService.getUser();

    $scope.sensors = [];

	//$scope.sensors = sensorService.getAllSensors()
    sensorService.getAllSensors(userService.getToken()).success(
		function (data, status, headers, response) {
			$scope.sensors = data;
		}
	);
	
    $scope.createSensor = function () {

        $scope.modal = {};
        $scope.sensor = {};
        $scope.modal.title = 'New Sensor...';

        $scope.modal.ok = function(sensor) {
            sensorService.createSensor(userService.getToken(), sensor).success(
				function (data, status, headers, response) {
                    angular.element("#sensorModal").modal("hide");
                    angular.element("#sensorModal").on('hidden.bs.modal', function () {
                        $location.path('dashboard/'.concat(data.sensorId));
                        $scope.$apply();
                    });
				}
			);
        };
        $scope.modal.cancel = function() {
            angular.element("#sensorModal").modal("hide");
        };

    };

    $scope.sensorDetails = function(sensorId) {
        $location.path('dashboard/'.concat(sensorId));
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
                        sensorService.getAllSensors($rootScope.authToken).success(
                            function(data, status, headers, response) {
                                $scope.sensors = data;
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
  DashboardCtrl.$inject = ["$rootScope", "$scope", "userService", "sensorService", "$location"];

  /*var AdminDashboardCtrl = function($scope, userService, sensorService, $location) {

  };
  AdminDashboardCtrl.$inject = ["$scope", "userService", "sensorService"];*/

  return {
    DashboardCtrl: DashboardCtrl/*,
    AdminDashboardCtrl: AdminDashboardCtrl*/
  };

});