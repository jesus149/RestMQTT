angular.module('app.controllers', [])


  .controller('loginCtrl', function ($scope, $rootScope, $ionicHistory, sharedUtils, $state, $ionicSideMenuDelegate, $http) {
    $rootScope.extras = false;  // For hiding the side bar and nav icon

    // When the user logs out and reaches login page,
    // we clear all the history and cache to prevent back link
    $scope.$on('$ionicView.enter', function (ev) {
      if (ev.targetScope !== $scope) {
        $ionicHistory.clearHistory();
        $ionicHistory.clearCache();
      }
    });

    //Check if user already logged in
    firebase.auth().onAuthStateChanged(function (user) {
      if (user) {

        $ionicHistory.nextViewOptions({
          historyRoot: true
        });
        $ionicSideMenuDelegate.canDragContent(true);  // Sets up the sideMenu dragable
        $rootScope.extras = true;
        sharedUtils.hideLoading();
        $state.go('topicsList', {}, { location: "replace" });

      }
    });


    $scope.loginEmail = function (formName, cred) {


      if (formName.$valid) {  // Check if the form data is valid or not

        sharedUtils.showLoading();

        //Email
        firebase.auth().signInWithEmailAndPassword(cred.email, cred.password).then(function (result) {

          // You dont need to save the users session as firebase handles it
          // You only need to :
          // 1. clear the login page history from the history stack so that you cant come back
          // 2. Set rootScope.extra;
          // 3. Turn off the loading
          // 4. Got to menu page

          $ionicHistory.nextViewOptions({
            historyRoot: true
          });
          $rootScope.extras = true;
          sharedUtils.hideLoading();
          $state.go('topicsList', {}, { location: "replace" });

          var correo = cred.email;
          var pass = cred.password;
          var idUsuarioFirebase = result.user.uid;

          var url = 'http://mqtt.sofisoft.com.mx/MQTT/apiRest/usuario/insertUsuarioGet/?correo=' + correo + '&pass=' + pass + '&idUsuarioFirebase=' + idUsuarioFirebase + '';

          $http.get(url).
            then(function (response) {
              console.log('response.data');
              console.log(response.data);
            });

        },
          function (error) {
            sharedUtils.hideLoading();
            sharedUtils.showAlert("Error al crear", "Hubo un problema autenticarse, verifique que el correo este registrado con el administrador de la aplicación");
          }
        );

      } else {
        sharedUtils.showAlert("Error", "Los datos ingresados no son validos");
      }

    };

  })

  .controller('signupCtrl', function ($scope, $rootScope, sharedUtils, $ionicSideMenuDelegate,
    $state, fireBaseData, $ionicHistory, $http) {
    $rootScope.extras = false; // For hiding the side bar and nav icon

    $scope.signupEmail = function (formName, cred) {

      if (formName.$valid) {  // Check if the form data is valid or not

        sharedUtils.showLoading();

        //Main Firebase Authentication part
        firebase.auth().createUserWithEmailAndPassword(cred.email, cred.password).then(function (result) {

          //Registered OK
          $ionicHistory.nextViewOptions({
            historyRoot: true
          });
          $ionicSideMenuDelegate.canDragContent(true);  // Sets up the sideMenu dragable
          $rootScope.extras = true;
          sharedUtils.hideLoading();
          sharedUtils.showAlert("Cuenta creada", "Su cuenta ha sido creada, será dirigido al inicio");
          $state.go('topicsList', {}, { location: "replace" });

          var correo = cred.email;
          var pass = cred.password;
          var idUsuarioFirebase = result.user.uid;

          var url = 'http://mqtt.sofisoft.com.mx/MQTT/apiRest/usuario/insertUsuarioGet/?correo=' + correo + '&pass=' + pass + '&idUsuarioFirebase=' + idUsuarioFirebase + '';

          $http.get(url).
            then(function (response) {
              console.log('response.data');
              console.log(response.data);
            });

        }, function (error) {
          sharedUtils.hideLoading();
          sharedUtils.showAlert("Error al crear", "Hubo un problema al crear la cuenta, verifique que el correo que uso no exista con el administrador de la aplicación");
        });

      } else {
        sharedUtils.showAlert("Error", "Los datos ingresados no son validos");
      }

    }

  })

  .controller('topicsCtrl', function ($scope, $rootScope, $ionicSideMenuDelegate,
    fireBaseData, $state, $ionicPopup, $firebaseObject,
    $ionicHistory, $firebaseArray, sharedUtils, $http) {

    //Check if user already logged in
    sharedUtils.showLoading();

    $scope.changeTopics = function (con) {

      firebase.auth().onAuthStateChanged(function (user) {

        if (user) {
          $scope.user_info = user;

          $scope.topics = $firebaseArray(fireBaseData.refMqtt().child(user.uid).child("conexiones").child(con.serviceName.serviceName).child("topics"));

          $scope.topics.$loaded().then(function (data) {   //Calls when the firebase data is loaded
            sharedUtils.hideLoading();
          }, 500);

        } else {

          $ionicSideMenuDelegate.toggleLeft(); //To close the side bar
          $ionicSideMenuDelegate.canDragContent(false);  // To remove the sidemenu white space

          $ionicHistory.nextViewOptions({
            historyRoot: true
          });
          $rootScope.extras = false;
          sharedUtils.hideLoading();
          $state.go('tabsController.login', {}, { location: "replace" });
          sharedUtils.hideLoading();
        }
      });

    }

    firebase.auth().onAuthStateChanged(function (user) {

      if (user) {
        $scope.user_info = user;

        $scope.conexiones = $firebaseArray(fireBaseData.refMqtt().child(user.uid).child("conexiones"));

        $scope.conexiones.$loaded().then(function (data) {   //Calls when the firebase data is loaded
          sharedUtils.hideLoading();
        }, 500);

      } else {

        $ionicSideMenuDelegate.toggleLeft(); //To close the side bar
        $ionicSideMenuDelegate.canDragContent(false);  // To remove the sidemenu white space

        $ionicHistory.nextViewOptions({
          historyRoot: true
        });
        $rootScope.extras = false;
        sharedUtils.hideLoading();
        $state.go('tabsController.login', {}, { location: "replace" });
        sharedUtils.hideLoading();
      }
    });

    // On Loggin in to menu page, the sideMenu drag state is set to true
    $ionicSideMenuDelegate.canDragContent(true);
    $rootScope.extras = true;

    // When user visits A-> B -> C -> A and clicks back, he will close the app instead of back linking
    $scope.$on('$ionicView.enter', function (ev) {
      if (ev.targetScope !== $scope) {
        $ionicHistory.clearHistory();
        $ionicHistory.clearCache();
      }
    });

    //Insert new Topic
    $scope.createTopic = function () {
      sharedUtils.showLoading();
      var nombre = $scope.data.nuevoTopic;
      var idUsuarioFirebase = $scope.user_info.uid;
      var url = 'http://mqtt.sofisoft.com.mx/MQTT/apiRest/topic/insertCatTopics/?nombre=' + nombre + '&idUsuarioFirebase=' + idUsuarioFirebase;

      $http.get(url).
        then(function (response) {
          console.log('response.data');
          console.log(response.data);
          if (response.data.output == 1) {
            sharedUtils.hideLoading();
            $scope.getListTopics();
            $scope.data.nuevoTopic = null;
            sharedUtils.showAlert("Exito", "Los datos se han guardado");
          } else {
            sharedUtils.hideLoading();
            sharedUtils.showAlert("Error", "vuelve a intentarlo");
          }
        });
    };

    $scope.nuevoTopic = function () {

      document.getElementById("selectTopic").disabled = true;
      document.getElementById("divNuevoTopic").style.display = "block";
      document.getElementById("nuevoTopic").value = "";


    }

    $scope.cerrarModal = function () {

      document.getElementById("selectTopic").disabled = false;
      document.getElementById("divNuevoTopic").style.display = "none";

    }

    //Edit section
    $scope.itemManipulation = function (edit_val) {
      sharedUtils.showLoading();
      // Takes care of item add and edit ie Item Manipulator
      var title, sub_title;
      $http.get('http://mqtt.sofisoft.com.mx/MQTT/apiRest/topic/listCatTopics').success(function (data) {

        $scope.lisTopics = data.listCatTopics;
        sharedUtils.hideLoading();
      });

      if (edit_val != null) {
        $scope.data = null;
        $scope.data = edit_val; // For editing address
        title = "Editar topic";
        sub_title = "Editar topic";
      }
      else {
        $scope.data = {};    // For adding new address
        title = "Agregar Topic";
        sub_title = "Agregar nuevo topic";
      }
      // An elaborate, custom popup
      var connectionPopup = $ionicPopup.show({
        template: '<label>Conexion: </label>' +
          '<select class="form-control" ng-model="data.serviceName" ng-options="conexiones.serviceName for conexiones in conexiones">' +
          '</select>' +

          '<label>Topic: </label>' +
          '<select id="selectTopic" class="form-control" name="listTopics" ng-model="data.topic" ' +
          'ng-options="lisTopics.topic for lisTopics in lisTopics track by lisTopics.topic" >' +
          '<option value="">selecciona una opción</option>' +
          '</select>' +

          '<button id="btn-nuevo-topic" ng-click="nuevoTopic();">Nuevo Topic <img class="img-rounded" width="25px" src="img/add-topic.svg"></button></br>' +
          '<div id="divNuevoTopic" style="display: none" class="content-nuevo-topic">' +
          '<input id="nuevoTopic" class="form-control" type="text"  placeholder="Ejemplo: nuevo/topic" ng-model="data.topic"> <br/>' +
          '<button ng-click="cerrarModal()" type="button" class="btn btn-light">Cancelar <img class="img-rounded" width="25px" src="img/cancel.svg"></button>' +
          '</div>' +
          '<label>Elemento: </label>' +
          '<select class="form-control" ng-model="data.element">' +
          '<option value="line">Line Chart</option>' +
          '<option value="pie">Pie Chart</option>' +
          '<option value="display">Display</option>' +
          '<option value="switch">Switch</option>' +
          '<option value="message">Message</option>' +
          '<option value="meter">Meter</option>' +
          '</select>' +
          '<img class="img-rounded" width="150px" ng-src="img/{{data.element}}.png"> <br/> ' +
          '<label for="data-info">Descripción:</label>' +
          '<input id="data-info" class="form-control" type="text"  placeholder="Descripción" ng-model="data.info"> <br/> ',
        title: title,
        subTitle: sub_title,
        scope: $scope,
        buttons: [
          { text: 'Cerrar' },
          {
            text: '<b>Guardar</b>',
            type: 'button-positive',
            onTap: function (e) {
              if (!$scope.data.topic || !$scope.data.info) {
                e.preventDefault(); //don't allow the user to close unless he enters full details
              } else {
                return $scope.data;
              }
            }
          }
        ]
      });

      connectionPopup.then(function (res) {
        //sharedUtils.showLoading();
        if (edit_val == null) {

          //Update  address
          if (res != null) {

            if (!res.img) { res.img = "metrics.svg"; }

            var topicValue;

            if(res.topic.topic != null){
              topicValue = res.topic.topic;
            }else{
              topicValue = res.topic;
            }

            var newRef = fireBaseData.refMqtt().child($scope.user_info.uid).child("conexiones").child(res.serviceName.serviceName).child("topics").push({    // set
              topic: topicValue,
              info: res.info,
              img: res.element,
              element: res.element,
              serviceName: res.serviceName.serviceName,
              currentTopic: topicValue
            });
          }
          var topic = topicValue;
          var descripcion = res.info;
          var elemento = res.element;
          var imagen = res.element;
          var idTopicFirebase = newRef.path.o[5];
          var clientId = res.serviceName.clientId;
          var idUsuarioFirebase = $scope.user_info.uid;

          var url = 'http://mqtt.sofisoft.com.mx/MQTT/apiRest/topic/insertCatTopics/?nombre=' + topic.toUpperCase() + '&idUsuarioFirebase=' + idUsuarioFirebase;

          var url2 = 'http://mqtt.sofisoft.com.mx/MQTT/apiRest/topic/insertTopicsGet/?topic=' + topic + '&descripcion=' + descripcion + '&elemento=' + elemento + '&imagen=' + imagen + '&idTopicFirebase=' + idTopicFirebase + '&clientId=' + clientId + '&idUsuarioFirebase=' + idUsuarioFirebase + '';

          $http.get(url).
            then(function (response) {
            });

          $http.get(url2).
            then(function (response) {
            });

        } else {
          //Add new address
          if (res != null) {
            /*AGREGANDO NUEVO TOPIC */
            /*TERMINA AGREGANDO NUEVO TOPIC */
            if (!res.img) { res.img = "metrics.svg"; }

            var topicValue;

            if(res.topic.topic != null){
              topicValue = res.topic.topic;
            }else{
              topicValue = res.topic;
            }

            fireBaseData.refMqtt().child($scope.user_info.uid).child("conexiones").child(res.serviceName.serviceName).child("topics").child(edit_val.$id).update({    // set
              topic: topicValue,
              info: res.info,
              img: res.element,
              element: res.element,
              serviceName: res.serviceName.serviceName,
              currentTopic: topicValue
            });

            var topic = topicValue;
            var descripcion = res.info;
            var elemento = res.element;
            var imagen = res.element;

            var idTopicFirebase = res.$id;
            var clientId = res.serviceName.clientId;
            var idUsuarioFirebase = $scope.user_info.uid;

            var url = 'http://mqtt.sofisoft.com.mx/MQTT/apiRest/topic/insertTopicsGet/?topic=' + topic.toUpperCase() + '&descripcion=' + descripcion + '&elemento=' + elemento + '&imagen=' + imagen + '&idTopicFirebase=' + idTopicFirebase + '&clientId=' + clientId + '&idUsuarioFirebase=' + idUsuarioFirebase + '';

            var url2 = 'http://mqtt.sofisoft.com.mx/MQTT/apiRest/topic/insertCatTopics/?nombre=' + topic + '&idUsuarioFirebase=' + idUsuarioFirebase;

            $http.get(url).
              then(function (response) {
              });

            $http.get(url2).
              then(function (response) {
              });

          }
          sharedUtils.hideLoading();
        }

      });

    };

    // A confirm dialog for deleting topic
    $scope.deleteTopic = function (del_id, service) {
      var confirmPopup = $ionicPopup.confirm({
        title: 'Eliminar Topic',
        template: '¿Estás seguro de que quieres eliminar este topic?',
        buttons: [
          { text: 'No', type: 'button-stable' },
          { text: 'Si', type: 'button-assertive', onTap: function () { return del_id; } }
        ]
      });

      confirmPopup.then(function (res) {
        if (res) {
          fireBaseData.refMqtt().child($scope.user_info.uid).child("conexiones").child(service).child("topics").child(res).remove();
        }
      });
    };

    $scope.view_graph = function (item) {
      fireBaseData.refMqtt().child($scope.user_info.uid).child("conexiones").child(item.serviceName).update({ currentTopic: item.topic }); //set the current topic
      $state.go('graph', { serviceName: item.serviceName, element: item }, { location: "replace" }); //move to graph page
    };

  })

  .controller('indexCtrl', function ($scope, $rootScope, sharedUtils, $ionicHistory, $state, $ionicSideMenuDelegate) {

    //Check if user already logged in
    firebase.auth().onAuthStateChanged(function (user) {
      if (user) {
        $scope.user_info = user; //Saves data to user_info
      } else {

        $ionicSideMenuDelegate.toggleLeft(); //To close the side bar
        $ionicSideMenuDelegate.canDragContent(false);  // To remove the sidemenu white space

        $ionicHistory.nextViewOptions({
          historyRoot: true
        });
        $rootScope.extras = false;
        sharedUtils.hideLoading();
        $state.go('tabsController.login', {}, { location: "replace" });

      }
    });

    $scope.logout = function () {

      sharedUtils.showLoading();

      // Main Firebase logout
      firebase.auth().signOut().then(function () {

        $ionicSideMenuDelegate.toggleLeft(); //To close the side bar
        $ionicSideMenuDelegate.canDragContent(false);  // To remove the sidemenu white space

        $ionicHistory.nextViewOptions({
          historyRoot: true
        });


        $rootScope.extras = false;
        sharedUtils.hideLoading();
        $state.go('tabsController.login', {}, { location: "replace" });

      }, function (error) {
        sharedUtils.showAlert("Error", "Logout Failed")
      });

    }

  })

  .controller('compareAllCtrl', function ($scope, $rootScope) {
    //For compare All
  })

  .controller('settingsCtrl', function ($scope, $rootScope, fireBaseData, $firebaseObject,
    $ionicPopup, $state, $window, $firebaseArray,
    sharedUtils, $http) {
    //Bugs are most prevailing here
    $rootScope.extras = true;
    //Shows loading bar
    sharedUtils.showLoading();
    //Check if user already logged in
    firebase.auth().onAuthStateChanged(function (user) {
      if (user) {

        $scope.conexiones = $firebaseArray(fireBaseData.refMqtt().child(user.uid).child("conexiones"));

        $scope.user_info = user; //gives user id

        $scope.conexiones.$loaded().then(function (data) {   //Calls when the firebase data is loaded
          sharedUtils.hideLoading();
        }, 500);

      }
    });

    $scope.changeConexion = function (con) {

      firebase.auth().onAuthStateChanged(function (user) {
        if (user) {
          $scope.mqtt = $firebaseObject(fireBaseData.refMqtt().child(user.uid).child("conexiones").child(con.serviceName.serviceName));

          $scope.user_info = user; //gives user id

          $scope.mqtt.$loaded().then(function (data) {   //Calls when the firebase data is loaded
            sharedUtils.hideLoading();
          }, 500);

        }
      });

      document.getElementById("serviceName").disabled = true;

    }


    $scope.save = function (mqttRef) {

      console.log(mqttRef);

      if (mqttRef.username == "" || mqttRef.username == null) {
        mqttRef.username = "";
        mqttRef.password = "";
      }
      client_id = "myClientId" + $scope.user_info.uid + mqttRef.password.substring(0, 4);
      if ((mqttRef.url != "" && mqttRef.url != null) &&
        (mqttRef.port != "" && mqttRef.port != null)
      ) {
        fireBaseData.refMqtt().child($scope.user_info.uid).child("conexiones").child(mqttRef.serviceName).update({
          url: mqttRef.url,
          port: mqttRef.port,
          username: mqttRef.username,
          password: mqttRef.password,
          ssl: mqttRef.ssl,
          clientId: client_id,
          serviceName: mqttRef.serviceName
        });

        var nombreServicio = mqttRef.serviceName;
        var host = mqttRef.url;
        var puerto = mqttRef.port;
        var ssl = mqttRef.ssl;
        var usuario = mqttRef.username;
        var password = mqttRef.password;
        var clientId = client_id;
        var idUsuarioFirebase = $scope.user_info.uid;

        var url = 'http://mqtt.sofisoft.com.mx/MQTT/apiRest/conexion/insertConexionGet/?nombreServicio=' + nombreServicio + '&host=' + host + '&puerto=' + puerto + '&ssl=' + ssl + '&usuario=' + usuario + '&password=' + password + '&clientId=' + clientId + '&idUsuarioFirebase=' + idUsuarioFirebase + '';

        $http.get(url).
          then(function (response) {
            console.log(response.data);
          });

        console.log("Los datos se han guardado");
        sharedUtils.showAlert("Exito", "Los datos se han guardado");
      } else {
        sharedUtils.showAlert("Error", "Los datos se han podido guardado");
      }

    };

    $scope.cancel = function () {
      sharedUtils.showAlert("Aviso", "Los cambios se han revertido");
      $window.location.reload(true);

    }

    $scope.resetForm = function () {
      document.getElementById("conexiones").value = "";
      document.getElementById("serviceName").value = "";
      document.getElementById("serviceName").disabled = false;
      document.getElementById("host").value = "";
      document.getElementById("puerto").value = "";
      document.getElementById("usuario").value = "";
      document.getElementById("password").value = "";
    }

  })

  .controller('supportCtrl', function ($scope, $rootScope) {

    $rootScope.extras = true;

  })

  .controller('graphCtrl', function ($scope, $rootScope, fireBaseData,
    $firebaseObject, sharedUtils, $stateParams, $http) {

    sharedUtils.showLoading(); // starts with loading bar

    /*--------------------------------FIREBASE---------------------------*/
    $rootScope.extras = true;
    //var mqttData;
    firebase.auth().onAuthStateChanged(function (user) {

      if (user) {
        $scope.useridFireBase = user.uid;
        $scope.topics = $firebaseObject(fireBaseData.refMqtt().child(user.uid).child("topics"));
        $scope.topics.$loaded().then(function (data) {   //Calls when the firebase data is loaded
          sharedUtils.hideLoading();
        }, 500);

        mqttData = $firebaseObject(fireBaseData.refMqtt().child(user.uid).child("conexiones").child($stateParams.serviceName));  //Mqtt data
        mqttData.$loaded().then(function (data) {   //Calls when the firebase data is loaded
          $scope.MQTTconnect();
        }, 500);

      }
    });
    /*--------------------------------END OF FIREBASE---------------------------*/




    /*--------------------------------MQTT---------------------------*/
    //MQTT variables
    var client;
    var reconnectTimeout = 2000;
    var element;

    $scope.MQTTconnect = function () {

      client = new Paho.MQTT.Client(
        mqttData.url,
        Number(mqttData.port),
        mqttData.clientId  //Client Id
      );
      client.onConnectionLost = onConnectionLost;
      client.onMessageArrived = onMessageArrived;

      var options = {
        timeout: 3,
        useSSL: mqttData.ssl,
        onSuccess: onConnect,
        onFailure: doFail
      };

      if (mqttData.username != "") {
        options.userName = mqttData.username;
        options.password = mqttData.password;
      }

      console.log("TXSS", options);
      client.connect(options);
    };

    function onConnect() {
      sharedUtils.hideLoading();
      console.log("onConnect");
      client.subscribe(mqttData.currentTopic);
      console.log("TOPCS", mqttData.currentTopic);
    }

    function doFail(e) {
      sharedUtils.hideLoading();
      console.log("Error", e);
      sharedUtils.showAlert("Error de configuración", "Compruebe los datos de conexión");
      //setTimeout($scope.MQTTconnect, reconnectTimeout);
    }

    // called when the client loses its connection
    function onConnectionLost(responseObject) {
      if (responseObject.errorCode !== 0) {
        console.log("onConnectionLost:" + responseObject.errorMessage);
        sharedUtils.showAlert("Aviso", "Se ha perdido la conexión");
        //$window.location.reload(true);
        //setTimeout($scope.MQTTconnect, reconnectTimeout);
      }
    }

    // called when a message arrives
    function onMessageArrived(message) {

      var valor = message.payloadString;
      var idUsuarioFirebase = $scope.useridFireBase;
      var clientId = mqttData.clientId;
      var idTopicFirebase = $stateParams.element.$id;
      //console.log("ID");
      //console.log(idTopicFirebase);
      var url = 'http://mqtt.sofisoft.com.mx/MQTT/apiRest/usuarioConexionTopic/insertUsuarioConexionTopicGet/?valor=' + valor + '&idUsuarioFirebase=' + idUsuarioFirebase + '&clientId=' + clientId + '&idTopicFirebase=' + idTopicFirebase;

      //console.log(url);
      $http.get(url).
        then(function (response) {
          console.log('response.data');
          console.log(response.data);
        });


      if (Number(message.payloadString) > 0) { //-ve number are reserved for notification
        $scope.addPoint(Number(message.payloadString));
      } else {
        if ($stateParams.element.element == "message") {
          $scope.addPoint(message.payloadString);
        } else {
          sharedUtils.showAlert("Aviso", message.payloadString);
        }
      }

      console.log("message.payloadString: " + message.payloadString);
    }


    /*--------------------------------END OF MQTT---------------------------*/






    /*--------------------------------GRAPH---------------------------*/
    $scope.element = $stateParams.element.element;

    switch ($scope.element) {
      case 'line':
        google.charts.load('current', { 'packages': ['line'] });
        google.charts.setOnLoadCallback(drawChartLine);

        function drawChartLine() {

          $scope.data = new google.visualization.DataTable();
          $scope.data.addColumn('timeofday', 'Time');
          $scope.data.addColumn('number', $stateParams.element.info);

          currentHoras = new Date().getHours();
          currentMinute = new Date().getMinutes();
          currentsegundos = new Date().getSeconds();
          $scope.data.addRows([
            [[currentHoras, currentMinute, currentsegundos], 1]
          ]);

          $scope.options = {
            chart: {
              title: $stateParams.element.info,
              subtitle: $stateParams.element.topic
            },
            lineWidth: 10,
            width: 500,
            height: 300
          };

          $scope.chart = new google.charts.Line(document.getElementById('chartMQTTL'));

          $scope.chart.draw($scope.data, google.charts.Line.convertOptions($scope.options));
        }
        break;
      case 'pie':
        google.charts.load("current", { packages: ["corechart"] });
        google.charts.setOnLoadCallback(drawChart);
        function drawChart() {
          $scope.data = google.visualization.arrayToDataTable([
            ['Task', 'Hours per Day'],
            ['Otro', 11],
            [$stateParams.element, 2]
          ]);

          $scope.options = {
            width: 500,
            height: 300,
            title: $stateParams.element.info,
            is3D: true,
          };

          $scope.chartPie = new google.visualization.PieChart(document.getElementById('chartMQTT'));
          $scope.chartPie.draw($scope.data, $scope.options);
        }

        break;
      case 'meter':
        google.charts.load('current', { 'packages': ['gauge'] });
        google.charts.setOnLoadCallback(drawChartMeter);

        function drawChartMeter() {

          $scope.data = google.visualization.arrayToDataTable([
            ['Label', 'Value'],
            [$scope.element, 0]
          ]);

          $scope.options = {
            width: 500, height: 300,
            redFrom: 90, redTo: 100,
            yellowFrom: 75, yellowTo: 90,
            minorTicks: 5,
            subtitle: $stateParams.element.topic
          };

          $scope.chartMeter = new google.visualization.Gauge(document.getElementById('chartMQTTM'));

          $scope.chartMeter.draw($scope.data, $scope.options);


        }
        break;
      case 'switch':
        var info = $stateParams.element.info;

        break;
      case 'message':
        google.charts.load('current', { 'packages': ['table'] });
        google.charts.setOnLoadCallback(drawTable);

        function drawTable() {
          $scope.dataTable = new google.visualization.DataTable();
          $scope.dataTable.addColumn('string', 'Mensaje');
          $scope.dataTable.addColumn('boolean', 'Recibido');

          $scope.dataTable.addRows([
            ['Sin mensaje', false]
          ]);

          $scope.table = new google.visualization.Table(document.getElementById('chartMQTTMMS'));

          $scope.table.draw($scope.dataTable, { showRowNumber: true, width: '100%', height: '100%' });
        }
        break;
      default:
        var info = $stateParams.element.info;

        break;

    }



    $scope.addPoint = function (point) {

      switch ($scope.element) {
        case 'line':
          currentHoras = new Date().getHours();
          currentMinute = new Date().getMinutes();
          currentsegundos = new Date().getSeconds();
          $scope.data.addRows([
            [[currentHoras, currentMinute, currentsegundos], point]
          ]);
          $scope.chart.draw($scope.data, google.charts.Line.convertOptions($scope.options));
          break;
        case 'pie':
          $scope.dataPie = google.visualization.arrayToDataTable([
            ['Task', 'Hours per Day'],
            ['Otro', 0],
            [$stateParams.element.element, point]
          ]);
          $scope.chartPie.draw($scope.dataPie, $scope.options);
          break;
        case 'meter':
          $scope.data.setValue(0, 1, point);
          $scope.chartMeter.draw($scope.data, $scope.options);
        case 'message':
          $scope.dataTable.addRows([
            [point, true]
          ]);
          $scope.table.draw($scope.dataTable, { showRowNumber: true, width: '100%', height: '100%' });
          break;

      }

    };

    $scope.notifyOnOff = function () {
      message = new Paho.MQTT.Message("1"); // -1 => Notify
      message.destinationName = 'CISTERNA1/COMANDOS';
      console.log("message.destinationName: " + message.destinationName);
      client.send(message);

    };

    $scope.notify = function () {
      message = new Paho.MQTT.Message("Notificacion: " + $scope.element); // -1 => Notify
      message.destinationName = 'CISTERNA1/COMANDOS';
      console.log("message.destinationName: " + message.destinationName);
      client.send(message);

    };



  });

/*--------------------------------END OF GRAPH---------------------------*/



