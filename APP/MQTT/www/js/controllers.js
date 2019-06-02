angular.module('app.controllers', [])


  .controller('loginCtrl', function($scope,$rootScope,$ionicHistory,sharedUtils,$state,$ionicSideMenuDelegate) {
    $rootScope.extras = false;  // For hiding the side bar and nav icon

    // When the user logs out and reaches login page,
    // we clear all the history and cache to prevent back link
    $scope.$on('$ionicView.enter', function(ev) {
      if(ev.targetScope !== $scope){
        $ionicHistory.clearHistory();
        $ionicHistory.clearCache();
      }
    });

    //Check if user already logged in
    firebase.auth().onAuthStateChanged(function(user) {
      if (user) {

        $ionicHistory.nextViewOptions({
          historyRoot: true
        });
        $ionicSideMenuDelegate.canDragContent(true);  // Sets up the sideMenu dragable
        $rootScope.extras = true;
        sharedUtils.hideLoading();
        $state.go('topicsList', {}, {location: "replace"});

      }
    });


    $scope.loginEmail = function(formName,cred) {


      if(formName.$valid) {  // Check if the form data is valid or not

        sharedUtils.showLoading();

        //Email
        firebase.auth().signInWithEmailAndPassword(cred.email,cred.password).then(function(result) {

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
            $state.go('topicsList', {}, {location: "replace"});

          },
          function(error) {
            sharedUtils.hideLoading();
            sharedUtils.showAlert("Error al crear","Hubo un problema autenticarse, verifique que el correo este registrado con el administrador de la aplicación");
          }
        );

      }else{
        sharedUtils.showAlert("Error","Los datos ingresados no son validos");
      }

    };

  })

  .controller('signupCtrl', function($scope,$rootScope,sharedUtils,$ionicSideMenuDelegate,
                                     $state,fireBaseData,$ionicHistory) {
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
          sharedUtils.showAlert("Cuenta creada","Su cuenta ha sido creada, será dirigido al inicio");
          $state.go('topicsList', {}, {location: "replace"});

        }, function (error) {
          sharedUtils.hideLoading();
          sharedUtils.showAlert("Error al crear","Hubo un problema al crear la cuenta, verifique que el correo que uso no exista con el administrador de la aplicación");
        });

      }else{
        sharedUtils.showAlert("Error","Los datos ingresados no son validos");
      }

    }

  })

  .controller('topicsCtrl', function($scope,$rootScope,$ionicSideMenuDelegate,
                                     fireBaseData,$state,$ionicPopup,$firebaseObject,
                                     $ionicHistory,$firebaseArray,sharedUtils) {

    //Check if user already logged in
    sharedUtils.showLoading();
    firebase.auth().onAuthStateChanged(function(user) {
      if (user) {
        $scope.user_info=user;
        $scope.topics= $firebaseArray(fireBaseData.refMqtt().child(user.uid).child("topics"));

        $scope.topics.$loaded().then(function(data) {   //Calls when the firebase data is loaded
          sharedUtils.hideLoading();
        }, 500);

      }else {

        $ionicSideMenuDelegate.toggleLeft(); //To close the side bar
        $ionicSideMenuDelegate.canDragContent(false);  // To remove the sidemenu white space

        $ionicHistory.nextViewOptions({
          historyRoot: true
        });
        $rootScope.extras = false;
        sharedUtils.hideLoading();
        $state.go('tabsController.login', {}, {location: "replace"});
        sharedUtils.hideLoading();
      }
    });

    // On Loggin in to menu page, the sideMenu drag state is set to true
    $ionicSideMenuDelegate.canDragContent(true);
    $rootScope.extras=true;

    // When user visits A-> B -> C -> A and clicks back, he will close the app instead of back linking
    $scope.$on('$ionicView.enter', function(ev) {
      if(ev.targetScope !== $scope){
        $ionicHistory.clearHistory();
        $ionicHistory.clearCache();
      }
    });

    //Edit section
    $scope.itemManipulation = function(edit_val) {  // Takes care of item add and edit ie Item Manipulator
      var title,sub_title;
      if(edit_val!=null) {
        $scope.data=null;
        $scope.data = edit_val; // For editing address
        title="Editar topic";
        sub_title="Editar topic";
      }
      else {
        $scope.data = {};    // For adding new address
        title="Agregar Topic";
        sub_title="Agregar nuevo topic";
      }
      // An elaborate, custom popup
      var connectionPopup = $ionicPopup.show({
        template: '<label>Topic: </label>'+
                  '<select class="form-control" ng-model="data.topic">' +
                    '<option value="CISTERNA1/NIVEL">CISTERNA1/NIVEL</option>' +
                    '<option value="CISTERNA1/STATUS">CISTERNA1/STATUS</option>' +
                    '<option value="CISTERNA1/COMANDOS">CISTERNA1/COMANDOS</option>' +
                  '</select>'+
                  '<label>Elemento: </label>'+
                  '<select class="form-control" ng-model="data.img">' +
                    '<option value="line-chart">Line Chart</option>' +
                    '<option value="pie-chart">Pie Chart</option>' +
                    '<option value="display">Display</option>' +
                    '<option value="switch">Switch</option>' +
                    '<option value="message">Message</option>' +
                    '<option value="meter">Meter</option>' +
                  '</select>'+
                  '<img width="150px" ng-src="img/{{data.img}}"> <br/> ' +
                  '<input type="text" ng-model="data.img" disabled> <br/> '+
                  '<label>Descripción:</label>'+
                  '<input type="text"  placeholder="Descripción" ng-model="data.info"> <br/> ' ,
        title: title,
        subTitle: sub_title,
        scope: $scope,
        buttons: [
          { text: 'Cerrar' },
          {
            text: '<b>Guardar</b>',
            type: 'button-positive',
            onTap: function(e) {
              if ( !$scope.data.topic || !$scope.data.info  ) {
                e.preventDefault(); //don't allow the user to close unless he enters full details
              } else {
                return $scope.data;
              }
            }
          }
        ]
      });

      connectionPopup.then(function(res) {

        if(edit_val!=null) {
          //Update  address
          if(res!=null) { //res == null => close()
            if(!res.img){ res.img="metrics.svg";  }
            fireBaseData.refMqtt().child($scope.user_info.uid).child("topics").child(edit_val.$id).update({    // set
              topic: res.topic,
              info: res.info,
              img:res.img,
              element:res.element
            });
          }
        }else{
          //Add new address
          if(res!=null) {
            if (!res.img) {res.img = "metrics.svg";}
            fireBaseData.refMqtt().child($scope.user_info.uid).child("topics").push({    // set
              topic: res.topic,
              info: res.info,
              img: res.img,
              element:res.element
            });
          }
        }

      });

    };

    // A confirm dialog for deleting topic
    $scope.deleteTopic = function(del_id) {
      var confirmPopup = $ionicPopup.confirm({
        title: 'Eliminar Topic',
        template: '¿Estás seguro de que quieres eliminar este topic?',
        buttons: [
          { text: 'No' , type: 'button-stable' },
          { text: 'Si', type: 'button-assertive' , onTap: function(){return del_id;} }
        ]
      });

      confirmPopup.then(function(res) {
        if(res) {
          fireBaseData.refMqtt().child($scope.user_info.uid).child("topics").child(res).remove();
        }
      });
    };

    $scope.view_graph=function(c_id){
      fireBaseData.refMqtt().child($scope.user_info.uid).update({ currentTopic: c_id }); //set the current topic
      $state.go('graph', {}, {location: "replace"}); //move to graph page
    };

  })

  .controller('indexCtrl', function($scope,$rootScope,sharedUtils,$ionicHistory,$state,$ionicSideMenuDelegate) {

    //Check if user already logged in
    firebase.auth().onAuthStateChanged(function(user) {
      if (user) {
        $scope.user_info=user; //Saves data to user_info
      }else {

        $ionicSideMenuDelegate.toggleLeft(); //To close the side bar
        $ionicSideMenuDelegate.canDragContent(false);  // To remove the sidemenu white space

        $ionicHistory.nextViewOptions({
          historyRoot: true
        });
        $rootScope.extras = false;
        sharedUtils.hideLoading();
        $state.go('tabsController.login', {}, {location: "replace"});

      }
    });

    $scope.logout=function(){

      sharedUtils.showLoading();

      // Main Firebase logout
      firebase.auth().signOut().then(function() {

        $ionicSideMenuDelegate.toggleLeft(); //To close the side bar
        $ionicSideMenuDelegate.canDragContent(false);  // To remove the sidemenu white space

        $ionicHistory.nextViewOptions({
          historyRoot: true
        });


        $rootScope.extras = false;
        sharedUtils.hideLoading();
        $state.go('tabsController.login', {}, {location: "replace"});

      }, function(error) {
        sharedUtils.showAlert("Error","Logout Failed")
      });

    }

  })

  .controller('compareAllCtrl', function($scope,$rootScope) {
    //For compare All
  })

  .controller('settingsCtrl', function($scope,$rootScope,fireBaseData,$firebaseObject,
                                       $ionicPopup,$state,$window,$firebaseArray,
                                       sharedUtils) {
    //Bugs are most prevailing here
    $rootScope.extras=true;
    //Shows loading bar
    sharedUtils.showLoading();
    //Check if user already logged in
    firebase.auth().onAuthStateChanged(function(user) {
      if (user) {
        $scope.mqtt= $firebaseObject(fireBaseData.refMqtt().child(user.uid));
        $scope.user_info=user; //gives user id

        $scope.mqtt.$loaded().then(function(data) {   //Calls when the firebase data is loaded
          sharedUtils.hideLoading();
        }, 500);

      }
    });


    $scope.save= function (mqttRef) {

      if(mqttRef.username=="" || mqttRef.username==null){
        mqttRef.username="";
        mqttRef.password="";
      }
      client_id="myClientId" + new Date().getTime();
      if( (mqttRef.url!="" && mqttRef.url!=null ) &&
          (mqttRef.port!="" && mqttRef.port!=null )
        ){
        fireBaseData.refMqtt().child($scope.user_info.uid).update({
          url: mqttRef.url,
          port: mqttRef.port,
          username: mqttRef.username,
          password: mqttRef.password,
          ssl: mqttRef.ssl,
          clientId:client_id,
          currentTopic:""
        });
        console.log("Los datos se han guardado");
        sharedUtils.showAlert("Exito","Los datos se han guardado");
      } else {
        sharedUtils.showAlert("Error","Los datos se han podido guardado");
      }

    };

    $scope.cancel=function(){
      sharedUtils.showAlert("Aviso","Los cambios se han revertido");
      $window.location.reload(true);
      
    }

  })

  .controller('supportCtrl', function($scope,$rootScope) {

    $rootScope.extras=true;

  })

  .controller('graphCtrl', function($scope,$rootScope,fireBaseData,
                                    $firebaseObject,sharedUtils) {

    sharedUtils.showLoading(); // starts with loading bar

    /*--------------------------------FIREBASE---------------------------*/
    $rootScope.extras=true;
    //var mqttData;
    firebase.auth().onAuthStateChanged(function(user) {
      if (user) {
        mqttData=$firebaseObject(fireBaseData.refMqtt().child(user.uid));  //Mqtt data
        mqttData.$loaded().then(function(data) {   //Calls when the firebase data is loaded
            $scope.MQTTconnect();
          }, 500);

      }
    });
    /*--------------------------------END OF FIREBASE---------------------------*/




    /*--------------------------------MQTT---------------------------*/
    //MQTT variables
    var client;
    var reconnectTimeout = 2000;

    $scope.MQTTconnect=function() {

      console.log("START");
      client = new Paho.MQTT.Client(
        mqttData.url,
        Number(mqttData.port),
        mqttData.clientId  //Client Id
      );

      client.onConnectionLost = onConnectionLost;
      client.onMessageArrived = onMessageArrived;

      var options = {
        timeout: 3,
        useSSL:mqttData.ssl,
        onSuccess:onConnect,
        onFailure:doFail
      };

      if(mqttData.username!="" ){
        options.userName=mqttData.username;
        options.password=mqttData.password;
      }

      console.log("TXSS",options);
      client.connect(options);
    };

    function onConnect() {
      sharedUtils.hideLoading();
      console.log("onConnect");
      client.subscribe(mqttData.currentTopic);
    }

    function doFail(e){
      sharedUtils.hideLoading();
      console.log("Error",e);
      sharedUtils.showAlert("Error de configuración","Compruebe los datos de conexión");
      //setTimeout($scope.MQTTconnect, reconnectTimeout);
    }

    // called when the client loses its connection
    function onConnectionLost(responseObject) {
      if (responseObject.errorCode !== 0) {
        console.log("onConnectionLost:"+responseObject.errorMessage);
        sharedUtils.showAlert("Aviso","Se ha perdido la conexión");
        //$window.location.reload(true);
        //setTimeout($scope.MQTTconnect, reconnectTimeout);
      }
    }

    // called when a message arrives
    function onMessageArrived(message) {
      if(Number(message.payloadString)>0) { //-ve number are reserved for notification
        $scope.addPoint(Number(message.payloadString));
      } else {
        sharedUtils.showAlert("Aviso", message.payloadString);
      }

      console.log("message.payloadString: "+ message.payloadString);
    }

    
    /*--------------------------------END OF MQTT---------------------------*/






    /*--------------------------------GRAPH---------------------------*/



    /*var ISTOffset = 330;   // IST offset UTC +5:30

    var options = {
      chart: {
        renderTo: 'container',
        type: 'spline',
        animation: Highcharts.svg, // don't animate in old IE
        marginRight: 30
      },
      title: {
        text: 'Live Sensor data'
      },
      xAxis: {
        type: 'datetime',
        tickPixelInterval: 150
      },
      yAxis: {
        title: {
          text: 'Value'
        }
      },
      tooltip: {
        formatter: function () {
          return '<b>' + this.series.name + '</b><br/>' +
            Highcharts.dateFormat('%H:%M:%S', this.x) + '<br/>' +
            Highcharts.numberFormat(this.y, 2);
        }
      },
      legend: {
        enabled: false
      },
      exporting: {
        enabled: false
      },
      series: [{
        name: 'Sensor data',
        data: (function () {
              var data = [],
                  time = moment().tz("America/Mexico_City").valueOf()+(ISTOffset*60000),
                  i;

              for (i = -19; i <= 0; i += 1) {
                data.push({
                  x: time + i * 1000,
                  y: null
                });
              }
              return data;
            }())
      }]

    };

    var chart = Highcharts.chart(options);*/
   
    var config = {
        type: 'line',
        options: {
          title: {
            display: true,
            text: 'Sensor CISTERNA'
          },
          legend: {
              display: true,
              labels: {
                  fontColor: 'rgb(255, 99, 132)'
              }
            },
          layout: {
              padding: {
                  left: 50,
                  right: 0,
                  top: 0,
                  bottom: 0
              }
            },
            scales: {
              yAxes: [{
                  ticks: {
                      beginAtZero: true
                  }
              }]
            }
          },
        data: {
          labels: [],
          datasets: [{
            label: "Sensor data",
            data: [],
            fill: 'origin'
          }]
        }
      };

    var ctx = document.getElementById("chartMQTT").getContext("2d");
    var myChart = new Chart(ctx, config);
  
    $scope.addPoint = function (point) {
      var time = moment().tz("America/Mexico_City").format('HH:MM:SS');
      myChart.data.labels.push(time);
      myChart.data.datasets[0].data.push(point);
      myChart.update();

      /*chart.series[0].addPoint(
          [
            moment().tz("America/Mexico_City").valueOf()+(ISTOffset*60000),
            point
          ],true,true
      );*/
    };


    $scope.notify=function(){
      message = new Paho.MQTT.Message("a"); // -1 => Notify
      message.destinationName = 'CISTERNA1/COMANDOS';
      console.log("message.destinationName: "+message.destinationName);
      client.send(message);

    };

    
    //Called add data grafica
    function addData(chart, label, data) {
      chart.data.labels.push(label);
      chart.data.datasets.forEach((dataset) => {
          dataset.data.push(data);
      });
      chart.update();
    }

    

  });

    /*--------------------------------END OF GRAPH---------------------------*/




