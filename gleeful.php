<?php
//file_put_contents('post_outfile.txt', var_export($_POST, true));
//file_put_contents('file_outfile.txt', var_export($_FILES, true));

ini_set("log_errors", 1);
ini_set("display_errors", 0);
ini_set("error_log", "myscript_error.log");

$stuff = 'Problem is '.$_POST['probDesc'].'. Location is '.$_POST['loc'];
file_put_contents('outfile1.txt', $stuff);
move_uploaded_file($_FILES['bmp']['tmp_name'], 'out1.png');

header('Content-Type: application/json');
echo json_encode('Done');
?>