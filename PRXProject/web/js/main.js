function laptop(img, name, cpuMark, ramMark, monitorMark, weightMark, cpu, core, thread, baseClock, boostClock, cache, ram, monitor, weight) {
    this.img = img;
    this.name = name;
    this.cpuMark = cpuMark;
    this.ramMark = ramMark;
    this.monitorMark = monitorMark;
    this.weightMark = weightMark;
    this.cpu = cpu;
    this.core = core;
    this.thread = thread;
    this.baseClock = baseClock;
    this.boostClock = boostClock;
    this.cache = cache;
    this.ram = ram;
    this.monitor = monitor;
    this.weight = weight;

    return this;
}

var model = {
    compareType: null,
    highestMark: null,
    xmlDOM: XMLDocument,
    xmlHttp: null,
    laptops: []
}

function addDataToUrl(data) {
    var result = "";
    Object.entries(data).forEach(function (a) {
        result += a[0] + "=" + a[1] + "&";
    })
    return result.slice(0, result.length - 1);
}

function callUrl(url, data) {
    model.xmlHttp = new XMLHttpRequest();
    if (model.xmlHttp == null) {
        window.alert("Your browser donse not support AJAX!");
        return;
    }
    url += "?" + addDataToUrl(data);
    model.xmlHttp.open("GET", url, true);
    model.xmlHttp.onreadystatechange = function () {
        if (this.readyState == 4){
            model.xmlDOM = model.xmlHttp.responseXML;
            searchNode();
            laptopListView();
        }
    }
    model.xmlHttp.send(null);
    // xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
}

function searchNode() {
    var numberOfLaptop = document.evaluate("count(//laptop)", model.xmlDOM, null, XPathResult.NUMBER_TYPE, null).numberValue;
    for (var i = 0; i < numberOfLaptop; i++) {
        model.laptops[i] = new laptop(
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/image", model.xmlDOM,null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/name", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/processor/mark", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/ram/mark", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/monitor/mark", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/weightMark", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/processor/name", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/processor/core", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/processor/thread", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/processor/baseClock", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/processor/boostClock", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/processor/cache", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/ram/memory", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/monitor/size", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/weight", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue
        )
    }
}

function get(name) {
    if (name = (new RegExp('[?&]' + encodeURIComponent(name) + '=([^&]*)')).exec(location.search))
        return decodeURIComponent(name[1]);
}

function initComparePage() {
    var id1 = get("id1");
    var id2 = get("id2");
    model.compareType = "all";
    callUrl("CompareLaptopServlet", {"id1": id1, "id2": id2, "compareType": model.compareType})
}

function onChangeCompareType(compareType) {
    if (model.compareType != compareType){
        model.compareType = compareType;
        model.highestMark = null;
        laptopListView();
    }
}

function laptopListView() {
    var laptops = model.laptops;
    var tableElement = document.getElementById("compare-table");

    for (var i = 0; i < laptops.length; i++) {
        var laptop = laptops[i];
        for (var j = 0; j < tableElement.rows.length; j++) {
            loadCell(tableElement, j, i + 1, laptop);
        }
    }
}

function loadCell(tableElement, rowNumber, cellNumber, laptop) {
    var row = tableElement.rows[rowNumber];
    if (rowNumber == 0) {
        var img = document.createElement("img");
        img.src = laptop.img;
        row.cells[cellNumber].removeChild(row.cells[cellNumber].firstElementChild);
        row.cells[cellNumber].appendChild(img);
    } else if (rowNumber == 1) {
        row.cells[cellNumber].innerHTML = laptop.name;
    } else if (rowNumber == 2) { //mark
        var mark;
        if (model.compareType == "all") {
            mark = (parseFloat(laptop.cpuMark) + parseFloat(laptop.ramMark) + parseFloat(laptop.monitorMark) + parseFloat(laptop.weightMark)) / 4 + 10;
        } else if (model.compareType == "power") {
            mark = (parseFloat(laptop.cpuMark) * 3 + parseFloat(laptop.ramMark) * 3 + parseFloat(laptop.monitorMark) + parseFloat(laptop.weightMark)) / 8 + 10;
        } else if (model.compareType == "compactness") {
            mark = (parseFloat(laptop.cpuMark) + parseFloat(laptop.ramMark) + parseFloat(laptop.monitorMark) * 3 + parseFloat(laptop.weightMark) * 3) / 8 + 10;
        }

        if (model.highestMark == null || model.highestMark < mark) {
            model.highestMark = mark;
            for (var i = 0; i < model.laptops.length; i++) {
                row.cells[i + 1].style.background = "none";
            }
            row.cells[cellNumber].style.background = "green";
        }
        row.cells[cellNumber].firstElementChild.innerHTML = Math.round(mark * 100) / 100 + "";
    } else if (rowNumber == 3) { //CPU name
        row.cells[cellNumber].innerHTML = laptop.cpu;
    } else if (rowNumber == 4) {//Core
        row.cells[cellNumber].innerHTML = laptop.core;
    } else if (rowNumber == 5) {//Thread
        row.cells[cellNumber].innerHTML = laptop.thread;
    } else if (rowNumber == 6) {//BaseClock
        row.cells[cellNumber].innerHTML = laptop.baseClock + " GHz";
    } else if (rowNumber == 7) {//BoostClock
        row.cells[cellNumber].innerHTML = laptop.boostClock + " GHz";//10.228821092385223
    } else if (rowNumber == 8) {//Cache
        row.cells[cellNumber].innerHTML = laptop.cache + " MB";
    } else if (rowNumber == 9) {//Ram
        row.cells[cellNumber].innerHTML = laptop.ram + " GB";
    } else if (rowNumber == 10) {//Monitor
        row.cells[cellNumber].innerHTML = laptop.monitor + " INCH";
    } else if (rowNumber == 11) {//Weight
        row.cells[cellNumber].innerHTML = laptop.weight + " kg"
    }
}



