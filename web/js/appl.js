var cats = document.getElementsByClassName("cat")

function hideAllCats() {
    for (var i = 1; i <= cats.length; i++) {
        cats[i].style.display = "none";
    }
}

function bindButtonToCat(idNumber) {
    var element = document.getElementById("button" + idNumber);
    element.addEventListener("click", function (e) {
        hideAllCats();
        document.getElementById("cat"+idNumber).style.display = "block";
    });
}

function bindCounterToCat(idNumber) {
    var cat = "cat" + idNumber;
    var element = document.getElementById(cat);
    element.addEventListener("click", function (e) {
        var eSpan = element.getElementsByTagName("span");
        var count = parseInt(eSpan[0].textContent) + 1;
        eSpan[0].textContent = count;
    });
}

var buttons = document.getElementsByTagName("button");
for (var i = 1; i <= buttons.length; i++) {
    bindButtonToCat(i);
}

for (var i = 1; i <= buttons.length; i++) {
    bindCounterToCat(i);
}

hideAllCats();

