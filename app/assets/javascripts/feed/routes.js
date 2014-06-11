/**
 * Sensor routes.
 */
define(['angular', './controllers', 'common'], function(angular, controllers) {
  'use strict';

  var mod = angular.module("feed.routes", ['ngRoute', 'multicast.common']);
  mod.config(["$routeProvider", function($routeProvider) {
    $routeProvider
      .when('/dashboard/:sensorId/feeds/:feedId',  {templateUrl: '/assets/templates/feed/feed-dashboard.html',  controller: controllers.FeedCtrl,
            resolve: {
                feed: ['$q', '$route', 'feedService', function($q, $route, feedService) {
                    var deferred = $q.defer();
                    console.log('resolving feed');
                    feedService.getFeed($route.current.params.sensorId, $route.current.params.feedId).then(
                        function(result) {
                            console.log(result.data);
                            deferred.resolve(result.data);
                        },
                        function(err) {
                            deferred.reject(err);
                        }
                    );
                    return deferred.promise;
                }],

                stream: ['$q', '$route', 'feedService', function($q, $route, feedService) {
                    var deferred = $q.defer();
                    feedService.getStream($route.current.params.sensorId, $route.current.params.feedId).then(
                        function(result) {
                            deferred.resolve(result.data);
                        },
                        function(err) {
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
