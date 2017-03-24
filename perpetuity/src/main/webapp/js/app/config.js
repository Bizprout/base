baseApp.config(function($routeProvider){
    
    $routeProvider.when("/",{templateUrl: "html/Login.html", controller:"LoginController"
    }).when("/home",{templateUrl:"html/LandingPage.html", controller:"LoginController"
    }).when("/masters",{templateUrl:"html/MasterPage.html", controller:"LoginController"
    }).when("/mapping",{templateUrl:"html/MappingPage.html", controller:"LoginController"
    }).when("/reports",{templateUrl:"html/Reports.html", controller:"AddEditController"
    }).when("/newuser",{templateUrl:"html/UserCreation.html", controller:"AddEditController"
    }).when("/newclient",{templateUrl:"html/ClientMasterCreation.html", controller:"AddEditController"
    }).when("/newcompany",{templateUrl:"html/CompanyMaster.html", controller:"AddEditController"
    }).when("/PPmasters",{templateUrl:"html/CreatePPStandardMasters.html", controller:"AddEditController"
    }).when("/PPstandards",{templateUrl:"html/MapPPStandardwithTally.html", controller:"AddEditController"
    }).when("/mappingUsers",{templateUrl:"html/UserMappingtoClients.html", controller:"AddEditController"
    }).when("/changepassword",{templateUrl:"html/ChangePassword.html", controller:"LoginController"
    });   
        
});