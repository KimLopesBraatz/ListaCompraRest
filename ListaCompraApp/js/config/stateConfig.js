app.config(function($stateProvider, $urlRouterProvider) {
    
    $stateProvider
        .state("home", {
            url: "/home",
            templateUrl: "view/home.html",
            controller: "appCtrl"
        })
        .state("lista", {
            url: "/lista",
            templateUrl: "view/lista.html",
            controller: "listaCtrl"
        })
        .state("item", {
            url: "/item",
            templateUrl: "view/item.html",
            controller: "itemCtrl"
        });

    $urlRouterProvider.otherwise('/home');
});