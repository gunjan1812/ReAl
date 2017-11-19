
function openTabHeader(cityName, elmnt, fontWeight) {
    // Hide all elements with class="tabcontent" by default */
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabHeader");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Remove the background color of all tablinks/buttons
    tablinks = document.getElementsByClassName("tablink");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].style.fontWeight = "normal";
    }

    // Show the specific tab content
    document.getElementById(cityName).style.display = "block";

    // Add the specific color to the button used to open the tab content
    elmnt.style.fontWeight = fontWeight;
    elmnt.style.ac
}

// Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();

function openTab(evt, cityName) {
    // Declare all variables
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";

    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablink");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(cityName).style.display = "block";
    evt.currentTarget.className += " active";
}

function handleOpenClose(evt, openCloseAction) {
    // Declare all variables
    var i, divcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    divcontent = document.getElementsByClassName("inner-hidden-container");
    for (i = 0; i < divcontent.length; i++) {
        divcontent[i].style.display = "none";
    }
    document.getElementById(openCloseAction).style.display = "block";
}

var index = 1;

function plusIndex(n) {
    index = index + 1;
    showImage(index);
}

function showImage(n) {
    var i;
    var x = document.getElementsByName("slides");
    if (n > x.length) {
        index = 1
    }
    if (n < 1) {
        index = x.length
    }
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none"
    }
    x[index - 1].style.display = "block";
}

autoSlide();

function autoSlide() {
    var i;
    var x = document.getElementsByClassName("slides");
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none"
    }
    if (index > x.length) {
        index = 1
    }
    x[index - 1].style.display = "block";
    index++;
    setTimeout(autoSlide, 2000);
}
