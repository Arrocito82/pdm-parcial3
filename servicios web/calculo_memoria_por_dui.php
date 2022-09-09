<?php
$dui=isset($_GET['dui'])?$_GET['dui']: "";

//Base de datos local
$servidor = '192.168.1.6';
$database = 'Parcial3';
$port = '3306';
$username = 'admin';
$password = '123456';

//Crear conexion
$conn = mysqli_connect($servidor, $username, $password, $database, $port);

//declaracion de query
$query = "SELECT SUM( `memoria` ) as resultado
FROM `celular`
WHERE `dui` = '".$dui."';";

//ejecutando
$result = $conn->query($query);
while($fila=$result -> fetch_array()){
    $consulta[] = array_map('utf8_encode', $fila);
}

echo json_encode($consulta);

mysqli_close($conn);
?>