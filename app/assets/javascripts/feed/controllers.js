/**
 * Feed controllers.
 *
 */
define(['angular'], function(angular) {

  "use strict";

  var minute = 60000;

  var FeedCtrl = function($rootScope, $scope, userService, feedService, $stateParams, $timeout, $state, feed, stream, toaster) {

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
              data: $scope.stream
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
      };

      $scope.updateGraph = function () {

          if ($stateParams.feedId!==undefined) {
              feedService.getStream($stateParams.sensorId, $stateParams.feedId).success(
                  function(data/*, status, headers, response*/) {

                      $scope.stream = data;

                      var series = $scope.chartConfig.series[0];

                      if ($scope.stream.length > 0) {
                          max = $scope.stream[$scope.stream.length - 1].x;
                          $scope.updateGraph = function () {

                              if ($stateParams.feedId!==undefined) {
                                  feedService.getStream($stateParams.sensorId, $stateParams.feedId).success(
                                      function(data/*, status, headers, response*/) {

                                          $scope.stream = data;

                                          var series = $scope.chartConfig.series[0];

                                          if ($scope.stream.length > 0) {
                                              max = $scope.stream[$scope.stream.length - 1].x;
                                          } else {
                                              max = (new Date()).getTime();
                                          }

                                          series.data = $scope.stream;

                                          $scope.chartConfig.xAxis.currentMin = max  - $scope.currentTimeWindow.secs;
                                          $scope.chartConfig.xAxis.currentMax = max;

                                          $scope.timer = $timeout($scope.updateGraph, 5000);
                                      }
                                  );
                              }

                          };        } else {
                          max = (new Date()).getTime();
                      }

                      series.data = $scope.stream;

                      $scope.chartConfig.xAxis.currentMin = max  - $scope.currentTimeWindow.secs;
                      $scope.chartConfig.xAxis.currentMax = max;

                      $scope.timer = $timeout($scope.updateGraph, 5000);
                  }
              );
          }

      };

    function init(feed, stream) {
      console.log(feed);
      $scope.feed = feed;
      $rootScope.pageTitle = $scope.feed.feedId;
      if ($scope.feed.alertConfig === null) {
          $scope.feed.alertConfig = {};
          $scope.feed.alertConfig.status = false;
          $scope.feed.alertConfig.min =0;
          $scope.feed.alertConfig.max =0;
      } else {
          $scope.feed.alertConfig.status = true;
      }

      $scope.chartConfig.series[0].name = $scope.feed.feedId;
      $scope.stream = stream;
      $scope.updateGraph();

      /*$scope.chartConfig.series[0].data = $scope.stream;
      $scope.timer = $timeout($scope.updateGraph, 5000);*/
      //$scope.chartConfig.title.text = $scope.feed.label;
    }

    init(feed, stream);

    console.log("timezoneOffset");

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
    };

    $scope.editFeed = function(feedId) {

        $scope.modal = {};

        $scope.modal.title = 'Edit Feed...';
        $scope.modal.ok = function(feed) {
            feedService.updateFeed($stateParams.sensorId, feedId, feed).success(
                function() {
                    angular.element("#feedModal").modal("hide");
                    angular.element("#feedModal").on('hidden.bs.modal', function () {

                        $state.go('home.feed', {sensorId: $stateParams.sensorId, feedId: feed.feedId});

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

            feedService.deleteFeed(sensorId, feedId).success(
                function() {
                    angular.element("#confirmModal").modal("hide");
                    angular.element("#confirmModal").on('hidden.bs.modal', function () {
                        $scope.$apply();
                        toaster.clear();
                        $state.go('home.sensor', {sensorId: sensorId});
                        toaster.pop('warning', 'Deleted Feed', 'feed ' + feedId + ' removed succesfully.', 2000);
                    });
                }
            );
        };
        $scope.modal.no = function() {
            angular.element("#confirmModal").modal("hide");
        };
    };

    var max;

    $scope.addPoints = function () {
        var point =  {
          timestamp: (new Date()).getTime(),
          value: $scope.pointValue
        };
        feedService.postData($scope.feed.sensor.sensorId, $scope.feed.feedId, point);
    };

    $scope.pauseStream = function () {
      $timeout.cancel($scope.timer);
    };

    $scope.$on('$destroy', $scope.pauseStream);

  };
  FeedCtrl.$inject = ["$rootScope", "$scope", "userService", "feedService", "$stateParams", "$timeout", "$state", "feed", "stream", "toaster"];

  return {
    FeedCtrl: FeedCtrl
    //FeedModalCtrl: FeedModalCtrl

  };

});
