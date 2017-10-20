<?php include 'vars.php'; ?>
<!-- Footer -->
<footer>
    <div class="container">
        <ul class="list-inline">
            <?php
              $toEnd = count($clickable_tabs);
              foreach ($clickable_tabs as $key => $value) {
                echo '<li class="list-inline-item">';
                echo '    <a href="'.$value.'">'.$key.'</a>';
                echo '</li>';
                if (0 !== --$toEnd) {
                  echo '<li class="footer-menu-divider list-inline-item">&sdot;</li>';
                }
              }
            ?>
        </ul>
        <p class="affiliated text-muted small">Minecraft content and materials are trademarks and copyrights of Mojang and its licensors.
            <br style="clear:both" />All rights reserved.
            <br style="clear:both" />This site is not affiliated with Mojang.</p>
        <p class="copyright text-muted small">Copyright &copy; Eric Golde 2017.
            <br style="clear:both" />All Rights Reserved</p>
    </div>
    <br>
</footer>
<!-- Bootstrap core JavaScript -->
<script src="js/jquery.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/landing.js"></script>
</body>

</html>