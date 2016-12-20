<?PHP
if(isset($_POST['beacon_uuid']) ){
	date_default_timezone_set('Europe/Istanbul');
	$saat=date('H');
	
	$con=mysqli_connect("localhost","root","","yoklama");
	$beacon_uuid=$_POST['beacon_uuid'];
	$beacon_major=$_POST['beacon_major'];
	$beacon_minor=$_POST['beacon_minor'];
	
	
	$ders_adi=mysqli_query($con,"SELECT `ders_adi` AS ad FROM `ders` WHERE `ders_baslangicsaati`<='$saat' AND `ders_bitissaati`>'$saat' AND `sinif_id` = (SELECT `sinif_id` FROM `sinif` WHERE `beacon_id`IN(SELECT `beacon_id` FROM `beacon` WHERE `beacon_uuid`='$beacon_uuid' AND `beacon_major`='$beacon_major' AND `beacon_minor`='$beacon_minor') )");
	$veri=echo $ders_adi->fetch_object()->ad;
	 $sayi=mysqli_num_rows($ders_adi);
	if($sayi>0){
		
	$veri=echo $ders_adi->fetch_object()->ad;

	}
	else{
	echo "ders yoktur";
	}

	$result=mysqli_query($con,"insert into deneme(adsoyadi,ogrno,ders_adi) values('$adsoyad','$ogrno','$veri')");
	
}
?>
