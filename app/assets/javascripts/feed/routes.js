/**
 * Sensor routes.
 */
define(['angular', './controllers', 'common'], function(angular, controllers) {
  'use strict';

  var mod = angular.module("feed.routes", ['multicast.common']);
  mod.config(["$stateProvider", function($stateProvider) {
      $stateProvider
      .state('home.feed',  {
            url: "dashboard/:sensorId/feeds/:feedId",
            views: {
                'content': {
                    templateUrl: '/assets/templates/feed/feed-dashboard.html',
                    controller: controllers.FeedCtrl,
                    resolve: {
                        feed: ['$q', '$stateParams', 'feedService', function($q, $stateParams, feedService) {
                            var deferred = $q.defer();
                            console.log('resolving feed');
                            feedService.getFeed($stateParams.sensorId, $stateParams.feedId).then(
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

                        stream: ['$q', '$stateParams', 'feedService', function($q, $stateParams, feedService) {
                            var deferred = $q.defer();
                            feedService.getStream($stateParams.sensorId, $stateParams.feedId).then(
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
                }
            }
        }
      )
          .state('404',
          {
              url: "/{path:.*}",
              views: {
                  'main': {
                      templateUrl: "/assets/templates/home/notFound2.html"
                  }
              }
          }
      );

  }]);
  return mod;
});
