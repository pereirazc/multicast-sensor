/**
 * Feed service, exposes user model to the rest of the app.
 */
define(["angular", "common"], function(angular) {
  var mod = angular.module("feed.services", ["ngRoute", "yourprefix.common"]);

  mod.factory("feedService", ["$http", "$q", "playRoutes", function($http, $q, playRoutes) {
    var FeedService = function() {

        var self = this;

        self.getAllFeeds = function(sensorId) {
            return playRoutes.controllers.FeedCtrl.getFeed(sensorId).get().then(function(response) {
                return response.data;
            });
        };

        self.createFeed = function(sensorId, feed) {
         return playRoutes.controllers.FeedCtrl.createFeed(sensorId).post(feed).then(function(response) {
            return response.data;
         });
        };

        self.getFeed = function(sensorId, feedId) {
            return playRoutes.controllers.FeedCtrl.getFeed(sensorId, feedId).get().then(function(response) {
                return response.data;
            });
        };

        self.updateFeed = function(sensorId, feedId, feed) {
            return playRoutes.controllers.FeedCtrl.updateFeed(sensorId, feedId).post(feed).then(function(response) {
                return response.data;
            });
        };

        self.getStream = function(sensorId, feedId) {
            return playRoutes.controllers.APICtrl.getStream(sensorId, feedId).get().then(function(response) {
                return response.data;
            });
        };

        self.postData = function(sensorId, feedId, data) {
            return playRoutes.controllers.APICtrl.postData(sensorId, feedId).post(data).then(function(response) {
                return response.data;
            });
        };

        self.deleteFeed = function(sensorId, feedId) {
            return playRoutes.controllers.FeedCtrl.deleteFeed(sensorId, feedId)['delete']().then(function(response) {
                return response.data;
            });
        };

    };

    return new FeedService();
  }]);

  /**
   * If the current route does not resolve, go back to the start page.
   */
  var handleRouteError = function($rootScope, $location) {
    $rootScope.$on("$routeChangeError", function(e, next, current) {
      //$location.path("/");
    });
  };
  handleRouteError.$inject = ["$rootScope", "$location"];
  mod.run(handleRouteError);
  return mod;
});
