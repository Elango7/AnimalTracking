<?php
require_once("TrackingRestHandler.php");
		
$view = "";
if(isset($_GET["view"]))
	$view = $_GET["view"];
/*
controls the RESTful services
URL mapping
*/
		$mobileRestHandler = new TrackingRestHandler();

switch($view){

	case "locationAll":
		$mobileRestHandler->getDeviceInfo();
		break;

        case "idleDevice":
		$mobileRestHandler->getIdleDeviceInfo($_GET["idleMins"]);
		break;
		
	case "animals":
		$mobileRestHandler->getAnimalList();
		break;
		
	case "single":
		// to handle REST Url /mobile/show/<id>/
		
		$mobileRestHandler->getSingleDeviceInfo($_GET["id"]);
		break;
	
	case "login":
		// to handle REST Url /mobile/show/<id>/
		
		$mobileRestHandler->doLogin($_GET["userName"],$_GET["password"]);
		break;
		
	case "insert":
		// to handle REST Url /mobile/show/<id>/
 		$entityBody=file_get_contents('php://input');
		//print_r(json_decode($entityBody,true));
		$mobileRestHandler = new TrackingRestHandler();
		$mobileRestHandler->insertLocation(json_decode($entityBody,true));
		break;
	case "insertAnimalInfo":
		// to handle REST Url /mobile/show/<id>/
 		$entityBody=file_get_contents('php://input');
		//print_r(json_decode($entityBody,true));
		$mobileRestHandler = new TrackingRestHandler();
		$mobileRestHandler->insertAnimalInfo(json_decode($entityBody,true));
		break;
	case "deleteLocation":
		$mobileRestHandler->deleteLocation($_GET["deviceId"]);
		break;

	case "" :
		//404 - not found;
		break;
}
?>
