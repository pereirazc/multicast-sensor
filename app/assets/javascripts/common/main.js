/**
 * Common functionality.
 */
define(["angular", "./services/helper", "./services/playRoutes", "./filters", "./directives/example"],
  function(angular) {
    return angular.module("yourprefix.common", ["ngRoute","common.helper", "common.playRoutes", "common.filters",
      "common.directives.example"]);
});
