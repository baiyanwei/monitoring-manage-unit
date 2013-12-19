<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

 <HTML>
    <HEAD>
        <title>WebForm5</title>

        <style>/* Root = Horizontal, Secondary = Vertical */
ul#navmenu-h {
  margin: 0;
  border: 0 none;
  padding: 0;
  width: 500px; /*For KHTML*/
  list-style: none;
  height: 24px;
}

ul#navmenu-h li {
  margin: 0;
  border: 0 none;
  padding: 0;
  float: left; /*For Gecko*/
  display: inline;
  list-style: none;
  position: relative;
  height: 24px;
}

ul#navmenu-h ul {
  margin: 0;
  border: 0 none;
  padding: 0;
  width: 160px;
  list-style: none;
  display: none;
  position: absolute;
  top: 24px;
  left: 0;
}

ul#navmenu-h ul:after /*From IE 7 lack of compliance*/{
  clear: both;
  display: block;
  font: 1px/0px serif;
  content: ".";
  height: 0;
  visibility: hidden;
}

ul#navmenu-h ul li {
  width: 160px;
  float: left; /*For IE 7 lack of compliance*/
  display: block !important;
  display: inline; /*For IE*/
}

/* Root Menu */
ul#navmenu-h a {
  border: 1px solid #FFF;
  border-right-color: #CCC;
  border-bottom-color: #CCC;
  padding: 0 6px;
  float: none !important; /*For Opera*/
  float: left; /*For IE*/
  display: block;
  background: #EEE;
  color: #666;
  font: bold 10px/22px Verdana, Arial, Helvetica, sans-serif;
  text-decoration: none;
  height: auto !important;
  height: 1%; /*For IE*/
}

/* Root Menu Hover Persistence */
ul#navmenu-h a:hover,
ul#navmenu-h li:hover a,
ul#navmenu-h li.iehover a {
  background: #CCC;
  color: #FFF;
}

/* 2nd Menu */
ul#navmenu-h li:hover li a,
ul#navmenu-h li.iehover li a {
  float: none;
  background: #EEE;
  color: #666;
}

/* 2nd Menu Hover Persistence */
ul#navmenu-h li:hover li a:hover,
ul#navmenu-h li:hover li:hover a,
ul#navmenu-h li.iehover li a:hover,
ul#navmenu-h li.iehover li.iehover a {
  background: #CCC;
  color: #FFF;
}

/* 3rd Menu */
ul#navmenu-h li:hover li:hover li a,
ul#navmenu-h li.iehover li.iehover li a {
  background: #EEE;
  color: #666;
}

/* 3rd Menu Hover Persistence */
ul#navmenu-h li:hover li:hover li a:hover,
ul#navmenu-h li:hover li:hover li:hover a,
ul#navmenu-h li.iehover li.iehover li a:hover,
ul#navmenu-h li.iehover li.iehover li.iehover a {
  background: #CCC;
  color: #FFF;
}

/* 4th Menu */
ul#navmenu-h li:hover li:hover li:hover li a,
ul#navmenu-h li.iehover li.iehover li.iehover li a {
  background: #EEE;
  color: #666;
}

/* 4th Menu Hover */
ul#navmenu-h li:hover li:hover li:hover li a:hover,
ul#navmenu-h li.iehover li.iehover li.iehover li a:hover {
  background: #CCC;
  color: #FFF;
}

ul#navmenu-h ul ul,
ul#navmenu-h ul ul ul {
  display: none;
  position: absolute;
  top: 0;
  left: 160px;
}

/* Do Not Move - Must Come Before display:block for Gecko */
ul#navmenu-h li:hover ul ul,
ul#navmenu-h li:hover ul ul ul,
ul#navmenu-h li.iehover ul ul,
ul#navmenu-h li.iehover ul ul ul {
  display: none;
}

ul#navmenu-h li:hover ul,
ul#navmenu-h ul li:hover ul,
ul#navmenu-h ul ul li:hover ul,
ul#navmenu-h li.iehover ul,
ul#navmenu-h ul li.iehover ul,
ul#navmenu-h ul ul li.iehover ul {
  display: block;
}

</style><script language="javascript">
navHover = function() {
    var lis = document.getElementById("navmenu-h").getElementsByTagName("LI");
    for (var i=0; i<lis.length; i++) {
        lis[i].onmouseover=function() {
            this.className+=" iehover";
        }
        lis[i].onmouseout=function() {
            this.className=this.className.replace(new RegExp(" iehover\\b"), "");
        }
    }
}
if (window.attachEvent) window.attachEvent("onload", navHover);
        </script>
    </HEAD>
    <body>
   <ul id="navmenu-h">
   <li><a href="#">Root nav item1</a>
   <ul>
   <li><a href="#">Sub nav item1</a></li>
<li><a href="#">Sub nav item1-2</a></li>
   </ul>
   </li>
<li><a href="#">Root nav item2</a>
   <ul>
   <li><a href="#">Sub nav item2</a></li>
<li><a href="#">Sub nav item2-2</a></li>
   </ul>
   </li>
<li><a href="#">Root nav item3</a>
   <ul>
   <li><a href="#">Sub nav item3</a></li>
<li><a href="#">Sub nav item3-2</a></li>
   </ul>
   </li>
   </ul>
</body>
</html>