<?php include 'vars.php'; ?>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="A simple, intuitive, and friendly UI allow for kids and teens alike to create Minecraft mods with no Java knowledge.">
    <meta name="author" content="Eric Golde">
    <!-- Bootstrap core CSS -->
    <link href="<?php echo $page_dir; ?>css/bootstrap.min.css" rel="stylesheet">
    <link href="<?php echo $page_dir; ?>css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">
    <link href="<?php echo $page_dir; ?>css/all.css" rel="stylesheet">
    <link href="<?php echo $page_dir; ?>css/<?php echo $page_css; ?>.css" rel="stylesheet">
    <title>
        <?php echo $page_title; ?> - ScratchForge</title>
</head>

<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
        <div class="container">
            <a class="navbar-brand" href="#">ScratchForge</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ml-auto">
                    <?php
              foreach ($clickable_tabs as $key => $value) {
                echo '<li class="nav-item">';
                echo '    <a class="nav-link" href="'.$value.'">'.$key.'</a>';
                echo '</li>';
              }
            ?>
                </ul>
            </div>
        </div>
    </nav>