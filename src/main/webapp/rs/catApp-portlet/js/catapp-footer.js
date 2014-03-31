var htmlString = "$(document).ready(function() {";

htmlString += "$('.sidebar ul li a').click(function(ev) {";
htmlString += "$('.sidebar .sub-menu').not($(this).parents('.sub-menu')).slideUp();";
htmlString += "$(this).next('.sub-menu').slideToggle();";
htmlString += "ev.stopPropagation();";
htmlString += "});"
htmlString += "$('.menudrop li a').click(function(ev) {";
htmlString += "$(this).next('.dropdown').fadeToggle('slow');";
htmlString += "});"

htmlString += "});";
