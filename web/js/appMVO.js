function cat(clickCount, name, imgSrc) {
    this.clickCount = clickCount;
    this.name = name;
    this.imgSrc = imgSrc;

    return this;
}

var model = {
    currentCat: null,
    cats: [
        new cat(0,'Tom','img/Tom.jpeg'),
        new cat(0,'Jerry','img/Jerry.png'),
        new cat(0,'Peppa','img/Peppa.png')
    ]
};

var catView = {
    init: function () {
        this.catNameElem = document.getElementById("cat-name");
        this.catImageElem = document.getElementById("cat-img");
        this.countElem = document.getElementById("cat-count");

        this.catImageElem.addEventListener("click", function (e) {
            octopus.incrementCount();
        });

        this.render();
    },

    render: function () {
        var currentCat = octopus.getCurrentCat();
        this.countElem.textContent = currentCat.clickCount;
        this.catNameElem.textContent = currentCat.name;
        this.catImageElem.src = currentCat.imgSrc;
    }
};

var catListView = {
    init: function () {
        this.catListElement = document.getElementById("cat-list");
        this.render();
    },

    render: function () {
        var cats = octopus.getCats();
        this.catListElement.innerHTML = "";
        for (var i = 0; i < cats.length; i++) {
            var cat = cats[i];
            var elem = document.createElement("li");
            elem.textContent = cat.name;

            elem.addEventListener("click", (function () {
                return function () {
                    octopus.getCurrentCat(cat);
                }
            }) (cat));

            this.catListElement.appendChild(elem);
        }
    }
};

var octopus = {
    init : function () {
        model.currentCat = model.cats[0];
        catListView.init();
        catView.init();
    },
    getCurrentCat: function () {
        return model.currentCat;
    },
    getCats: function () {
        return model.cats;
    },
    getCurrentCat: function (cat) {
        model.currentCat = cat;
        catView.render();
    },
    incrementCount: function () {
        model.currentCat.clickCount++;
        catView.render();
    }
};

octopus.init();