(function(requirejs) {
  "use strict";

  // -- PROD RequireJS config --
  requirejs.config({
    packages: ["common", "main", "user", "dashboard", "sensor", "feed"],
    shim: {
      "_" : {},
      "jquery": {
        exports: "$"
      },
      "angular" : {
        exports : "angular",
        deps: ["jquery"]
      },
      "angular-cookies": ["angular"],
      "angular-route": ["angular"],
      //"angular-strap" : { deps: ['angular'] },
      //"angular-ui-bootstrap" : { deps: ['angular'] },
	  "highcharts":["jquery"],
      "highcharts-ng": ["angular", "highcharts"],
      "bootstrap" : ["jquery"],
      "app" : ["angular"],
      "jsRoutes" : {
        deps : [],
        // it's not a RequireJS module, so we have to tell it what var is returned
        exports : "jsRoutes"
      }
    },
    paths: {
      // Map the dependencies to CDNs or WebJars directly
      "_" : "//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.1/underscore-min",
      // Use WebJars as a fallback
      "jquery": ["//code.jquery.com/jquery-1.10.2.min", "/webjars/jquery/1.10.2/jquery.min"],
      "bootstrap": "//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min",
      "angular": "//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular.min",
      "angular-cookies": "//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular-cookies.min",
      "angular-route": "//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular-route.min",
      //"angular-strap": "//rawgithub.com/mgcrea/angular-strap/master/dist/angular-strap.min",
      //"angular-ui-bootstrap" : "//cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.9.0/ui-bootstrap-tpls.min",
      "highcharts": "//code.highcharts.com/highcharts",
      "highcharts-ng": "//rawgithub.com/pablojim/highcharts-ng/master/dist/highcharts-ng",
      "jsRoutes" : "/jsroutes"
    },
    priority: ["angular"]
  });

  requirejs.onError = function(err) {
    console.log(err);
  };

  // Make sure generic external scripts are loaded
  require(["angular", "app", "angular-cookies", "angular-route", "jquery", "bootstrap",  "highcharts-ng"], function(angular, app) {
    angular.bootstrap(document, ["app"]);
  });
})(requirejs);
