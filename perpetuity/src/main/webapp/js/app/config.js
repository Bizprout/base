baseApp.config(function($routeProvider){
    
    $routeProvider.when("/",{templateUrl: "html/Login.html", controller:"LoginController"
    }).when("/home",{templateUrl:"html/Landing_Page.html", controller:"LoginController"
    }).when("/masters",{templateUrl:"html/Master_Page.html", controller:"LoginController"
    }).when("/mapping",{templateUrl:"html/Mapping_Page.html", controller:"LoginController"
    }).when("/reports",{templateUrl:"html/8-Reports.html", controller:"LoginController"
    }).when("/newuser",{templateUrl:"html/2-User_Creation.html", controller:"AddEditController"
    }).when("/newclient",{templateUrl:"html/3-Client_Master_Creation.html", controller:"AddEditController"
    }).when("/newcompany",{templateUrl:"html/4-Company_Master.html", controller:"DatepickerPopupDemoCtrl"
    }).when("/PPmasters",{templateUrl:"html/6-Create_PP_Standard_Masters.html", controller:"AddEditController"
    }).when("/PPstandards",{templateUrl:"html/7-Map_PP_Standard_with_Tally.html", controller:"AddEditController"
    }).when("/mappingUsers",{templateUrl:"html/5-User_Mapping_to_Clients.html", controller:"LoginController"
    }).when("/changepassword",{templateUrl:"html/Change_Password.html", controller:"LoginController"
    });   
        
});