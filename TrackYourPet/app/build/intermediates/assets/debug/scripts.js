
jQuery(document).ready(function() {
	


       var map;
       var mapLocation={lat: 13.76339578, lng: 79.32815552};
       var MarkerIcon="pin.png";


       var pinIcon = new google.maps.MarkerImage(
                        MarkerIcon,
                        null, /* size is determined at runtime */
                        null, /* origin is 0,0 */
                        null, //new google.maps.Point(0, 0), /* anchor is bottom center of the scaled image */
                        new google.maps.Size(50, 50)
                    );

       map = new google.maps.Map(document.getElementById('googleMap'), {
                      zoom: 9,
                       center: mapLocation ,
                       disableDefaultUI: true,
                       mapTypeId:google.maps.MapTypeId.ROADMAP//SATELLITE//TERRAIN //HYBRID//ROADMAP

                    });


        var marker = new google.maps.Marker({
                        position: mapLocation,
                        map: map,
                        icon: pinIcon,
                        title: 'Hello World!'
                      });

	/*$('.launch-modal').on('click', function(e){
		e.preventDefault();
		$( '#' + $(this).data('modal-id') ).modal();
	});
    
     
    $('.registration-form input[type="text"], .registration-form textarea').on('focus', function() {
    	$(this).removeClass('input-error');
    });
    
    $('.registration-form').on('submit', function(e) {
    	
    	$(this).find('input[type="text"], textarea').each(function(){
    		if( $(this).val() == "" ) {
    			e.preventDefault();
    			$(this).addClass('input-error');
    		}
    		else {
    			$(this).removeClass('input-error');
    		}
    	});
    	
    });*/
    
    
});
