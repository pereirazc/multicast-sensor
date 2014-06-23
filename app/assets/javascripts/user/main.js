/**
 * User package module.
 * Manages all sub-modules so other RequireJS modules only have to import the package.
 */
define(['angular', './routes', './services'], function(angular, routes, controllers) {
  'use strict';

    var mod =  angular.module('multicast.user', ['ngCookies', 'ui.router', 'user.routes', 'user.services']);

    mod.controller('LoginCtrl', controllers.LoginCtrl);
    mod.controller('SignupCtrl', controllers.SignupCtrl);

    return mod;
});
