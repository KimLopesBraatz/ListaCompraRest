var app = angular.module("listaCompraApp", ["ui.router", "ngResource"]);

app.config(function($httpProvider){
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
});
