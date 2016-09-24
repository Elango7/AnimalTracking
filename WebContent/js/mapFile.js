$( document ).ready(function() {
 	var liveTrackCount=0;
	var liveTrack;
	var lineCount=false;
	var flightPath;
	var rootPath="http://animaltrack.in/locationtracking/";


     $("#myModal").modal("hide");
$('#StopLiveTrack').hide();
			$('#StartLiveTrack').hide();
	 
	 		 $('#showDetailsLi').hide();

	
	 $("#showSideBar").click(function(e) {
	     //   console.log( "inside!" );

	        e.preventDefault();
	        $("#wrapper").toggleClass("toggled");
	    });
	 
		 

	 $("#mainPage").click(function(e) {
	      //  console.log( "inside!" );
	        $('#StartLiveTrack').hide();
	 
	 		 $('#showDetailsLi').hide();
	        GetAllAnimal();

	        // alert(1);
	    });


	  $("#dashBoardClick").click(function(e) {
 	       window.location.replace("dashboard.php");   

 	    });






   $.ajax({
          //type : "POST",
          contentType : "application/json",
          url : rootPath+"/tracking/idleDevice/"+480+"/",
          //data : JSON.stringify(loginfo),
          dataType : 'json',
          timeout : 100000,
          success : function(data) {
           $("#idleCount").text(data.length);

            
          },
          error : function(e) {
            console.log("ERROR: ", e);
          },
          done : function(e) {
            console.log("DONE");
          }
        });

	 
	 var tempLocation;
	 GetAllAnimal();
	 
      function GetAllAnimal() {
	 
		$.ajax({
			//type : "POST",
			contentType : "application/json",
			url : rootPath+"tracking/locationAll/",
			//data : JSON.stringify(loginfo),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				//("SUCCESS: ", data );
 		 	       MainPage(data);

			
			},
			error : function(e) {
				console.log("ERROR: ", e);
 			},
			done : function(e) {
				console.log("DONE");
 			}
		});
      }
	 
	 
	 
	 var id;
	 var mark = {};
    var markers = [];
    var markersArray = [];

    var map;
    
	  var image = 'baidu-paw-logo.png';
	  
	/*   var tempLocation=[{
			"device_id": "Animal_1",
			"update_time": "2016-07-04 22:37:13",
			"lan_lat": {
				"lat": 13.76339578,
				"lng": 79.32815552
			}
		}, {
			"device_id": "Animal_2",
			"update_time": "2016-07-04 22:38:21",
			"lan_lat": {
				"lat": 13.79473936,
				"lng": 79.27871704
			}
		}, {
			"device_id": "Animal_3",
			"update_time": "2016-07-04 22:39:15",
			"lan_lat": {
				"lat": 13.87141335,
				"lng": 79.24575806
			}
		}, {
			"device_id": "Animal_4",
			"update_time": "2016-07-27 00:27:58",
			"lan_lat": {
				"lat": 13.94273074,
				"lng": 79.15512085
			}
		}];
	    */
	 







	   var neighborhoods = [
	                      {lat: 13.76339578, lng: 79.32815552},
	                      {lat: 13.79473936, lng: 79.27871704}/* ,
	                      {lat: 13.87141335, lng: 79.24575806},
	                      {lat: 13.94273074, lng: 79.15512085} */
	                    ];
	
 
	

 	        
 	       
 	        
 		      function MainPage(data) {
 		    	  $(".liClick").empty();

 		    	  
 		    	 tempLocation=data;
 		    	  
 		    	  
 		    	   //console.log("SUCCESS..........: ", tempLocation.length );

 		    	//  var secretMessages = ['Animal_1', 'Animal_2'];
 		    	  var int = 0;
 		    for (int = 0; int < tempLocation.length; int++) {
 		    	var jsonData=$('<li class="liClick"  data-deviceName="'+tempLocation[int].device_id+'"> <a href="#">Track '+tempLocation[int].animal_name+'<span class="pull-right delDevice"  data-toggle="modal" data-deviceId="'+tempLocation[int].device_id+'" data-deviceNameModel="'+tempLocation[int].animal_name+'"  data-toggle="tooltip" data-placement="top" title="Delete !" style="cursor:pointer"><i class="fa fa-times-circle" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></a></li>');
 		        $("#searchListUl").append(jsonData.clone());

 		    }
 		    $("#AnimalCount").text(int);
 		    	  var delDeviceId;
 		    	  $(".delDevice").on('click', function() {

 		    	  	 $('#DeleteModal').modal('show');
 		    	  	//console.log('Inside');
					//console.log($(this).attr("data-deviceId"));
					$("#animalName").text($(this).attr("data-deviceNameModel"));
					delDeviceId=$(this).attr("data-deviceId");
					

					});

 		    	    $("#deleteDevice").on('click', function() {

 

								$.ajax({
											//type : "POST",
											contentType : "application/json",
											url : rootPath+"tracking/deleteLocation/"+delDeviceId+"/",
											//data : JSON.stringify(loginfo),
											dataType : 'json',
											timeout : 100000,
											success : function(data) {
												//("SUCCESS: ", data );
												$.notify("Delete Success..", "success");
								 		 	       GetAllAnimal();

											
											},
											error : function(e) {
												console.log("ERROR: ", e.status);
												if (e.status==200) {
													$.notify("Delete Success..", "success");
								 		 	       GetAllAnimal();
												}
												
								 			},
											done : function(e) {
												console.log("DONE");
								 			}
										});

 		    	   		   
					console.log(delDeviceId);

 		    	   		   	});

 		    	  
 		    	 var i=0;
 		    	 var count1=Math.round(tempLocation.length / 2);
var mainCenter;
if(tempLocation.length !=0 && tempLocation.length !=1){
console.log("Elan...",tempLocation.length);
mainCenter=new google.maps.LatLng(parseFloat(tempLocation[count1].lan_lat.lat),parseFloat(tempLocation[count1].lan_lat.lan));
}else{
mainCenter=new google.maps.LatLng(parseFloat(20.869078),parseFloat(78.222656));
}
 		    	 
 
 		 	        map = new google.maps.Map(document.getElementById('googleMap'), {
 			          zoom: 5,
  			          center: mainCenter,
 			    	   mapTypeId:google.maps.MapTypeId.TERRAIN//SATELLITE//TERRAIN //HYBRID//ROADMAP

 			        });
 		 	     // map.fitBounds(bounds);
 		 	     for (i = 0; i < tempLocation.length; i++) {
 		 	    	 var temp=tempLocation[i].animal_name;
  		 	 	  var obj={temp};
  		 	 	var myLatlng = new google.maps.LatLng(parseFloat(tempLocation[i].lan_lat.lat),parseFloat(tempLocation[i].lan_lat.lan));

  			          addMarkerWithTimeout(myLatlng, i * 200);
 			        }
 		 	 
 		 	     $(".liClick").on("click",function(e) {
  		 	      	clearOverlays();

  		 	      	if (liveTrackCount==0) {
 		 	      	 ShowIndRoute($(this).attr("data-deviceName"));


  		 	      	}else{

				 			clearInterval(liveTrack);


  		 	      	ShowIndRoute($(this).attr("data-deviceName"));

  		 	      	}


 		 			  });  
 		 			  
 		 			  
 		 			  
 			      function addMarkerWithTimeout(position, timeout) {
 						var deviceName=tempLocation[i].animal_name;
 						var deviceId=tempLocation[i].device_id;
  			        window.setTimeout(function() {
 			          markers=new google.maps.Marker({
 			        	  id: i,
 			            position: position,
 			            map: map,
 			            animation: google.maps.Animation.DROP,
 			            //icon: image,
 			            title: temp
 			             
 			          });
 			    	  //console.log(i);

 	 			     markersArray.push(markers);
 	 			     
 	 			     
 	 			     
 	 			     
  			          attachSecretMessage(markers,deviceName,deviceId);

 			        	//console.log(markers);
 			        	// id = markers.__gm_id;

 
 
 			        }, timeout);


 			        
 			      }
		        	//console.log("Testtttttttttt");

		        	

					function clearOverlays() {
						//console.log(markersArray.length);
					  for (var i = 0; i < markersArray.length; i++ ) {
					    markersArray[i].setMap(null);
					  }
					  markersArray.length = 0;
					}
							        	
		        	
		        	
 			      function attachSecretMessage(marker, deviceName,deviceId) {
			    	  var contentString = '<div style="width: 130px; padding-left:10px; height: 25px;float: left;color: #FFF;background: #ed1e79;line-height: 25px;border-radius:5px 5px 0px 0px;"><strong><center><b>'+deviceName+'</b></center></strong></div>';  

 			    	 //infowindow.setContent(locations[i][1]);
 			          var infowindow = new google.maps.InfoWindow({
 			            content: contentString
 			            
 			          });

 			          markers.addListener('mouseover', function() {
 			            infowindow.open(markers.get('map'), marker);
 			           // markers.setAnimation(google.maps.Animation.BOUNCE);
 			          //  console.log(markers);
 			            //toggleBounce(markers.get('map'));
 			          }); 
 			          
 			          markers.addListener('mouseout', function() {
 			        	// infowindow.close(markers.get('map'), marker);
 			        	  
 			        	  setTimeout(function(){infowindow.close(markers.get('map'), marker);}, '2000');
 			        	  
 			        	  
 				           // infowindow.close();
 				           // markers.setAnimation(google.maps.Animation.BOUNCE);
 				          //  console.log(markers);
 				           // toggleBounce(markers.get('map'));
 				          }); 
 			        	 markers.addListener('click', function() {
					        	//console.log(markers.visible='false');
					        	clearOverlays();
		 			        	  ShowIndRoute(deviceId);

					        	

								if (liveTrackCount==0) {
			 		 	      	 ShowIndRoute(deviceId);


			  		 	      	}else{

							 			clearInterval(liveTrack);


			  		 	      	ShowIndRoute(deviceId);

			  		 	      	}



 				            //console.log(marker);
 				            //console.log(markers);

 				          }); 
 			        	 
 			        	 
 			        	 
 			        	 
 			        	 
 			        	function clearMarkers() {
 					        for (var i = 0; i < markers.length; i++) {
 					        	//console.log(markers.length);
 					          markers[i].setMap(null);
 					          
 					        }
 					        markers = [];
 					      }
 			        	 
 			        }
 			     
 			       
 			      
 			      
 			      function toggleBounce(temp) {
 			            //console.log(temp);

 					  if (markers.getAnimation()	 !== null) {
 						  markers.setAnimation(null);
 					  } else {
 						  markers.setAnimation(google.maps.Animation.BOUNCE);
 					  }
 					}
 		      
 		      
 		      }
 		      
 		      
 //------------------------------------------------------------------------------------------------------------------------------------
 //------------------------------------------------------------------------------------------------------------------------------------
 //------------------------------------------------------------------------------------------------------------------------------------
 		     function ShowIndRoute(secretMessage) {
    			$('#livTrk').addClass('active');

 		 $('#showDetailsLi').hide();
 			var timerCount=0
			clearInterval(liveTrack);
			var zoomSize=0;
				//console.log(lineCount);
				

				 
 

 		    	$.ajax({
 					//type : "POST",
 					contentType : "application/json",
 					url : rootPath+"tracking/single_device_info/"+secretMessage+"/",
 					//data : JSON.stringify(loginfo),
 					dataType : 'json',
 					timeout : 100000,
 					success : function(data) {
 						//console.log("SUCCESS: ", data.lan_lat );
 		 		 	      DisplayIndividualRoute(data);

 					
 					},
 					error : function(e) {
 						console.log("ERROR: ", e);
 		 			},
 					done : function(e) {
 						console.log("DONE");
 		 			}
 				});

			$('#StopLiveTrack').hide();
			$('#StartLiveTrack').show();


		


			$("#StartLiveTrack").click(function(){
					clearInterval(liveTrack);
					zoomSize=18;
					liveTrackInterval();
					liveTrackCount=1;
				$('#livTrkStop').addClass('active');


					   

					$('#StopLiveTrack').show();
					$('#StartLiveTrack').hide();
 					$("#wrapper").toggleClass("toggled");
 						$("#StopLiveTrack").notify(
	        			  "Live Tracking Activated", 
	        			  { position:"left",  class: 'success' }
	        			);


			  // action goes here!!
			});





				$("#StopLiveTrack").click(function(){
						clearInterval(liveTrack);
						$('#StopLiveTrack').hide();
						$('#StartLiveTrack').show();
 					$("#wrapper").toggleClass("toggled");
    			$('#livTrk').addClass('active');
							zoomSize=0;
							liveTrackCount=0;

						  // action goes here!!
				});

    //your code
    var liveCount=0;
  			        	function liveTrackInterval() {

 		   liveTrack=setInterval(function(){

 
 		   console.log(liveCount++);

 		    	$.ajax({
 					//type : "POST",
 					contentType : "application/json",
 					url : rootPath+"tracking/single_device_info/"+secretMessage+"/",
 					//data : JSON.stringify(loginfo),
 					dataType : 'json',
 					timeout : 100000,
 					success : function(data) {
 						//console.log("SUCCESS: ", data.lan_lat );
 						  clearFootprint();
 
 		 		 	      ShowFootPrint(data);

 					
 					},
 					error : function(e) {
 						console.log("ERROR: ", e);
 		 			},
 					done : function(e) {
 						console.log("DONE");
 		 			}
 				});
 		    	
 		    	
 		    	 }, '1000');
 		
 	 		    }



							var footPrintMarker  ;
							var footPrintMarkerArray  = [];
 	 		     function ShowFootPrint(jsonData) {
 	 		     	var footaprintLocation=jsonData;
 	 		     	//var lastCount=footaprintLocation.length - 1 ; 
 	 		     	var icon = {
							    url: "WebContent/js/foot.png", // url
							    scaledSize: new google.maps.Size(20, 20), // scaled size
							    origin: new google.maps.Point(0,0), // origin
							    anchor: new google.maps.Point(0, 0) // anchor
							};
							
					var mapLocation=new google.maps.LatLng(parseFloat(footaprintLocation[0].lan_lat.lat),parseFloat(footaprintLocation[0].lan_lat.lan));
 						
 						
						footPrintMarker = new google.maps.Marker({
						    map: map,
						    draggable: true,
						   // animation: google.maps.Animation.DROP,
						    position: mapLocation,
						    icon: icon
						  });
						 map.setZoom(20);
						 footPrintMarkerArray.push(footPrintMarker);
						  //marker.addListener('click', toggleBounce);

						}


					function clearFootprint() {


						//footPrintMarker.setMap(null);
 						//console.log(markersArray.length);
					   for (var i = 0; i < footPrintMarkerArray.length; i++ ) {
					    footPrintMarkerArray[i].setMap(null);
					  }
					  footPrintMarkerArray.length = 0; 
					}
 			        	 

 	 		     function DisplayIndividualRoute(jsonData) {
 	 		    	 
 	 		    	var temp1=jsonData;
 	 		    	if (temp1.length != null || temp1.length != undefined) {

					$('#showDetailsLi').show();
 	 		    		$("#jsonCount").text(temp1.length-1);
 	 		    	}
 	 		    	 
 	 		    	 //console.log(',name: secretMessage');
 	 		    	 //console.log(secretMessage);
 	 		    	var i=0; 
 	 		    	
  	 		 /*    	[
                      {lat: 13.76339578, lng: 79.32815552},
                      {lat: 13.79473936, lng: 79.27871704},
                      {lat: 13.87141335, lng: 79.24575806},
                      {lat: 13.94273074, lng: 79.15512085}
                    ];
 	 		    	 */
 	 		    	
 	 		    	var drawPolyLine=new Array();

  	 	     	
 	 		    	
 	 		    		
 	 		    	  	for (var i = 0; i < temp1.length; i++) {
 	 		    	  		 
 	 		    	   drawPolyLine.push({'lat' : temp1[i].lan_lat.lat , 'lng' : temp1[i].lan_lat.lan})
 	 		    	  
		          	}  
 	 	 		    	 //console.log("Draw................. ",drawPolyLine);


//PDF GEnerate................
//---------------------------------------------------------------------------------------
$("#generatePdf").click(function(){
 
 			    var pdf = new jsPDF('p', 'pt', 'letter');
			    // source can be HTML-formatted string, or a reference
			    // to an actual DOM element from which the text will be scraped.
			   var source = $('#exportPdf')[0];

			    // we support special element handlers. Register them with jQuery-style 
			    // ID selector for either ID or node name. ("#iAmID", "div", "span" etc.)
			    // There is no support for any other type of selectors 
			    // (class, of compound) at this time.
			    specialElementHandlers = {
			        // element with id of "bypass" - jQuery style selector
			        '#bypassme': function (element, renderer) {
			            // true = "handled elsewhere, bypass text extraction"
			            return true
			        }
			    };
			    margins = {
			        top: 80,
			        bottom: 60,
			        left: 40,
			        width: 522
			    };
			    // all coords and widths are in jsPDF instance's declared units
			    // 'inches' in this case
			    pdf.fromHTML(
			    source, // HTML string or DOM elem ref.
			    margins.left, // x coord
			    margins.top, { // y coord
			        'width': margins.width, // max width of content on PDF
			        'elementHandlers': specialElementHandlers
			    },

			    function (dispose) {
			        // dispose: object with X, Y of the last line add to the PDF 
			        //          this allow the insertion of new lines after html
			        pdf.save('Test.pdf');
			        }, margins );
 

});
//---------------------------------------------------------------------------------------




 			        	//neighborhoods=[];
 			        	
 			        	/* 
 			          	var temp1=[{
 			        		"device_id": "device1",
 			        		"update_time": "2016-07-04 22:30",
 			        		"lan_lat": {
 			        			"lat": 12.9767336,
 			        			"lng": 80.2158342
 			        		}
 			        	}, {
 			        		"device_id": "device1",
 			        		"update_time": "2016-07-04 23:00:00",
 			        		"lan_lat": {
 			        			"lat": 12.9917889,
 			        			"lng": 80.2253708
 			        		}
 			        	}, {
 			        		"device_id": "device1",
 			        		"update_time": "2016-07-04 23:30:00",
 			        		"lan_lat": {
 			        			"lat": 12.9981895,
 			        			"lng": 80.2159241
 			        		}
 			        	}, {
 			        		"device_id": "device1",
 			        		"update_time": "2016-07-04 24:00:00",
 			        		"lan_lat": {
 			        			"lat": 13.0030857,
 			        			"lng": 80.2093557
 			        		}
 			        	}];   */
 			        	
 			          	
 			        	/* 	for (var i = 0; i < temp1.length; i++) {
			          		
	 			          	neighborhoods = neighborhoods + new google.maps.LatLng(parseFloat(temp1[i].lan_lat.lat),parseFloat(temp1[i].lan_lat.lan));

			          	} */
 			            
 			            
 			            
 			           
 			            
 				//--------------------------------------------------------------
 				//Bind The Location In Modal
 					var tData=null;
 			          	var totalDist=0;
 			          	 var count;
 				            var country;
 				            var state;
 				            var city='';

 			          	//$('#tableData').append(tData );
 			            $("#tableData").find("tr:gt(0)").remove();
 			          	for (var int = 0; int < temp1.length-1; int++) {
 			          		
 			          		var lat1=temp1[int].lan_lat.lat,
 			          			lon1=temp1[int].lan_lat.lan,
 			            		lat2=temp1[int+1].lan_lat.lat,
 			            		lon2=temp1[int+1].lan_lat.lan;
 			          		
 			          		var cityNameStart='test';//findPlaceName(lat1,lon1);
 			          		var cityNameEnd='Test';//findPlaceName(lat2,lon2);

 	                        //console.log("city name recived is: " , cityNameStart);

 			          		var dt;//=temp1[int+1].update_time;
 			          		
 			          		var finaldt=getDistanceFromLatLonInKm(lat1,lon1,lat2,lon2);
 			            	//console.log('Dist=',Math.round(finaldt));
 			            	 finaldt=finaldt.toFixed(2)//Math.round(finaldt);
 			            	 
 			            	 
 			            	totalDist=parseFloat(totalDist) +  parseFloat(finaldt);     //totalDist+finaldt;
 			            	
 					      tData= tData +'<tr>'+
 			          		'<td>lat : '+ lat1 + '<br> lng : '+ lon1 +'<br>Place :<span id="cityName"> '+ cityNameStart +' </span></td>'+
 					        '<td>lat : '+ lat2 + '<br> lng : '+ lon2 +'<br>Place :<span id="cityName"> '+ cityNameEnd +' </span></td>'+
 					        '<td><br>'+ finaldt +'</td>'
 					      '</tr>';
 				          	//console.log(lat2);
 							
 						}
 			          	//console.log(tData);
 			          	//console.log(temp1.length);
 			          	
 						var totalTime= parseFloat(temp1.length-1) * parseFloat(0.50);
 			          	//console.log('totalTime',totalTime);

 						var timeTravel=totalTime * 60;
 			          	//console.log('timeTravel',timeTravel);

 						var avgDist= parseFloat(totalDist) / parseFloat(timeTravel);
 						
 			          	//console.log('totalDist.............',totalDist);

 			          
					if (zoomSize==0) {
 			          	if (totalDist == 0) {
 			          		zoomSize=22;
						}else if (totalDist < 6) {
 			          		zoomSize=15;
						}
						else if (totalDist < 10) {
 			          		zoomSize=12;
						}else if (totalDist < 200) {
 			          		zoomSize=8;

						}else if (totalDist > 200 && totalDist < 500) {
 			          		zoomSize=5;

						}else if (totalDist > 500 && totalDist < 1000) {
 			          		zoomSize=3;

						}else {
 			          		zoomSize=2;

						}
 			          	
 			          	
 			          	
 			          	
 			          	}
			        	   map.setZoom(zoomSize);

 			          	tData=tData+'<tr><td></td><td>Total Distance</td><td>'+ (parseFloat(totalDist)).toFixed(2) +' <small> per/hr</small>	</td></tr>';

 			          	$('#tableData').append(tData );
 			          	
 			          	
 			        	function getDistanceFromLatLonInKm(lat1,lon1,lat2,lon2) {
 			      		  var R = 6371; // Radius of the earth in km
 			      		  var dLat = deg2rad(lat2-lat1);  // deg2rad below
 			      		  var dLon = deg2rad(lon2-lon1); 
 			      		  var a = 
 			      		    Math.sin(dLat/2) * Math.sin(dLat/2) +
 			      		    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
 			      		    Math.sin(dLon/2) * Math.sin(dLon/2)
 			      		    ; 
 			      		  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
 			      		  var d = R * c; // Distance in km
 			      		  return d;
 			      		}

 			      		function deg2rad(deg) {
 			      		  return deg * (Math.PI/180)
 			      		}
 			      		
 			      		
 			      		function findPlaceName(lat,lng) {
 			      			var finalCity;

 			      			var t1=11.699895,t2=77.475586;
 			      			 //Find The Place Name
 				            var geocoder;
 				            geocoder = new google.maps.Geocoder();
 				            var latlng = new google.maps.LatLng(t1, t2);
 				           
 				            geocoder.geocode(
 				                {'latLng': latlng}, 
 				                function(results, status) {
 				                    if (status == google.maps.GeocoderStatus.OK) {
 				                            if (results[0]) {
 				                                var add= results[0].formatted_address ;
 				                                var  value=add.split(",");

 				                                count=value.length;
 				                                country=value[count-1];
 				                                state=value[count-2];
 				                                city=value[count-3];
 				                                finalCity=value[count-3];

 				                                //console.log("city name is:.......... " , finalCity);
 				                                //return city;
 				                            }
 				                            else  {
 				                            	console.log("address not found");
 				                            }
 				                    }
 				                     else {
 				                    	 console.log("Geocoder failed due to: " , status);
 				                    }
 				                }
 				            );
 	                        console.log("city name is:..xxxxxxx........ " , finalCity);

 				            return finalCity;

 				      		  
 				      		}
 			      		
 			      		
 			      		
 			      		
 			      		
 			      		
 			          	//-----------------------------------------------------------------------------------------------------
 			          	
 	 		    	 
 			    	/*   var flightPath = new google.maps.Polyline({
 				          path: drawPolyLine,
 				          geodesic: true,
 				          strokeColor: '#FF0000',
 				          strokeOpacity: 0,
 				          icons: [{
 				        	    icon: lineSymbol,
 				        	    offset: '0',
 				        	    repeat: '20px'
 				        	  }],
 				          strokeWeight: 2
 				        });
 			    	   
 	 		        	flightPath.setMap(null);
 			          */
 	 		        	
 	 		        	
 		  		 	 	//var myLatlng = new google.maps.LatLng(parseFloat(temp1[i].lan_lat.lat),parseFloat(temp1[i].lan_lat.lan));

 	 		        	 
 			        	/* if (secretMessage=='Animal_1') {
 			        		neighborhoods = [
 			 			                      {lat: 13.76339578, lng: 79.32815552 ,time: '12:10 PM'},
 			 			                     {lat: 11.740235 , lng: 78.711548 ,time: '12:11 PM'},
 			 			          	       {lat: 11.866595 , lng: 78.189697 ,time: '12:13 PM'},
 			 			          	       {lat: 11.699895 , lng: 77.475586 ,time: '12:14 PM'}
 			 			                    ];

 						}else{
 							
 							neighborhoods = [
 						                      {lat: 13.76339578, lng: 79.32815552},
 						                      {lat: 13.79473936, lng: 79.27871704},
 						                      {lat: 13.87141335, lng: 79.24575806},
 						                      {lat: 13.94273074, lng: 79.15512085}
 						                    ];
 				        	  map.setZoom(10);

 							
 						} */
 			        	if (lineCount==true) {
  				  
  				    flightPath.setMap(null);
  				    lineCount=false;


			  				}

 			        	  function clearLine() {
  				    	          flightPath.setMap(null);
 
                                   lineCount=false;
 						      }

 						      	$("#showLine").click(function(){
									flightPath.setMap(null);
								});
 			        	
 			        	/* $("#UserRoleSh").click(function(e) {
 			    	        console.log( "inside!" );
 			    	        $("#myModal").modal("show");
 			    	       // flightPath.setMap(null);
 			    	        // alert(1);
 			    	    }); */
 	 		    	 
 	 		    	/* neighborhoods = [
 	 			                      {lat: 13.76339578, lng: 79.32815552 ,time: '12:10 PM'},
 	 			                     {lat: 11.740235 , lng: 78.711548 ,time: '12:11 PM'},
 	 			          	       {lat: 11.866595 , lng: 78.189697 ,time: '12:13 PM'},
 	 			          	       {lat: 11.699895 , lng: 77.475586 ,time: '12:14 PM'}
 	 			                    ]; */
						clearOverlays();

 			 	      function clearMarkers() {
 					        for (var i = 0; i < markers.length; i++) {
 					        	//console.log(markers.length);
 					          markers[i].setMap(null);
 					          
 					        }
 					        markers = [];
 					      }
 			 	      
 	  				function clearOverlays() {
						//console.log(markersArray.length);
					  for (var i = 0; i < markersArray.length; i++ ) {
					    markersArray[i].setMap(null);
					  }
					  markersArray.length = 0;
					}
 			        	  
 			        	 var count2= temp1.length -1;
 			        	 count2=Math.round(count2);
 		 		    	 var mainCenter1=new google.maps.LatLng(parseFloat(temp1[count2].lan_lat.lat),parseFloat(temp1[count2].lan_lat.lan));

			        	 
							var CountLatLan;

 							CountLatLan=0; 	
 			        	   var startlatLan = new google.maps.LatLng(parseFloat(temp1[0].lan_lat.lat),parseFloat(temp1[0].lan_lat.lan));
							
							 map.setCenter(startlatLan);
							 addMarkerWithTimeout(startlatLan, 0 );
							
							// CountLatLan=temp1.length-1;
							//var endlatLan = new google.maps.LatLng(parseFloat(temp1[CountLatLan].lan_lat.lat),parseFloat(temp1[CountLatLan].lan_lat.lan));
							//addMarkerWithTimeout(endlatLan, 0);





							//Add All latlan Marker in timmer :)
 			        	/*  for ( i = 0; i < temp1.length; i++) {
 			        		  
 		 		   		 	 	var myLatlng1 = new google.maps.LatLng(parseFloat(temp1[i].lan_lat.lat),parseFloat(temp1[i].lan_lat.lan));
								     "http://blogs.technet.com/cfs-file.ashx/__key/communityserver-blogs-components-weblogfiles/00-00-01-01-35/e8nZC.gif",

 		 			          addMarkerWithTimeout(myLatlng1, i * 200);
 		 			        }
 			        	  *///showDetailsLi
  			        	  var pinIcon = new google.maps.MarkerImage(
								     "http://www.clker.com/cliparts/q/I/Q/u/Z/1/marker.svg",
								    null, /* size is determined at runtime */
								    null, /* origin is 0,0 */
								    null, /* anchor is bottom center of the scaled image */
								    new google.maps.Size(42, 68)
							);
 			        	  function addMarkerWithTimeout(position, timeout) {
 			        		  var temp=temp1[CountLatLan];
 			        		  //console.log(temp.time);
 			 			        window.setTimeout(function() {
 			 			          markers=new google.maps.Marker({
 			 			            position: position,
 			 			            map: map,
 			 			            optimized:false,
  			 			            // icon: pinIcon ,
 			 			            title: secretMessage
 			 			             
 			 			          });
 			  	 			      markersArray.push(markers);

 			 			          attachSecretMessage(markers, temp);
 			 	 		    	 
									//clearMarkers();
 			 			         
 			 	 		    	    //clearLine();

 			 			         

 			 			        }, timeout);


 			 			        
 			 			      }
 	 		    	 
 	 		    	 
 	 		    	 
 			        	  function clearMarkers() {
 				        		//addMarkerWithTimeout(null,0);
 						        for (var i = 0; i < markers.length; i++) {
 						        	//console.log(markers.length);
 						          markers[i].setMap(null);
 						          
 						        }
 						        markers = [];
 						      }
 			        	  var lineSymbol = {
 			        			  path: 'M 0,-1 0,1',
 			        			  strokeOpacity: 1,
 			        			  scale: 4
 			        			};

 	 		    	     flightPath = new google.maps.Polyline({
 				          path: drawPolyLine,
 				          geodesic: true,
 				          strokeColor: '#FF0000',
 				          strokeOpacity: 0,
 				          icons: [{
 				        	    icon: lineSymbol,
 				        	    offset: '0',
 				        	    repeat: '20px'
 				        	  }],
 				          strokeWeight: 2
 				        });
 	 		    	     addLine();

 	 		    	 function addLine() {
  	 		    		 lineCount=true;
 	 		    		 //console.log(flightPath.size);
 	  		    		
 

 	 		    		flightPath.setMap(map);
 	 		          }
 				        //flightPath.setMap(map);

 				         
 				        
 				      
 				      function attachSecretMessage(marker, neighborhoods) {
 				    	  var animalName=neighborhoods.animal_name;
 				    	  var lat=neighborhoods.lan_lat.lat;
 				    	  var lng=neighborhoods.lan_lat.lan;
 				    	  var time=neighborhoods.update_time;
 				    	  var contentString = '<div style="width: 100%; padding-left:10px; height: 25px;float: left;color: #FFF;background: #ed1e79;line-height: 25px;border-radius:5px 5px 0px 0px;"><strong><center><b>'+animalName+'</b></center></strong></div><div style="clear:both;background-color: aliceblue;height:0px;"><div style="float:left;width:100%;padding:5px 10px;border:1px solid #ccc;border-top:none;border-radius:0px 0px 5px 5px;background-color: antiquewhite;"><div style="float:left;color:#666;font-size:18px;font-weight:bold;margin:5px 0px;"> <div style="padding: 0px;">Lat : '+lat+'<br>Lng: '+lng+'</div></div><div style="clear:both;height:0px;"></div><div style="float:left;line-height:18px;color:#666;font-size:13px;">Time : '+time+'</div><div style="clear:both;height:0px;"></div><form action=\"navigat:"You feild"\"><input data-toggle="modal" data-target="#myModal" type=\"submit\"/ style=\"background:#7e7e7e;float:right;color:#FFF;padding:5px 7px;font-size:10px;font-weight:bold;border:none;margin:5px 0px; border-radius:0px !important;\" value=\"More Info\" ></form></div></div>';  

 						var contentWindow='<p>Lat : '+lat+'<p><p>Lng : '+lng+'<p><p>Time : '+secretMessage+'<p>';
 						 
 				    	  
 	 	 			          var infowindow = new google.maps.InfoWindow({
 		 			            content: contentString,
 		 			           maxWidth: 700
 		 			            
 		 			            
 		 			          });

 		 			          markers.addListener('mouseover', function() {
 		 			            infowindow.open(markers.get('map'), marker);
  		 			        	  

 		 			            
 		 			          }); 
 		 			          
 		 			          markers.addListener('mouseout', function() {
 	 	 			        	  
 		 			        	 setTimeout(function(){infowindow.close(markers.get('map'), marker);}, '1500');
 		 			        	   
 		 				           
 		 				          }); 
 		 			        	 markers.addListener('click', function() {
 		 	 			            infowindow.open(markers.get('map'), marker);

 		 			        		  map.setZoom(11);
 		 	 			        	  map.setCenter(marker.getPosition());
 	    
 		 				            //console.log(marker);
 		 				            //console.log(markers);

 	 	 				          }); 
 		 			        	   
 		 			        }
 		 			     
 				      
 				      
 				      
 	 		    	 
 	 		    	 
 	 		     }
 	 		      
 	 		      
 	 		    	 
 	 		    	 
 	 		    	 
 	 		    	 
 	 		     }
 		    	
 	        
 	        
 	        
 	        
 	        
	        
	   
	   /* 
	   
	   function initialize() {
	    	  var mapProp = {
	    	    center:new google.maps.LatLng(12.300652034209379,79.7169041633607),
	    	    zoom:12,
	    	    mapTypeId:google.maps.MapTypeId.TERRAIN //HYBRID//ROADMAP
	    	  };
	    	     map=new google.maps.Map(document.getElementById("googleMap"), mapProp);
	    	  
	    	  
	    	  var image = 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png';
	    	   beachMarker = new google.maps.Marker({
	    	    position: {lat: 12.300652034209378, lng: 79.7169041633606},
	    	    animation: google.maps.Animation.DROP,
	    	    map: map,
	    	    icon: image
	    	  });
	    	  beachMarker.addListener('click', toggleBounce);
	    	  
	    	}
	    	google.maps.event.addDomListener(window, 'load', initialize);
	
	
	
	
	clearMarkers();
    for (var i = 0; i < neighborhoods.length; i++) {
      addMarkerWithTimeout(neighborhoods[i], i * 200);
    }
    
    
    
    function addMarkerWithTimeout(position, timeout) {
        window.setTimeout(function() {
        	beachMarker.push(new google.maps.Marker({
            position: position,
            map: map,
            animation: google.maps.Animation.DROP
          }));
        }, timeout);
      }
    
    function clearMarkers() {
    	console.log('Insie clears');
        for (var i = 0; i < beachMarker.length; i++) {
        	beachMarker[i].setMap(null);
        }
        beachMarker = [];
      }
	
     console.log( "ready!" );
    var i=0;
    var j=i;
    
    $("#samClick").click(function(e) {
    	console.log("sammmmmmmmmm");
    });
    $("#showSideBar").click(function(e) {
        console.log( "inside!" );

        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
    function toggleBounce() {
		  if (beachMarker.getAnimation() !== null) {
			  beachMarker.setAnimation(null);
		  } else {
			  beachMarker.setAnimation(google.maps.Animation.BOUNCE);
		  }
		}
     */
 /*    $("#showSideBar").click(function(){
      	
 $("#sidebar-wrapper").toggle();
 if (i==j) {
	 $('#main-wrapper').removeClass('col-md-10');
	 $('#main-wrapper').addClass('col-md-12');
	 i=i+1;
}else if (i!=j) {
	$('#main-wrapper').removeClass('col-md-12');
	 $('#main-wrapper').addClass('col-md-10');
j=i;
}

 
 
 }); */

 
   /*  function initialize() {
    	  var mapProp = {
    	    center:new google.maps.LatLng(12.300652034209379,79.7169041633607),
    	    zoom:12,
    	    mapTypeId:google.maps.MapTypeId.TERRAIN //HYBRID//ROADMAP
    	  };
    	  var map=new google.maps.Map(document.getElementById("googleMap"), mapProp);
    	  
    	  
    	  var image = 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png';
    	   beachMarker = new google.maps.Marker({
    	    position: {lat: 12.300652034209378, lng: 79.7169041633606},
    	    animation: google.maps.Animation.DROP,
    	    map: map,
    	    icon: image
    	  });
    	  beachMarker.addListener('click', toggleBounce);
    	  
    	}
    	google.maps.event.addDomListener(window, 'load', initialize); */
    
    	
});
