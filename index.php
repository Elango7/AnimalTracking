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
 	        	
     <link href="WebContent/simple-sidebar.css" rel="stylesheet">
 
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"  ></script>
    
      <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.2.61/jspdf.min.js"  ></script>

    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDYOURZCobEnu5vpAfN5Z5bFFDrrZ2SBIw">
    </script>
    
            <script src="WebContent/js/jquery.base64.js"></script>
            <script src="WebContent/js/tableexport.js"></script>
            <script src="WebContent/js/mapFile.js"></script>
            
            <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.2.61/jspdf.min.js"></script>
            
    
    <script src="WebContent/js/jquery.backstretch.min.js"></script>
        <script src="WebContent/js/scripts.js"></script>
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
 
 
 
 
 
 
 
 
 
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
                <li class="active"><a href="#" id="showSideBar"><span class="glyphicon glyphicon-list" ></span> <span id="AnimalCount" class="badge"></span></a></li>
                <li id="showDetailsLi" class="active" ><a href="#" data-toggle="modal" data-target="#myModal" id="samClick"><span  class="glyphicon glyphicon-flag"></span><span id="jsonCount" class="badge"></span></a></li>
                <li id="DashBoard" class="active" ><a href="#"   id="dashBoardClick" data-toggle="tooltip" title="DashBoard"><span class="fa fa-tachometer"></span><span id="idleCount" class="badge"></span></a></li>
            </ul>
            
      </div>
      <!-- /navbar-header -->
      <!-- nav-collapse -->
      	<ul class="nav navbar-nav navbar-right">
        <li id="livTrkStop" ><a href="#"  id="StopLiveTrack" >Stop Track </a></li>
			<li id="livTrk" ><a href="#"  id="StartLiveTrack" >Live Track </a></li>
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


<div id="wrapper">

        <!-- Sidebar -->
        <div id="sidebar-wrapper">
            <ul class="sidebar-nav" id="searchListUl">
                <li class="sidebar-brand">
                    <a href="#">
                        Start Bootstrap
                    </a>
                </li>
                
            </ul>
        </div>
        <!-- /#sidebar-wrapper -->

        <!-- Page Content -->     
        <div id="page-content-wrapper">
        
            <div class="container-fluid">
                
                   
            </div>
        </div>
        
        <div class="col-md-12" id="googleMap" style="width:110%;height:97%;"></div>
        
        <!-- /#page-content-wrapper -->
        
        
         <!-- Modal -->
  <!-- <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      Modal content
      <div class="modal-content">
          <div class="modal-header">
       <a class="close" data-dismiss="modal">&times;</a>
       <h3>Title of the form</h3>
    </div>
    <div class="modal-body">
       <p>Body of the form</p>
    </div>
    <div class="modal-footer">
       <a href="#" class="btn" data-dismiss="modal">Close</a>
       <a href="#" class="btn btn-primary">Save Changes</a>
    </div>
      </div>
      
    </div>
  </div> -->
  
     <!-- MODAL -->
     
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="modal-register-label" aria-hidden="true">
        	<div class="modal-dialog">
        		<div class="modal-content">
        			
        			<div class="modal-header">
        				<button type="button" class="close" data-dismiss="modal">
        					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
        				</button>
        				<h3 class="modal-title" id="modal-register-label">Tracking Details	</h3>
         			</div>
        			
        			<div class="modal-body">
        				
 	                    	<div class="form-group" id="exportPdf">
	                    		<table  class="table table-condensed">
						    <thead id="tableData">
						      <tr>
						        <th>Start</th>
						        <th>End</th>
						        <th>Km/Ph</th>
						      </tr>
						    </thead>
						    <tbody>
						      
						    </tbody>
						  </table>
	                    		
	                    		
	                    		 </div>
	                         
 	                       
	                           <button id="generatePdf"   class="btn"><i class="fa fa-download" aria-hidden="true"></i>  &nbsp
	                        Export to PDF</button>
 	                    
        			</div>
        			
        		</div>
        	</div>
        </div>
  


   <!--Delete MODAL -->
     
        <div class="modal fade" id="DeleteModal" tabindex="-1" role="dialog" aria-labelledby="modal-register-label" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                            <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                        </button>
                        <h3 class="modal-title" id="modal-register-label">Tracking Details  </h3>
                    </div>
                    
                    <div class="modal-body">
                        
                           <h3 style="color:white;">Want to Delete <span style="color: #66ff66" id="animalName"></span>?</h3><hr>
                     <center  >
             <input type="button" data-dismiss="modal" class="btn btn-danger" id="deleteDevice" value="Delete"></input>
                    <input type="button" data-dismiss="modal" class="btn" value="Cancel"></input>
</center>
                           </div>
                 
              
                
                           
                               
                        
                    </div>
                    
                </div>
            </div>
        </div>
  
  
     
        
	     <!--    <div class="modal-body" id="myModal">
	    <div class="row-fluid">
	        <div class="controls span6">
	            <label class="control-label" for="Title">Title and summary</label>
	            <input type="text" 
	                   class="input-xlarge" id="Title" name="Title" 
	                   placeholder="Title of the article" />
	            <textarea class="input-xlarge" id="Summary"
	                      name="Summary" rows="5" cols="0" 
	                      placeholder="Summary"></textarea>
	        </div> 
	        <div class="controls span6">
	            <label class="control-label" for="Title">Date of publication</label>
	            <input type="month" class="input-xlarge" id="PubMonth" name="PubMonth" />
	            <label class="control-label" for="Magazine">Publication</label>
	            <input type="text" class="input-xlarge" id="Magazine" name="Magazine" 
	                   placeholder="Magazine" />
	            <input type="text" class="input-xlarge" id="Url" name="Url" 
	                   placeholder="Url" />
	        </div> 
	    </div>
	</div>
	<div id="validationSummary" class="validation-summary">
	    <ul></ul>
	</div> -->
	        
        

    </div>








<!-- 
<div id="wrapper">
  <div id="sidebar-wrapper" class="col-md-2">
            <div id="sidebar">
                <ul class="nav list-group">
                    <li>
                        <a class="list-group-item" href="#"><i class="icon-home icon-1x"></i>Sidebar Item 1</a>
                    </li>
                    <li>
                        <a class="list-group-item" href="#"><i class="icon-home icon-1x"></i>Sidebar Item 2</a>
                    </li>
                    <li>
                        <a class="list-group-item" href="#"><i class="icon-home icon-1x"></i>Sidebar Item 9</a>
                    </li>
                    <li>
                        <a class="list-group-item" href="#"><i class="icon-home icon-1x"></i>Sidebar Item 10</a>
                    </li>
                    <li>
                        <a class="list-group-item" href="#"><i class="icon-home icon-1x"></i>Sidebar Item 11</a>
                    </li>
                </ul>
            </div>
        </div>
        <div id="main-wrapper" class="col-md-10 pull-right">
            <div id="main">
              <br> 
             <div id="googleMap" style="width:100%;height:97%;"></div>

            </div>
          
            <div class="col-md-12 footer">
              <ul class="nav navbar-nav"><li><a href="">Link</a></li><li><a href="">Link</a></li><li><a href="">Link</a></li></ul>
            </div>
          
        </div>
</div>  -->
</body>
<style type="text/css">
td {
    color: #e6e6e6;
}
table {
    color:   #99ffeb;
}
/* Model	 */

/*   #container .modal.fade {
         left: -25%;
          -webkit-transition: opacity 0.3s linear, left 0.3s ease-out;
             -moz-transition: opacity 0.3s linear, left 0.3s ease-out;
               -o-transition: opacity 0.3s linear, left 0.3s ease-out;
                  transition: opacity 0.3s linear, left 0.3s ease-out;
    }
    #container .modal.fade.in {
        left: 50%;
    }
    #container .modal-body {
        max-height: 50px;
    }
    #article-editor {
    width: 600px;
    margin-left: -300px;
    margin-right: -300px;
}
 */

 
/***** Modal *****/



.delDevice:hover { 
 color: red;
}



.modal-content {
	background: #3a3a3a;
	-moz-border-radius: 4px; -webkit-border-radius: 4px; border-radius: 4px;
	border: 0;
	text-align: left;
}

.modal-header {
	padding: 25px 25px 15px 25px;
	background: #333;
	border: 0;
	-moz-border-radius: 4px 4px 0 0; -webkit-border-radius: 4px 4px 0 0; border-radius: 4px 4px 0 0;
	color: #888;
}

.modal-header .close {
	font-size: 36px;
	color: #eee;
	font-weight: 300;
	text-shadow: none;
	opacity: 1;
}

.modal-title {
	margin-bottom: 10px;
	line-height: 30px;
	color: #eee;
}

.modal-body {
	padding: 25px 25px 30px 25px;
	background: #3a3a3a;
	text-align: left;
  	-moz-border-radius: 0 0 4px 4px; -webkit-border-radius: 0 0 4px 4px; border-radius: 0 0 4px 4px;
}

.modal-body img {
	margin-bottom: 15px;
}

.modal-body form textarea {
	height: 100px;
}

.modal-body form .input-error {
	border-color: #fd625e;
}
.modal-dialog{
    overflow-y: initial !important
}
/**/



/* white background and box outline */
.gm-style > div:first-child > div + div > div:last-child > div > div:first-child > div
{
    /* we have to use !important because we are overwritng inline styles */
    background-color: transparent !important;
    box-shadow: none !important;
    width: auto !important;
    height: auto !important;
}

/* arrow colour */
.gm-style > div:first-child > div + div > div:last-child > div > div:first-child > div > div > div
{
    background-color: #003366 !important; 
}

/* /* close button */
.gm-style > div:first-child > div + div > div:last-child > div > div:last-child
{
    margin-right: 28px;
    margin-top: 21px;
 } */

/* image icon inside close button */
.gm-style > div:first-child > div + div > div:last-child > div > div:last-child > img
{
    display: block;
}

/* positioning of infowindow */
.gm-style-iw
{
    top: 22px !important;
    left: 22px !important;
 }
body {
    padding-top: 50px;
    overflow: hidden;
}
#wrapper {
    min-height: 100%;
    height: 100%;
    width: 100%;
    position: absolute;
    top: 0px;
    left: 0;
    display: inline-block;
}
 
#main-wrapper {
    height: 100%;
    overflow-y: auto;
    padding: 50px 0 0px 0;
}
 #cityName{
    color: #99ffff;
}
#main {
    position: relative;
    height: 100%;
    overflow-y: auto;
    padding: 0 15px;
}
#sidebar-wrapper {
    height: 100%;
    padding: 50px 0 0px 0;
    position: fixed;
    border-right: 1px solid gray;
}
#sidebar {
    position: relative;
    height: 100%;
    overflow:hidden;
}
#sidebar .list-group-item {
        border-radius: 0;
        border-left: 0;
        border-right: 0;
        border-top: 0;
}
@media (max-width: 992px) {
    body {
        padding-top: 0px;
    }
}
@media (min-width: 992px) {
    #main-wrapper {
        float:right;
    }
}
@media (max-width: 992px) {
    #main-wrapper {
        padding-top: 0px;
    }
}
@media (max-width: 992px) {
    #sidebar-wrapper {
        position: static;
        height:auto;
        max-height: 300px;
  		border-right:0;
	}
}
.footer {
    background-color:#ffffff;
	bottom:0;
  	position:fixed;
    padding:10px;
}



</style>
<script>


</script>
</html>