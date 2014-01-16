/**
 * Sensor routes.
 */
define(["angular", "./controllers", "common"], function(angular, controllers) {
  var mod = angular.module("feed.routes", ["ngRoute", "feed.services", "yourprefix.common"]);
  mod.config(["$routeProvider", function($routeProvider) {
    $routeProvider
      .when("/dashboard/:sensorId/feeds/:feedId",  {templateUrl: "/assets/templates/feed/feed-dashboard.html",  controller:controllers.FeedCtrl});
  }]);
  return mod;
});
