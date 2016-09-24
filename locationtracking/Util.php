<?php
/* 
A domain Class to demonstrate RESTful web services
*/
Class Util {
	
	
	public $data = "";
	const DB_SERVER = "localhost";
	const DB_USER = "animaltr_perumal";
	const DB_PASSWORD = "Welcome@321";
	const DB = "animaltr_gpstrack";


//Database connection
public function dbConnect()
{
 return mysqli_connect(self::DB_SERVER,self::DB_USER,self::DB_PASSWORD,self::DB);
 
}


}
?>