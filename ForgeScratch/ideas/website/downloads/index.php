<?php 
$page_title = "Downloads";
$page_css = "downloads-page";
$page_dir = "../";
include("../include/header.php");
?>

<?php
  if ($handle = opendir('.')) {
    while (false !== ($file = readdir($handle))) {
      if ($file != "." && $file != ".." && $file != "index.php" && $file != "assets") {
        $thelist .= '<li><a href="'.$file.'">'.$file.'</a></li>';
      }
    }
    closedir($handle);
  }
?>

<h3>Downloads: </h3>
<ul><?php echo $thelist; ?></ul>

<?php include("../include/footer.php");?>