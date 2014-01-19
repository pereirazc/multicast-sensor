/**
 * Feed controllers.
 */
define(["angular"], function(angular) {
  "use strict";

  var minute = 60000;

  var FeedCtrl = function($rootScope, $scope, userService, feedService, $routeParams, $timeout, $location) {

    $scope.user = userService.getUser();

    $scope.timeWindowOpts = [
        {id: 1, secs: minute, label: "1 Minuto"},
        {id: 2, secs: 2*minute, label: "2 Minutos"},
        {id: 3, secs: 5*minute, label: "5 Minutos"},
        {id: 4, secs: 10*minute, label: "10 Minutos"},
        {id: 5, secs: 30*minute, label: "30 Minutos"},
        {id: 6, secs: 60*minute, label: "1 Hora"}
    ];

    $scope.currentTimeWindow = $scope.timeWindowOpts[0];

    $scope.updateTimeWindow = function () {
        $scope.chartConfig.xAxis.currentMin = max  - $scope.currentTimeWindow.secs;
        $scope.chartConfig.xAxis.currentMax = max;
    }

    feedService.getFeed(userService.getToken(), $routeParams.sensorId, $routeParams.feedId).success(
        function(data, status, headers, response) {
            $scope.feed = data;
            $scope.chartConfig.series[0].name = $scope.feed.feedId;
            $rootScope.pageTitle = $scope.feed.feedId;
            //$scope.chartConfig.title.text = $scope.feed.label;
        }
    );

    $scope.editFeed = function(feedId) {

        $scope.modal = {};
        $scope.modal.title = 'Edit Feed...';
        $scope.modal.ok = function(feed) {
            feedService.updateFeed(userService.getToken(), $routeParams.sensorId, feedId, feed).success(
                function() {
                    angular.element("#feedModal").modal("hide");
                    angular.element("#feedModal").on('hidden.bs.modal', function () {
                        $location.path('dashboard/'.concat($routeParams.sensorId).concat('/feeds/').concat(feed.feedId));
                        $scope.$apply();
                    });
                }
            );
        };
        $scope.modal.cancel = function() {
            angular.element("#feedModal").modal("hide");
        };
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
                        $location.path('dashboard/'.concat(sensorId));
                        $scope.$apply();
                    });
                }
            );
        };
        $scope.modal.no = function() {
            angular.element("#confirmModal").modal("hide");
        };
    };

    var max;
    var stream = [];

    $scope.addPoints = function () {
        var point =  {
          timestamp: (new Date()).getTime(),
          value: 30 + (Math.random()/2)
        };
        feedService.postData($scope.feed.sensor.sensorId, $scope.feed.feedId, point);
    };

      $scope.chartConfig = {
          options: {
              chart: {
                  type: 'line',
                  zoomType: 'x'
              }
          },
          credits: {
              enabled: false
          },
          series: [{
              name: "",

              color: '#7ECE25',

              data: (function() {

					var stream = [];

                    feedService.getStream(userService.getToken(), $routeParams.sensorId, $routeParams.feedId).success(
						function(data, status, headers, response) {

							stream = data;

							var series = $scope.chartConfig.series[0];

							if (stream.length > 0) {
								max = stream[stream.length - 1].x;
							} else max = (new Date()).getTime();

							series.data = stream;

							$scope.chartConfig.xAxis.currentMin = max  - minute;
							$scope.chartConfig.xAxis.currentMax = max;

							$scope.timer = $timeout($scope.updateGraph, 5000);
						}
					);

					return stream;
              })()
          }],
          title: {
              text: ''
          },
          xAxis: {
              currentMin: max - 60000,
              currentMax: max,
              //minRange: 1,
              type: 'datetime',
              tickPixelInterval: 150

          },

          loading: false
      }

      $scope.updateGraph = function () {

        if ($routeParams.feedId!=undefined) {
            feedService.getStream(userService.getToken(), $routeParams.sensorId, $routeParams.feedId).success(
				function(data, status, headers, response) {

					stream = data;

					var series = $scope.chartConfig.series[0];

					if (stream.length > 0) {
						max = stream[stream.length - 1].x;
					} else max = (new Date()).getTime();

					series.data = stream;

					$scope.chartConfig.xAxis.currentMin = max  - $scope.currentTimeWindow.secs;
					$scope.chartConfig.xAxis.currentMax = max;

					$scope.timer = $timeout($scope.updateGraph, 5000);
				}
			);
        }

      }
      $scope.pauseStream = function () {
          $timeout.cancel($scope.timer);
      }
  };
  FeedCtrl.$inject = ["$rootScope", "$scope", "userService", "feedService", "$routeParams", "$timeout", "$location"];

  return {
    FeedCtrl: FeedCtrl
    //FeedModalCtrl: FeedModalCtrl

  };

});
