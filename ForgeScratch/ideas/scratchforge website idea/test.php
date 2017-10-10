<?php 
$page_title = "Home";
$page_css = "landing-page";
include("header.php");
?>

    <!-- Header -->
    <header class="intro-header">
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


<?php include("footer.html");?>