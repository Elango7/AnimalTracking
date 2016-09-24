<!DOCTYPE html>
<html>
<head>
	<title></title>
	
	<header>
	 <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
 	
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	  <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
	        <link rel="stylesheet" href="WebContent/css/bootstrap.min.css">
	        		<link rel="stylesheet" href="WebContent/css/form-elements.css">
	        	 <link rel="stylesheet" href="WebContent/css/font-awesome.min.css">
	        	 <script src="WebContent/js/notify.js"></script>	
 	        	
     <link href="WebContent/simple-sidebar.css" rel="stylesheet" />
 
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    
   
    
           
            
            <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.2.61/jspdf.min.js"></script>
            
    
    <script src="WebContent/js/jquery.backstretch.min.js"></script>
        <script src="WebContent/js/scripts.js"></script>
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
  <!-- Bootstrap -->
      <link href="WebContent/vendors/bootstrap.min.css" rel="stylesheet"> 
    <!-- Font Awesome -->
   <link href="WebContent/vendors/font-awesome.min.css" rel="stylesheet"> 
 
   <link href="WebContent/vendors/custom.css" rel="stylesheet">
 
 
 
 
 
 
 
 
	</header>
	
</head>
 

  
<body>
<div id="header" class="navbar navbar-default navbar-fixed-top">
    <div class="navbar-header">
        <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target=".navbar-collapse">
            <i class="icon-reorder"></i>
        </button>
        <a class="navbar-brand" href="#">
            Brand
        </a>
    </div>
    <nav role="navigation" class="navbar navbar-inverse navbar-fixed-top">
    <!-- container-fluid -->  
    <div class="container-fluid">
      <!-- navbar-header -->
      <div class="navbar-header">
            <button data-target="#navbar-top" data-toggle="collapse" class="navbar-toggle" type="button">
                <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
            </button>
            <a href="#" id="mainPage" class="navbar-brand">GPS Tracking</a>
            <ul class="nav navbar-nav nav-pills">
                <!-- <li class="active"><a href="#"><span class="glyphicon glyphicon-envelope"></span> <span class="badge">2</span></a></li> -->
                
                 
                 
                <li id="Home" class="active" ><a href="#"   id="homeClick" data-toggle="tooltip" title="DashBoard"><span class="fa fa-home""></span><span id="jsonCount" class="badge"></span></a></li>
            </ul>
            
      </div>
      <!-- /navbar-header -->
      <!-- nav-collapse -->
      	<ul class="nav navbar-nav navbar-right">
       
 		<li class="dropdown  "><a class="dropdown-toggle" data-toggle="dropdown" id="userlogName" href="#" aria-expanded="true">Avik</a>
				<ul class="dropdown-menu">
					<li><input type="submit" class="btn btn-link" value="Profile"></li>
		
		<li><input type="submit" id="logout" class="btn btn-link" value="Logout"></li>
				</ul></li>
		</ul>
      <div class="navbar-collapse collapse" id="navbar-top">
          ...
      </div>
      <!-- /nav-collapse -->
    </div>
    <!-- /container-fluid --> 
</nav>
</div>


<div>

        <!-- Sidebar -->
     
        <!-- /#sidebar-wrapper -->

        <!-- Page Content -->     
       
        
         
        <!-- Dash board content.. -->
          <div  id="dashBoardContainer" class="divContainer">        
              <br>                     
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2 style="font-size: 18px;font-weight: 400;">Last Location Updates</h2>
                    
                    <div class="clearfix"></div>
                  </div>

                  <div class="x_content" style="height:800px">
                   <label style="margin-left:400px">Choose Time</label>
                   <select   id="selectTime" class="selectpicker" style="width:100px">
                      <option value="60">1 Hr</option>
                      <option value="120">2 Hr</option>
                      <option value="180">3 Hr</option>
                      <option value="240">4 Hr</option>
                      <option value="300">5 Hr</option>
                      <option value="360">6 Hr</option>
                      <option value="420">7 Hr</option>
                      <option value="480" >8 Hr</option>
                  </select>
 

                   <br>
                   <br>
                   

                    <div class="table-responsive">
                      <table id="idleInfo" class="table table-striped jambo_table bulk_action">
                        <thead>
                          <tr class="headings">
                          
                            <th class="column-title">Animal name </th>
                            <th class="column-title">Last Update Time</th>
                            <th class="column-title">Last Location</th>
                            <th class="column-title">Last Lan,Lat</th>
                            <th class="column-title no-link last"><span class="nobr">Action</span>
                            </th>
                            
                          </tr>
                        </thead>

                        
                      </table>
                    </div>
                  </div>
                </div>
              </div>
          </div>
        
        
     
  
  
     
       
        

    </div>




<script src="WebContent/vendors/custom.min.js"></script>

<script type="text/javascript">
  $("#dashBoardContainer").show();

    getIdleDeviceInfo(120); 


 $("#mainPage").click(function(e) {
        //  console.log( "inside!" );
    
window.location.replace("index.php");          // alert(1);
      });
  $("#homeClick").click(function(e) {
        //  console.log( "inside!" );
    
window.location.replace("index.php");          // alert(1);
      });



       $("#selectTime").on("change",function(e) {
            //$("#dashBoardContainer").hide();
            console.log('Inside onchange..'+$("#selectTime option:selected").val());
             getIdleDeviceInfo($("#selectTime option:selected").val())

        }); 


    function popluateIdleInfo(data){
                         
          $("#idleInfo").find("tr:gt(0)").remove();
          for(var i=0; i<data.length; i++){
 
            $("#idleInfo").append('<tbody><tr   class="even pointer">'+                            
                                    '<td style="color:black" class=" ">'+data[i].animal_name+'</td> '+
                                     '<td style="color:black" class=" ">'+data[i].update_time+'</td>'+
                                    '<td style="color:black" class=" ">'+getAddress(data[i].lat+','+data[i].lan)+'</td> '+
                                    '<td style="color:black" class=" ">'+data[i].lan+':'+data[i].lat+'</td> '+                           
                                    ' <td style="color:black" class=" last"><a href="#">View</a> '+
                                    ' </td> '+
                                    '</tr>  </tbody>'); 
          }
          
        
      }
      
      function getAddress(lanlat){
          var address=''; 
                $.ajax({
          type: "GET",
          async: false, 
          cache: true,
          url: "http://maps.googleapis.com/maps/api/geocode/json?latlng="+lanlat+"&sensor=true",
          dataType: "json",
          jsonpCallback:'json_response',
          success: function(data) {
          console.log("Success");           
          address=data.results[0].formatted_address;
          var add= data.results[0].formatted_address ;
                              var  value=add.split(",");        
                              var count=value.length;
                              var country=value[count-1];
                              var state=value[count-2];
                              var city=value[count-3];
                              var finalCity=value[count-3];     
          },
          error: function() {
            console.log('Error');
          }
          
        });
        console.log('Address'+address);
        return address; 
        
        
      }
          
          
            
           function getIdleDeviceInfo(idleMins) {
        console.log('Inside idle device info..');
        
        $.ajax({
          //type : "POST",
          contentType : "application/json",
          url : "http://animaltrack.in/locationtracking/tracking/idleDevice/"+idleMins+"/",
          //data : JSON.stringify(loginfo),
          dataType : 'json',
          timeout : 100000,
          success : function(data) {
            console.log("SUCCESS: ", data );
            popluateIdleInfo(data);
            
          },
          error : function(e) {
            console.log("ERROR: ", e);
          },
          done : function(e) {
            console.log("DONE");
          }
        });
      }
         
       
   
   
</script>



</body>