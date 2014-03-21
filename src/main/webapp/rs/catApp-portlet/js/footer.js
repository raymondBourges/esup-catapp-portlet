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


function launchJs() {
    var menu = document.createElement('script');
    menu.type = 'text/javascript';
    menu.innerHTML = htmlString;
    document.getElementById('dropdown-Menu').appendChild(menu);
}
setTimeout("launchJs();", 2000)

$(function () {
    $("#sortable").sortable({
        placeholder: "ui-state-highlight"
    });
    $("#sortable").disableSelection();
});

$(function () {
    $('#slider-1').liquidSlider({
        autoHeight: false,
        slideEaseDuration: 1000,
        heightEaseDuration: 1000,
        animateIn: "lightSpeedOut",
        animateOut: "lightSpeedIn"
    });
});