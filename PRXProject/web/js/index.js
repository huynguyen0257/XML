function laptop(id, img, name, cpu, core, thread, baseClock, boostClock, cache, ram, monitor, weight, price, model) {
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
    var processorLevel = form.cpu.value;
    var ramMemory = form.ram.value;
    var monitorSize = form.monitorSize.value;
    var priceString = form.price.value;
    callUrl("AdviceServlet", {
        "processorLevel": processorLevel,
        "ramMemory": ramMemory,
        "monitorSize": monitorSize,
        "priceString": priceString
    }).then(rs =>{
        model.xmlDOM = rs;
        searchNode();
        renderListAdvice()
    })
}

function callUrl(url, data) {
    model.xmlHttp = new XMLHttpRequest();
    if (model.xmlHttp == null) {
        window.alert("Your browser donse not support AJAX!");
        return;
    }
    url += "?" + addDataToUrl(data);
    model.xmlHttp.open("GET", url, true);
    return new Promise(function (resolve, reject) {
        model.xmlHttp.onreadystatechange = function () {
            if (model.xmlHttp.readyState === 4) {
                if (model.xmlHttp.status != 200) {
                    reject("Error, status code = " + model.xmlHttp.status);
                } else {
                    resolve(model.xmlHttp.responseXML);
                }
            }
        };
        model.xmlHttp.send(null);
    });
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
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/id", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
            document.evaluate("/laptops/laptop[" + (i + 1) + "]/image", model.xmlDOM, null, XPathResult.STRING_TYPE, null).stringValue,
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
    items.innerHTML = `
            ${model.laptops.length === 0 ? `<h1 style="color: #ff0000">We can not find any laptop near match with your suggest!!!</h1>` :
        model.laptops.map((item) => `
                <div class="item">
                <a href="MainServlet?btAction=showDetailLaptop&laptopId=${item.id}">
                    <img src="${item.img}"/>
                </a>
                <div class="item-info">
                    <a href="MainServlet?btAction=showDetailLaptop&laptopId=${item.id}">
                        <h3>${item.name}</h3>
                    </a>
                    <h4>${item.price}</h4>
                    <p>${item.model}</p>
                </div>
            </div>
                `).join('')
    }
    `;
}

function deleteChild() {
    model.laptops = [];
    var e = document.getElementById("items");
    //e.firstElementChild can be used.
    var child = e.lastElementChild;
    while (child) {
        e.removeChild(child);
        child = e.lastElementChild;
    }
}

function search() {
    deleteChild();
    var txtSearch = document.getElementById("txtSearch").value;
    callUrl("SearchServlet",{"txtSearch" : txtSearch}).then(rs => {
        model.xmlDOM = rs;
        searchNode();
        renderListAdvice();
    })
}

function checkKey(input) {
    // window.alert(input.value);
    // window.alert(input.key);
    window.alert(input.event.keyCode);

    if (input.keyCode === 13){
        window.alert("Enter ne");
    }


}

function init() {
    var input = document.getElementById("txtSearch");

    input.addEventListener("keyup", function(event) {
        // Number 13 is the "Enter" key on the keyboard
        if (event.keyCode === 13) {
            // window.alert("onInputLoad === 13")
            search();
        }
    });
}