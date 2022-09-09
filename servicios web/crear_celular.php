<?php
$dui=isset($_GET['dui'])?$_GET['dui']: "";
$modelo=isset($_GET['modelo'])?$_GET['modelo']: "";
$memoria=isset($_GET['memoria'])?$_GET['memoria']: "";

//Base de datos local
$servidor = '192.168.1.6';
$database = 'Parcial3';
$port = '3306';
$username = 'admin';
$password = '123456';

//Crear conexion
$conn = mysqli_connect($servidor, $username, $password, $database, $port);
$query = "INSERT INTO `celular` ( `dui`, `modelo`, `memoria`) VALUES ('".$dui."', '".$modelo."', '".$memoria."');";

//Resultados
$resultado = mysqli_query($conn, $query) or die(mysqli_error($conn));
//Si la respuesta es correcta enviamos 1 y sino enviamos 0
if (mysqli_affected_rows($conn) == 1) {
    $respuesta[] = array_map('utf8_encode', array('resultado' => 1));
    echo json_encode($respuesta);
}
mysqli_close($conn);
?>