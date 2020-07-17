function laptop(id,img, name, cpu, core, thread, baseClock, boostClock, cache, ram, monitor, weight, price,model) {
    this.id = id;
    this.img = img;
    this.name = name;
    this.cpu = cpu;
    this.core = core;
    this.thread = thread;
    this.baseClock = baseClock;
    this.boostClock = boostClock;
    this.cache = cache;
    this.ram = ram;
    this.monitor = monitor;
    this.weight = weight;
    this.weight = weight;
    this.price = price;
    this.model = model;

    return this;
}

var model = {
    xmlHttp: null,
    xmlDOM: XMLDocument,
    laptops: []
}

function getAdvice(form) {
    deleteChild()
    model.laptops = [];
    var processorLevel = form.cpu.value;
    var ramMemory = form.ram.value;
    var monitorSize = form.monitorSize.value;
    var priceString = form.price.value;
    callUrl("AdviceServlet",{"processorLevel":processorLevel,"ramMemory":ramMemory,"monitorSize":monitorSize,"priceString":priceString});
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
            // window.alert(model.xmlDOM);
            searchNode();
            renderListAdvice();
        }
    }
    model.xmlHttp.send(null);
    // xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
}

function addDataToUrl(data) {
    var result = "";
    Object.entries(data).forEach(function (a) {
        result += a[0] + "=" + a[1] + "&";
    })
    return result.slice(0, result.length - 1);
}

function searchNode() {
    var numberOfLaptop = document.evaluate("count(//laptop)", model.xmlDOM, null, XPathResult.NUMBER_TYPE, null).numberValue;
    for (var i = 0; i < numberOfLaptop; i++) {
        model.laptops[i] = new laptop(
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/id", model.xmlDOM,null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/image", model.xmlDOM,null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/name", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/processor/name", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/processor/core", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/processor/thread", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/processor/baseClock", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/processor/boostClock", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/processor/cache", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/ram/memory", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/monitor/size", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/weight", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/price", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/model", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue
        )
    }
}

function renderListAdvice() {
    var items = document.getElementById("items");
    if (model.laptops.length == 0){
        var h1 = document.createElement("h1");
        h1.style.color = "red";
        h1.innerHTML = "We can not find any laptop near match with your suggest!!!";
        items.appendChild(h1);
    }
    for (var i = 0; i < model.laptops.length; i++) {
        var currentLaptop = model.laptops[i];
        var divElement = document.createElement("div");
        divElement.className = "item";
        var a1 = document.createElement("a");
        a1.href = "MainServlet?btAction=showDetailLaptop&laptopId=" + currentLaptop.id;
        var imageElement = document.createElement("img");
        imageElement.src = currentLaptop.img;
        var childDivElement = document.createElement("div");
        childDivElement.className = "item-info";
        var h3Element = document.createElement("h3");
        h3Element.innerHTML = currentLaptop.name;
        var a2 = document.createElement("a");
        a2.href = "MainServlet?btAction=showDetailLaptop&laptopId=" + currentLaptop.id;
        var h4Element = document.createElement("h4");
        h4Element.innerHTML = "Price : " + currentLaptop.price + " VND";
        var pElement = document.createElement("p");
        pElement.innerHTML = currentLaptop.model;
        a2.appendChild(h3Element);
        a1.appendChild(imageElement);
        childDivElement.appendChild(a2);
        childDivElement.appendChild(h4Element);
        childDivElement.appendChild(pElement);
        divElement.appendChild(a1);
        divElement.appendChild(childDivElement);
        items.appendChild(divElement);
    }
}

function deleteChild() {
    var e = document.getElementById("items");
    //e.firstElementChild can be used.
    var child = e.lastElementChild;
    while (child) {
        e.removeChild(child);
        child = e.lastElementChild;
    }
}
// var btn = document.getElementById(
//     "btn").onclick = function() {
//     deleteChild();
// }