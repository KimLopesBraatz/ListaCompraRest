angular.module("listaCompraApp").controller("listaCtrl", function($scope, $timeout, listasAPI, itensAPI) {
  $scope.listas = listasAPI.query();
  $scope.produtos = [{id: 'produto1'}];
  $scope.lista = {total: 0};

	$scope.loadAll = function () {
	  	$scope.listas = listasAPI.query();
	};

	$scope.init = function () {
		$timeout(function(){
    		if ($scope.editForm.$dirty = true) {
    			getProdutosFromForm();
    		}
  		});
	}
	
  	$scope.save = function(lista) {
    	$scope.isSaving = true;

        	if ($scope.lista.id == null) {
            	listasAPI.save(lista, onSaveSuccess, onSaveError);
            	clear();
        	} else if ($scope.lista.id !== null) {
            	listasAPI.update(lista, onSaveSuccess, onSaveError);
            	clear();
        	};
    };

 	function onSaveSuccess (result) {
        $scope.$emit('listaCompraApp:listaUpdate', result);
        for (var i = 0; i < $scope.itens.length; i++) {
        	delete $scope.itens[i].qtd;
    		delete $scope.itens[i].subtotal;
        	$scope.itens[i].lista = result;
        	itensAPI.save($scope.itens[i]);
        }
        $scope.isSaving = false;
   	};

    function onSaveError () {
        $scope.isSaving = false;
    };

    var getProdutosFromForm = function () {
    	$scope.itens = [];
    	var temp = [];
    	var total = 0.0;
    	var sub = 0.0;

    	for (var i = 0; i <= $scope.produtos.length - 1; i++) {
    		temp = $scope.produtos[i];
    		
    		$scope.produtos[i].subtotal = ($scope.produtos[i].valor * $scope.produtos[i].qtd);
    		sub = $scope.produtos[i].subtotal;

    		if (!isNaN(sub)) {
    			console.log("É NaN");
    		}
    		total += sub;

    		if ($scope.produtos.length !== 0) {
    			delete temp.id;
    			$scope.itens.push(temp);
    		};

    	};
    	$scope.lista.total = total;
    };

    $scope.addContactForm = function () {
    	var newProduto = $scope.produtos.length +1;
    	$scope.produtos.push({'id': 'produto' + newProduto});
    	getProdutosFromForm();
    };

    $scope.removeProdutoForm = function (form) {
    	if ($scope.produtos.length == 1) {
    		$scope.produtos = [{id: 'produto1'}];
    	} else {
    		$scope.produtos.splice(form.$index, 1);
    	};
    	getProdutosFromForm();
    };

    var clear = function () {
    	for (var i = 0; i < $scope.produtos.length - 1; i++) {
    		if ($scope.produtos.length == 1) {
    			$scope.produtos = [{id: 'produto1'}];
    		} else {
    			$scope.produtos.splice($scope.produtos[i], 1);
    		};
    	};
    	delete $scope.produtos[i].descricao;
    	delete $scope.produtos[i].valor;
    	delete $scope.item;
        delete $scope.lista;

        $scope.editForm.$setPristine();
    };
});
