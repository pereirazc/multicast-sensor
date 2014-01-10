/**
 * User service, exposes user model to the rest of the app.
 */
define(["angular", "common"], function(angular) {
  var mod = angular.module("user.services", ["ngRoute", "ngCookies", "yourprefix.common"]);

  mod.factory("userService", ["$http", "$q", "playRoutes", "$cookieStore", function($http, $q, playRoutes, $cookieStore) {
    var UserService = function() {
      var self = this;
      var user;
      var token;
      self.loginUser = function(credentials) {

        console.log("a");
        return playRoutes.controllers.Application.login().post(credentials).then(function(response) {
          // return promise so we can chain easily
          this.token = response.data.token;
          // in a real app we could use the token to fetch the user data
          return playRoutes.controllers.Users.user(3).get();
        }).then(function(response) {
          user = response.data; // Extract user data from user() request
          user.email = credentials.email;
          self.user = user;
          $cookieStore.remove('user');
          $cookieStore.put('user', user);
          return user;
        });

      };
      self.logout = function() {
        $cookieStore.remove('user');
        // Logout on server in a real app
        self.user = undefined;
      };
      self.getUser = function() {
        var user = $cookieStore.get('user');
        if (user!=undefined) {
            self.user = user;
        }
        return user;
      };
    };
    return new UserService();
  }]);
  /**
   * Add this object to a route definition to only allow resolving the route if the user is
   * logged in.
   */
  mod.constant("userResolve", {
    checkAuth: ["$q", "userService", function($q, userService) {
      var deferred = $q.defer();
      if (userService.getUser()) {
        deferred.resolve();
      } else {
        deferred.reject();
      }
      return deferred.promise;
    }]
  });
  /**
   * If the current route does not resolve, go back to the start page.
   */
  var handleRouteError = function($rootScope, $location) {
    $rootScope.$on("$routeChangeError", function(e, next, current) {
      $location.path("/");
    });
  };
  handleRouteError.$inject = ["$rootScope", "$location"];
  mod.run(handleRouteError);
  return mod;
});
