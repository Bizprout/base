baseApp.controller("PPMasterController", function($scope, $location, $http, $timeout, $q, $filter, $localStorage, $mdDialog) {

	$scope.cmpname=$localStorage.cmpname;

	if($localStorage.cmpid===undefined || $localStorage.cmpid===0)
	{
		$location.path("/home");
	}

	if($localStorage.userid===undefined)
	{
		$location.path("/");
	}
	
	//get the screens mapped for the userid and cmpid

	if($localStorage.usertype!="PPsuperadmin")
	{
		$http({
			method : "POST",
			url : "usermapping/getScreensMapped",
			data: {"cmpId":$localStorage.cmpid, "userid":$localStorage.userid},
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(datascreenids, status, headers, config){

			//*******options for client Names*********

			if(datascreenids!="")
			{
				var screenidsmapped = datascreenids.screenId.split(',');
			}
			else
			{
				$location.path("/home");
			}

			//get all the screens list and ids

			$http({
				method : "GET",
				url : "usermapping/getscreens",
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(datascreens, status, headers, config){

				var ppscreen=$filter('filter')(datascreens, {screenName: "PP Master"}, true)[0];


				if(screenidsmapped.indexOf(ppscreen.sid.toString()) === -1)
				{
					$location.path("/home");
				}

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		}).error(function(data, status, headers, config){
			// called asynchronously if an error occurs
			// or server returns response with an error status.
		});	
	}

	$scope.hidecostcat=true;

	// **********switch flag for success message**********
	$scope.switchBool = function (value) {
		$scope[value] = !$scope[value];
	};

	//******Autocomplete dropdown default options for Select master type********
	$scope.mastertypeselectOptions = {
			displayText: 'Select Master Type',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};

	//******Autocomplete dropdown default options for Select pp master name********
	$scope.ppmasternameselectOptions = {
			displayText: 'Select PP Master Name',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};

	//******Autocomplete dropdown default options for Select pp parent name********
	$scope.ppparentnameselectOptions = {
			displayText: 'Select PP Parent Name',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};

	//******Autocomplete dropdown default options for Select pp parent name********
	$scope.catselectOptions = {
			displayText: 'Select Category',
			emptyListText: 'Oops! The list is empty',
			emptySearchResultText: 'Sorry, couldn\'t find "$0"'
	};

	//*******DTO to store the form values for pp standard masters**********
	$scope.ppmasterDTO = {
			"cmpid" : $localStorage.cmpid,
			"mastertype" : "",
			"ppmastername":"",
			"ppparentname" : "",
			"category" : ""	
	};	

	$scope.editppmasterDTO = {
			"mastertype" : "",
			"ppmastername":"",
			"editppmastername":"",
			"ppparentname" : "",
			"category" : ""
	};

	//***********when Create tab is clicked**********************************
	$scope.oncreateclick=function(){

		$scope.hidecostcat=true;

		$scope.ppmasternamelabel="PP Master";

		$scope.ppmasterDTO.mastertype='';
		$scope.ppmasterDTO.category='';
		$scope.ppmasterDTO.ppmastername='';
		$scope.ppmasterDTO.ppparentname='';

		$scope.isLoadingmastertype=true;

		$scope.mastertypes = ["Ledger","Group","Cost Category","Cost Centre","Voucher Type"];

		$scope.isLoadingmastertype=false;

		$scope.mastertypechanged=function(){

			$scope.ppmasterDTO.category='';
			$scope.ppmasterDTO.ppmastername='';
			$scope.ppmasterDTO.ppparentname='';

			$scope.ppmasternamelabel=$scope.ppmasterDTO.mastertype;

			$scope.categories=[];

			if($scope.ppmasterDTO.mastertype==="Ledger")
			{
				$scope.hidecostcat=false;
				$scope.categories=["Assets","Liabilities","Expenses","Income"];
				$scope.ppparentnames=[];
			}
			else if($scope.ppmasterDTO.mastertype==="Group")
			{
				$scope.hidecostcat=false;
				$scope.categories=["Assets","Liabilities","Expenses","Income"];
			}
			else if($scope.ppmasterDTO.mastertype==="Cost Category")
			{
				$scope.hidecostcat=true;
				$scope.ppmasterDTO.category="Cost Category";
				$scope.ppparentnames=["Primary"];
				
				$scope.isLoadingppparentname=true;
				
				$http({
					method : "POST",
					url : "ppmaster/getppmastersnameall",
					data : {"mastertype":$scope.ppmasterDTO.mastertype, "category":$scope.ppmasterDTO.category, "cmpid":$scope.ppmasterDTO.cmpid},
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(data, status, headers, config){

					for(var key in data){
						$scope.ppparentnames.push(data[key]);
					}

					$scope.isLoadingppparentname=false;

				}).error(function(data, status, headers, config){
					// called asynchronously if an error occurs
					// or server returns response with an error status.
				});
			}
			else if($scope.ppmasterDTO.mastertype==="Cost Centre"){

				$scope.hidecostcat=false;
				$scope.categories=["Primary"];			
				
				$scope.isLoadingcat=true;

				$http({
					method : "POST",
					url : "ppmaster/getppmastersnameall",
					data : {"mastertype":"Cost Category", "category":"Cost Category", "cmpid":$scope.ppmasterDTO.cmpid},
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(dataccat, status, headers, config){

					for(var key1 in dataccat){
						$scope.categories.push(dataccat[key1]);
					}
					
					$scope.isLoadingcat=false;
					
					
				}).error(function(data, status, headers, config){
					// called asynchronously if an error occurs
					// or server returns response with an error status.
				});
			}
			else if($scope.ppmasterDTO.mastertype==="Voucher Type")
			{
				$scope.hidecostcat=true;
				$scope.ppmasterDTO.category="Voucher Type";
				$scope.ppparentnames=["Contra","Credit Note","Debit Note","Delivery Note","Job Work In Order","Job Work Out Order","Journal","Material In","Material Out","Memorandum","Payment","Physical Stock","Purchase","Purchase Order","Receipt","Receipt Note","Rejections In","Rejections Out","Reversing Journal","Sales","Sales Order","Stock Journal"];
			}


			$scope.oncategorychange=function(){

				$scope.ppmasterDTO.ppparentname='';

				$scope.isLoadingppparentname=true;

				if($scope.ppmasterDTO.mastertype==="Ledger")
				{
					$scope.ppparentnames=[];

					$http({
						method : "POST",
						url : "ppmaster/getppmastersnameall",
						data : {"mastertype":"Group", "category":$scope.ppmasterDTO.category, "cmpid":$scope.ppmasterDTO.cmpid},
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data, status, headers, config){

						$scope.ppparentnames.push($scope.ppmasterDTO.category);

						for(var key in data){
							$scope.ppparentnames.push(data[key]);
						}

						$scope.isLoadingppparentname=false;

					}).error(function(data, status, headers, config){
						// called asynchronously if an error occurs
						// or server returns response with an error status.
					});
				}
				else if($scope.ppmasterDTO.mastertype==="Group")
				{
					$scope.ppparentnames=[];

					$scope.ppmasterDTO.ppparentname=$scope.ppmasterDTO.category;

					$http({
						method : "POST",
						url : "ppmaster/getppmastersnameall",
						data : {"mastertype":"Group", "category":$scope.ppmasterDTO.category, "cmpid":$scope.ppmasterDTO.cmpid},
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data, status, headers, config){

						$scope.ppparentnames.push($scope.ppmasterDTO.category);

						for(var key in data){
							$scope.ppparentnames.push(data[key]);
						}

						$scope.isLoadingppparentname=false;

					}).error(function(data, status, headers, config){
						// called asynchronously if an error occurs
						// or server returns response with an error status.
					});
				}
				else if($scope.ppmasterDTO.mastertype==="Cost Centre")
				{
					$scope.ppparentnames=["Primary"];				

					$http({
						method : "POST",
						url : "ppmaster/getppmastersnamebycompany",
						data : {"mastertype":"Cost Centre", "cmpid":$scope.ppmasterDTO.cmpid},
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(dataccat, status, headers, config){

						for(var key1 in dataccat){
							$scope.ppparentnames.push(dataccat[key1]);
						}

						$scope.isLoadingppparentname=false;

					}).error(function(data, status, headers, config){
						// called asynchronously if an error occurs
						// or server returns response with an error status.
					});
				}
				else
				{
					$http({
						method : "POST",
						url : "ppmaster/getppmastersnameall",
						data : {"mastertype":$scope.ppmasterDTO.mastertype, "category":$scope.ppmasterDTO.category, "cmpid":$scope.ppmasterDTO.cmpid},
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data, status, headers, config){

						for(var key in data){
							$scope.ppparentnames.push(data[key]);
						}

						$scope.isLoadingppparentname=false;

					}).error(function(data, status, headers, config){
						// called asynchronously if an error occurs
						// or server returns response with an error status.
					});
				}
			};
		};

		//Create pp Standard Masters=======================================================================================================
		$scope.createppmasters=function(ppmasterDTO){

			//call pp master add service
			if($scope.ppmasterDTO.mastertype.length!=0 || $scope.ppmasterDTO.ppmastername.length!=0 || $scope.ppmasterDTO.ppparentname.length!=0)
			{	
				if($scope.ppmasterDTO.mastertype==='Cost Centre' && $scope.ppmasterDTO.category.length===0)
				{
					$scope.alerts = { type: 'danger' ,msg: 'Category is Required!'};
					$scope.showSuccessAlert = true;
				}
				else if($scope.ppmasterDTO.mastertype==='')
				{
					$scope.alerts = { type: 'danger' ,msg: 'Master Type is Required'};
					$scope.showSuccessAlert = true;
				}
				else if($scope.ppmasterDTO.ppmastername==='')
				{
					$scope.alerts = { type: 'danger' ,msg: $scope.ppmasternamelabel+' Name is Required'};
					$scope.showSuccessAlert = true;
				}
				else if(($scope.ppmasterDTO.mastertype==='Ledger' || $scope.ppmasterDTO.mastertype==='Group' || $scope.ppmasterDTO.mastertype==='Cost Category' || $scope.ppmasterDTO.mastertype==='Cost Centre') && $scope.ppmasterDTO.category==='')
				{
					$scope.alerts = { type: 'danger' ,msg: 'Category is Required'};
					$scope.showSuccessAlert = true;
				}
				else if($scope.ppmasterDTO.ppparentname==='')
				{
					$scope.alerts = { type: 'danger' ,msg: 'PP Parent Name is Required'};
					$scope.showSuccessAlert = true;
				}
				else
				{
					$scope.isLoadingresponse=true;
					
					$http({
						method : "POST",
						url : "ppmaster/add",
						data : ppmasterDTO,
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data, status, headers, config){

						if(data[0]==="success")
						{
							$scope.alerts = { type: 'success' ,msg: 'PP Masters Created'};
							$scope.showSuccessAlert = true;
							$scope.showerror=false;

							$scope.ppmasterDTO.mastertype='';
							$scope.ppmasterDTO.ppmastername='';
							$scope.ppmasterDTO.ppparentname='';
							$scope.ppmasterDTO.category='';
							
							$scope.isLoadingresponse=false;

							$scope.hidecostcat=true;
						}
						else if(data[0]==="failure")
						{
							$scope.alerts = { type: 'danger' ,msg: 'PP Masters not Created'};
							$scope.showSuccessAlert = true;
							$scope.showerror=false;
							
							$scope.isLoadingresponse=false;
						}
						else
						{
							if(data.length>0)
							{
								$scope.alerts = { type: 'danger'};
								$scope.errdata=data[0];
								$scope.showerror=true;
								$scope.showSuccessAlert = false;
								
								$scope.isLoadingresponse=false;
							}
							else
							{
								$scope.alerts = { type: 'danger' ,msg: 'PP Masters not Created'};
								$scope.showSuccessAlert = true;
								$scope.showerror=false;
								
								$scope.isLoadingresponse=false;
							}
						}
					}).error(function(data, status, headers, config){

						$scope.alerts = { type: 'danger' ,msg: 'PP Masters not Created'};
						$scope.showSuccessAlert = true;
						
						$scope.isLoadingresponse=false;
					});	
				}		
			}	 	
			else
			{
				$scope.alerts = { type: 'danger' ,msg: 'All Mandatory Fields should be Filled up.'};
				$scope.showSuccessAlert = true;
			}
		};

		//*********Clear all the fields when clear button pressed*******
		$scope.clear=function(){

			$scope.ppmasterDTO.mastertype='';
			$scope.ppmasterDTO.ppmastername='';
			$scope.ppmasterDTO.ppparentname='';
			$scope.ppmasterDTO.category='';
		}
	};	


	//***********when Edit tab is clicked**********************************
	$scope.oneditclick=function(){
		//to get pp masters list based on master type selection

		$scope.editppmasterDTO.mastertype='';
		$scope.editppmasterDTO.ppmastername='';
		$scope.editppmasterDTO.editppmastername='';
		$scope.editppmasterDTO.ppparentname='';
		$scope.editppmasterDTO.category='';

		$scope.editppmasternamelabel="PP Master";

		$scope.getppmasternamelist=function(){

			$scope.editppmasterDTO.ppmastername='';
			$scope.editppmasterDTO.editppmastername = '';
			$scope.editppmasterDTO.ppparentname = '';
			$scope.editppmasterDTO.category = '';

			$scope.editppmasternamelabel=$scope.editppmasterDTO.mastertype;

			$scope.isLoadingeditselectppmastername=true;

			$http({
				method : "POST",
				url : "ppmaster/getppmastersnamebycompany",
				data : {"mastertype":$scope.editppmasterDTO.mastertype, "cmpid":$localStorage.cmpid},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config){

				//*******options for pp Masters name based on master type selection**********************************

				$scope.eppmasternames=data;
				$scope.isLoadingeditselectppmastername = false;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		};

		$scope.ppmasternamechange=function(){

			$scope.isLoadingeditppmastername = true;

			$scope.editppmasterDTO.editppmastername=$scope.editppmasterDTO.ppmastername;

			$scope.isLoadingeditppmastername = false;

			$scope.ppparentnames=[];

			$scope.isLoadingselecteditcategory=true;
			$scope.isLoadingeditppparentname=true;

			$http({
				method : "POST",
				url : "ppmaster/getppparentname",
				data : {"mastertype":$scope.editppmasterDTO.mastertype, "ppmastername":$scope.editppmasterDTO.ppmastername, "cmpid":$localStorage.cmpid},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(datapop, status, headers, config){

				//*******options for pp Masters name based on master type selection**********************************

				if($scope.editppmasterDTO.mastertype==="Ledger" || $scope.editppmasterDTO.mastertype==="Group")
				{
					$scope.hidecostcat=false;
					$scope.categories=["Assets","Liabilities","Expenses","Income"];
				}
				else if($scope.editppmasterDTO.mastertype==="Cost Category")
				{
					$scope.ppparentnames=[];
					
					$scope.hidecostcat=true;
					$scope.editppmasterDTO.category="Cost Category";
					$scope.ppparentnames.push("Primary");
					
				}
				else if($scope.editppmasterDTO.mastertype==="Cost Centre"){
					$scope.hidecostcat=false;
					$scope.categories=["Primary"];

					$http({
						method : "POST",
						url : "ppmaster/getppmastersnamebycompany",
						data : {"mastertype":"Cost Category", "cmpid":$localStorage.cmpid},
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(dataccat, status, headers, config){

						for(var key1 in dataccat){
							$scope.categories.push(dataccat[key1]);
						}

					}).error(function(data, status, headers, config){
						// called asynchronously if an error occurs
						// or server returns response with an error status.
					});
				}
				else if($scope.editppmasterDTO.mastertype==="Voucher Type")
				{
					$scope.hidecostcat=true;
					$scope.editppmasterDTO.category="Voucher Type";
					$scope.ppparentnames.push("Contra","Credit Note","Debit Note","Delivery Note","Job Work In Order","Job Work Out Order","Journal","Material In","Material Out","Memorandum","Payment","Physical Stock","Purchase","Purchase Order","Receipt","Receipt Note","Rejections In","Rejections Out","Reversing Journal","Sales","Sales Order","Stock Journal");
				}


				if($scope.editppmasterDTO.mastertype==="Group" || $scope.editppmasterDTO.mastertype==="Ledger")
				{
					$scope.editppmasterDTO.category=datapop[0].category;
				}


				if($scope.editppmasterDTO.mastertype==="Ledger")
				{
					$http({
						method : "POST",
						url : "ppmaster/getppmastersname",
						data : {"mastertype":"Group", "category":$scope.editppmasterDTO.category, "cmpid":$localStorage.cmpid, "ppmastername":$scope.editppmasterDTO.ppmastername},
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data, status, headers, config){

						$scope.ppparentnames.push($scope.editppmasterDTO.category);

						for(var key in data){
							$scope.ppparentnames.push(data[key]);
						}	

						$scope.isLoadingselecteditcategory=false;
						$scope.isLoadingeditppparentname=false;


					}).error(function(data, status, headers, config){
						// called asynchronously if an error occurs
						// or server returns response with an error status.
					});
				}
				else if($scope.editppmasterDTO.mastertype==="Group")
				{
					$http({
						method : "POST",
						url : "ppmaster/getppmastersname",
						data : {"mastertype":"Group", "category":$scope.editppmasterDTO.category, "cmpid":$localStorage.cmpid, "ppmastername":$scope.editppmasterDTO.ppmastername},
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data, status, headers, config){

						$scope.ppparentnames.push($scope.editppmasterDTO.category);

						for(var key in data){
							$scope.ppparentnames.push(data[key]);
						}	

						$scope.isLoadingselecteditcategory=false;
						$scope.isLoadingeditppparentname=false;


					}).error(function(data, status, headers, config){
						// called asynchronously if an error occurs
						// or server returns response with an error status.
					});
				}
				else if($scope.editppmasterDTO.mastertype==="Cost Centre")
				{
					$scope.ppparentnames.push("Primary");				

					$http({
						method : "POST",
						url : "ppmaster/getppmastersnamebycostcategory",
						data : {"mastertype":"Cost Centre", "cmpid":$localStorage.cmpid, "ppmastername":$scope.editppmasterDTO.ppmastername},
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(dataccat, status, headers, config){

						for(var key1 in dataccat){
							$scope.ppparentnames.push(dataccat[key1]);
						}

						$scope.isLoadingselecteditcategory=false;
						$scope.isLoadingeditppparentname=false;

					}).error(function(data, status, headers, config){
						// called asynchronously if an error occurs
						// or server returns response with an error status.
					});
				}
				else
				{
					$http({
						method : "POST",
						url : "ppmaster/getppmastersname",
						data : {"mastertype":$scope.editppmasterDTO.mastertype, "category":$scope.editppmasterDTO.category, "cmpid":$localStorage.cmpid, "ppmastername":$scope.editppmasterDTO.ppmastername},
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data, status, headers, config){

						//$scope.ppparentnames.push($scope.editppmasterDTO.category);

						for(var key in data){
							$scope.ppparentnames.push(data[key]);
						}
						$scope.isLoadingselecteditcategory=false;
						$scope.isLoadingeditppparentname=false;

					}).error(function(data, status, headers, config){
						// called asynchronously if an error occurs
						// or server returns response with an error status.
					});
				}

				$scope.editppmasterDTO.ppparentname=datapop[0].ppparentname;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		};


		$scope.oneditcategorychange=function(){

			$scope.ppparentnames=[];

			$scope.editppmasterDTO.ppparentname='';

			if($scope.editppmasterDTO.mastertype==="Ledger" || $scope.editppmasterDTO.mastertype==="Group")
			{
				$scope.tempmastertype="Group";

				$scope.ppparentnames.push($scope.editppmasterDTO.category);
			}
			else if($scope.editppmasterDTO.mastertype==="Cost Category")
			{
				$scope.tempmastertype="Cost Category";
			}
			else if($scope.editppmasterDTO.mastertype==="Cost Centre")
			{
				$scope.tempmastertype="Cost Centre";
			}
			else if($scope.editppmasterDTO.mastertype==="Voucher Types")
			{
				$scope.tempmastertype="Voucher Types";
			}

			$http({
				method : "POST",
				url : "ppmaster/getppmastersname",
				data : {"mastertype":$scope.tempmastertype, "category":$scope.editppmasterDTO.category, "cmpid":$localStorage.cmpid, "ppmastername":$scope.editppmasterDTO.ppmastername},
				headers : {
					'Content-Type' : 'application/json'
				}
			}).success(function(data, status, headers, config){

				for(var key in data){
					$scope.ppparentnames.push(data[key]);
				}

				//$scope.editppmasterDTO.ppparentname=$scope.ppparentnames;

				$scope.isLoadingselecteditcategory=false;
				$scope.isLoadingeditppparentname=false;

			}).error(function(data, status, headers, config){
				// called asynchronously if an error occurs
				// or server returns response with an error status.
			});
		};

		$scope.editppmasters=function(editppmasterDTO){

			//$scope.edituserDTO.usertype=$scope.usertype;

			if($scope.editppmasterDTO.mastertype.length!=0 || $scope.editppmasterDTO.ppmastername.length!=0 || $scope.editppmasterDTO.editppmastername.length!=0 || $scope.editppmasterDTO.ppparentname.length!=0)
			{				
				if($scope.editppmasterDTO.mastertype==='')
				{
					$scope.alerts = { type: 'danger' ,msg: 'Master Type is Required!'};
					$scope.showSuccessAlert = true;
				}
				else if($scope.editppmasterDTO.ppmastername==='')
				{
					$scope.alerts = { type: 'danger' ,msg: 'PP Master Name is Required!'};
					$scope.showSuccessAlert = true;
				}
				else if($scope.editppmasterDTO.editppmastername==='')
				{
					$scope.alerts = { type: 'danger' ,msg: 'Edit PP Master Name is Required!'};
					$scope.showSuccessAlert = true;
				}
				else if(($scope.editppmasterDTO.mastertype==='Ledger' || $scope.editppmasterDTO.mastertype==='Group' || $scope.editppmasterDTO.mastertype==='Cost Category' || $scope.editppmasterDTO.mastertype==='Cost Centre') && $scope.editppmasterDTO.category==="")
				{
					$scope.alerts = { type: 'danger' ,msg: 'Category is Required'};
					$scope.showSuccessAlert = true;
				}
				else if($scope.editppmasterDTO.ppparentname==='')
				{
					$scope.alerts = { type: 'danger' ,msg: 'PP Parent Name is Required!'};
					$scope.showSuccessAlert = true;
				}
				else
				{
					$scope.isLoadingresponse=true;
					
					$http({
						method : "POST",
						url : "ppmaster/edit",
						data : editppmasterDTO,
						headers : {
							'Content-Type' : 'application/json'
						}
					}).success(function(data, status, headers, config){

						if(data[0]==="success")
						{
							$scope.alerts = { type: 'success' ,msg: 'PP Masters Updated!'};
							$scope.showSuccessAlert = true;
							$scope.showerror=false;
							
							$scope.isLoadingresponse=false;

							$scope.editppmasterDTO.mastertype='';
							$scope.editppmasterDTO.ppmastername='';
							$scope.editppmasterDTO.editppmastername = '';
							$scope.editppmasterDTO.ppparentname = '';
							$scope.editppmasterDTO.category = '';
						}
						else if(data[0]==="failure")
						{
							$scope.alerts = { type: 'danger' ,msg: 'PP Masters not Updated!'};
							$scope.showSuccessAlert = true;
							$scope.showerror=false;
							
							$scope.isLoadingresponse=false;
						}
						else
						{
							if(data.length>0)
							{
								$scope.alerts = { type: 'danger'};
								$scope.errdata=data[0];
								$scope.showerror=true;
								$scope.showSuccessAlert = false;
								
								$scope.isLoadingresponse=false;
							}
							else
							{
								$scope.alerts = { type: 'danger' ,msg: 'PP Masters not Updated!'};
								$scope.showSuccessAlert = true;
								$scope.showerror=false;
								
								$scope.isLoadingresponse=false;
							}							
						}

					}).error(function(data, status, headers, config){

						$scope.alerts = { type: 'danger' ,msg: 'PP Masters not Updated!'};
						$scope.showSuccessAlert = true;
						
						$scope.isLoadingresponse=false;
					});
				}
			}
			else
			{
				$scope.alerts = { type: 'danger' ,msg: 'All Fields should be Filled up.'};
				$scope.showSuccessAlert = true;
			}
		};

		$scope.eclear=function(){

			$scope.editppmasterDTO.mastertype='';
			$scope.editppmasterDTO.ppmastername='';
			$scope.editppmasterDTO.editppmastername='';
			$scope.editppmasterDTO.ppparentname='';
			$scope.editppmasterDTO.category='';
		}
	};

	//***********when Import/Export tab is clicked**********************************
	$scope.onimpexpclick=function(){
		
		$http({
			method : "POST",
			url : "ppmaster/setsessioncmpid",
			data : {"cmpId":$localStorage.cmpid},
			headers : {
				'Content-Type' : 'application/json'
			}
		}).success(function(data, status, headers, config){

		}).error(function(data, status, headers, config){

		});

		$scope.exportppformat=function(){

			var confirm = $mdDialog.confirm()
			.title('Would you like to Export Excel Format?')
			.textContent("Note: Please Save the Excel file as Excel Workbook and Upload.")
			.ok('OK')
			.cancel('Cancel');

			$mdDialog.show(confirm).then(function() {
				var blob = new Blob([document.getElementById('ppexcelexport').innerHTML], {
					type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
				});
				saveAs(blob, "PPMaster_upload_format.xls");
			});
		};

		$scope.ppmasterimport=function(file){

			$scope.isLoadingresponse=true;

			var file = $scope.myFile;
			var uploadUrl = "ppmaster/ppmasteruploadfile";

			if(file!=undefined)
			{
				if(file.type==="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" || file.type==="application/vnd.ms-excel")
				{
					var fd = new FormData();
					fd.append('file', file);
					$http.post(uploadUrl, fd, {
						transformRequest: angular.identity,
						headers: {'Content-Type': undefined}
					})
					.success(function(response){
						
						if(response[0]==="success")
						{
							$scope.alerts = { type: 'success' ,msg: "File - "+ file.name +" Updated Successfully!"};
							$scope.showSuccessAlert = true;
							$scope.showerror=false;
						}
						else if(response[0]==="failure")
						{
							$scope.alerts = { type: 'danger' ,msg: "File- "+ file.name +" not Updated!"};
							$scope.showSuccessAlert = true;
							$scope.showerror=false;
						}
						else
						{
							if(response.length>0)
							{
								$scope.alerts = { type: 'danger'};
								$scope.errdata=response;
								$scope.showerror=true;
								$scope.showSuccessAlert = false;
							}
							else
							{
								$scope.alerts = { type: 'danger' ,msg: "File- "+ file.name +" not Updated!"};
								$scope.showSuccessAlert = true;
								$scope.showerror=false;
							}
						}
						$scope.isLoadingresponse=false;

					})
					.error(function(response){

					});
				}
				else
				{
					$scope.alerts = { type: 'danger' ,msg: "Please Upload Excel Files only!"};
					$scope.showSuccessAlert = true;
				}

			}
			else
			{
				$scope.alerts = { type: 'danger' ,msg: "No file Selected!"};
				$scope.showSuccessAlert = true;
			}

		};
	};

	//***********when Report tab is clicked**********************************
	$scope.currentPage=1;
	$scope.itemsPerPage=5;

	$scope.onreportclick=function(){

		$scope.search='';

		//View PP Master - Report================================================================================================

		$scope.ppmasterdata = []; //declare an empty array	
		
		$scope.repomastertype='';
		
		$scope.repomastertypechanged=function(){
			
			if($scope.repomastertype!="")
			{
				$scope.isLoading = true;
				$http({
					method : "POST",
					url : "ppmaster/getppmasterdata",
					data : {"cmpid":$localStorage.cmpid, "mastertype":$scope.repomastertype},
					headers : {
						'Content-Type' : 'application/json'
					}
				}).success(function(response, status, headers, config){

					$scope.ppmasterdata = response;
					$scope.isLoading = false;

				}).error(function(data, status, headers, config){

				});
			}
		};



		$scope.sort = function(keyname){
			$scope.sortKey = keyname;   //set the sortKey to the param passed
			$scope.reverse = !$scope.reverse; //if true make it false and vice versa
		};


		$scope.exportData = function () {
			
			if($scope.ppmasterdata.length>0)
			{
				var confirm = $mdDialog.confirm()
				.title('Would you like to Export Table data to Excel?')
				.ok('OK')
				.cancel('Cancel');

				$mdDialog.show(confirm).then(function() {
					var blob = new Blob([document.getElementById('exportableall').innerHTML], {
						type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
					});
					saveAs(blob, "PPMaster_Report.xls");
				});
			}
			else
			{
				$scope.alerts = { type: 'danger' ,msg: "No Data to Export!"};
				$scope.showSuccessAlert = true;
			}

		};
	};



});