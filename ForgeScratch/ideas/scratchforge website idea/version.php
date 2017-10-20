<?php 
include 'include/vars.php'; 

$shouldDownload = intval($_GET['dl']);

if($shouldDownload == 1){
	echo $versionDownload;
}
else{
	echo $version;
}
?>
