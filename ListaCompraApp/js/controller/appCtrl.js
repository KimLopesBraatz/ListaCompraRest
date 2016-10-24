angular.module("listaCompraApp").controller("appCtrl", function($scope, itensAPI, listasAPI) {
	$scope.itens = itensAPI.query();
  	$scope.listas = listasAPI.query();
});
