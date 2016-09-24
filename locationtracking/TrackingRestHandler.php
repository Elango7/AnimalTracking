<?php
require_once("SimpleRest.php");
require_once("Util.php");

require_once("Mobile.php");
		
class TrackingRestHandler extends SimpleRest {

	public function insertLocation($locationInfo){
		$utils = new Util(); 
		$device_id=$locationInfo["device_id"];
		$lan=$locationInfo["lan"];
		$lat=$locationInfo["lat"];
		 	$sql="INSERT INTO location_info (device_id, lan,lat, update_time) VALUES ('$device_id', '$lan','$lat', CURRENT_TIMESTAMP)";
		if (mysqli_query( $utils->dbConnect(), $sql)) {
			echo "Success";
		} else {
			echo "Error: " . $sql . "<br>" . mysqli_error( $utils->dbConnect());
		}
 	}
	
	public function insertAnimalInfo($animalInfo){
		$utils = new Util(); 
                 $successArray= array();
                 $successArray["Success"]="Success";
		$device_id=$animalInfo["device_id"];
		$animalName=$animalInfo["name"];
		$animalType=$animalInfo["type"];
		 	$sql="INSERT INTO animal_info (animal_name, device_id, animal_type) VALUES ('$animalName', '$device_id', $animalType)";
		if (mysqli_query( $utils->dbConnect(), $sql)) {
//			echo $successArray;
                        echo json_encode($successArray);
		} else {
			echo "Error: " . $sql . "<br>" . mysqli_error( $utils->dbConnect());
		}
 	}
	
	public function getDeviceInfo(){
		$utils = new Util();
    	//$sql = "SELECT device_id,max(update_time) update_time,lan_lat FROM location_info group by device_id";
		$sql = "SELECT lf.id, lf.device_id, max(lf.update_time) update_time, lf.lan, lf.lat,ain.animal_name,ain.animal FROM location_info lf left outer join (select ai.animal_name,ai.device_id,a.animal from animal_info ai left outer join animal a on a.id=ai.animal_type) ain on ain.device_id=lf.device_id group by lf.device_id";

		//$result = mysqli_query($utils->dbConnect(), $sql) or die("Error in Selecting ");
		if ($result = mysqli_query($utils->dbConnect(), $sql)) {
		} else {
			echo "Error: " . $sql . "<br>" . mysqli_error( $utils->dbConnect());
		}
		//create an array
		$locationArray = array();
		while($row =mysqli_fetch_assoc($result))			
		{
			
			$lan_lat["lan"]=floatval($row["lan"]);
			$lan_lat["lat"]=floatval($row["lat"]);
			$row["lan_lat"]=$lan_lat;
			unset($row["lan"]);
			unset($row["id"]);

			unset($row["lat"]);

			//array_push($row["0"],$lan_lat);
			$locationArray[] = $row;

		}
		echo json_encode($locationArray);

  		
	}
	
	public function getAnimalList(){
		$utils = new Util(); 

    	 $sql = "SELECT id,animal from animal";
		$result = mysqli_query($utils->dbConnect(), $sql) or mysqli_error($connection);
		//create an array
		$animalArray = array();
		while($row =mysqli_fetch_assoc($result))
		{
			$animalArray[] = $row;
		}
		echo json_encode($animalArray);
		

  		
	}
	
		public function getSingleDeviceInfo($deviceId){
		  $utils = new Util(); 
    	 // $sql = "SELECT device_id,update_time,lan_lat FROM location_info where device_id='$deviceId'";
		 $sql = "SELECT lf.id, lf.device_id, lf.update_time, lf.lan, lf.lat,ain.animal_name,ain.animal FROM location_info lf left outer join (select ai.animal_name,ai.device_id,a.animal from animal_info ai left outer join animal a on a.id=ai.animal_type) ain on ain.device_id=lf.device_id WHERE lf.device_id='$deviceId' order by lf.id DESC";

		  $result = mysqli_query($utils->dbConnect(), $sql) or die("Error in Selecting ");
				//create an array
		$locationArray = array();
		$lan_lat = array();

		while($row =mysqli_fetch_assoc($result))
		{
			$lan_lat["lan"]=floatval($row["lan"]);
			$lan_lat["lat"]=floatval($row["lat"]);
			$row["lan_lat"]=$lan_lat;
			unset($row["lan"]);
			unset($row["id"]);

			unset($row["lat"]);

			//array_push($row["0"],$lan_lat);
						$locationArray[] = $row;

		}
		echo json_encode($locationArray);

	}

	
	
	public function getIdleDeviceInfo($idleMins){
		  $utils = new Util(); 
    	 // $sql = "SELECT device_id,update_time,lan_lat FROM location_info where device_id='$deviceId'";
		// $sql = "SELECT lf.id, lf.device_id, lf.update_time, lf.lan, lf.lat,ain.animal_name,ain.animal FROM location_info lf left outer join (select ai.animal_name,ai.device_id,a.animal from animal_info ai left outer join animal a on a.id=ai.animal_type) ain on ain.device_id=lf.device_id WHERE lf.device_id='$deviceId'";
              // $sql = "select z.device_id,z.update_time,z.lan,z.lat, diff from (SELECT lf.id, lf.device_id, max(lf.update_time) update_time, lf.lan, lf.lat,ain.animal_name,ain.animal,ceil((TIME_TO_SEC(TIMEDIFF(current_timestamp,max(lf.update_time)))/(60))) as diff FROM location_info lf left outer join (select ai.animal_name,ai.device_id,a.animal from animal_info ai left outer join animal a on a.id=ai.animal_type) ain on ain.device_id=lf.device_id group by lf.device_id) z where z.diff>='$idleMins' ";
$sql = "select k.device_id,k.update_time,k.lan,k.lat, k.diff,ai.animal_name from (select z.device_id,z.update_time,z.lan,z.lat, diff from (SELECT lf.id, lf.device_id, max(lf.update_time) update_time, lf.lan, lf.lat,ain.animal_name,ain.animal,ceil((TIME_TO_SEC(TIMEDIFF(current_timestamp,max(lf.update_time)))/(60))) as diff FROM location_info lf left outer join (select ai.animal_name,ai.device_id,a.animal from animal_info ai left outer join animal a on a.id=ai.animal_type) ain on ain.device_id=lf.device_id group by lf.device_id) z where z.diff>='$idleMins' ) k left outer join animal_info ai on ai.device_id= k.device_id";
		  $result = mysqli_query($utils->dbConnect(), $sql) or mysqli_error( $utils->dbConnect());
			//create an array
		$locationArray = array();
		$lan_lat = array();

		while($row =mysqli_fetch_assoc($result))
		{
			//$lan_lat["lan"]=floatval($row["lan"]);
			//$lan_lat["lat"]=floatval($row["lat"]);
			//$row["lan_lat"]=$lan_lat;
			//unset($row["lan"]);


			//unset($row["lat"]);

			//array_push($row["0"],$lan_lat);
						$locationArray[] = $row;

		}
		echo json_encode($locationArray);

	}
	
	public function doLogin($userName,$password){
		  $utils = new Util(); 
		  
    	  $sql = "SELECT user_name,password FROM login where user_name='$userName' and password='$password'";
 		  $result = mysqli_query($utils->dbConnect(), $sql) or die("Error in Selecting ");

		//create an array
		$locationArray = array();
		if($row =mysqli_fetch_assoc($result)){
			echo 1;
		}else{
			echo 0; 
		}
  		
	}
	
	public function deleteLocation($deviceId){
		$utils = new Util(); 	
     	$sql = "DELETE FROM location_info WHERE device_id='$deviceId'";
		$result = mysqli_query($utils->dbConnect(), $sql) or mysqli_error($connection);
		if($result){
			echo "Success";
		}else{
			echo "Error in deleting";
		}
		
	}
	
	public function encodeHtml($responseData) {
	
		$htmlResponse = "<table border='1'>";
		foreach($responseData as $key=>$value) {
    			$htmlResponse .= "<tr><td>". $key. "</td><td>". $value. "</td></tr>";
		}
		$htmlResponse .= "</table>";
		return $htmlResponse;		
	}
	
	public function encodeJson($responseData) {
		$jsonResponse = json_encode($responseData);
		return $jsonResponse;		
	}
	
	public function encodeXml($responseData) {
		// creating object of SimpleXMLElement
		$xml = new SimpleXMLElement('<?xml version="1.0"?><mobile></mobile>');
		foreach($responseData as $key=>$value) {
			$xml->addChild($key, $value);
		}
		return $xml->asXML();
	}
	
}
?>	