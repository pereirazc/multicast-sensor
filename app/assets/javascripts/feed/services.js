/**
 * Feed service, exposes user model to the rest of the app.
 */
define(["angular", "common"], function(angular) {
  var mod = angular.module("feed.services", ["ngRoute", "yourprefix.common"]);

  mod.factory("feedService", ["$http", "$q", "playRoutes", function($http, $q, playRoutes) {
    var FeedService = function() {

        var self = this;

        setAuthHeader = function (authToken) {
            $http.defaults.headers.common['X-AUTH-TOKEN'] = authToken;
        }

        self.getAllFeeds = function(authToken, sensorId) {
            setAuthHeader(authToken);
            return playRoutes.controllers.FeedCtrl.getFeed(sensorId).get();
        };

        self.createFeed = function(authToken, sensorId, feed) {
            setAuthHeader(authToken);
            return playRoutes.controllers.FeedCtrl.createFeed(sensorId).post(feed);
        };

        self.getFeed = function(authToken, sensorId, feedId) {
            setAuthHeader(authToken);
            return playRoutes.controllers.FeedCtrl.getFeed(sensorId, feedId).get();
        };

        self.updateFeed = function(authToken, sensorId, feedId, feed) {
            setAuthHeader(authToken);
            return playRoutes.controllers.FeedCtrl.updateFeed(sensorId, feedId).post(feed);
        };

        self.getStream = function(authToken, sensorId, feedId) {
            setAuthHeader(authToken);
            return playRoutes.controllers.APICtrl.getStream(sensorId, feedId).get();
        };

        self.postData = function(sensorId, feedId, data) {
            return playRoutes.controllers.APICtrl.postData(sensorId, feedId).post(data);
        };

        self.deleteFeed = function(authToken, sensorId, feedId) {
            setAuthHeader(authToken);
            return playRoutes.controllers.FeedCtrl.deleteFeed(sensorId, feedId)['delete']();
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
