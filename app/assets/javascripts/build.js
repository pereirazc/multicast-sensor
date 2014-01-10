/*
 * A custom build profile that is passed to the optimizer via requireJsShim in build.sbt.
 * Play does this via settings it as the mainConfigFile:
 * http://requirejs.org/docs/optimization.html#mainConfigFile
 */
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
    "bootstrap" : ["jquery"],
    //"angular-strap": { deps: ["angular"] },
    //"angular-ui-bootstrap" : { deps: ["angular"] },
    "highcharts": ["jquery"],
    "highcharts-ng": ["angular", "highcharts"],
    "app" : ["angular"],
    "jsRoutes" : {
      deps : [],
      // it's not a RequireJS module, so we have to tell it what var is returned
      exports : "jsRoutes"
    }
  },
  paths: {
    "_" : "//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.1/underscore-min",
    // empty: so the optimizer ignores jquery; necessary because it doesn't support fallbacks
    "jquery": "empty:",
    "bootstrap": "//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min",
    "angular": "//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular.min",
    "angular-cookies": "//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular-cookies.min",
    "angular-route": "//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular-route.min",
    //"angular-strap": "//rawgithub.com/mgcrea/angular-strap/master/dist/angular-strap.min",
    //"angular-ui-bootstrap" : "//cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.9.0/ui-bootstrap-tpls.min",
    "highcharts": "//code.highcharts.com/highcharts",
    "highcharts-ng": "//raw.github.com/pablojim/highcharts-ng/master/dist/highcharts-ng.min",
    // empty: so the optimizer doesn't try to find jsRoutes.js in our project
    "jsRoutes" : "empty:"
  }
});
