<?php 
$page_title = "Home";
$page_css = "landing-page";
include("include/header.php");
?>
<!-- Header -->
<header class="intro-header" id="home">
    <div class="container">
        <div class="intro-message">
            <h1>ScratchForge</h1>
            <h3>A Block Coding Language to Mod Minecraft</h3>
            <hr class="intro-divider">
            <ul class="list-inline intro-social-buttons">
                <li class="list-inline-item">
                    <a href="#" class="btn btn-secondary btn-lg">
                <i class="fa fa-download" aria-hidden="true"></i>
                <span class="network-name">Download</span>
              </a>
                </li>
                <li class="list-inline-item">
                    <a href="https://github.com/egold555/ForgeScratch" class="btn btn-secondary btn-lg">
                <i class="fa fa-github fa-fw"></i>
                <span class="network-name">Github</span>
              </a>
                </li>
            </ul>
        </div>
    </div>
</header>
<!-- Page Content -->
<section class="content-section-a">
    <div class="container">
        <div class="row">
            <div id="what-we-do-box">
                <div id="what-we-do-box-icon">
                    <i aria-hidden="true" class="fa fa-wrench"></i>
                </div>
                <h3 id="what-we-do-box-title">Easy to Use</h3>
                <p id="what-we-do-box-desc">A simple, intuitive, and friendly UI allow for kids and teens alike to create Minecraft mods with no Java knowledge.</p>
            </div>
            <div id="what-we-do-box">
                <div id="what-we-do-box-icon">
                    <i aria-hidden="true" class="fa fa-gift"></i>
                </div>
                <h3 id="what-we-do-box-title">Express Creativity</h3>
                <p id="what-we-do-box-desc">A vast range of blocks for kids to use and express their creativity with endless options.</p>
            </div>
            <div id="what-we-do-box">
                <div id="what-we-do-box-icon">
                    <i aria-hidden="true" class="fa fa-rocket"></i>
                </div>
                <h3 id="what-we-do-box-title">Share with the World</h3>
                <p id="what-we-do-box-desc">A easy way to share and load other people's creations, and turn them into their own.</p>
            </div>
            <div id="what-we-do-box">
                <div id="what-we-do-box-icon">
                    <i aria-hidden="true" class="fa fa-book"></i>
                </div>
                <h3 id="what-we-do-box-title">Fit for Teachers</h3>
                <p id="what-we-do-box-desc">Built in is a system for teachers to limit what kids are allowed to do while still allowing kids to have fun and express their creativity.</p>
            </div>
        </div>
    </div>
    <!-- /.container -->
</section>
<section class="content-section-b" id="about">
    <!-- Page Content -->
    <div class="container">
        <!-- Introduction Row -->
        <div class="row">
            <div class="col-lg-12">
                <center><h2 class="my-4">About Me</h2></center>
            </div>
            <div class="col-lg-6 text-center mb-4 col-centered">
                <img class="rounded-circle img-fluid d-block mx-auto" src="img/eric-avatar.png" alt="">
                <h4>Eric Golde</h4>
                <p>Hi! I am Eric Golde. I desided to develop ScratchForge because while teaching a summer camp at my old middle school, Hamlin Robinson School, we were using online block based coding for the game Minecraft. These javascript recreations often has one end goal. I personally felt like these were a great starting point for kids, but lacked freedom of creativity. So I set to work trying to develop a block language that integrated with Minecraft Forge. A little while later, here we are.</p>
            </div>
        </div>
    </div>
    <!-- /.container -->
</section>

<script src="js/smooth-scroll.min.js"></script>
<script>
  var scroll = new SmoothScroll('a[href*="#"]', {
    speed: 1000,
  });
</script>


<?php include("include/footer.php");?>