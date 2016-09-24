<?php
/* 
A domain Class to demonstrate RESTful web services
*/
Class Mobile {
	
	
	public $data = "";
	const DB_SERVER = "127.10.204.130:3306";
	const DB_USER = "adminsVg23g3";
	const DB_PASSWORD = "nKd3hSrurtVj";
	const DB = "gpstrack";


//Database connection
public function dbConnect()
{
 return mysqli_connect(self::DB_SERVER,self::DB_USER,self::DB_PASSWORD);
 
}

	
	
private $db = NULL;
	private $mobiles = array(
		1 => 'Apple iPhone 6S',  
		2 => 'Samsung Galaxy S6',  
		3 => 'Apple iPhone 6S Plus',  			
		4 => 'LG G4',  			
		5 => 'Samsung Galaxy S6 edge',  
		6 => 'OnePlus 2');
		
	/*
		you should hookup the DAO here
	*/
	public function getAllMobile(){
		return $this->mobiles;
	}
	
	public function getMobile($id){
		
		$mobile = array($id => ($this->mobiles[$id]) ? $this->mobiles[$id] : $this->mobiles[1]);
		return $mobile;
	}	
}
?>