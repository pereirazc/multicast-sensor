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
    //"google-maps": ["jquery"],
    //"ui-event": ["angular"],
    //"ui-map": ["angular", "google-maps", "ui-event"],
    "angular-cookies": ["angular"],
    "angular-route": ["angular"],
    "angular-touch": ["angular"],
    "bootstrap" : ["jquery"],
    //"angular-strap": { deps: ["angular"] },
    //"angular-ui-bootstrap" : { deps: ["angular"] },
    "highcharts": ["jquery"],
    "highcharts-ng": ["angular", "highcharts"],
    "range-slider": ["angular", "jquery"],
    "bootstrap-switch": ["jquery"],
    "ng-switch": ["angular", "bootstrap-switch"],
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
    "async": "https://raw.github.com/millermedeiros/requirejs-plugins/master/src/async",
    "jquery": "empty:",
    //  "google-maps": "empty:",
    //  "ui-event": "https://rawgithub.com/angular-ui/ui-utils/master/modules/event/event",
    //  "ui-map": "http://angular-ui.github.io/ui-map/dist/ui-map.min",
    "bootstrap": "//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min",
    "angular": "//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular.min",
    "angular-cookies": "//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular-cookies.min",
    "angular-route": "//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular-route.min",
    //"angular-strap": "//rawgithub.com/mgcrea/angular-strap/master/dist/angular-strap.min",
    //"angular-ui-bootstrap" : "//cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.9.0/ui-bootstrap-tpls.min",
    "highcharts": "//code.highcharts.com/highcharts",
    "highcharts-ng": "//raw.github.com/pablojim/highcharts-ng/master/dist/highcharts-ng.min",
    "range-slider": "//raw.githubusercontent.com/danielcrisp/angular-rangeslider/v0.0.7/angular.rangeSlider",
    "bootstrap-switch": "////raw.githubusercontent.com/nostalgiaz/bootstrap-switch/v3.0.0/dist/js/bootstrap-switch.min",
    "ng-switch": "//raw.githubusercontent.com/frapontillo/angular-bootstrap-switch/0.3.0-alpha.2/dist/angular-bootstrap-switch.min",
    // empty: so the optimizer doesn't try to find jsRoutes.js in our project
    "jsRoutes" : "empty:"
  }
});
