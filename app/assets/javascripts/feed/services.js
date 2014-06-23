/**
 * Feed service, exposes user model to the rest of the app.
 */
define(["angular", "common"], function(angular) {

  'use strict';

  var mod = angular.module("feed.services", ["multicast.common"]);

  mod.factory("feedService", ["$http", "$q", "playRoutes", function($http, $q, playRoutes) {
    var FeedService = function() {

        var self = this;

        self.getAllFeeds = function(sensorId) {
            return playRoutes.controllers.FeedCtrl.getFeed(sensorId).get();
        };

        self.createFeed = function(sensorId, feed) {
            return playRoutes.controllers.FeedCtrl.createFeed(sensorId).post(feed);
        };

        self.getFeed = function(sensorId, feedId) {
            //setAuthHeader(authToken);
            return playRoutes.controllers.FeedCtrl.getFeed(sensorId, feedId).get();
        };

        self.updateFeed = function(sensorId, feedId, feed) {
            return playRoutes.controllers.FeedCtrl.updateFeed(sensorId, feedId).post(feed);
        };

        self.getStream = function(sensorId, feedId) {
            return playRoutes.controllers.APICtrl.getStream(sensorId, feedId).get();
        };

        self.postData = function(sensorId, feedId, data) {
            return playRoutes.controllers.APICtrl.postData(sensorId, feedId).post(data);
        };

        self.deleteFeed = function(sensorId, feedId) {
            return playRoutes.controllers.FeedCtrl.deleteFeed(sensorId, feedId)['delete']();
        };

    };

    return new FeedService();
  }]);

  /**
   * If the current route does not resolve, go back to the start page.
   */

  return mod;
});
