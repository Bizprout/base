baseApp.config(function($routeProvider){
    
    $routeProvider.when("/",{templateUrl: "html/Login.html", controller:"LoginController"
    }).when("/home",{templateUrl:"html/LandingPage.html", controller:"LoginController"
    }).when("/masters",{templateUrl:"html/MasterPage.html", controller:"LoginController"
    }).when("/mapping",{templateUrl:"html/MappingPage.html", controller:"LoginController"
    }).when("/reports",{templateUrl:"html/reports/Reports.html", controller:"AddEditController"
    }).when("/newuser",{templateUrl:"html/usercreation/UserCreation.html", controller:"UserController"
    }).when("/newclient",{templateUrl:"html/clientmaster/ClientMasterCreation.html", controller:"AddEditController"
    }).when("/newcompany",{templateUrl:"html/companymaster/CompanyMaster.html", controller:"AddEditController"
    }).when("/PPmasters",{templateUrl:"html/ppstandardmaster/CreatePPStandardMasters.html", controller:"PPMasterController"
    }).when("/PPstandards",{templateUrl:"html/ppstandardmapping/MapPPStandardwithTally.html", controller:"AddEditController"
    }).when("/mappingUsers",{templateUrl:"html/usermapping/UserMappingtoClients.html", controller:"UserMappingController"
    }).when("/changepassword",{templateUrl:"html/changepassword/ChangePassword.html", controller:"LoginController"
    });   
        
});