<?PHP
if(isset($_POST['imza_saati']) ){

	
	$con=mysqli_connect("localhost","root","","yoklama");
	$imza_saati=$_POST['imza_saati'];
	$ogrenci_adi=$_POST['ogrenci_adi'];
	$ogrenci_numarasi=$_POST['ogrenci_numarasi'];
	$ders_adi=$_POST['ders_adi'];
	
	
	
	$imza =mysqli_query($con,"INSERT INTO `imza`(`imza_saati`,`ders_adi`,`ogrenci_adi`,`ogrenci_numarasi`) VALUES ('$imza_saati','$ders_adi','$ogrenci_adi','$ogrenci_numarasi')");
	if($imza){
		echo "verieklendi";

	}else{
		echo "veri eklenmedi";
	}



	
}
?>
<html>
<head>
<title>Insert Data</title>	
</head>
<body>
<h1>Insert Data</h1>
	<form action="<?PHP $_PHP_SELF ?>" method="post">
		imza saati <input type="text" name="imza_saati" placeholder="Student Name" /> <br/> <br/>
		ogrenci_adisoyadi <input type="text" name="ogrenci_adi" placeholder="Tel" /> <br/> <br/>
		ogrenci_numarasi <input type="text" name="ogrenci_numarasi" placeholder="Bolum" /> <br/> <br/>
		ders_adi <input type="text" name="ders_adi" placeholder="Telefon" /> <br/> <br/>
		



		<input type="submit" value="Insert" /> <br/>
	</form>
</body>
</html>	