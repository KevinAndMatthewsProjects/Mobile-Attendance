 <html>
        <head>
        <title>pharmacy</title>
        </head>
        <body>
        <form method="post" action="pharmacy.php">
        <?php
       //some code
            if(array_key_exists('update',$_POST)){
                //somecode
                }
        ?>
<input type="submit" name="update" value="<?php echo if(isset($_GET['update'])) ? 'Show' : 'Update' ?> ">
    </form>
    </body>
    </html>