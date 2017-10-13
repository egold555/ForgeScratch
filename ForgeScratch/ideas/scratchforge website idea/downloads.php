<?php 
$page_title = "Downloads";
$page_css = "explore-page";
include("include/header.php");
?>

<?php
  if ($handle = opendir('downloads')) {
    while (false !== ($file = readdir($handle))) {
      if ($file != "." && $file != ".." && $file != "index.html") {
        $thelist .= '<li><a href="downloads/'.$file.'">'.$file.'</a></li>';
      }
    }
    closedir($handle);
  }
?>
<h1>List of files:</h1>
<ul><?php echo $thelist; ?></ul>

<?php include("include/footer.php");?>